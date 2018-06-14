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
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.service.dict.DepartmentService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
            if(datum==null||datum.size()==0 || StringUtils.isBlank(datum.get(0)+""))break;
            ImportAgent importAgent = new ImportAgent();
            importAgent.setBatchcode(batch);
            importAgent.setcUser(user);
            importAgent.setDatacontent(JSONArray.toJSONString(datum));
            importAgent.setDataid(datum.get(0)+"");
            importAgent.setDatatype(dataType);
            if(1!=insertAgentImportData(importAgent)){
                throw new ProcessException("插入失败");
            }
            ids.add(importAgent.getId());
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
                        Agent ag_db = agentService.insertAgent(ag, Arrays.asList());
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
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public ResultVO analysisPayment(List<ImportAgent>  data,String userid)throws Exception{
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
                    if(agQuery.size()==0){
                        throw new ProcessException("代理商交款导入失败");
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
                        if(c.getcPaytime()!=null && DateUtil.format(c.getcPaytime(),"yyyy-MM-dd").equals(capital.getcPaytime())){
                               logger.info("用户已交过款项{},{},{}",capital.getcAmount(),capital.getcType(),capital.getcPaytime());
                        }else{
                            AgentResult result = accountPaidItemService.insertAccountPaid(capital,Arrays.asList(),userid);
                            if(null==result || !result.isOK())throw new ProcessException("代理商交款导入失败");
                        }
                    }else{
                        AgentResult result = accountPaidItemService.insertAccountPaid(capital,Arrays.asList(),userid);
                        if(null==result || !result.isOK())throw new ProcessException("代理商交款导入失败");
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
                } catch (Exception e) {
                    e.printStackTrace();
                    ImportAgent con  = importAgentMapper.selectByPrimaryKey(datum.getId());
                    con.setDealstatus(Status.STATUS_3.status);
                    con.setDealmsg(e.getMessage());
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
                        throw new ProcessException("代理商交款导入失败");
                    }

                    Agent agent =  agQuery.get(0);
                    JSONArray array = JSONArray.parseArray(datacontent);
                    AgentContract agentContract =  parseContractFromJson(array);
                    agentContract.setAgentId(agent.getId());
                    agentContract.setcUser(userid);
                    AgentContractExample agentContractExample = new AgentContractExample();
                    agentContractExample.or().andAgentIdEqualTo(agentContract.getAgentId())
                            .andContNumEqualTo(agentContract.getContNum())
                            .andStatusEqualTo(Status.STATUS_1.status)
                            .andContTypeEqualTo(agentContract.getContType());
                    List<AgentContract> agentContractDb =  agentContractMapper.selectByExample(agentContractExample);
                    ImportAgent con  = importAgentMapper.selectByPrimaryKey(datum.getId());

                    if(agentContractDb.size()==0){
                        logger.info("导入代理商合同信息{}",datum.getId());
                        AgentContract ac =  agentContractService.insertAgentContract(agentContract,Arrays.asList());
                        logger.info("导入代理商合同信息{}成功",ac.getId());
                        con.setDealmsg("添加成功");
                    }else{
                        AgentContract contract = agentContractDb.get(0);
                        contract.setContType(agentContract.getContType());
                        contract.setContNum(agentContract.getContNum());
                        contract.setContDate(agentContract.getContDate());
                        contract.setContEndDate(agentContract.getContEndDate());
                        contract.setRemark(agentContract.getRemark());
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
                        throw new ProcessException("代理商不存在");
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

                        AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
                        agentBusInfoExample.or()
                                .andAgentIdEqualTo(busItem.getAgentId())
                                .andBusPlatformEqualTo(busItem.getBusPlatform())
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
                            agentBusinfoService.updateAgentBusInfo(db_agentBusInfo);
                            break;
                        }

                         busItem.setcUser(userid);
                         busItem =  agentBusinfoService.agentBusInfoInsert(busItem);
                        List<AgentColinfo> colinfos = busItem.getAgentColinfoList();
                        if(colinfos.size()>0){
                            AgentColinfo colinfo = colinfos.get(0);
                            colinfo.setcUser(userid);
                            AgentColinfoExample agentColinfoExample = new AgentColinfoExample();
                            agentColinfoExample.or().andAgentIdEqualTo(busItem.getAgentId()).andCloRealnameEqualTo(colinfo.getCloRealname())
                                    .andCloBankAccountEqualTo(colinfo.getCloBankAccount()).andCloBankEqualTo(colinfo.getCloBank());
                            //添加收款账户
                            AgentColinfo ac = null;
                            List<AgentColinfo>  colinfodb = agentColinfoMapper.selectByExample(agentColinfoExample);
                            if(colinfodb.size()==0){
                                //添加收款账户
                                ac =  agentColinfoService.agentColinfoInsert(colinfo,Arrays.asList());
                            }else{
                                ac = colinfodb.get(0);
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
                                logger.info("代理商导入收款账户业务关系{}",busItem.getId(),rel.getMsg());
                            }else{
                                logger.info("代理商导入收款账户业务关系已存在{}",busItem.getId(),listRel_db.get(0).getId());
                            }
                        }
                    }


                    //更新业务
                    ImportAgentExample importAgentExample = new ImportAgentExample();
                    importAgentExample.or().andDatatypeEqualTo(AgImportType.BUSINESS.name()).andDealstatusEqualTo(Status.STATUS_0.status).andDataidEqualTo(datum.getDataid());
                    List<ImportAgent>  importAgentsBusiness = importAgentMapper.selectByExample(importAgentExample);
                    for (ImportAgent agentsBusiness : importAgentsBusiness) {
                        agentsBusiness.setDealTime(datum.getDealTime());
                        agentsBusiness.setDealstatus(Status.STATUS_2.status);
                        importAgentMapper.updateByPrimaryKeySelective(agentsBusiness);
                    }

                    ImportAgent payment =  importAgentMapper.selectByPrimaryKey(datum.getId());
                    payment.setDealstatus(Status.STATUS_2.status);//处理成功
                    payment.setDealmsg("成功");
                    payment.setDealTime(new Date());
                    if(1!=importAgentMapper.updateByPrimaryKeySelective(payment)){
                        logger.info("代理商导入业务{}失败",datum.getId());
                        throw new ProcessException("代理商导入业务");
                    }else{
                        logger.info("代理商导入业务{}成功",datum.getId());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ImportAgent payment =  importAgentMapper.selectByPrimaryKey(datum.getId());
                    payment.setDealstatus(Status.STATUS_3.status);//处理成功
                    payment.setDealmsg(e.getMessage());
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
            throw e;
        }

    }


    private AgentContract parseContractFromJson(JSONArray obj)throws Exception{
        try {
            logger.info("解析json{}",obj.toJSONString());
            AgentContract a = new AgentContract();
            String id =  obj.getString(0);
            if(null!=obj.getString(2))
                a.setContNum(obj.getString(2));
            if(null!=obj.getString(3)) {
                //便利查询合同类型
                List<Dict>  list = dictOptionsService.dictList(DictGroup.AGENT.name(),DictGroup.CONTRACT_TYPE.name());
                BigDecimal v = null;
                for (Dict dict : list) {
                    if(dict.getdItemname().equals(obj.getString(3))){
                        v= new BigDecimal(dict.getdItemvalue());
                    }
                }
                if(null==v)
                    logger.info("合同类型为空{}",obj.toJSONString());
                a.setContType(v);
            }
            if(null!=obj.getString(4)) {
                //便利查询合同类型
                a.setContDate(org.apache.commons.lang.time.DateUtils.parseDate(obj.getString(4),new String[]{"yyyy-MM-dd"}));
            }
            if(null!=obj.getString(5)) {
                //便利查询合同类型
                a.setContEndDate(org.apache.commons.lang.time.DateUtils.parseDate(obj.getString(5),new String[]{"yyyy-MM-dd"}));
            }
            if(obj.size()>=7 && null!=obj.getString(6)) {
                //便利查询合同类型
                a.setRemark(obj.getString(6));
            }
            return a;
        } catch (ParseException e) {
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
            if(null!=obj.getString(1))
            a.setAgName(obj.getString(1));
            if(null!=obj.getString(2))
            a.setAgNature(AgNature.getAgNatureMsgString(obj.getString(2)));
            if(null!=obj.getString(3))
            a.setAgCapital(new BigDecimal(obj.getString(3)));
            if(null!=obj.getString(4))
                a.setAgBusLic(obj.getString(4));
            if(null!=obj.getString(5))
                a.setAgBusLicb(org.apache.commons.lang.time.DateUtils.parseDate(obj.getString(5),new String[]{"yyyy-MM-dd"}));
            if(null!=obj.getString(6))
                a.setAgBusLice(org.apache.commons.lang.time.DateUtils.parseDate(obj.getString(6),new String[]{"yyyy-MM-dd"}));
            if(null!=obj.getString(7))
                a.setAgLegal(obj.getString(7));
            if(null!=obj.getString(8))
                a.setAgLegalCertype(AgCertType.getAgCertTypeMsgString(obj.getString(8)));
            if(null!=obj.getString(9))
                a.setAgLegalCernum(obj.getString(9));
            if(null!=obj.getString(10))
                a.setAgLegalMobile(obj.getString(10));
            if(null!=obj.getString(11))
                a.setAgHead(obj.getString(11));
            if(null!=obj.getString(12))
                a.setAgHeadMobile(obj.getString(12));
            if(null!=obj.getString(13))
                a.setAgRegAdd(obj.getString(13));
            if(null!=obj.getString(14))
                a.setAgBusScope(obj.getString(14));
            if(null!=obj.getString(15)) {
                COrganization org = departmentService.getByName(obj.getString(15));
                a.setAgDocPro(org==null?null:org.getId()+"");
            }
            if(null!=obj.getString(16)){
                COrganization org = departmentService.getByName(obj.getString(16));
                a.setAgDocDistrict(org==null?null:org.getId()+"");
            }
            return a;
        } catch (ParseException e) {
            logger.info("解析json{}:{}",e.getMessage(),obj.toJSONString());
            e.printStackTrace();
            throw e;
        }
    }


    private Capital parsePayMentFromJson(JSONArray obj)throws Exception{
        logger.info("解析json{}",obj.toJSONString());
        try {
            Capital c = new Capital();
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(obj.getString(2))){
                c.setcType(AgCapitalType.getAgNatureMsgString(obj.getString(2)));

            }
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(obj.getString(3))){
                c.setcAmount(new BigDecimal(obj.getString(3)));

            }
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(obj.getString(4))){
                c.setcPaytime(org.apache.commons.lang.time.DateUtils.parseDate(obj.getString(4),new String[]{"yyyy-MM-dd"}));
            }
            if(obj.size()>=6 && org.apache.commons.lang3.StringUtils.isNotEmpty(obj.getString(5))){
                c.setRemark(obj.getString(5));
            }

            return c;
        } catch (ParseException e) {
            logger.info("解析json{}:{}",e.getMessage(),obj.toJSONString());
            e.printStackTrace();
            throw new ProcessException("parsePayMentFromJson错误");
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
            JSONObject busitem2 = new JSONObject();
            JSONObject busitem3 = new JSONObject();
            JSONObject busitem4 = new JSONObject();
            JSONObject busitem5 = new JSONObject();
            JSONObject busitem6 = new JSONObject();
            JSONObject busitem7 = new JSONObject();
            JSONObject busitem8 = new JSONObject();
            JSONObject busitem9 = new JSONObject();

            if(obj.size()>4 && StringUtils.isNotBlank(obj.getString(3))) {
                busitem1.put("agId", AG.get(0).getId());
                busitem1.put("uniqNum", uniqNum);
                busitem1.put("agName", agName);
                busitem1.put("cwzbh", cwzbh);
                busitem1.put("p", obj.getString(3));
                busitem1.put("pn", obj.getString(4));
                res.add(busitem1);
            }
            if(obj.size()>6 && StringUtils.isNotBlank(obj.getString(5))) {
                busitem2.put("agId", AG.get(0).getId());
                busitem2.put("uniqNum", uniqNum);
                busitem2.put("agName", agName);
                busitem2.put("cwzbh", cwzbh);
                busitem2.put("p", obj.getString(5));
                busitem2.put("pn", obj.getString(6));
                res.add(busitem2);
            }

            if(obj.size()>8 && StringUtils.isNotBlank(obj.getString(7))) {
                busitem3.put("agId", AG.get(0).getId());
                busitem3.put("uniqNum", uniqNum);
                busitem3.put("agName", agName);
                busitem3.put("cwzbh", cwzbh);
                busitem3.put("p", obj.getString(7));
                busitem3.put("pn", obj.getString(8));
                res.add(busitem3);
            }
            if(obj.size()>10 && StringUtils.isNotBlank(obj.getString(9))) {
                busitem4.put("agId", AG.get(0).getId());
                busitem4.put("uniqNum", uniqNum);
                busitem4.put("agName", agName);
                busitem4.put("cwzbh", cwzbh);
                busitem4.put("p", obj.getString(9));
                busitem4.put("pn", obj.getString(10));
                res.add(busitem4);
            }

            if(obj.size()>12 && StringUtils.isNotBlank(obj.getString(11))) {
                busitem5.put("agId", AG.get(0).getId());
                busitem5.put("uniqNum", uniqNum);
                busitem5.put("agName", agName);
                busitem5.put("cwzbh", cwzbh);
                busitem5.put("p", obj.getString(11));
                busitem5.put("pn", obj.getString(12));
                res.add(busitem5);
            }

            if(obj.size()>14 && StringUtils.isNotBlank(obj.getString(13))) {
                busitem6.put("agId", AG.get(0).getId());
                busitem6.put("uniqNum", uniqNum);
                busitem6.put("agName", agName);
                busitem6.put("cwzbh", cwzbh);
                busitem6.put("p", obj.getString(13));
                busitem6.put("pn", obj.getString(14));
                res.add(busitem6);
            }

            if(obj.size()>16 && StringUtils.isNotBlank(obj.getString(15))) {
                busitem7.put("agId", AG.get(0).getId());
                busitem7.put("uniqNum", uniqNum);
                busitem7.put("agName", agName);
                busitem7.put("cwzbh", cwzbh);
                busitem7.put("p", obj.getString(15));
                busitem7.put("pn", obj.getString(16));
                res.add(busitem7);
            }

            if(obj.size()>18 &&StringUtils.isNotBlank(obj.getString(17))) {
                busitem8.put("agId", AG.get(0).getId());
                busitem8.put("uniqNum", uniqNum);
                busitem8.put("agName", agName);
                busitem8.put("cwzbh", cwzbh);
                busitem8.put("p", obj.getString(17));
                busitem8.put("pn", obj.getString(18));
                res.add(busitem8);
            }

            if(obj.size()>20 &&StringUtils.isNotBlank(obj.getString(19))) {
                busitem9.put("agId", AG.get(0).getId());
                busitem9.put("uniqNum", uniqNum);
                busitem9.put("agName", agName);
                busitem9.put("cwzbh", cwzbh);
                busitem9.put("p", obj.getString(19));
                busitem9.put("pn", obj.getString(20));
                res.add(busitem9);
            }

            return res;
        } catch (ProcessException e) {
            logger.info("解析json{}:{}",e.getMessage(),obj.toJSONString());
            e.printStackTrace();
            throw e;
        }
    }


    /**
     * 解析业务
     * @param obj
     * @return
     * @throws Exception
     */
    private AgentBusInfo parseBusFromJson(JSONObject obj, List<PlatForm>  platForms,List<Dict>  bustype, List<PayComp>  payCompList)throws Exception{
        logger.info("解析json{}",obj.toJSONString());
        try {
            String agid =   obj.getString("agId");
            String uniqNum =   obj.getString("uniqNum");
            String agName =    obj.getString("agName");
            String cwzbh =    obj.getString("cwzbh");
            String p =   obj.getString("p");
            String pn =    obj.getString("pn");

            ImportAgentExample importAgentExample = new ImportAgentExample();
            importAgentExample.or().andDataidEqualTo(uniqNum).andDatatypeEqualTo(AgImportType.BUSINESS.name()).andDealstatusEqualTo(Status.STATUS_0.status);
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
                for (PlatForm platForm : platForms) {
                    if (platForm.getPlatformName().equals(p)) {
                        ab.setBusPlatform(platForm.getPlatformNum());
                        break;
                    }
                }
            }

            if(StringUtils.isNotBlank(bus_json_array.getString(5))){
                String type = bus_json_array.getString(5).trim();
                for (Dict dict : bustype) {
                    if(dict.getdItemname().equals(type))
                    ab.setBusType(dict.getdItemvalue());
                }
            }

            if(StringUtils.isNotBlank(bus_json_array.getString(10)))
            ab.setBusSentDirectly(BigDecimal.valueOf(yesorno.indexOf(bus_json_array.getString(10))));
            if(StringUtils.isNotBlank(bus_json_array.getString(11)))
            ab.setBusDirectCashback(BigDecimal.valueOf(yesorno.indexOf(bus_json_array.getString(11))));
            if(StringUtils.isNotBlank(bus_json_array.getString(12)))
            ab.setBusIndeAss(BigDecimal.valueOf(yesorno.indexOf(bus_json_array.getString(12))));
            if(StringUtils.isNotBlank(bus_json_array.getString(13)))
            ab.setBusContact(bus_json_array.getString(13));
            if(StringUtils.isNotBlank(bus_json_array.getString(14)))
            ab.setBusContactMobile(bus_json_array.getString(14));
            if(StringUtils.isNotBlank(bus_json_array.getString(15)))
            ab.setBusContactEmail(bus_json_array.getString(15));
            if(StringUtils.isNotBlank(bus_json_array.getString(16)))
            ab.setBusContactPerson(bus_json_array.getString(16));
            if(StringUtils.isNotBlank(bus_json_array.getString(17)))
            ab.setBusRiskEmail(bus_json_array.getString(17));
            if(StringUtils.isNotBlank(bus_json_array.getString(18)))
            ab.setCloTaxPoint(bus_json_array.getBigDecimal(18));
            if(StringUtils.isNotBlank(bus_json_array.getString(19)))
            ab.setCloInvoice(BigDecimal.valueOf(yesorno.indexOf(bus_json_array.getString(19))));
            if(StringUtils.isNotBlank(bus_json_array.getString(20)))
            ab.setCloReceipt(BigDecimal.valueOf(yesorno.indexOf(bus_json_array.getString(20))));

            //打款公司
            if(StringUtils.isNotBlank(bus_json_array.getString(21)))
            for (PayComp payComp : payCompList) {
                if(payComp.getComName().equals(bus_json_array.getString(21))) {
                    ab.setCloPayCompany(payComp.getId());
                    break;
                }
            }

            //设置打款账户

            if(StringUtils.isNotBlank(bus_json_array.getString(22))) {
                AgentColinfo colinfo = new AgentColinfo();
                //收款账户 对公对私
                if (StringUtils.isNotBlank(bus_json_array.getString(22)))
                    colinfo.setCloType(BigDecimal.valueOf(gs.indexOf(bus_json_array.getString(22))));
                //收款账户 realname
                if (StringUtils.isNotBlank(bus_json_array.getString(23)))
                    colinfo.setCloRealname(bus_json_array.getString(23));
                //收款账户 卡号
                if (StringUtils.isNotBlank(bus_json_array.getString(24)))
                    colinfo.setCloBankAccount(bus_json_array.getString(24));
                //收款账户 开户行
                if (StringUtils.isNotBlank(bus_json_array.getString(25)))
                    colinfo.setCloBank(bus_json_array.getString(25));
                //收款账户 开户支行
                if (StringUtils.isNotBlank(bus_json_array.getString(26)))
                    colinfo.setCloBankBranch(bus_json_array.getString(26));

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
        c.andStatusEqualTo(Status.STATUS_1.status);
        int count = importAgentMapper.countByExample(example);
        example.setOrderByClause(" c_time desc ");
        example.setPage(new Page(page.getFrom(),page.getPagesize()));
        List<ImportAgent> list = importAgentMapper.selectByExample(example);
        page.setRows(list);
        page.setTotal(count);
        return page;
    }









}
