package com.demo.exceptions;

public class DataPipelineException extends Exception {

    public DataPipelineException(String message, Exception e) {
        super("Something went wrong during the apache beam pipeline execution: " + message, e);
    }
}
