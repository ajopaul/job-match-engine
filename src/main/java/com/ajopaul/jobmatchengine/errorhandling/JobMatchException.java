package com.ajopaul.jobmatchengine.errorhandling;

/**
 * Created by ajopaul on 6/7/18.
 */
public class JobMatchException extends RuntimeException {

    public ERROR_CODE code;
    public String secondaryMessage;
    public JobMatchException(ERROR_CODE code,String message, String secondaryMessage){
        super(message);
        this.code = code;
        this.secondaryMessage = secondaryMessage;
    }

    public enum ERROR_CODE{
        REQUEST_ERROR,
        DEP_ERROR,
        WORKER_INACTIVE
    }
}
