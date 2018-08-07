package com.ryx.credit.profit.exceptions;

/**
 * @author zhaodw
 * @Title: StagingException
 * @ProjectName agentManage
 * @Description: 扣款异常处理
 * @date 2018/7/2514:42
 */
public class DeductionException extends RuntimeException {
    private static final long serialVersionUID = 8349687090501314585L;

    public DeductionException(String message) {
        super(message);
    }
}
