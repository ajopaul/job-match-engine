package com.ajopaul.jobmatchengine;

import org.springframework.http.HttpStatus;

/**
 * Created by ajopaul on 17/7/18.
 */
public class ResponseData {

    public HttpStatus status;
    public String errorMessage;
    public Object data;

    private ResponseData(Object data) {
        this.data = data;
        this.status = HttpStatus.OK;
    }

    private ResponseData(HttpStatus status,String errorMessage){
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public static ResponseData success(Object data){
        return new ResponseData(data);
    }

    public static ResponseData error(HttpStatus status, String errorMessage){
        return new ResponseData(status, errorMessage);
    }
}
