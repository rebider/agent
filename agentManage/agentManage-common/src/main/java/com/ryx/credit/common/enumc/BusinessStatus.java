package com.ryx.credit.common.enumc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * 代理商状态
 * Created by cx on 2018/5/22.
 */
public enum BusinessStatus {

    pause(0,"注销"),
    Enabled(1,"启用"),
    inactive(2,"未激活"),
    lock(3,"锁定"),
    quit(4,"退出"),
    quitzq(5,"退出直签"),
    move(6,"业务已迁移");

    public BigDecimal status;

    public String  msg;

    BusinessStatus(int status,String s){
        this.status = new BigDecimal(status);
        msg = s;
    }

    public static List<BigDecimal> getNotUse(){
        return Arrays.asList(BusinessStatus.move.status,
                BusinessStatus.quit.status,
                BusinessStatus.quitzq.status,
                BusinessStatus.pause.status);
    }
}
