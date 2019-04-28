package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.OLogisticsDetailStatus;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.dao.order.OLogisticsDetailMapper;
import com.ryx.credit.dao.order.OLogisticsDetailHMapper;
import com.ryx.credit.pojo.admin.order.OLogisticsDetail;
import com.ryx.credit.pojo.admin.order.OLogisticsDetailH;
import com.ryx.credit.pojo.admin.order.OLogisticsDetailHExample;
import com.ryx.credit.service.order.OLogisticsDetailHService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public AgentResult transferHistoryData() {
        // 调用明细查询 迁移数据
        queryLogisticsHistoryOrder();

        // 调用物流明细更新记录状态
        AgentResult result = clearLogisticsDetailOrder();
        if (result.isOK()) {
            logger.info("数据更新成功~", result);
        }
        return AgentResult.ok();
    }

    @Override
    public AgentResult queryLogisticsHistoryOrder() {
        List<OLogisticsDetailH> history = new ArrayList();
        List<OLogisticsDetail> oLogisticsDetailList = oLogisticsDetailMapper.queryHistoryOrder();
        for (OLogisticsDetail detail : oLogisticsDetailList) {
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

            history.add(oLogDetailH);
        }
        for(int i=0; i<history.size(); i++) {
            OLogisticsDetailH oLogisticsDetailH = history.get(i);
            // 查询数据是否已迁移到历史明细表中
            OLogisticsDetailHExample historyExample = new OLogisticsDetailHExample();
            historyExample.or().andIdEqualTo(oLogisticsDetailH.getId());
            List<OLogisticsDetailH> oLogDetailHistoryList = oLogisticsDetailHMapper.selectByExample(historyExample);
            if (oLogDetailHistoryList.size() == 0 && oLogDetailHistoryList.isEmpty()) {
                // size==0&&list为空 则迁移历史数据
                int insertHistory = oLogisticsDetailHMapper.insertSelective(oLogisticsDetailH);
                if(1 != insertHistory) {
                    AgentResult.fail("数据保存失败~");
                    logger.info("数据保存失败~", history, insertHistory);
                }
            }
        }
        return AgentResult.ok();
    }


    @Override
    public AgentResult clearLogisticsDetailOrder() {
        AgentResult result = queryLogisticsHistoryOrder();
        if (result.isOK()) {
            // 数据迁移成功 更新记录状态
            List<OLogisticsDetail> oLogisticsDetailList = oLogisticsDetailMapper.queryHistoryOrder();
            for (OLogisticsDetail oLogisticsDetail : oLogisticsDetailList) {
                oLogisticsDetail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_DEL.code);
                int updateDetail = oLogisticsDetailMapper.updateByPrimaryKeySelective(oLogisticsDetail);
                if (1 != updateDetail) {
                    AgentResult.fail("数据状态更新失败~");
                    logger.info("数据更新失败~", oLogisticsDetailList, updateDetail);
                }
            }
        }
        return AgentResult.ok();
    }

}
