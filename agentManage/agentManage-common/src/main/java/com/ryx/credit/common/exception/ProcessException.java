package com.ryx.credit.common.exception;

/**
 * Created by cx on 2018/5/22.
 */
public class ProcessException extends RuntimeException {

    private String code = "1000";

    public ProcessException(String code,String message){
       super(message);
        this.code = code;
    }

    public ProcessException(String message){
        super(message);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
