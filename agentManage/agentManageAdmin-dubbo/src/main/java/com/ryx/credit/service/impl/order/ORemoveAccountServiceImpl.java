package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.BeanUtils;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.CUserMapper;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.order.OOrderMapper;
import com.ryx.credit.dao.order.OPaymentDetailMapper;
import com.ryx.credit.dao.order.OPaymentMapper;
import com.ryx.credit.dao.order.ORemoveAccountMapper;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.service.agent.BusinessPlatformService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.ORemoveAccountService;
import com.ryx.credit.service.order.OrderOffsetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ryx.credit.common.enumc.OffsetPaytype.DDBK;

/**
 * @Auther: lrr
 * @Date: 2019/9/20 09:44
 * @Description:销账实现类
 */
@Service("oRemoveAccountService")
public class ORemoveAccountServiceImpl implements ORemoveAccountService {
    private Logger logger = LoggerFactory.getLogger(ORemoveAccountServiceImpl.class);


    @Autowired
    private ORemoveAccountMapper oRemoveAccountMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private OOrderMapper oOrderMapper;
    @Autowired
    private OPaymentDetailMapper oPaymentDetailMapper;
    @Autowired
    private OPaymentMapper oPaymentMapper;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private  BusinessPlatformService businessPlatformService;
    @Autowired
    private CUserMapper cUserMapper;
    @Autowired
    private OrderOffsetService orderOffsetService;

