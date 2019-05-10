package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.LogType;
import com.ryx.credit.common.enumc.LogisticsSendStatus;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.MailUtil;
import com.ryx.credit.dao.CUserMapper;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.PlatFormMapper;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.machine.service.ImsTermWarehouseDetailService;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.pojo.admin.order.OLogistics;
import com.ryx.credit.pojo.admin.order.OLogisticsDetail;
import com.ryx.credit.pojo.admin.order.OLogisticsExample;
import com.ryx.credit.pojo.admin.order.ReceiptPlan;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.OLogisticsService;
import com.ryx.credit.service.order.OsnOperateService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 作者：cx
 * 时间：2019/5/9
 * 描述：
 */
//@Service("osnOperateReturnService")
public class OsnOperateReturnServiceImpl extends OsnOperateServiceImpl {

    private static Logger logger = LoggerFactory.getLogger(OsnOperateReturnServiceImpl.class);



    @Autowired
    private OLogisticsMapper oLogisticsMapper;
    @Autowired
    private OLogisticsDetailMapper oLogisticsDetailMapper;
    @Autowired
    private ReceiptPlanMapper receiptPlanMapper;
    @Autowired
    private OReceiptProMapper oReceiptProMapper;
    @Autowired
    private OSubOrderActivityMapper oSubOrderActivityMapper;
    @Autowired
    private OSubOrderMapper oSubOrderMapper;
    @Autowired
    private OOrderMapper oOrderMapper;
    @Autowired
    private OReturnOrderDetailMapper oReturnOrderDetailMapper;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private PlatFormMapper platFormMapper;
    @Autowired
    private OActivityMapper oActivityMapper;
    @Autowired
    private OLogisticsService oLogisticsService;
    @Autowired
    private ImsTermWarehouseDetailService imsTermWarehouseDetailService;
    @Autowired
    private TermMachineService termMachineService;
    @Autowired
    private CUserMapper cUserMapper;
    @Autowired
    private IdService idService;
    @Resource(name = "osnOperateReturnService")
    private OsnOperateService osnOperateService;
    /**
     * 查询物流
     * @param logType
     * @param sendStatus
     * @return
     */
    @Override
    public List<String> queryLogicInfoIdByStatus(LogType logType, LogisticsSendStatus sendStatus) {
        return super.queryLogicInfoIdByStatus(logType,sendStatus);
    }

