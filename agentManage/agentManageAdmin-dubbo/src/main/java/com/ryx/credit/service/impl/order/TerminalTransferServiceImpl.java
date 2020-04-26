package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONArray;
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
import com.ryx.credit.service.order.OsnOperateService;
import com.ryx.credit.service.order.TerminalTransferDetail2;
import com.ryx.credit.service.order.TerminalTransferService;
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
 * @Author chenliang
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
    @Autowired
    private TerminalTransferDetail2 terminalTransferDetail2;
    @Autowired
    private OsnOperateService osnOperateService;

    private String QUERY_SWITCH = "TerminalTransfer:ISOPEN_RES_QUERY";
    private String TRANS_SWITCH = "TerminalTransfer:ISOPEN_RES_trans";

    //综管划拨类型
    //检验
    private final static String TRANSFER_ZG_CHECK = "check";
    //调整
    private final static String TRANSFER_ZG_ADJUST = "adjust";


    //手刷划拨类型
    //检验
    private final static String TRANSFER_SS_CX = "cx";
    //调整
    private final static String TRANSFER_SS_HB = "hb";

    //划拨类型  指定划拨
    private final static String TRANSFER_TYPE_ASSIGN = "1";


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
            reqMap.put("goalOrgId", terminalTransferDetail.getGoalOrgId().trim());
        }
        if (StringUtils.isNotBlank(terminalTransferDetail.getGoalOrgName())) {
            reqMap.put("goalOrgName", terminalTransferDetail.getGoalOrgName());
        }
        if (StringUtils.isNotBlank(terminalTransferDetail.getOriginalOrgId())) {
            reqMap.put("originalOrgId", terminalTransferDetail.getOriginalOrgId().trim());
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
            reqMap.put("orgCode", String.valueOf(stringObjectMap.get("ORGANIZATIONCODE")));
        }

        //本次查询sn的区间
        if (StringUtils.isNotBlank(terminalTransferDetail.getSnBeginNum())) {
            String snBeginNumArr = terminalTransferDetail.getSnBeginNum();
            if (snBeginNumArr.length() > 6) {
                snBeginNumArr = snBeginNumArr.substring(snBeginNumArr.length() - 6);
            }
            String[] querysn = snBeginNumArr.split("");
            StringBuffer querySame = new StringBuffer();
            for (int i = querysn.length - 1; i >= 0; i--) {
                if (Character.isDigit(querysn[i].charAt(0))) {
                    querySame.append(querysn[i]);
                } else {
                    break;
                }
            }

            String snStart = terminalTransferDetail.getSnBeginNum().replaceAll(querySame.reverse().toString(), "");

            List<Map<String, Object>> queryIntervalMap = terminalTransferDetailMapper.queryInterval(snStart);
            List<String> stringList = new ArrayList<>();
            for (Map<String, Object> Interval : queryIntervalMap) {
                if (Interval.get("SN_BEGIN_NUM").toString().length() == terminalTransferDetail.getSnBeginNum().length()) {
                    try {
                        Map<String, Object> mapSn = disposeSN(Interval.get("SN_BEGIN_NUM").toString(), Interval.get("SN_END_NUM").toString());
                        if (snStart.equals(mapSn.get("sb"))) {
                            if ((Integer.valueOf(String.valueOf(querySame)) >= (Integer.valueOf((String) mapSn.get("snBeginNum1")))) && (Integer.valueOf(String.valueOf(querySame)) <= (Integer.valueOf((String) mapSn.get("snEndNum1"))))) {
                                stringList.add(String.valueOf(Interval.get("ID")));
                            }
                        }
                    } catch (MessageException e) {
                        log.error("传入两个SN获取区间失败");
                        e.printStackTrace();
                    }
                }
            }
            if (stringList.size() > 0) {
                log.info("本次查询的SN集合区间集合为：" + stringList);
                reqMap.put("intervalList", stringList);
            }
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
            reqMap.put("goalOrgId", terminalTransferDetail.getGoalOrgId().trim());
        }
        if (StringUtils.isNotBlank(terminalTransferDetail.getGoalOrgName())) {
            reqMap.put("goalOrgName", terminalTransferDetail.getGoalOrgName());
        }
        if (StringUtils.isNotBlank(terminalTransferDetail.getOriginalOrgId())) {
            reqMap.put("originalOrgId", terminalTransferDetail.getOriginalOrgId().trim());
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

    @Override
    public Map<String, Object> queryPlatFrom(String plat) {
        return terminalTransferMapper.queryPlatFrom(plat);
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
        if (terminalTransferDetailList == null || terminalTransferDetailList.size() == 0) {
            log.info("终端划拨申请明细为空");
            return AgentResult.fail("终端划拨申请明细为空");
        }
        for (TerminalTransferDetail terminalTransferDetailUtil : terminalTransferDetailList) {
            if (terminalTransferDetailUtil.getComSnNum().compareTo(new BigDecimal("1000")) == 1) {
                log.info("单次划拨超过1000台，不被允许");
                return AgentResult.fail("有单次划拨超过1000台，不被允许");
            }
        }
        try {
            if (StringUtils.isBlank(terminalTransfer.getPlatformType())) {
                throw new MessageException("终端划拨，平台类型不能为空");
            }
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(terminalTransfer.getPlatformType());
            if (agentBusInfo == null) {
                throw new MessageException("审批流启动失败:业务信息不存在");
            }
            if (agentBusInfo.getAgDocPro() == null || "".equals(agentBusInfo.getAgDocPro())) {
                throw new MessageException("终端划拨，审批流向不完整，缺少大区省区");
            }
            if (agentBusInfo.getAgDocDistrict() == null || "".equals(agentBusInfo.getAgDocDistrict())) {
                throw new MessageException("终端划拨，审批流向不完整，缺少大区省区");
            }
            if (agentBusInfo.getBusNum() == null || "".equals(agentBusInfo.getBusNum())) {
                throw new MessageException("终端划拨，审批流向不完整，缺少平台码");
            }
            if (agentBusInfo.getBusPlatform() == null || "".equals(agentBusInfo.getBusPlatform())) {
                throw new MessageException("终端划拨，审批流向不完整，平台为空");
            }

            //业务编码智慧pos业务码的处理
            terminalTransferDetailList = judgeStartsWithS(terminalTransferDetailList);
            //判断上下级
            judgeSubSup(terminalTransferDetailList, agentId);

            //判断sn是否重复提交
            repetitionSN(terminalTransferDetailList);


            terminalTransfer.setReviewStatus(AgStatus.Create.status);
            String terminalTransferId = idService.genId(TabId.O_TERMINAL_TRANSFER);
            terminalTransfer.setId(terminalTransferId);
            terminalTransfer.setAgentId(agentId);
            Date date = new Date();
            terminalTransfer.setcTime(date);
            terminalTransfer.setTaskId(UUID.randomUUID().toString().replace("-",""));
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
            for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailList) {
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
                terminalTransferDetailMapper.insert(terminalTransferDetail);
            }
            // * 判断平台是否属于提交平台
            String result = platformSame(terminalTransferDetailList, saveFlag);
            if (saveFlag.equals(SaveFlag.TJSP.getValue())) {
                startTerminalTransferActivity(terminalTransferId, cuser, agentId, false);
            }
            return AgentResult.ok(result);

        } catch (MessageException e) {
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("新增失败");
        }
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
        if (StringUtils.isBlank(terminalTransferDetail.getGoalOrgId().trim()) || StringUtils.isBlank(terminalTransferDetail.getOriginalOrgId().trim())) {
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
        agentBusInfoCriteria.andBusNumEqualTo(terminalTransferDetail.getGoalOrgId().trim());
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
        agentBusInfoCriteria1.andBusNumEqualTo(terminalTransferDetail.getOriginalOrgId().trim());
        List<AgentBusInfo> agentBusInfos1 = agentBusInfoMapper.selectByExample(agentBusInfoExample1);
        if (agentBusInfos1.size() != 1) {
            throw new MessageException("原机构机构ID(不存在或存在多个或审批未通过)");
        }
        Agent agent2 = agentMapper.selectByPrimaryKey(agentBusInfos1.get(0).getAgentId());
        if (!agent2.getAgName().equals(terminalTransferDetail.getOriginalOrgName())) {
            throw new MessageException("原机构ID和名称不匹配");
        }
        AgentBusInfoExample originalOrgExample = new AgentBusInfoExample();
        AgentBusInfoExample.Criteria originalOrgCriteria = originalOrgExample.createCriteria();
        originalOrgCriteria.andStatusEqualTo(Status.STATUS_1.status);
        originalOrgCriteria.andBusStatusEqualTo(Status.STATUS_1.status);
        originalOrgCriteria.andCloReviewStatusEqualTo(AgStatus.Approved.getValue());
        originalOrgCriteria.andBusNumEqualTo(terminalTransferDetail.getOriginalOrgId().trim());
        List<AgentBusInfo> originalOrgBusInfoList = agentBusInfoMapper.selectByExample(originalOrgExample);
        if (originalOrgBusInfoList.size() == 1) {
            resultMap.put("originalBusId", originalOrgBusInfoList.get(0).getId());
        }
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
        log.info("终端划拨流提交审批,proIns:{},agStatus:{}", cuser, agentId + "");
        String retIdentifier = "";
        try {
            retIdentifier = redisService.lockWithTimeout(RedisCachKey.TERMINAL_TRANSFER.code + cuser, RedisService.ACQUIRE_TIME_OUT, RedisService.TIME_OUT);
            if (StringUtils.isBlank(retIdentifier)) {
                log.info("已经联动划拨，请勿重复提交,proIns:{}", cuser);
                throw new MessageException("已经联动划拨，请勿重复提交");
            }
            TerminalTransfer terminalTransfer = terminalTransferMapper.selectByPrimaryKey(id);
            if (terminalTransfer.getStatus().compareTo(new BigDecimal("1")) != 0) {
                throw new MessageException("本次提交审批信息已经删除");
            }
            if (AgStatus.Create.status.compareTo(terminalTransfer.getReviewStatus()) != 0) {
                throw new MessageException("本次提交审批信息已经提交不需要多次提交");
            }
            TerminalTransferDetailExample terminalTransferDetailExample = new TerminalTransferDetailExample();
            TerminalTransferDetailExample.Criteria criteria = terminalTransferDetailExample.createCriteria();
            criteria.andTerminalTransferIdEqualTo(terminalTransfer.getId());
            criteria.andStatusEqualTo(AdjustStatus.WTZ.getValue());
            List<TerminalTransferDetail> terminalTransferDetails = terminalTransferDetailMapper.selectByExample(terminalTransferDetailExample);
            for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetails) {
                //判断代理商名称
                Map<String, String> resultMap = saveOrEditVerify(terminalTransferDetail, agentId);
            }
            //判断平台是否属于提交平台
            platformSame(terminalTransferDetails, SaveFlag.TJSP.getValue());
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

            String proce = null;
            try {
                //启动审批
                proce = activityService.createDeloyFlow(null, dictOptionsService.getApproveVersion("agentTerminal"), null, null, null);
                if (proce == null) {
                    //解锁 todo
                    AgentResult agentResult = terminalTransferunlock(terminalTransfer.getTaskId(), null, terminalTransferDetails.get(0).getPlatformType().toString());
                    if(!agentResult.isOK()){
                        throw new MessageException("审批启动异常，且解冻失败!");
                    }
                    log.info("终端划拨提交审批，审批流启动失败{}:{}", id, cuser);
                    throw new MessageException("审批流启动失败!");
                }
            }catch (Exception e){
                //解锁 todo
                AgentResult agentResult = terminalTransferunlock(terminalTransfer.getTaskId(), null, terminalTransferDetails.get(0).getPlatformType().toString());
                if(!agentResult.isOK()){
                    throw new MessageException("审批启动异常，且解冻失败!："+agentResult);
                }
                log.info("终端划拨提交审批，审批流启动异常{}:{}", id, cuser);
                throw new MessageException("审批流启动异常!");
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
            if (agentBusInfo.getAgDocPro() == null || "".equals(agentBusInfo.getAgDocPro())) {
                throw new MessageException("终端划拨，审批流向不完整，缺少大区省区");
            }
            if (agentBusInfo.getAgDocDistrict() == null || "".equals(agentBusInfo.getAgDocDistrict())) {
                throw new MessageException("终端划拨，审批流向不完整，缺少大区省区");
            }
            if (agentBusInfo.getBusNum() == null || "".equals(agentBusInfo.getBusNum())) {
                throw new MessageException("终端划拨，审批流向不完整，缺少平台码");
            }
            if (agentBusInfo.getBusPlatform() == null || "".equals(agentBusInfo.getBusPlatform())) {
                throw new MessageException("终端划拨，审批流向不完整，平台为空");
            }
            record.setAgDocPro(agentBusInfo.getAgDocPro());
            record.setAgDocDistrict(agentBusInfo.getAgDocDistrict());
            if (1 != busActRelMapper.insertSelective(record)) {
                log.info("订单提交审批，启动审批异常，添加审批关系失败{}:{}", id, proce);
                throw new MessageException("审批流启动失败:添加审批关系失败");
            }
            return AgentResult.ok();
        } finally {
            if (StringUtils.isNotBlank(retIdentifier)) {
                redisService.releaseLock(RedisCachKey.TERMINAL_TRANSFER.code + cuser, retIdentifier);
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult approvalTerminalTransferTask(AgentVo agentVo, String userId, String busId, boolean tf) throws Exception {
        try {
            List<TerminalTransferDetail> terminalTransferDetails = queryDetailByTerminalId(busId);
            TerminalTransfer terminalTransfer = terminalTransferMapper.selectByPrimaryKey(terminalTransferDetails.get(0).getTerminalTransferId());
            if (agentVo.getApprovalResult().equals(ApprovalType.PASS.getValue())) {

                 terminalTransferDetails = queryDetailByTerminalId(busId);
                log.info("本次提交的明细SN:{}", JSONObject.toJSON(terminalTransferDetails));
                //判断sn是否重复提交
                repetitionSN(terminalTransferDetails);
                if (tf) {
                    platformSame(terminalTransferDetails, SaveFlag.TJSP.getValue());
                }

                List<String> detailIds = agentVo.getTerminalTransferDetailID();
                //更新是否支付，为不影响审批流运行单独开启一个事务
                terminalTransferDetail2.updateIsNoPay(terminalTransferDetails, detailIds, userId);

            }else if(agentVo.getApprovalResult().equals(ApprovalType.BACK.getValue())){
                //解锁 todo
                AgentResult agentResult = terminalTransferunlock(terminalTransfer.getTaskId(), null, terminalTransferDetails.get(0).getPlatformType().toString());
                if(!agentResult.isOK()){
                    throw new MessageException("审批退回，且解冻失败!："+agentResult);
                }
            }
            AgentResult result = agentEnterService.completeTaskEnterActivity(agentVo, userId);
            if (!result.isOK()) {
                //解锁 todo
                AgentResult agentResult = terminalTransferunlock(terminalTransfer.getTaskId(), null, terminalTransferDetails.get(0).getPlatformType().toString());
                if(!agentResult.isOK()){
                    throw new MessageException("工作流处理任务异常，且解冻失败!："+agentResult);
                }
                log.info(result.getMsg());
                throw new MessageException("工作流处理任务异常");
            }
        }  catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new MessageException("工作流处理任务异常，请退回或者重新申请，同一审批只能一人审批切勿多人同审!");
        }
        return AgentResult.ok();
    }

    @Override
    public List<Map<String, Object>> queryToolsFloor(Map<String, String> param) {
        return terminalTransferMapper.queryToolsFloor(param);
    }

    @Override
    public List<Map<String, Object>> querySubBusNumTopAgentAll(String bus_num) {
        return terminalTransferMapper.querySubBusNumTopAgentAll(bus_num);
    }

    @Override
    public Map<String, Object> agentBase(String BUS_NUM) {
        return terminalTransferMapper.agentBase(BUS_NUM);
    }

    @Override
    public List<Map<String, Object>> queryLowerAgentAll(String bus_num) {
        return terminalTransferMapper.queryLowerAgentAll(bus_num);
    }




    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult compressTerminalTransferActivity(String proIns, BigDecimal agStatus) throws Exception {
        log.info("终端划拨流程结束,proIns:{},agStatus:{}", proIns, agStatus + "");
        String retIdentifier = "";
        try {
            retIdentifier = redisService.lockWithTimeout(RedisCachKey.TERMINAL_TRANSFER.code + proIns, RedisService.ACQUIRE_TIME_OUT, RedisService.TIME_OUT);
            if (StringUtils.isBlank(retIdentifier)) {
                log.info("已经联动划拨，请勿重复提交,proIns:{}", proIns);
                throw new MessageException("已经联动划拨，请勿重复提交");
            }
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
            busActRel.setActivStatus(AgStatus.getAgStatusString(agStatus));
            int j = busActRelMapper.updateByPrimaryKey(busActRel);
            if (j != 1) {
                log.info("审批任务结束{}{}，终端划拨更新失败2", proIns, agStatus);
                throw new MessageException("终端划拨更新失败");
            }


            if (agStatus.compareTo(AgStatus.Refuse.status) == 0) {
                RefuseTransfer(terminalTransfer);
            } else if (agStatus.compareTo(AgStatus.Approved.status) == 0) {
                //将通过的结果再次返回给业务平台通知他们开始划拨
                startTransfer(terminalTransfer);
            }

            return AgentResult.ok();
        } finally {
            if (StringUtils.isNotBlank(retIdentifier)) {
                redisService.releaseLock(RedisCachKey.TERMINAL_TRANSFER.code + proIns, retIdentifier);
            }
        }
    }


    //代理商拒绝更新明细
    public void RefuseTransfer(TerminalTransfer terminalTransfer) throws Exception {
        TerminalTransferDetailExample terminalTransferDetailExample = new TerminalTransferDetailExample();
        TerminalTransferDetailExample.Criteria criteria = terminalTransferDetailExample.createCriteria();
        criteria.andTerminalTransferIdEqualTo(terminalTransfer.getId());
        criteria.andStatusEqualTo(AdjustStatus.WTZ.getValue());
        List<TerminalTransferDetail> terminalTransferDetails = terminalTransferDetailMapper.selectByExample(terminalTransferDetailExample);
        for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetails) {
            terminalTransferDetail.setAdjustStatus(AdjustStatus.JJTZ.getValue());
            terminalTransferDetail.setAdjustTime(new Date());
            terminalTransferDetail.setuTime(new Date());
            terminalTransferDetail.setRemark("代理商已经拒绝");
            terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
        }
    }

    //审批通过后再一次将调整信息发送，并告知调整
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW)
    public void startTransfer(TerminalTransfer terminalTransfer) throws Exception {
        /*获取明细*/
        TerminalTransferDetailExample terminalTransferDetailExample = new TerminalTransferDetailExample();
        terminalTransferDetailExample.or().andTerminalTransferIdEqualTo(terminalTransfer.getId()).andStatusEqualTo(AdjustStatus.WTZ.getValue());
        List<TerminalTransferDetail> terminalTransferDetails = terminalTransferDetailMapper.selectByExample(terminalTransferDetailExample);
        /*各平台的明细*/
        List<TerminalTransferDetail> terminalTransferDetailListsPos = new ArrayList<>();
        List<TerminalTransferDetail> terminalTransferDetailListsMpos = new ArrayList<>();
        List<TerminalTransferDetail> terminalTransferDetailListsRDBPOS = new ArrayList<>();
        List<TerminalTransferDetail> terminalTransferDetailListsRJPOS = new ArrayList<>();

        for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetails) {
            if (AdjustStatus.WTZ.getValue().compareTo(terminalTransferDetail.getStatus()) == 0 && (terminalTransferDetail.getPlatformType().compareTo(TerminalPlatformType.POS.getValue()) == 0 || terminalTransferDetail.getPlatformType().compareTo(TerminalPlatformType.ZHPOS.getValue()) == 0)) {
                terminalTransferDetailListsPos.add(terminalTransferDetail);
            } else if (terminalTransferDetail.getPlatformType().compareTo(TerminalPlatformType.MPOS.getValue()) == 0 && AdjustStatus.WTZ.getValue().compareTo(terminalTransferDetail.getStatus()) == 0) {
                terminalTransferDetailListsMpos.add(terminalTransferDetail);
            } else if (terminalTransferDetail.getPlatformType().compareTo(TerminalPlatformType.RDBPOS.getValue()) == 0 && AdjustStatus.WTZ.getValue().compareTo(terminalTransferDetail.getStatus()) == 0) {
                terminalTransferDetailListsRDBPOS.add(terminalTransferDetail);
            } else if (terminalTransferDetail.getPlatformType().compareTo(TerminalPlatformType.RJPOS.getValue()) == 0 && AdjustStatus.WTZ.getValue().compareTo(terminalTransferDetail.getStatus()) == 0) {
                terminalTransferDetailListsRJPOS.add(terminalTransferDetail);
            }
        }

        /*pos划拨调整通知*/
        posAdjust(terminalTransferDetailListsPos);

        /*手刷划拨调整通知*/
        mposAdjust(terminalTransferDetailListsMpos);

        /*瑞大宝划拨结果通知*/
        rdbposAdjust(terminalTransferDetailListsRDBPOS);

        /*瑞+划拨结果通知*/
        rjAdjust(terminalTransferDetailListsRJPOS);

    }

    /**
     * pos划拨调整通知
     *
     * @param terminalTransferDetailListsPos
     */
    public void posAdjust(List<TerminalTransferDetail> terminalTransferDetailListsPos) {
        if (terminalTransferDetailListsPos != null && terminalTransferDetailListsPos.size() > 0) {
            try {
                termMachineService.queryTerminalTransfer(terminalTransferDetailListsPos, TRANSFER_ZG_ADJUST, terminalTransferMapper.selectByPrimaryKey(terminalTransferDetailListsPos.get(0).getTerminalTransferId()).getTaskId());
            } catch (Exception e) {
                log.info("调用开始划拨接口时报错参数为 {}", JSONObject.toJSON(terminalTransferDetailListsPos));
            }
            for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailListsPos) {
                terminalTransferDetail.setuTime(Calendar.getInstance().getTime());
                terminalTransferDetail.setAdjustStatus(AdjustStatus.TZZ.getValue());
                terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
            }
        }
    }

    /**
     * 手刷划拨结果通知
     *
     * @param terminalTransferDetailListsMpos
     */
    public void mposAdjust(List<TerminalTransferDetail> terminalTransferDetailListsMpos) {

        if (terminalTransferDetailListsMpos != null && terminalTransferDetailListsMpos.size() > 0) {
            for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailListsMpos) {
                String originalOrgId = terminalTransferDetail.getOriginalOrgId().trim();
                String goalOrgId = terminalTransferDetail.getGoalOrgId().trim();
                Map<String, Object> map1 = getAgentType(originalOrgId);
                terminalTransferDetail.setPlatFrom(map1.get("BUS_PLATFORM").toString());
            }
            try {

                termMachineService.queryTerminalTransfer(terminalTransferDetailListsMpos, TRANSFER_SS_HB,terminalTransferMapper.selectByPrimaryKey(terminalTransferDetailListsMpos.get(0).getTerminalTransferId()).getTaskId());
            } catch (Exception e) {
                log.info("调用开始划拨接口时报错参数为 {}", JSONObject.toJSON(terminalTransferDetailListsMpos));
            }

            for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailListsMpos) {
                terminalTransferDetail.setuTime(Calendar.getInstance().getTime());
                terminalTransferDetail.setAdjustStatus(AdjustStatus.TZZ.getValue());
                terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
            }
        }
    }

    /**
     * 瑞大宝划拨结果通知
     *
     * @param terminalTransferDetailListsRDBPOS
     * @throws Exception
     */
    public void rdbposAdjust(List<TerminalTransferDetail> terminalTransferDetailListsRDBPOS) throws Exception {
        if (terminalTransferDetailListsRDBPOS != null && terminalTransferDetailListsRDBPOS.size() > 0) {
            termMachineService.queryTerminalTransfer(terminalTransferDetailListsRDBPOS, TRANSFER_ZG_ADJUST,terminalTransferMapper.selectByPrimaryKey(terminalTransferDetailListsRDBPOS.get(0).getTerminalTransferId()).getTaskId());
            RDBPOSCycleTransfer rdbposCycleTransfer = new RDBPOSCycleTransfer(terminalTransferDetailListsRDBPOS);
            Thread thread3 = new Thread(rdbposCycleTransfer);
            thread3.start();
        }
    }

    /**
     * 瑞+划拨结果通知
     * @param terminalTransferDetailListsRJpos
     */
    public void rjAdjust(List<TerminalTransferDetail> terminalTransferDetailListsRJpos) {
        if (terminalTransferDetailListsRJpos != null && terminalTransferDetailListsRJpos.size() > 0) {
            try {
                termMachineService.queryTerminalTransfer(terminalTransferDetailListsRJpos, TRANSFER_ZG_ADJUST,terminalTransferMapper.selectByPrimaryKey(terminalTransferDetailListsRJpos.get(0).getTerminalTransferId()).getTaskId());
            } catch (Exception e) {
                log.info("调用开始划拨接口时报错参数为 {}", JSONObject.toJSON(terminalTransferDetailListsRJpos));
            }
            for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailListsRJpos) {
                terminalTransferDetail.setuTime(Calendar.getInstance().getTime());
                terminalTransferDetail.setAdjustStatus(AdjustStatus.TZZ.getValue());
                terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
            }
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
            if (terminalTransferDetail.getPlatformType().compareTo(TerminalPlatformType.POS.getValue()) == 0 || terminalTransferDetail.getPlatformType().compareTo(TerminalPlatformType.ZHPOS.getValue()) == 0) {
                try {
                    log.info("pos划拨开始查询:" + terminalTransferDetail.getId());
                    agentResult = termMachineService.queryTerminalTransferResult(terminalTransferDetail.getId(), terminalTransferDetail.getPlatformType().toString());
                    log.info("POS划拨返回：" + JSONObject.toJSONString(agentResult));
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("POS调用远程接口时异常==================：" + JSONObject.toJSONString(terminalTransferDetail));
                    agentResult = AgentResult.fail();
                }
                try {
                    if (agentResult != null) {
                        if (agentResult.isOK()) {
                            JSONObject jsonObject = JSONObject.parseObject(agentResult.getMsg());
                            JSONObject data = JSONObject.parseObject(String.valueOf(jsonObject.get("data")));
                            String result_code = String.valueOf(data.get("result_code"));
                            String resMsg = String.valueOf(data.get("resMsg"));
                            if ("000000".equals(result_code)) {
                                String transferStatus = String.valueOf(data.get("transferStatus"));
                                if ("00".equals(transferStatus)) {
                                    log.info("POS划拨成功请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                                    terminalTransferDetail.setAdjustStatus(AdjustStatus.YTZ.getValue());
                                    terminalTransferDetail.setRemark(resMsg);
                                } else if ("01".equals(transferStatus)) {
                                    log.info("POS划拨中请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                                    log.info("POS划拨中请求结果：{}", JSONObject.toJSON(agentResult));
                                    continue;
                                } else if ("02".equals(transferStatus)) {
                                    log.info("POS划拨失败请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                                    log.info("POS划拨失败请求结果：{}", JSONObject.toJSON(agentResult));
                                    terminalTransferDetail.setRemark(resMsg);
                                    terminalTransferDetail.setAdjustStatus(AdjustStatus.TZSB.getValue());
                                }
                            } else {
                                log.info("POS未查到划拨结果请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                                log.info("POS未查到划拨结果请求结果：{}", JSONObject.toJSON(agentResult));
                                terminalTransferDetail.setRemark(resMsg);
                                terminalTransferDetail.setAdjustStatus(AdjustStatus.WLDTZ.getValue());
                            }

                        } else {
                            log.info("POS未连通查询请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                            log.info("POS未连通查询请求结果：{}", JSONObject.toJSON(agentResult));
                            terminalTransferDetail.setRemark("POS未联动调整需线下处理");
                            terminalTransferDetail.setAdjustStatus(AdjustStatus.WLDTZ.getValue());
                        }
                        terminalTransferDetail.setAdjustTime(new Date());
                        terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("POS更新数据库状态失败");
                }
            }
            //手刷划拨开始查询
            if (terminalTransferDetail.getPlatformType().compareTo(TerminalPlatformType.MPOS.getValue()) == 0) {
                try {
                    log.info("手刷划拨开始查询" + terminalTransferDetail.getId());
                    agentResult = termMachineService.queryTerminalTransferResult(terminalTransferDetail.getId(), terminalTransferDetail.getPlatformType().toString());
                    log.info("手刷划拨返回：" + JSONObject.toJSONString(agentResult));
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("MPOS调用远程接口时异常==================：" + JSONObject.toJSONString(terminalTransferDetail));
                    agentResult = AgentResult.fail();
                }

                try {
                    if (agentResult != null) {
                        if (agentResult.isOK()) {
                            JSONObject jsonObject = JSONObject.parseObject(agentResult.getData().toString());
                            List<Map<String, Object>> result = (List<Map<String, Object>>) jsonObject.get("result");
                            if (result == null || result.size() == 0) {
                                continue;
                            }
                            for (Map map : result) {
                                if ("code6".equals(map.get("code").toString())) {
                                    log.info("MPOS划拨成功请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                                    log.info("MPOS划拨成功请求结果：{}", JSONObject.toJSON(agentResult));
                                    terminalTransferDetail.setAdjustStatus(AdjustStatus.YTZ.getValue());
                                    terminalTransferDetail.setRemark(map.get("message").toString());
                                } else {
                                    log.info("MPOS划拨失败请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                                    log.info("MPOS划拨失败请求结果：{}", JSONObject.toJSON(agentResult));
                                    terminalTransferDetail.setRemark(map.get("message").toString());
                                    terminalTransferDetail.setAdjustStatus(AdjustStatus.TZSB.getValue());
                                }
                            }
                        } else {
                            log.info("MPOS手刷划拨未联动请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                            log.info("MPOS手刷划拨未联动返回参数：{}", JSONObject.toJSON(agentResult));
                            terminalTransferDetail.setRemark("MPOS手刷未联动调整需线下处理");
                            terminalTransferDetail.setAdjustStatus(AdjustStatus.WLDTZ.getValue());
                        }
                        terminalTransferDetail.setAdjustTime(new Date());
                        terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("MPOS更新数据库状态失败");
                }
            }
            if (terminalTransferDetail.getPlatformType().compareTo(TerminalPlatformType.RDBPOS.getValue()) == 0) {


            }
            if (terminalTransferDetail.getPlatformType().compareTo(TerminalPlatformType.RJPOS.getValue()) == 0) {
                try {
                    log.info("瑞+划拨开始查询:" + terminalTransferDetail.getId());
                    agentResult = termMachineService.queryTerminalTransferResult(terminalTransferDetail.getId(), terminalTransferDetail.getPlatformType().toString());
                    log.info("划拨开始查询返回：" + JSONObject.toJSONString(agentResult));
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("划拨开始查询调用远程接口时异常==================：" + JSONObject.toJSONString(terminalTransferDetail));
                    agentResult = AgentResult.fail();
                }

                try {
                    if (agentResult != null) {
                        if (agentResult.isOK()) {
                            JSONObject jsonObject = JSONObject.parseObject(agentResult.getMsg());
                            JSONObject JSONObjectData = JSONObject.parseObject(String.valueOf(jsonObject.get("data")));
                            JSONObject data = JSONObject.parseObject(String.valueOf(JSONObjectData.get("data")));
                            String respCode = String.valueOf(data.get("respCode"));
                            String resMsg = String.valueOf(data.get("resMsg"));
                            if ("000000".equals(respCode)) {
                                String transferStatus = String.valueOf(data.get("transferStatus"));
                                if ("00".equals(transferStatus)) {
                                    log.info("瑞+划拨成功请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                                    terminalTransferDetail.setAdjustStatus(AdjustStatus.YTZ.getValue());
                                    terminalTransferDetail.setRemark(resMsg);
                                } else if ("01".equals(transferStatus)) {
                                    log.info("瑞+划拨中请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                                    log.info("瑞+划拨中请求结果：{}", JSONObject.toJSON(agentResult));
                                    continue;
                                } else if ("02".equals(transferStatus)) {
                                    log.info("瑞+划拨失败请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                                    log.info("瑞+划拨失败请求结果：{}", JSONObject.toJSON(agentResult));
                                    terminalTransferDetail.setRemark(resMsg);
                                    terminalTransferDetail.setAdjustStatus(AdjustStatus.TZSB.getValue());
                                }
                            } else {
                                log.info("瑞+未查到划拨结果的请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                                log.info("瑞+未查到划拨结果的请求结果：{}", JSONObject.toJSON(agentResult));
                                terminalTransferDetail.setRemark(resMsg);
                                terminalTransferDetail.setAdjustStatus(AdjustStatus.WLDTZ.getValue());
                            }

                        } else {
                            log.info("瑞+未连通查询请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                            log.info("瑞+未连通查询请求结果：{}", JSONObject.toJSON(agentResult));
                            terminalTransferDetail.setRemark("瑞+未联动调整需线下处理");
                            terminalTransferDetail.setAdjustStatus(AdjustStatus.WLDTZ.getValue());
                        }
                        terminalTransferDetail.setAdjustTime(new Date());
                        terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("瑞+更新数据库状态失败");
                }
            }

            try {
                //两天后不处理就自动变为失败
                long day = (new Date().getTime() - (terminalTransferDetail.getuTime() == null ? terminalTransferDetail.getcTime() : terminalTransferDetail.getuTime()).getTime()) / (24 * 60 * 60 * 1000);
                if (day >= 2) {
                    log.info("划拨超时失败请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                    log.info("划拨超时失败请求结果：{}", JSONObject.toJSON(agentResult));
                    terminalTransferDetail.setRemark("划拨超时失败");
                    terminalTransferDetail.setAdjustTime(new Date());
                    terminalTransferDetail.setAdjustStatus(AdjustStatus.WCDJG.getValue());
                    terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                    //解锁 todo
                    AgentResult agentResultlock = terminalTransferunlock(terminalTransferMapper.selectByPrimaryKey(terminalTransferDetail.getTerminalTransferId()).getTaskId(), terminalTransferDetail.getId(), terminalTransferDetail.getPlatformType().toString());
                    if(!agentResultlock.isOK()){
                        throw new MessageException("超时划拨失败，且解冻失败!："+agentResultlock);
                    }
                }
            } catch (Exception e) {
                log.error("划拨超时更新数据库失败");
                e.printStackTrace();
                continue;
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

                    int j = terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                    if (j != 1) {
                        throw new MessageException("第" + i + "个数据更新失败");
                    }
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
                /**
                 * 导入解锁
                 */
                AgentResult agentResultlock = terminalTransferunlock(terminalTransferMapper.selectByPrimaryKey(terminalTransferDetail.getTerminalTransferId()).getTaskId(), terminalTransferDetail.getId(), terminalTransferDetail.getPlatformType().toString());
                if(!agentResultlock.isOK()){
                    throw new MessageException("导入解冻失败!："+agentResultlock);
                }
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
        if (terminalTransfer.getStatus().compareTo(new BigDecimal("1")) != 0) {
            throw new MessageException("本次提交审批信息已经删除,不需要多次删除");
        }
        if (AgStatus.Create.status.compareTo(terminalTransfer.getReviewStatus()) != 0) {
            throw new MessageException("本次提交审批信息已经提交，不能再删除，若要删除，请联系对接人执行退回后待办任务自行拒绝。");
        }
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
        //智慧pos平台申请
        terminalTransferDetailList = judgeStartsWithS(terminalTransferDetailList);
        //判断提交sn是否重复
        repetitionSN(terminalTransferDetailList);
        //判断是否属于一个平台
        judgeSubSup(terminalTransferDetailList, agentId);

        Date date = new Date();
        terminalTransfer.setuTime(date);
        terminalTransfer.setuUser(cuser);
        terminalTransfer.setTaskId(UUID.randomUUID().toString().replace("-",""));
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
        for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailList) {
            /*  platformTypeSet.add(terminalTransferDetail.getPlatformType());*/
            Map<String, String> resultMap = saveOrEditVerify(terminalTransferDetail, agentId);
            terminalTransferDetail.setId(idService.genId(TabId.O_TERMINAL_TRANSFER_DE));
            terminalTransferDetail.setTerminalTransferId(terminalTransfer.getId());
            terminalTransferDetail.setcUser(cuser);
            terminalTransferDetail.setcTime(date);
            terminalTransferDetail.setuTime(date);
            terminalTransferDetail.setStatus(Status.STATUS_1.status);
            terminalTransferDetail.setVersion(Status.STATUS_1.status);
            terminalTransferDetail.setAgentId(agentId);
            terminalTransferDetail.setAdjustStatus(AdjustStatus.WTZ.getValue());
            terminalTransferDetail.setGoalBusId(resultMap.get("goalBusId"));
            terminalTransferDetail.setOriginalBusId(resultMap.get("originalBusId"));
            terminalTransferDetail.setBusId(terminalTransfer.getPlatformType());

            terminalTransferDetailMapper.insert(terminalTransferDetail);
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


    //以下为划拨公共判断方法 =============================================================================


    /**
     * chenliang
     * 判断上下级和平台以及指定代理商判断
     *
     * @param terminalTransferDetailList
     * @param agentId
     * @throws Exception
     */
    private void judgeSubSup(List<TerminalTransferDetail> terminalTransferDetailList, String agentId) throws Exception {

        List<Map<String, Object>> stringList = terminalTransferMapper.querySubBusNum(agentId);
        for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailList) {
            String originalOrgId = terminalTransferDetail.getOriginalOrgId().trim();
            String goalOrgId = terminalTransferDetail.getGoalOrgId().trim();
            //判断是否为指定划拨代理商
            List<Map<String, Object>> queryToolsFloorList = queryToolsFloor(FastMap.fastMap("TERINAL_TRANSFER_TYPE", TRANSFER_TYPE_ASSIGN).putKeyV("ORG_ID", originalOrgId));
            if (queryToolsFloorList != null && queryToolsFloorList.size() != 0) {
                for (Map<String, Object> queryToolsFloorMap : queryToolsFloorList) {
                    if (String.valueOf(queryToolsFloorMap.get("ORG_ID")).equals(originalOrgId)) {
                        if (String.valueOf(queryToolsFloorMap.get("ORG_ID_TARGET")).equals(goalOrgId)) {
                            if (String.valueOf(agentBase(originalOrgId).get("ID")).equals(agentId)) {
                                return;
                            }
                        }

                    }
                }
            }
            //判断最顶级不能划拨
            String originalType = terminalTransferDetail.getOriginalType();
            String goalType = terminalTransferDetail.getGoalType();
            if (BusType.JG.msg.equals(originalType) && BusType.JG.msg.equals(goalType)) {
                throw new MessageException("机构和机构不能划拨");
            }
            if (BusType.JG.msg.equals(originalType) && BusType.BZYD.msg.equals(goalType)) {
                throw new MessageException("机构和标准一代不能划拨");
            }
            if (BusType.BZYD.msg.equals(originalType) && BusType.JG.msg.equals(goalType)) {
                throw new MessageException("标准一代和机构不能划拨");
            }
            if (BusType.BZYD.msg.equals(originalType) && BusType.BZYD.msg.equals(goalType)) {
                throw new MessageException("标准一代和标准一代不能划拨");
            }

            //平台判断
            Map<String, Object> map1 = getAgentType(originalOrgId);
            Map<String, Object> map2 = getAgentType(goalOrgId);
            if (!(map1.get("BUS_PLATFORM").toString()).equals(map2.get("BUS_PLATFORM").toString())) {
                log.info("不能跨平台划拨：原：" + originalOrgId + "目标：" + goalOrgId);
                throw new MessageException("不能跨平台划拨：原：" + originalOrgId + "目标：" + goalOrgId);
            }
            List<Map<String, Object>> maps = terminalTransferMapper.querySubBusNumTopAgentAll(terminalTransferDetail.getOriginalOrgId().trim());
            String originalTop = "";
            for (Map<String, Object> map : maps) {
                if ((map.get("BUS_TYPE").toString().equals(BusType.JG.key)) || (map.get("BUS_TYPE").toString().equals(BusType.BZYD.key))) {
                    originalTop = map.get("AGENT_ID").toString();
                    break;
                }
            }
            int number = 0;
            for (Map<String, Object> map : maps) {
                if (map.get("AGENT_ID").toString().equals(agentId)) {
                    number++;
                    break;
                }
            }

            List<Map<String, Object>> maps2 = terminalTransferMapper.querySubBusNumTopAgentAll(terminalTransferDetail.getGoalOrgId().trim());
            String goalTop = "";
            //操盘方的判断
            for (Map<String, Object> map : maps2) {
                if ((map.get("BUS_TYPE").toString().equals(BusType.JG.key)) || (map.get("BUS_TYPE").toString().equals(BusType.BZYD.key))) {
                    goalTop = map.get("AGENT_ID").toString();
                    break;
                }
            }
            if (!originalTop.equals(goalTop)) {
                log.info("您提交的目标代理商和机构代理商不属于同一个机构或标准一代，请修改提交");
                throw new MessageException("您提交的目标代理商和机构代理商不属于同一个机构或标准一代，请修改提交");
            }

            //判断是否是操作的自己的下级
            for (Map<String, Object> map : maps2) {
                if (map.get("AGENT_ID").toString().equals(agentId)) {
                    number++;
                    break;
                }
            }
            if (number != 2) {
                log.info("您本次申请的目标代理商与原代理商存在不是你的下级或您本级，请修改提交");
                throw new MessageException("您本次申请的目标代理商与原代理商存在不是你的下级或您本级，请修改提交");
            }
        }
    }


    /**
     * chenliang
     * 判断重复sn提交时
     *
     * @param terminalTransferDetailList
     * @throws Exception
     */
    private void repetitionSN(List<TerminalTransferDetail> terminalTransferDetailList) throws Exception {
        //本次提交是否有重复SN
        thisTimeSubmit(terminalTransferDetailList);
        //提交是否含有重复SN在审核中
        beforeSubmit(terminalTransferDetailList);

    }


    //本次提交是否重复判断
    public void thisTimeSubmit(List<TerminalTransferDetail> terminalTransferDetailList) throws MessageException {
        //本次提交是否有重复SN
        List<TerminalTransferDetail> terminalTransferDetailListA = new ArrayList<>();
        terminalTransferDetailListA.addAll(terminalTransferDetailList);
        if (terminalTransferDetailList.size() > 0) {
            for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailListA) {
                String snBeginNumOut = terminalTransferDetail.getSnBeginNum();
                String snEndNum1Out = terminalTransferDetail.getSnEndNum();
                if (snBeginNumOut.length() != snEndNum1Out.length()) {
                    log.info("本次提交的SN号" + snBeginNumOut + "---" + snEndNum1Out + "有误请检查");
                    throw new MessageException("本次提交的SN号" + snBeginNumOut + "---" + snEndNum1Out + "有误请检查");
                }
                Map<String, Object> map1 = disposeSN(snBeginNumOut, snEndNum1Out);
                int number = 0;
                for (TerminalTransferDetail terminalTransferDetail1 : terminalTransferDetailListA) {
                    String snBeginNumIn = terminalTransferDetail1.getSnBeginNum();
                    String snEndNumIn = terminalTransferDetail1.getSnEndNum();
                    Map<String, Object> map2 = disposeSN(snBeginNumIn, snEndNumIn);
                    if (snBeginNumIn.length() == snEndNumIn.length()) {
                        if (map1.get("sb").toString().equals(map2.get("sb").toString())) {
                            if (!((new Integer((String) map1.get("snEndNum1")) < (new Integer((String) map2.get("snBeginNum1")))) || (new Integer((String) map1.get("snBeginNum1")) > (new Integer((String) map2.get("snEndNum1")))))) {
                                number++;
                            }
                        }
                    }
                }
                if (number > 1) {
                    log.info(" 本次提交的SN号存在区间重复，请重新提交");
                    throw new MessageException("本次提交的SN号存在区间重复，请重新提交");
                }
            }
        }
    }

    //以前提交是否重复判断
    public void beforeSubmit(List<TerminalTransferDetail> terminalTransferDetailList) throws MessageException {
        for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailList) {
            //检查sn是否在划拨，换活动，退货审批中
            FastMap fastMap = osnOperateService.checkSNApproval(FastMap
                    .fastMap("beginSN", terminalTransferDetail.getSnBeginNum())
                    .putKeyV("endSN", terminalTransferDetail.getSnEndNum())
                    .putKeyV("type", "transfer"));
            if (!FastMap.isSuc(fastMap)) {
                throw new MessageException(fastMap.get("msg").toString());
            }

            String snBeginNum = terminalTransferDetail.getSnBeginNum();
            String snEndNum = terminalTransferDetail.getSnEndNum();
            Map<String, Object> map3 = disposeSN(snBeginNum, snEndNum);
            List<Map<String, Object>> terminalTransferMappers = terminalTransferMapper.getSN((String) map3.get("sb"));
            int num = 0;
            for (Map<String, Object> terminalTransferDetailMap : terminalTransferMappers) {
                String snBeginNumMap = (String) terminalTransferDetailMap.get("SN_BEGIN_NUM");
                String snEndNumMap = (String) terminalTransferDetailMap.get("SN_END_NUM");
                Map<String, Object> map4 = disposeSN(snBeginNumMap.trim(), snEndNumMap.trim());
                if (snBeginNum.length() == snBeginNumMap.length()) {
                    if (map3.get("sb").toString().equals(map4.get("sb").toString())) {
                        if (!((new Integer(map4.get("snEndNum1").toString()) < new Integer(map3.get("snBeginNum1").toString())) || (new Integer(map4.get("snBeginNum1").toString()) > new Integer(map3.get("snEndNum1").toString())))) {
                            if (String.valueOf(terminalTransferDetailMap.get("ID")).equals(terminalTransferDetail.getId())) {
                                continue;
                            } else {
                                log.info("在区间:" + snBeginNum + "----" + snEndNum + "已经提交过划拨申请");
                                throw new MessageException("在区间:" + snBeginNum + "----" + snEndNum + "已经提交过划拨申请");
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 判断平台是否属于提交平台
     * chenliang
     *
     * @param terminalTransferDetailList
     * @throws Exception
     */
    private String platformSame(List<TerminalTransferDetail> terminalTransferDetailList, String saveFlag) throws Exception {
        List<TerminalTransferDetail> terminalTransferDetailListsPos = new ArrayList<>();
        List<TerminalTransferDetail> terminalTransferDetailListsMpos = new ArrayList<>();
        List<TerminalTransferDetail> terminalTransferDetailListsRDBPOS = new ArrayList<>();
        List<TerminalTransferDetail> terminalTransferDetailListsRJPOS = new ArrayList<>();


        for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailList) {
            if (terminalTransferDetail.getPlatformType().toString().equals(TerminalPlatformType.POS.getValue().toString()) || terminalTransferDetail.getPlatformType().toString().equals(TerminalPlatformType.ZHPOS.getValue().toString())) {
                String platformType = disBusCode(terminalTransferDetail);
                if (!(platformType.equals(PlatformType.POS.getValue()) || platformType.equals(PlatformType.ZPOS.getValue()) || platformType.equals(PlatformType.ZHPOS.getValue()) || platformType.equals(PlatformType.SSPOS.getValue()))) {
                    log.info("您的机构码不属于pos平台请选择：原：" + terminalTransferDetail.getOriginalOrgId() + "目标：" + terminalTransferDetail.getGoalOrgId());
                    throw new MessageException("您的机构码不属于pos平台请选择：原：" + terminalTransferDetail.getOriginalOrgId() + "目标：" + terminalTransferDetail.getGoalOrgId());
                }
                terminalTransferDetailListsPos.add(terminalTransferDetail);
            } else if (terminalTransferDetail.getPlatformType().compareTo(TerminalPlatformType.MPOS.getValue()) == 0) {
                String platformType = disBusCode(terminalTransferDetail);
                if (!platformType.equals(PlatformType.MPOS.getValue())) {
                    log.info("您的机构码不属于手刷平台请选择：原：" + terminalTransferDetail.getOriginalOrgId() + "目标：" + terminalTransferDetail.getGoalOrgId());
                    throw new MessageException("您的机构码不属于手刷平台请选择：原：" + terminalTransferDetail.getOriginalOrgId() + "目标：" + terminalTransferDetail.getGoalOrgId());
                }
                terminalTransferDetailListsMpos.add(terminalTransferDetail);
            } else if (terminalTransferDetail.getPlatformType().compareTo(TerminalPlatformType.RDBPOS.getValue()) == 0) {
                String platformType = disBusCode(terminalTransferDetail);
                if (!platformType.equals(PlatformType.RDBPOS.getValue())) {
                    log.info("您的机构码不属于瑞大宝平台请选择：原：" + terminalTransferDetail.getOriginalOrgId() + "目标：" + terminalTransferDetail.getGoalOrgId());
                    throw new MessageException("您的机构码不属于瑞大宝平台请选择：原：" + terminalTransferDetail.getOriginalOrgId() + "目标：" + terminalTransferDetail.getGoalOrgId());
                }
                terminalTransferDetailListsRDBPOS.add(terminalTransferDetail);
            } else if (terminalTransferDetail.getPlatformType().compareTo(TerminalPlatformType.RJPOS.getValue()) == 0) {
                String platformType = disBusCode(terminalTransferDetail);
                if (!platformType.equals(PlatformType.RJPOS.getValue())) {
                    log.info("您的机构码不属于瑞+平台请选择：原：" + terminalTransferDetail.getOriginalOrgId() + "目标：" + terminalTransferDetail.getGoalOrgId());
                    throw new MessageException("您的机构码不属于瑞+平台请选择：原：" + terminalTransferDetail.getOriginalOrgId() + "目标：" + terminalTransferDetail.getGoalOrgId());
                }
                terminalTransferDetailListsRJPOS.add(terminalTransferDetail);
            }
        }
        /**
         * 规定必须一个平台提交
         */
        if (terminalTransferDetailList.size() > (terminalTransferDetailListsPos == null ? 0 : terminalTransferDetailListsPos.size())
                && terminalTransferDetailList.size() > (terminalTransferDetailListsMpos == null ? 0 : terminalTransferDetailListsMpos.size())
                && terminalTransferDetailList.size() > (terminalTransferDetailListsRDBPOS == null ? 0 : terminalTransferDetailListsRDBPOS.size())
                && terminalTransferDetailList.size() > (terminalTransferDetailListsRJPOS == null ? 0 : terminalTransferDetailListsRJPOS.size())) {
            log.info("划拨仅支持单品牌的提交");
            throw new MessageException("划拨仅支持单品牌的提交");
        }


        if (saveFlag.equals(SaveFlag.TJSP.getValue())) {
            //POS检验
            posCheck(terminalTransferDetailListsPos);
            //手刷校验
            mposCheck(terminalTransferDetailListsMpos);
            //手刷校验
            rdbposCheck(terminalTransferDetailListsRDBPOS);
            //瑞+检验
            rjposCheck(terminalTransferDetailListsRJPOS);

            return "提交成功";
        } else {
            return "保存成功";
        }
    }

    /**
     * pos检验
     *
     * @param terminalTransferDetailListsPos
     * @throws MessageException
     */
    public void posCheck(List<TerminalTransferDetail> terminalTransferDetailListsPos) throws MessageException {
        //检查提交数据是否可执行
        if (terminalTransferDetailListsPos != null && terminalTransferDetailListsPos.size() > 0) {
            AgentResult agentResult = null;
            JSONObject jsonObject;
            JSONObject data;
            try {
                agentResult = termMachineService.queryTerminalTransfer(terminalTransferDetailListsPos, TRANSFER_ZG_CHECK, terminalTransferMapper.selectByPrimaryKey(terminalTransferDetailListsPos.get(0).getTerminalTransferId()).getTaskId());
                jsonObject = JSONObject.parseObject(agentResult.getMsg());
                data = JSONObject.parseObject(String.valueOf(jsonObject.get("data")));
            } catch (Exception e) {
                log.error("POS获得检验结果异常");
                throw new MessageException("POS获得检验结果异常");
            }
            if (agentResult.isOK()) {
                String result_code = String.valueOf(data.get("result_code"));
                if (!"000000".equals(result_code)) {
                    log.info("调用POS接口查询验证接口返回异常");
                    throw new MessageException("调用POS接口查询验证接口返回异常");
                }
            } else {
                String result_msg = String.valueOf(data.get("result_msg"));
                if (result_msg.indexOf("TTD") != -1) {
                    String ttd = result_msg.substring(result_msg.indexOf("TTD"), Integer.valueOf(result_msg.indexOf("TTD")) + 26);
                    TerminalTransferDetail terminalTransferDetail = terminalTransferDetailMapper.selectByPrimaryKey(ttd);
                    if (terminalTransferDetail != null) {
                        log.info("POS未连通查询检测接口" + terminalTransferDetail.getSnBeginNum() + "--------" + terminalTransferDetail.getSnEndNum());
                        throw new MessageException("在区间" + terminalTransferDetail.getSnBeginNum() + "--------" + terminalTransferDetail.getSnEndNum() + "   " + result_msg);
                    } else {
                        log.info("POS未连通查询检测接口" + result_msg);
                        throw new MessageException(result_msg);
                    }
                }
                log.info("POS未连通查询检测接口" + result_msg);
                throw new MessageException(result_msg);
            }
        }
    }

    /**
     * 手刷检验
     *
     * @param terminalTransferDetailListsMpos
     * @throws MessageException
     */
    public void mposCheck(List<TerminalTransferDetail> terminalTransferDetailListsMpos) throws MessageException {
        if (terminalTransferDetailListsMpos != null && terminalTransferDetailListsMpos.size() > 0) {
            for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailListsMpos) {
                String originalOrgId = terminalTransferDetail.getOriginalOrgId().trim();
                Map<String, Object> mapAgentType = getAgentType(originalOrgId);
                /* Map<String, Object> platFromMap = terminalTransferMapper.queryPlatFrom(mapAgentType.get("BUS_PLATFORM").toString());*/
                terminalTransferDetail.setPlatFrom(mapAgentType.get("BUS_PLATFORM").toString());
            }

            AgentResult agentResult = null;
            try {
                agentResult = termMachineService.queryTerminalTransfer(terminalTransferDetailListsMpos, TRANSFER_SS_CX, terminalTransferMapper.selectByPrimaryKey(terminalTransferDetailListsMpos.get(0).getTerminalTransferId()).getTaskId());
            } catch (Exception e) {
                log.error("MPOS获得检验结果异常");
                throw new MessageException("MPOS获得检验结果异常");
            }
            if (agentResult.isOK()) {
                List<Map<String, Object>> mapList = (List<Map<String, Object>>) agentResult.getData();
                if (mapList == null || mapList.size() == 0) {
                    log.info("调用MPOS接口查询验证接口返回异常，返回结果为：" + mapList);
                    throw new MessageException("调用MPOS接口查询验证接口返回异常，返回结果为：" + mapList);
                }
                for (Map<String, Object> map : mapList) {
                    if ("code6".equals(map.get("code").toString())) {
                        continue;
                    } else {
                        log.info(map.get("startTerm").toString() + "-------" + map.get("endTerm").toString() + ":" + map.get("msg"));
                        throw new MessageException(map.get("startTerm").toString() + "-------" + map.get("endTerm").toString() + map.get("msg"));
                    }

                }

            } else {
                log.info("MPOS未连通查询检测接口：" + agentResult);
                throw new MessageException("MPOS未连通查询检测接口：" + agentResult);
            }
        }
    }

    /**
     * 瑞大宝检验
     *
     * @param terminalTransferDetailListsRDBPOS
     * @throws Exception
     */
    public void rdbposCheck(List<TerminalTransferDetail> terminalTransferDetailListsRDBPOS) throws Exception {
        if (terminalTransferDetailListsRDBPOS != null && terminalTransferDetailListsRDBPOS.size() > 0) {
            AgentResult agentResult = termMachineService.queryTerminalTransfer(terminalTransferDetailListsRDBPOS, TRANSFER_ZG_CHECK, terminalTransferMapper.selectByPrimaryKey(terminalTransferDetailListsRDBPOS.get(0).getTerminalTransferId()).getTaskId());
            if (agentResult.isOK()) {
            } else {
                log.info("RDBPOS未连通查询接口");
            }
        }
    }

    /**
     * 瑞+检验
     *
     * @param terminalTransferDetailListsRJPOS
     * @throws MessageException
     */
    public void rjposCheck(List<TerminalTransferDetail> terminalTransferDetailListsRJPOS) throws MessageException {
        if (terminalTransferDetailListsRJPOS != null && terminalTransferDetailListsRJPOS.size() > 0) {
            AgentResult agentResult = null;
            JSONObject jsonObject;
            JSONObject jsonData;
            List<Map<String, String>> datalist;
            try {
                agentResult = termMachineService.queryTerminalTransfer(terminalTransferDetailListsRJPOS, TRANSFER_ZG_CHECK, terminalTransferMapper.selectByPrimaryKey(terminalTransferDetailListsRJPOS.get(0).getTerminalTransferId()).getTaskId());
                log.info("RJ校验返回结果："+JSONObject.toJSON(agentResult));
                jsonObject = JSONObject.parseObject(agentResult.getMsg());
                jsonData = JSONObject.parseObject(String.valueOf(jsonObject.get("data")));
                datalist = (List<Map<String, String>>) JSONArray.parse(String.valueOf(jsonData.get("data")));
            } catch (Exception e) {
                log.error("RJPOS获得检验结果异常");
                throw new MessageException("RJPOS获得检验结果异常");
            }
            if (agentResult.isOK()) {
                for (Map<String, String> data : datalist) {
                    String serialNumberStatus = String.valueOf(data.get("serialNumberStatus"));
                    if (!"00".equals(serialNumberStatus)) {
                        log.info("调用RJ接口查询验证接口返回异常");
                        throw new MessageException("调用RJ接口查询验证接口返回异常");
                    }
                }
            } else {
                String respMsg = String.valueOf(jsonObject.get("respMsg"));
                log.info("RJ未连通查询检测接口" + respMsg);
                throw new MessageException("RJ未连通查询检测接口" + respMsg);
            }

        }
    }

    /**
     * 比较平台编码  并返回
     *
     * @param terminalTransferDetail
     * @return
     * @throws MessageException
     */
    public String disBusCode(TerminalTransferDetail terminalTransferDetail) throws MessageException {
        String originalOrgId = terminalTransferDetail.getOriginalOrgId().trim();
        String goalOrgId = terminalTransferDetail.getGoalOrgId().trim();
        Map<String, Object> mapAgentTypeOriginal = getAgentType(originalOrgId);
        Map<String, Object> platFromOriginalMap = terminalTransferMapper.queryPlatFrom(String.valueOf(mapAgentTypeOriginal.get("BUS_PLATFORM")));
        Map<String, Object> mapAgentTypeGoalOrgId = getAgentType(goalOrgId);
        Map<String, Object> platFromGoalOrgIdMap = terminalTransferMapper.queryPlatFrom(String.valueOf(mapAgentTypeGoalOrgId.get("BUS_PLATFORM")));
        if (!platFromOriginalMap.get("PLATFORM_TYPE").toString().equals(platFromGoalOrgIdMap.get("PLATFORM_TYPE").toString())) {
            log.info("您的机构码不属于同一种平台类型：原：" + originalOrgId + "目标：" + goalOrgId);
            throw new MessageException("您的机构码不属于同一种平台类型：原：" + originalOrgId + "目标：" + goalOrgId);
        }
        return platFromOriginalMap.get("PLATFORM_TYPE").toString();
    }

    /**
     * 如果输入的平台编号为智慧pos的处置方法
     *
     * @param terminalTransferDetailList
     * @throws MessageException
     * @Auth chenliang
     */
    public List<TerminalTransferDetail> judgeStartsWithS
    (List<TerminalTransferDetail> terminalTransferDetailList) throws MessageException {
        List<TerminalTransferDetail> terminalTransferDetails = new ArrayList<>();
        for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailList) {
            String originalOrgId = terminalTransferDetail.getOriginalOrgId().trim();
            String goalOrgId = terminalTransferDetail.getGoalOrgId().trim();
            BigDecimal platformType = terminalTransferDetail.getPlatformType();
            //原代理商
            if (platformType.compareTo(TerminalPlatformType.ZHPOS.getValue()) == 0) {
                log.info("原代理商业务平台智慧pos处理开始。。。。。。。。。。。。。。");
                String originalOrgIds = judgeStartsWithSUtil(originalOrgId, terminalTransferDetail);
                terminalTransferDetail.setOriginalOrgId(originalOrgIds);
                terminalTransferDetail.setOriginalOrgIdS(originalOrgId);
            } else {
                terminalTransferDetail.setOriginalOrgIdS("");
            }

            //目标代码商
            if (platformType.compareTo(TerminalPlatformType.ZHPOS.getValue()) == 0) {
                log.info("原代理商业务平台智慧pos处理开始。。。。。。。。。。。。。。");
                String goalOrgIds = judgeStartsWithSUtil(goalOrgId, terminalTransferDetail);
                terminalTransferDetail.setGoalOrgId(goalOrgIds);
                terminalTransferDetail.setGoalOrgIdS(goalOrgId);
            } else {
                terminalTransferDetail.setGoalOrgIdS("");
            }
            terminalTransferDetails.add(terminalTransferDetail);
        }
        return terminalTransferDetails;

    }

    @Override
    public String judgeStartsWithSUtil(String param, TerminalTransferDetail terminalTransferDetail) throws
            MessageException {
        FastMap fastMap = FastMap.fastMap("POS_PLAT_CODE", param);
        List<Map<String, Object>> terminalTransferOriginalOrgIdMaps = terminalTransferMapper.queryBusInfo(fastMap);
        if (terminalTransferOriginalOrgIdMaps == null || terminalTransferOriginalOrgIdMaps.size() != 1) {
            log.info("查询用户数据并不唯一:{}", JSONObject.toJSON(terminalTransferDetail));
            throw new MessageException("查询用户数据并不唯一:" + param);
        }
        Map<String, Object> terminalTransferOriginalOrgIdMap = terminalTransferOriginalOrgIdMaps.get(0);
        if (terminalTransferOriginalOrgIdMap == null || terminalTransferOriginalOrgIdMap.isEmpty()) {
            log.info("此用户并未找到对应的直签代理商信息，核对信息:{}", JSONObject.toJSON(terminalTransferDetail));
            throw new MessageException("此用户并未找到对应的直签代理商信息：原业务平台编码：" + param);
        } else {
            Map<String, Object> queryPlatFromMap = terminalTransferMapper.queryPlatFrom(String.valueOf(terminalTransferOriginalOrgIdMap.get("BUS_PLATFORM")));
            if (queryPlatFromMap.get("PLATFORM_TYPE") != null && "ZHPOS".equals(String.valueOf(queryPlatFromMap.get("PLATFORM_TYPE")))) {
                if (terminalTransferOriginalOrgIdMap.get("BUS_NUM") != null) {
                    return String.valueOf(terminalTransferOriginalOrgIdMap.get("BUS_NUM"));
                } else {
                    log.info("此用户并未找到对应的业务平台编码，核对信息:{}", JSONObject.toJSON(terminalTransferDetail));
                    throw new MessageException("此用户并未找到对应的业务平台编码：原业务平台编码：" + param);
                }
            } else {
                log.info("此用户并不是智慧pos平台用户，核对信息:{}", JSONObject.toJSON(terminalTransferDetail));
                throw new MessageException("此用户并不是智慧pos平台用户：原业务平台编码：" + param);
            }
        }
    }


    /**
     * 处理开始和后期的SN
     * chenliang
     *
     * @param snBeginNum
     * @param snEndNum
     * @return
     */
    //传入两个sn  返回其共同部分，以及相差区间。
    @Override
    public Map<String, Object> disposeSN(String snBeginNum, String snEndNum) throws MessageException {
        log.info("处理传入SN开始处理：开始SN：" + snBeginNum + "结束SN：" + snEndNum);
        Map<String, Object> map = new HashMap<>();

        try {
            String snBeginNumArr = snBeginNum;
            String snEndNumArr = snEndNum;
            if (snBeginNum.length() > 6) {

                snBeginNumArr = snBeginNum.substring(snBeginNum.length() - 6);
            }
            if (snEndNum.length() > 6) {

                snEndNumArr = snEndNum.substring(snEndNum.length() - 6);
            }
            String[] snBeginNumChar = snBeginNumArr.split("");
            String[] snEndNumChar = snEndNumArr.split("");
            StringBuffer sbBegin = new StringBuffer();
            for (int i = snBeginNumChar.length - 1; i >= 0; i--) {
                if (Character.isDigit(snBeginNumChar[i].charAt(0))) {
                    sbBegin.append(snBeginNumChar[i]);
                } else {
                    break;
                }
            }

            String sb1 = snBeginNum.replaceAll(sbBegin.reverse().toString(), "");

            StringBuffer sbEnd = new StringBuffer();
            for (int i = snEndNumChar.length - 1; i >= 0; i--) {
                if (Character.isDigit(snEndNumChar[i].charAt(0))) {
                    sbEnd.append(snEndNumChar[i]);
                } else {
                    break;
                }
            }
            String sb2 = snEndNum.replaceAll(sbEnd.reverse().toString(), "");
            map.put("sb", sb1);
            map.put("snBeginNum1", "".equals(sbBegin) ? "0" : sbBegin.toString());
            map.put("snEndNum1", "".equals(sbEnd) ? "0" : sbEnd.toString());

        } catch (StringIndexOutOfBoundsException e) {
            throw new MessageException("StringIndexOutOfBoundsException异常忽略");
        }
        return map;
    }


    /**
     * 终端划拨解锁
     * @param taskId
     * @param serialNumber
     * @param type
     * @return
     */
    public AgentResult terminalTransferunlock(String taskId, String serialNumber, String type){
        AgentResult agentResult = null ;
        try {
            log.info("划拨解锁开始："+taskId +"明细id："+serialNumber);
            agentResult = termMachineService.terminalTransferunlock(taskId,null, type);
            log.info("划拨解锁结束："+JSONObject.toJSON(agentResult));
        }catch (Exception e){
            log.error("划拨解锁异常："+taskId +"明细id："+serialNumber);
            e.printStackTrace();
            agentResult = AgentResult.fail("划拨解锁异常");
        }

        /**
         * 调用接口远程
         */
        if (agentResult != null) {
            if (agentResult.isOK()) {
                JSONObject jsonObject = JSONObject.parseObject(agentResult.getMsg());
                String respCode = String.valueOf(jsonObject.get("respCode"));
                JSONObject JSONObjectData = JSONObject.parseObject(String.valueOf(jsonObject.get("data")));
                JSONObject data = JSONObject.parseObject(String.valueOf(JSONObjectData.get("data")));
                if ("000000".equals(respCode)) {
                    String code = String.valueOf(data.get("code"));
                    String msg = String.valueOf(data.get("msg"));
                    if ("0".equals(code)) {
                        agentResult = AgentResult.ok(msg);
                    } else if ("1".equals(code)) {
                        agentResult = AgentResult.fail(msg);
                    }
                } else {
                    agentResult = AgentResult.fail("划拨解锁处理失败");
                }

            } else {
                agentResult = AgentResult.fail("划拨解锁调用接口失败");
            }
        }else {
            agentResult = AgentResult.ok();
        }
        return agentResult;
    }


    /**
     * 终端划拨重新划拨
     * @param id 明细Id
     * @return
     */
    @Override
    public AgentResult adjustAgain(String id) {

        AgentResult agentResult = null ;
        try {
            log.info("划拨重新开始明细id："+id);
            agentResult = termMachineService.terminalTransferunlock(terminalTransferMapper.selectByPrimaryKey(terminalTransferDetailMapper.selectByPrimaryKey(id).getTerminalTransferId()).getTaskId(),id, String.valueOf(terminalTransferDetailMapper.selectByPrimaryKey(id).getPlatformType()));
            log.info("划拨解锁结束："+JSONObject.toJSON(agentResult));
        }catch (Exception e){
            log.error("划拨重新发起异常明细id："+id);
            e.printStackTrace();
            agentResult = AgentResult.fail("划拨重新发起异常");
        }
        /**
         * 调用接口远程
         */
        if (agentResult != null) {
            if (agentResult.isOK()) {
                JSONObject jsonObject = JSONObject.parseObject(agentResult.getMsg());
                String respCode = String.valueOf(jsonObject.get("respCode"));
                JSONObject JSONObjectData = JSONObject.parseObject(String.valueOf(jsonObject.get("data")));
                JSONObject data = JSONObject.parseObject(String.valueOf(JSONObjectData.get("data")));
                if ("000000".equals(respCode)) {
                    String resultCode = String.valueOf(data.get("resultCode"));
                    String resultMsg = String.valueOf(data.get("resultMsg"));
                    if ("00".equals(resultCode)) {
                        agentResult = AgentResult.ok(resultMsg);
                    } else if ("01".equals(resultCode)) {
                        agentResult = AgentResult.fail(resultMsg);
                    }
                } else {
                    agentResult = AgentResult.fail("划拨重新发起失败");
                }

            } else {
                agentResult = AgentResult.fail("划拨重新发起调用接口失败");
            }
        }else {
            agentResult = AgentResult.ok();
        }
        return agentResult;

    }


    /***
     * @Description: 获取AgentType
     * @Param: orgId 代理商id
     * @Author: chen_Liang
     * @Date: 2019/7/25
     */
    @Override
    public Map<String, Object> getAgentType(String orgId) {

        return terminalTransferMapper.getAgentType(orgId);

    }

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


