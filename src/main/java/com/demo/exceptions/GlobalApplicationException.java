package com.demo.exceptions;

public class GlobalApplicationException extends Exception {

    public GlobalApplicationException(String message, Exception e) {
        super("Something went wrong during the application runtime: " + message, e);
    }
}