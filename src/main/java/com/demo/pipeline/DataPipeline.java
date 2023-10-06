package com.demo.pipeline;

import avro.shaded.com.google.common.collect.Lists;
import com.demo.pipeline.transformations.CalculateYearsIntervalTransformation;
import com.demo.pipeline.transformations.FilterWinnersTransformation;
import com.demo.pipeline.transformations.ProducerNameParserTransformation;
import com.demo.pipeline.transformations.SkipCsvHeaderLineTransformation;
import org.apache.beam.repackaged.core.org.apache.commons.lang3.StringUtils;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.GroupByKey;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.*;
import org.apache.beam.vendor.grpc.v1p54p0.io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.demo.exceptions.DataPipelineException;

import java.util.List;

@Component
public class DataPipeline {
    private static final Logger LOG = LoggerFactory.getLogger(DataPipeline.class);
    private static final Integer CSV_HEADER_SIZE = 5;
    private static final String DATABASE_JDBC_CONNECTION_URL = "jdbc:h2:mem:moviesdb;DATABASE_TO_UPPER=false";
    private static final String DATABASE_DRIVER_NAME = "org.h2.Driver";
    private static final String DATABASE_USER = "sa";

    public void run(String... args) throws DataPipelineException {
        DataPipelineOptions options = PipelineOptionsFactory
                .fromArgs(args)
                .withoutStrictParsing()
                .as(DataPipelineOptions.class);

        executePipeline(options);
    }

    private static void executePipeline(DataPipelineOptions options) {
        LOG.info("[Data Pipeline] Initiating the Apache Beam pipeline...");
        Pipeline pipeline = Pipeline.create(options);

        PCollection<String> rawData = pipeline.apply("Load CSV File", TextIO.read().from(options.getInputFile()));
        LOG.info("[Data Pipeline] Step 1 - CSV file loaded");

        PCollection<List<String>> csvRows = rawData.apply("Skip header line", ParDo.of(new SkipCsvHeaderLineTransformation()));
        LOG.info("[Data Pipeline] Step 2 - Skipped the header line and applied data validation");

        PDone rawDataStatementResult = csvRows.apply("Store the raw data",
                JdbcIO.<List<String>>write().withDataSourceConfiguration(JdbcIO.DataSourceConfiguration
                        .create(DATABASE_DRIVER_NAME, DATABASE_JDBC_CONNECTION_URL)
                        .withUsername(DATABASE_USER)
                        .withPassword(StringUtil.EMPTY_STRING))
                .withBatchSize(10L)
                .withStatement(String.format("insert into %s (year_, title, studio, producers, winner) values (?,?,?,?,?);", "movies_raw_data"))
                .withPreparedStatementSetter(
                        ((element, statement) -> {
                            if (StringUtils.isNumeric(element.get(0))) {
                                statement.setInt(1, Integer.valueOf(element.get(0)));
                                statement.setString(2, element.get(1));
                                statement.setString(3, element.get(2));
                                statement.setString(4, element.get(3));

                                if (element.size() == CSV_HEADER_SIZE)
                                    statement.setString(5, element.get(4));
                                else
                                    statement.setString(5, StringUtils.EMPTY);
                            }
                        }
                    )
                )
        );
        LOG.info("[Data Pipeline] Step 3 - Raw data stored in database");

        PCollection<List<String>> csvRowsFilteredByWinners = csvRows.apply("Filter movies by winners",
                ParDo.of(new FilterWinnersTransformation()));
        LOG.info("[Data Pipeline] Step 4 - Filtered only the winners rows");

        PCollection<KV<String,String>> producersByYear = csvRowsFilteredByWinners.apply("Parse the producer's column name",
                ParDo.of(new ProducerNameParserTransformation()));
        LOG.info("[Data Pipeline] Step 5 - Producers name parsed");

        PCollection<KV<String,Iterable<String>>> producersGroupedByName = producersByYear.apply("Group producers by name",
                GroupByKey.<String,String> create());
        LOG.info("[Data Pipeline] Step 6 - Producers grouped by name and their associated years of awards");

        PCollection<KV<String,Iterable<Integer>>> filteredByAwardsInterval =
                producersGroupedByName.apply("Calculate the years interval", ParDo.of(new CalculateYearsIntervalTransformation()));
        LOG.info("[Data Pipeline] Step 7 - Calculated the interval between the years of awards");

        PDone statementResult = filteredByAwardsInterval.apply(
                JdbcIO.<KV<String,Iterable<Integer>>>write().withDataSourceConfiguration(JdbcIO.DataSourceConfiguration
                        .create(DATABASE_DRIVER_NAME, DATABASE_JDBC_CONNECTION_URL)
                        .withUsername(DATABASE_USER)
                        .withPassword(StringUtil.EMPTY_STRING))
                        .withBatchSize(10L)
                        .withStatement(String.format("insert into %s (producer, previous_win, following_win, intervals) values (?,?,?,?);", "winner"))
                        .withPreparedStatementSetter(
                                ((element, statement) -> {
                                    List<Integer> values = Lists.newArrayList(element.getValue());
                                    statement.setString(1, element.getKey());
                                    statement.setInt(2, values.get(0));
                                    statement.setInt(3, values.get(1));
                                    statement.setInt(4, values.get(2));
                                }
                        )
                )
        );
        LOG.info("[Data Pipeline] Step 8 - Aggregated data stored in database");

        pipeline.run().waitUntilFinish();

        LOG.info("[Data Pipeline] Pipeline finished!");
    }
}