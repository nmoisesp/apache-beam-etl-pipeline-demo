package com.demo.exceptions;

public class DataPipelineException extends Exception {

    public DataPipelineException(String message) {
        super("Something went wrong during the apache beam pipeline execution: " + message);
    }
}
