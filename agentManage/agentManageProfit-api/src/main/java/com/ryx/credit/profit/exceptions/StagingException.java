package com.ryx.credit.profit.exceptions;

/**
 * @author zhaodw
 * @Title: StagingException
 * @ProjectName agentManage
 * @Description: 分期异常处理
 * @date 2018/7/2514:42
 */
public class StagingException extends RuntimeException {
    private static final long serialVersionUID = 8349687090501314585L;

    public StagingException(String message) {
        super(message);
    }
}
