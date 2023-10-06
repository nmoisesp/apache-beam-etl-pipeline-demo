package com.demo.exceptions;

public class HttpRestException extends Exception {

    public HttpRestException(String message) {
        super("Something went wrong during the apache beam pipeline execution: " + message);
    }
}
