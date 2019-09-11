package com.ryx.credit.cms.controller.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.Organization;
import com.ryx.credit.pojo.admin.vo.AgentBusInfoVo;
import com.ryx.credit.pojo.admin.vo.AgentColinfoVo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.CapitalVo;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.service.order.OrganizationService;
import com.ryx.credit.service.pay.LivenessDetectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by cx on 2018/6/6.
 * AgentUpdateApyController
 */
@RequestMapping("agentUpdateApy")
@Controller
public class AgentUpdateApyController extends BaseController {


    private static Logger logger = LoggerFactory.getLogger(AgentUpdateApyController.class);

    @Autowired
    private AgentQueryService agentQueryService;
    @Autowired
    private DateChangeReqService dateChangeReqService;
    @Autowired
    private AgentAssProtocolService agentAssProtocolService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private AgentColinfoService agentColinfoService;
    @Autowired
    private AColinfoPaymentService colinfoPaymentService;
    @Autowired
    private PlatFormService platFormService;
    @Autowired
    private ApaycompService apaycompService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private LivenessDetectionService livenessDetectionService;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private AgentService agentService;


    /**
     * /agentUpdateApy/agentBaseUpdateApyView
     *
     * @param id
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"agentBaseUpdateApyView"})
    public String agentByid(String id, Model model, HttpServletRequest request) {
        AgentBusInfo agentBusInfo = agentBusinfoService.getById(id);
        selectAll(agentBusInfo.getAgentId(), model, request, agentBusInfo);
//        model.addAttribute("agentBusInfos", agentBusInfo);
        List<PayComp> payCompList = apaycompService.recCompList();
        request.setAttribute("payCompList", payCompList);
        request.setAttribute("appEditFlag", "1");
        List<Organization> platIdOrganList = platFormService.queryByOrganName(agentBusInfo.getBusPlatform(), null, null);
        request.setAttribute("platIdOrganList", platIdOrganList);
        return "agent/business/agentUpdateApyBuss";
    }


    /**
     * /agentUpdateApy/agentBaseUpdateApyView
     *
     * @param id
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"agentByidForUpdateColInfoView"})
    public String agentByidForUpdateColInfo(String id, Model model, HttpServletRequest request) {
        selectAll(id, model, request, null);
        return "agent/agentUpdateColInfoApy";
    }


    /**
     * 代理商入网修改申请
     * /agentUpdateApy/agentBaseUpdateApy
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"agentBaseUpdateApy"}, method = RequestMethod.POST)
    public ResultVO agentBaseUpdateApy(HttpServletRequest request, HttpServletResponse response,
                                       @RequestBody AgentVo agentVo) {
        try {
            Map olddata = selectBussess(agentVo.getAgent().getId(), null, request, null);
            if (agentVo != null) {
                if (agentVo.getAgent() != null) {
                    Map<String,String> stringMap = new HashMap<String, String>();
                    stringMap.put("agName", agentVo.getAgent().getAgName());
                    List<Agent> agentList = agentService.getListByORGAndId(stringMap);
                    if (agentList.size() == 1) {
                        if (!agentVo.getAgent().getId().equals(agentList.get(0).getId())) {
                            throw new ProcessException("代理商名称重复");
                        }
                    } else if(agentList.size() > 1) {
                        throw new ProcessException("代理商名称重复");
                    }
                }
                //需要验证法人姓名和法人身份证是否存在
                Agent agent = agentVo.getAgent();
                if (null==agent){
                    logger.info("结算卡信息不完整,请申请基本信息修改(基础信息)");
                    throw new ProcessException("结算卡信息不完整,请申请基本信息修改");
                }else{
                    if (StringUtils.isBlank(agent.getAgLegalCernum())){
                        logger.info("法人证件号码,请申请基本信息修改");
                        throw new ProcessException("法人证件号码,请申请基本信息修改");
                    }if (StringUtils.isBlank(agent.getAgLegal())){
                        logger.info("法人姓名不完整,请申请基本信息修改");
                        throw new ProcessException("法人姓名不完整,请申请基本信息修改");
                    }
                }
                //需要查询是否存在收款账户记录
                AgentColinfo agentColinfo = agentColinfoService.selectByAgentId(agentVo.getAgent().getId());
                if (null==agentColinfo){
                    logger.info("结算卡信息不完整,请申请基本信息修改");
                    throw new ProcessException("结算卡信息不完整,请申请基本信息修改");
                }else{
                    if(null==agentColinfo.getCloType()){
                        logger.info("收款账户类型不完整,请申请基本信息修改");
                        throw new ProcessException("收款账户类型不完整,请申请基本信息修改");
                    }if (StringUtils.isBlank(agentColinfo.getCloRealname())){
                        logger.info("收款账户名不完整,请申请基本信息修改");
                        throw new ProcessException("收款账户名不完整,请申请基本信息修改");
                    }if (StringUtils.isBlank(agentColinfo.getCloBank())){
                        logger.info("收款开户总行不完整,请申请基本信息修改");
                        throw new ProcessException("收款开户总行不完整,请申请基本信息修改");
                    }if (StringUtils.isBlank(agentColinfo.getCloBankBranch())){
                        logger.info("收款开户行支行不完整,请申请基本信息修改");
                        throw new ProcessException("收款开户行支行不完整,请申请基本信息修改");
                    }if (StringUtils.isBlank(agentColinfo.getCloBankAccount())){
                        logger.info("收款账号不完整,请申请基本信息修改");
                        throw new ProcessException("收款账号不完整,请申请基本信息修改");
                    }if (StringUtils.isBlank(agentColinfo.getBranchLineNum())){
                        logger.info("支行联行号不完整,请申请基本信息修改");
                        throw new ProcessException("支行联行号不完整,请申请基本信息修改");
                    }if (StringUtils.isBlank(agentColinfo.getAllLineNum())){
                        logger.info("总行联行号不完整,请申请基本信息修改");
                        throw new ProcessException("总行联行号不完整,请申请基本信息修改");
                    }if (null==agentColinfo.getCloTaxPoint()){
                        logger.info("税点不完整,请申请基本信息修改");
                        throw new ProcessException("税点不完整,请申请基本信息修改");
                    }if (null==agentColinfo.getCloInvoice()){
                        logger.info("开具分润发票不完整,请申请基本信息修改");
                        throw new ProcessException("开具分润发票不完整,请申请基本信息修改");
                    }if (StringUtils.isBlank(agentColinfo.getBankRegion())){
                        logger.info("开户行地区不完整,请申请基本信息修改");
                        throw new ProcessException("开户行地区不完整,请申请基本信息修改");
                    }
                }
                List<AgentBusInfoVo> busInfoVos = agentVo.getBusInfoVoList();
//                Set<String> resultSet = new HashSet<>();
                if (busInfoVos != null) {
                    for (AgentBusInfoVo busInfoVo : busInfoVos) {
                        if (StringUtils.isBlank(busInfoVo.getBusPlatform())){
                            logger.info("请选择业务平台");
                            throw new ProcessException("请选择业务平台");
                        }
                        if (OrgType.zQ(busInfoVo.getBusType())) {
                            if (StringUtils.isBlank(busInfoVo.getBusParent()))
                                throw new ProcessException("直签上级不能为空");
                        }
                        //代理商选择上级代理商时添加限制 不能选择同级别代理商为上级
                        if (StringUtils.isNotBlank(busInfoVo.getBusParent())) {
                            //获取上级代理商类型
                            AgentBusInfo busInfo = agentBusinfoService.getById(busInfoVo.getBusParent());
                            if (busInfoVo.getBusType().equals(BusType.ZQ.key) || busInfoVo.getBusType().equals(BusType.ZQBZF.key) || busInfoVo.getBusType().equals(BusType.ZQZF.key)) {
                                if (busInfo.getBusType().equals(BusType.ZQ.key) || busInfo.getBusType().equals(BusType.ZQZF.key) || busInfo.getBusType().equals(BusType.ZQBZF.key)) {
                                    throw new ProcessException("不能选择同级别的代理商为上级，请重新选择");
                                }
                            }
                            if (busInfoVo.getBusType().equals(BusType.YDX.key)) {
                                if (busInfo.getBusType().equals(BusType.ZQ.key) || busInfo.getBusType().equals(BusType.YDX.key)
                                        || busInfo.getBusType().equals(BusType.ZQZF.key) || busInfo.getBusType().equals(BusType.ZQBZF.key)) {
                                    throw new ProcessException("不能选择同级别的代理商为上级，请重新选择");
                                }
                            }
                            if (busInfoVo.getBusType().equals(BusType.JGYD.key)) {
                                if (!busInfo.getBusType().equals(BusType.JG.key)) {
                                    throw new ProcessException("不能选择同级别的代理商为上级，请重新选择");
                                }
                            }
                        }

                        if(StringUtils.isNotBlank(busInfoVo.getBusType()) && !busInfoVo.getBusType().equals(BusType.JG.key) && !busInfoVo.getBusType().equals(BusType.BZYD.key)){
                            if (StringUtils.isBlank(busInfoVo.getBusParent())){
                                throw new ProcessException("请选择上级代理");
                            }
                            if (StringUtils.isBlank(busInfoVo.getBusRiskParent())){
                                throw new ProcessException("请选择风险承担所属代理商");
                            }
                            if (StringUtils.isBlank(busInfoVo.getBusContact())){
                                throw new ProcessException("请填写业务联系人");
                            }
                            if (StringUtils.isBlank(busInfoVo.getBusContactMobile())){
                                throw new ProcessException("请填写业务联系电话");
                            }
                            if (StringUtils.isBlank(busInfoVo.getBusContactPerson())){
                                throw new ProcessException("请填写业务对接人");
                            }
                        }
                        PlatForm platForm = platFormService.selectByPlatformNum(busInfoVo.getBusPlatform());
                        if (StringUtils.isNotBlank(busInfoVo.getId()))
//                        resultSet.add(platForm.getPlatformType());
                            if (null != platForm) {
                                busInfoVo.setBusPlatformType(platForm.getPlatformType());
                            }
                        if (StringUtils.isNotBlank(busInfoVo.getId()) && StringUtils.isNotBlank(busInfoVo.getBusParent())) {
                            AgentBusInfo agentBusInfo = agentBusinfoService.getById(busInfoVo.getBusParent());
                            if (agentBusInfo != null && StringUtils.isNotBlank(busInfoVo.getBusPlatform())) {
                                if (!agentBusInfo.getBusPlatform().equals(busInfoVo.getBusPlatform())) {
                                    return ResultVO.fail("业务平台上级和业务平台类型不匹配，请检查" + busInfoVo.getBusNum() + "业务上级");
                                }
                            }
                        }
                        if (null != busInfoVo.getBusPlatform()) {
                            PlatformType platformType = platFormService.byPlatformCode(busInfoVo.getBusPlatform());
                            if (null != platformType) {
                                if (platformType.code.equals(PlatformType.POS.code) || platformType.code.equals(PlatformType.ZPOS.code)) {
                                    if (StringUtils.isNotBlank(busInfoVo.getBusNum())) {
                                        if (StringUtils.isBlank(busInfoVo.getBusLoginNum())) {
                                            logger.info("请填写平台登录账号");
                                            return ResultVO.fail("请填写平台登录账号");
                                        }
                                    }
                                }
                            }
                        }
                        AgentBusInfo agentBusInfo = agentBusinfoService.getById(busInfoVo.getId());
                        busInfoVo.setDebitRateLower(agentBusInfo.getDebitRateLower());
                        busInfoVo.setDebitCapping(agentBusInfo.getDebitCapping());
                        busInfoVo.setDebitAppearRate(agentBusInfo.getDebitAppearRate());
                        busInfoVo.setCreditRateFloor(agentBusInfo.getCreditRateFloor());
                        busInfoVo.setCreditRateCeiling(agentBusInfo.getCreditRateCeiling());
                        //判断所选机构是否属于所选平台（机构编号&业务平台）
                        if (StringUtils.isNotBlank(busInfoVo.getOrganNum())) {
                            List<Organization> organList = organizationService.selectOrganization(busInfoVo.getOrganNum());
                            for (Organization organization : organList) {
                                if (!organization.getPlatId().contains(agentBusInfo.getBusPlatform())) {
                                    throw new ProcessException("所选机构不属于该业务平台");
                                }
                                busInfoVo.setOrganNum(organization.getOrgId());
                            }
                        }
                    }
//                    if(resultSet.size()>1){
//                        throw new ProcessException("不能同时提交pos和手刷平台");
//                    }
                }
            }
            String data = JSONObject.toJSONString(agentVo);
            String old = JSONObject.toJSONString(olddata);
            return dateChangeReqService.dateChangeReqIn(data, old, agentVo.getAgent().getId(), DataChangeApyType.DC_Agent.name(), getUserId() + "");
        } catch (Exception e) {
            logger.info("代理商修改错误{}{}{}", getUserId() + "", agentVo.getAgent().getId(), e.getMessage());
            e.printStackTrace();
            return ResultVO.fail(e.getMessage());
        }

    }

    /**
     * 代理商入网收款信息修改
     * /agentUpdateApy/agentColInfoUpdateApy
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"agentColInfoUpdateApy"}, method = RequestMethod.POST)
    public ResultVO agentColInfoUpdateApy(HttpServletRequest request, HttpServletResponse response,
                                          @RequestBody AgentVo agentVo) {
        try {
            Agent a_agent = agentVo.getAgent();
            List<AgentColinfoVo> agentColinfoVoList = agentVo.getColinfoVoList();
            for (AgentColinfoVo agentColinfoVo : agentColinfoVoList) {
                if (null != agentColinfoVo.getCloTaxPoint() && agentColinfoVo.getCloTaxPoint().compareTo(new BigDecimal(1)) >= 0) {
                    throw new ProcessException("税点不能大于1");
                }


                //银行卡扫描件
                boolean isHaveYHKSMJ = false;
                //开户许可证
                boolean isHaveKHXUZ = false;
                //一般纳税人证明
                boolean isHaveYBNSRZM = false;
                //账户变更表
                boolean isHaveZHBGB = false;
                //结算人身份证
                boolean isHaveJSRSFZ = false;
                //非法人授权书
                boolean isHaveFFRSQS = false;

                //添加新的附件
                List<String> fileIdList = agentColinfoVo.getColinfoTableFile();
                if (fileIdList != null) {
                    for (String fileId : fileIdList) {

                        Attachment attachment = agentColinfoService.selectAttachment(fileId);
                        if (attachment != null) {
                            if (AttDataTypeStatic.YHKSMJ.code.equals(attachment.getAttDataType() + "")) {
                                isHaveYHKSMJ = true;
                            }
                            if (AttDataTypeStatic.KHXUZ.code.equals(attachment.getAttDataType() + "")) {
                                isHaveKHXUZ = true;
                            }
                            if (AttDataTypeStatic.YBNSRZM.code.equals(attachment.getAttDataType() + "")) {
                                isHaveYBNSRZM = true;
                            }

                            if (AttDataTypeStatic.ZHBGB.code.equals(attachment.getAttDataType() + "")) {
                                isHaveZHBGB = true;
                            }
                            if (AttDataTypeStatic.JSRSFZ.code.equals(attachment.getAttDataType() + "")) {
                                isHaveJSRSFZ = true;
                            }
                            if (AttDataTypeStatic.FFRSQS.code.equals(attachment.getAttDataType() + "")) {
                                isHaveFFRSQS = true;
                            }
                        }
                    }
                }
                //对公并且税点等于0.06一般纳税人证明必填
                if (agentColinfoVo.getCloType().compareTo(new BigDecimal("1")) == 0 && agentColinfoVo.getCloTaxPoint().compareTo(new BigDecimal("0.06")) == 0) {
                    if (!isHaveYBNSRZM) {
                        throw new MessageException("请添加一般纳税人证明");
                    }
                }
                if (agentColinfoVo.getCloType().compareTo(new BigDecimal("2")) == 0) {//对私
                    if (!isHaveYHKSMJ) {
                        throw new MessageException("请添加银行卡扫描件");
                    }
                }
                if (agentColinfoVo.getCloType().compareTo(new BigDecimal("1")) == 0) {//对公
                    if (!isHaveKHXUZ) {
                        throw new MessageException("请添加开户许可证");
                    }
                }

                //收款账户对私时做校验
                String agentName = agentVo.getAgent().getAgName();
                String agLegalName = agentVo.getAgent().getAgLegal();
                String trueName = agentColinfoVo.getCloRealname();
                String certNo = agentColinfoVo.getAgLegalCernum();
                if (StringUtils.isBlank(trueName)) {
                    throw new ProcessException("请填写结算卡户主姓名");
                }
                AgentColinfo agentColinfo = agentColinfoService.selectByAgentId(a_agent.getId());
                BigDecimal preColtype=new BigDecimal(0);
                if (null!=agentColinfo){
                     preColtype=agentColinfo.getCloType();
                }else {
                    preColtype=agentColinfoVo.getCloType();
                }
                if (agentColinfoVo.getCloType().compareTo(new BigDecimal(2)) == 0) {
                    //对私时 收款账户名与法人姓名一致时 把法人身份证号拷贝到户主身份证号并进行认证
                    if (agLegalName.equals(trueName)) {
//                      收款账户名与法人姓名一致时      提示上传法人身份证    变更表
                        if(preColtype.compareTo(agentColinfoVo.getCloType())!=0){
                            if (!isHaveZHBGB) {
                                throw new MessageException("请添加账户变更表");
                            }
                            if (!isHaveJSRSFZ) {
                                throw new MessageException("请添加结算人身份证");
                            }
                        }

                        Agent agent = agentVo.getAgent();
                        //法人身份证未空，必须输入结算卡户主身份证
                        if(StringUtils.isBlank(agent.getAgLegalCernum())){
                            if(StringUtils.isBlank(certNo)){
                                throw new ProcessException("法人身份证未获取到，请输入结算卡户主身份证号");
                            }
                        }
                        if(StringUtils.isBlank(agent.getAgLegalCernum()) && StringUtils.isBlank(certNo))
                            throw new ProcessException("请输入结算卡户主身份证");
                        if(StringUtils.isNotBlank(certNo)) {
                            agentColinfoVo.setAgLegalCernum(certNo);
                        }
                        if(StringUtils.isBlank(agentColinfoVo.getAgLegalCernum())){
                            agentColinfoVo.setAgLegalCernum(agent.getAgLegalCernum());
                        }
                    }else{
                        //收款账户名与法人姓名不一致时：提示上传变更表、非法人授权书   结算人身份证
                        if(preColtype.compareTo(agentColinfoVo.getCloType())!=0){
                            if (!isHaveZHBGB) {
                                throw new MessageException("请添加账户变更表");
                            }
                            if (!isHaveFFRSQS) {
                                throw new MessageException("请添加非法人授权书");
                            }
                            if (!isHaveJSRSFZ) {
                                throw new MessageException("请添加结算人身份证");
                            }
                        }

                    }
                    //三要素认证
                    if (StringUtils.isNotBlank(certNo)) {
                        //三要素认证
                        AgentResult result = livenessDetectionService.threeElementsCertificationDetection(trueName, certNo, getUserId()+"",agentColinfoVo.getCloBankAccount());
                        if (!result.isOK()) {
                            throw new ProcessException("收款账户姓名身份证三要素认证失败");
                        }
                    } else {
                        throw new ProcessException("请输入收款账户名相对应的户主证件号");
                    }

                }
                //对公时 判断收款账户名是否与代理商名称一致 不一致则抛异常提示信息
                if (agentColinfoVo.getCloType().compareTo(new BigDecimal(1)) == 0) {
                    if (agentName.equals(trueName)) {
                        agentColinfoVo.setAgLegalCernum(agentVo.getAgent().getAgLegalCernum());
                    } else if (!agentName.equals(trueName)) {
                        throw new ProcessException("收款账户名与代理商名称不一致");
                    }
                }
                agentColinfoService.checkColInfo(agentColinfoVo);
                List<AColinfoPayment> aColinfoPayments = colinfoPaymentService.queryConlifoPaymentList(agentVo.getAgent().getId(), agentColinfoVo.getCloBankAccount());
                if (null == aColinfoPayments) {
                    logger.info("收款账号添加:{}", "收款账号添加附件关系失败");
                    throw new ProcessException("收款账号查询失败");
                }
                if (aColinfoPayments.size() > 2) {
                    logger.info("收款账号添加:{}", "收款账号添加附件关系失败");
                    throw new ProcessException("收款账号已验证超过2次请更换银行卡！");
                }
            }
            String data = JSONObject.toJSONString(agentVo);
            String old = JSONObject.toJSONString(selectAll(agentVo.getAgent().getId(), null, request, null));
            return dateChangeReqService.dateChangeReqIn(data, old, agentVo.getAgent().getId(), DataChangeApyType.DC_Colinfo.name(), getUserId() + "");
        } catch (ProcessException e) {
            logger.info("代理商修改错误{}{}{}", getUserId() + "", agentVo.getAgent().getId(), e.getMessage());
            e.printStackTrace();
            return ResultVO.fail(e.getMsg());
        } catch (Exception e) {
            logger.info("代理商修改错误{}{}{}", getUserId() + "", agentVo.getAgent().getId(), e.getMessage());
            e.printStackTrace();
            return ResultVO.fail(e.getMessage());
        }

    }


    /**
     * @param id
     * @param model
     * @param request
     * @return
     */
    public Map selectAll(String id, Model model, HttpServletRequest request, AgentBusInfo editAgentBusInfo) {
        Agent agent = agentQueryService.informationQuery(id);
        List<AgentColinfo> agentColinfos = agentQueryService.proceedsQuery(id);
        List<Capital> capitals = agentQueryService.paymentQuery(id);
        List<AgentContract> agentContracts = agentQueryService.compactQuery(id);
        List<AgentBusInfo> agentBusInfos = new ArrayList<>();
        if (null == editAgentBusInfo) {
            agentBusInfos = agentQueryService.businessQuery(id);
        } else {
            agentBusInfos.add(editAgentBusInfo);
        }
        List<Attachment> attachment = agentQueryService.accessoryQuery(id, AttachmentRelType.Agent.name());

        List<String> busIds = new ArrayList<>();
        for (AgentBusInfo agentBusInfo : agentBusInfos) {
            busIds.add(agentBusInfo.getId());
        }
        List<AssProtoColRel> assProtoColRelList = new ArrayList<>();
        if (busIds.size() > 0) {
            assProtoColRelList = agentAssProtocolService.queryProtoColByBusIds(busIds);
        }
        List<AssProtoCol> ass = agentAssProtocolService.queryProtocol(null, null);
        if (model != null) {
            model.addAttribute("assProtoColRelList", assProtoColRelList);
            model.addAttribute("ass", ass);
            model.addAttribute("agent", agent);
            model.addAttribute("agentColinfos", agentColinfos);
            model.addAttribute("capitals", capitals);
            model.addAttribute("agentContracts", agentContracts);
            model.addAttribute("agentBusInfos", agentBusInfos);
            model.addAttribute("attachment", attachment);
        }
        optionsData(request);
        return FastMap.fastMap("agent", agent)
                .putKeyV("capitalVoList", capitals)
                .putKeyV("contractVoList", agentContracts)
                .putKeyV("busInfoVoList", agentBusInfos)
                .putKeyV("colinfoVoList", agentColinfos);
    }


