package com.ryx.internet.pojo;

import com.ryx.credit.pojo.admin.vo.OCashReceivablesVo;

import java.io.Serializable;
import java.util.List;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/7/3 10:36
 * @Param
 * @return
 **/
public class InternetCardVo implements Serializable{

    private OInternetRenew internetRenew;
    private List<OCashReceivablesVo> oCashReceivablesVoList;

    public OInternetRenew getInternetRenew() {
        return internetRenew;
    }

    public void setInternetRenew(OInternetRenew internetRenew) {
        this.internetRenew = internetRenew;
    }

    public List<OCashReceivablesVo> getoCashReceivablesVoList() {
        return oCashReceivablesVoList;
    }

    public void setoCashReceivablesVoList(List<OCashReceivablesVo> oCashReceivablesVoList) {
        this.oCashReceivablesVoList = oCashReceivablesVoList;
    }
}
