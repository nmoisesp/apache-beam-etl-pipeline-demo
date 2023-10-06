package com.demo.pipeline;

import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;

public interface DataPipelineOptions extends PipelineOptions {

    @Description("Path to the CSV file")
    @Default.String("src/main/resources/movielist.csv")
    String getInputFile();
    void setInputFile(String file);

}