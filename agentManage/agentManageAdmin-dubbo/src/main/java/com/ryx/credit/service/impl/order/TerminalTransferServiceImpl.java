package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.AgentVoTerminalTransferDetail;
import com.ryx.credit.pojo.admin.order.TerminalTransfer;
import com.ryx.credit.pojo.admin.order.TerminalTransferDetail;
import com.ryx.credit.pojo.admin.order.TerminalTransferDetailExample;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.BusinessPlatformService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.TerminalTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2018/12/20 10:57
 * @Param
 * @return
 **/
@Service("terminalTransferService")
public class TerminalTransferServiceImpl implements TerminalTransferService {

    private static Logger log = LoggerFactory.getLogger(TerminalTransferServiceImpl.class);
    @Autowired
    private TerminalTransferMapper terminalTransferMapper;
    @Autowired
    private TerminalTransferDetailMapper terminalTransferDetailMapper;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private OLogisticsDetailMapper logisticsDetailMapper;
    @Autowired
    private OSubOrderMapper subOrderMapper;
    @Autowired
    private OSubOrderActivityMapper subOrderActivityMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private IUserService userService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private OActivityMapper oActivityMapper;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private AttachmentRelMapper attachmentRelMapper;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private BusinessPlatformService businessPlatformService;
    @Autowired
    private TermMachineService termMachineService;

    private String QUERY_SWITCH = "TerminalTransfer:ISOPEN_RES_QUERY";
    private String TRANS_SWITCH = "TerminalTransfer:ISOPEN_RES_trans";


