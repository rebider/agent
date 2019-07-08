package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.dao.bank.DPosRegionMapper;
import com.ryx.credit.dao.order.OrganizationMapper;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.bank.DPosRegion;
import com.ryx.credit.pojo.admin.bank.DPosRegionExample;
import com.ryx.credit.pojo.admin.order.Organization;
import com.ryx.credit.pojo.admin.order.OrganizationExample;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.service.dict.DepartmentService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.sun.org.apache.regexp.internal.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * Created by cx on 2018/6/11.
 */
@Service("aimportService")
public class AimportServiceImpl implements AimportService {

    private Logger logger = LoggerFactory.getLogger(AimportServiceImpl.class);

    public static List<String> yesorno = Arrays.asList("否","是");
    public static  List<String> gs = Arrays.asList("无","对公","对私");

    public static  Map<String,String> BUS_SCOP = FastMap.fastMap("市代","city").putKeyV("国代","nation").putKeyV("省代","province");

    @Autowired
    private ImportAgentMapper importAgentMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private AgentService agentService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private AccountPaidItemService accountPaidItemService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private AgentContractMapper agentContractMapper;
    @Autowired
    private AgentContractService agentContractService;
    @Autowired
    private BusinessPlatformService businessPlatformService;
    @Autowired
    private ApaycompService apaycompService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private AgentColinfoService agentColinfoService;
    @Autowired
    private AgentColinfoMapper agentColinfoMapper;
    @Autowired
    private AgentColinfoRelMapper agentColinfoRelMapper;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private CapitalMapper capitalMapper;
    @Autowired
    private DPosRegionMapper dPosRegionMapper;
    @Autowired
    private OrganizationMapper organizationMapper;




    @Override
    public int insertAgentImportData(ImportAgent importAgent) {
        importAgent.setStatus(Status.STATUS_1.status);
        importAgent.setDealstatus(Status.STATUS_0.status);//未处理
        importAgent.setcTime(Calendar.getInstance().getTime());
        importAgent.setId(idService.genId(TabId.a_import_agent));
        importAgent.setVersion(Status.STATUS_0.status);
        return importAgentMapper.insertSelective(importAgent);
    }

    @Override
    public List<String> addList(List<List<Object>> data, String dataType,String user,String batch)throws Exception {
        List<String> ids = new ArrayList<>();
        for (List<Object> datum : data) {
            if(!AgImportType.BUSINESS.name().equals(dataType)) {
                if (datum == null || datum.size() == 0 || StringUtils.isBlank(datum.get(0) + "")) break;
                ImportAgent importAgent = new ImportAgent();
                importAgent.setBatchcode(batch);
                importAgent.setcUser(user);
                importAgent.setDatacontent(JSONArray.toJSONString(datum));
                importAgent.setDataid(datum.get(0) + "");
                importAgent.setDatatype(dataType);
                if (1 != insertAgentImportData(importAgent)) {
                    throw new ProcessException("插入失败");
                }
                ids.add(importAgent.getId());
            }else{
                if (datum == null || datum.size() == 0 || StringUtils.isBlank(datum.get(3) + "")) break;
                ImportAgent importAgent = new ImportAgent();
                importAgent.setBatchcode(batch);
                importAgent.setcUser(user);
                importAgent.setDatacontent(JSONArray.toJSONString(datum));
                importAgent.setDataid(datum.get(3) + "");
                importAgent.setDatatype(dataType);
                if (1 != insertAgentImportData(importAgent)) {
                    throw new ProcessException("插入失败");
                }
                ids.add(importAgent.getId());
            }
        }
        return ids;
    }



