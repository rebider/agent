package com.ryx.internet.service.impl;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OCashReceivablesVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.service.data.AttachmentService;
import com.ryx.credit.service.dict.DepartmentService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.OCashReceivablesService;
import com.ryx.internet.dao.*;
import com.ryx.internet.pojo.*;
import com.ryx.internet.service.OInternetRenewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

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
    @Autowired
    private AgentService agentService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private OInternetRenewService internetRenewService;
    @Autowired
    private BusActRelService busActRelService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private InternetRenewOffsetMapper internetRenewOffsetMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private InternetRenewOffsetDetailMapper internetRenewOffsetDetailMapper;


    @Override
    public PageInfo internetRenewList(OInternetRenew internetRenew, Page page,String agentId,Long userId){

        OInternetRenewExample internetRenewExample = new OInternetRenewExample();
        OInternetRenewExample.Criteria criteria = internetRenewExample.createCriteria();
        if(StringUtils.isNotBlank(internetRenew.getId())){
            criteria.andIdEqualTo(internetRenew.getId());
        }
        if(StringUtils.isNotBlank(internetRenew.getRenewWay())){
            criteria.andRenewWayEqualTo(internetRenew.getRenewWay());
        }
        if(null!=internetRenew.getReviewStatus()){
            criteria.andReviewStatusEqualTo(internetRenew.getReviewStatus());
        }
        //代理商只查询自己的
        if(StringUtils.isNotBlank(agentId)){
            criteria.andAgentIdEqualTo(agentId);
        }else if(StringUtils.isNotBlank(internetRenew.getAgentId())){
            criteria.andAgentIdEqualTo(internetRenew.getAgentId());
        }
        //内部人员根据名称查询指定流量卡
        List<String> agentNameList = dictOptionsService.getAgentNameList(userId);
        if(agentNameList.size()!=0) {
            criteria.andAgentNameIn(agentNameList);
        }else if(StringUtils.isNotBlank(internetRenew.getAgentName())){
            criteria.andAgentNameLike("%"+internetRenew.getAgentName()+"%");
        }
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        internetRenewExample.setPage(page);
        internetRenewExample.setOrderByClause(" c_time desc");
        List<OInternetRenew> internetRenews = internetRenewMapper.selectByExample(internetRenewExample);
        for (OInternetRenew renew : internetRenews) {
            renew.setRenewWayName(InternetRenewWay.getContentByValue(renew.getRenewWay()));
            CUser cUser = iUserService.selectById(renew.getcUser());
            if(null!=cUser)
            renew.setcUser(cUser.getName());
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(internetRenews);
        pageInfo.setTotal((int)internetRenewMapper.countByExample(internetRenewExample));
        return pageInfo;
    }

    @Override
    public PageInfo internetRenewDetailList(OInternetRenewDetail internetRenewDetail, Page page,String agentId,Long userId){

        OInternetRenewDetailExample internetRenewDetailExample = queryParam(internetRenewDetail,agentId,userId);
        internetRenewDetailExample.setPage(page);
        List<OInternetRenewDetail> internetRenewDetails = internetRenewDetailMapper.selectByExample(internetRenewDetailExample);
        for (OInternetRenewDetail renewDetail : internetRenewDetails) {
            renewDetail.setRenewWayName(InternetRenewWay.getContentByValue(renewDetail.getRenewWay()));
            CUser cUser = iUserService.selectById(renewDetail.getcUser());
            if(null!=cUser)
            renewDetail.setcUser(cUser.getName());
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(internetRenewDetails);
        pageInfo.setTotal((int)internetRenewDetailMapper.countByExample(internetRenewDetailExample));
        return pageInfo;
    }

    @Override
    public List<OInternetRenewDetail> queryInternetRenewDetailList(OInternetRenewDetail internetRenewDetail, Page page,String agentId,Long userId){
        OInternetRenewDetailExample oInternetRenewDetailExample = queryParam(internetRenewDetail,agentId,userId);
        oInternetRenewDetailExample.setPage(page);
        List<OInternetRenewDetail> internetRenewDetailList = internetRenewDetailMapper.selectByExample(oInternetRenewDetailExample);
        return internetRenewDetailList;
    }

    @Override
    public Integer queryInternetRenewDetailCount(OInternetRenewDetail internetRenewDetail,String agentId,Long userId){
        OInternetRenewDetailExample oInternetRenewDetailExample = queryParam(internetRenewDetail,agentId,userId);
        Integer count = Integer.valueOf((int)internetRenewDetailMapper.countByExample(oInternetRenewDetailExample));
        return count;
    }

    /**
     * 查询和导出的条件
     * @param internetRenewDetail
     * @return
     */
    private OInternetRenewDetailExample queryParam(OInternetRenewDetail internetRenewDetail,String agentId,Long userId){

        OInternetRenewDetailExample internetRenewDetailExample = new OInternetRenewDetailExample();
        OInternetRenewDetailExample.Criteria criteria = internetRenewDetailExample.createCriteria();
        //如果代理商登陆执行此查询条件
        if(StringUtils.isNotBlank(agentId)){
            criteria.andAgentIdEqualTo(agentId);
        }else if(StringUtils.isNotBlank(internetRenewDetail.getAgentId())){
            criteria.andAgentIdEqualTo(internetRenewDetail.getAgentId());
        }
        //内部人员根据名称查询指定流量卡
        List<String> agentNameList = dictOptionsService.getAgentNameList(userId);
        if(agentNameList.size()!=0) {
            criteria.andAgentNameIn(agentNameList);
        }else if(StringUtils.isNotBlank(internetRenewDetail.getAgentName())){
            criteria.andAgentNameLike("%"+internetRenewDetail.getAgentName()+"%");
        }
        if(StringUtils.isNotBlank(internetRenewDetail.getIccidNum())){
            criteria.andIccidNumEqualTo(internetRenewDetail.getIccidNum());
        }
        if(StringUtils.isNotBlank(internetRenewDetail.getRenewId())){
            criteria.andRenewIdEqualTo(internetRenewDetail.getRenewId());
        }
        if(StringUtils.isNotBlank(internetRenewDetail.getId())){
            criteria.andIdEqualTo(internetRenewDetail.getId());
        }
        if(StringUtils.isNotBlank(internetRenewDetail.getSnNum())){
            criteria.andSnNumEqualTo(internetRenewDetail.getSnNum());
        }
        if(StringUtils.isNotBlank(internetRenewDetail.getRenewWay())){
            criteria.andRenewWayEqualTo(internetRenewDetail.getRenewWay());
        }
        if(StringUtils.isNotBlank(internetRenewDetail.getRenewStatus())){
            criteria.andRenewStatusEqualTo(internetRenewDetail.getRenewStatus());
        }
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        internetRenewDetailExample.setOrderByClause(" c_time desc ");
        return internetRenewDetailExample;
    }

    @Override
    public PageInfo internetRenewOffsetList(InternetRenewOffset internetRenewOffset, Page page,String agentId){

        InternetRenewOffsetExample internetRenewOffsetExample = new InternetRenewOffsetExample();
        InternetRenewOffsetExample.Criteria criteria = internetRenewOffsetExample.createCriteria();
        //代理商只查询自己的
        if(StringUtils.isNotBlank(agentId)){
            criteria.andAgentIdEqualTo(agentId);
        }else if(StringUtils.isNotBlank(internetRenewOffset.getAgentId())){
            criteria.andAgentIdEqualTo(internetRenewOffset.getAgentId());
        }
        if(StringUtils.isNotBlank(internetRenewOffset.getAgentName())){
            criteria.andAgentNameLike("%"+internetRenewOffset.getAgentName()+"%");
        }
        if(StringUtils.isNotBlank(internetRenewOffset.getRenewId())){
            criteria.andRenewIdEqualTo(internetRenewOffset.getRenewId());
        }
        if(StringUtils.isNotBlank(internetRenewOffset.getRenewDetailId())){
            criteria.andRenewDetailIdEqualTo(internetRenewOffset.getRenewDetailId());
        }
        if(StringUtils.isNotBlank(internetRenewOffset.getMerId())){
            criteria.andMerIdEqualTo(internetRenewOffset.getMerId());
        }
        if(StringUtils.isNotBlank(internetRenewOffset.getMerName())){
            criteria.andMerNameEqualTo(internetRenewOffset.getMerName());
        }
        if(StringUtils.isNotBlank(internetRenewOffset.getIccidNum())){
            criteria.andIccidNumEqualTo(internetRenewOffset.getIccidNum());
        }
        if(StringUtils.isNotBlank(internetRenewOffset.getFlowId())){
            criteria.andFlowIdEqualTo(internetRenewOffset.getFlowId());
        }
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        internetRenewOffsetExample.setPage(page);
        internetRenewOffsetExample.setOrderByClause(" c_time desc");
        List<InternetRenewOffset> internetRenewOffsets = internetRenewOffsetMapper.selectByExample(internetRenewOffsetExample);
        for (InternetRenewOffset offset : internetRenewOffsets) {
            CUser cUser = iUserService.selectById(offset.getcUser());
            if(null!=cUser)
            offset.setcUser(cUser.getName());
            offset.setCleanStatus(InternetCleanStatus.getContentByValue(offset.getCleanStatus()));
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(internetRenewOffsets);
        pageInfo.setTotal((int)internetRenewOffsetMapper.countByExample(internetRenewOffsetExample));
        return pageInfo;
    }

    @Override
    public PageInfo internetRenewOffsetDetailList(InternetRenewOffsetDetail internetRenewOffsetDetail, Page page,String agentId){

        InternetRenewOffsetDetailExample internetRenewOffsetDetailExample = queryOffsetDetailParam(internetRenewOffsetDetail, agentId);
        internetRenewOffsetDetailExample.setPage(page);
        List<InternetRenewOffsetDetail> internetRenewOffsetDetails = internetRenewOffsetDetailMapper.selectByExample(internetRenewOffsetDetailExample);
        for (InternetRenewOffsetDetail offsetDetail : internetRenewOffsetDetails) {
            CUser cUser = iUserService.selectById(offsetDetail.getcUser());
            if(null!=cUser)
            offsetDetail.setcUser(cUser.getName());
            offsetDetail.setCleanStatus(InternetCleanStatus.getContentByValue(offsetDetail.getCleanStatus()));
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(internetRenewOffsetDetails);
        pageInfo.setTotal((int)internetRenewOffsetDetailMapper.countByExample(internetRenewOffsetDetailExample));
        return pageInfo;
    }

    /**
     * 每日轧差汇总 查询和导出公共
     * @param internetRenewOffsetDetail
     * @param agentId
     * @return
     */
    private InternetRenewOffsetDetailExample queryOffsetDetailParam(InternetRenewOffsetDetail internetRenewOffsetDetail,String agentId){
        InternetRenewOffsetDetailExample internetRenewOffsetDetailExample = new InternetRenewOffsetDetailExample();
        InternetRenewOffsetDetailExample.Criteria criteria = internetRenewOffsetDetailExample.createCriteria();
        //代理商只查询自己的
        if(StringUtils.isNotBlank(agentId)){
            criteria.andAgentIdEqualTo(agentId);
        }else if(StringUtils.isNotBlank(internetRenewOffsetDetail.getAgentId())){
            criteria.andAgentIdEqualTo(internetRenewOffsetDetail.getAgentId());
        }
        if(StringUtils.isNotBlank(internetRenewOffsetDetail.getAgentName())){
            criteria.andAgentNameLike("%"+internetRenewOffsetDetail.getAgentName()+"%");
        }
        if(StringUtils.isNotBlank(internetRenewOffsetDetail.getRenewId())){
            criteria.andRenewIdEqualTo(internetRenewOffsetDetail.getRenewId());
        }
        if(StringUtils.isNotBlank(internetRenewOffsetDetail.getRenewDetailId())){
            criteria.andRenewDetailIdEqualTo(internetRenewOffsetDetail.getRenewDetailId());
        }
        if(StringUtils.isNotBlank(internetRenewOffsetDetail.getMerId())){
            criteria.andMerIdEqualTo(internetRenewOffsetDetail.getMerId());
        }
        if(StringUtils.isNotBlank(internetRenewOffsetDetail.getMerName())){
            criteria.andMerNameEqualTo(internetRenewOffsetDetail.getMerName());
        }
        if(StringUtils.isNotBlank(internetRenewOffsetDetail.getIccidNum())){
            criteria.andIccidNumEqualTo(internetRenewOffsetDetail.getIccidNum());
        }
        if(StringUtils.isNotBlank(internetRenewOffsetDetail.getFlowId())){
            criteria.andFlowIdEqualTo(internetRenewOffsetDetail.getFlowId());
        }
        if(StringUtils.isNotBlank(internetRenewOffsetDetail.getId())){
            criteria.andIdEqualTo(internetRenewOffsetDetail.getId());
        }
        if(StringUtils.isNotBlank(internetRenewOffsetDetail.getProcessDateBegin())){
            String dateBegin = DateUtil.dateConvertion(internetRenewOffsetDetail.getProcessDateBegin(), DateUtil.DATE_FORMAT_yyyy_MM_dd, DateUtil.DATE_FORMAT_3);
            criteria.andProcessDateGreaterThanOrEqualTo(dateBegin);
        }
        if(StringUtils.isNotBlank(internetRenewOffsetDetail.getProcessDateEnd())){
            String dateEnd = DateUtil.dateConvertion(internetRenewOffsetDetail.getProcessDateEnd(), DateUtil.DATE_FORMAT_yyyy_MM_dd, DateUtil.DATE_FORMAT_3);
            criteria.andProcessDateLessThanOrEqualTo(dateEnd);
        }

        criteria.andStatusEqualTo(Status.STATUS_1.status);
        internetRenewOffsetDetailExample.setOrderByClause(" c_time desc");
        return internetRenewOffsetDetailExample;
    }


    @Override
    public List<InternetRenewOffsetDetail> queryInternetRenewOffsetDetailList(InternetRenewOffsetDetail internetRenewOffsetDetail, Page page,String agentId){
        InternetRenewOffsetDetailExample internetRenewOffsetDetailExample = queryOffsetDetailParam(internetRenewOffsetDetail, agentId);
        internetRenewOffsetDetailExample.setPage(page);
        List<InternetRenewOffsetDetail> internetRenewOffsetDetailList = internetRenewOffsetDetailMapper.selectByExample(internetRenewOffsetDetailExample);
        return internetRenewOffsetDetailList;
    }

    @Override
    public Integer queryInternetRenewOffsetDetailCount(InternetRenewOffsetDetail internetRenewOffsetDetail,String agentId){
        InternetRenewOffsetDetailExample internetRenewOffsetDetailExample = queryOffsetDetailParam(internetRenewOffsetDetail,agentId);
        Integer count = Integer.valueOf((int)internetRenewOffsetDetailMapper.countByExample(internetRenewOffsetDetailExample));
        return count;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public AgentResult saveAndApprove(OInternetRenew internetRenew,List<String> iccids, String cUser,
                                      List<OCashReceivablesVo> oCashReceivablesVoList)throws MessageException{

        String retIdentifier = "";
        try {
            retIdentifier = redisService.lockWithTimeout(RedisCachKey.RENEW_CARD.code + cUser, RedisService.ACQUIRE_TIME_OUT, RedisService.TIME_OUT);
            if (StringUtils.isBlank(retIdentifier)) {
                log.info("续费中请勿重复提交,cUser:{}", cUser);
                throw new MessageException("续费中请勿重复提交");
            }
            if(StringUtils.isBlank(InternetRenewWay.getContentByValue(internetRenew.getRenewWay()))){
                throw new MessageException("续费方式错误");
            }
            if(iccids==null){
                throw new MessageException("请选择要续费的卡");
            }
            if(iccids.size()==0){
                throw new MessageException("请选择要续费的卡");
            }

            int z = 1;
            Set<String> agentIdSet = new HashSet<>();
            String agentId = "";
            String agName = "";
            for (String iccid : iccids) {
                OInternetCard oInternetCard = internetCardMapper.selectByPrimaryKey(iccid);
                if (oInternetCard == null) {
                    throw new MessageException("第" + z + "个iccid不存在");
                }
                agentIdSet.add(oInternetCard.getAgentId());
                agentId = oInternetCard.getAgentId();
                agName = oInternetCard.getAgentName();
            }
            if(agentIdSet.size()!=1){
                throw new MessageException("不同代理商请分开申请");
            }
            List<String> agentNameList = dictOptionsService.getAgentNameList(Long.valueOf(cUser));
            if(agentNameList.size()!=0){
                if(!internetRenew.getRenewWay().equals(InternetRenewWay.XXBK.getValue())){
                    throw new MessageException("您只能选择线下打款支付方式,如有问题请联系管理员");
                }
            }
            BigDecimal renewCardCount = new BigDecimal(iccids.size());
            String internetRenewId = idService.genId(TabId.O_INTERNET_RENEW);
            internetRenew.setId(internetRenewId);
            internetRenew.setReviewStatus(AgStatus.Approving.status);
            internetRenew.setRenewCardCount(renewCardCount);
            internetRenew.setAgentId(agentId);
            internetRenew.setAgentName(agName);
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
            internetRenew.setSuppAmt(renewCardCount.multiply(new BigDecimal(cardAmt.getdItemvalue())));

            Dict offsetAmt = dictOptionsService.findDictByName(DictGroup.ORDER.name(), DictGroup.INTERNET_RENEW.name(), DictGroup.OFFSET_AMT.name());
            //没有轧差直接设置为0
            if(internetRenew.getRenewWay().equals(InternetRenewWay.FRDK.getValue()) || internetRenew.getRenewWay().equals(InternetRenewWay.XXBK.getValue())){
                internetRenew.setSumOffsetAmt(BigDecimal.ZERO);
            }else{
                if(offsetAmt==null){
                    throw new MessageException("缺少参数配置");
                }
                internetRenew.setSumOffsetAmt(renewCardCount.multiply(new BigDecimal(offsetAmt.getdItemvalue())));
            }
            if(internetRenew.getRenewWay().equals(InternetRenewWay.XXBKGC.getValue()) || internetRenew.getRenewWay().equals(InternetRenewWay.XXBK.getValue())) {
                if (oCashReceivablesVoList.size() == 0) {
                    throw new MessageException("线下打款必须填写打款记录");
                }
                BigDecimal xxdkAmount = BigDecimal.ZERO; //总打款金额
                for (OCashReceivablesVo oCashReceivablesVo : oCashReceivablesVoList) {
                    xxdkAmount = xxdkAmount.add(oCashReceivablesVo.getAmount());
                }
                if(xxdkAmount.compareTo(internetRenew.getSuppAmt())!=0){
                    throw new MessageException("线下打款必须等于应补款金额");
                }
                if (StringUtils.isBlank(internetRenew.getFiles())) {
                    throw new MessageException("线下打款必须上传附件");
                }
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
                OInternetRenewDetailExample oInternetRenewDetailExample = new OInternetRenewDetailExample();
                OInternetRenewDetailExample.Criteria criteria = oInternetRenewDetailExample.createCriteria();
                criteria.andStatusEqualTo(Status.STATUS_1.status);
                criteria.andIccidNumEqualTo(iccid);
                criteria.andRenewStatusEqualTo(InternetRenewStatus.XFZ.getValue());
                List<OInternetRenewDetail> oInternetRenewDetails = internetRenewDetailMapper.selectByExample(oInternetRenewDetailExample);
                if(oInternetRenewDetails.size()!=0){
                    throw new MessageException("iccid:"+iccid+"续费中,请勿重复发起");
                }
                oInternetCard.setRenewStatus(InternetRenewStatus.XFZ.getValue());
                int j = internetCardMapper.updateByPrimaryKeySelective(oInternetCard);
                if(j!=1){
                    throw new MessageException("更新物联网卡信息失败");
                }
                OInternetRenewDetail oInternetRenewDetail = new OInternetRenewDetail();
                oInternetRenewDetail.setId(idService.genId(TabId.O_INTERNET_RENEW_DETAIL));
                oInternetRenewDetail.setRenewId(internetRenewId);
                oInternetRenewDetail.setIccidNum(iccid);
                oInternetRenewDetail.setOrderId(oInternetCard.getOrderId());
                oInternetRenewDetail.setSnNum(oInternetCard.getSnNum());
                oInternetRenewDetail.setInternetCardNum(oInternetCard.getInternetCardNum());
                Date earlyDate = DateUtil.format("1900-01-01 00:00:00");
                if(oInternetCard.getOpenAccountTime().getTime()<earlyDate.getTime()){
                    throw new MessageException("第"+i+"个开户日期不正确请联系相关部门");
                }
                oInternetRenewDetail.setOpenAccountTime(oInternetCard.getOpenAccountTime());
                if(oInternetCard.getExpireTime()==null){
                    throw new MessageException("第"+i+"个缺少到期时间");
                }
                oInternetRenewDetail.setExpireTime(oInternetCard.getExpireTime());
                if(internetRenew.getRenewWay().equals(InternetRenewWay.XXBKGC.getValue()) || internetRenew.getRenewWay().equals(InternetRenewWay.FRDKGC.getValue())
                 || internetRenew.getRenewWay().equals(InternetRenewWay.GSCDGC.getValue())){
                    if(StringUtils.isBlank(oInternetCard.getMerId()) || StringUtils.isBlank(oInternetCard.getMerName())  ){
                        throw new MessageException("第"+i+"个商户信息不全,轧差商户方式必须包含商户信息");
                    }
                }
                oInternetRenewDetail.setMerId(oInternetCard.getMerId());
                oInternetRenewDetail.setMerName(oInternetCard.getMerName());
//                if(StringUtils.isBlank(oInternetCard.getAgentId())){
//                    throw new MessageException("第"+i+"个代理商编号为空");
//                }
                oInternetRenewDetail.setAgentId(oInternetCard.getAgentId());
                if(StringUtils.isBlank(oInternetCard.getAgentName())){
                    throw new MessageException("第"+i+"个代理商名称为空");
                }
                oInternetRenewDetail.setAgentName(oInternetCard.getAgentName());
                oInternetRenewDetail.setRenewWay(internetRenew.getRenewWay());
                oInternetRenewDetail.setOffsetAmt(new BigDecimal(offsetAmt.getdItemvalue()));
                oInternetRenewDetail.setRenewAmt(new BigDecimal(cardAmt.getdItemvalue()));
                oInternetRenewDetail.setOughtAmt(new BigDecimal(cardAmt.getdItemvalue()));
                //线下打款直接是实际扣款金额
                if(internetRenew.getRenewWay().equals(InternetRenewWay.XXBKGC.getValue()) || internetRenew.getRenewWay().equals(InternetRenewWay.XXBK.getValue()) ||
                    internetRenew.getRenewWay().equals(InternetRenewWay.GSCDGC.getValue()) || internetRenew.getRenewWay().equals(InternetRenewWay.GSCD.getValue())){
                    oInternetRenewDetail.setRealityAmt(new BigDecimal(cardAmt.getdItemvalue()));
                }else{
                    oInternetRenewDetail.setRealityAmt(BigDecimal.ZERO);
                }
                oInternetRenewDetail.setRenewStatus(InternetRenewStatus.XFZ.getValue());
                oInternetRenewDetail.setStatus(Status.STATUS_1.status);
                oInternetRenewDetail.setcUser(cUser);
                oInternetRenewDetail.setuUser(cUser);
                oInternetRenewDetail.setcTime(new Date());
                oInternetRenewDetail.setuTime(new Date());
                oInternetRenewDetail.setVersion(BigDecimal.ONE);
                internetRenewDetailMapper.insert(oInternetRenewDetail);
            }
            try {
                AgentResult agentResult = cashReceivablesService.addOCashReceivablesAndStartProcing(oCashReceivablesVoList,cUser,StringUtils.isBlank(agentId)?cUser:agentId, StringUtils.isBlank(agentId)?CashPayType.INTERNETRENEWN:CashPayType.INTERNETRENEW,internetRenewId);
                if(!agentResult.isOK()){
                    throw new ProcessException("保存打款记录失败");
                }
            } catch (Exception e) {
                throw new MessageException(e.getMessage());
            }
            //启动审批
            String proce = activityService.createDeloyFlow(null, dictOptionsService.getApproveVersion("cardRenew"), null, null,null);
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
            record.setBusType(BusActRelBusType.cardRenew.name());
            record.setActivStatus(AgStatus.Approving.name());
            record.setDataShiro(BusActRelBusType.cardRenew.key);
            record.setAgentId(StringUtils.isBlank(agentId) || agentId.equals("null")?"":agentId);
            record.setAgentName(StringUtils.isBlank(agName) || agName.equals("null")?"":agName);
            try {
                taskApprovalService.addABusActRel(record);
                log.info("物联网卡续费审批流启动成功");
            } catch (Exception e) {
                e.getStackTrace();
                log.error("物联网卡续费审批流启动失败{}");
                throw new MessageException("物联网卡续费审批流启动失败!:{}",e.getMessage());
            }
            return AgentResult.ok();
        }finally {
            if(StringUtils.isNotBlank(retIdentifier)){
                redisService.releaseLock(RedisCachKey.RENEW_CARD.code+cUser, retIdentifier);
            }
        }
    }

    @Override
    public OInternetRenew selectByPrimaryKey(String id){
        OInternetRenew oInternetRenew = internetRenewMapper.selectByPrimaryKey(id);
        //查询关联附件
        List<Attachment> attachments = attachmentService.accessoryQuery(id, AttachmentRelType.internetRenew.name());
        oInternetRenew.setAttachmentList(attachments);
        return oInternetRenew;
    }

    /**
     * 处理任务
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws Exception {
        try {
            if (agentVo.getApprovalResult().equals(ApprovalType.PASS.getValue())) {
                //开启独立事务，审批通过需处理
                internetRenewService.approveTashBusiness(agentVo,userId);
            }
            AgentResult result = agentEnterService.completeTaskEnterActivity(agentVo,userId);
            if(!result.isOK()){
                log.error(result.getMsg());
                throw new MessageException("工作流处理任务异常");
            }
        } catch (MessageException e) {
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MessageException(e.getLocalizedMessage());
        }
        return AgentResult.ok();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public void approveTashBusiness(AgentVo agentVo, String userId) throws Exception {
        OInternetRenew oInternetRenew = internetRenewService.selectByPrimaryKey(agentVo.getAgentBusId());
        List<String> agentNameList = dictOptionsService.getAgentNameList(Long.valueOf(oInternetRenew.getcUser()));
        CashPayType cashPayType = CashPayType.INTERNETRENEW;
        if(agentNameList.size()!=0) {
            cashPayType = CashPayType.INTERNETRENEWN;
        }
        //目前只有财务节点直接处理，后续加判断
        AgentResult cashAgentResult = cashReceivablesService.approveTashBusiness(cashPayType,agentVo.getAgentBusId(),userId,new Date(),agentVo.getoCashReceivablesVoList());
        if(!cashAgentResult.isOK()){
            throw new MessageException("更新收款信息失败");
        }
    }

    /**
     * 完成处理
     * @param proIns
     * @param agStatus
     * @return
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult compressCompensateActivity(String proIns, BigDecimal agStatus)throws Exception{

        BusActRel busActRel = busActRelService.findByProIns(proIns);
        if (busActRel==null) {
            log.info("审批任务结束{}{}，未找到审批中的审批和数据关系", proIns, agStatus);
            throw new MessageException("查询关系表失败");
        }
        OInternetRenew oInternetRenew = internetRenewMapper.selectByPrimaryKey(busActRel.getBusId());
        if(oInternetRenew==null){
            throw new MessageException("查询续费记录失败");
        }
        oInternetRenew.setReviewStatus(agStatus);
        if(agStatus.compareTo(AgStatus.Approved.getValue())==0){
            oInternetRenew.setReviewPassTime(new Date());
        }
        int i = internetRenewMapper.updateByPrimaryKeySelective(oInternetRenew);
        if(i!=1){
            throw new MessageException("更新续费记录失败");
        }
        OInternetRenewDetailExample oInternetRenewDetailExample = new OInternetRenewDetailExample();
        OInternetRenewDetailExample.Criteria criteria = oInternetRenewDetailExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andRenewIdEqualTo(oInternetRenew.getId());
        List<OInternetRenewDetail> oInternetRenewDetails = internetRenewDetailMapper.selectByExample(oInternetRenewDetailExample);
        for (OInternetRenewDetail oInternetRenewDetail : oInternetRenewDetails) {
            OInternetCard oInternetCard = internetCardMapper.selectByPrimaryKey(oInternetRenewDetail.getIccidNum());
            if(oInternetCard==null){
                throw new MessageException("查询流量卡信息失败");
            }
            //拒绝全部失效
            if(agStatus.compareTo(AgStatus.Refuse.getValue())==0){
                oInternetRenewDetail.setRenewStatus(InternetRenewStatus.SX.getValue());
                oInternetCard.setRenewStatus(InternetRenewStatus.WXF.getValue());
            }
            if(agStatus.compareTo(AgStatus.Approved.getValue())==0){
                //如果线下补款,审批通过直接已付款,否则未续费
                if(oInternetRenewDetail.getRenewWay().equals(InternetRenewWay.XXBK.getValue()) || oInternetRenewDetail.getRenewWay().equals(InternetRenewWay.XXBKGC.getValue())
                    || oInternetRenewDetail.getRenewWay().equals(InternetRenewWay.GSCD.getValue()) || oInternetRenewDetail.getRenewWay().equals(InternetRenewWay.GSCDGC.getValue())){
                    oInternetRenewDetail.setRenewStatus(InternetRenewStatus.YXF.getValue());
                    oInternetCard.setRenewStatus(InternetRenewStatus.YXF.getValue());
                    //续费成功到期时间加一年
                    oInternetCard.setExpireTime(DateUtil.getOneYearLater(oInternetCard.getExpireTime()));
                }
                oInternetCard.setStop(Status.STATUS_0.status);
                oInternetCard.setRenew(Status.STATUS_0.status);
                //生成轧差明细，同步清结算
                if(oInternetRenewDetail.getRenewWay().equals(InternetRenewWay.XXBKGC.getValue()) || oInternetRenewDetail.getRenewWay().equals(InternetRenewWay.FRDKGC.getValue())
                    || oInternetRenewDetail.getRenewWay().equals(InternetRenewWay.GSCDGC.getValue())){
                    InternetRenewOffset internetRenewOffset = new InternetRenewOffset();
                    internetRenewOffset.setFlowId(idService.genInternetOffset());
                    internetRenewOffset.setRenewId(oInternetRenew.getId());
                    internetRenewOffset.setRenewDetailId(oInternetRenewDetail.getId());
                    internetRenewOffset.setIccidNum(oInternetRenewDetail.getIccidNum());
                    internetRenewOffset.setAgentId(oInternetRenewDetail.getAgentId());
                    internetRenewOffset.setAgentName(oInternetRenewDetail.getAgentName());
                    internetRenewOffset.setMerId(oInternetRenewDetail.getMerId());
                    internetRenewOffset.setMerName(oInternetRenewDetail.getMerName());
                    internetRenewOffset.setOffsetAmt(oInternetRenewDetail.getOffsetAmt());
                    internetRenewOffset.setAlreadyOffsetAmt(BigDecimal.ZERO);
                    internetRenewOffset.setcTime(DateUtil.format(new Date(),DateUtil.DATE_FORMAT_3));
                    internetRenewOffset.setcUser(oInternetRenewDetail.getcUser());
                    internetRenewOffset.setuUser(oInternetRenewDetail.getuUser());
                    internetRenewOffset.setStatus(Status.STATUS_1.status);
                    internetRenewOffset.setVersion(BigDecimal.ONE);
                    internetRenewOffset.setCleanStatus(InternetCleanStatus.ZERO.getValue());
                    internetRenewOffsetMapper.insert(internetRenewOffset);
                }
            }
            int j = internetRenewDetailMapper.updateByPrimaryKeySelective(oInternetRenewDetail);
            if(j!=1){
                throw new MessageException("更新续费明细失败");
            }
            oInternetCard.setuTime(new Date());
            int k = internetCardMapper.updateByPrimaryKeySelective(oInternetCard);
            if(k!=1){
                throw new MessageException("更新物联网卡信息失败");
            }
        }

        AgentResult agentResult = AgentResult.fail();
        List<String> agentNameList = dictOptionsService.getAgentNameList(Long.valueOf(oInternetRenew.getcUser()));
        CashPayType cashPayType = CashPayType.INTERNETRENEW;
        if(agentNameList.size()!=0) {
            cashPayType = CashPayType.INTERNETRENEWN;
        }
        if(agStatus.compareTo(AgStatus.Refuse.getValue())==0){
            agentResult = cashReceivablesService.refuseProcing(cashPayType,busActRel.getBusId(),busActRel.getcUser());
        }
        if(agStatus.compareTo(AgStatus.Approved.getValue())==0){
            agentResult = cashReceivablesService.finishProcing(cashPayType,busActRel.getBusId(),busActRel.getcUser());
        }
        if(!agentResult.isOK()){
            throw new MessageException("更新打款记录失败");
        }
        busActRel.setActivStatus(AgStatus.getAgStatusString(agStatus));
        int z = busActRelService.updateByPrimaryKey(busActRel);
        if(z!=1) {
            throw new MessageException("物联网卡更新关系表失败");
        }

        return AgentResult.ok();
    }

    @Override
    public void renewVerify(String iccidNumIds)throws MessageException{

        String[] iccidNumIdsStr = iccidNumIds.split(",");
        for (String iccidNumId : iccidNumIdsStr) {
            OInternetCard oInternetCard = internetCardMapper.selectByPrimaryKey(iccidNumId);
            if(null==oInternetCard.getInternetCardStatus()){
                throw new MessageException("物联网卡信息不存在,请联系相关部门");
            }
            if(null==oInternetCard.getRenew()){
                throw new MessageException("物联网卡续费状态不存在,请联系相关部门");
            }
            //是否需续费为是,才展示按钮
            if(oInternetCard.getRenew().compareTo(BigDecimal.ZERO)==0){
                throw new MessageException("是否需续费否,不允许续费");
            }
            if((oInternetCard.getInternetCardStatus().compareTo(InternetCardStatus.NORMAL.getValue())==0 || oInternetCard.getInternetCardStatus().compareTo(InternetCardStatus.NOACTIVATE.getValue())==0 )
                    && oInternetCard.getRenewStatus().equals(InternetRenewStatus.WXF.getValue())){
                if(null==oInternetCard.getExpireTime()){
                    throw new MessageException("到期时间为空,不允许续费");
                }
                Date date = DateUtil.dateDay(oInternetCard.getExpireTime(), "22");
                if(Calendar.getInstance().getTime().getTime()>date.getTime()){
                    throw new MessageException("到期时间超过22号,不允许续费");
                }
            }else{
                throw new MessageException("状态不正确,不允许续费");
            }
        }
    }

    /**
     * 根据当前用户判断续费类型
     * @param cUser
     * @return
     */
    @Override
    public Map<Object, Object> getInternetRenewWay(Long cUser){
        Map<Object, Object> contentMap;
        List<COrganization> cOrganizations = departmentService.selectCityRegion(cUser);
        if(cOrganizations.size()==0){
            contentMap = InternetRenewWay.getContentMap();
        }else{
            contentMap = InternetRenewWay.getContentMapForAgent();
        }
        return contentMap;
    }



    /**
     * 获取前一天轧差数据生成明细
     * @param
     */
    @Override
    public void processDataInternetCardOffset(){

        log.info("获取前一天轧差数据生成明细,开始");
        List<InternetRenewOffset> internetRenewOffsetList = internetRenewOffsetMapper.selectInternetCardOffset();
        log.info("获取前一天轧差数据生成明细,轧差明细数量:{}",internetRenewOffsetList.size());
        for (InternetRenewOffset internetRenewOffset : internetRenewOffsetList) {
            String flowId = internetRenewOffset.getFlowId();
            BigDecimal alreadyAmt = BigDecimal.ZERO;
            InternetRenewOffsetDetailExample internetRenewOffsetDetailExample = new InternetRenewOffsetDetailExample();
            InternetRenewOffsetDetailExample.Criteria criteria = internetRenewOffsetDetailExample.createCriteria();
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            criteria.andFlowIdEqualTo(flowId);
            List<InternetRenewOffsetDetail> internetRenewOffsetDetails = internetRenewOffsetDetailMapper.selectByExample(internetRenewOffsetDetailExample);
            for (InternetRenewOffsetDetail internetRenewOffsetDetail : internetRenewOffsetDetails) {
                alreadyAmt = alreadyAmt.add(internetRenewOffsetDetail.getTodayOffsetAmt());
            }
            InternetRenewOffsetDetail internetRenewOffsetDetail = new InternetRenewOffsetDetail();
            internetRenewOffsetDetail.setId(idService.genId(TabId.O_INTERNET_RENEW_OFF_D));
            internetRenewOffsetDetail.setFlowId(flowId);
            internetRenewOffsetDetail.setRenewId(internetRenewOffset.getRenewId());
            internetRenewOffsetDetail.setRenewDetailId(internetRenewOffset.getRenewDetailId());
            internetRenewOffsetDetail.setIccidNum(internetRenewOffset.getIccidNum());
            internetRenewOffsetDetail.setAgentId(internetRenewOffset.getAgentId());
            internetRenewOffsetDetail.setAgentName(internetRenewOffset.getAgentName());
            internetRenewOffsetDetail.setMerId(internetRenewOffset.getMerId());
            internetRenewOffsetDetail.setMerName(internetRenewOffset.getMerName());
            internetRenewOffsetDetail.setOffsetAmt(internetRenewOffset.getOffsetAmt());
            internetRenewOffsetDetail.setAlreadyOffsetAmt(internetRenewOffset.getAlreadyOffsetAmt());
            BigDecimal subtract = internetRenewOffset.getAlreadyOffsetAmt().subtract(alreadyAmt);
            if(subtract.compareTo(BigDecimal.ZERO)==0){
                log.info("获取前一天轧差数据生成明细,今天没有变更不生成明细,flowId:{}",flowId);
                continue;
            }
            internetRenewOffsetDetail.setTodayOffsetAmt(subtract);
            internetRenewOffsetDetail.setcTime(internetRenewOffset.getcTime());
            internetRenewOffsetDetail.setProcessDate(internetRenewOffset.getProcessDate());
            internetRenewOffsetDetail.setProcessTime(internetRenewOffset.getProcessTime());
            internetRenewOffsetDetail.setcUser(internetRenewOffset.getcUser());
            internetRenewOffsetDetail.setuUser(internetRenewOffset.getuUser());
            internetRenewOffsetDetail.setCleanStatus(internetRenewOffset.getCleanStatus());
            internetRenewOffsetDetail.setStatus(Status.STATUS_1.status);
            internetRenewOffsetDetail.setVersion(BigDecimal.ONE);
            internetRenewOffsetDetailMapper.insertSelective(internetRenewOffsetDetail);
            log.info("获取前一天轧差数据生成明细,flowId:{},金额:{},结束:", flowId,subtract);
        }

    }


    /**
     * 给分润提供查询轧差数据
     * @param reqMap
     * @return
     */
    @Override
    public AgentResult queryMonthSumOffsetAmt(Map<String,Object> reqMap){
        log.info("给分润提供查询轧差数据,请求参数:{}",reqMap);
        AgentResult agentResult = AgentResult.fail();
        String month = String.valueOf(reqMap.get("month"));
        if(StringUtils.isBlank(month) || month.equals("null")){
            agentResult.setMsg("缺少月份");
            return agentResult;
        }
        Set<String> agentList = (Set<String>) reqMap.get("agentIdList");
        if(null==agentList){
            agentResult.setMsg("缺少代理商编号");
            return agentResult;
        }
        if(agentList.size()==0){
            agentResult.setMsg("缺少代理商编号");
            return agentResult;
        }
        List<Map<String, Object>> list = internetRenewOffsetDetailMapper.queryMonthSumOffsetAmt(reqMap);
        if(list.size()==0){
            agentResult.setMsg("暂无代理商数据");
            log.info("给分润提供查询轧差数据,返回参数1:{}",JsonUtil.objectToJson(agentResult));
            return agentResult;
        }
        log.info("给分润提供查询轧差数据,返回参数2:{}",JsonUtil.objectToJson(AgentResult.ok(list)));
        return AgentResult.ok(list);
    }


    /**
     * 查询分润抵扣流量卡数据，提供给分润系统
     * @param reqMap
     * @return
     */
    @Override
    public AgentResult queryCardProfit(Map<String,Object> reqMap){
        log.info("查询分润抵扣流量卡数据,请求参数:{}",reqMap);
        AgentResult agentResult = AgentResult.fail();
        String month = String.valueOf(reqMap.get("month"));
        if(StringUtils.isBlank(month) || month.equals("null")){
            agentResult.setMsg("缺少月份");
            return agentResult;
        }
        Set<String> agentList = (Set<String>) reqMap.get("agentIdList");
        if(null==agentList){
            agentResult.setMsg("缺少代理商编号");
            return agentResult;
        }
        if(agentList.size()==0){
            agentResult.setMsg("缺少代理商编号");
            return agentResult;
        }
        List<String> renewStatusList = new ArrayList<>();
        renewStatusList.add(InternetRenewStatus.BFXF.getValue());
        renewStatusList.add(InternetRenewStatus.XFZ.getValue());
        reqMap.put("renewStatusList",renewStatusList);
        List<Map<String, Object>> list = internetRenewDetailMapper.queryCardProfit(reqMap);
        if(list.size()==0){
            agentResult.setMsg("暂无代理商数据");
            log.info("查询分润抵扣流量卡数据,返回参数1:{}",agentResult.toString());
            return agentResult;
        }
        log.info("查询分润抵扣流量卡数据,返回参数2:{}",JsonUtil.objectToJson(AgentResult.ok(list)));
        return AgentResult.ok(list);
    }

}