    public Map selectBussess(String id, Model model, HttpServletRequest request, AgentBusInfo editAgentBusInfo) {
        Agent agent = agentQueryService.informationQuery(id);
        List<AgentBusInfo> agentBusInfos = new ArrayList<>();
        if (null == editAgentBusInfo) {
            agentBusInfos = agentQueryService.businessQuery(id);
        } else {
            agentBusInfos.add(editAgentBusInfo);
        }
        List<Attachment> attachment = agentQueryService.accessoryQuery(id, AttachmentRelType.Agent.name());

        List<String> busIds = new ArrayList<>();
        for (AgentBusInfo agentBusInfo : agentBusInfos) {
            busIds.add(agentBusInfo.getId());
        }
        List<AssProtoColRel> assProtoColRelList = new ArrayList<>();
        if (busIds.size() > 0) {
            assProtoColRelList = agentAssProtocolService.queryProtoColByBusIds(busIds);
        }
        List<AssProtoCol> ass = agentAssProtocolService.queryProtocol(null, null);
        if (model != null) {
            model.addAttribute("assProtoColRelList", assProtoColRelList);
            model.addAttribute("ass", ass);
            model.addAttribute("agent", agent);
            model.addAttribute("agentBusInfos", agentBusInfos);
            model.addAttribute("attachment", attachment);
        }
        optionsData(request);
        return FastMap.fastMap("agent", agent)
                .putKeyV("busInfoVoList", agentBusInfos);
    }

}
