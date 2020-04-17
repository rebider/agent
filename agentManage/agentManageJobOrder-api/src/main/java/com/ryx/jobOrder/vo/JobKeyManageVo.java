package com.ryx.jobOrder.vo;


import com.ryx.jobOrder.pojo.JoCustomKey;
import com.ryx.jobOrder.pojo.JoKeyManage;

import java.util.List;

/**
 * @Auther: lrr
 * @Date: 2020/2/20 10:56
 * @Description:
 */
public class JobKeyManageVo extends JoKeyManage {
    private List<JoCustomKey> joCustomKeyList;

    public List<JoCustomKey> getJoCustomKeyList() {
        return joCustomKeyList;
    }

    public void setJoCustomKeyList(List<JoCustomKey> joCustomKeyList) {
        this.joCustomKeyList = joCustomKeyList;
    }
}