package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.OLogisticsDetailStatus;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.dao.order.OLogisticsDetailMapper;
import com.ryx.credit.dao.order.OLogisticsDetailHMapper;
import com.ryx.credit.pojo.admin.order.OLogisticsDetail;
import com.ryx.credit.pojo.admin.order.OLogisticsDetailExample;
import com.ryx.credit.pojo.admin.order.OLogisticsDetailH;
import com.ryx.credit.pojo.admin.order.OLogisticsDetailHExample;
import com.ryx.credit.service.order.OLogisticsDetailHService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lhl on 2019/4/24.
 * 物流明细的sn明细订单历史数据进行清理，移动到历史表
 */
@Service("oLogisticsDetailHService")
public class OLogisticsDetailHServiceImpl implements OLogisticsDetailHService {

    private static Logger logger = LoggerFactory.getLogger(OLogisticsDetailHServiceImpl.class);
    @Autowired
    private OLogisticsDetailMapper oLogisticsDetailMapper;
    @Autowired
    private OLogisticsDetailHMapper oLogisticsDetailHMapper;
    @Autowired
    private OLogisticsDetailHService oLogisticsDetailHService;

    @Override
    public AgentResult transferHistoryData() {
        List<OLogisticsDetail> oLogisticsDetailList = oLogisticsDetailMapper.queryHistoryOrder();
        for (OLogisticsDetail detail : oLogisticsDetailList) {
            try {
                //数据转移
                AgentResult agentResult =  oLogisticsDetailHService.clearLogisticsDetailOrder(detail);
                logger.info("历史迁移结果:{}",agentResult.getMsg());
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("历史迁移异常："+detail.getId(),e);
            }
        }
        return AgentResult.ok();
    }




    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRES_NEW)
    @Override
    public AgentResult clearLogisticsDetailOrder(OLogisticsDetail detail)throws Exception {
        OLogisticsDetailH oLogDetailH = new OLogisticsDetailH();
        oLogDetailH.setId(detail.getId());
        oLogDetailH.setOrderId(detail.getOrderId());
        oLogDetailH.setLogisticsId(detail.getLogisticsId());
        oLogDetailH.setProId(detail.getOrderNum());
        oLogDetailH.setOrderNum(detail.getProId());
        oLogDetailH.setProName(detail.getProName());
        oLogDetailH.setSettlementPrice(detail.getSettlementPrice());
        oLogDetailH.setActivityId(detail.getActivityId());
        oLogDetailH.setActivityName(detail.getActivityName());
        oLogDetailH.setgTime(detail.getgTime());
        oLogDetailH.setSnNum(detail.getSnNum());
        oLogDetailH.setAgentId(detail.getAgentId());
        oLogDetailH.setOptId(detail.getOptId());
        oLogDetailH.setOptType(detail.getOptType());
        oLogDetailH.setReturnOrderId(detail.getReturnOrderId());
        oLogDetailH.setcTime(detail.getcTime());
        oLogDetailH.setuTime(detail.getuTime());
        oLogDetailH.setcUser(detail.getcUser());
        oLogDetailH.setuUser(detail.getuUser());
        oLogDetailH.setStatus(detail.getStatus());
        oLogDetailH.setRecordStatus(detail.getRecordStatus());
        oLogDetailH.setTerminalid(detail.getTerminalid());
        oLogDetailH.setTerminalidKey(detail.getTerminalidKey());
        oLogDetailH.setTerminalidCheck(detail.getTerminalidCheck());
        oLogDetailH.setTerminalidSeq(detail.getTerminalidSeq());
        oLogDetailH.setTerminalidType(detail.getTerminalidType());
        oLogDetailH.setVersion(detail.getVersion());
        oLogDetailH.setBusProCode(detail.getBusProCode());
        oLogDetailH.setBusProName(detail.getBusProName());
        oLogDetailH.setTermBatchcode(detail.getTermBatchcode());
        oLogDetailH.setTermBatchname(detail.getTermBatchname());
        oLogDetailH.setTermtype(detail.getTermtype());
        oLogDetailH.setTermtypename(detail.getTermtypename());
        oLogDetailH.setPosType(detail.getPosType());
        oLogDetailH.setPosSpePrice(detail.getPosSpePrice());
        oLogDetailH.setStandTime(detail.getStandTime());
        oLogDetailH.setBusId(detail.getBusId());

        OLogisticsDetailHExample historyExample = new OLogisticsDetailHExample();
        historyExample.or().andIdEqualTo(oLogDetailH.getId());
        List<OLogisticsDetailH> oLogDetailHistoryList = oLogisticsDetailHMapper.selectByExample(historyExample);
        // size==0&&list为空 则迁移历史数据
        if (oLogDetailHistoryList.size() == 0 && oLogDetailHistoryList.isEmpty()) {
            if(1 != oLogisticsDetailHMapper.insertSelective(oLogDetailH)) {
                logger.info("数据保存失败，{}", oLogDetailH.getId());
            }else{
                OLogisticsDetailExample example = new OLogisticsDetailExample();
                example.or().andIdEqualTo(detail.getId());
                if(1!=oLogisticsDetailMapper.deleteByExample(example)){
                  throw new MessageException("物流明细迁移异常，删除历史数据失败:"+oLogDetailH.getId());
                }
            }
            return AgentResult.ok("移除成功:"+oLogDetailH.getId());
        }else{
            return AgentResult.fail("历史已存在"+oLogDetailH.getId());
        }
    }

}
