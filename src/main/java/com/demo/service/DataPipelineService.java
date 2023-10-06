package com.demo.service;

import com.demo.pipeline.DataPipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataPipelineService {

    @Autowired
    private DataPipeline dataPipeline;

    public void run() throws Exception {
        dataPipeline.run();
    }
}