    @Override
    public PageInfo removeAccountDetail(Map<String, Object> param, PageInfo pageInfo) {
        List<Map<String, Object>> maps = oRemoveAccountMapper.rAccountDetailList(param);
        if(null!=maps && maps.size()>0){
            for (Map<String, Object> map : maps) {
                String submit_person = String.valueOf(map.get("SUBMIT_PERSON"));
               if(StringUtils.isNotBlank(submit_person)){
                   CUser cUser = cUserMapper.selectById(submit_person);
                   if (null!=cUser && null !=cUser.getName()){
                       map.put("SUBMIT_PERSON",cUser.getName());
                   }
               }
            }
        }

        pageInfo.setTotal(oRemoveAccountMapper.rAccountDetailCount(param));
        pageInfo.setRows(maps);
        return pageInfo;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public List<String> importExcel(List<List<Object>> list, String userid) throws Exception {
        List<String> removeAccountList = new ArrayList<>();
        if (null == list && list.size() == 0) {
            logger.info("导入的数据为空");
            throw new MessageException("导入的数据为空");
        }
        int i=2;
        Calendar d = Calendar.getInstance();
        for (List<Object> objectList : list) {
            int j=i++;
            if (StringUtils.isBlank(String.valueOf(objectList.get(0))) && !String.valueOf(objectList.get(0)).equals("null")) {
                logger.info("代理商唯一码为空:{}", String.valueOf(objectList.get(0)));
                throw new MessageException("请填写第"+j+"行数据的代理商唯一码");
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(1))) && !String.valueOf(objectList.get(1)).equals("null")) {
                logger.info("业务平台号为空:{}", String.valueOf(objectList.get(1)));
                throw new MessageException("请填写第"+j+"行数据的业务平台号");
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(2)))&& !String.valueOf(objectList.get(2)).equals("null")) {
                logger.info("业务平台为空:{}", String.valueOf(objectList.get(2)));
                throw new MessageException("请填写第"+j+"行数据的业务平台");
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(3))) && !String.valueOf(objectList.get(3)).equals("null")) {
                logger.info("销账类型为空:{}", String.valueOf(objectList.get(3)));
                throw new MessageException("请填写第"+j+"行数据的销账类型");
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(4)))&& !String.valueOf(objectList.get(4)).equals("null")) {
                logger.info("销账金额为空:{}", String.valueOf(objectList.get(4)));
                throw new MessageException("请填写第"+j+"行数据的销账金额");
            }
            if (isInteger(String.valueOf(objectList.get(4)))==false){
                logger.info("请检查第"+j+"行数据的销账金额的正确性");
                throw new MessageException("请检查第"+j+"行数据的销账金额的正确性");
            }
            ORemoveAccount oRemoveAccount = new ORemoveAccount();
            //开始校验ag码 业务平台号是否有效
            AgentExample agent_Example= new AgentExample();
            AgentExample.Criteria criteria1 = agent_Example.createCriteria().andIdEqualTo(String.valueOf(objectList.get(0))).andStatusEqualTo(Status.STATUS_1.status);
            List<Agent> agentList = agentMapper.selectByExample(agent_Example);
            if (null==agentList || agentList.size()==0){
                logger.info("请检查第"+j+"行数据的代理商唯一码的正确性");
                throw new MessageException("请检查第"+j+"行数据的代理商唯一码的正确性");
            }else{
                Agent agent= agentList.get(0);
                oRemoveAccount.setAgId(agent.getId());
                oRemoveAccount.setAgName(agent.getAgName());
            }
            int num=0;
            String platName = String.valueOf(objectList.get(2));
            List<PlatForm> platFormList = businessPlatformService.queryAblePlatForm();
            for (int p=0;p<platFormList.size();p++) {
                num++;
                if(platFormList.get(p).getPlatformName().equals(platName.trim())){
                    platName=platFormList.get(p).getPlatformNum();
                    break;
                }else if(!platFormList.get(p).getPlatformName().equals(platName.trim())){
                    continue;
                }else if(num==platFormList.size()){
                    logger.info("请检查第"+j+"行数据业务平台的正确性");
                    throw new MessageException("请检查第"+j+"行数据业务平台的正确性");
                }
            }
            AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
            agentBusInfoExample.createCriteria().andBusNumEqualTo(String.valueOf(objectList.get(1))).andBusPlatformEqualTo(platName)
                    .andStatusEqualTo(Status.STATUS_1.status).andBusStatusEqualTo(Status.STATUS_1.status);
            List<AgentBusInfo> agentBusInfoList = agentBusInfoMapper.selectByExample(agentBusInfoExample);
            if(null==agentBusInfoList || agentBusInfoList.size()==0){
                logger.info("请检查第"+j+"行数据的业务平台号或者业务平台的正确性");
                throw new MessageException("请检查第"+j+"行数据的业务平台号或者业务平台的正确性");
            }
            oRemoveAccount.setId(idService.genId(TabId.O_REMOVE_ACCOUNT));
            oRemoveAccount.setSubmitPerson(userid);
            oRemoveAccount.setBusNum(String.valueOf(objectList.get(1)));//O码
            oRemoveAccount.setBusPlatform(platName);//业务平台
            if (String.valueOf(objectList.get(3)).equals(RemoveAccotunMethod.XXDK.msg)){
                oRemoveAccount.setPayMethod(RemoveAccotunMethod.XXDK.code);//销账类型
            }else if (String.valueOf(objectList.get(3)).equals(RemoveAccotunMethod.FRDK.msg)){
                oRemoveAccount.setPayMethod(RemoveAccotunMethod.FRDK.code);//销账类型
            }else{
                logger.info("请检查第"+j+"行数据的销账类型(填写线下打款 或者 分润抵扣)");
                throw new MessageException("请检查第"+j+"行数据的销账类型(填写线下打款 或者 分润抵扣)");
            }

            String amount = String.valueOf(objectList.get(4));
            oRemoveAccount.setRamount((new BigDecimal(amount)));//销账金额
            oRemoveAccount.setSubmitTime(Calendar.getInstance().getTime());
            oRemoveAccount.setRstatus(RemoveAccountStatus.WCL.code);//销账状态
            oRemoveAccount.setBatchNum(d.getTime().getTime() + "");
            oRemoveAccount.setVersion(Status.STATUS_1.status);
            oRemoveAccount.setStatus(Status.STATUS_1.status);
            oRemoveAccount.setRealRamount(new BigDecimal(0));
            oRemoveAccount.setLogicalVersion(LogicalVersion.ONE.code);
            //添加销账金额
            HashMap<Object, Object> map = new HashMap<>();
            map.put("platform",platName);
            map.put("busNum",String.valueOf(objectList.get(1)));
            map.put("agId",String.valueOf(objectList.get(0)));
            List<Map> listMap=  oOrderMapper.queryJjqk(map);
             if (null!=listMap && listMap.size()>0){
                 for (Map jjqk_map : listMap) {
                     String machinesAmount = String.valueOf(jjqk_map.get("JJQK"));//机具欠款
                     oRemoveAccount.setMachinesAmount(new BigDecimal(machinesAmount));
                 }
             }else{
                 oRemoveAccount.setMachinesAmount(new BigDecimal(0));
             }
            //进行添加
            if (1 != oRemoveAccountMapper.insertSelective(oRemoveAccount)) {
                logger.info("插入失败!");
                throw new MessageException("插入失败！");
            }
            removeAccountList.add(oRemoveAccount.getId());
        }

        return removeAccountList;
    }

    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRES_NEW)
    public List<String> fetchFhData(int nodecount, int shardingItem) throws Exception {
        //查询待处理的销账列表
        List<String> data = oRemoveAccountMapper.queryRaccountByStatus(FastMap
                        .fastMap("rStatus", RemoveAccountStatus.WCL.code)
                        .putKeyV("pagesize",100)
        );

        //更新销账为处理中，任务更新状态，下次不再查询
        data.forEach(ids ->{
            ORemoveAccount oRemoveAccount = oRemoveAccountMapper.selectByPrimaryKey(ids);
            oRemoveAccount.setRstatus(RemoveAccountStatus.CLZ.code);
            if(oRemoveAccountMapper.updateByPrimaryKeySelective(oRemoveAccount)!=1){
                logger.info("查询待处理的销账列表，并更新成处理中失败:{}",ids);
            }
        });

        return data;
    }

    @Override
    public boolean processData(List<String> ids) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        if (ids != null && ids.size() > 0) {
            for (String id : ids) {
                ORemoveAccountExample oRemoveAccountExample = new ORemoveAccountExample();
                oRemoveAccountExample.or().andRstatusEqualTo(RemoveAccountStatus.CLZ.code).andIdEqualTo(id);
                //每次处理一百条数据
                oRemoveAccountExample.setPage(new Page(0, 100));
                List<ORemoveAccount> oRemoveAccountList = oRemoveAccountMapper.selectByExample(oRemoveAccountExample);
                ORemoveAccount oRemoveAccount_item = null;
                //销账赋值
                if (oRemoveAccountList.size() > 0) {
                    oRemoveAccount_item = oRemoveAccountList.get(0);
                }
                //查询到销账信息才进行处理
                if (oRemoveAccount_item != null) {
                    //每次销账金额都会减少--剩余
                    BigDecimal residue = oRemoveAccount_item.getRamount();
                    BigDecimal real_ramount = new BigDecimal(0);
                    ArrayList<OPaymentDetail> lists = new ArrayList<>();
                    HashMap map = new HashMap();
                    //根据销账保存 为条件
                    if (null != oRemoveAccount_item.getRmonth()) {
                        String omonth = simpleDateFormat.format(oRemoveAccount_item.getRmonth()).substring(0, 7);
                        map.put("time", omonth);
                    }
                    if (StringUtils.isNotBlank(oRemoveAccount_item.getAgId())) {
                        map.put("agentId", oRemoveAccount_item.getAgId());
                    }
                    if (StringUtils.isNotBlank(oRemoveAccount_item.getBusPlatform())) {
                        map.put("busPlatform", oRemoveAccount_item.getBusPlatform());
                    }
                    if (StringUtils.isNotBlank(oRemoveAccount_item.getBusNum())) {
                        map.put("busNum", oRemoveAccount_item.getBusNum());
                    }
                    try {
                        BigDecimal outstandingAmount = new BigDecimal(0);
                        List<Map> orderList = oOrderMapper.arrearageQuery(map);
                        if (null != orderList && orderList.size() > 0) {
                            for (Map order_map : orderList) {
                                OPaymentExample oPaymentExample = new OPaymentExample();
                                OPaymentExample.Criteria criteria = oPaymentExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andOrderIdEqualTo(String.valueOf(order_map.get("OID"))).andAgentIdEqualTo(String.valueOf(order_map.get("AGENT_ID")));
                                List<OPayment> oPayments = oPaymentMapper.selectByExample(oPaymentExample);
                                if (null!=oPayments && oPayments.size()!=0){
                                    outstandingAmount=outstandingAmount.add(oPayments.get(0).getOutstandingAmount());
                                }

                            }
                            //查到相关的订单
                            for (int i = 0; i < orderList.size(); i++) {
                                Map mapItem = orderList.get(i);
                                //根据agentId和oId查询付款单的明细
                                HashMap<Object, Object> hashMap = new HashMap<>();
                                hashMap.put("orderId",String.valueOf(mapItem.get("OID")));
                                hashMap.put("agentId",String.valueOf(mapItem.get("AGENT_ID")));
                                if(StringUtils.isNotBlank(String.valueOf( mapItem.get("OMONTH")))){
                                    hashMap.put("omonth",String.valueOf( mapItem.get("OMONTH")));
                                }
                                List<OPaymentDetail> oPaymentDetailList = oPaymentDetailMapper.selectOPaymentDetail(hashMap);
                                lists.addAll(oPaymentDetailList);
                            }
                            AgentResult agentResult= orderOffsetService.OffsetArrears(lists, residue,OffsetPaytype.DDXZ.code, id);
                            if (!agentResult.isOK()){
                                throw new MessageException(agentResult.getMsg());
                            }else if (agentResult.getMapData() != null && agentResult.getMapData().size()!=0) {
                                Map<String, Object> resMapCash = agentResult.getMapData();
                                BigDecimal residueAmt = new BigDecimal(resMapCash.get("residueAmt").toString());
                                real_ramount = new BigDecimal(resMapCash.get("OfffsetAmt").toString());
                                if (residueAmt.compareTo(new BigDecimal(BigInteger.ZERO))==-1 || residueAmt.compareTo(new BigDecimal(BigInteger.ZERO))==1) {
                                    logger.info("销账失败:{}", "抵扣金额大于或小于欠款金额"+residueAmt);
                                    throw new MessageException("抵扣金额大于或小于欠款金额"+residueAmt);
                                }
                                List<OPaymentDetail> offsetPaymentDetails=(ArrayList)resMapCash.get("offsetPaymentDetails");
                            }

                            ORemoveAccount oRemoveAccount = oRemoveAccountMapper.selectByPrimaryKey(id);
                            AgentResult agentResultCommit = orderOffsetService.OffsetArrearsCommit(oRemoveAccount.getRamount(), OffsetPaytype.DDXZ.code, oRemoveAccount.getId());
                            if (!agentResultCommit.isOK()){
                                logger.info(agentResultCommit.getMsg());
                                throw new MessageException("销账数据提交更新异常！");
                            }


                        }else{
                            if(StringUtils.isNotBlank(oRemoveAccount_item.getAgId()) && null !=oRemoveAccount_item.getRmonth()){
                                Date rmonth = oRemoveAccount_item.getRmonth();
                                String rtime = simpleDateFormat.format(rmonth);
                                logger.info("没有获取到代理商:"+oRemoveAccount_item.getAgId()+",时间:"+rtime+"的数据");
                                throw new MessageException("没有获取到代理商:"+oRemoveAccount_item.getAgId()+",时间:"+rtime+"的数据");
                            } else if (StringUtils.isNotBlank(oRemoveAccount_item.getAgId())){
                                logger.info("没有获取到代理商:"+oRemoveAccount_item.getAgId()+"的数据");
                                throw new MessageException("没有获取到代理商:"+oRemoveAccount_item.getAgId()+"的数据");
                            }
                        }
                        //修改销账表
                        ORemoveAccount oRemoveAccount = oRemoveAccountMapper.selectByPrimaryKey(oRemoveAccount_item.getId());
                        if (null!=oRemoveAccount){
                            oRemoveAccount.setRstatus(RemoveAccountStatus.CLCG.code);
                            oRemoveAccount.setFinishTime(new Date());
                            //实际销账金额
                            oRemoveAccount.setRealRamount(real_ramount);
                            if (1!=oRemoveAccountMapper.updateByPrimaryKeySelective(oRemoveAccount)){
                                logger.info("销账更新失败");
                                throw new MessageException("销账更新失败");
                            }
                        }
                    } catch (MessageException  e) {
                        logger.error("生成销账任务异常：", e);
                        ORemoveAccount oRemoveAccount = oRemoveAccountMapper.selectByPrimaryKey(oRemoveAccount_item.getId());
                        if (null!=oRemoveAccount){
                            oRemoveAccount.setRstatus(RemoveAccountStatus.CLSB.code);
                            oRemoveAccount.setFinishTime(new Date());
                            oRemoveAccount.setFailCause(e.getMsg());
                            if (1!=oRemoveAccountMapper.updateByPrimaryKeySelective(oRemoveAccount)){
                                logger.info("销账更新失败:{}",oRemoveAccount.getId());
                            }else{
                                logger.info("销账更新成功:{}",oRemoveAccount.getId());
                            }
                        }
                        e.printStackTrace();
                        List<Dict> dicts = dictOptionsService.dictList(DictGroup.EMAIL.name(), DictGroup.REMOVEACCOUNT_FAIL_EMAIL.name());
                        String[] emailArr = new String[dicts.size()];
                        for (int i = 0; i < dicts.size(); i++) {
                            emailArr[i] = String.valueOf(dicts.get(i).getdItemvalue());
                        }
                        AppConfig.sendEmail(emailArr, "销账失败：" + MailUtil.printStackTrace(e), "销账失败");
                    }catch (Exception e) {
                        logger.error("生成销账任务异常：", e);
                        ORemoveAccount oRemoveAccount = oRemoveAccountMapper.selectByPrimaryKey(oRemoveAccount_item.getId());
                        if (null!=oRemoveAccount){
                            oRemoveAccount.setRstatus(RemoveAccountStatus.CLSB.code);
                            oRemoveAccount.setFinishTime(new Date());
                            oRemoveAccount.setFailCause(e.getLocalizedMessage().length() > 30 ? e.getLocalizedMessage().substring(0, 30) : e.getLocalizedMessage());
                            if (1!=oRemoveAccountMapper.updateByPrimaryKeySelective(oRemoveAccount)){
                                logger.info("销账更新失败:{}",oRemoveAccount.getId());
                            }else{
                                logger.info("销账更新成功:{}",oRemoveAccount.getId());
                            }
                        }
                        e.printStackTrace();
                        AppConfig.sendEmails("销账id:"+id+"错误信息:"+MailUtil.printStackTrace(e), "任务生成生成销账失误报警ORemoveAccountServiceImpl");
                    }
                }
            }
        }
        return true;
    }

    @Override
    public PageInfo orderDetailList(Map<String, Object> param, PageInfo pageInfo) {
        if(null!=param){
            String id = (String) param.get("id");
            if(StringUtils.isNotBlank(id)){
                ORemoveAccount oRemoveAccount = oRemoveAccountMapper.selectByPrimaryKey(id);
                if (null!=oRemoveAccount){
                    //判断逻辑版本号  1---》新数据
                    if(oRemoveAccount.getLogicalVersion().equals("1")){
                        List<Map<String, Object>> maps = oRemoveAccountMapper.orderDetailListNew(param);
                        if(null!=maps && maps.size()>0){
                            for (Map<String, Object> map : maps) {
                                String plan_num = String.valueOf(map.get("PLAN_NUM"));
                                String plan_num_now="第"+plan_num+"期";
                                map.put("PLAN_NUM",plan_num_now);

                            }
                        }
                        pageInfo.setTotal(oRemoveAccountMapper.orderDetailCountNew(param));
                        pageInfo.setRows(maps);

                    }else {
                        List<Map<String, Object>> maps = oRemoveAccountMapper.orderDetailList(param);
                        if(null!=maps && maps.size()>0){
                            for (Map<String, Object> map : maps) {
                                String plan_num = String.valueOf(map.get("PLAN_NUM"));
                                String plan_num_now="第"+plan_num+"期";
                                map.put("PLAN_NUM",plan_num_now);

                            }
                        }
                        pageInfo.setTotal(oRemoveAccountMapper.orderDetailCount(param));
                        pageInfo.setRows(maps);
                    }
                }
            }

        }

        return pageInfo;
    }


    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        Matcher match = pattern.matcher(str);
        return match.matches();
    }
}