package com.demo.pipeline.transformations;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.vendor.grpc.v1p54p0.io.netty.util.internal.StringUtil;

import java.util.Arrays;
import java.util.List;

public class ProducerNameParserTransformation extends DoFn<List<String>, KV<String,String>> {

    private static final Integer CSV_COLUMN_POSITION_FOR_YEAR = 0;
    private static final Integer CSV_COLUMN_POSITION_FOR_PRODUCERS = 3;
    private static final String TOKENIZER_COMMA = ",";
    private static final String TOKENIZER_AND = " and ";
    private static final String PRODUCER_NAME_REGEX = "\\s*, and | and \\s*|, ";

    @ProcessElement
    public void processElement(ProcessContext context) {
        List<String> row = context.element();
        String year = row.get(CSV_COLUMN_POSITION_FOR_YEAR);
        String producerName = row.get(CSV_COLUMN_POSITION_FOR_PRODUCERS);

        if (!StringUtil.isNullOrEmpty(producerName) &&
                producerName.contains(TOKENIZER_COMMA) ||
                producerName.contains(TOKENIZER_AND)){

            producerName = producerName.replaceAll(PRODUCER_NAME_REGEX, TOKENIZER_COMMA);
            List<String> producersNames = Arrays.asList(producerName.split(TOKENIZER_COMMA));

            for (String name: producersNames) {
                KV keyValue = KV.of(name, year);
                context.output(keyValue);
            }
        } else if (!StringUtil.isNullOrEmpty(producerName)){
            KV keyValue = KV.of(producerName, year);
            context.output(keyValue);
        }
    }
}