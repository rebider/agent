package com.ryx.credit.common.exception;

import com.ryx.credit.common.util.MailUtil;

/**
 * Created by cx on 2018/5/22.
 */
public class ProcessException extends RuntimeException{

    private String code = "1000";

    private String msg = "失败";

    public ProcessException(String code,String message){
       super(message);
        this.code = code;
        this.msg = message;
    }

    public ProcessException(String message){
        super(message);
        this.msg = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStackTraceString() {
        return MailUtil.printStackTrace(this);
    }
}