    @Override
    public ResultVO analysisRecode(String userid)throws Exception{

        try {
            //导入代理商信息
            ImportAgentExample basequery = new ImportAgentExample();
            basequery.or().andStatusEqualTo(Status.STATUS_1.status).andDealstatusEqualTo(Status.STATUS_0.status).andDatatypeEqualTo(AgImportType.BASICS.name());
            List<ImportAgent>  baseList = importAgentMapper.selectByExampleWithBLOBs(basequery);
            logger.info("代理商基础信息处理{}",baseList.size());
            ResultVO baseRes = analysisBase(baseList, userid);
            logger.info("代理商基础信息处理{}",baseRes.getResInfo());


            //导入已支付款项
            ImportAgentExample payquery = new ImportAgentExample();
            payquery.or().andStatusEqualTo(Status.STATUS_1.status).andDealstatusEqualTo(Status.STATUS_0.status).andDatatypeEqualTo(AgImportType.PAYMENT.name());
            List<ImportAgent>  payqueryList = importAgentMapper.selectByExampleWithBLOBs(payquery);
            logger.info("代理商交款信息处理{}",payqueryList.size());
            ResultVO payqueryListres = analysisPayment(payqueryList,userid);
            logger.info("代理商交款信息处理{}",payqueryListres.getResInfo());


            //导入合同信息
            ImportAgentExample contractquery = new ImportAgentExample();
            contractquery.or().andStatusEqualTo(Status.STATUS_1.status).andDealstatusEqualTo(Status.STATUS_0.status).andDatatypeEqualTo(AgImportType.CONTRACT.name());
            List<ImportAgent>  contractqueryList = importAgentMapper.selectByExampleWithBLOBs(contractquery);
            logger.info("代理商合同信息处理{}",contractqueryList.size());
            ResultVO contractqueryListRes = analysisContract(contractqueryList,userid);
            logger.info("代理商合同信息处理{}",contractqueryListRes.getResInfo());


            //导入业务信息
            ImportAgentExample busquery = new ImportAgentExample();
            busquery.or().andStatusEqualTo(Status.STATUS_1.status).andDealstatusEqualTo(Status.STATUS_0.status).andDatatypeEqualTo(AgImportType.BASBUSR.name());
            List<ImportAgent>  busqueryList = importAgentMapper.selectByExampleWithBLOBs(busquery);
            logger.info("代理商业务信息处理{}",busqueryList.size());
            ResultVO busqueryListRes = analysisBus(busqueryList,userid);
            logger.info("代理商业务信息处理{}",busqueryListRes.getResInfo());

            ImportAgentExample success = new ImportAgentExample();
            success.or().andStatusEqualTo(Status.STATUS_1.status).andDealstatusEqualTo(Status.STATUS_2.status).andDatatypeEqualTo(AgImportType.BASICS.name());
            List<ImportAgent>  successList = importAgentMapper.selectByExample(success);
            for (ImportAgent importAgent : successList) {

               String uniqnum =   importAgent.getDataid();
                AgentExample example = new AgentExample();
                example.or().andAgUniqNumEqualTo(uniqnum);
                List<Agent>  agents =  agentMapper.selectByExample(example);
                //更新代理商状态
                for (Agent agent : agents) {
                    agent.setAgStatus(AgStatus.Approved.name());
                    agent.setcIncomStatus(Status.STATUS_1.status);
                    agent.setcIncomTime(new Date());
                    agentMapper.updateByPrimaryKeySelective(agent);

                    //更新收款账户状态
                    AgentColinfoExample agentColinfoExample = new AgentColinfoExample();
                    agentColinfoExample.or().andAgentIdEqualTo(agent.getId()).andStatusEqualTo(Status.STATUS_1.status);
                    List<AgentColinfo>  colinfos = agentColinfoMapper.selectByExample(agentColinfoExample);
                    for (AgentColinfo colinfo : colinfos) {
                        colinfo.setCloReviewStatus(AgStatus.Approved.status);
                        agentColinfoMapper.updateByPrimaryKeySelective(colinfo);
                    }

                    //更新合同状态
                    AgentContractExample agentContractExample = new AgentContractExample();
                    agentContractExample.or().andAgentIdEqualTo(agent.getId()).andStatusEqualTo(Status.STATUS_1.status);
                    List<AgentContract>  contracts= agentContractMapper.selectByExample(agentContractExample);
                    for (AgentContract contract : contracts) {
                        contract.setCloReviewStatus(AgStatus.Approved.status);
                        agentContractMapper.updateByPrimaryKeySelective(contract);
                    }

                    //更新业务状态
                    AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
                    agentBusInfoExample.or().andAgentIdEqualTo(agent.getId()).andStatusEqualTo(Status.STATUS_1.status);
                    List<AgentBusInfo>  agentBusInfos= agentBusInfoMapper.selectByExample(agentBusInfoExample);
                    for (AgentBusInfo agentBusInfo : agentBusInfos) {
                        agentBusInfo.setCloReviewStatus(AgStatus.Approved.status);
                        agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
                    }


                }
            }

            parseParent();
            return ResultVO.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.fail(e.getMessage());
        }


    }


    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public ResultVO parseParent()throws Exception{

        ImportAgentExample success = new ImportAgentExample();
        success.or().andStatusEqualTo(Status.STATUS_1.status)
                .andDealstatusEqualTo(Status.STATUS_2.status)
                .andDatatypeEqualTo(AgImportType.BASICS.name());
        List<ImportAgent>  successList = importAgentMapper.selectByExample(success);

        for (ImportAgent importAgent : successList) {

            AgentExample example = new AgentExample();
            example.or().andAgUniqNumEqualTo(importAgent.getDataid());
            List<Agent>  agents =  agentMapper.selectByExample(example);

            for (Agent agent : agents) {

                AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
                agentBusInfoExample.or().andAgentIdEqualTo(agent.getId()).andStatusEqualTo(Status.STATUS_1.status).andAgZbhIsNotNull().andBusPlatformIsNotNull();
                List<AgentBusInfo>  agentBusInfos= agentBusInfoMapper.selectByExample(agentBusInfoExample);

                for (AgentBusInfo agentBusInfo : agentBusInfos) {

                    String platForm = agentBusInfo.getBusPlatform();
                    String zbh = agentBusInfo.getAgZbh();

                    if(org.apache.commons.lang3.StringUtils.isNotEmpty(zbh) && zbh.contains("-") && zbh.length()>0){
                       int index =  zbh.lastIndexOf("-");
                       if(index!=-1){
                           String parentCode = zbh.substring(0,index);
                           if(org.apache.commons.lang3.StringUtils.isNotEmpty(parentCode) && parentCode.length()>0) {
                               AgentBusInfoExample parentQuery = new AgentBusInfoExample();
                               parentQuery.or().andStatusEqualTo(Status.STATUS_1.status).andAgZbhEqualTo(parentCode).andBusPlatformEqualTo(platForm);
                               List<AgentBusInfo> parentQueryList = agentBusInfoMapper.selectByExample(parentQuery);
                               if(parentQueryList.size()>0){
                                   agentBusInfo.setBusParent(parentQueryList.get(0).getId());
                                   agentBusInfo.setBusRiskParent(parentQueryList.get(0).getId());
                                   agentBusInfo.setBusActivationParent(parentQueryList.get(0).getId());
                                   agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
                               }
                           }

                       }

                    }
                }
            }
        }

        return ResultVO.success(null);
    }




    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public  ResultVO analysisBase(List<ImportAgent>  data,String userid)throws Exception{
        try {
            for (ImportAgent datum : data) {

                try {
                    datum.setDealstatus(Status.STATUS_1.status);//处理中
                    if(1!=importAgentMapper.updateByPrimaryKeySelective(datum))throw new ProcessException("更新记录失败");
                    //代理商编号
                    String dataId = datum.getDataid();
                    //代理商基础数据
                    String datacontent =datum.getDatacontent();
                    AgentExample example = new AgentExample();
                    example.or().andAgUniqNumEqualTo(dataId);

                    List<Agent>  agQuery = agentMapper.selectByExample(example);
                    JSONArray array = JSONArray.parseArray(datacontent);
                    Agent ag =  parseAgentFromJson(array);
                    //修改
                    if(agQuery.size()>0){
                        Agent ag_db = agQuery.get(0);
                        ag.setId(ag_db.getId());
                        ag.setVersion(ag_db.getVersion());
                        ag.setAgRemark(ag.getAgRemark()==null?"(老数据导入)":ag.getAgRemark());
                        if(1!=agentService.updateAgent(ag)){
                            logger.info("更新代理商出错{}",datum.getId());
                            throw new ProcessException("更新代理商出错");
                        }
                        ImportAgent agent =  importAgentMapper.selectByPrimaryKey(datum.getId());
                        agent.setDealstatus(Status.STATUS_2.status);
                        agent.setDealTime(new Date());
                        agent.setDealmsg("修改成功");
                        if(1!=importAgentMapper.updateByPrimaryKeySelective(agent)){
                            logger.info("导入代理商更新失败{}",datum.getId());
                            throw new ProcessException("导入代理商更新失败");
                        }else{
                            logger.info("导入代理商更新成功{}",datum.getId());
                        }
                    //添加
                    }else{
                        ag.setcUser(userid);
                        ag.setImport(true);
                        ag.setAgRemark("(老数据导入)");
                        ag.setCaStatus(Status.STATUS_0.status);
                        Agent ag_db = agentService.insertAgent(ag, Arrays.asList(),userid);
                        try {
                            //todo 生成后台用户
                            agentService.createBackUserbyAgent(ag_db.getId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ImportAgent agent =  importAgentMapper.selectByPrimaryKey(datum.getId());
                        agent.setDealstatus(Status.STATUS_2.status);
                        agent.setDealTime(new Date());
                        agent.setDealmsg("添加成功");
                        if(1!=importAgentMapper.updateByPrimaryKeySelective(agent)){
                            logger.info("导入代理商插入失败{}",datum.getId());
                            throw new ProcessException("导入代理商插入失败");
                        }else{
                            logger.info("导入代理商插入成功{}",datum.getId());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("导入代理商失败{}:{}",datum.getId(),e.getLocalizedMessage());
                    logger.error("导入代理商失败",e);
                    ImportAgent agent =  importAgentMapper.selectByPrimaryKey(datum.getId());
                    agent.setDealstatus(Status.STATUS_3.status);
                    agent.setDealTime(new Date());
                    agent.setDealmsg(e.getMessage());
                    if(1!=importAgentMapper.updateByPrimaryKeySelective(agent)){
                        logger.info("导入代理商插入失败{}",datum.getId());
                        throw new ProcessException("导入代理商插入失败");
                    }else{
                        logger.info("导入代理商插入失败{}",datum.getId());
                    }
                }
            }
            return ResultVO.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("analysisBase err",e);
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public ResultVO analysisPayment(List<ImportAgent>  data,String userid)throws Exception{
        try {
            for (ImportAgent datum : data) {
                try {
                    datum = importAgentMapper.selectByPrimaryKey(datum.getId());
                    datum.setDealstatus(Status.STATUS_1.status);//处理中
                    if(1!=importAgentMapper.updateByPrimaryKeySelective(datum))throw new ProcessException("更新记录失败");
                    //代理商编号
                    String dataId = datum.getDataid();
                    //代理商基础数据
                    String datacontent =datum.getDatacontent();
                    AgentExample example = new AgentExample();
                    example.or().andAgUniqNumEqualTo(dataId);
                    List<Agent>  agQuery = agentMapper.selectByExample(example);
                    if(agQuery.size()==0){
                        throw new ProcessException("代理商交款导入失败，代理商信息未找到("+dataId+")");
                    }

                    Agent agent =  agQuery.get(0);
                    JSONArray array = JSONArray.parseArray(datacontent);
                    Capital capital =  parsePayMentFromJson(array);
                    capital.setcUser(userid);
                    capital.setcAgentId(agent.getId());

                    CapitalExample capitalExample = new CapitalExample();
                    capitalExample.or().andStatusEqualTo(Status.STATUS_1.status).andCAgentIdEqualTo(capital.getcAgentId()).andCAmountEqualTo(capital.getcAmount()).andCTypeEqualTo(capital.getcType());
                    List<Capital>  listC =  capitalMapper.selectByExample(capitalExample);

                    if(listC.size()>0){
                        Capital c =  listC.get(0);
                        if(c.getcPaytime()!=null && capital.getcPaytime()!=null && DateUtil.format(c.getcPaytime(),"yyyy-MM-dd").equals(DateUtil.format(capital.getcPaytime(),"yyyy-MM-dd"))){
                               logger.info("用户已交过款项{},{},{}",capital.getcAmount(),capital.getcType(),capital.getcPaytime());
                            c.setcInCom(capital.getcInCom());
                            c.setcPayType(capital.getcPayType());
                            c.setCloReviewStatus(capital.getCloReviewStatus());
                            c.setVersion(capital.getVersion());
                            capitalMapper.updateByPrimaryKeySelective(c);
                        }else{
                            AgentResult result = accountPaidItemService.insertAccountPaid(capital,Arrays.asList(),userid,false);
                            if(null==result || !result.isOK())throw new ProcessException("代理商交款导入失败"+result.getMsg());
                        }
                    }else{
                        AgentResult result = accountPaidItemService.insertAccountPaid(capital,Arrays.asList(),userid,false);
                        if(null==result || !result.isOK())throw new ProcessException("代理商交款导入失败"+result.getMsg());
                    }
                    ImportAgent payment =  importAgentMapper.selectByPrimaryKey(datum.getId());
                    payment.setDealstatus(Status.STATUS_2.status);//处理成功
                    payment.setDealmsg("成功");
                    payment.setDealTime(new Date());
                    if(1!=importAgentMapper.updateByPrimaryKeySelective(payment)){
                        logger.info("导入代理商付款金额插入失败{}",datum.getId());
                        throw new ProcessException("导入代理商付款金额插入失败");
                    }else{
                        logger.info("导入代理商付款金额插入失败{}",datum.getId());
                    }
                } catch (ProcessException e) {
                    e.printStackTrace();
                    ImportAgent con  = importAgentMapper.selectByPrimaryKey(datum.getId());
                    con.setDealstatus(Status.STATUS_3.status);
                    con.setDealmsg(e.getMsg());
                    con.setDealTime(new Date());
                    if(1!=importAgentMapper.updateByPrimaryKeySelective(con)) {
                        logger.info("导入代理商付款金额插入失败{}失败",con.getId());
                        throw new ProcessException("更新记录失败");
                    }else{
                        logger.info("导入代理商付款金额插入失败{}失败",con.getId());
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    ImportAgent con  = importAgentMapper.selectByPrimaryKey(datum.getId());
                    con.setDealstatus(Status.STATUS_3.status);
                    con.setDealmsg(e.getLocalizedMessage());
                    con.setDealTime(new Date());
                    if(1!=importAgentMapper.updateByPrimaryKeySelective(con)) {
                        logger.info("导入代理商付款金额插入失败{}失败",con.getId());
                        throw new ProcessException("更新记录失败");
                    }else{
                        logger.info("导入代理商付款金额插入失败{}失败",con.getId());
                    }
                }
            }
            return ResultVO.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public ResultVO analysisContract(List<ImportAgent>  data,String userid)throws Exception{
        try {
            for (ImportAgent datum : data) {
                try {
                    datum = importAgentMapper.selectByPrimaryKey(datum.getId());
                    datum.setDealstatus(Status.STATUS_1.status);//处理中
                    if(1!=importAgentMapper.updateByPrimaryKeySelective(datum))throw new ProcessException("更新记录失败");
                    //代理商编号
                    String dataId = datum.getDataid();
                    //代理商基础数据
                    String datacontent =datum.getDatacontent();

                    AgentExample example = new AgentExample();
                    example.or().andAgUniqNumEqualTo(dataId);
                    List<Agent>  agQuery = agentMapper.selectByExample(example);
                    if(agQuery.size()==0){
                        throw new ProcessException("代理商合同导入失败 代理商未找到（"+dataId+"）");
                    }

                    Agent agent =  agQuery.get(0);
                    JSONArray array = JSONArray.parseArray(datacontent);
                    AgentContract agentContract =  parseContractFromJson(array);
                    agentContract.setAgentId(agent.getId());
                    agentContract.setcUser(userid);
                    agentContract.setRemark(agentContract.getRemark()+"(导入)");
                    AgentContractExample agentContractExample = new AgentContractExample();
                    agentContractExample.or().andAgentIdEqualTo(agentContract.getAgentId())
                            .andContNumEqualTo(agentContract.getContNum())
                            .andStatusEqualTo(Status.STATUS_1.status)
                            .andContTypeEqualTo(agentContract.getContType());
                    List<AgentContract> agentContractDb =  agentContractMapper.selectByExample(agentContractExample);
                    ImportAgent con  = importAgentMapper.selectByPrimaryKey(datum.getId());

                    if(agentContractDb.size()==0){
                        logger.info("导入代理商合同信息{}",datum.getId());
                        AgentContract ac =  agentContractService.insertAgentContract(agentContract,Arrays.asList(),userid);
                        logger.info("导入代理商合同信息{}成功",ac.getId());
                        con.setDealmsg("添加成功");
                    }else{
                        AgentContract contract = agentContractDb.get(0);
                        contract.setContType(agentContract.getContType());
                        contract.setContNum(agentContract.getContNum());
                        contract.setContDate(agentContract.getContDate());
                        contract.setContEndDate(agentContract.getContEndDate());
                        contract.setRemark(agentContract.getRemark()+"(导入)");
                        contract.setcUser(userid);
                        logger.info("导入代理商合同信息{}记录应存在",datum.getId());
                        con.setDealmsg("添加成功");
                        agentContractService.update(contract);
                    }
                    con.setDealstatus(Status.STATUS_2.status);
                    con.setDealTime(new Date());
                    if(1!=importAgentMapper.updateByPrimaryKeySelective(con)) {
                        logger.info("导入代理商合同信息{}更新记录失败",con.getId());
                        throw new ProcessException("更新记录失败");
                    }else{
                        logger.info("导入代理商合同信息{}更新记录成功",con.getId());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ImportAgent con  = importAgentMapper.selectByPrimaryKey(datum.getId());
                    con.setDealstatus(Status.STATUS_3.status);
                    con.setDealmsg(e.getMessage());
                    con.setDealTime(new Date());
                    if(1!=importAgentMapper.updateByPrimaryKeySelective(con)) {
                        logger.info("导入代理商合同信息失败{}更新记录失败",con.getId());
                        throw new ProcessException("更新记录失败");
                    }else{
                        logger.info("导入代理商合同信息失败{}更新记录成功",con.getId());
                    }
                }
            }
            return ResultVO.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public ResultVO analysisBus(List<ImportAgent>  data,String userid)throws Exception{

        try {
            for (ImportAgent datum : data) {
                try {
                    datum = importAgentMapper.selectByPrimaryKey(datum.getId());
                    datum.setDealstatus(Status.STATUS_1.status);//处理中
                    datum.setDealTime(new Date());
                    if(1!=importAgentMapper.updateByPrimaryKeySelective(datum))throw new ProcessException("更新记录失败");
                    //代理商编号
                    String dataId = datum.getDataid();
                    //代理商基础数据
                    String datacontent =datum.getDatacontent();

                    AgentExample example = new AgentExample();
                    example.or().andAgUniqNumEqualTo(dataId);
                    List<Agent>  agQuery = agentMapper.selectByExample(example);
                    if(agQuery.size()==0){
                        datum = importAgentMapper.selectByPrimaryKey(datum.getId());
                        datum.setDealstatus(Status.STATUS_3.status);//处理中
                        datum.setDealTime(new Date());
                        datum.setDealmsg("代理商信息不存在("+dataId+")");
                        importAgentMapper.updateByPrimaryKeySelective(datum);
                        continue;
                    }

                    Agent agent =  agQuery.get(0);

                    JSONArray array = JSONArray.parseArray(datacontent);
                    //解析关系表
                    JSONArray busRelArray =  parseBusRelFromJson(array);

                    List<PlatForm>  platForms = businessPlatformService.queryAblePlatForm();

                    List<Dict>  bustype = dictOptionsService.dictList(DictGroup.AGENT.name(),DictGroup.BUS_TYPE.name());

                    List<PayComp>  payCompList = apaycompService.compList();

                    for (int i = 0; i < busRelArray.size(); i++) {

                        AgentBusInfo busItem = parseBusFromJson(busRelArray.getJSONObject(i),platForms,bustype,payCompList);
                        if(busItem==null)continue;
                        AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
                        agentBusInfoExample.or()
                                .andAgentIdEqualTo(busItem.getAgentId())
                                .andBusPlatformEqualTo(busItem.getBusPlatform())
                                .andStatusEqualTo(Status.STATUS_1.status);
                        agentBusInfoExample.or()
                                .andAgentIdEqualTo(busItem.getAgentId())
                                .andBusPlatformEqualTo(busItem.getBusNum())
                                .andStatusEqualTo(Status.STATUS_1.status);
                        List<AgentBusInfo> agentBusInfoExamplelist = agentBusInfoMapper.selectByExample(agentBusInfoExample);

                        if(agentBusInfoExamplelist.size()>0) {
                            logger.info("业务已存在{}{}",busItem.getBusPlatform(),busItem.getAgentId());
                            AgentBusInfo db_agentBusInfo = agentBusInfoExamplelist.get(0);
                            db_agentBusInfo.setBusRegion(busItem.getBusRegion());
                            db_agentBusInfo.setBusSentDirectly(busItem.getBusSentDirectly());
                            db_agentBusInfo.setBusDirectCashback(busItem.getBusDirectCashback());
                            db_agentBusInfo.setBusIndeAss(busItem.getBusIndeAss());
                            db_agentBusInfo.setBusContact(busItem.getBusContact());
                            db_agentBusInfo.setBusContactMobile(busItem.getBusContactMobile());
                            db_agentBusInfo.setBusContactEmail(busItem.getBusContactEmail());
                            db_agentBusInfo.setBusContactPerson(busItem.getBusContactPerson());
                            db_agentBusInfo.setBusRiskEmail(busItem.getBusRiskEmail());
                            db_agentBusInfo.setCloTaxPoint(busItem.getCloTaxPoint());
                            db_agentBusInfo.setCloInvoice(busItem.getCloInvoice());
                            db_agentBusInfo.setCloReceipt(busItem.getCloReceipt());
                            db_agentBusInfo.setCloPayCompany(busItem.getCloPayCompany());
                            db_agentBusInfo.setAgZbh(busItem.getAgZbh());
                            db_agentBusInfo.setBusRegion(busItem.getBusRegion());
                            db_agentBusInfo.setBusScope(busItem.getBusScope());
                            db_agentBusInfo.setBusUseOrgan(busItem.getBusUseOrgan());
                            db_agentBusInfo.setBusType(busItem.getBusType());
                            agentBusinfoService.updateAgentBusInfo(db_agentBusInfo);
                            busItem.setId(db_agentBusInfo.getId());
                        }else{
                            busItem.setcUser(userid);
                            busItem =  agentBusinfoService.agentBusInfoInsert(busItem);
                        }

                        //更新导入业务
                        ImportAgentExample importAgentExample = new ImportAgentExample();
                        importAgentExample.or().andDatatypeEqualTo(AgImportType.BUSINESS.name())
                                .andDealstatusEqualTo(Status.STATUS_0.status)
                                .andDataidEqualTo(busItem.getBusNum());
                        List<ImportAgent>  importAgentsBusiness = importAgentMapper.selectByExample(importAgentExample);
                        for (ImportAgent agentsBusiness : importAgentsBusiness) {
                            agentsBusiness.setDealTime(new Date());
                            agentsBusiness.setDealstatus(Status.STATUS_2.status);
                            agentsBusiness.setDealmsg("处理完成");
                            importAgentMapper.updateByPrimaryKeySelective(agentsBusiness);
                        }


                        List<AgentColinfo> colinfos = busItem.getAgentColinfoList();
                        if(colinfos.size()>0){
                            AgentColinfo colinfo = colinfos.get(0);
                            AgentColinfoExample agentColinfoExample_uniq  = new AgentColinfoExample();
                            agentColinfoExample_uniq.or()
                                    .andAgentIdEqualTo(busItem.getAgentId())
                                    .andStatusEqualTo(Status.STATUS_1.status);
                            if(agentColinfoMapper.countByExample(agentColinfoExample_uniq)==0){
                                    colinfo.setcUser(userid);
                                    AgentColinfoExample agentColinfoExample = new AgentColinfoExample();
                                    agentColinfoExample.or()
                                            .andAgentIdEqualTo(busItem.getAgentId())
                                            .andCloRealnameEqualTo(colinfo.getCloRealname())
                                            .andCloBankAccountEqualTo(colinfo.getCloBankAccount());
                                    //添加收款账户
                                    AgentColinfo ac = null;
                                    List<AgentColinfo>  colinfodb = agentColinfoMapper.selectByExample(agentColinfoExample);
                                    if(colinfodb.size()==0){
                                        //添加收款账户
                                        colinfo.setImport(true);
                                        ac =  agentColinfoService.agentColinfoInsert(colinfo,Arrays.asList());
                                    }else{
                                        ac = colinfodb.get(0);
                                        ac.setCloTaxPoint(colinfo.getCloTaxPoint());
                                        ac.setCloInvoice(colinfo.getCloInvoice());
                                        ac.setBranchLineNum(colinfo.getBranchLineNum());
                                        ac.setCloBankBranch(colinfo.getCloBankBranch());
                                        agentColinfoService.updateAgentColinfo(ac);
                                    }
                                    AgentColinfoRelExample agentColinfoRelExample = new AgentColinfoRelExample();
                                    agentColinfoRelExample.or().andAgentbusidEqualTo(busItem.getId()).andAgentidEqualTo(busItem.getAgentId())
                                            .andAgentColinfoidEqualTo(ac.getId()).andBusPlatformEqualTo(busItem.getBusPlatform()).andStatusEqualTo(Status.STATUS_1.status);
                                    List<AgentColinfoRel>  listRel_db = agentColinfoRelMapper.selectByExample(agentColinfoRelExample);

                                    //没有建立关系就建立关系
                                    if(listRel_db.size()==0) {
                                        //添加收款账户关系
                                        AgentColinfoRel agentColinfoRel = new AgentColinfoRel();
                                        agentColinfoRel.setcUse(userid);
                                        agentColinfoRel.setAgentid(ac.getAgentId());
                                        agentColinfoRel.setAgentColinfoid(ac.getId());
                                        agentColinfoRel.setBusPlatform(busItem.getBusPlatform());
                                        agentColinfoRel.setAgentbusid(busItem.getId());
                                        AgentResult rel = agentColinfoService.saveAgentColinfoRel(agentColinfoRel, userid);
                                        logger.info("代理商导入收款账户业务关系{},{}",busItem.getId(),rel.getMsg());
                                    }else{
                                        logger.info("代理商导入收款账户业务关系已存在{},{}",busItem.getId(),listRel_db.get(0).getId());
                                    }
                            }else{
                                AgentColinfoExample agentColinfoExample = new AgentColinfoExample();
                                agentColinfoExample.or()
                                        .andAgentIdEqualTo(busItem.getAgentId())
                                        .andCloRealnameEqualTo(colinfo.getCloRealname())
                                        .andCloBankAccountEqualTo(colinfo.getCloBankAccount())
                                        .andStatusEqualTo(Status.STATUS_1.status);
                                //添加收款账户
                                List<AgentColinfo>  colinfodb = agentColinfoMapper.selectByExample(agentColinfoExample);
                                if(colinfodb.size()>0){
                                    AgentColinfo agentColinfo = colinfodb.get(0);
                                    agentColinfo.setCloInvoice(colinfo.getCloInvoice());
                                    agentColinfo.setCloTaxPoint(colinfo.getCloTaxPoint());
                                    agentColinfo.setBranchLineNum(colinfo.getBranchLineNum());
                                    agentColinfo.setCloBankBranch(colinfo.getCloBankBranch());
                                    agentColinfoMapper.updateByPrimaryKeySelective(agentColinfo);
                                }

                            }

                        }
                    }
                    ImportAgent payment =  importAgentMapper.selectByPrimaryKey(datum.getId());
                    payment.setDealstatus(Status.STATUS_2.status);//处理成功
                    payment.setDealmsg("成功");
                    payment.setDealTime(new Date());
                    if(1!=importAgentMapper.updateByPrimaryKeySelective(payment)){
                        logger.info("代理商导入业务{}失败",datum.getId());
                        throw new ProcessException("代理商导入业务更新失败（"+datum.getId()+"）");
                    }else{
                        logger.info("代理商导入业务{}成功",datum.getId());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ImportAgent payment =  importAgentMapper.selectByPrimaryKey(datum.getId());
                    payment.setDealstatus(Status.STATUS_3.status);//处理成功
                    payment.setDealmsg(e.getLocalizedMessage());
                    payment.setDealTime(new Date());
                    if(1!=importAgentMapper.updateByPrimaryKeySelective(payment)){
                        logger.info("代理商导入业务{}失败",datum.getId());
                        throw new ProcessException("代理商导入业务");
                    }else{
                        logger.info("代理商导入业务{}失败",datum.getId());
                    }
                }



            }



            return ResultVO.success(null);
        } catch (Exception e) {
            logger.info("代理商导入业务失败{}",e.getMessage());
            e.printStackTrace();
            return ResultVO.fail("代理商导入业务失败");
        }

    }


    private AgentContract parseContractFromJson(JSONArray obj)throws Exception{
        try {
            logger.info("解析json{}",obj.toJSONString());
            AgentContract a = new AgentContract();
            String id =  obj.getString(0);
            if(obj.size()>2 && null!=obj.getString(2))
                a.setContNum(obj.getString(2));
            if(obj.size()>3 && null!=obj.getString(3)) {
                //便利查询合同类型
                List<Dict>  list = dictOptionsService.dictList(DictGroup.AGENT.name(),DictGroup.CONTRACT_TYPE.name());
                BigDecimal v = null;
                for (Dict dict : list) {
                    if(dict.getdItemname().equals(obj.getString(3).trim())){
                        v= new BigDecimal(dict.getdItemvalue());
                    }
                }
                if(null==v)
                    logger.info("合同类型为空{}",obj.toJSONString());
                a.setContType(v==null?new BigDecimal(-1):v);
            }
            if(obj.size()>4 && null!=obj.getString(4)) {
                //便利查询合同类型
                try {
                    a.setContDate(org.apache.commons.lang.time.DateUtils.parseDate(obj.getString(4),new String[]{"yyyy/MM/dd"}));
                } catch (ParseException e) {
                    try {
                        a.setContDate(org.apache.commons.lang.time.DateUtils.parseDate(obj.getString(4),new String[]{"yyyy-MM-dd"}));
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            if(obj.size()>5 && null!=obj.getString(5)) {
                //便利查询合同类型
                try {
                    a.setContEndDate(org.apache.commons.lang.time.DateUtils.parseDate(obj.getString(5),new String[]{"yyyy/MM/dd"}));
                } catch (ParseException e) {
                    try {
                        a.setContEndDate(org.apache.commons.lang.time.DateUtils.parseDate(obj.getString(5),new String[]{"yyyy-MM-dd"}));
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            if(obj.size()>6 && null!=obj.getString(6)) {
                //便利查询合同类型
                a.setRemark(obj.getString(6));
            }
            return a;
        } catch (Exception e) {
            logger.info("解析json{}",obj.toJSONString());
            e.printStackTrace();
            throw e;
        }
    }
    private Agent parseAgentFromJson(JSONArray obj)throws Exception{
        logger.info("解析json{}",obj.toJSONString());
        try {
            Agent a = new Agent();
            String id =  obj.getString(0);
            a.setAgUniqNum(id);
            if(obj.size()>1 && null!=obj.getString(1))
            a.setAgName(obj.getString(1));
            if(obj.size()>2 && null!=obj.getString(2))
            a.setAgNature(AgNature.getAgNatureMsgString(obj.getString(2)));
            if(obj.size()>3 && StringUtils.isNotBlank(obj.getString(3)))
            a.setAgCapital(new BigDecimal(obj.getString(3)));
            if(obj.size()>4 && null!=obj.getString(4))
                a.setAgBusLic(obj.getString(4));
            if(obj.size()>5 && StringUtils.isNotBlank(obj.getString(5))) {
                try {
                    a.setAgBusLicb(org.apache.commons.lang.time.DateUtils.parseDate(obj.getString(5), new String[]{"yyyy-MM-dd"}));
                } catch (ParseException e) {
                    try {
                        a.setAgBusLicb(org.apache.commons.lang.time.DateUtils.parseDate(obj.getString(5), new String[]{"yyyy/MM/dd"}));
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }

                }
            }

            if(obj.size()>6 && StringUtils.isNotBlank(obj.getString(6))) {
                try {
                    a.setAgBusLice(org.apache.commons.lang.time.DateUtils.parseDate(obj.getString(6), new String[]{"yyyy-MM-dd"}));
                } catch (ParseException e) {
                    try {
                        a.setAgBusLice(org.apache.commons.lang.time.DateUtils.parseDate(obj.getString(6), new String[]{"yyyy/MM/dd"}));
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            if(obj.size()>7 && null!=obj.getString(7))
                a.setAgLegal(obj.getString(7));
            if(obj.size()>8 && null!=obj.getString(8))
                a.setAgLegalCertype(AgCertType.getAgCertTypeMsgString(obj.getString(8)));
            if(obj.size()>9 && null!=obj.getString(9))
                a.setAgLegalCernum(obj.getString(9));
            if(obj.size()>10 && null!=obj.getString(10))
                a.setAgLegalMobile(obj.getString(10));
            if(obj.size()>11 && null!=obj.getString(11))
                a.setAgHead(obj.getString(11));
            if(obj.size()>12 && null!=obj.getString(12))
                a.setAgHeadMobile(obj.getString(12));
            if(obj.size()>13 && null!=obj.getString(13))
                a.setAgRegAdd(obj.getString(13));
            if(obj.size()>14 && null!=obj.getString(14))
                a.setAgBusScope(obj.getString(14));
            if(obj.size()>15 && StringUtils.isNotBlank(obj.getString(15))) {
                COrganization org = departmentService.getByName(obj.getString(15));
                if(org!=null) {
                    a.setAgDocPro(org == null ? null : org.getId() + "");
                }else{
                    COrganization org_pro =  departmentService.getByUserName(obj.getString(15));
                        a.setAgDocPro(org_pro == null ? null : org_pro.getId() + "");
                }
            }
            if(obj.size()>16 && StringUtils.isNotBlank(obj.getString(16))){
                String region = obj.getString(16);
                if("北方".equals(region)) {
                    region = "北方大区";
                }
                if("南方".equals(region)) {
                    region = "南方大区";
                }
                if("北京".equals(region)) {
                    region = "北京市场部";
                }
                COrganization org = departmentService.getByName(region);
                if (org != null) {
                    a.setAgDocDistrict(org == null ? null : org.getId() + "");
                } else {
                    COrganization org_DocDistrict = departmentService.getByUserNameParent(region);
                    a.setAgDocPro(org_DocDistrict == null ? null : org_DocDistrict.getId() + "");
                }
            }
            return a;
        } catch (Exception e) {
            logger.info("解析json{}:{}",e.getMessage(),obj.toJSONString());
            e.printStackTrace();
            throw e;
        }
    }


    private Capital parsePayMentFromJson(JSONArray obj)throws Exception{
        logger.info("解析json{}",obj.toJSONString());
        try {
            Capital c = new Capital();
            if(obj.size() >2 && org.apache.commons.lang3.StringUtils.isNotEmpty(obj.getString(2))){
                String ctype = obj.getString(2);
                Dict dict = dictOptionsService.findDictByName("AGENT","CAPITAL_TYPE",ctype);
                if(dict!=null){
                    c.setcType(dict.getdItemvalue());
                }else{
                    c.setcType(ctype);
                }


            }
            if(obj.size() >3 && org.apache.commons.lang3.StringUtils.isNotEmpty(obj.getString(3))){
                c.setcAmount(new BigDecimal(obj.getString(3)));

            }
            if(obj.size() >4 && org.apache.commons.lang3.StringUtils.isNotEmpty(obj.getString(4))){
                try {
                    c.setcPaytime(org.apache.commons.lang.time.DateUtils.parseDate(obj.getString(4),new String[]{"yyyy/MM/dd"}));
                } catch (ParseException e) {
                    try {
                        c.setcPaytime(org.apache.commons.lang.time.DateUtils.parseDate(obj.getString(4),new String[]{"yyyy-MM-dd"}));
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            if(obj.size() >5 && org.apache.commons.lang3.StringUtils.isNotEmpty(obj.getString(5))){
                try {
                    List<PayComp> list =  apaycompService.recCompList();
                    for (PayComp payComp : list) {
                        if(payComp.getComName().equals(obj.getString(5))) {
                            c.setcInCom(payComp.getId());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            c.setcPayType("YHHK");
            c.setCloReviewStatus(AgStatus.Approved.status);
            c.setcFqInAmount(c.getcAmount());
            if(obj.size()>5 && org.apache.commons.lang3.StringUtils.isNotEmpty(obj.getString(5))){
                c.setRemark(obj.getString(5)+"(导入)");
            }else{
                c.setRemark("(老数据导入)");
            }

            return c;
        } catch (Exception e) {
            logger.info("解析json{}:{}",e.getMessage(),obj.toJSONString());
            e.printStackTrace();
            throw new ProcessException("parsePayMentFromJson错误"+e.getLocalizedMessage());
        }
    }

    private JSONArray parseBusRelFromJson(JSONArray obj)throws Exception{

        logger.info("解析json{}",obj.toJSONString());
        try {
            JSONArray res = new JSONArray();

            String uniqNum = obj.getString(0);
            String agName = obj.getString(1);
            String cwzbh = obj.getString(2);
            AgentExample example  = new AgentExample();
            example.or().andAgUniqNumEqualTo(uniqNum);
            List<Agent>  AG =  agentMapper.selectByExample(example);

            if(AG.size()==0){
                throw new ProcessException("代理商查询为空");
            }
            JSONObject busitem1 = new JSONObject();
            if(obj.size()>=4 && StringUtils.isNotBlank(obj.getString(3))) {
                busitem1.put("agId", AG.get(0).getId());
                busitem1.put("uniqNum", uniqNum);
                busitem1.put("agName", agName);
                busitem1.put("cwzbh", cwzbh);
                busitem1.put("p", obj.getString(3));
                busitem1.put("pn", obj.getString(4));
                res.add(busitem1);
            }
            return res;
//            JSONObject busitem1 = new JSONObject();
//            JSONObject busitem2 = new JSONObject();
//            JSONObject busitem3 = new JSONObject();
//            JSONObject busitem4 = new JSONObject();
//            JSONObject busitem5 = new JSONObject();
//            JSONObject busitem6 = new JSONObject();
//            JSONObject busitem7 = new JSONObject();
//            JSONObject busitem8 = new JSONObject();
//            JSONObject busitem9 = new JSONObject();
//
//            if(obj.size()>4 && StringUtils.isNotBlank(obj.getString(3))) {
//                busitem1.put("agId", AG.get(0).getId());
//                busitem1.put("uniqNum", uniqNum);
//                busitem1.put("agName", agName);
//                busitem1.put("cwzbh", cwzbh);
//                busitem1.put("p", obj.getString(3));
//                busitem1.put("pn", obj.getString(4));
//                res.add(busitem1);
//            }
//            if(obj.size()>6 && StringUtils.isNotBlank(obj.getString(5))) {
//                busitem2.put("agId", AG.get(0).getId());
//                busitem2.put("uniqNum", uniqNum);
//                busitem2.put("agName", agName);
//                busitem2.put("cwzbh", cwzbh);
//                busitem2.put("p", obj.getString(5));
//                busitem2.put("pn", obj.getString(6));
//                res.add(busitem2);
//            }
//
//            if(obj.size()>8 && StringUtils.isNotBlank(obj.getString(7))) {
//                busitem3.put("agId", AG.get(0).getId());
//                busitem3.put("uniqNum", uniqNum);
//                busitem3.put("agName", agName);
//                busitem3.put("cwzbh", cwzbh);
//                busitem3.put("p", obj.getString(7));
//                busitem3.put("pn", obj.getString(8));
//                res.add(busitem3);
//            }
//            if(obj.size()>10 && StringUtils.isNotBlank(obj.getString(9))) {
//                busitem4.put("agId", AG.get(0).getId());
//                busitem4.put("uniqNum", uniqNum);
//                busitem4.put("agName", agName);
//                busitem4.put("cwzbh", cwzbh);
//                busitem4.put("p", obj.getString(9));
//                busitem4.put("pn", obj.getString(10));
//                res.add(busitem4);
//            }
//
//            if(obj.size()>12 && StringUtils.isNotBlank(obj.getString(11))) {
//                busitem5.put("agId", AG.get(0).getId());
//                busitem5.put("uniqNum", uniqNum);
//                busitem5.put("agName", agName);
//                busitem5.put("cwzbh", cwzbh);
//                busitem5.put("p", obj.getString(11));
//                busitem5.put("pn", obj.getString(12));
//                res.add(busitem5);
//            }
//
//            if(obj.size()>14 && StringUtils.isNotBlank(obj.getString(13))) {
//                busitem6.put("agId", AG.get(0).getId());
//                busitem6.put("uniqNum", uniqNum);
//                busitem6.put("agName", agName);
//                busitem6.put("cwzbh", cwzbh);
//                busitem6.put("p", obj.getString(13));
//                busitem6.put("pn", obj.getString(14));
//                res.add(busitem6);
//            }
//
//            if(obj.size()>16 && StringUtils.isNotBlank(obj.getString(15))) {
//                busitem7.put("agId", AG.get(0).getId());
//                busitem7.put("uniqNum", uniqNum);
//                busitem7.put("agName", agName);
//                busitem7.put("cwzbh", cwzbh);
//                busitem7.put("p", obj.getString(15));
//                busitem7.put("pn", obj.getString(16));
//                res.add(busitem7);
//            }
//
//            if(obj.size()>18 &&StringUtils.isNotBlank(obj.getString(17))) {
//                busitem8.put("agId", AG.get(0).getId());
//                busitem8.put("uniqNum", uniqNum);
//                busitem8.put("agName", agName);
//                busitem8.put("cwzbh", cwzbh);
//                busitem8.put("p", obj.getString(17));
//                busitem8.put("pn", obj.getString(18));
//                res.add(busitem8);
//            }
//
//            if(obj.size()>20 &&StringUtils.isNotBlank(obj.getString(19))) {
//                busitem9.put("agId", AG.get(0).getId());
//                busitem9.put("uniqNum", uniqNum);
//                busitem9.put("agName", agName);
//                busitem9.put("cwzbh", cwzbh);
//                busitem9.put("p", obj.getString(19));
//                busitem9.put("pn", obj.getString(20));
//                res.add(busitem9);
//            }
//
//            return res;
        } catch (ProcessException e) {
            logger.info("解析json{}:{}",e.getMessage(),obj.toJSONString());
            e.printStackTrace();
            throw e;
        }
    }


    private static List<Dict> bus_use_organ = null;

    /**
     * 解析业务
     * @param obj
     * @return
     * @throws Exception
     */
    private AgentBusInfo parseBusFromJson(JSONObject obj, List<PlatForm>  platForms,List<Dict>  bustype, List<PayComp>  payCompList)throws Exception{
        logger.info("解析json{}",obj.toJSONString());
        if(bus_use_organ==null){
            bus_use_organ = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.USE_SCOPE.name());
        }
        try {
            String agid =   obj.getString("agId");
            String uniqNum =   obj.getString("uniqNum");
            String agName =    obj.getString("agName");
            String cwzbh =    obj.getString("cwzbh");
            String p =   obj.getString("p");//业务平台
            String pn =    obj.getString("pn");//业务平台编码

            ImportAgentExample importAgentExample = new ImportAgentExample();
            //根据业务平台编号查询业务平台信息
//            importAgentExample.or().andDataidEqualTo(uniqNum).andDatatypeEqualTo(AgImportType.BUSINESS.name()).andDealstatusEqualTo(Status.STATUS_0.status);
            importAgentExample.or().andDataidEqualTo(pn).andDatatypeEqualTo(AgImportType.BUSINESS.name()).andDealstatusEqualTo(Status.STATUS_0.status);
            List<ImportAgent>  list = importAgentMapper.selectByExampleWithBLOBs(importAgentExample);
            if(list.size()==0)return null;
            ImportAgent img_db = list.get(0);

            String bus_json = img_db.getDatacontent();
            JSONArray bus_json_array = JSONArray.parseArray(bus_json);

            AgentBusInfo ab = new AgentBusInfo();

            ab.setAgentId(agid);

            ab.setBusNum(pn);

            ab.setAgZbh(cwzbh);

            if(StringUtils.isNotBlank(p)) {
                p = p.trim();
                if(p.contains("MPOS")){
                    p = p.replace("MPOS","手刷");
                }
                for (PlatForm platForm : platForms) {
                    if (platForm.getPlatformName().equals(p)) {
                        ab.setBusPlatform(platForm.getPlatformNum());
                        break;
                    }
                }
            }

            if(bus_json_array.size()>5 && StringUtils.isNotBlank(bus_json_array.getString(5))){
                String type = bus_json_array.getString(5).trim();
                for (Dict dict : bustype) {
                    if(dict.getdItemname().equals(type))
                    ab.setBusType(dict.getdItemvalue());
                }
            }

            if(bus_json_array.size()>10 && StringUtils.isNotBlank(bus_json_array.getString(10)))
            ab.setBusSentDirectly(BigDecimal.valueOf(yesorno.indexOf(bus_json_array.getString(10))));//是否直接返现
            if(bus_json_array.size()>11 && StringUtils.isNotBlank(bus_json_array.getString(11)))
            ab.setBusDirectCashback(BigDecimal.valueOf(yesorno.indexOf(bus_json_array.getString(11))));
            if(bus_json_array.size()>12 && StringUtils.isNotBlank(bus_json_array.getString(12)))
            ab.setBusIndeAss(BigDecimal.valueOf(yesorno.indexOf(bus_json_array.getString(12))));
            if(bus_json_array.size()>13 && StringUtils.isNotBlank(bus_json_array.getString(13)))
            ab.setBusContact(bus_json_array.getString(13));
            if(bus_json_array.size()>14 && StringUtils.isNotBlank(bus_json_array.getString(14)))
            ab.setBusContactMobile(bus_json_array.getString(14));
            if(bus_json_array.size()>15 && StringUtils.isNotBlank(bus_json_array.getString(15)))
            ab.setBusContactEmail(bus_json_array.getString(15));
            if(bus_json_array.size()>16 && StringUtils.isNotBlank(bus_json_array.getString(16)))
            ab.setBusContactPerson(bus_json_array.getString(16));
            if(bus_json_array.size()>17 && StringUtils.isNotBlank(bus_json_array.getString(17)))
            ab.setBusRiskEmail(bus_json_array.getString(17));

            //税点
            if(bus_json_array.size()>18 && StringUtils.isNotBlank(bus_json_array.getString(18)))
            ab.setCloTaxPoint(bus_json_array.getBigDecimal(18));

            //是否开具分润发票
            if(bus_json_array.size()>19 && StringUtils.isNotBlank(bus_json_array.getString(19)))
            ab.setCloInvoice(BigDecimal.valueOf(yesorno.indexOf(bus_json_array.getString(19))));

            //是否开收据
            if(bus_json_array.size()>20 && StringUtils.isNotBlank(bus_json_array.getString(20)))
            ab.setCloReceipt(BigDecimal.valueOf(yesorno.indexOf(bus_json_array.getString(20))));

            //使用范围
            if(bus_json_array.size()>28 && StringUtils.isNotBlank(bus_json_array.getString(28)) && bus_use_organ!=null) {
                for (Dict dict : bus_use_organ) {
                    if(dict.getdItemname().equals(bus_json_array.getString(28))) {
                        ab.setBusUseOrgan(dict.getdItemvalue());
                    }
                }
            }
            //业务范围
            if(bus_json_array.size()>29 && StringUtils.isNotBlank(bus_json_array.getString(29))) {
                ab.setBusScope(BUS_SCOP.get(bus_json_array.getString(29)));
            }



            //业务区域
            if(bus_json_array.size()>6 && StringUtils.isNotBlank(bus_json_array.getString(6))){
               String busregion =  bus_json_array.getString(6);
                String codeName[] = busregion.split(",");
                DPosRegionExample example = new DPosRegionExample();
                example.or().andNameIn(Arrays.asList(codeName));
                List<DPosRegion> regions =  dPosRegionMapper.selectByExample(example);
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < regions.size(); i++) {
                    sb.append(regions.get(i).getCode()).append( (i==regions.size()-1)?"":"," );
                }
                ab.setBusRegion(sb.toString());
            }

            //打款公司
            if(bus_json_array.size()>21 && StringUtils.isNotBlank(bus_json_array.getString(21)))
            for (PayComp payComp : payCompList) {
                if(payComp.getComName().equals(bus_json_array.getString(21))) {
                    ab.setCloPayCompany(payComp.getId());
                    break;
                }
            }

            //设置打款账户
            if(bus_json_array.size()>22 && StringUtils.isNotBlank(bus_json_array.getString(22))) {
                AgentColinfo colinfo = new AgentColinfo();
                //收款账户 对公对私
                if (bus_json_array.size()>22 && StringUtils.isNotBlank(bus_json_array.getString(22)))
                    colinfo.setCloType(BigDecimal.valueOf(gs.indexOf(bus_json_array.getString(22))));
                //收款账户 realname
                if (bus_json_array.size()>23 && StringUtils.isNotBlank(bus_json_array.getString(23)))
                    colinfo.setCloRealname(bus_json_array.getString(23));
                //收款账户 卡号
                if (bus_json_array.size()>24 && StringUtils.isNotBlank(bus_json_array.getString(24)))
                    colinfo.setCloBankAccount(bus_json_array.getString(24));
                //收款账户 开户支行
                if (bus_json_array.size()>25 && StringUtils.isNotBlank(bus_json_array.getString(25)))
                    colinfo.setCloBankBranch(bus_json_array.getString(25));
                //收款账户 开户支行联号
                if (bus_json_array.size()>26 && StringUtils.isNotBlank(bus_json_array.getString(26)))
                    colinfo.setBranchLineNum(bus_json_array.getString(26));
                //是否开具分润发票
                if(bus_json_array.size()>19 && StringUtils.isNotBlank(bus_json_array.getString(19))) {
                    colinfo.setCloInvoice(BigDecimal.valueOf(yesorno.indexOf(bus_json_array.getString(19))));
                }
                //税点
                if(bus_json_array.size()>18  && StringUtils.isNotBlank(bus_json_array.getString(18))) {
                    colinfo.setCloTaxPoint(bus_json_array.getBigDecimal(18));
                }
                colinfo.setAgentId(ab.getAgentId());
                colinfo.setRemark("老数据导入");
                colinfo.setStatus(Status.STATUS_1.status);
                colinfo.setVarsion(Status.STATUS_1.status);
                colinfo.setcTime(new Date());
                colinfo.setcUtime(new Date());
                ab.setAgentColinfoList(Arrays.asList(colinfo));
            }else{
                ab.setAgentColinfoList(Arrays.asList());
            }

            img_db.setDealstatus(Status.STATUS_2.status);
            img_db.setDealmsg("获取成功");
            importAgentMapper.updateByPrimaryKeySelective(img_db);
            return ab;
        } catch (Exception e) {
            logger.info("解析json{}:{}",e.getMessage(),obj.toJSONString());
            e.printStackTrace();
            throw e;
        }
    }



    /**
     * 查询代理商信息
     * @param page
     * @param importAgent
     * @return
     */
    @Override
    public PageInfo queryList(PageInfo page, ImportAgent importAgent){
        ImportAgentExample example = new ImportAgentExample();
        ImportAgentExample.Criteria c = example.or();
        if(importAgent!=null && org.apache.commons.lang.StringUtils.isNotEmpty(importAgent.getDatatype())) {
            c.andDatatypeEqualTo(importAgent.getDatatype());
        }
        if(importAgent!=null && importAgent.getDealstatus()!=null) {
            c.andDealstatusEqualTo(importAgent.getDealstatus());
        }
        if(importAgent!=null && StringUtils.isNotBlank(importAgent.getBatchcode())) {
            c.andBatchcodeEqualTo(importAgent.getBatchcode());
        }
        c.andStatusEqualTo(Status.STATUS_1.status);
        int count = importAgentMapper.countByExample(example);
        example.setOrderByClause(" c_time desc ");
        example.setPage(new Page(page.getFrom(),page.getPagesize()));
        List<ImportAgent> list = importAgentMapper.selectByExample(example);
        page.setRows(list);
        page.setTotal(count);
        logger.info("=======================");
        return page;
    }


    /**
     * 处理信息多样格式导入
     * @param user
     * @param list
     * @return
     */
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    @Override
    public ResultVO importAgentBusInfoBusInfoFromExcel(String type,String user, List<Object> list) throws Exception{
        //更新业务信息
        ResultVO resultVO = null;
        switch (type) {
            case "AGENT":
                return importAgentInfo(user, list);
            case "BUSINESS":
                 return importBusInfo(user, list);
            case"BANK":
                 return importBankInfo(user, list);
            case"CONTARCT":
                 return importContractInfo(user, list);
        }
        return ResultVO.success(null);

    }

    private static String prefix_importContractInfo = "导入合同数据：";
    private ResultVO importContractInfo(String user, List<Object> list){
        logger.info(prefix_importContractInfo+"{},{}",user,list);
        String  ag = list.get(0)+"";//唯一码
        String agName = list.size()>1?list.get(1)+"":"";
        String hetongbianhao = list.size()>2?list.get(2)+"":"";
        String xieyileixing = list.size()>3?list.get(3)+"":"";
        String qianyueshijian = list.size()>4?list.get(4)+"":"";
        String hetongdaoqiri = list.size()>5?list.get(5)+"":"";
        String beizhu = list.size()>6?list.get(6)+"":"";
        AgentExample example = new AgentExample();
        example.or().andAgUniqNumEqualTo(ag);
        List<Agent> agents = agentMapper.selectByExample(example);
        Agent agent = null;
        if(agents.size()==1){
            agent= agents.get(0);
        }else{
            logger.info(prefix_importContractInfo+"代理商信息未找到{},{}",user,list);
            return ResultVO.fail("代理商信息未找到");
        }

        AgentContract agentContract = null;
        AgentContractExample agentContractExample = new AgentContractExample();
        agentContractExample.or().andAgentIdEqualTo(agent.getId()).andContNumEqualTo(hetongbianhao).andStatusEqualTo(Status.STATUS_1.status);
        List<AgentContract> agentContracts = agentContractMapper.selectByExample(agentContractExample);
        if(agentContracts.size()>0){
            agentContract = agentContracts.get(0);
        }else{
            agentContract = new AgentContract();
        }

        if(StringUtils.isBlank(hetongbianhao) || !"null".equalsIgnoreCase(hetongbianhao))
            agentContract.setContNum(hetongbianhao);
        if(StringUtils.isBlank(xieyileixing) || !"null".equalsIgnoreCase(xieyileixing)) {
            //便利查询合同类型
            List<Dict>  CONTRACT_TYPE_list = dictOptionsService.dictList(DictGroup.AGENT.name(),DictGroup.CONTRACT_TYPE.name());
            BigDecimal v = null;
            for (Dict dict : CONTRACT_TYPE_list) {
                if(dict.getdItemname().equals(xieyileixing)){
                    v= new BigDecimal(dict.getdItemvalue());
                }
            }
            if(null==v)
                logger.info(prefix_importContractInfo+"协议类型为空,{},{},{},{}",ag,hetongbianhao,user,list);
            agentContract.setContType(v==null?new BigDecimal(-1):v);
        }
        if(StringUtils.isBlank(qianyueshijian) || !"null".equalsIgnoreCase(qianyueshijian)) {
            //便利查询合同类型
            try {
                agentContract.setContDate(org.apache.commons.lang.time.DateUtils.parseDate(qianyueshijian,new String[]{"yyyy/MM/dd"}));
            } catch (ParseException e) {
                try {
                    agentContract.setContDate(org.apache.commons.lang.time.DateUtils.parseDate(qianyueshijian,new String[]{"yyyy-MM-dd"}));
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if(StringUtils.isBlank(qianyueshijian) || !"null".equalsIgnoreCase(qianyueshijian)) {
            //便利查询合同类型
            try {
                agentContract.setContEndDate(org.apache.commons.lang.time.DateUtils.parseDate(qianyueshijian,new String[]{"yyyy/MM/dd"}));
            } catch (ParseException e) {
                try {
                    agentContract.setContEndDate(org.apache.commons.lang.time.DateUtils.parseDate(qianyueshijian,new String[]{"yyyy-MM-dd"}));
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if(StringUtils.isBlank(beizhu) || !"null".equalsIgnoreCase(beizhu)) {
            agentContract.setRemark(beizhu);
        }

        if(StringUtils.isBlank(agentContract.getId())){
            agentContract.setAgentId(agent.getId());
            agentContract.setcTime(Calendar.getInstance().getTime());
            agentContract.setcUser(user);
            agentContract.setCloReviewStatus(AgStatus.Approved.status);
            agentContract.setStatus(Status.STATUS_1.status);
            agentContract.setVersion(Status.STATUS_1.status);
            agentContract.setAppendAgree(Status.STATUS_0.status);
            agentContract.setId(idService.genId(TabId.a_agent_contract));
            if(1==agentContractMapper.insertSelective(agentContract)){
                logger.info(prefix_importContractInfo+"导入成功,{},{},{},{}",ag,hetongbianhao,user,list);
            }else{
                logger.info(prefix_importContractInfo+"导入失败,{},{},{},{}",ag,hetongbianhao,user,list);
            }
        }else{
            if(1==agentContractMapper.updateByPrimaryKeySelective(agentContract)){
                logger.info(prefix_importContractInfo+"修改成功,{},{},{},{}",ag,hetongbianhao,user,list);
            }else{
                logger.info(prefix_importContractInfo+"修改失败,{},{},{},{}",ag,hetongbianhao,user,list);
            }
        }
        return ResultVO.success("");
    }

    private static String prefix_importAgentInfo = "导入代理商数据：";
    private ResultVO importAgentInfo(String user, List<Object> list){
        logger.info(prefix_importAgentInfo+"{},{}",user,list);
        String  ag = list.get(0)+"";//唯一码
        String agName = list.size()>1?list.get(1)+"":"";
        String gongsixingzhi = list.size()>2?list.get(2)+"":"";
        String zuceziben = list.size()>3?list.get(3)+"":"";
        String yingyezhizhao = list.size()>4?list.get(4)+"":"";
        String yingyezhizhaoqishishijian = list.size()>5?list.get(5)+"":"";
        String yingyezhizhaojieshushijian = list.size()>6?list.get(6)+"":"";
        String farenxingming = list.size()>7?list.get(7)+"":"";
        String farenzhengjianleixing = list.size()>8?list.get(8)+"":"";
        String farenshenfenzhenghao = list.size()>9?list.get(9)+"":"";
        String farenlianxidianhua = list.size()>10?list.get(10)+"":"";
        String fuzeren = list.size()>11?list.get(11)+"":"";
        String fuzerendianhua = list.size()>12?list.get(12)+"":"";
        String zhucedizhi = list.size()>13?list.get(13)+"":"";
        String yingyefanwei = list.size()>14?list.get(14)+"":"";
        String yewuduijieshengqu = list.size()>15?list.get(15)+"":"";
        String yewuduijiedaqu = list.size()>16?list.get(16)+"":"";
        String beizhu = list.size()>17?list.get(17)+"":"";
        AgentExample example = new AgentExample();
        example.or().andAgUniqNumEqualTo(ag);
        List<Agent> agents = agentMapper.selectByExample(example);

        try {
            Agent a = new Agent();
            if(agents.size()==1){
                a = agents.get(0);
            }else{
                a = new Agent();
            }
            a.setAgUniqNum(ag);
            if(StringUtils.isNotBlank(agName))
                a.setAgName(agName);
            if(StringUtils.isNotBlank(gongsixingzhi) && !"null".equalsIgnoreCase(gongsixingzhi)) {
                a.setAgNature(AgNature.getAgNatureMsgString(gongsixingzhi));
            }
            if(StringUtils.isNotBlank(zuceziben) && !"null".equalsIgnoreCase(zuceziben) && RegexUtil.checkNum(zuceziben))
                a.setAgCapital(new BigDecimal(zuceziben));
            if(StringUtils.isNotBlank(yingyezhizhao) && !"null".equalsIgnoreCase(yingyezhizhao))
                a.setAgBusLic(yingyezhizhao);

            if(StringUtils.isNotBlank(yingyezhizhaoqishishijian) && !"null".equalsIgnoreCase(yingyezhizhaoqishishijian)) {
                try {
                    a.setAgBusLicb(org.apache.commons.lang.time.DateUtils.parseDate(yingyezhizhaoqishishijian, new String[]{"yyyy-MM-dd"}));
                } catch (ParseException e) {
                    try {
                        a.setAgBusLicb(org.apache.commons.lang.time.DateUtils.parseDate(yingyezhizhaoqishishijian, new String[]{"yyyy/MM/dd"}));
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            if(StringUtils.isNotBlank(yingyezhizhaojieshushijian) && !"null".equalsIgnoreCase(yingyezhizhaojieshushijian)) {
                try {
                    a.setAgBusLice(org.apache.commons.lang.time.DateUtils.parseDate(yingyezhizhaojieshushijian, new String[]{"yyyy-MM-dd"}));
                } catch (ParseException e) {
                    try {
                        a.setAgBusLice(org.apache.commons.lang.time.DateUtils.parseDate(yingyezhizhaojieshushijian, new String[]{"yyyy/MM/dd"}));
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            if(StringUtils.isNotBlank(farenxingming) && !"null".equalsIgnoreCase(farenxingming))
                a.setAgLegal(farenxingming);
            if(StringUtils.isNotBlank(farenzhengjianleixing) && !"null".equalsIgnoreCase(farenzhengjianleixing))
                a.setAgLegalCertype(AgCertType.getAgCertTypeMsgString(farenzhengjianleixing));
            if(StringUtils.isNotBlank(farenshenfenzhenghao) && !"null".equalsIgnoreCase(farenshenfenzhenghao))
                a.setAgLegalCernum(farenshenfenzhenghao);
            if(StringUtils.isNotBlank(farenlianxidianhua) && !"null".equalsIgnoreCase(farenlianxidianhua))
                a.setAgLegalMobile(farenlianxidianhua);
            if(StringUtils.isNotBlank(fuzeren) && !"null".equalsIgnoreCase(fuzeren))
                a.setAgHead(fuzeren);
            if(StringUtils.isNotBlank(fuzerendianhua) && !"null".equalsIgnoreCase(fuzerendianhua))
                a.setAgHeadMobile(fuzerendianhua);
            if(StringUtils.isNotBlank(zhucedizhi) && !"null".equalsIgnoreCase(zhucedizhi))
                a.setAgRegAdd(zhucedizhi);
            if(StringUtils.isNotBlank(yingyefanwei) && !"null".equalsIgnoreCase(yingyefanwei))
                a.setAgBusScope(yingyefanwei);
            if(StringUtils.isNotBlank(yewuduijieshengqu) && !"null".equalsIgnoreCase(yewuduijieshengqu)) {
                COrganization org = departmentService.getByName(yewuduijieshengqu);
                if(org!=null) {
                    a.setAgDocPro(org == null ? null : org.getId() + "");
                }else{
                    COrganization org_pro =  departmentService.getByUserName(yewuduijieshengqu);
                    a.setAgDocPro(org_pro == null ? null : org_pro.getId() + "");
                }
            }
            if(StringUtils.isNotBlank(yewuduijiedaqu) && !"null".equalsIgnoreCase(yewuduijiedaqu)){
                String region =yewuduijiedaqu;
                if("北方".equals(region)) {
                    region = "北方大区";
                }
                if("南方".equals(region)) {
                    region = "南方大区";
                }
                if("北京".equals(region)) {
                    region = "北京市场部";
                }
                COrganization org = departmentService.getByName(region);
                if (org != null) {
                    a.setAgDocDistrict(org == null ? null : org.getId() + "");
                } else {
                    COrganization org_DocDistrict = departmentService.getByUserNameParent(region);
                    a.setAgDocPro(org_DocDistrict == null ? null : org_DocDistrict.getId() + "");
                }
            }

            if(StringUtils.isNotBlank(beizhu) && !"null".equalsIgnoreCase(beizhu))
                a.setAgRemark(beizhu);

            if(StringUtils.isBlank(a.getId())){
                a.setId(idService.genId(TabId.a_agent));
                a.setStatus(Status.STATUS_1.status);
                a.setVersion(Status.STATUS_1.status);
                a.setcIncomStatus(Status.STATUS_1.status);
                a.setFreestatus(Status.STATUS_1.status);
                a.setAgStatus(AgStatus.Approved.name());
                a.setCaStatus(Status.STATUS_1.status);
                a.setAgLegal(null);
                a.setcUtime(Calendar.getInstance().getTime());
                a.setcTime(Calendar.getInstance().getTime());
                if(1==agentMapper.insertSelective(a)){
                    logger.info(prefix_importAgentInfo+"导入成功 {},{},{}",a.getAgUniqNum(),user,list);
                }else{
                    logger.info(prefix_importAgentInfo+"导入失败 {},{},{}",a.getAgUniqNum(),user,list);
                }
            }else{
                a.setcUtime(Calendar.getInstance().getTime());
                if(1==agentMapper.updateByPrimaryKeySelective(a)){
                    logger.info(prefix_importAgentInfo+"修改成功 {},{},{}",a.getAgUniqNum(),user,list);
                }else{
                    logger.info(prefix_importAgentInfo+"修改失败 {},{},{}",a.getAgUniqNum(),user,list);
                }
            }
            return ResultVO.success(a);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(prefix_importAgentInfo+"修改异常 {},{},{}",ag,user,list);
            return ResultVO.fail("修改异常");
        }
    }
    private static String prefix_importBusInfo = "导入业务数据：";
    private ResultVO importBusInfo(String user, List<Object> list){
        logger.info(prefix_importBusInfo+"{},{}",user,list);
        String  ag = list.get(0)+"";//唯一码
        String  agName = list.get(1)+"";//代理商名称
        String  busPlatform_num = list.size()>2?list.get(2)+"":"";//业务平台唯一编号
        String  busPlatform = list.size()>3?list.get(3)+"":"";//业务平台类型
        String  busType = list.size()>4?list.get(4)+"":"";//所属类型(机构或二代直签直发等)
        String  busRegion = list.size()>5?list.get(5)+"":"";//业务区域
        String  shangjiAgName = list.size()>6?list.get(6)+"":"";//上级代理商名称
        String  shangjiAg = list.size()>7?list.get(7)+"":"";//上级代理商唯一编号
        String  fengxianAg = list.size()>8?list.get(8)+"":"";//风险承担所属代理商唯一编号
        String  jihuofanxainAg = list.size()>9?list.get(9)+"":"";//激活及返现所属代理商唯一编号
        String  shifouzhifa = list.size()>10?list.get(10)+"":"";//是否直发
        String  shifouzhijiefanxian = list.size()>11?list.get(11)+"":"";//是否直接返现
        String  shifoudulikaohe = list.size()>12?list.get(12)+"":"";//是否独立考核
        String  yewulianxiren = list.size()>13?list.get(13)+"":"";//业务联系人
        String  yewulianxidianhua = list.size()>14?list.get(14)+"":"";//业务联系电话
        String  fenrunduijieyouxiang = list.size()>15?list.get(15)+"":"";//分润对接邮箱
        String  yewuduijieren = list.size()>16?list.get(16)+"":"";//业务对接人
        String  tousujifengxianfengkongduijieemail = list.size()>17?list.get(17)+"":"";//投诉及风险风控对接邮箱
        String  shifouyaoqiushouju = list.size()>18?list.get(18)+"":"";//是否要求收据
        String  dakuangongsi = list.size()>19?list.get(19)+"":"";//打款公司
        String  shiyongfanwei = list.size()>20?list.get(20)+"":"";//使用范围(代理商、机构、自营、手刷)
        String  yewufanwei = list.size()>21?list.get(21)+"":"";//业务范围(市代、省代，国代)
        String  pingtaidengluhao = list.size()>22?list.get(22)+"":"";//平台登陆号
        String  zhongduanshuliangxiaxian = list.size()>23?list.get(23)+"":"";//终端数量下限
        String  jijifeilvxiaxian = list.size()>24?list.get(24)+"":"";//借记费率下限
        String  jiejifengdinge = list.size()>25?list.get(25)+"":"";//借记封顶额
        String  jiejichukuanfeilv = list.size()>26?list.get(26)+"":"";//借记出款费率
        String  shifoukaitongs0 = list.size()>27?list.get(27)+"":"";//是否开通s0
        String  shengqu = list.size()>28?list.get(28)+"":"";//省区
        String  daqu = list.size()>29?list.get(29)+"":"";//大区
        String  yewujigou = list.size()>30?list.get(30)+"":"";//业务机构
        String  chukuanjigou = list.size()>31?list.get(31)+"":"";//出款机构
        String  credit_rate_floor = list.size()>32?list.get(32)+"":"";//贷记费率下线

        ag  = ag.trim();
        busPlatform_num = busPlatform_num.trim();
        AgentExample agentExample = new AgentExample();
        agentExample.or().andAgUniqNumEqualTo(ag);
        List<Agent> agents = agentMapper.selectByExample(agentExample);

        if(agents.size()==0){
            logger.info(prefix_importBusInfo+"唯一码未找到({}),{},{}",ag,user,list);
            return ResultVO.fail("唯一码未找到["+ag+"]");
        }
        Agent agent = agents.get(0);

        String PlatformNum = null;

        if(StringUtils.isNotBlank(busPlatform)) {
            busPlatform = busPlatform.trim();
            if(busPlatform.contains("MPOS")){
                busPlatform = busPlatform.replace("MPOS","手刷");
            }
            if(busPlatform.contains("瑞+平台")){
                busPlatform = busPlatform.replace("瑞+平台","瑞+");
            }
            List<PlatForm>  platForms = businessPlatformService.queryAblePlatForm();
            for (PlatForm platForm : platForms) {
                if (platForm.getPlatformName().equals(busPlatform)) {
                    PlatformNum = platForm.getPlatformNum();
                    break;
                }
            }
        }

        //平台不能为空
        if(StringUtils.isBlank(PlatformNum)){
            return ResultVO.fail("业务平台未找到："+busPlatform);
        }

        AgentBusInfo agentBusInfo = new AgentBusInfo();
        if(StringUtils.isNotBlank(busPlatform_num)){
            AgentBusInfoExample example = new AgentBusInfoExample();
            example.or()
                    .andStatusEqualTo(Status.STATUS_1.status)
                    .andBusNumEqualTo(String.valueOf(busPlatform_num))
                    .andBusPlatformEqualTo(PlatformNum)
                    .andAgentIdEqualTo(agent.getId());
            List<AgentBusInfo> businfos = agentBusInfoMapper.selectByExample(example);
            if(businfos.size()==1){
                logger.info(prefix_importBusInfo+"业务码,{},{}",user,list);
                agentBusInfo=businfos.get(0);
            }else if(businfos.size()>1){
                logger.info(prefix_importBusInfo+"业务码({})数量不唯一,{},{}",busPlatform_num,user,list);
                return ResultVO.fail("业务码数量不唯一");
            }else{
                agentBusInfo.setcTime(new Date());
                agentBusInfo.setcUtime(agentBusInfo.getcTime());
                agentBusInfo.setBusStatus(BusinessStatus.Enabled.status);
                agentBusInfo.setCloReviewStatus(AgStatus.Approved.status);
                agentBusInfo.setStatus(Status.STATUS_1.status);
                agentBusInfo.setVersion(Status.STATUS_1.status);
                agentBusInfo.setcUser(user);
                agentBusInfo.setApproveTime(Calendar.getInstance().getTime());
            }
        }else{
            return ResultVO.fail("业务编码不能为空["+ag+"]");
        }
        agentBusInfo.setBusNum(busPlatform_num);
        agentBusInfo.setAgentId(agent.getId());
        agentBusInfo.setBusStatus(BusinessStatus.Enabled.status);
        //业务平台类型
        agentBusInfo.setBusPlatform(PlatformNum);
        //机构类型
        String busType_value = null;
        if(busType!=null && StringUtils.isNotBlank(busType) && !"null".equals(busType)) {
            List<Dict> bustype = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.BUS_TYPE.name());
            for (Dict dict : bustype) {
                if (dict.getdItemname().equals(busType)){
                    busType_value = dict.getdItemvalue();
                    agentBusInfo.setBusType(busType_value);
                }

            }
        }
        //所属上级代理(平台机构编号)
        if(shangjiAg!=null && StringUtils.isNotBlank(shangjiAg) && !"null".equals(shangjiAg)) {
            //上级代理
            AgentBusInfoExample sahngji_ex = new AgentBusInfoExample();
            sahngji_ex.or().andStatusEqualTo(Status.STATUS_1.status).andBusNumEqualTo(String.valueOf(shangjiAg));
            List<AgentBusInfo>  list_shangji =  agentBusInfoMapper.selectByExample(sahngji_ex);
            if(list_shangji!=null && list_shangji.size()>0){
                AgentBusInfo agentBusInfo_sahngji = list_shangji.get(0);
                agentBusInfo.setBusParent(agentBusInfo_sahngji.getId());
            }
        }
        //风险承担所属代理商
        if(fengxianAg!=null && StringUtils.isNotBlank(fengxianAg) && !"null".equals(fengxianAg)) {
            //上级代理
            AgentBusInfoExample sahngji_ex = new AgentBusInfoExample();
            sahngji_ex.or().andStatusEqualTo(Status.STATUS_1.status).andBusNumEqualTo(String.valueOf(fengxianAg));
            List<AgentBusInfo>  list_shangji =  agentBusInfoMapper.selectByExample(sahngji_ex);
            if(list_shangji!=null && list_shangji.size()>0){
                AgentBusInfo agentBusInfo_fengxianAg = list_shangji.get(0);
                agentBusInfo.setBusRiskParent(agentBusInfo_fengxianAg.getId());
            }
        }
        //激活返现
        if(jihuofanxainAg!=null && StringUtils.isNotBlank(jihuofanxainAg) && !"null".equals(jihuofanxainAg)) {
            //上级代理
            AgentBusInfoExample sahngji_ex = new AgentBusInfoExample();
            sahngji_ex.or().andStatusEqualTo(Status.STATUS_1.status).andBusNumEqualTo(String.valueOf(jihuofanxainAg));
            List<AgentBusInfo>  list_shangji =  agentBusInfoMapper.selectByExample(sahngji_ex);
            if(list_shangji!=null && list_shangji.size()>0){
                AgentBusInfo agentBusInfo_jihuofanxainAg = list_shangji.get(0);
                agentBusInfo.setBusActivationParent(agentBusInfo_jihuofanxainAg.getId());
            }
        }
        //业务区域
        List<String> arr = new ArrayList<>();
        logger.info(prefix_importBusInfo+"业务码({})区域,{},{},{}",busPlatform_num,busRegion,user,list);
        if(busRegion!=null && StringUtils.isNotBlank(busRegion) && !"null".equals(busRegion)) {
            busRegion = busRegion.trim();
            String[] regions = busRegion.split(",");
            DPosRegionExample dPosRegionExample = new DPosRegionExample();
            dPosRegionExample.or().andNameIn(Arrays.asList(regions)).andCodeLevelIn(Arrays.asList("2","1"));
            List<DPosRegion> dPosRegions = dPosRegionMapper.selectByExample(dPosRegionExample);
            for (DPosRegion dPosRegion : dPosRegions) {
                arr.add(dPosRegion.getCode());
            }
            if(arr.size()>0) {
                agentBusInfo.setBusRegion(String.join(",", arr));
            }
        }
        //是否直发
        int bus_sent_directly_index = -1;
        if(shifouzhifa!=null && StringUtils.isNotBlank(shifouzhifa) && !"null".equals(shifouzhifa)) {
            bus_sent_directly_index = yesorno.indexOf(shifouzhifa);
            if(bus_sent_directly_index!=-1){
                agentBusInfo.setBusSentDirectly(BigDecimal.valueOf(bus_sent_directly_index));
            }
        }

        //是否直接返现
        int bus_direct_cashback_index = -1;
        if(shifouzhijiefanxian!=null && StringUtils.isNotBlank(shifouzhijiefanxian) && !"null".equals(shifouzhijiefanxian)) {
            bus_direct_cashback_index = yesorno.indexOf(shifouzhijiefanxian);
            if(bus_direct_cashback_index!=-1){
                agentBusInfo.setBusDirectCashback(BigDecimal.valueOf(bus_direct_cashback_index));
            }
        }
        //是否独立考核
        int bus_Inde_ass_index = -1;
        if(shifoudulikaohe!=null && StringUtils.isNotBlank(shifoudulikaohe) && !"null".equals(shifoudulikaohe)) {
            bus_Inde_ass_index = yesorno.indexOf(shifoudulikaohe);
            if(bus_Inde_ass_index!=-1){
                agentBusInfo.setBusIndeAss(BigDecimal.valueOf(bus_Inde_ass_index));
            }
        }

        //业务联系人
        if(yewulianxiren!=null && StringUtils.isNotBlank(yewulianxiren) && !"null".equals(yewulianxiren)) {
                agentBusInfo.setBusContact(yewulianxiren);
        }
        //业务联系电话
        if(yewulianxidianhua!=null && StringUtils.isNotBlank(yewulianxidianhua) && !"null".equals(yewulianxidianhua)) {
            agentBusInfo.setBusContactMobile(yewulianxidianhua);
        }
        //分润对接邮箱
        if(fenrunduijieyouxiang!=null && StringUtils.isNotBlank(fenrunduijieyouxiang) && !"null".equals(fenrunduijieyouxiang)) {
            agentBusInfo.setBusContactEmail(fenrunduijieyouxiang);
        }
        //业务对接人
        if(yewuduijieren!=null && StringUtils.isNotBlank(yewuduijieren) && !"null".equals(yewuduijieren)) {
            agentBusInfo.setBusContactPerson(yewuduijieren);
        }
        //投诉及风险风控对接邮箱
        if(tousujifengxianfengkongduijieemail!=null && StringUtils.isNotBlank(tousujifengxianfengkongduijieemail) && !"null".equals(tousujifengxianfengkongduijieemail)) {
            agentBusInfo.setBusRiskEmail(tousujifengxianfengkongduijieemail);
        }
        //是否要求收据
        int clo_receipt_index = -1;
        if(shifouyaoqiushouju!=null && StringUtils.isNotBlank(shifouyaoqiushouju) && !"null".equals(shifouyaoqiushouju)) {
            clo_receipt_index = yesorno.indexOf(shifouyaoqiushouju);
            if(clo_receipt_index!=-1){
                agentBusInfo.setCloReceipt(BigDecimal.valueOf(clo_receipt_index));
            }
        }
        //打款公司
        if(dakuangongsi!=null && StringUtils.isNotBlank(dakuangongsi) && !"null".equals(dakuangongsi)) {
            List<PayComp>  payCompList = apaycompService.compList();
            for (PayComp payComp : payCompList) {
                if(payComp.getComName().equals(dakuangongsi)) {
                    agentBusInfo.setCloPayCompany(payComp.getId());
                    break;
                }
            }
        }
        //使用范围
        if(shiyongfanwei!=null && StringUtils.isNotBlank(shiyongfanwei) && !"null".equals(shiyongfanwei)) {
            List<Dict>  bus_use_organ = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.USE_SCOPE.name());
            for (Dict dict : bus_use_organ) {
                if(dict.getdItemname().equals(shiyongfanwei)) {
                    agentBusInfo.setBusUseOrgan(dict.getdItemvalue());
                }
            }
        }
        //业务范围 StringUtils.isNotBlank(yewufanwei)
        if(yewufanwei!=null && StringUtils.isNotBlank(yewufanwei) && !"null".equals(yewufanwei)) {
            agentBusInfo.setBusScope(BUS_SCOP.get(yewufanwei));
        }
        //是否开通s0：1是，0否 StringUtils.isNotBlank(shifoukaitongs0)
        int shifoukaitongs0_index=-1;
        if(shifoukaitongs0!=null && StringUtils.isNotBlank(shifoukaitongs0) && !"null".equals(shifoukaitongs0) ) {
            shifoukaitongs0_index = yesorno.indexOf(shifoukaitongs0);
            if(shifoukaitongs0_index!=-1){
                agentBusInfo.setDredgeS0(BigDecimal.valueOf(shifoukaitongs0_index));
            }
        }
        //业务系统登录账号
        if(pingtaidengluhao!=null && StringUtils.isNotBlank(pingtaidengluhao) && !"null".equals(pingtaidengluhao) ) {
            agentBusInfo.setBusLoginNum(pingtaidengluhao);
        }
        //借记费率下限（%）0.55
        if(jijifeilvxiaxian!=null && StringUtils.isNotBlank(jijifeilvxiaxian) && !"null".equals(jijifeilvxiaxian) ) {
            agentBusInfo.setDebitRateLower(jijifeilvxiaxian);
        }
        //借记封顶额（元）9999
        if(jiejifengdinge!=null && StringUtils.isNotBlank(jiejifengdinge) && !"null".equals(jiejifengdinge) ) {
            agentBusInfo.setDebitCapping(jiejifengdinge);
        }
        //借记出款费率（%）0
        if(jiejichukuanfeilv!=null && StringUtils.isNotBlank(jiejichukuanfeilv) && !"null".equals(jiejichukuanfeilv) ) {
            agentBusInfo.setDebitAppearRate(jiejichukuanfeilv);
        }
        //终端数量下限
        if(zhongduanshuliangxiaxian!=null && StringUtils.isNotBlank(zhongduanshuliangxiaxian) && !"null".equals(zhongduanshuliangxiaxian) ) {
            agentBusInfo.setTerminalsLower(zhongduanshuliangxiaxian);
        }
        //省区
        if(StringUtils.isNotBlank(shengqu) && !"null".equalsIgnoreCase(shengqu)) {
            COrganization org = departmentService.getByName(shengqu);
            if(org!=null) {
                agentBusInfo.setAgDocPro(org == null ? null : org.getId() + "");
            }else{
                COrganization org_pro =  departmentService.getByUserName(shengqu);
                agentBusInfo.setAgDocPro(org_pro == null ? null : org_pro.getId() + "");
            }
        }
        //大区
        if(StringUtils.isNotBlank(daqu) && !"null".equalsIgnoreCase(daqu)){
            String region =daqu;
            if("北方".equals(region)) {
                region = "北方大区";
            }
            if("南方".equals(region)) {
                region = "南方大区";
            }
            if("北京".equals(region)) {
                region = "北京市场部";
            }
            COrganization org = departmentService.getByName(region);
            if (org != null) {
                agentBusInfo.setAgDocDistrict(org == null ? null : org.getId() + "");
            } else {
                COrganization org_DocDistrict = departmentService.getByUserNameParent(region);
                agentBusInfo.setAgDocPro(org_DocDistrict == null ? null : org_DocDistrict.getId() + "");
            }
        }
        //业务机构
        if(yewujigou!=null && StringUtils.isNotBlank(yewujigou) && !"null".equals(yewujigou) ) {
            OrganizationExample example = new OrganizationExample();
            example.or().andOrgNameEqualTo(yewujigou).andStatusEqualTo(Status.STATUS_1.status);
            List<Organization> listOrganization = organizationMapper.selectByExample(example);
            if(listOrganization.size()>0) {
                agentBusInfo.setOrganNum(listOrganization.get(0).getOrgId());
            }
        }
        //出款机构
        if(chukuanjigou!=null && StringUtils.isNotBlank(chukuanjigou) && !"null".equals(chukuanjigou) ) {
            OrganizationExample example = new OrganizationExample();
            example.or().andOrgNameEqualTo(yewujigou).andStatusEqualTo(Status.STATUS_1.status);
            List<Organization> listOrganization = organizationMapper.selectByExample(example);
            if(listOrganization.size()>0) {
                agentBusInfo.setFinaceRemitOrgan(listOrganization.get(0).getOrgId());
            }

        }
        //贷记费率下限
        if(credit_rate_floor!=null && StringUtils.isNotBlank(credit_rate_floor) && !"null".equals(credit_rate_floor) ) {
            agentBusInfo.setCreditRateFloor(credit_rate_floor);
        }
        if(StringUtils.isNotBlank(agentBusInfo.getId())){
            agentBusInfo.setcUtime(Calendar.getInstance().getTime());
            if(StringUtils.isBlank(agentBusInfo.getBusRiskParent())){
                agentBusInfo.setBusRiskParent(agentBusInfo.getId());
            }
            if(agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo)==1){
                logger.info(prefix_importBusInfo+"更新业务码({}),{},{}，成功",busPlatform_num,user,list);
            }else{
                logger.info(prefix_importBusInfo+"更新业务码({}),{},{}，失败",busPlatform_num,user,list);
                return ResultVO.fail("更新业务失败");
            }
        }else{
            agentBusInfo.setcUtime(Calendar.getInstance().getTime());
            agentBusInfo.setId(idService.genId(TabId.a_agent_businfo));
            if(StringUtils.isBlank(agentBusInfo.getBusRiskParent())){
                agentBusInfo.setBusRiskParent(agentBusInfo.getId());
            }
            if(agentBusInfoMapper.insertSelective(agentBusInfo)==1){
                logger.info(prefix_importBusInfo+"添加业务码({}),{},{}，成功",busPlatform_num,user,list);
            }else{
                logger.info(prefix_importBusInfo+"添加业务码({}),{},{}，失败",busPlatform_num,user,list);
                return ResultVO.fail("添加业务失败");
            }
        }
        return ResultVO.success(null);
    }


    private static String prefix_importBankInfo= "导入收款账户：";
    private ResultVO importBankInfo(String user, List<Object> list){
        logger.info(prefix_importBankInfo+"{},{}",user,list);
        String  ag = list.get(0)+"";//唯一码
        String  agName = list.size()>1?list.get(1)+"":"";//代理商名称
        String  zhanghuleixing = list.size()>2?list.get(2)+"":"";//账户类型(对公，对私)
        String  shoukuanzhanghao = list.size()>3?list.get(3)+"":"";//收款账号
        String  shoukuanzhanghuming = list.size()>4?list.get(4)+"":"";//收款账户名
        String  shoukuankaihuhang = list.size()>5?list.get(5)+"":"";//收款开户行
        String  zonghanglianhanghao = list.size()>6?list.get(6)+"":"";//总行联行号
        String  shoukuankaihuhangzhihang = list.size()>7?list.get(7)+"":"";//收款开户行支行
        String  zhihanglianhanghao = list.size()>8?list.get(8)+"":"";//支行联行号
        String  shuidian = list.size()>9?list.get(9)+"":"";//税点
        String  shifoukaijufenrunfapiao = list.size()>10?list.get(10)+"":"";//是否开具分润发票
        String  kaihudiqu = list.size()>11?list.get(11)+"":"";//开户行地区
        String  kaihuhanghanghao = list.size()>12?list.get(12)+"":"";//开户行行号
        String  shoukuanzhanghuming_shenfz = list.size()>13?list.get(13)+"":"";//收款账户名 身份证
        String  remark = list.size()>14?list.get(14)+"":"";//备注

        try {
            AgentExample agentExample = new AgentExample();
            agentExample.or().andAgUniqNumEqualTo(ag).andStatusEqualTo(Status.STATUS_1.status);
            List<Agent> agents = agentMapper.selectByExample(agentExample);
            if(agents.size()!=0){
                logger.info(prefix_importBankInfo+"唯一码未找到({}),{},{}",ag,user,list);
            }
            Agent agent = agents.get(0);
            AgentColinfoExample agentColinfoExample = new AgentColinfoExample();
            agentColinfoExample.or().andAgentIdEqualTo(agent.getId()).andStatusEqualTo(Status.STATUS_1.status);
            List<AgentColinfo>  agentColinfos = agentColinfoMapper.selectByExample(agentColinfoExample);
            AgentColinfo agentColinfo = null;
            if(agentColinfos.size()==1){
                 agentColinfo =  agentColinfos.get(0);
            }else if(agentColinfos.size()>1){
                logger.info(prefix_importBankInfo+"收款账户不唯一({}),{},{}",ag,user,list);
                return ResultVO.fail("收款账户不唯一");
            }else if(agentColinfos.size()<1){
                 agentColinfo =  new AgentColinfo();
            }
            agentColinfo.setAgentId(agent.getId());
            if(StringUtils.isNotBlank(zhanghuleixing) && !"null".equalsIgnoreCase(zhanghuleixing)) {
                agentColinfo.setCloType(BigDecimal.valueOf(gs.indexOf(zhanghuleixing)));
            }else{
                agentColinfo.setCloType(null);
            }
            if(StringUtils.isNotBlank(shoukuanzhanghao) && !"null".equalsIgnoreCase(shoukuanzhanghao)) {
                agentColinfo.setCloBankAccount(shoukuanzhanghao.trim());
            }else{
                agentColinfo.setCloBankAccount(null);
            }
            if(StringUtils.isNotBlank(shoukuanzhanghuming) && !"null".equalsIgnoreCase(shoukuanzhanghuming)) {
                agentColinfo.setCloRealname(shoukuanzhanghuming.trim());
            }else{
                agentColinfo.setCloRealname(null);
            }
            if(StringUtils.isNotBlank(shoukuankaihuhang) && !"null".equalsIgnoreCase(shoukuankaihuhang)) {
                agentColinfo.setCloBank(shoukuankaihuhang.trim());
            }else{
                agentColinfo.setCloBank(null);
            }
            if(StringUtils.isNotBlank(shoukuankaihuhangzhihang) && !"null".equalsIgnoreCase(shoukuankaihuhangzhihang)) {
                agentColinfo.setCloBankBranch(shoukuankaihuhangzhihang);
            }else{
                agentColinfo.setCloBankBranch(null);
            }
            if(StringUtils.isNotBlank(remark) && !"null".equalsIgnoreCase(remark)) {
                agentColinfo.setRemark(remark);
            }else{
                agentColinfo.setRemark(null);
            }
            if(StringUtils.isNotBlank(zhihanglianhanghao) && !"null".equalsIgnoreCase(zhihanglianhanghao)) {
                agentColinfo.setBranchLineNum(zhihanglianhanghao);
            }else{
                agentColinfo.setBranchLineNum(null);
            }
            if(StringUtils.isNotBlank(zonghanglianhanghao) && !"null".equalsIgnoreCase(zonghanglianhanghao)) {
                agentColinfo.setAllLineNum(zonghanglianhanghao);
            }else{
                agentColinfo.setAllLineNum(null);
            }
            if(StringUtils.isNotBlank(shuidian) && !"null".equalsIgnoreCase(shuidian)) {
                agentColinfo.setCloTaxPoint(new BigDecimal(shuidian));
            }else{
                agentColinfo.setCloTaxPoint(null);
            }
            if(StringUtils.isNotBlank(kaihudiqu) && !"null".equalsIgnoreCase(kaihudiqu)) {
                agentColinfo.setBankRegion(kaihudiqu);
            }else{
                agentColinfo.setBankRegion(null);
            }
            if(StringUtils.isNotBlank(kaihuhanghanghao) && !"null".equalsIgnoreCase(kaihuhanghanghao)) {
                agentColinfo.setCloBankCode(kaihuhanghanghao);
            }else{
                agentColinfo.setCloBankCode(null);
            }
            if(StringUtils.isNotBlank(shoukuanzhanghuming_shenfz) && !"null".equalsIgnoreCase(shoukuanzhanghuming_shenfz)) {
                agentColinfo.setAgLegalCernum(shoukuanzhanghuming_shenfz);
            }else{
                agentColinfo.setCloBankCode(null);
            }
            agentColinfo.setPayStatus(ColinfoPayStatus.C.code);

            if(StringUtils.isBlank(agentColinfo.getId())){
                agentColinfo.setId(idService.genId(TabId.a_agent_colinfo));
                agentColinfo.setStatus(Status.STATUS_1.status);
                agentColinfo.setVarsion(Status.STATUS_0.status);
                agentColinfo.setcTime(Calendar.getInstance().getTime());
                agentColinfo.setcUtime(Calendar.getInstance().getTime());
                agentColinfo.setCloReviewStatus(AgStatus.Approved.status);
                if(agentColinfoMapper.insertSelective(agentColinfo)==1){
                    logger.info(prefix_importBankInfo+"导入成功({}),{},{}",ag,user,list);
                }else{
                    logger.info(prefix_importBankInfo+"导入失败({}),{},{}",ag,user,list);
                }
            }else{
                agentColinfo.setcUtime(Calendar.getInstance().getTime());
                if(1==agentColinfoMapper.updateByPrimaryKey(agentColinfo)){
                    logger.info(prefix_importBankInfo+"更新成功({}),{},{}",ag,user,list);
                }else{
                    logger.info(prefix_importBankInfo+"更新失败({}),{},{}",ag,user,list);
                }
            }
            //代理商业务平台
            AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
            agentBusInfoExample.or().andAgentIdEqualTo(agent.getId()).andStatusEqualTo(Status.STATUS_1.status);
            List<AgentBusInfo> listBusinfo = agentBusInfoMapper.selectByExample(agentBusInfoExample);
            for (AgentBusInfo agentBusInfo : listBusinfo) {
                //收款账户关系匹配
                AgentColinfoRelExample agentColinfoRelExample = new AgentColinfoRelExample();
                agentColinfoRelExample.or().andAgentbusidEqualTo(agentBusInfo.getId()).andAgentidEqualTo(agentBusInfo.getAgentId())
                       .andBusPlatformEqualTo(agentBusInfo.getBusPlatform()).andStatusEqualTo(Status.STATUS_1.status);
                List<AgentColinfoRel>  listRel_db = agentColinfoRelMapper.selectByExample(agentColinfoRelExample);

                //没有建立关系就建立关系
                if(listRel_db.size()==0) {
                    //添加收款账户关系
                    AgentColinfoRel agentColinfoRel = new AgentColinfoRel();
                    agentColinfoRel.setcUse(user);
                    agentColinfoRel.setAgentid(agent.getId());
                    agentColinfoRel.setAgentColinfoid(agentColinfo.getId());
                    agentColinfoRel.setBusPlatform(agentBusInfo.getBusPlatform());
                    agentColinfoRel.setAgentbusid(agentBusInfo.getId());
                    AgentResult rel = agentColinfoService.saveAgentColinfoRel(agentColinfoRel, user);
                    logger.info(prefix_importBankInfo+"添加平台收款账号关系{},{},{}",ag,user,agentBusInfo.getBusPlatform());
                }else{
                    logger.info("代理商导入收款账户业务关系已存在{},{}",agentBusInfo.getId(),listRel_db.get(0).getId());
                    AgentColinfoRel agentColinfoRel = listRel_db.get(0);
                    agentColinfoRel.setAgentColinfoid(agentColinfo.getId());
                    if(1==agentColinfoRelMapper.updateByPrimaryKeySelective(agentColinfoRel)){
                        logger.info(prefix_importBankInfo+"修改平台收款账号关系{},{},{}",ag,user,agentBusInfo.getBusPlatform());
                    }
                }
            }
            return ResultVO.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.success(null);
        }
    }


    private ResultVO updateBusInfo(String user, List<Object> list){

        logger.info("用户{}更新业务信息{}",user,list);
        String  ag = list.get(0)+"",
                busPlatform = list.get(1)+"",
                busNum = list.size()>2?list.get(2)+"":"",
                busRegion=list.size()>3?list.get(3)+"":"",
                isS0=list.size()>4?list.get(4)+"":"",
                jglx=list.size()>5?(list.get(5))+"":"",//机构类型
                bus_sent_directly=list.size()>6?(list.get(6))+"":"",
                bus_direct_cashback=list.size()>7?(list.get(7))+"":"",//是否直接返现
                bus_Inde_ass=list.size()>8?(list.get(8))+"":"",//是否独立考核
                clo_receipt=list.size()>9?(list.get(9))+"":"",//是否要求收据
                bus_login_num=list.size()>10?(list.get(10))+"":"",//登录账号
                update_busNum=list.size()>11?(list.get(11))+"":"";//要更新的业务编码

        List<String> arr = new ArrayList<>();
        if(busRegion!=null && StringUtils.isNotBlank(busRegion) && !"null".equals(busRegion)) {
            String[] regions = busRegion.split(",");
            DPosRegionExample dPosRegionExample = new DPosRegionExample();
            dPosRegionExample.or().andCodeIn(Arrays.asList(regions)).andCodeLevelIn(Arrays.asList("2"));

            List<DPosRegion> dPosRegions = dPosRegionMapper.selectByExample(dPosRegionExample);
            for (DPosRegion dPosRegion : dPosRegions) {
                arr.add(dPosRegion.getCode());
            }
        }
        String busType = null;
        if(jglx!=null && StringUtils.isNotBlank(jglx) && !"null".equals(jglx)) {
            List<Dict> bustype = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.BUS_TYPE.name());
            for (Dict dict : bustype) {
                if (dict.getdItemname().equals(jglx)){
                    busType = dict.getdItemvalue();
                }

            }
        }

        int bus_sent_directly_index = -1;
        if(bus_sent_directly!=null && StringUtils.isNotBlank(bus_sent_directly) && !"null".equals(bus_sent_directly)) {
            bus_sent_directly_index = yesorno.indexOf(bus_sent_directly);
        }

        int bus_direct_cashback_index = -1;
        if(bus_direct_cashback!=null && StringUtils.isNotBlank(bus_direct_cashback) && !"null".equals(bus_direct_cashback)) {
            bus_direct_cashback_index = yesorno.indexOf(bus_direct_cashback);
        }

        int bus_Inde_ass_index = -1;
        if(bus_Inde_ass!=null && StringUtils.isNotBlank(bus_Inde_ass) && !"null".equals(bus_Inde_ass)) {
            bus_Inde_ass_index = yesorno.indexOf(bus_Inde_ass);
        }

        int clo_receipt_index = -1;
        if(clo_receipt!=null && StringUtils.isNotBlank(clo_receipt) && !"null".equals(clo_receipt)) {
            clo_receipt_index = yesorno.indexOf(clo_receipt);
        }



        AgentBusInfoExample example = new AgentBusInfoExample();
        if(StringUtils.isNotBlank(busNum)) {
            example.or().andStatusEqualTo(Status.STATUS_1.status).andBusNumEqualTo(String.valueOf(busNum));
        }else{
            example.or().andStatusEqualTo(Status.STATUS_1.status).andAgentIdEqualTo(ag).andBusPlatformEqualTo(busPlatform);
        }
        List<AgentBusInfo> businfos = agentBusInfoMapper.selectByExample(example);

        if(businfos.size()==0){
            return ResultVO.fail("业务未找到");
        }

        if(businfos.size()>1){
            return ResultVO.fail("业务数量查出多个");
        }

        for (AgentBusInfo businfo : businfos) {

            if(arr.size()>0) {
                logger.info("用户{}修改前{}业务区域{}", user, busNum, businfo.getBusRegion());
                businfo.setBusRegion(String.join(",", arr));
                logger.info("用户{}修改为{}业务区域{}",user,busNum,businfo.getBusRegion());
            }

            if(StringUtils.isNotBlank(isS0)){
                if(yesorno.indexOf(isS0)!=-1) {
                    logger.info("用户{}修改前{}是否开通s0：1是，0否 {}", user, busNum, businfo.getDredgeS0());
                    businfo.setDredgeS0(new BigDecimal(yesorno.indexOf(isS0)));
                    logger.info("用户{}修改前{}是否开通s0：1是，0否 {}", user, busNum, businfo.getDredgeS0());
                }
            }
            if(StringUtils.isNotBlank(busType)){
                businfo.setBusType(busType);
            }
            if(bus_sent_directly_index!=-1){
                businfo.setBusSentDirectly(BigDecimal.valueOf(bus_sent_directly_index));
            }
            if(bus_direct_cashback_index!=-1){
                businfo.setBusDirectCashback(BigDecimal.valueOf(bus_direct_cashback_index));
            }
            if(bus_Inde_ass_index!=-1){
                businfo.setBusIndeAss(BigDecimal.valueOf(bus_Inde_ass_index));
            }
            if(clo_receipt_index!=-1){
                businfo.setCloReceipt(BigDecimal.valueOf(clo_receipt_index));
            }
            if(StringUtils.isNotBlank(bus_login_num)){
                logger.info("用户{}登录编号业务编号前:{}", user,businfo.getBusLoginNum());
                businfo.setBusLoginNum(bus_login_num);
                logger.info("用户{}登录编号业务编号后:{}", user,businfo.getBusLoginNum());
            }
            if(StringUtils.isBlank(busNum) || "null".equalsIgnoreCase(busNum)) {
                if(StringUtils.isNotBlank(update_busNum) && !"null".equalsIgnoreCase(update_busNum.trim())) {
                    logger.info("用户{}补全业务编号前:{}", user,businfo.getBusNum());
                    businfo.setBusNum(update_busNum.trim());
                    logger.info("用户{}补全业务编号后:{}", user,businfo.getBusNum());
                }
            }
            if(agentBusInfoMapper.updateByPrimaryKeySelective(businfo)==1){
                logger.info("用户{}修改为{}",user,busNum);
            }else{
                logger.info("用户{}修改失败{}",user,busNum);
            }

        }
        return ResultVO.success("");
    }




}
