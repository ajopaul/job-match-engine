package com.ajopaul.jobmatchengine.errorhandling;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * To catch the custom exceptions had throw appropriate return JSON
 *
 */
@ControllerAdvice
public class JobMatchEngineRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch( MethodArgumentTypeMismatchException e,  WebRequest request) {
         String error = e.getName() + " should be of type " + e.getRequiredType().getName();

         ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage(), error);
        return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException( NoHandlerFoundException e,  HttpHeaders headers,  HttpStatus status,  WebRequest request) {
        
         String error = "Invalid URL " + e.getHttpMethod() + " " + e.getRequestURL();

         ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, e.getMessage(), error);
        return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported( HttpRequestMethodNotSupportedException e,  HttpHeaders headers,  HttpStatus status,  WebRequest request) {
        
        StringBuilder builder = new StringBuilder();
        builder.append(e.getMethod());
        builder.append(" method Not supported. Supported methods are ");
        e.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage(), builder.toString());
        return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }


    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll( Exception e,  WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if(e instanceof JobMatchException){
            switch (((JobMatchException)e).code){
                case REQUEST_ERROR:status = HttpStatus.BAD_REQUEST;
                                    break;
                case DEP_ERROR: status = HttpStatus.SERVICE_UNAVAILABLE;
                                break;
            }
        }
        ErrorMessage errorMessage = new ErrorMessage(status, e.getMessage(), "error occurred");
        return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }

}