    @Override
    public PageInfo terminalTransferList(TerminalTransfer terminalTransfer, Page page, String agName, String dataRole, Long userId) {

        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("status", Status.STATUS_1.status);
        if (StringUtils.isNotBlank(terminalTransfer.getAgentId())) {
            reqMap.put("agentId", terminalTransfer.getAgentId());
        }
        if (StringUtils.isNotBlank(agName)) {
            reqMap.put("agName", agName);
        }
        if (StringUtils.isNotBlank(terminalTransfer.getId())) {
            reqMap.put("id", terminalTransfer.getId());
        }
        if (null != terminalTransfer.getReviewStatus()) {
            reqMap.put("reviewStatus", terminalTransfer.getReviewStatus());
        }
        if (StringUtils.isBlank(dataRole) && StringUtils.isBlank(terminalTransfer.getAgentId())) {
            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
            if (orgCodeRes == null && orgCodeRes.size() != 1) {
                return null;
            }
            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
//            reqMap.put("orgId",String.valueOf(stringObjectMap.get("ORGID")));
            reqMap.put("orgCode", String.valueOf(stringObjectMap.get("ORGANIZATIONCODE")));
        }
        List<Map<String, Object>> terminalTransferList = terminalTransferMapper.selectTerminalTransferList(reqMap, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(terminalTransferList);
        pageInfo.setTotal(terminalTransferMapper.selectTerminalTransferCount(reqMap));
        return pageInfo;
    }

    @Override
    public PageInfo terminalTransferDetailList(TerminalTransferDetail terminalTransferDetail, Page page, String agName, String dataRole, Long userId) {

        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("status", Status.STATUS_1.status);
        if (StringUtils.isNotBlank(terminalTransferDetail.getTerminalTransferId())) {
            reqMap.put("terminalTransferId", terminalTransferDetail.getTerminalTransferId());
        }
        if (StringUtils.isNotBlank(terminalTransferDetail.getAgentId())) {
            reqMap.put("agentId", terminalTransferDetail.getAgentId());
        }
        if (StringUtils.isNotBlank(terminalTransferDetail.getSnBeginNum())) {
            reqMap.put("snBeginNum", terminalTransferDetail.getSnBeginNum());
        }
        if (StringUtils.isNotBlank(terminalTransferDetail.getSnEndNum())) {
            reqMap.put("snEndNum", terminalTransferDetail.getSnEndNum());
        }
        if (null != terminalTransferDetail.getAdjustStatus()) {
            reqMap.put("adjustStatus", terminalTransferDetail.getAdjustStatus());
        }
        if (StringUtils.isNotBlank(agName)) {
            reqMap.put("agName", agName);
        }
        if (StringUtils.isNotBlank(terminalTransferDetail.getGoalOrgId())) {
            reqMap.put("goalOrgId", terminalTransferDetail.getGoalOrgId());
        }
        if (StringUtils.isNotBlank(terminalTransferDetail.getGoalOrgName())) {
            reqMap.put("goalOrgName", terminalTransferDetail.getGoalOrgName());
        }
        if (StringUtils.isNotBlank(terminalTransferDetail.getOriginalOrgId())) {
            reqMap.put("originalOrgId", terminalTransferDetail.getOriginalOrgId());
        }
        if (StringUtils.isNotBlank(terminalTransferDetail.getOriginalOrgName())) {
            reqMap.put("originalOrgName", terminalTransferDetail.getOriginalOrgName());
        }
        if (StringUtils.isNotBlank(terminalTransferDetail.getButtJointPersonName())) {
            reqMap.put("buttJointPersonName", terminalTransferDetail.getButtJointPersonName());
        }
        if (StringUtils.isNotBlank(terminalTransferDetail.getId())) {
            reqMap.put("id", terminalTransferDetail.getId());
        }

        if (StringUtils.isBlank(dataRole) && StringUtils.isBlank(terminalTransferDetail.getAgentId())) {
            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
            if (orgCodeRes == null && orgCodeRes.size() != 1) {
                return null;
            }
            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
//            reqMap.put("orgId",String.valueOf(stringObjectMap.get("ORGID")));
            reqMap.put("orgCode", String.valueOf(stringObjectMap.get("ORGANIZATIONCODE")));
        }
        List<Map<String, Object>> terminalTransferList = null;
        if (page != null) {
            terminalTransferList = terminalTransferDetailMapper.selectTerminalTransferDetailList(reqMap, page);
        } else {
            terminalTransferList = terminalTransferDetailMapper.exprotTerminalTransferDetails(reqMap);
        }
        for (Map<String, Object> queryMap : terminalTransferList) {
            BigDecimal adjustStatus = new BigDecimal(queryMap.get("ADJUST_STATUS").toString());
            queryMap.put("ADJUST_STATUS_MSG", AdjustStatus.getContentByValue(adjustStatus));
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(terminalTransferList);
        pageInfo.setTotal(terminalTransferDetailMapper.selectTerminalTransferDetailCount(reqMap));
        return pageInfo;
    }

    @Override
    public PageInfo terminalTransferDetailListExport(AgentVoTerminalTransferDetail terminalTransferDetail) {

        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("status", Status.STATUS_1.status);
        if (StringUtils.isNotBlank(terminalTransferDetail.getTerminalTransferId())) {
            reqMap.put("terminalTransferId", terminalTransferDetail.getTerminalTransferId());
        }
        if (StringUtils.isNotBlank(terminalTransferDetail.getAgentId())) {
            reqMap.put("agentId", terminalTransferDetail.getAgentId());
        }
        if (StringUtils.isNotBlank(terminalTransferDetail.getSnBeginNum())) {
            reqMap.put("snBeginNum", terminalTransferDetail.getSnBeginNum());
        }
        if (StringUtils.isNotBlank(terminalTransferDetail.getSnEndNum())) {
            reqMap.put("snEndNum", terminalTransferDetail.getSnEndNum());
        }
        if (null != terminalTransferDetail.getAdjustStatus()) {
            reqMap.put("adjustStatus", terminalTransferDetail.getAdjustStatus());
        }
        if (StringUtils.isNotBlank(terminalTransferDetail.getAgName())) {
            reqMap.put("agName", terminalTransferDetail.getAgName());
        }

        if (StringUtils.isNotBlank(terminalTransferDetail.getGoalOrgId())) {
            reqMap.put("goalOrgId", terminalTransferDetail.getGoalOrgId());
        }
        if (StringUtils.isNotBlank(terminalTransferDetail.getGoalOrgName())) {
            reqMap.put("goalOrgName", terminalTransferDetail.getGoalOrgName());
        }
        if (StringUtils.isNotBlank(terminalTransferDetail.getOriginalOrgId())) {
            reqMap.put("originalOrgId", terminalTransferDetail.getOriginalOrgId());
        }
        if (StringUtils.isNotBlank(terminalTransferDetail.getOriginalOrgName())) {
            reqMap.put("originalOrgName", terminalTransferDetail.getOriginalOrgName());
        }
        if (StringUtils.isNotBlank(terminalTransferDetail.getButtJointPersonName())) {
            reqMap.put("buttJointPersonName", terminalTransferDetail.getButtJointPersonName());
        }
        if (StringUtils.isNotBlank(terminalTransferDetail.getId())) {
            reqMap.put("id", terminalTransferDetail.getId());
        }
        List<Map<String, Object>> terminalTransferList = terminalTransferDetailMapper.exprotTerminalTransferDetails(reqMap);
        for (Map<String, Object> queryMap : terminalTransferList) {
            BigDecimal adjustStatus = new BigDecimal(queryMap.get("ADJUST_STATUS").toString());
            queryMap.put("ADJUST_STATUS_MSG", AdjustStatus.getContentByValue(adjustStatus));
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(terminalTransferList);
        pageInfo.setTotal(terminalTransferDetailMapper.selectTerminalTransferDetailCount(reqMap));
        return pageInfo;
    }


    /**
     * saveFlag 1暂存2提交审批
     *
     * @param terminalTransfer
     * @param terminalTransferDetailList
     * @param cuser
     * @param saveFlag
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult saveTerminalTransfer(TerminalTransfer terminalTransfer, List<TerminalTransferDetail> terminalTransferDetailList, String cuser, String agentId, String saveFlag) throws Exception {

        if (StringUtils.isBlank(cuser) || StringUtils.isBlank(agentId)) {
            log.info("终端划拨提交审批,操作用户为空:{}", cuser);
            return AgentResult.fail("终端划拨，操作用户为空");
        }
        try {
            if (StringUtils.isBlank(terminalTransfer.getPlatformType())) {
                throw new MessageException("终端划拨，平台类型不能为空");
            }
            List<Map<String, Object>> stringList = terminalTransferMapper.querySubBusNum(agentId);
            for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailList) {


                String originalOrgId = terminalTransferDetail.getOriginalOrgId();
                String goalOrgId = terminalTransferDetail.getGoalOrgId();
                Map<String, Object> map1 = getAgentType(originalOrgId);
                Map<String, Object> map2 = getAgentType(goalOrgId);
                if (!(map1.get("BUS_PLATFORM").toString()).equals(map2.get("BUS_PLATFORM").toString())) {
                    log.info("不能跨平台划拨：原：" + originalOrgId + "目标：" + goalOrgId);
                    throw new MessageException("不能跨平台划拨：原：" + originalOrgId + "目标：" + goalOrgId);
                }


                /*Map<String,Object> querySubBusNumTopAgent1 = terminalTransferMapper.querySubBusNumTopAgent(terminalTransferDetail.getOriginalOrgId());
                Map<String,Object> querySubBusNumTopAgent2 = terminalTransferMapper.querySubBusNumTopAgent(terminalTransferDetail.getGoalOrgId());
                if(!querySubBusNumTopAgent1.get("AGENT_ID").toString().equals(querySubBusNumTopAgent2.get("AGENT_ID").toString())){
                    log.info("您本次提交的代理商没有共同上级");
                    throw new MessageException("您本次提交的代理商没有共同上级");
                }
                Map<String,Object> querySubBusNumTopAgent3 = terminalTransferMapper.querySubBusNumAgent(terminalTransferDetail.getOriginalOrgId());
                if(querySubBusNumTopAgent3.get("AGENT_ID").toString().equals(agentId)){
                    continue;
                }else{
                    if(querySubBusNumTopAgent3.get("AGENT_ID").toString().equals(querySubBusNumTopAgent1.get("AGENT_ID").toString())){
                        continue;
                    }


                }*/
                List<Map<String, Object>> maps = terminalTransferMapper.querySubBusNumTopAgentAll(terminalTransferDetail.getOriginalOrgId());
                int number = 0;
                for (Map<String, Object> map : maps) {
                    if (map.get("AGENT_ID").toString().equals(agentId)) {
                        number++;
                        break;
                    }
                }
                List<Map<String, Object>> maps2 = terminalTransferMapper.querySubBusNumTopAgentAll(terminalTransferDetail.getGoalOrgId());
                for (Map<String, Object> map : maps2) {
                    if (map.get("AGENT_ID").toString().equals(agentId)) {
                        number++;
                        break;
                    }
                }
                if (number != 2) {
                    log.info("您本次申请的目标代理商与原代理商存在不是你的下级或您本级，请修改提交");
                   /* throw new MessageException("您本次申请的目标代理商与原代理商存在不是你的下级或您本级，请修改提交");*/
                }
            }
            //本次提交是否有重复SN
            List<TerminalTransferDetail> terminalTransferDetailListA = new ArrayList<>();
            terminalTransferDetailListA.addAll(terminalTransferDetailList);
            if (terminalTransferDetailList.size() > 1) {
                for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailListA) {
                    String snBeginNum1 = terminalTransferDetail.getSnBeginNum();
                    String snEndNum1 = terminalTransferDetail.getSnEndNum();
                    Map<String, Object> map1 = disposeSN(snBeginNum1, snEndNum1);
                    int number = 0;
                    for (TerminalTransferDetail terminalTransferDetail1 : terminalTransferDetailListA) {
                        String snBeginNum = terminalTransferDetail1.getSnBeginNum();
                        String snEndNum = terminalTransferDetail1.getSnEndNum();
                        if (snBeginNum.length() != snEndNum.length()) {
                            log.info("本次提交的SN号" + snBeginNum + "---" + snEndNum + "有误请检查");
                            throw new MessageException("本次提交的SN号" + snBeginNum + "---" + snEndNum + "有误请检查");
                        }
                        Map<String, Object> map2 = disposeSN(snBeginNum, snEndNum);
                        if (snBeginNum1.length() == snBeginNum.length()) {
                            if (map1.get("sb").toString().equals(map2.get("sb").toString())) {
                                if (!(Integer.parseInt(map1.get("snEndNum1").toString()) < Integer.parseInt(map2.get("snBeginNum1").toString()) || Integer.parseInt(map1.get("snBeginNum1").toString()) > Integer.parseInt(map2.get("snEndNum1").toString()))) {
                                    number++;
                                }
                            }
                        }
                    }
                    if (number > 1) {
                        log.info("本次提交的SN号存在区间重复，请重新提交");
                        throw new MessageException("本次提交的SN号存在区间重复，请重新提交");
                    }
                }
            }

//提交是否含有重复SN在审核中
            List<Map<String, Object>> terminalTransferMappers = terminalTransferMapper.getSN();

            for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailList) {
                String snBeginNum = terminalTransferDetail.getSnBeginNum();
                String snEndNum = terminalTransferDetail.getSnEndNum();
                Map<String, Object> map3 = disposeSN(snBeginNum, snEndNum);

                /*if(new BigDecimal(map3.get("snEndNum1").toString()).subtract(new BigDecimal(map3.get("snBeginNum1").toString())).add(new BigDecimal(1)).compareTo(terminalTransferDetail.getSnCount())!=0){
                    log.info("本次提交的SN号存在区间个数有误:"+snBeginNum+"----"+snEndNum);
                    throw new MessageException("本次提交的SN号存在区间个数有误请检查:" + snBeginNum + "----" + snEndNum);
                }*/

                for (Map<String, Object> terminalTransferDetailMap : terminalTransferMappers) {
                    String snBeginNumMap = (String) terminalTransferDetailMap.get("SN_BEGIN_NUM");
                    String snEndNumMap = (String) terminalTransferDetailMap.get("SN_END_NUM");
                    Map<String, Object> map4 = disposeSN(snBeginNumMap, snEndNumMap);
                    if (snBeginNum.length() == snBeginNumMap.length()) {
                        if (map3.get("sb").toString().equals(map4.get("sb").toString())) {
                            if (!(Integer.parseInt(map4.get("snEndNum1").toString()) < Integer.parseInt(map3.get("snBeginNum1").toString()) || Integer.parseInt(map4.get("snBeginNum1").toString()) > Integer.parseInt(map3.get("snEndNum1").toString()))) {
                                log.info("在区间:" + snBeginNum + "----" + snEndNum + "已经提交过划拨申请");
                                throw new MessageException("在区间:" + snBeginNum + "----" + snEndNum + "已经提交过划拨申请");
                            }
                        }
                    }
                }
            }


            if (saveFlag.equals(SaveFlag.TJSP.getValue())) {
                terminalTransfer.setReviewStatus(AgStatus.Approving.status);
            } else {
                terminalTransfer.setReviewStatus(AgStatus.Create.status);
            }
            String terminalTransferId = idService.genId(TabId.O_TERMINAL_TRANSFER);
            terminalTransfer.setId(terminalTransferId);
            terminalTransfer.setAgentId(agentId);
            Date date = new Date();
            terminalTransfer.setcTime(date);
            terminalTransfer.setuTime(date);
            terminalTransfer.setcUser(cuser);
            terminalTransfer.setuUser(cuser);
            terminalTransfer.setStatus(Status.STATUS_1.status);
            terminalTransfer.setVersion(Status.STATUS_1.status);
            int i = terminalTransferMapper.insert(terminalTransfer);
            if (1 != i) {
                log.info("终端划拨提交审批，更新订单基本信息失败:{}", cuser);
                throw new MessageException("终端划拨提交审批，更新终端划拨基本信息失败");
            }
            if (terminalTransferDetailList.size() == 0) {
                throw new MessageException("请填写明细最少一条");
            }
            //添加新的附件
            if (StringUtils.isNotBlank(terminalTransfer.getTerTranFile())) {
                String[] terTranFiles = terminalTransfer.getTerTranFile().split(",");
                for (String terTranFile : terTranFiles) {
                    AttachmentRel record = new AttachmentRel();
                    record.setAttId(terTranFile);
                    record.setSrcId(terminalTransferId);
                    record.setcUser(cuser);
                    record.setcTime(Calendar.getInstance().getTime());
                    record.setStatus(Status.STATUS_1.status);
                    record.setBusType(AttachmentRelType.terminalTransfer.name());
                    record.setId(idService.genId(TabId.a_attachment_rel));
                    int f = attachmentRelMapper.insertSelective(record);
                    if (1 != f) {
                        log.info("终端划拨保存附件关系失败");
                        throw new MessageException("保存附件失败");
                    }
                }
            }


