package com.ryx.internet.service.impl;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.AttachmentRel;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.vo.OCashReceivablesVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.AttachmentRelService;
import com.ryx.credit.service.agent.TaskApprovalService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.OCashReceivablesService;
import com.ryx.internet.dao.OInternetCardMapper;
import com.ryx.internet.dao.OInternetRenewDetailMapper;
import com.ryx.internet.dao.OInternetRenewMapper;
import com.ryx.internet.pojo.OInternetCard;
import com.ryx.internet.pojo.OInternetRenew;
import com.ryx.internet.pojo.OInternetRenewDetail;
import com.ryx.internet.service.OInternetRenewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/***
 * 物联网卡续费
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/7/1 19:14
 * @Param
 * @return
 **/
@Service("internetRenewService")
public class OInternetRenewServiceImpl implements OInternetRenewService {

    private static Logger log = LoggerFactory.getLogger(OInternetRenewServiceImpl.class);

    @Autowired
    private OInternetRenewMapper internetRenewMapper;
    @Autowired
    private OInternetCardMapper internetCardMapper;
    @Autowired
    private OInternetRenewDetailMapper internetRenewDetailMapper;
    @Autowired
    private OCashReceivablesService cashReceivablesService;
    @Autowired
    private IdService idService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private AttachmentRelService attachmentRelService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public AgentResult saveAndApprove(OInternetRenew internetRenew,List<String> iccids, String cUser,
                                      List<OCashReceivablesVo> oCashReceivablesVoList)throws MessageException{

        if(StringUtils.isBlank(InternetRenewWay.getContentByValue(internetRenew.getRenewWay()))){
            throw new MessageException("续费方式错误");
        }
        if(null==internetRenew.getRenewCardCount()){
            throw new MessageException("请填写卡数量");
        }
        String internetRenewId = idService.genId(TabId.O_INTERNET_RENEW);
        internetRenew.setId(internetRenewId);
        internetRenew.setReviewStatus(AgStatus.Approving.status);
        internetRenew.setcTime(new Date());
        internetRenew.setuTime(new Date());
        internetRenew.setcUser(cUser);
        internetRenew.setuUser(cUser);
        internetRenew.setStatus(Status.STATUS_1.status);
        internetRenew.setVersion(BigDecimal.ONE);
        Dict cardAmt = dictOptionsService.findDictByName(DictGroup.ORDER.name(), DictGroup.INTERNET_RENEW.name(), DictGroup.CARD_AMT.name());
        if(cardAmt==null){
            throw new MessageException("缺少参数配置");
        }
        internetRenew.setSuppAmt(internetRenew.getRenewCardCount().multiply(new BigDecimal(cardAmt.getdItemvalue())));

        Dict offsetAmt = dictOptionsService.findDictByName(DictGroup.ORDER.name(), DictGroup.INTERNET_RENEW.name(), DictGroup.OFFSET_AMT.name());
        //没有轧差直接设置为0
        if(internetRenew.getRenewWay().equals(InternetRenewWay.FRDK.getValue()) || internetRenew.getRenewWay().equals(InternetRenewWay.XXBK.getValue())){
            internetRenew.setSumOffsetAmt(BigDecimal.ZERO);
        }else{
            if(offsetAmt==null){
                throw new MessageException("缺少参数配置");
            }
            internetRenew.setSumOffsetAmt(internetRenew.getRenewCardCount().multiply(new BigDecimal(offsetAmt.getdItemvalue())));
        }
        internetRenewMapper.insert(internetRenew);

        //添加新的附件
        if (StringUtils.isNotBlank(internetRenew.getFiles())) {
            String[] files = internetRenew.getFiles().split(",");
            for (int i=0;i<files.length;i++){
                AttachmentRel record = new AttachmentRel();
                record.setAttId(files[i]);
                record.setSrcId(internetRenewId);
                record.setcUser(cUser);
                record.setcTime(Calendar.getInstance().getTime());
                record.setStatus(Status.STATUS_1.status);
                record.setBusType(AttachmentRelType.internetRenew.name());
                record.setId(idService.genId(TabId.a_attachment_rel));
                int j = attachmentRelService.insertSelective(record);
                if (1 != j) {
                    throw new ProcessException("保存附件失败");
                }
            }
        }

        int i = 1;
        for (String iccid : iccids) {
            OInternetCard oInternetCard = internetCardMapper.selectByPrimaryKey(iccid);
            if(oInternetCard==null){
                throw new MessageException("第"+i+"个iccid不存在");
            }
            OInternetRenewDetail oInternetRenewDetail = new OInternetRenewDetail();
            oInternetRenewDetail.setId(idService.genId((TabId.O_INTERNET_RENEW_DETAIL)));
            oInternetRenewDetail.setRenewId(internetRenewId);
            oInternetRenewDetail.setIccidNum(iccid);
            oInternetRenewDetail.setOrderId(oInternetCard.getOrderId());
            oInternetRenewDetail.setSnNum(oInternetCard.getSnNum());
            oInternetRenewDetail.setInternetCardNum(oInternetCard.getInternetCardNum());
            oInternetRenewDetail.setOpenAccountTime(oInternetCard.getOpenAccountTime());
            oInternetRenewDetail.setExpireTime(oInternetCard.getExpireTime());
            oInternetRenewDetail.setMerId(oInternetCard.getMerId());
            oInternetRenewDetail.setMerName(oInternetCard.getMerName());
            oInternetRenewDetail.setAgentId(oInternetCard.getAgentId());
            oInternetRenewDetail.setAgentName(oInternetCard.getAgentName());
            oInternetRenewDetail.setRenewWay(internetRenew.getRenewWay());
            oInternetRenewDetail.setOffsetAmt(new BigDecimal(offsetAmt.getdItemvalue()));
            oInternetRenewDetail.setRenewAmt(new BigDecimal(cardAmt.getdItemvalue()));
            oInternetRenewDetail.setOughtAmt(new BigDecimal(cardAmt.getdItemvalue()));
            //线下打款直接是实际扣款金额
            if(internetRenew.getRenewWay().equals(InternetRenewWay.XXBKGC.getValue()) || internetRenew.getRenewWay().equals(InternetRenewWay.XXBK.getValue())){
                oInternetRenewDetail.setRealityAmt(new BigDecimal(cardAmt.getdItemvalue()));
            }else{
                oInternetRenewDetail.setRealityAmt(BigDecimal.ZERO);
            }
            oInternetRenewDetail.setRenewStatus(InternetRenewStatus.WXF.getValue());
            oInternetRenewDetail.setStatus(Status.STATUS_1.status);
            oInternetRenewDetail.setcUser(cUser);
            oInternetRenewDetail.setuUser(cUser);
            oInternetRenewDetail.setcTime(new Date());
            oInternetRenewDetail.setuTime(new Date());
            oInternetRenewDetail.setVersion(BigDecimal.ONE);
            internetRenewDetailMapper.insert(oInternetRenewDetail);
        }

        String workId = dictOptionsService.getApproveVersion("cardRenew");
        //启动审批
        String proce = activityService.createDeloyFlow(null, workId, null, null,null);
        if (proce == null) {
            throw new MessageException("审批流启动失败!");
        }
        //代理商业务视频关系
        BusActRel record = new BusActRel();
        record.setBusId(internetRenewId);
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(cUser);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.COMPENSATE.name());
        record.setActivStatus(AgStatus.Approving.name());
        record.setDataShiro(BusActRelBusType.cardRenew.key);
        try {
            taskApprovalService.addABusActRel(record);
            log.info("物联网卡续费审批流启动成功");
        } catch (Exception e) {
            e.getStackTrace();
            log.error("物联网卡续费审批流启动失败{}");
            throw new MessageException("物联网卡续费审批流启动失败!:{}",e.getMessage());
        }
        try {
            AgentResult agentResult = cashReceivablesService.addOCashReceivables(oCashReceivablesVoList,cUser,null, CashPayType.INTERNETRENEW,internetRenewId);
            if(!agentResult.isOK()){
                log.info("退补差价保存打款记录失败1");
                throw new ProcessException("保存打款记录失败");
            }
        } catch (Exception e) {
            throw new MessageException(e.getMessage());
        }
        return AgentResult.ok();
    }
}
