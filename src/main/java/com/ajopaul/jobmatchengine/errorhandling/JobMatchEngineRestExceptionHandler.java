package com.ajopaul.jobmatchengine.errorhandling;

import com.ajopaul.jobmatchengine.ResponseData;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

/**
 * To catch the custom exceptions had throw appropriate return JSON
 *
 */
@ControllerAdvice
public class JobMatchEngineRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<?> handleMethodArgumentTypeMismatch( MethodArgumentTypeMismatchException e,  WebRequest request) {
         String error = e.getName() +
                 " should be of type " +
                 Optional.ofNullable(e.getRequiredType()).map(Class::getSimpleName).orElse("");

        return new ResponseEntity<>(ResponseData.error(HttpStatus.BAD_REQUEST,error)
                , new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException( NoHandlerFoundException e,  HttpHeaders headers
                                                            ,  HttpStatus status,  WebRequest request) {
        
         String error = "Invalid URL " + e.getHttpMethod() + " " + e.getRequestURL();
        return new ResponseEntity<>(ResponseData.error(HttpStatus.NOT_FOUND,error)
                , new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported( HttpRequestMethodNotSupportedException e
                                        ,  HttpHeaders headers,  HttpStatus status,  WebRequest request) {
        
        StringBuilder builder = new StringBuilder();
        builder.append(e.getMethod());
        builder.append(" method Not supported. Supported methods are ");
        Optional.ofNullable(e.getSupportedHttpMethods()).map(t -> builder.append(t).append(" "));

        return new ResponseEntity<>(ResponseData.error(HttpStatus.METHOD_NOT_ALLOWED,builder.toString())
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
        return new ResponseEntity<>(ResponseData.error(status,errMsg), new HttpHeaders(), status);
    }

}