            /*Set<BigDecimal> platformTypeSet = new HashSet<>();*/
            for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailList) {
                /*platformTypeSet.add(terminalTransferDetail.getPlatformType());*/
                Map<String, String> resultMap = saveOrEditVerify(terminalTransferDetail, agentId);
                terminalTransferDetail.setId(idService.genId(TabId.O_TERMINAL_TRANSFER_DE));
                terminalTransferDetail.setTerminalTransferId(terminalTransferId);
                terminalTransferDetail.setcUser(cuser);
                terminalTransferDetail.setuUser(cuser);
                terminalTransferDetail.setcTime(date);
                terminalTransferDetail.setuTime(date);
                terminalTransferDetail.setStatus(Status.STATUS_1.status);
                terminalTransferDetail.setVersion(Status.STATUS_1.status);
                terminalTransferDetail.setAgentId(agentId);
                terminalTransferDetail.setAdjustStatus(AdjustStatus.WTZ.getValue());
                terminalTransferDetail.setGoalBusId(resultMap.get("goalBusId"));
                terminalTransferDetail.setOriginalBusId(resultMap.get("originalBusId"));
                terminalTransferDetail.setBusId(terminalTransfer.getPlatformType());
//                terminalTransferDetail.setProCom(resultMap.get("proCom"));
//                terminalTransferDetail.setProModel(resultMap.get("proModel"));
                terminalTransferDetailMapper.insert(terminalTransferDetail);
            }
           /* if (platformTypeSet.size() != 1) {
                throw new MessageException("不同平台类型,请分开提交");
            }*/

            if (saveFlag.equals(SaveFlag.TJSP.getValue())) {
                //检查提交数据是否可执行
                List<TerminalTransferDetail> terminalTransferDetailListsPos = new ArrayList<>();
                List<TerminalTransferDetail> terminalTransferDetailListsMpos = new ArrayList<>();
                List<TerminalTransferDetail> terminalTransferDetailListsRDBPOS = new ArrayList<>();
                for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailList) {
                    if (terminalTransferDetail.getPlatformType().toString().equals("1")) {
                        terminalTransferDetailListsPos.add(terminalTransferDetail);
                    } else if (terminalTransferDetail.getPlatformType().toString().equals("2")) {
                        terminalTransferDetailListsMpos.add(terminalTransferDetail);
                    } else if (terminalTransferDetail.getPlatformType().toString().equals("3")) {
                        terminalTransferDetailListsRDBPOS.add(terminalTransferDetail);
                    }
                }

                if (terminalTransferDetailListsPos != null && terminalTransferDetailListsPos.size() > 0) {
                    for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailListsPos) {
                        String originalOrgId = terminalTransferDetail.getOriginalOrgId();
                        String goalOrgId = terminalTransferDetail.getGoalOrgId();
                        Map<String, Object> map1 = getAgentType(originalOrgId);
                        Map<String, Object> platFromMap = terminalTransferMapper.queryPlatFrom(map1.get("BUS_PLATFORM").toString());
                        if (!platFromMap.get("PLATFORM_TYPE").toString().equals("POS")) {
                            log.info("您的机构码不属于pos平台请选择：原：" + originalOrgId + "目标：" + goalOrgId);
                            throw new MessageException("您的机构码不属于pos平台请选择：原：" + originalOrgId + "目标：" + goalOrgId);
                        }
                    }
                    String res = redisService.getValue("TerminalTransfer:ISOPEN_RES_trans");
                    AgentResult agentResult = null;
                    if (StringUtils.isNotBlank(res) && "1".equals(res)) {
                        try {
                            agentResult = termMachineService.queryTerminalTransfer(terminalTransferDetailListsPos, "check");
                        }catch ( Exception e ){
                            log.info("未获得查询结果");
                            throw new MessageException("未获得查询结果");
                        }

                    } else {
                        startTerminalTransferActivity(terminalTransferId, cuser, agentId, true);
                        return AgentResult.ok();
                    }
                    if (agentResult.isOK()) {
                        JSONObject jsonObject = JSONObject.parseObject(agentResult.getMsg());
                        JSONObject data = JSONObject.parseObject(String.valueOf(jsonObject.get("data")));
                        String result_code = String.valueOf(data.get("result_code"));
                        if (!"000000".equals(result_code)) {
                            log.info("经查询不符合划拨标准");
                            throw new MessageException("经查询不符合划拨标准");
                        }
                    } else {
                        JSONObject jsonObject = JSONObject.parseObject(agentResult.getMsg());
                        JSONObject data = JSONObject.parseObject(String.valueOf(jsonObject.get("data")));
                        String result_msg = String.valueOf(data.get("result_msg"));
                        log.info("未连通查询接口" + result_msg);
                        throw new MessageException(result_msg);
                    }
                }
                if (terminalTransferDetailListsMpos != null && terminalTransferDetailListsMpos.size() > 0) {
                    for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailListsMpos) {
                        String originalOrgId = terminalTransferDetail.getOriginalOrgId();
                        String goalOrgId = terminalTransferDetail.getGoalOrgId();
                        Map<String, Object> map1 = getAgentType(originalOrgId);
                       /* Map<String,Object> map2 = getAgentType(goalOrgId);
                        if(!(map1.get("BUS_PLATFORM").toString()).equals(map2.get("BUS_PLATFORM").toString())){
                            log.info("不能跨平台划拨：原："+originalOrgId+"目标："+goalOrgId);
                            throw new MessageException("不能跨平台划拨：原："+originalOrgId+"目标："+goalOrgId);
                        }*/
                        Map<String, Object> platFromMap = terminalTransferMapper.queryPlatFrom(map1.get("BUS_PLATFORM").toString());
                        if (!platFromMap.get("PLATFORM_TYPE").toString().equals("MPOS")) {
                            log.info("您的机构码不属于手刷平台请选择：原：" + originalOrgId + "目标：" + goalOrgId);
                            throw new MessageException("您的机构码不属于手刷平台请选择：原：" + originalOrgId + "目标：" + goalOrgId);
                        }
                        terminalTransferDetail.setPlatFrom(map1.get("BUS_PLATFORM").toString());
                    }

                    /*    String res = redisService.getValue("TerminalTransfer:ISOPEN_RES_trans");*/
                    AgentResult agentResult = null;
                    /* if(StringUtils.isNotBlank(res) && "1".equals(res)) {*/
                    try {
                        agentResult = termMachineService.queryTerminalTransfer(terminalTransferDetailListsMpos, "cx");
                    }catch ( Exception e ){
                        log.info("未获得查询结果");
                        throw new MessageException("未获得查询结果");
                    }
                   /* }else{
                        startTerminalTransferActivity(terminalTransferId, cuser, agentId, true);
                        return AgentResult.ok();
                    }*/
                    if (agentResult.isOK()) {
                        List<Map<String, Object>> mapList =  (List<Map<String, Object>>) agentResult.getData();
                        if (mapList == null || mapList.size() == 0) {
                            log.info("手刷划拨未获得结果");
                            throw new MessageException("手刷划拨未获得结果");
                        }
                        for (Map<String, Object> map : mapList) {
                            if ("code6".equals(map.get("code").toString())) {
                                continue;
                            } else {
                                log.info(map.get("startTerm").toString() + "-------" + map.get("endTerm").toString()+":"+ map.get("msg"));
                                throw new MessageException(map.get("startTerm").toString() + "-------" + map.get("endTerm").toString() + map.get("msg"));
                            }

                        }

                    } else {
                        String resultMsg = agentResult.getMsg();
                        log.info("未连通查询接口" + resultMsg);
                        throw new MessageException("未连通查询接口" + resultMsg);
                    }
                }
                if (terminalTransferDetailListsRDBPOS != null && terminalTransferDetailListsRDBPOS.size() > 0) {
                    AgentResult agentResult = termMachineService.queryTerminalTransfer(terminalTransferDetailListsRDBPOS, "check");
                    if (agentResult.isOK()) {
                    } else {
                        log.info("未连通查询接口");
                    }
                }

                startTerminalTransferActivity(terminalTransferId, cuser, agentId, true);
            }
            return AgentResult.ok();
        } catch (MessageException e) {
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("新增失败");
        }
    }

    /**
     * 处理开始和后期的SN
     *
     * @param snBeginNum
     * @param snEndNum
     * @return
     */