    @Override
    public void genLogicDetailTask() {
        //查询发货数量大于 count_wall的物流id
        List<String>  list = queryLogicInfoIdByStatus(LogType.Refund,LogisticsSendStatus.none_send);
        if(list.size()>0) {
            logger.info("退货物流处理 开始执行sn明细生成任务");
            for (String id : list) {
                //根据id
                OLogisticsExample example = new OLogisticsExample();
                example.or().andSendStatusEqualTo(LogisticsSendStatus.none_send.code).andIdEqualTo(id);
                List<OLogistics> logistics_list = oLogisticsMapper.selectByExample(example);
                OLogistics logistics = null;
                if(logistics_list.size()>0){
                    logistics = logistics_list.get(0);
                }
                //info 此处禁止处理退货发货
                ReceiptPlan receiptPlan = receiptPlanMapper.selectByPrimaryKey(logistics.getReceiptPlanId());
                if(logistics!=null && StringUtils.isNotEmpty(receiptPlan.getReturnOrderDetailId())) {
                    logger.info("退货物流处理 更新物流状态为生成明细中:{},{},{}",logistics.getId(),logistics.getSnBeginNum(),logistics.getSnEndNum());
                    logistics.setSendStatus(LogisticsSendStatus.gen_detail_ing.code);
                    if (1 == oLogisticsMapper.updateByPrimaryKeySelective(logistics)) {
                        try {
                            logger.info("退货物流处理 处理物流生成明细:{},{},{}",logistics.getId(),logistics.getSnBeginNum(),logistics.getSnEndNum());
                            if (osnOperateService.genOlogicDetailInfo(id)) {
                                logistics = oLogisticsMapper.selectByPrimaryKey(id);
                                logistics.setSendStatus(LogisticsSendStatus.gen_detail_sucess.code);
                                if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                                    logger.info("退货物流处理 物流生成明细成功，更新数据库失败:{},{},{}",logistics.getId(),logistics.getSnBeginNum(),logistics.getSnEndNum());
                                }else{
                                    logger.info("退货物流处理 物流生成明细成功，更新数据库成功:{},{},{}",logistics.getId(),logistics.getSnBeginNum(),logistics.getSnEndNum());
                                }
                            } else {
                                logistics = oLogisticsMapper.selectByPrimaryKey(id);
                                logistics.setSendStatus(LogisticsSendStatus.gen_detail_fail.code);
                                if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                                    logger.info("退货物流处理 物流生成明细失败，更新数据库失败:{},{},{}",logistics.getId(),logistics.getSnBeginNum(),logistics.getSnEndNum());
                                }else{
                                    logger.info("退货物流处理 物流生成明细失败，更新数据库成功:{},{},{}",logistics.getId(),logistics.getSnBeginNum(),logistics.getSnEndNum());
                                }
                            }
                        } catch (MessageException e) {
                            logger.error("生成物流明细任务异常：", e);
                            logistics = oLogisticsMapper.selectByPrimaryKey(id);
                            logistics.setSendStatus(LogisticsSendStatus.gen_detail_fail.code);
                            logistics.setSendMsg(e.getMsg());
                            if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                                logger.info("退货物流处理 物流生成明细异常，更新数据库失败:{},{},{},{}",logistics.getId(),logistics.getSnBeginNum(),logistics.getSnEndNum(),e.getLocalizedMessage());
                            }else{
                                logger.info("退货物流处理 物流生成明细异常，更新数据库成功:{},{},{},{}",logistics.getId(),logistics.getSnBeginNum(),logistics.getSnEndNum(),e.getLocalizedMessage());
                            }
                            e.printStackTrace();
                            AppConfig.sendEmails("logisticId:"+id+"错误信息:"+ MailUtil.printStackTrace(e), "任务生成物流明细错误报警OsnOperateServiceImpl");
                        } catch (Exception e) {
                            logger.error("生成物流明细任务异常：", e);
                            logistics = oLogisticsMapper.selectByPrimaryKey(id);
                            logistics.setSendStatus(LogisticsSendStatus.gen_detail_fail.code);
                            logistics.setSendMsg(e.getLocalizedMessage().length() > 30 ? e.getLocalizedMessage().substring(0, 30) : e.getLocalizedMessage());
                            if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                                logger.info("退货物流处理 物流生成明细异常，更新数据库失败:{},{},{},{}",logistics.getId(),logistics.getSnBeginNum(),logistics.getSnEndNum(),e.getLocalizedMessage());
                            }else{
                                logger.info("退货物流处理 物流生成明细异常，更新数据库成功:{},{},{},{}",logistics.getId(),logistics.getSnBeginNum(),logistics.getSnEndNum(),e.getLocalizedMessage());
                            }
                            e.printStackTrace();
                            AppConfig.sendEmails("logisticId:"+id+"错误信息:"+MailUtil.printStackTrace(e), "任务生成物流明细错误报警OsnOperateServiceImpl");
                        }
                    }
                }

            }
            logger.info("开始执行sn明细生成结束");
        }
    }

    @Override
    public List<String> fetchFhData(int nodecount, int shardingItem) throws Exception {
        return null;
    }

    @Override
    public boolean processData(List<String> ids) {
        return false;
    }

    @Override
    public List<OLogisticsDetail> updateDetailBatch(List<OLogisticsDetail> datas, BigDecimal batch) throws Exception {
        return null;
    }

    @Override
    public boolean genOlogicDetailInfo(String logcId) throws Exception {
        return true;
    }

    @Override
    public boolean sendInfoToBusinessSystem(List<OLogisticsDetail> datas, String logcId, BigDecimal batch) throws Exception {
        return false;
    }
}
