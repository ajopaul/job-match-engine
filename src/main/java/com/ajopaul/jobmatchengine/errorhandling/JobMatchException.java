package com.ajopaul.jobmatchengine.errorhandling;

/**
 * Created by ajopaul on 6/7/18.
 */
public class JobMatchException extends RuntimeException {

    public ERROR_CODE code;
    public JobMatchException(ERROR_CODE code,String message){
        super(message);
        this.code = code;
    }

    public enum ERROR_CODE{
        REQUEST_ERROR,
        DEP_ERROR,
    }
}