//传入两个sn  返货其共同部分，以及相差区间。
    public Map<String, Object> disposeSN(String snBeginNum, String snEndNum) {
        Map<String, Object> map = new HashMap<>();
        String[] snBeginNumChar = snBeginNum.split("");
        String[] snEndNumChar = snEndNum.split("");
        StringBuffer sb = new StringBuffer();
        for (int w = 0; w < snBeginNumChar.length; w++) {
            if (snBeginNumChar[w].equals(snEndNumChar[w])) {
                sb.append(snBeginNumChar[w]);
            } else {
                break;
            }
        }
        //因为最多划拨1000，所以剔除sn开始时的剔除后前段为0，剔除三次。
        String snBeginNum1 = snBeginNum.replace(sb, "");
        if (snBeginNum1.startsWith("0") && !"0".equals(snBeginNum1)) {
            snBeginNum1 = snBeginNum1.substring(1, snBeginNum1.length());
            if (snBeginNum1.startsWith("0")) {
                snBeginNum1 = snBeginNum1.substring(1, snBeginNum1.length());
                if (snBeginNum1.startsWith("0")) {
                    snBeginNum1 = snBeginNum1.substring(1, snBeginNum1.length());
                }
            }

        }
        String endsb = panduan(sb.toString());

        String snEndNum1 = snEndNum.replace(sb, "");
        map.put("sb", endsb);
        map.put("snBeginNum1", "".equals(snBeginNum1) ? "0" : snBeginNum1);
        map.put("snEndNum1", "".equals(snEndNum1) ? "0" : snEndNum1);
        return map;

    }

    //将共同部分末尾是0的全部剔除
    public String panduan(String str) {
        String[] strs = str.split("");
        for (int i = strs.length - 1; i > 0; i--) {
            if ("0".equals(strs[i])) {
                strs[i] = "";
            } else {
                break;
            }
        }
        StringBuffer sb = new StringBuffer();
        for (String ss : strs) {
            sb.append(ss);
        }

        return sb.toString().trim();
    }

    /**
     * 保存修改校验
     *
     * @param terminalTransferDetail
     * @param agentId
     * @return
     * @throws Exception
     */
    private Map<String, String> saveOrEditVerify(TerminalTransferDetail terminalTransferDetail, String agentId) throws Exception {

        Map<String, String> resultMap = new HashMap<>();
        if (StringUtils.isBlank(terminalTransferDetail.getGoalOrgId()) || StringUtils.isBlank(terminalTransferDetail.getOriginalOrgId())) {
            throw new MessageException("缺少参数");
        }
        //验证目标代理商是否存在
        AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
        AgentBusInfoExample.Criteria agentBusInfoCriteria = agentBusInfoExample.createCriteria();
        agentBusInfoCriteria.andStatusEqualTo(Status.STATUS_1.status);
        List<BigDecimal> busStatusList = new ArrayList<>();
        busStatusList.add(BusinessStatus.Enabled.status);
        busStatusList.add(BusinessStatus.inactive.status);
        agentBusInfoCriteria.andBusStatusIn(busStatusList);
        agentBusInfoCriteria.andCloReviewStatusEqualTo(AgStatus.Approved.getValue());
        agentBusInfoCriteria.andBusNumEqualTo(terminalTransferDetail.getGoalOrgId());
        List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
        if (agentBusInfos.size() != 1) {
            throw new MessageException("目标机构ID(不存在或存在多个或审批未通过)");
        }
        AgentBusInfo goalAgentBusInfo = agentBusInfos.get(0);
        Agent agent = agentMapper.selectByPrimaryKey(goalAgentBusInfo.getAgentId());
        if (!agent.getAgName().equals(terminalTransferDetail.getGoalOrgName())) {
            throw new MessageException("目标机构ID和名称不匹配");
        }
        //验证目标代理商是否存在
        AgentBusInfoExample agentBusInfoExample1 = new AgentBusInfoExample();
        AgentBusInfoExample.Criteria agentBusInfoCriteria1 = agentBusInfoExample1.createCriteria();
        agentBusInfoCriteria1.andStatusEqualTo(Status.STATUS_1.status);
        List<BigDecimal> busStatusList1 = new ArrayList<>();
        busStatusList1.add(BusinessStatus.Enabled.status);
        busStatusList1.add(BusinessStatus.inactive.status);
        agentBusInfoCriteria1.andBusStatusIn(busStatusList);
        agentBusInfoCriteria1.andCloReviewStatusEqualTo(AgStatus.Approved.getValue());
        agentBusInfoCriteria1.andBusNumEqualTo(terminalTransferDetail.getOriginalOrgId());
        List<AgentBusInfo> agentBusInfos1 = agentBusInfoMapper.selectByExample(agentBusInfoExample1);
        if (agentBusInfos1.size() != 1) {
            throw new MessageException("原机构机构ID(不存在或存在多个或审批未通过)");
        }
        Agent agent2 = agentMapper.selectByPrimaryKey(agentBusInfos1.get(0).getAgentId());
        if (!agent2.getAgName().equals(terminalTransferDetail.getOriginalOrgName())) {
            throw new MessageException("原机构ID和名称不匹配");
        }
        //查询目标机构是否是当前代理商下的
//        AgentBusInfoExample agentExample = new AgentBusInfoExample();
//        AgentBusInfoExample.Criteria agentCriteria = agentExample.createCriteria();
//        agentCriteria.andStatusEqualTo(Status.STATUS_1.status);
//        agentCriteria.andBusStatusIn(busStatusList);
//        agentCriteria.andCloReviewStatusEqualTo(AgStatus.Approved.getValue());
//        agentCriteria.andAgentIdEqualTo(agentId);
//        List<AgentBusInfo> agentBusInfoList = agentBusInfoMapper.selectByExample(agentExample);
//        Boolean isSub = false; //是否是下级
//        here:
//        for (AgentBusInfo busInfo : agentBusInfoList) {
//            List<AgentBusInfo> childLevelBusInfos = agentBusinfoService.queryChildLevelByBusNum(null, busInfo.getBusPlatform(), busInfo.getBusNum());
//            log.info("是否是下级,个数:{}",childLevelBusInfos.size());
//            for (AgentBusInfo childLevelBusInfo : childLevelBusInfos) {
//                log.info("是否是下级,childBusNum:{},GoalOrgId:{}",childLevelBusInfo.getBusNum(),terminalTransferDetail.getGoalOrgId());
//                if(childLevelBusInfo.getBusNum().equals(terminalTransferDetail.getGoalOrgId())){
//                    isSub = true;
//                    break here;
//                }
//            }
//        }
//        if(!isSub){
//            log.info("目标机构,goalOrgId:{}",terminalTransferDetail.getGoalOrgId());
//            log.info("目标机构不是当前代理商下级,agentId:{}",agentId);
//            throw new MessageException("目标机构不是当前代理商下级");
//        }
//        Map<String, Object> reqParam = new HashMap<>();
//        reqParam.put("snBegin",terminalTransferDetail.getSnBeginNum());
//        reqParam.put("snEnd",terminalTransferDetail.getSnEndNum());
//        reqParam.put("status",OLogisticsDetailStatus.STATUS_FH.code);
//        ArrayList<Object> recordStatusList = new ArrayList<>();
//        recordStatusList.add(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
//        reqParam.put("recordStatusList",recordStatusList);
//        List<Map<String,Object>> logisticsDetailList = logisticsDetailMapper.queryCompensateLList(reqParam);
//        if(logisticsDetailList.size()==0){
//            throw new MessageException("sn号在审批中或已退货");
//        }
//        BigDecimal proNumSum = new BigDecimal(0);
//        for (Map<String, Object> stringObjectMap : logisticsDetailList) {
//            proNumSum = proNumSum.add(new BigDecimal(stringObjectMap.get("PRO_NUM").toString()));
//        }
//        if(proNumSum.compareTo(terminalTransferDetail.getSnCount())!=0){
//            throw new MessageException("sn号数量不匹配");
//        }
//        Set<String> proComSet = new HashSet<>();
//        Set<String> proModelSet = new HashSet<>();
//        for (Map<String, Object> logisticsDetail : logisticsDetailList) {
//            String activityId = String.valueOf(logisticsDetail.get("ACTIVITY_ID"));
//            OActivity oActivity = oActivityMapper.selectByPrimaryKey(activityId);
//            if(oActivity==null){
//                throw new MessageException("活动不存在");
//            }
//            proComSet.add(oActivity.getVender());
//            proModelSet.add(oActivity.getProModel());
//        }
//        if(proComSet.size()!=1){
//            throw new MessageException(terminalTransferDetail.getSnBeginNum()+"到"+terminalTransferDetail.getSnEndNum()+"不是同一厂商");
//        }
//        if(proModelSet.size()!=1){
//            throw new MessageException(terminalTransferDetail.getSnBeginNum()+"到"+terminalTransferDetail.getSnEndNum()+"不是同一型号");
//        }
        AgentBusInfoExample originalOrgExample = new AgentBusInfoExample();
        AgentBusInfoExample.Criteria originalOrgCriteria = originalOrgExample.createCriteria();
        originalOrgCriteria.andStatusEqualTo(Status.STATUS_1.status);
        originalOrgCriteria.andBusStatusEqualTo(Status.STATUS_1.status);
        originalOrgCriteria.andCloReviewStatusEqualTo(AgStatus.Approved.getValue());
        originalOrgCriteria.andBusNumEqualTo(terminalTransferDetail.getOriginalOrgId());
        List<AgentBusInfo> originalOrgBusInfoList = agentBusInfoMapper.selectByExample(originalOrgExample);
        if (originalOrgBusInfoList.size() == 1) {
            resultMap.put("originalBusId", originalOrgBusInfoList.get(0).getId());
        }
//        resultMap.put("proCom",proComSet.iterator().next());
//        resultMap.put("proModel",proModelSet.iterator().next());
        resultMap.put("goalBusId", goalAgentBusInfo.getId());
        return resultMap;
    }


    /**
     * 提交审批
     *
     * @param id
     * @param cuser
     * @param agentId
     * @param isSave  是否已保存
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult startTerminalTransferActivity(String id, String cuser, String agentId, Boolean isSave) throws Exception {
        TerminalTransfer terminalTransfer = terminalTransferMapper.selectByPrimaryKey(id);
        if (terminalTransfer == null) {
            throw new MessageException("提交审批信息有误");
        }
        if (!isSave) {
            terminalTransfer.setuUser(cuser);
            terminalTransfer.setuTime(new Date());
            terminalTransfer.setReviewStatus(AgStatus.Approving.status);
            int i = terminalTransferMapper.updateByPrimaryKeySelective(terminalTransfer);
            if (i != 1) {
                throw new MessageException("提交审批处理失败");
            }
        }
        //启动审批
        String proce = activityService.createDeloyFlow(null, dictOptionsService.getApproveVersion("agentTerminal"), null, null, null);
        if (proce == null) {
            log.info("终端划拨提交审批，审批流启动失败{}:{}", id, cuser);
            throw new MessageException("审批流启动失败!");
        }
        //代理商业务和工作流关系
        BusActRel record = new BusActRel();
        record.setBusId(id);
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(cuser);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.agentTerminal.name());
        record.setActivStatus(AgStatus.Approving.name());
        record.setAgentId(agentId);
        record.setDataShiro(BusActRelBusType.agentTerminal.key);
        Agent agent = agentMapper.selectByPrimaryKey(agentId);
        if (null != agent) {
            record.setAgentName(agent.getAgName());
        }
        AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(terminalTransfer.getPlatformType());
        if (agentBusInfo == null) {
            throw new MessageException("审批流启动失败:业务信息不存在");
        }
        record.setAgDocPro(agentBusInfo.getAgDocPro());
        record.setAgDocDistrict(agentBusInfo.getAgDocDistrict());
        if (1 != busActRelMapper.insertSelective(record)) {
            log.info("订单提交审批，启动审批异常，添加审批关系失败{}:{}", id, proce);
            throw new MessageException("审批流启动失败:添加审批关系失败");
        }
        return AgentResult.ok();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult approvalTerminalTransferTask(AgentVo agentVo, String userId, String busId) throws Exception {
        try {
            if (agentVo.getApprovalResult().equals(ApprovalType.PASS.getValue())) {

                List<TerminalTransferDetail> terminalTransferDetails = queryDetailByTerminalId(busId);
              /*  if (agentVo.getSid().equals("cw")) {
                    List<TerminalTransferDetail> terminalTransferDetailsRedis = queryImprotMsgList(busId);
                    if (terminalTransferDetails.size() != terminalTransferDetailsRedis.size()) {
                  *//*      throw new MessageException("请导入信息后在提交审批");*//*
                    }
                } else {*/
                //目前就省区一个节点直接else
                for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetails) {
                    if ("".equals(terminalTransferDetail.getButtJointPerson()) || null == terminalTransferDetail.getButtJointPerson()) {
                        terminalTransferDetail.setButtJointPerson(userId);
                        int i = terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                        if (i != 1) {
                            throw new MessageException("更新审批失败");
                        }
                    }

                }
                /*   }*/
            }
            AgentResult result = agentEnterService.completeTaskEnterActivity(agentVo, userId);
            if (!result.isOK()) {
                log.error(result.getMsg());
                throw new MessageException("工作流处理任务异常");
            }
        } catch (MessageException e) {
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MessageException("catch工作流处理任务异常!");
        }
        return AgentResult.ok();
    }


    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult compressTerminalTransferActivity(String proIns, BigDecimal agStatus) throws Exception {

        BusActRelExample example = new BusActRelExample();
        example.or().andActivIdEqualTo(proIns).andStatusEqualTo(Status.STATUS_1.status).andActivStatusEqualTo(AgStatus.Approving.name());
        List<BusActRel> list = busActRelMapper.selectByExample(example);
        if (list.size() != 1) {
            log.info("审批任务结束{}{}，未找到审批中的审批和数据关系", proIns, agStatus);
            throw new MessageException("审批和数据关系有误");
        }
        BusActRel busActRel = list.get(0);
        TerminalTransfer terminalTransfer = terminalTransferMapper.selectByPrimaryKey(busActRel.getBusId());


        terminalTransfer.setReviewStatus(agStatus);
        terminalTransfer.setuTime(new Date());
        int i = terminalTransferMapper.updateByPrimaryKeySelective(terminalTransfer);
        if (i != 1) {
            log.info("审批任务结束{}{}，终端划拨更新失败1", proIns, agStatus);
            throw new MessageException("终端划拨更新失败");
        }
        TerminalTransferDetailExample terminalTransferDetailExample = new TerminalTransferDetailExample();
        TerminalTransferDetailExample.Criteria criteria = terminalTransferDetailExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andTerminalTransferIdEqualTo(terminalTransfer.getId());
        List<TerminalTransferDetail> terminalTransferDetails = terminalTransferDetailMapper.selectByExample(terminalTransferDetailExample);
        if (terminalTransferDetails.size() == 0) {
            throw new MessageException("终端划拨更新失败");
        }
        /*for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetails) {
            try {
                //从redis取出导入的数据更新到DB
                String terminalTransferJson = redisService.hGet(RedisCachKey.TERMINAL_TRANSFER.code, terminalTransferDetail.getId());
                TerminalTransferDetail terminal = JsonUtil.jsonToPojo(terminalTransferJson, TerminalTransferDetail.class);
                TerminalTransferDetail upTerminal = new TerminalTransferDetail();
                upTerminal.setId(terminalTransferDetail.getId());
                upTerminal.setAdjustStatus(terminal.getAdjustStatus());
                upTerminal.setRemark(terminal.getRemark());
                upTerminal.setAdjustTime(terminal.getAdjustTime());
                upTerminal.setBatchNum(terminal.getBatchNum());
                int j = terminalTransferDetailMapper.updateByPrimaryKeySelective(upTerminal);
                if (j != 1) {
                    throw new MessageException("终端划拨明细更新到DB失败");
                }
                redisService.hDel(RedisCachKey.TERMINAL_TRANSFER.code, terminalTransferDetail.getId());
            } catch (Exception e) {
                e.getStackTrace();
                throw new MessageException("终端划拨明细更新到DB失败");
            }
        }*/
        busActRel.setActivStatus(AgStatus.getAgStatusString(agStatus));
        int j = busActRelMapper.updateByPrimaryKey(busActRel);
        if (j != 1) {
            log.info("审批任务结束{}{}，终端划拨更新失败2", proIns, agStatus);
            throw new MessageException("终端划拨更新失败");
        }


        if(agStatus.compareTo(AgStatus.Refuse.status)==1){
            RefuseTransfer(terminalTransfer);
        }else if(agStatus.compareTo(AgStatus.Approved.status)==1){
            //将通过的结果再次返回给业务平台通知他们开始划拨
            startTransfer(terminalTransfer);
        }

        return AgentResult.ok();
    }


