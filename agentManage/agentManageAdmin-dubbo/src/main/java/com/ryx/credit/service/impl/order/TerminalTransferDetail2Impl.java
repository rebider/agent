package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.dao.order.TerminalTransferDetailMapper;
import com.ryx.credit.pojo.admin.order.TerminalTransferDetail;
import com.ryx.credit.service.order.TerminalTransferDetail2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service("terminalTransferDetail2")
public class TerminalTransferDetail2Impl implements TerminalTransferDetail2 {

    @Autowired
    private TerminalTransferDetailMapper terminalTransferDetailMapper;

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void  updateIsNoPay(List<TerminalTransferDetail> terminalTransferDetails, List<String> detailIds,String userId) throws MessageException {


        if(detailIds==null||detailIds.size()==0){
            for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetails) {
                if ("".equals(terminalTransferDetail.getButtJointPerson()) || null == terminalTransferDetail.getButtJointPerson()) {
                    terminalTransferDetail.setButtJointPerson(userId);
                }
                if(terminalTransferDetail.getPlatformType().compareTo(new BigDecimal(1))==0){
                    terminalTransferDetail.setIsNoPay("0");

                }
                int i = terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                if (i != 1) {
                    throw new MessageException("更新是否支付失败");
                }

            }
        }else{
            for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetails) {
                if(terminalTransferDetail.getPlatformType().compareTo(new BigDecimal(1))==0){
                    for (String str :detailIds) {
                        if(terminalTransferDetail.getId().equals(str)){
                            terminalTransferDetail.setIsNoPay("1");
                            break;
                        }else{
                            terminalTransferDetail.setIsNoPay("0");
                        }

                    }
                    int i = terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                    if (i != 1) {
                        throw new MessageException("更新是否支付失败");
                    }
                }else{
                    continue;
                }

            }
        }



    }

}
