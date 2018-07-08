package com.ajopaul.jobmatchengine.errorhandling;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

/**
 * Error Message constructed as JSON
 */
public class ErrorMessage {

    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ErrorMessage() {
        super();
    }

    public ErrorMessage(final HttpStatus status, final String message, final List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ErrorMessage(final HttpStatus status, final String message, final String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }


    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(final HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(final List<String> errors) {
        this.errors = errors;
    }

    public void setError(final String error) {
        errors = Arrays.asList(error);
    }

}