//代理商拒绝更新明细
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW)
    public void RefuseTransfer(TerminalTransfer terminalTransfer) throws Exception {
        TerminalTransferDetailExample terminalTransferDetailExample = new TerminalTransferDetailExample();
        TerminalTransferDetailExample.Criteria criteria = terminalTransferDetailExample.createCriteria();
        criteria.andTerminalTransferIdEqualTo(terminalTransfer.getId());
        criteria.andStatusEqualTo(new BigDecimal("1"));
        List<TerminalTransferDetail> terminalTransferDetails = terminalTransferDetailMapper.selectByExample(terminalTransferDetailExample);
        for (TerminalTransferDetail terminalTransferDetail:terminalTransferDetails) {
            terminalTransferDetail.setAdjustStatus(AdjustStatus.JJTZ.getValue());
            terminalTransferDetail.setAdjustTime(new Date());
            terminalTransferDetail.setuTime(new Date());
            terminalTransferDetail.setRemark("代理商已经拒绝");
            terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
        }
    }

    //审批通过后再一次将调整信息发送，并告知调整
    //此方法单独开启一个事物
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW)
    public void startTransfer(TerminalTransfer terminalTransfer) throws Exception {
        TerminalTransferDetailExample terminalTransferDetailExample = new TerminalTransferDetailExample();
        TerminalTransferDetailExample.Criteria criteria = terminalTransferDetailExample.createCriteria();
        criteria.andTerminalTransferIdEqualTo(terminalTransfer.getId());
        criteria.andStatusEqualTo(new BigDecimal("1"));
        List<TerminalTransferDetail> terminalTransferDetails = terminalTransferDetailMapper.selectByExample(terminalTransferDetailExample);
        List<TerminalTransferDetail> terminalTransferDetailListsPos = new ArrayList<>();
        List<TerminalTransferDetail> terminalTransferDetailListsMpos = new ArrayList<>();
        List<TerminalTransferDetail> terminalTransferDetailListsRDBPOS = new ArrayList<>();
        for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetails) {
            if (terminalTransferDetail.getPlatformType().toString().equals("1") && "1".equals(terminalTransferDetail.getStatus().toString())) {
                terminalTransferDetailListsPos.add(terminalTransferDetail);
            } else if (terminalTransferDetail.getPlatformType().toString().equals("2") && "1".equals(terminalTransferDetail.getStatus().toString())) {
                terminalTransferDetailListsMpos.add(terminalTransferDetail);
            } else if (terminalTransferDetail.getPlatformType().toString().equals("3") && "1".equals(terminalTransferDetail.getStatus().toString())) {
                terminalTransferDetailListsRDBPOS.add(terminalTransferDetail);
            }
        }

        if (terminalTransferDetailListsPos != null && terminalTransferDetailListsPos.size() > 0) {
            String res = redisService.getValue("TerminalTransfer:ISOPEN_RES_trans");
            if (StringUtils.isNotBlank(res) && "1".equals(res)) {
                try{
                    termMachineService.queryTerminalTransfer(terminalTransferDetailListsPos, "adjust");
                }catch (Exception e){
                    log.info("调用开始划拨接口时报错参数为 {}",JSONObject.toJSON(terminalTransferDetailListsPos));
                }
                for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailListsPos) {
                    terminalTransferDetail.setuTime(Calendar.getInstance().getTime());
                    terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                }
            } else {
                for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailListsPos) {
                    terminalTransferDetail.setAdjustTime(new Date());
                    terminalTransferDetail.setuTime(new Date());
                    terminalTransferDetail.setAdjustStatus(AdjustStatus.WLDTZ.getValue());
                    terminalTransferDetail.setRemark("需线下调整");
                    terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                }
            }
        }

        if (terminalTransferDetailListsMpos != null && terminalTransferDetailListsMpos.size() > 0) {
            String res = redisService.getValue("TerminalTransfer:ISOPEN_RES_trans");
            if (StringUtils.isNotBlank(res) && "1".equals(res)) {
                try{
                  termMachineService.queryTerminalTransfer(terminalTransferDetailListsMpos, "hb");
                }catch (Exception e){
                    log.info("调用开始划拨接口时报错参数为 {}",JSONObject.toJSON(terminalTransferDetailListsMpos));
                }

                for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailListsMpos) {
                    terminalTransferDetail.setuTime(Calendar.getInstance().getTime());
                    terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                }
            } else {
                for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailListsPos) {
                    terminalTransferDetail.setAdjustTime(new Date());
                    terminalTransferDetail.setuTime(new Date());
                    terminalTransferDetail.setAdjustStatus(AdjustStatus.WLDTZ.getValue());
                    terminalTransferDetail.setRemark("开关未打开需线下调整");
                    terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                }
            }

        }
        if (terminalTransferDetailListsRDBPOS != null && terminalTransferDetailListsRDBPOS.size() > 0) {
            termMachineService.queryTerminalTransfer(terminalTransferDetailListsRDBPOS, "adjust");
            RDBPOSCycleTransfer rdbposCycleTransfer = new RDBPOSCycleTransfer(terminalTransferDetailListsRDBPOS);
            Thread thread3 = new Thread(rdbposCycleTransfer);
            thread3.start();
        }
    }

    @Override
    public void queryTerminalTransferResult() throws Exception {
        List<TerminalTransferDetail> terminalTransferDetailListsPos = terminalTransferDetailMapper.queryTerminalTransferDetail();

        Iterator<TerminalTransferDetail> iterator = terminalTransferDetailListsPos.iterator();
        while (iterator.hasNext()) {
            TerminalTransferDetail terminalTransferDetail = iterator.next();

            AgentResult agentResult = null;
            //pos划拨开始查询
            if (terminalTransferDetail.getPlatformType().toString().equals("1")) {
                log.info("pos划拨开始查询");
                try {
                    if ("1".equals(terminalTransferDetail.getStatus().toString())) {
                    /*    String res = redisService.getValue("TerminalTransfer:ISOPEN_RES_QUERY");
                        if (StringUtils.isNotBlank(res) && "1".equals(res)) {*/
                            agentResult = termMachineService.queryTerminalTransferResult(terminalTransferDetail.getId(), terminalTransferDetail.getPlatformType().toString());
                       /* } else {
                        log.info("---------------------------------未打开开关--------------------------------------");
                            agentResult = AgentResult.fail();
                            agentResult.setMsg(JSONObject.toJSONString(FastMap.fastMap("data",
                                    FastMap.fastMap("result_code", "000000"))
                                    .putKeyV("transferStatus", "01")
                                    .putKeyV("resMsg", "未联动")
                            ));
                        }*/

                    } else {
                        continue;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    agentResult = AgentResult.fail();
                    agentResult.setMsg(JSONObject.toJSONString(FastMap.fastMap("data",
                            FastMap.fastMap("result_code", "000000"))
                            .putKeyV("transferStatus", "01")
                            .putKeyV("resMsg", "未联动")
                    ));
                }
                try {
                    JSONObject jsonObject = JSONObject.parseObject(agentResult.getMsg());
                    JSONObject data = JSONObject.parseObject(String.valueOf(jsonObject.get("data")));
                    String result_code = String.valueOf(data.get("result_code"));
                    String resMsg = String.valueOf(data.get("resMsg"));
                    if (agentResult.isOK()) {
                        if ("000000".equals(result_code)) {
                            String transferStatus = String.valueOf(data.get("transferStatus"));
                            if ("00".equals(transferStatus)) {
                                log.info("划拨成功请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                                log.info("划拨成功请求结果：{}", JSONObject.toJSON(agentResult));
                                terminalTransferDetail.setAdjustStatus(new BigDecimal(2));
                                terminalTransferDetail.setAdjustTime(new Date());
                                terminalTransferDetail.setuTime(new Date());
                                terminalTransferDetail.setRemark(resMsg);
                                terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                            } else if ("01".equals(transferStatus)) {
                                log.info("划拨中请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                                log.info("划拨中请求结果：{}", JSONObject.toJSON(agentResult));
                                continue;
                            } else if ("02".equals(transferStatus)) {
                                log.info("划拨失败请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                                log.info("划拨失败请求结果：{}", JSONObject.toJSON(agentResult));
                                terminalTransferDetail.setRemark(resMsg);
                                terminalTransferDetail.setAdjustTime(new Date());
                                terminalTransferDetail.setuTime(new Date());
                                terminalTransferDetail.setAdjustStatus(new BigDecimal(4));
                                terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                            }
                        } else {
                            log.info("未查到划拨结果请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                            log.info("未查到划拨结果请求结果：{}", JSONObject.toJSON(agentResult));
                            terminalTransferDetail.setRemark(resMsg);
                            terminalTransferDetail.setAdjustTime(new Date());
                            terminalTransferDetail.setuTime(new Date());
                            terminalTransferDetail.setAdjustStatus(new BigDecimal(5));
                            terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                        }

                    } else {
                        log.info("未连通查询请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                        log.info("未连通查询请求结果：{}", JSONObject.toJSON(agentResult));
                        terminalTransferDetail.setRemark(resMsg);
                        terminalTransferDetail.setAdjustTime(new Date());
                        terminalTransferDetail.setuTime(new Date());
                        terminalTransferDetail.setAdjustStatus(new BigDecimal(5));
                        terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("插入或更新数据库失败");
                    log.info("插入或更新数据库失败");
                    continue;
                }
            }
            //手刷划拨开始查询
            if (terminalTransferDetail.getPlatformType().toString().equals("2")) {
                log.info("手刷划拨开始查询");
                try {
                       /* String res = redisService.getValue("TerminalTransfer:ISOPEN_RES_QUERY");
                           if(StringUtils.isNotBlank(res) && "1".equals(res)) {*/
                    agentResult = termMachineService.queryTerminalTransferResult(terminalTransferDetail.getId(), terminalTransferDetail.getPlatformType().toString());
                          /* }else{
                               log.info("---------------------------------未打开开关----------------------------");
                               agentResult = AgentResult.fail();
                               agentResult.setMsg(JSONObject.toJSONString(FastMap.fastMap("data",
                                       FastMap.fastMap("result_code","000000"))
                                       .putKeyV("transferStatus","01")
                                       .putKeyV("resMsg","未联动")
                               ));
                           }*/
                } catch (Exception e) {
                    e.printStackTrace();
                    agentResult = AgentResult.fail();
                    agentResult.setMsg(JSONObject.toJSONString(FastMap.fastMap("data",
                            FastMap.fastMap("result_code", "000000"))
                            .putKeyV("transferStatus", "01")
                            .putKeyV("resMsg", "未联动")
                    ));
                }

                try {
                    JSONObject jsonObject = JSONObject.parseObject(agentResult.getData().toString());
                    List<Map<String, Object>> result = (List<Map<String, Object>>) jsonObject.get("result");
                    if (result == null || result.size() == 0) {
                        continue;
                    }
                    for (Map map : result) {
                        if ("code6".equals(map.get("code").toString())) {
                            log.info("划拨成功请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                            log.info("划拨成功请求结果：{}", JSONObject.toJSON(agentResult));
                            terminalTransferDetail.setAdjustStatus(AdjustStatus.YTZ.getValue());
                            terminalTransferDetail.setAdjustTime(new Date());
                            terminalTransferDetail.setuTime(new Date());
                            terminalTransferDetail.setRemark(map.get("message").toString());
                            terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                        } else {
                            log.info("划拨失败请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                            log.info("划拨失败请求结果：{}", JSONObject.toJSON(agentResult));
                            terminalTransferDetail.setRemark(map.get("message").toString());
                            terminalTransferDetail.setAdjustTime(new Date());
                            terminalTransferDetail.setuTime(new Date());
                            terminalTransferDetail.setAdjustStatus(AdjustStatus.TZSB.getValue());
                            terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("插入或更新数据库失败");
                    log.info("插入或更新数据库失败");
                    continue;
                }
            }
            if (terminalTransferDetail.getPlatformType().toString().equals("3")) {


            }

            //两天后不处理就自动变为失败
            long day = (new Date().getTime() - (terminalTransferDetail.getuTime() == null ? terminalTransferDetail.getcTime() : terminalTransferDetail.getuTime()).getTime()) / (24 * 60 * 60 * 1000);
            if (day >= 2) {
                log.info("划拨超时失败请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                log.info("划拨超时失败请求结果：{}", JSONObject.toJSON(agentResult));
                terminalTransferDetail.setRemark("划拨超时失败");
                terminalTransferDetail.setAdjustTime(new Date());
                terminalTransferDetail.setuTime(new Date());
                terminalTransferDetail.setAdjustStatus(new BigDecimal(4));
                terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
            }
        }
    }


    /**
     * 根据id查询带明细
     *
     * @param terminalTransferId
     * @return
     */
    @Override
    public TerminalTransfer queryTerminalTransfer(String terminalTransferId) {
        if (StringUtils.isBlank(terminalTransferId)) {
            return null;
        }
        TerminalTransfer terminalTransfer = terminalTransferMapper.selectByPrimaryKey(terminalTransferId);
        if (null == terminalTransfer) {
            return null;
        }
        TerminalTransferDetailExample terminalTransferDetailExample = new TerminalTransferDetailExample();
        TerminalTransferDetailExample.Criteria criteria = terminalTransferDetailExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andTerminalTransferIdEqualTo(terminalTransfer.getId());
        List<TerminalTransferDetail> terminalTransferDetails = terminalTransferDetailMapper.selectByExample(terminalTransferDetailExample);
       /* for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetails) {
            if (StringUtils.isNotBlank(terminalTransferDetail.getButtJointPerson())) {
                CUser cUser = userService.selectById(Integer.valueOf(terminalTransferDetail.getButtJointPerson()));
                    terminalTransferDetail.setButtJointPerson(cUser.getName());
            }
        }*/
        terminalTransfer.setTerminalTransferDetailList(terminalTransferDetails);
        //查询关联附件
        List<Attachment> attachments = attachmentMapper.accessoryQuery(terminalTransferId, AttachmentRelType.terminalTransfer.name());
        terminalTransfer.setAttachments(attachments);

        return terminalTransfer;
    }

    /**
     * 根据申请id查询明细
     *
     * @param terminalTransferId
     * @return
     */
    @Override
    public List<TerminalTransferDetail> queryDetailByTerminalId(String terminalTransferId) {
        if (StringUtils.isBlank(terminalTransferId)) {
            return null;
        }
        TerminalTransferDetailExample terminalTransferDetailExample = new TerminalTransferDetailExample();
        TerminalTransferDetailExample.Criteria criteria = terminalTransferDetailExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andTerminalTransferIdEqualTo(terminalTransferId);
        List<TerminalTransferDetail> terminalTransferDetails = terminalTransferDetailMapper.selectByExample(terminalTransferDetailExample);
        return terminalTransferDetails;
    }

    /**
     * 查询redis已导入信息
     *
     * @param terminalTransferId
     * @return
     */
    @Override
    public List<TerminalTransferDetail> queryImprotMsgList(String terminalTransferId) {
        List<TerminalTransferDetail> terminalTransferDetails = queryDetailByTerminalId(terminalTransferId);
        if (terminalTransferDetails == null) {
            return null;
        }
        List<TerminalTransferDetail> resultList = new ArrayList<>();
        for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetails) {
            String terminalTransferJson = redisService.hGet(RedisCachKey.TERMINAL_TRANSFER.code, terminalTransferDetail.getId());
            if (StringUtils.isBlank(terminalTransferJson)) {
                continue;
            }
            TerminalTransferDetail terminal = JsonUtil.jsonToPojo(terminalTransferJson, TerminalTransferDetail.class);
            if (terminal != null) {
                terminal.setAdjustMsg(AdjustStatus.getContentByValue(terminal.getAdjustStatus()));
                resultList.add(terminal);
            }
        }
        return resultList;
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult importTerminal(List<List<Object>> excelList, String cUser) throws Exception {

        int i = 1;
        /*  String batchNo = IDUtils.getBatchNo();*/
        List<Map<String, String>> resultList = new ArrayList<>();

        for (List<Object> objects : excelList) {
            if (objects.size() == 3 || objects.size() == 2) {
                String id = String.valueOf(objects.get(0)).trim();
                String adjustStatusCon = objects.size() >= 2 ? String.valueOf(objects.get(1)).trim() : "";
                String remark = objects.size() >= 3 ? String.valueOf(objects.get(2)).trim() : "";
                BigDecimal adjustStatus = AdjustStatus.getValueByContent(adjustStatusCon);

                if (StringUtils.isBlank(id)) {
                    throw new MessageException("第" + i + "个编号为空");
                }
                TerminalTransferDetail terminalTransferDetail = terminalTransferDetailMapper.selectByPrimaryKey(id);
           /* if (!busId.equals(terminalTransferDetail.getTerminalTransferId())) {
                throw new MessageException("第" + i + "个数据错误,不在该订单下！");
            }*/
                if (null == terminalTransferDetail) {
                    throw new MessageException("第" + i + "个编号不存在");
                }
                try {
                    terminalTransferDetail.setRemark(remark);
                    Date date = new Date();
                    terminalTransferDetail.setAdjustTime(date);
                    terminalTransferDetail.setAdjustStatus(adjustStatus);
                    terminalTransferDetail.setuUser(cUser);
                    terminalTransferDetail.setuTime(date);
                    /*  terminalTransferDetail.setBatchNum(batchNo);*/
               /* TerminalTransferDetail upTransferDetail = new TerminalTransferDetail();
                upTransferDetail.setId(id);
                upTransferDetail.setuUser(cUser);
                upTransferDetail.setuTime(date);
                upTransferDetail.setAdjustStatus(AdjustStatus.TZZ.getValue());*/
                    int j = terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                    if (j != 1) {
                        throw new MessageException("第" + i + "个数据更新失败");
                    }
                    /*redisService.hSet(RedisCachKey.TERMINAL_TRANSFER.code, id, JsonUtil.objectToJson(terminalTransferDetail));*/
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new MessageException("第" + i + "个数据处理失败");
                }
                i++;
                Map<String, String> resultMap = new HashMap<>();
                resultMap.put("id", id);
                resultMap.put("adjustStatusCon", adjustStatusCon);
                resultMap.put("remark", remark);
                resultList.add(resultMap);
            }
        }

        return AgentResult.ok(resultList);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult delTerminalTransfer(String terminalTransferId, String cUser) throws Exception {

        if (StringUtils.isBlank(terminalTransferId)) {
            throw new MessageException("数据ID为空");
        }
        TerminalTransfer terminalTransfer = terminalTransferMapper.selectByPrimaryKey(terminalTransferId);
        if (null == terminalTransfer) {
            throw new MessageException("数据不存在");
        }
        terminalTransfer.setStatus(Status.STATUS_0.status);
        Date date = new Date();
        terminalTransfer.setuTime(date);
        terminalTransfer.setuUser(cUser);
        int i = terminalTransferMapper.updateByPrimaryKeySelective(terminalTransfer);
        if (i != 1) {
            throw new MessageException("数据处理失败");
        }
        TerminalTransferDetailExample terminalTransferDetailExample = new TerminalTransferDetailExample();
        TerminalTransferDetailExample.Criteria criteria = terminalTransferDetailExample.createCriteria();
        criteria.andTerminalTransferIdEqualTo(terminalTransferId);
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<TerminalTransferDetail> terminalTransferDetails = terminalTransferDetailMapper.selectByExample(terminalTransferDetailExample);
        for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetails) {
            terminalTransferDetail.setStatus(Status.STATUS_0.status);
            terminalTransferDetail.setuTime(date);
            terminalTransferDetail.setuUser(cUser);
            int j = terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
            if (j != 1) {
                throw new MessageException("数据明细处理失败");
            }
        }
        return AgentResult.ok();
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult editTerminalTransfer(TerminalTransfer terminalTransfer, List<TerminalTransferDetail> terminalTransferDetailList, String cuser, String agentId) throws Exception {

        if (StringUtils.isBlank(terminalTransfer.getId())) {
            throw new MessageException("数据ID为空");
        }
        if (StringUtils.isBlank(agentId)) {
            throw new MessageException("缺少代理商编号");
        }
        if (StringUtils.isBlank(terminalTransfer.getPlatformType())) {
            throw new MessageException("终端划拨，平台类型不能为空");
        }
        Date date = new Date();
        terminalTransfer.setuTime(date);
        terminalTransfer.setuUser(cuser);
        int i = terminalTransferMapper.updateByPrimaryKeySelective(terminalTransfer);
        if (i != 1) {
            throw new MessageException("更新数据明细失败");
        }
        if (terminalTransferDetailList.size() == 0) {
            throw new MessageException("请填写明细最少一条");
        }
        TerminalTransfer qTerminalTransfer = terminalTransferMapper.selectByPrimaryKey(terminalTransfer.getId());
        if (qTerminalTransfer.getReviewStatus().compareTo(AgStatus.Approving.getValue()) == 0) {
            BusActRel busActRel = busActRelMapper.findByBusId(qTerminalTransfer.getId());
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(qTerminalTransfer.getPlatformType());
            if (agentBusInfo == null) {
                throw new MessageException("业务信息不存在");
            }
            busActRel.setAgDocDistrict(agentBusInfo.getAgDocDistrict());
            busActRel.setAgDocPro(agentBusInfo.getAgDocPro());
            int j = busActRelMapper.updateByPrimaryKeySelective(busActRel);
            if (j != 1) {
                throw new MessageException("修改终端信息失败");
            }
        }

        //附件修改
        AttachmentRelExample attachmentRelExample = new AttachmentRelExample();
        AttachmentRelExample.Criteria attCriteria = attachmentRelExample.createCriteria();
        attCriteria.andSrcIdEqualTo(terminalTransfer.getId());
        attCriteria.andStatusEqualTo(Status.STATUS_1.status);
        attCriteria.andBusTypeEqualTo(AttachmentRelType.terminalTransfer.name());
        List<AttachmentRel> attachmentRels = attachmentRelMapper.selectByExample(attachmentRelExample);
        for (AttachmentRel attachmentRel : attachmentRels) {
            attachmentRel.setStatus(Status.STATUS_0.status);
            int j = attachmentRelMapper.updateByPrimaryKeySelective(attachmentRel);
            if (1 != j) {
                log.info("删除附件关系失败");
                throw new MessageException("删除附件失败");
            }
        }
        if (StringUtils.isNotBlank(terminalTransfer.getTerTranFile())) {
            String[] terTranFiles = terminalTransfer.getTerTranFile().split(",");
            for (String terTranFile : terTranFiles) {
                AttachmentRel record = new AttachmentRel();
                record.setAttId(terTranFile);
                record.setSrcId(terminalTransfer.getId());
                record.setcUser(cuser);
                record.setcTime(Calendar.getInstance().getTime());
                record.setStatus(Status.STATUS_1.status);
                record.setBusType(AttachmentRelType.terminalTransfer.name());
                record.setId(idService.genId(TabId.a_attachment_rel));
                int f = attachmentRelMapper.insertSelective(record);
                if (1 != f) {
                    log.info("终端划拨附件关系失败");
                    throw new MessageException("附件关系失败");
                }
            }
        }
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("terminalTransferId", terminalTransfer.getId());
        int j = terminalTransferDetailMapper.updateStatusByTerminalTransferId(reqMap);
        if (j == 0) {
            throw new MessageException("更新失败");
        }
        Set<BigDecimal> platformTypeSet = new HashSet<>();
        for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailList) {
            platformTypeSet.add(terminalTransferDetail.getPlatformType());
            Map<String, String> resultMap = saveOrEditVerify(terminalTransferDetail, agentId);
            terminalTransferDetail.setId(idService.genId(TabId.O_TERMINAL_TRANSFER_DE));
            terminalTransferDetail.setTerminalTransferId(terminalTransfer.getId());
            terminalTransferDetail.setcUser(cuser);
            terminalTransferDetail.setuUser(cuser);
            terminalTransferDetail.setcTime(date);
            terminalTransferDetail.setuTime(date);
            terminalTransferDetail.setStatus(Status.STATUS_1.status);
            terminalTransferDetail.setVersion(Status.STATUS_1.status);
            terminalTransferDetail.setAgentId(agentId);
            terminalTransferDetail.setAdjustStatus(AdjustStatus.WTZ.getValue());
            terminalTransferDetail.setGoalBusId(resultMap.get("goalBusId"));
            terminalTransferDetail.setOriginalBusId(resultMap.get("originalBusId"));
            terminalTransferDetail.setBusId(terminalTransfer.getPlatformType());
//                terminalTransferDetail.setProCom(resultMap.get("proCom"));
//                terminalTransferDetail.setProModel(resultMap.get("proModel"));
            terminalTransferDetailMapper.insert(terminalTransferDetail);
        }
        if (platformTypeSet.size() != 1) {
            throw new MessageException("不能平台类型,请分开提交");
        }
        return AgentResult.ok();
    }


    @Override
    public void appTerminalTransfer() throws Exception {
        log.info("处理终端划拨开始");
        List<String> activIds = terminalTransferMapper.appTerminalTransfer();
        for (String activId : activIds) {
            compressTerminalTransferActivity(activId, AgStatus.Approved.status);
        }
        log.info("处理终端划拨结束");
    }


    /***
     * @Description: 获取AgentType
     * @Param: orgId 代理商id
     * @Author: chen_Liang
     * @Date: 2019/7/25
     */
    public Map<String, Object> getAgentType(String orgId) {

        return terminalTransferMapper.getAgentType(orgId);

    }

//
//    /**
//     * chenLiang
//     * MPOS内部类查询划拨结果
//     */
//    public class MposCycleTransfer implements Runnable {
//        private List<TerminalTransferDetail> terminalTransferDetailListsMpos;
//
//        public MposCycleTransfer(List<TerminalTransferDetail> terminalTransferDetailListsMpos) {
//            this.terminalTransferDetailListsMpos = terminalTransferDetailListsMpos;
//        }
//
//        @Override
//        public void run() {
//            for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailListsMpos) {
//                log.info("未联动调整");
//                terminalTransferDetail.setAdjustStatus(new BigDecimal(6));
//                terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
//            }
//        }
//    }

    /**
     * chenLiang
     * RDBPOS内部类查询划拨结果
     */
    public class RDBPOSCycleTransfer implements Runnable {
        private List<TerminalTransferDetail> terminalTransferDetailListsRDBPOS;

        public RDBPOSCycleTransfer(List<TerminalTransferDetail> terminalTransferDetailListsRDBPOS) {
            this.terminalTransferDetailListsRDBPOS = terminalTransferDetailListsRDBPOS;
        }

        @Override
        public void run() {
            for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailListsRDBPOS) {
                log.info("未联动调整");
                terminalTransferDetail.setAdjustStatus(new BigDecimal(6));
                terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
            }
        }
    }

}


