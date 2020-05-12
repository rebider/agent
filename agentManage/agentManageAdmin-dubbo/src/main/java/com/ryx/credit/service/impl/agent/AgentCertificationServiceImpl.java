package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.result.Result;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentCertificationMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentCertifiVo;
import com.ryx.credit.pojo.admin.vo.AgentFreezePort;
import com.ryx.credit.service.IResourceService;
import com.ryx.credit.service.agent.AgentCertificationService;
import com.ryx.credit.service.agent.BusinessCAService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import org.apache.poi.ss.format.CellTextFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
/**
 * @program: agentManage
 *
 * @description: 代理商认证信息
 *
 * @author: zxb
 *
 * @create: 2019-09-18 13:34
 **/
@Service("agentCertificationService")
public class AgentCertificationServiceImpl extends AgentFreezeServiceImpl implements AgentCertificationService {
    private static Logger logger = LoggerFactory.getLogger(AgentCertificationServiceImpl.class);
    @Autowired
    private AgentCertificationMapper agentCertificationMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private BusinessCAService businessCAService;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private DictOptionsService dictOptionsService;

    @Override
    public PageInfo agentCertifiDetails(Page page, Map map) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(agentCertificationMapper.queryAgentCerDetails(map,page));
        pageInfo.setTotal(agentCertificationMapper.queryAgentCerDetailsCount(map));//
//        pageInfo.setRows(agentCertificationMapper.queryAgentCerDetailsSingle(map,page));
//        pageInfo.setTotal(agentCertificationMapper.queryAgentCerDetailsSingleCount(map));


        return pageInfo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public AgentResult addAgentCertifis(List<Agent> agents,Long userId) {
        logger.info("添加待认证代理商信息");
        StringBuffer resStr = new StringBuffer();
        try {
           List<AgentCertification> agentCertifications = new ArrayList<>();

           agents.forEach((cer)->{

               FastMap par = FastMap.fastMap("id",cer.getId());
               AgentCertification agentCertification = agentCertificationMapper.queryCers(par);
                if (agentCertification!=null){
                    resStr.append(agentCertification.getOrgAgName()).append(",");
                    return;
                }
               AgentCertification agentCer = new AgentCertification();
               ZoneId zoneId = ZoneId.systemDefault();
               ZonedDateTime zdt = LocalDateTime.now().atZone(zoneId);//Combines this date-time with a time-zone to create a  ZonedDateTime.
               Date date = Date.from(zdt.toInstant());

               agentCer.setId(idService.genIdInTran(TabId.a_agent_certification));
               agentCer.setAgentId(cer.getId());
               agentCer.setReqRegNo("");
               agentCer.setReqEntName(cer.getAgName());
               agentCer.setReqCerTm(date);
               agentCer.setReqCerUser(String.valueOf(userId));
               agentCer.setCerNum(new BigDecimal(agentCertificationMapper.queryAgentCerDetailsCount(par)+1));
               agentCer.setCerProStat(Status.STATUS_0.status);
               agentCer.setOrgAgName(cer.getAgName());
               agentCer.setOrgAgNature(cer.getAgNature());
               agentCer.setOrgAgCapital(cer.getAgCapital());
               agentCer.setOrgAgBusLic(cer.getAgBusLic());
               agentCer.setOrgAgBusLicb(cer.getAgBusLicb());
               agentCer.setOrgAgBusLice(cer.getAgBusLice());
               agentCer.setOrgAgLegal(cer.getAgLegal());
               agentCer.setOrgAgRegAdd(cer.getAgRegAdd());
               agentCer.setOrgAgBusScope(cer.getAgBusScope());
               agentCertifications.add(agentCer);
           });
           if (agentCertifications.size()>0){
               int insertBatch = agentCertificationMapper.insertBatch(agentCertifications);
               if (insertBatch > 0){
                   if ("".equals(resStr.toString())||null==resStr)
                       return AgentResult.ok();
                   return new AgentResult(200,"存在未处理完成信息:"+resStr.toString()+"未再次添加",null);
               }else {
                   return AgentResult.fail("未添加认证数据");
               }
           }else {
               return new AgentResult(200,"存在未处理完成信息:"+resStr.toString()+"未再次添加",null);
           }

       }catch (Exception e){
            logger.error(e.toString());
          return  new AgentResult(500, "系统异常", e.toString());
       }

    }

