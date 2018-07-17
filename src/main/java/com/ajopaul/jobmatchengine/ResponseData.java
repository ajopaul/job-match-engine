package com.ajopaul.jobmatchengine;

import org.springframework.http.HttpStatus;

/**
 * Created by ajopaul on 17/7/18.
 */
public class ResponseData {

    public HttpStatus status;
    public String errorMessage;
    public Object data;

    public ResponseData(Object data) {
        this.data = data;
        this.status = HttpStatus.OK;
    }

    public ResponseData(HttpStatus status,String errorMessage){
        this.status = status;
        this.errorMessage = errorMessage;
    }
}
