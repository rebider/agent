package com.ryx.credit.common.exception;

import com.ryx.credit.common.util.MailUtil;

/**
 * Created by RYX on 2018/8/3.
 */
public class MessageException  extends Exception{
    private String code = "1000";

    private String msg = "失败";

    public MessageException(String code,String message){
        super(message);
        this.code = code;
        this.msg = message;
    }

    public MessageException(String message){
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
