package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentCertificationMapper;
import com.ryx.credit.dao.agent.AgentFreezeMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentCertifiVo;
import com.ryx.credit.pojo.admin.vo.AgentFreezePort;
import com.ryx.credit.pojo.admin.vo.FreezeDetail;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.AgentCertificationService;
import com.ryx.credit.service.agent.BusinessCAService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.management.resources.agent;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.*;
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
    @Autowired
    private AgentFreezeMapper agentFreezeMapper;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
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
            long currentTimeMillis = System.currentTimeMillis();
            List<String> list=new ArrayList<>();
           if (null!=agents && agents.size()>0){
               for (Agent agent : agents) {
                   list.add(agent.getId());
               }
           }
           //无需再次查询处理中或者待处理的数据；
            List<AgentCertification> agentCertifications = new ArrayList<>();
            /*List<AgentCertification> agentCertificationList = agentCertificationMapper.queryCersByAgent(list);
            if(null!=agentCertificationList && agentCertificationList.size()>0){
                for (AgentCertification agentCertification : agentCertificationList) {
                    resStr.append(agentCertification.getOrgAgName()).append(",");
                    continue;
                }
            }*/
            agents.forEach((cer)->{
                FastMap par = FastMap.fastMap("id",cer.getId());
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
            long currentTimeMillis1 = System.currentTimeMillis();
            logger.info("用时："+(currentTimeMillis1-currentTimeMillis));
            //分批添加
            if (null!= agentCertifications && agentCertifications.size()>0){
                int dataList=300;
                int size = agentCertifications.size();
                if (dataList<size){
                    int part= size/dataList;
                    logger.info("共有:"+size+"条,分为:"+part);
                    int insertBatch=0;
                    for(int i=0;i<part;i++){
                        List<AgentCertification> agent_Certifications = agentCertifications.subList(0, dataList);
                        insertBatch= agentCertificationMapper.insertBatch(agent_Certifications);
                        agentCertifications.subList(0, dataList).clear();
                    }
                    if(!agentCertifications.isEmpty()){
                         insertBatch = agentCertificationMapper.insertBatch(agentCertifications);
                        if (insertBatch > 0){
                            if ("".equals(resStr.toString())||null==resStr)
                                return AgentResult.ok();
                            return new AgentResult(200,"存在未处理完成信息:"+resStr.toString()+"未再次添加",null);
                        }else {
                            return AgentResult.fail("未添加认证数据");
                        }
                    }
                    if (insertBatch > 0){
                        if ("".equals(resStr.toString())||null==resStr)
                            return AgentResult.ok();
                        return new AgentResult(200,"存在未处理完成信息:"+resStr.toString()+"未再次添加",null);
                    }else {
                        return AgentResult.fail("未添加认证数据");
                    }
                }else{
                    int insertBatch = agentCertificationMapper.insertBatch(agentCertifications);
                    if (insertBatch > 0){
                        if ("".equals(resStr.toString())||null==resStr)
                            return AgentResult.ok();
                        return new AgentResult(200,"存在未处理完成信息:"+resStr.toString()+"未再次添加",null);
                    }else {
                        return AgentResult.fail("未添加认证数据");
                    }
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
            orgagent.setCaStatus(CerResStatus.NORECORD.status);//工商认证状态
            agentCertification.setCerProStat(Status.STATUS_2.status);//认证流程--处理成功
            agentCertification.setCerRes(CerResStatus.NORECORD.status);//认证结果---查询无记录
            if(1==agentMapper.updateByPrimaryKeySelective(orgagent) && 1 == agentCertificationMapper.updateByPrimaryKeySelective(agentCertification)){
                logger.info("认证代理商不存在，认证代理商{}状态为{},不进行信息同步",orgagent.getId(),orgagent.getCaStatus());
            }
            return new AgentResult(404,"工商认证代理商未找到"+agent.getId(),"");
        }
        //判断数据库代理商以下字段是否为空---未开始认证操作
        HashMap<Object, Object> map = new HashMap<>();
        String remark="";
        BigDecimal cerResStatus = CerResStatus.DEFICIENCY.status;
        if(StringUtils.isBlank(agent.getAgName()) || StringUtils.isBlank(agent.getAgBusLic()) ||
                StringUtils.isBlank(agent.getAgLegal()) ||  StringUtils.isBlank(agent.getAgLegalCernum())
                || null==agent.getAgBusLicb() || null==agent.getAgBusLice()){
            logger.info("基础信息有缺失",agent.getId());
            if(StringUtils.isBlank(agent.getAgName())){//代理商名称
                remark="代理商名称缺失;";
            }
            if(StringUtils.isBlank(agent.getAgBusLic())){ //营业执照
                remark+="营业执照缺失;";
            }
            if(StringUtils.isBlank(agent.getAgLegal())){//法人姓名
                remark+="法人姓名缺失;";
            }
            if(StringUtils.isBlank(agent.getAgLegalCernum())){//法人证件号
                remark+="法人证件号缺失;";
            }
            if(null==agent.getAgBusLicb()){//经营开始日期
                remark+="经营开始日期缺失;";
            }
            if(null==agent.getAgBusLice()){//经营结束日期
                remark+="经营结束日期缺失;";
            }
            remark = remark.substring(0,remark.length() - 1);
            map.put("remark",remark);
            map.put("cerResStatus",cerResStatus);
            if(null!=map && null!=(BigDecimal) map.get("cerResStatus")){
                agentCertification.setCerRes((BigDecimal) map.get("cerResStatus"));//“认证通过” "认证失败","信息缺失"，“认证成功，与本地信息不符“
                agent.setCaStatus((BigDecimal) map.get("cerResStatus"));
            }
            agentCertification.setCerProStat(Status.STATUS_2.status);//认证流程状态:0-未处理,1-处理中,2-处理成功,3-处理失败;
            agentCertification.setCerSuccessTm(new Date());
            if(1!=agentMapper.updateByPrimaryKeySelective(agent) || 1 != agentCertificationMapper.updateByPrimaryKeySelective(agentCertification)){
                logger.info(("基础信息更改失败"));
                return AgentResult.fail("基础信息更改失败");
            }
            return AgentfreezeShare(agent, agentCertification, map);
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
                agent.setCaStatus(CerResStatus.FAIL.status);
                agentCertification.setCerProStat(Status.STATUS_2.status);//认证流程状态:0-未处理,1-处理中,2-处理成功,3-处理失败;
                agentCertification.setCerRes(CerResStatus.FAIL.status);//测试环境不认证
                if(1==agentMapper.updateByPrimaryKeySelective(agent) && 1 == agentCertificationMapper.updateByPrimaryKeySelective(saveAgentCertification(dataObj,agentCertification))){
                    logger.info("测试环境不认证，认证代理商{}状态为{},不进行信息同步",agent.getId(),agent.getCaStatus());
                }
                return AgentResult.ok(dataObj);
            }
            if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("enterpriseStatus")) && dataObj.getString("enterpriseStatus").startsWith("在营")){
                if(StringUtils.isNotBlank(dataObj.getString("frName")) &&
                        StringUtils.isNotBlank(dataObj.getString("enterpriseName")) &&
                        StringUtils.isNotBlank(dataObj.getString("openFrom")) &&
                        StringUtils.isNotBlank(dataObj.getString("openTo"))
                        ){
                    String creditCode="";
                    if(StringUtils.isNotBlank(dataObj.getString("creditCode")) ){
                        creditCode=dataObj.getString("creditCode");
                    }else if(StringUtils.isNotBlank(dataObj.getString("regNo"))){
                        creditCode=dataObj.getString("regNo");
                    }
                    Date date=new Date();
                    if(StringUtils.isNotBlank(dataObj.getString("openTo")) && dataObj.getString("openTo").equals("长期")){
                        date =DateUtil.format("2099-12-31","yyyy-MM-dd");
                    }else if(StringUtils.isNotBlank(dataObj.getString("openTo"))){
                        date =DateUtil.format(dataObj.getString("openTo"),"yyyy-MM-dd");
                    }
                    //                如果与系统中的字段不一致  则进行冻结
                    if(!agent.getAgLegal().equals(dataObj.getString("frName")) ||
                            !agent.getAgName().equals(dataObj.getString("enterpriseName")) ||
                            agent.getAgBusLicb().compareTo(dataObj.getDate("openFrom"))!=0||
                            agent.getAgBusLice().compareTo(date)!=0 ||
                            !agent.getAgBusLic().equals(creditCode)){
                        logger.info("认证成功，与本地信息不符",agentCertification.getId());
                        if(!agent.getAgLegal().equals(dataObj.getString("frName"))){
                            remark+="法人姓名,";
                        }
                        if(!agent.getAgName().equals(dataObj.getString("enterpriseName"))){
                            remark+="企业名称,";
                        }
                        if(!agent.getAgBusLic().equals(creditCode)){
                            remark+="营业执照,";
                        }
                        if(agent.getAgBusLicb().compareTo(dataObj.getDate("openFrom"))!=0){
                            remark+="经营开始日期,";
                        }
                        if( agent.getAgBusLice().compareTo(date)!=0){
                            remark+="经营结束日期,";
                        }
                        remark = remark.substring(0,remark.length() - 1);
                        cerResStatus = CerResStatus.INCONFORMITY.status;
                        remark+="与本地信息不符";
                        map.put("remark",remark);
                        map.put("cerResStatus",cerResStatus);
                        if(null!=map && null!=(BigDecimal) map.get("cerResStatus")){
                            agentCertification.setCerRes((BigDecimal) map.get("cerResStatus"));//“认证通过” "认证失败","信息缺失"，“认证成功，与本地信息不符“
                            agent.setCaStatus((BigDecimal) map.get("cerResStatus"));
                        }
                        agentCertification.setCerProStat(Status.STATUS_2.status);//认证流程状态:0-未处理,1-处理中,2-处理成功,3-处理失败;
                        if(1!=agentMapper.updateByPrimaryKeySelective(agent) || 1 != agentCertificationMapper.updateByPrimaryKeySelective(saveAgentCertification(dataObj,agentCertification))){
                            logger.info("认证成功，与本地信息不符，认证代理商{}状态为{},信息同步失败",agent.getId(),dataObj.getString("enterpriseStatus"));
                            return AgentResult.fail("认证成功,与本地信息不符,认证代理商信息同步失败");
                        }
                        return  AgentfreezeShare(agent,agentCertification,map);
                    }else if(agent.getAgLegal().equals(dataObj.getString("frName")) &&
                            agent.getAgName().equals(dataObj.getString("enterpriseName")) &&
                            agent.getAgBusLicb().compareTo(dataObj.getDate("openFrom"))==0&&
                            agent.getAgBusLice().compareTo(date)==0 &&
                            agent.getAgBusLic().equals(creditCode)){
//                    一致则解冻 为认证通过
                        logger.info("认证成功,开始解冻",agentCertification.getId());
                        cerResStatus = CerResStatus.SUCCESS.status;
                        remark+="认证成功";
                        map.put("remark",remark);
                        map.put("cerResStatus",cerResStatus);
                        return agentUnFreeze(agent,agentCertification,dataObj,map);
                    }
                }else{
                    logger.info("接口有为空的数据");
                    return AgentResult.fail("接口有为空的数据");
                }
            }else if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("enterpriseStatus")) && !dataObj.getString("enterpriseStatus").startsWith("在营")){
                logger.info("非在营状态,进行冻结操作",dataObj.getString("enterpriseStatus"));
                //非在营状态则冻结该代理商
                agent.setCaStatus(CerResStatus.FAIL.status);
                agentCertification.setCerRes(CerResStatus.FAIL.status);//认证结果;1-成功,2-失败
                agentCertification.setCerProStat(Status.STATUS_2.status);//认证流程状态:0-未处理,1-处理中,2-处理成功,3-处理失败;
                if(1==agentMapper.updateByPrimaryKeySelective(agent)&& 1 == agentCertificationMapper.updateByPrimaryKeySelective(saveAgentCertification(dataObj,agentCertification))){
                    logger.info("工商认证成功，认证代理商{}状态为{},不进行信息同步",agent.getId(),dataObj.getString("enterpriseStatus"));
                }
            //非在营状态则冻结该代理商
//                AgentResult agentResultFreeze = queryAgentFreeze(agent.getId());
                      try {
                          List<AgentBusInfo> aginfo = agentBusinfoService.queryAgentBusInfoFreeze(agent.getId());
                          List<String> busList = new LinkedList<>();
                          for (AgentBusInfo busInfo : aginfo) {
                              busList.add(busInfo.getId());
                          }
                          AgentFreezePort agentFreezePort = new AgentFreezePort();
                          agentFreezePort.setAgentId(agent.getId());
                          agentFreezePort.setFreezeCause(FreeCause.RZDJ.code);
                          agentFreezePort.setFreezeNum(agentCertification.getId());
                          agentFreezePort.setOperationPerson(agentCertification.getReqCerUser());
                          agentFreezePort.setFreeType(Arrays.asList(FreeType.AGNET.code));
                          agentFreezePort.setBusPlatform(busList);
                          agentFreezePort.setNewBusFreeze(String.valueOf(BigDecimal.ZERO));
                          agentFreezePort.setRemark("认证结果非在营");

                          FreezeDetail freezeDetail = new FreezeDetail();
                          freezeDetail.setProfitFreeze(BigDecimal.ONE);//分润冻结
                          freezeDetail.setReflowFreeze(BigDecimal.ONE);//返现冻结
                          freezeDetail.setMonthlyFreeze(BigDecimal.ONE);//月结
                          freezeDetail.setDailyFreeze(BigDecimal.ONE);//日结
                          freezeDetail.setCashFreeze(BigDecimal.ONE);//体现结算冻结
                          agentFreezePort.setCurLevel(freezeDetail);
                          logger.info("代理商{}开始冻结",agent.getId());
                          AgentResult agentFreeze = agentFreeze(agentFreezePort);
                          if (agentFreeze.isOK()){
                              logger.info("代理商{}冻结成功",agent.getId());
                          }else {
                              logger.info("代理商{}冻结失败{}",agent.getId(),agentFreeze.getMsg());
                          }
                      }catch (MessageException e) {
                          AgentFreezeExample agentFreezeExample = new AgentFreezeExample();
                          agentFreezeExample.createCriteria().andAgentIdEqualTo(agent.getId())
                                  .andFreezeCauseEqualTo(FreeCause.RZDJ.code)
                                  .andStatusEqualTo(Status.STATUS_1.status)
                                  .andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString());
                          List<AgentFreeze> agentFreezeList = agentFreezeMapper.selectByExample(agentFreezeExample);
                          if(null!=agentFreezeList && agentFreezeList.size()>0){
                              AgentFreeze agentFreeze = agentFreezeList.get(0);
                              agentFreeze.setRemark("认证结果非在营");
                              agentFreeze.setFreezeDate(new Date());
                              if(1!=agentFreezeMapper.updateByPrimaryKeySelective(agentFreeze)){
                                  logger.info("代理商{}冻结失败{}",agent.getId(),e.getMsg());
                              }
                              Agent a_agent = agentMapper.selectByAgent(agent);
                              AgentCertification agent_crtification = new AgentCertification();
                              agent_crtification.setId(agentCertification.getId());
                              a_agent.setCaStatus(CerResStatus.FAIL.status);
                              agentCertification.setCerRes(CerResStatus.FAIL.status);//认证结果;1-成功,2-失败
                              agentCertification.setCerProStat(Status.STATUS_2.status);
                              agent_crtification.setCerSuccessTm(new Date());
                              if(1!=agentMapper.updateByPrimaryKeySelective(a_agent) || 1 != agentCertificationMapper.updateByPrimaryKeySelective(agent_crtification)){
                                  logger.info(("基础信息更改失败"));
                                  return AgentResult.fail("基础信息更改失败");
                              }
                          }
                          logger.info("代理商{}冻结成功{}",agent.getId(),e.getMsg());
                          return AgentResult.ok("冻结成功");
                      }catch (Exception e) {
                          logger.info("代理商{}",agent.getId());
                          return AgentResult.fail("认证失败");
                      }
            }
            return AgentResult.ok();
        }else{
            //工商认证失败
            agent.setCaStatus(CerResStatus.FAIL.status);
            agentCertification.setCerProStat(Status.STATUS_3.status);
            agentCertification.setCerRes(CerResStatus.FAIL.status);
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
        List<Dict> caStatus = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.CER_RES_STATUS.name());
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

    @Override
    public List<AgentCertification> queryCersByAgent(List ids) {
        return agentCertificationMapper.queryCersByAgent(ids);
    }

    @Override
    public long queryCertifiByCerPro() {
        AgentCertificationExample example =new AgentCertificationExample();
        AgentCertificationExample.Criteria criteria = example.or().andCerProStatIn(Arrays.asList(Status.STATUS_0.status, Status.STATUS_1.status));
        return agentCertificationMapper.countByExample(example);
    }

    /**
     * 冻结公共方法
     * @param agent
     * @param agentCertification
     * @throws MessageException
     */
    private AgentResult AgentfreezeShare(Agent agent,AgentCertification agentCertification,Map map)throws MessageException {
             try {
                 List<AgentBusInfo> aginfo = agentBusinfoService.queryAgentBusInfoFreeze(agent.getId());
                 List<String> busList = new LinkedList<>();
                 for (AgentBusInfo busInfo : aginfo) {
                     busList.add(busInfo.getId());
                 }
                 AgentFreezePort agentFreezePort = new AgentFreezePort();
                 agentFreezePort.setAgentId(agent.getId());
                 agentFreezePort.setFreezeCause(FreeCause.RZDJ.code);
                 agentFreezePort.setFreezeNum(agentCertification.getId());
                 agentFreezePort.setOperationPerson(agentCertification.getReqCerUser());
                 agentFreezePort.setFreeType(Arrays.asList(FreeType.AGNET.code));
                 agentFreezePort.setBusPlatform(busList);
                 agentFreezePort.setNewBusFreeze(String.valueOf(BigDecimal.ZERO));
                 if(null!=map && StringUtils.isNotBlank(String.valueOf(map.get("remark")))){
                     agentFreezePort.setRemark(String.valueOf(map.get("remark")));
                 }
                 FreezeDetail freezeDetail = new FreezeDetail();
                 freezeDetail.setProfitFreeze(BigDecimal.ONE);//分润冻结
                 freezeDetail.setReflowFreeze(BigDecimal.ONE);//返现冻结
                 freezeDetail.setMonthlyFreeze(BigDecimal.ONE);//月结
                 freezeDetail.setDailyFreeze(BigDecimal.ONE);//日结
                 freezeDetail.setCashFreeze(BigDecimal.ONE);//体现结算冻结
                 agentFreezePort.setCurLevel(freezeDetail);
                 logger.info("代理商{}开始冻结",agent.getId());
                 AgentResult agentFreeze = agentFreeze(agentFreezePort);
                 if (agentFreeze.isOK()){
                     logger.info("代理商{}冻结成功",agent.getId());
                     return AgentResult.ok("冻结成功");
                 }else {
                     logger.info("代理商{}冻结失败{}",agent.getId(),agentFreeze.getMsg());
                     return AgentResult.fail("冻结失败");
                 }
             }catch (MessageException e) {
                 AgentFreezeExample agentFreezeExample = new AgentFreezeExample();
                 agentFreezeExample.createCriteria().andAgentIdEqualTo(agent.getId())
                         .andFreezeCauseEqualTo(FreeCause.RZDJ.code)
                         .andStatusEqualTo(Status.STATUS_1.status)
                         .andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString());
                 List<AgentFreeze> agentFreezeList = agentFreezeMapper.selectByExample(agentFreezeExample);
                 if(null!=agentFreezeList && agentFreezeList.size()>0){
                     AgentFreeze agentFreeze = agentFreezeList.get(0);
                     if(null!=map && StringUtils.isNotBlank(String.valueOf(map.get("remark")))){
                         agentFreeze.setRemark(String.valueOf(map.get("remark")));
                     }
                     agentFreeze.setFreezeDate(new Date());
                     if(1!=agentFreezeMapper.updateByPrimaryKeySelective(agentFreeze)){
                         logger.info("代理商{}重复冻结失败{}",agent.getId(),e.getMsg());
                         return AgentResult.fail("代理商重复冻结失败");
                     }
                     Agent a_agent = agentMapper.selectByAgent(agent);
                     AgentCertification agent_crtification = new AgentCertification();
                     agent_crtification.setId(agentCertification.getId());
                     if(null!=map && null!=(BigDecimal) map.get("cerResStatus")){
                         agent_crtification.setCerRes((BigDecimal) map.get("cerResStatus"));//“认证通过” "认证失败","信息缺失"，“认证成功，与本地信息不符“
                         a_agent.setCaStatus((BigDecimal) map.get("cerResStatus"));
                     }
                     agent_crtification.setCerSuccessTm(new Date());
                     agent_crtification.setCerProStat(Status.STATUS_2.status);//认证流程状态:0-未处理,1-处理中,2-处理成功,3-处理失败;
                     if(1!=agentMapper.updateByPrimaryKeySelective(a_agent) || 1 != agentCertificationMapper.updateByPrimaryKeySelective(agent_crtification)){
                         logger.info(("基础信息更改失败"));
                         return AgentResult.fail("基础信息更改失败");
                     }
                 }
                 logger.info("代理商{}冻结成功{}",agent.getId(),e.getMsg());
                 return AgentResult.ok("冻结成功");
               }catch (Exception e) {
                 logger.info("代理商{}", agent.getId());
                 return AgentResult.fail("冻结失败");
             }

    }


    /**
     * 解冻公共方法
     */
    private AgentResult agentUnFreeze(Agent agent,AgentCertification agentCertification,JSONObject dataObj,Map map)throws MessageException {
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
        else if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(dataObj.getString("regNo")))
            agent.setAgBusLic(dataObj.getString("regNo"));//营业执照
        //如果负责人没有，采用工商认证后的法人
        if(StringUtils.isBlank(agent.getAgHead())){
            agent.setAgHead(agent.getAgLegal());
        }
        //更新认证信息表实体
        if(null!=map && null!=(BigDecimal) map.get("cerResStatus")){
            agentCertification.setCerRes((BigDecimal) map.get("cerResStatus"));//“认证通过” "认证失败","信息缺失"，“认证成功，与本地信息不符“
            agent.setCaStatus((BigDecimal) map.get("cerResStatus"));
        }
        agentCertification.setCerSuccessTm(new Date());
        agentCertification.setCerProStat(Status.STATUS_2.status);//认证流程状态:0-未处理,1-处理中,2-处理成功,3-处理失败;
        if(1!=agentMapper.updateByPrimaryKeySelective(agent) || 1 != agentCertificationMapper.updateByPrimaryKeySelective(saveAgentCertification(dataObj,agentCertification))){
            logger.info("工商认证失败，认证代理商{}状态为{},同步信息失败",agent.getId(),dataObj.getString("enterpriseStatus"));
            return AgentResult.fail("工商认证失败，认证代理商{}:"+agent.getId());
        }