    @Override
    public PageInfo agentCertifiListView(Page page, Map map) {

        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(agentCertificationMapper.queryAgentCerList(map,page));
        pageInfo.setTotal(agentCertificationMapper.queryAgentCerListCount(map));

        return pageInfo;
    }

    @Override
    public List<AgentCertification> fetchFhData() {
        logger.info("查询未认证的的商户信息开始");
        AgentCertificationExample agentCertificationExample = new AgentCertificationExample();
        AgentCertificationExample.Criteria criteria = agentCertificationExample.createCriteria();
        criteria.andCerProStatEqualTo(Status.STATUS_0.status);
        agentCertificationExample.setOrderByClause(" REQ_CER_TM asc ");
        agentCertificationExample.setPage(new Page(0,100));
        List<AgentCertification> agentCertifications = agentCertificationMapper.selectByExample(agentCertificationExample);
        logger.info("查询未认证的的商户信息结束");
        agentCertifications.forEach(cer->{
            cer.setCerProStat(Status.STATUS_1.status);
            if (agentCertificationMapper.updateByPrimaryKeySelective(cer)!=1){
                logger.info("更新未认证的的商户信息失败{},商户id{}",cer.getId(),cer.getAgentId());
            };
        });
        return agentCertifications;
    }

    @Override
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRES_NEW)
    public AgentResult processData(Agent orgagent,String id,String orgId) throws MessageException {
        Agent agent = agentMapper.selectByAgent(orgagent);
        AgentCertification agentCertification = agentCertificationMapper.selectByPrimaryKey(id);
        if(agent==null){
            orgagent.setCaStatus(Status.STATUS_0.status);//工商认证状态
            agentCertification.setCerProStat(Status.STATUS_2.status);//认证流程--处理成功
            agentCertification.setCerRes(Status.STATUS_0.status);//认证结果
            if(1==agentMapper.updateByPrimaryKeySelective(orgagent) && 1 == agentCertificationMapper.updateByPrimaryKeySelective(agentCertification)){
                logger.info("认证代理商不存在，认证代理商{}状态为{},不进行信息同步",orgagent.getId(),orgagent.getCaStatus());
            }
            return new AgentResult(404,"工商认证代理商未找到"+agent.getId(),"");
        }
        agentCertification = copyOrgAgentToCertifi(agent,agentCertification);
        agentCertification.setOrgCerId(orgId);
        //处理代理名称去掉前缀和括号后的信息
        String agname = agent.getAgName();
        logger.info("后台任务进行工商认证|{}|{}",agent.getId(),agname);
        agname = agname.replaceAll("[A-Z]|\\(*\\)","");
        logger.info("后台任务进行工商认证|{}|替换后名称|{}",agent.getId(),agname);
        AgentResult agentResult = businessCAService.agentBusinessCA(agname, "0");
        logger.info("接口返回"+agentResult.getData());
        if(agentResult.isOK()||(405==agentResult.getStatus())){
            JSONObject dataObj = (JSONObject)agentResult.getData();
            if ("1".equals((String) dataObj.getString("isTest"))){
                agent.setCaStatus(Status.STATUS_2.status);
                agentCertification.setCerProStat(Status.STATUS_2.status);//认证流程状态:0-未处理,1-处理中,2-处理成功,3-处理失败;
                agentCertification.setCerRes(new BigDecimal(2));//测试环境不认证
                if(1==agentMapper.updateByPrimaryKeySelective(agent) && 1 == agentCertificationMapper.updateByPrimaryKeySelective(saveAgentCertification(dataObj,agentCertification))){
                    logger.info("测试环境不认证，认证代理商{}状态为{},不进行信息同步",agent.getId(),agent.getCaStatus());
                }
                return AgentResult.ok(dataObj);
            }
            if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("enterpriseStatus")) && dataObj.getString("enterpriseStatus").startsWith("在营")){
            //更新代理商信息
            if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("regCap")))
                agent.setAgCapital(new BigDecimal(dataObj.getString("regCap").trim()));

            if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("openFrom")))
                agent.setAgBusLicb(DateUtil.format(dataObj.getString("openFrom"),"yyyy-MM-dd"));

            if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("openTo")) && dataObj.getString("openTo").equals("长期")){
                agent.setAgBusLice(DateUtil.format("2099-12-31","yyyy-MM-dd"));
            }else if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("openTo"))){
                agent.setAgBusLice(DateUtil.format(dataObj.getString("openTo"),"yyyy-MM-dd"));
            }
            if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("frName")))
                agent.setAgLegal(dataObj.getString("frName"));//法人姓名
            if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("address")))
                agent.setAgRegAdd(dataObj.getString("address"));//注册地址
            if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("operateScope")))
            agent.setAgBusScope(dataObj.getString("operateScope"));//经营范围
                if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("creditCode")))
                    agent.setAgBusLic(dataObj.getString("creditCode"));//营业执照
            //如果负责人没有，采用工商认证后的法人
            if(StringUtils.isBlank(agent.getAgHead())){
                agent.setAgHead(agent.getAgLegal());
            }
            agent.setCaStatus(Status.STATUS_1.status);
                //更新认证信息表实体
                agentCertification.setCerRes(Status.STATUS_1.status);//认证结果;1-成功,2-失败
                agentCertification.setCerProStat(Status.STATUS_2.status);//认证流程状态:0-未处理,1-处理中,2-处理成功,3-处理失败;
                if(1==agentMapper.updateByPrimaryKeySelective(agent)&& 1 == agentCertificationMapper.updateByPrimaryKeySelective(saveAgentCertification(dataObj,agentCertification))){
                    logger.info("工商认证成功，认证代理商{}状态为{},同步信息成功",agent.getAgUniqNum(),dataObj.getString("enterpriseStatus"));
                }

                AgentResult agentResultFreeze =  queryAgentFreeze(agent.getId());
                logger.info("代理商:{},查询冻结解冻状态{}",agent.getId(),agentResultFreeze.getData());
                if (agentResultFreeze.isOK()){
                    Map<String,Object> resultFreezeData = (Map<String,Object>)agentResultFreeze.getData();
                    if (FreeStatus.DJ.getValue().toString().equals((String) resultFreezeData.get("freeStatus").toString())){
                        AgentFreezePort agentFreezePort = new AgentFreezePort();
                        agentFreezePort.setAgentId(agent.getId());
                        agentFreezePort.setUnfreezeCause(FreeCause.RZDJ.code);
                        agentFreezePort.setOperationPerson(agentCertification.getReqCerUser());
                        agentFreezePort.setFreezeCause(FreeCause.RZDJ.code);
                        AgentResult agentUnFreeze = null;
                        logger.info("代理商{}开始解冻",agent.getId());
                            agentUnFreeze =  agentUnFreeze(agentFreezePort);
                            if (agentUnFreeze.isOK()){
                                logger.info("代理商{},解冻成功",agent.getId());
                            }else {
                                logger.info("代理商{},解冻失败:{}",agent.getId(),agentResultFreeze.getMsg());
                            }

                    }

                }
            }else if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("enterpriseStatus")) && !dataObj.getString("enterpriseStatus").startsWith("在营")){
                //非在营状态则冻结该代理商
                agent.setCaStatus(Status.STATUS_2.status);
                agentCertification.setCerRes(Status.STATUS_1.status);//认证结果;1-成功,2-失败
                agentCertification.setCerProStat(Status.STATUS_2.status);//认证流程状态:0-未处理,1-处理中,2-处理成功,3-处理失败;
                if(1==agentMapper.updateByPrimaryKeySelective(agent)&& 1 == agentCertificationMapper.updateByPrimaryKeySelective(saveAgentCertification(dataObj,agentCertification))){
                    logger.info("工商认证成功，认证代理商{}状态为{},不进行信息同步",agent.getAgUniqNum(),dataObj.getString("enterpriseStatus"));
                }
            //非在营状态则冻结该代理商
                AgentResult agentResultFreeze = queryAgentFreeze(agent.getId());
                logger.info("代理商:{},查询冻结解冻状态{}",agent.getId(),agentResultFreeze.getData());
                if (agentResultFreeze.isOK()){
                    Map<String,Object> resultFreezeData = (Map<String,Object>)agentResultFreeze.getData();
                    if (FreeStatus.JD.getValue().toString().equals(resultFreezeData.get("freeStatus").toString())){
                        AgentFreezePort agentFreezePort = new AgentFreezePort();
                        agentFreezePort.setAgentId(agent.getId());
                        agentFreezePort.setFreezeCause(FreeCause.RZDJ.code);
                        agentFreezePort.setFreezeNum(agentCertification.getId());
                        agentFreezePort.setOperationPerson(agentCertification.getReqCerUser());
                        agentFreezePort.setFreeType(Arrays.asList(FreeType.AGNET.code));
                        logger.info("代理商{}开始冻结",agent.getId());
                            AgentResult agentFreeze = agentFreeze(agentFreezePort);
                            if (agentFreeze.isOK()){
                                logger.info("代理商{}冻结成功",agent.getId());
                            }else {
                                logger.info("代理商{}冻结失败{}",agent.getId(),agentFreeze.getMsg());
                            }

                    }

                }
            }
            return AgentResult.ok();
        }else{
            //工商认证失败
            agent.setCaStatus(Status.STATUS_2.status);
            agentCertification.setCerProStat(Status.STATUS_3.status);
            agentCertification.setCerRes(Status.STATUS_2.status);
            ZoneId zoneId = ZoneId.systemDefault();
            ZonedDateTime zdt = LocalDateTime.now().atZone(zoneId);//Combines this date-time with a time-zone to create a  ZonedDateTime.
            Date date = Date.from(zdt.toInstant());
            agentCertification.setCerSuccessTm(date);
            if(1==agentMapper.updateByPrimaryKeySelective(agent) && 1 == agentCertificationMapper.updateByPrimaryKeySelective(agentCertification)){
                logger.info("工商认证失败"+agent.getId());
            }
            return AgentResult.fail();
        }
    }

    @Override
    public List<AgentCertifiVo> exportAgentCertifications(Map map) {
        List<Dict> caStatus = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.CERTIFICATION_STATUS.name());
        List<AgentCertifiVo> agentCertifiVos = agentCertificationMapper.exportAgentcertifis(map);




        if (null != agentCertifiVos && agentCertifiVos.size() > 0)
            agentCertifiVos.forEach(agentcer->{
                if (null!=agentcer.getCerRes()) {
                    for (Dict dict : caStatus) {
                        if (null!=dict  &&  agentcer.getCerRes().toString().equals(dict.getdItemvalue())){
                            agentcer.setCaStatusMark(dict.getdItemname());
                            break;
                        }
                    }
                    //认证流程状态:0-未处理,1-处理中,2-处理成功,3-处理失败
                    if(null != agentcer.getCerProStat()) {
                        if (agentcer.getCerProStat().compareTo(new BigDecimal(0)) == 0) {
                            agentcer.setCerProStatMark("未处理");
                        } else if (agentcer.getCerProStat().compareTo(new BigDecimal(1)) == 0) {
                            agentcer.setCerProStatMark("处理中");
                        } else if (agentcer.getCerProStat().compareTo(new BigDecimal(2)) == 0) {
                            agentcer.setCerProStatMark("处理成功");
                        } else if (agentcer.getCerProStat().compareTo(new BigDecimal(3)) == 0) {
                            agentcer.setCerProStatMark("处理失败");
                        }
                    }
                    if (null!=agentcer.getAgBusLicb()){
                        try {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = simpleDateFormat.parse((String)agentcer.getAgBusLicb());
                            agentcer.setAgBusLicb(simpleDateFormat.format(date).toString());
                        }catch (Exception e){

                        }

                    }
                    if (null!=agentcer.getAgBusLice()){

                        try {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = simpleDateFormat.parse((String)agentcer.getAgBusLice());
                            agentcer.setAgBusLice(simpleDateFormat.format(date).toString());
                        }catch (Exception e){

                        }

                    }
                }});
        return agentCertifiVos;
    }

    @Override
    public AgentCertification  getMaxId(Map map) {
        return agentCertificationMapper.queryMaxIdByAgentid(map);
    }

    private AgentCertification saveAgentCertification(JSONObject dataObj, AgentCertification agentCertification){
        //更新认证信息表
        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("enterpriseName")))
        agentCertification.setEnterpriseName(dataObj.getString("enterpriseName").trim());//企业名称
        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("frName")))
        agentCertification.setFrName(dataObj.getString("frName").trim());//法人名称
        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("regNo")))
        agentCertification.setRegNo                     (dataObj.getString("regNo").trim());//工商注册号
        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("regCap")))
            agentCertification.setRegCap                    (new BigDecimal(dataObj.getString("regCap").trim()));//注册资金
        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("regCapCur")))
        agentCertification.setRegCapCur                 (dataObj.getString("regCapCur").trim());//注册币种
        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("esDate")))
            agentCertification.setEsDate(dataObj.getString("esDate"));
        if (com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("openFrom")))
            agentCertification.setOpenFrom                  (DateUtil.format(dataObj.getString("openFrom"),"yyyy-MM-dd"));//经营开始日期

        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("openTo")) && dataObj.getString("openTo").equals("长期")){
            agentCertification.setOpenTo(DateUtil.format("2099-12-31","yyyy-MM-dd"));
        }else if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("openTo"))){
            agentCertification.setOpenTo(DateUtil.format(dataObj.getString("openTo"),"yyyy-MM-dd"));
        }

        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("enterpriseType")))
        agentCertification.setEnterpriseType            (dataObj.getString("enterpriseType").trim());//企业（机构）类型
        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("enterpriseStatus")))
        agentCertification.setEnterpriseStatus          (dataObj.getString("enterpriseStatus").trim());//经营状态
        if (com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("cancelDate")))
            agentCertification.setCancelDate                (DateUtil.format(dataObj.getString("cancelDate"),"yyyy-MM-dd"));//注销日期
        if (com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("revokeDate")))
            agentCertification.setRevokeDate                (DateUtil.format(dataObj.getString("revokeDate"),"yyyy-MM-dd"));//吊销日期
        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("address")))
        agentCertification.setAddress                   (dataObj.getString("address").trim());//注册地址
        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("abuItem")))
        agentCertification.setAbuItem                   (dataObj.getString("abuItem").trim());//许可经营项目
        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("cbuItem")))
        agentCertification.setCbuItem                   (dataObj.getString("cbuItem").trim());//一般经营项目
        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("operateScope")))
        agentCertification.setOperateScope              (dataObj.getString("operateScope").trim());//经营（业务）范围
        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("operateScopeAndForm")))
        agentCertification.setOperateScopeAndForm       (dataObj.getString("operateScopeAndForm").trim());//经营（业务）范围及方式
        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("regOrg")))
        agentCertification.setRegOrg                    (dataObj.getString("regOrg").trim());//登记机关
        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("ancheYear")))
        agentCertification.setAncheYear                 (dataObj.getString("ancheYear").trim());//最后年检年度
        if (com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("ancheDate")))
            agentCertification.setAncheDate               (DateUtil.format(dataObj.getString("ancheDate"),"yyyy-MM-dd"));//最后年检日期
        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("industryPhyCode")))
        agentCertification.setIndustryPhyCode           (dataObj.getString("industryPhyCode").trim());//行业门类代码
        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("industryPhyName")))
        agentCertification.setIndustryPhyName           (dataObj.getString("industryPhyName").trim());//行业门类名称
        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("industryCode")))
        agentCertification.setIndustryCode              (dataObj.getString("industryCode").trim());//国民经济行业代码
        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("industryName")))
        agentCertification.setIndustryName              (dataObj.getString("industryName").trim());//国民经济行业名称
        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("recCap")))
            agentCertification.setRecCap                    (new BigDecimal(dataObj.getString("recCap").trim()));//实收资本
        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("oriRegNo")))
        agentCertification.setOriRegNo                  (dataObj.getString("oriRegNo").trim());//原注册号
        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("creditCode")))
        agentCertification.setCreditCode                (dataObj.getString("creditCode").trim());//统一信用代码
        if (com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("apprDate")))
            agentCertification.setApprDate               (DateUtil.format(dataObj.getString("apprDate"),"yyyy-MM-dd"));//核准日期
        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("orgNo")))
        agentCertification.setOrgNo                     (dataObj.getString("orgNo").trim());//注册号
        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("usci")))
        agentCertification.setUsci                      (dataObj.getString("usci").trim());//组织机构号
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = LocalDateTime.now().atZone(zoneId);//Combines this date-time with a time-zone to create a  ZonedDateTime.
        Date date = Date.from(zdt.toInstant());
        agentCertification.setCerSuccessTm(date);
        return agentCertification;
    }

    private AgentCertification copyOrgAgentToCertifi(Agent agent,AgentCertification agentCertification){
        agentCertification.setOrgAgName(agent.getAgName());
        agentCertification.setOrgAgNature(agent.getAgNature());
        agentCertification.setOrgAgCapital(agent.getAgCapital());
        agentCertification.setOrgAgBusLic(agent.getAgBusLic());
        agentCertification.setOrgAgBusLicb(agent.getAgBusLicb());
        agentCertification.setOrgAgBusLice(agent.getAgBusLice());
        agentCertification.setOrgAgLegal(agent.getAgLegal());
        agentCertification.setOrgAgRegAdd(agent.getAgRegAdd());
        agentCertification.setOrgAgBusScope(agent.getAgBusScope());
        return agentCertification;
    }

    @Override
    public int updateCertifi(AgentCertification agentCertification) {
        int i = agentCertificationMapper.updateByPrimaryKeySelective(agentCertification);
        return i;
    }
}
