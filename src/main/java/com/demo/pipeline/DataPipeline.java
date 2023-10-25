package com.demo.pipeline;

import com.demo.exceptions.DataPipelineException;

public interface DataPipeline {

   public void run(String... args) throws DataPipelineException;
}
