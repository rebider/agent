package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentColinfoMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.CapitalMapper;
import com.ryx.credit.dao.agent.ImportAgentMapper;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.service.agent.AccountPaidItemService;
import com.ryx.credit.service.agent.AgentColinfoService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.agent.AimportService;
import com.ryx.credit.service.dict.DepartmentService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by cx on 2018/6/11.
 */
@Service("aimportService")
public class AimportServiceImpl implements AimportService {

    private Logger logger = LoggerFactory.getLogger(AimportServiceImpl.class);

    @Autowired
    private ImportAgentMapper importAgentMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private AgentService agentService;
    @Autowired
    private AgentColinfoMapper agentColinfoMapper;
    @Autowired
    private AgentColinfoService agentColinfoService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private CapitalMapper capitalMapper;
    @Autowired
    private AccountPaidItemService accountPaidItemService;

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

        //导入代理商信息
        ImportAgentExample basequery = new ImportAgentExample();
        basequery.or().andStatusEqualTo(Status.STATUS_1.status).andDealstatusEqualTo(Status.STATUS_0.status).andDatatypeEqualTo(AgImportType.BASICS.name());
        List<ImportAgent>  baseList = importAgentMapper.selectByExampleWithBLOBs(basequery);
        logger.info("代理商基础信息处理{}",baseList.size());
        ResultVO baseRes = analysisBase(baseList, userid);
        logger.info("代理商基础信息处理{}",baseRes.getResInfo());


        //导入已支付款项
        ImportAgentExample payquery = new ImportAgentExample();
        basequery.or().andStatusEqualTo(Status.STATUS_1.status).andDealstatusEqualTo(Status.STATUS_0.status).andDatatypeEqualTo(AgImportType.PAYMENT.name());
        List<ImportAgent>  payqueryList = importAgentMapper.selectByExampleWithBLOBs(payquery);
        logger.info("代理商交款信息处理{}",payqueryList.size());
        ResultVO payqueryListres = analysisPayment(payqueryList,userid);
        logger.info("代理商交款信息处理{}",payqueryListres.getResInfo());

        return ResultVO.success(null);
    }






    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    private ResultVO analysisBase(List<ImportAgent>  data,String userid)throws Exception{
        try {
            for (ImportAgent datum : data) {

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
                AgentColinfo colinfo =  parseAgentColInfoFromJson(array);
                //修改
                if(agQuery.size()>0){
                    if(1!=agentService.updateAgent(ag)){
                        logger.info("更新代理商出错{}",datum.getId());
                        throw new ProcessException("更新代理商出错");
                    }
                    ImportAgent agent =  importAgentMapper.selectByPrimaryKey(datum.getId());
                    agent.setDealstatus(Status.STATUS_2.status);
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
                    if(1!=importAgentMapper.updateByPrimaryKeySelective(agent)){
                        logger.info("导入代理商插入失败{}",datum.getId());
                        throw new ProcessException("导入代理商插入失败");
                    }else{
                        logger.info("导入代理商插入成功{}",datum.getId());
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
    private ResultVO analysisPayment(List<ImportAgent>  data,String userid)throws Exception{
        try {
            for (ImportAgent datum : data) {
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
                if(1!=importAgentMapper.updateByPrimaryKeySelective(payment)){
                    logger.info("导入代理商付款金额插入失败{}",datum.getId());
                    throw new ProcessException("导入代理商付款金额插入失败");
                }else{
                    logger.info("导入代理商付款金额插入失败{}",datum.getId());
                }
            }
            return ResultVO.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    private Agent parseAgentFromJson(JSONArray obj)throws Exception{
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
            e.printStackTrace();
            throw e;
        }
    }


    private Capital parsePayMentFromJson(JSONArray obj)throws Exception{
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
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(obj.getString(5))){
            c.setRemark(obj.getString(5));
        }

        return c;
    }

    private AgentColinfo parseAgentColInfoFromJson(JSONArray obj){


        return null;
    }

}
