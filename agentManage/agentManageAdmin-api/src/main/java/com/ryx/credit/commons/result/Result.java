package com.ryx.credit.commons.result;

import java.io.Serializable;

/**
 * @description：操作结果集
 * @author：wangyong
 * @date：2017/8/27 14:51
 */
public class Result implements Serializable {

    public static final int SUCCESS = 1;
    public static final int FAILURE = -1;

    private static final long serialVersionUID = 5576237395711742681L;

    private boolean success = false;

    private String msg = "";

    /**
     * 100:长度异常
     * 200:正常
     * 300:缺失要素
     * 400:格式错误
     * 500:违反唯一约束
     */
    private String code = "";
    
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private Object obj = null;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

}
