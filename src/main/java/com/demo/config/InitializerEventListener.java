package com.demo.config;

import com.demo.exceptions.DataPipelineException;
import com.demo.service.DataPipelineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InitializerEventListener {

    @Autowired
    private DataPipelineService dataPipeline;

    @EventListener(ApplicationReadyEvent.class)
    public void init() throws DataPipelineException {
        try {
            dataPipeline.run();
        } catch (Exception e) {
            throw new DataPipelineException(e.getMessage(), e);
        }
    }
}
