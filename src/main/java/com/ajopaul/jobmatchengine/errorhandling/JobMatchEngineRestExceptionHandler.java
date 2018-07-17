package com.ajopaul.jobmatchengine.errorhandling;

import com.ajopaul.jobmatchengine.ResponseData;
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
    public ResponseEntity<?> handleMethodArgumentTypeMismatch( MethodArgumentTypeMismatchException e,  WebRequest request) {
         String error = e.getName() + " should be of type " + e.getRequiredType().getSimpleName();

        return new ResponseEntity<>(new ResponseData(HttpStatus.BAD_REQUEST,error)
                , new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException( NoHandlerFoundException e,  HttpHeaders headers,  HttpStatus status,  WebRequest request) {
        
         String error = "Invalid URL " + e.getHttpMethod() + " " + e.getRequestURL();
        return new ResponseEntity<>(new ResponseData(HttpStatus.NOT_FOUND,error)
                , new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported( HttpRequestMethodNotSupportedException e,  HttpHeaders headers,  HttpStatus status,  WebRequest request) {
        
        StringBuilder builder = new StringBuilder();
        builder.append(e.getMethod());
        builder.append(" method Not supported. Supported methods are ");
        e.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

        return new ResponseEntity<>(new ResponseData(HttpStatus.METHOD_NOT_ALLOWED,builder.toString())
                , new HttpHeaders(),HttpStatus.METHOD_NOT_ALLOWED);
    }


    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll( Exception e,  WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String errMsg = e.getMessage();
        if(e instanceof JobMatchException){
            switch (((JobMatchException)e).code){
                case REQUEST_ERROR:status = HttpStatus.OK;
                                    break;
                case DEP_ERROR:status = HttpStatus.SERVICE_UNAVAILABLE;
                                    break;
                case WORKER_INACTIVE: status = HttpStatus.OK;
                                    break;
            }
        }
        return new ResponseEntity<>(new ResponseData(status,errMsg), new HttpHeaders(), status);
    }

}