//        AgentResult agentResultFreeze =  queryAgentFreeze(agent.getId());
               try {
                   AgentFreezePort agentFreezePort = new AgentFreezePort();
                   agentFreezePort.setAgentId(agent.getId());
                   agentFreezePort.setUnfreezeCause(FreeCause.RZDJ.code);
                   agentFreezePort.setOperationPerson(agentCertification.getReqCerUser());
                   agentFreezePort.setFreezeCause(FreeCause.RZDJ.code);
                   if(null!=map && StringUtils.isNotBlank(String.valueOf(map.get("remark")))){
                       agentFreezePort.setRemark(String.valueOf(map.get("remark")));
                   }
                   AgentResult agentUnFreeze = null;
                   logger.info("代理商{}开始解冻",agent.getId());
                   agentUnFreeze =  agentUnFreeze(agentFreezePort);
                   if (agentUnFreeze.isOK()){
                       logger.info("代理商{},解冻成功",agent.getId());
                       return AgentResult.ok("解冻成功");
                   }else {
                       logger.info("代理商{},解冻成功:{}",agent.getId(),agentUnFreeze.getMsg());
                       return AgentResult.ok("解冻成功");
                   }
               }catch (MessageException e) {
                   logger.info("代理商{}解冻失败{}",agent.getId(),e.getMsg());
                   return AgentResult.fail(e.getMsg());
               }catch (Exception e) {
                   logger.info("代理商{}",agent.getId());
                   return AgentResult.fail("解冻失败");
               }
    }
}
