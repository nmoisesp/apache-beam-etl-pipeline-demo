package com.demo.exceptions.handler;

import com.demo.exceptions.DataPipelineException;
import com.demo.exceptions.GlobalApplicationException;
import com.demo.exceptions.HttpRestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DataPipelineException.class)
    ProblemDetail handleDataPipelineException(DataPipelineException e) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        detail.setTitle("Data pipeline execution error");
        LOG.error(e.getMessage(), e);
        return detail;
    }

    @ExceptionHandler(GlobalApplicationException.class)
    ProblemDetail handleMoviesAwardsAppException(GlobalApplicationException e) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        detail.setTitle("Runtime application error");
        LOG.error(e.getMessage(), e);
        return detail;
    }

    @ExceptionHandler(HttpRestException.class)
    ProblemDetail handleHttpRestException(HttpRestException e) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        detail.setTitle("Rest API error");
        LOG.error(e.getMessage(), e);
        return detail;
    }
}