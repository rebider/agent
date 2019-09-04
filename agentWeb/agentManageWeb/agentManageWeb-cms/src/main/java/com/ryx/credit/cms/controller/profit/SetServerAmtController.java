package com.ryx.credit.cms.controller.profit;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.SetServerAmt;
import com.ryx.credit.profit.service.ISetServerAmtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/SetServerAmtController")
public class SetServerAmtController extends BaseController {
    private static Logger LOG = LoggerFactory.getLogger(SetServerAmtController.class);
    @Autowired
    ISetServerAmtService setServerAmtService;

    @RequestMapping("/setServerAmtPage")
    public String setServerAmtPage() {
        return "profit/setServerAmt/setServerAmt";
    }

    @RequestMapping("/setServerAmtList")
    @ResponseBody
    public Object setServerAmtList(HttpServletRequest request) {
        Page page = pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(), null, null);
        TreeMap map = getRequestParameter(request);
        map.put("begin", page.getBegin());
        map.put("end", page.getEnd());

        return setServerAmtService.setServerAmtList(map, pageInfo);
    }
    @RequestMapping("/setServerAmtDetailPage")
    public String setServerAmtDetailPage() {
        return "profit/setServerAmt/setServerAmtImprotant";
    }
    @RequestMapping("/setServerAmtDetailList")
    @ResponseBody
    public Object setServerAmtDetailList(HttpServletRequest request) {
        Page page = pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(), null, null);
        TreeMap map = getRequestParameter(request);
        map.put("begin", page.getBegin());
        map.put("end", page.getEnd());

        return setServerAmtService.queryDataDetail(map, pageInfo);
    }

    @RequestMapping("/queryBumId")
    @ResponseBody
    public List<Map<String, Object>> queryBumId() {
        List<Map<String, Object>> list = setServerAmtService.queryBumCode();
        Map<String, Object> map = new HashMap<>();
        map.put("BUM_ID", "");
        map.put("BUM_NAME", "-----------请选择----------");
        list.add(0, map);
        return list;
    }

    @RequestMapping("/setServerAmtAdd")
    @ResponseBody
    public ModelAndView setServerAmtAdd() {
        ModelAndView modelAndView = new ModelAndView();
        /*AgentResult result = agentService.isAgent(getUserId()+"");
        request.setAttribute("isagent", result);*/
        modelAndView.setViewName("profit/setServerAmt/setServerAmtAdd");
        return modelAndView;
    }
    @RequestMapping("/queryD")
    @ResponseBody
    public List<Map<String,Object>> queryD(HttpServletRequest request,String bumId) {
        List<Map<String,Object>> list = setServerAmtService.queryD(bumId);
        return  list;
    }

    @RequestMapping("/getDate")
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public Object getDate(HttpServletRequest request) {

        TreeMap treeMap = getRequestParameter(request);
        Integer size;
        try {
           size = Integer.parseInt(treeMap.get("size").toString());
            if(size==0){
                LOG.info("==================未选择代理商=====================");
                return renderError("未选择代理商");
            }

        }catch (Exception e){
            e.printStackTrace();
            LOG.info("==================未选择代理商=====================");
            return renderError("未选择代理商");
        }
        int a =0;
        for (int i =0;i<size;i++){
            if((treeMap.get("isno"+i))==null){
                a++;
            }
            if(a==size){
                LOG.info("==================未选择代理商=====================");
                return renderError("未选择代理商");
            }
        }

        for (int i =0;i<size;i++){
            if((treeMap.get("isno"+i))!=null){
                Integer isno = Integer.parseInt(treeMap.get("isno"+i).toString());
                if(treeMap.get("inputA"+isno)==null){
                    LOG.info("==================未输入代理商名称=====================");
                    return renderError("未输入代理商名称");
                }
                if(treeMap.get("inputB"+isno)==null){
                    LOG.info("==================未输入代理商机构编码=====================");
                    return renderError("未输入代理商机构编码");
                }
                if( treeMap.get("bumId")==null){
                    LOG.info("==================未选择业务平台====================");
                    return renderError("未选择业务平台");
                }
                String agentName = treeMap.get("inputA"+isno).toString();
                String agentId = treeMap.get("agentId"+isno).toString();
                String bumCode = treeMap.get("inputB"+isno).toString();
                String bumId =  treeMap.get("bumId").toString();
                int count = Integer.parseInt(treeMap.get("coun").toString());
                for(int j = 0; j<=count;j++){
                    if(treeMap.get("SERVER_TYPE"+j)==null){
                        LOG.info("==================未选择费用名称=====================");
                        return renderError("未选择费用名称");
                    }
                    if(treeMap.get("CHARGE_TYPE"+j)==null){
                        LOG.info("==================未选择收费类型=====================");
                        return renderError("未选择收费类型");
                    }
                    if(treeMap.get("CHARGE_BASE"+j)==null){
                        LOG.info("==================未选择收费基数=====================");
                        return renderError("未选择收费基数");
                    }
                    String serverType = treeMap.get("SERVER_TYPE"+j).toString();//00：服务费
                    String chargeType = treeMap.get("CHARGE_TYPE"+j).toString();//00:借记S0//02:贷记S0//03:贷记D0//01:借记D0
                    String chargeBase =treeMap.get("CHARGE_BASE"+j).toString();//00:交易总量
                    BigDecimal chargeProportion=null;
                    try{
                       chargeProportion = new BigDecimal(treeMap.get("CHARGE_PROPORTION"+j).toString());//服务费比例
                        if((chargeProportion.compareTo(new BigDecimal(0.001))==-1)||chargeProportion.compareTo(new BigDecimal(0.009))==1){
                            LOG.info("==================服务费比例输入有误=====================");
                            return renderError("服务费比例为0.001-0.009之间");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        LOG.info("==================服务费比例输入有误=====================");
                        return renderError("服务费比例输入有误");
                    }
                    String chargePeriod;
                    if(treeMap.get("longTime"+j)==null){
                        String f_Time = treeMap.get("F_MONTH"+j).toString();
                        String e_Time = treeMap.get("E_MONTH"+j).toString();
                        if(Integer.parseInt(f_Time)>Integer.parseInt(e_Time)){

                            LOG.info("==================开始日期必须大于或等于结束日期=====================");
                            return renderError("开始日期必须大于或等于结束日期");
                        }
                        chargePeriod=f_Time+"-"+e_Time;
                    }else{
                        chargePeriod ="长期";
                    }
                    SetServerAmt setServerAmt = new SetServerAmt();
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    String format = sdf.format(date);
                    setServerAmt.setCreateDate(format);
                    setServerAmt.setAgentId(agentId);
                    setServerAmt.setAgentName(agentName);
                    setServerAmt.setBumCode(bumCode);
                    setServerAmt.setBumId(bumId);
                    setServerAmt.setServerType(serverType);
                    setServerAmt.setChargePeriod(chargePeriod);
                    setServerAmt.setChargeProportion(chargeProportion);
                    setServerAmt.setChargeType(chargeType);
                    setServerAmt.setChargeBase(chargeBase);
                    setServerAmt.setIsNoSon("00");
                    setServerAmt.setStatus("00");
                    setServerAmt.setcUser(getUserId().toString());
                    setServerAmtService.insertSelective(setServerAmt);
                }
            }
        }
        return  renderSuccess("添加服务费成功");
    }


    @RequestMapping("/cancel")
    @ResponseBody
    public void getDate(String id) {
        SetServerAmt record = new SetServerAmt();
        record.setId(id);
        record.setStatus("99");
        setServerAmtService.updateByPrimaryKeySelective(record);
    }
}