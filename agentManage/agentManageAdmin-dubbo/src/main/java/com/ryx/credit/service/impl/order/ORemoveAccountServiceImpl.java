package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.BeanUtils;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.order.OOrderMapper;
import com.ryx.credit.dao.order.OPaymentDetailMapper;
import com.ryx.credit.dao.order.OPaymentMapper;
import com.ryx.credit.dao.order.ORemoveAccountMapper;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentExample;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.ORemoveAccountService;
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

    @Override
    public PageInfo removeAccountDetail(Map<String, Object> param, PageInfo pageInfo) {
//        Long count = oRemoveAccountMapper.rAccountDetailCount(param);
        List<Map<String, Object>> list = oRemoveAccountMapper.rAccountDetailList(param);
        pageInfo.setTotal(list.size());
        pageInfo.setRows(list);
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
        Agent agent=new Agent();
        Calendar d = Calendar.getInstance();
        for (List<Object> objectList : list) {
            int j=i++;
            if (StringUtils.isBlank(String.valueOf(objectList.get(0)))) {
                logger.info("代理商唯一码为空:{}", String.valueOf(objectList.get(0)));
                throw new MessageException("请填写第"+j+"行数据的代理商唯一码");
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(1)))) {
                logger.info("业务平台号为空:{}", String.valueOf(objectList.get(1)));
                throw new MessageException("请填写第"+j+"行数据的业务平台号");
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(2)))) {
                logger.info("销账类型为空:{}", String.valueOf(objectList.get(2)));
                throw new MessageException("请填写第"+j+"行数据的销账类型");
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(3)))) {
                logger.info("销账金额为空:{}", String.valueOf(objectList.get(3)));
                throw new MessageException("请填写第"+j+"行数据的销账金额");
            }
            ORemoveAccount oRemoveAccount = new ORemoveAccount();
            oRemoveAccount.setId(idService.genId(TabId.O_REMOVE_ACCOUNT));
            oRemoveAccount.setSubmitPerson(userid);
            //查询代理商id和name
            AgentExample agentExample = new AgentExample();
            AgentExample.Criteria criteria = agentExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andAgUniqNumEqualTo(String.valueOf(objectList.get(0)));
            List<Agent> agentList = agentMapper.selectByExample(agentExample);
            if (null!=agentList && agentList.size()>0){
                agent= agentList.get(0);
                if(null!=agent.getId()){
                    oRemoveAccount.setAgId(agent.getId());
                }
                oRemoveAccount.setAgName(agent.getAgName());
            }else{
                logger.info("查无第"+j+"行代理商:"+String.valueOf(objectList.get(0)));
                throw new MessageException("查无第"+j+"行代理商:"+String.valueOf(objectList.get(0)));
            }
            oRemoveAccount.setBusNum(String.valueOf(objectList.get(1)));//O码
            if (String.valueOf(objectList.get(2)).equals(RemoveAccotunMethod.XXDK.msg)){
                oRemoveAccount.setPayMethod(RemoveAccotunMethod.XXDK.code);//销账类型
            }else if (String.valueOf(objectList.get(2)).equals(RemoveAccotunMethod.FRDK.msg)){
                oRemoveAccount.setPayMethod(RemoveAccotunMethod.FRDK.code);//销账类型
            }

            String amount = String.valueOf(objectList.get(3));
            oRemoveAccount.setRamount((new BigDecimal(amount)));//销账金额
            oRemoveAccount.setSubmitTime(Calendar.getInstance().getTime());
            oRemoveAccount.setRstatus(RemoveAccountStatus.WCL.code);//销账状态
            oRemoveAccount.setBatchNum(d.getTime().getTime() + "");
            oRemoveAccount.setVersion(Status.STATUS_1.status);
            oRemoveAccount.setStatus(Status.STATUS_1.status);
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
                    //根据销账保存 为条件
                    HashMap map = new HashMap();
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
                    try {
                        List<Map> orderList = oOrderMapper.arrearageQuery(map);
                        if (null != orderList && orderList.size() > 0) {
                            //查到相关的订单
                            for (int i = 0; i < orderList.size(); i++) {
                                Map mapItem = orderList.get(i);
                                //根据agentId和oId查询付款单的明细
                                OPaymentDetailExample oPaymentDetailExample = new OPaymentDetailExample();
                                OPaymentDetailExample.Criteria criteria = oPaymentDetailExample.createCriteria().andAgentIdEqualTo(String.valueOf(mapItem.get("AGENT_ID")))
                                        .andOrderIdEqualTo(String.valueOf(mapItem.get("OID"))).andStatusEqualTo(Status.STATUS_1.status).andPaymentStatusEqualTo(Status.STATUS_1.status);
                                List<OPaymentDetail> oPaymentDetailList = oPaymentDetailMapper.selectByExample(oPaymentDetailExample);
                                if (null != oPaymentDetailList && oPaymentDetailList.size() > 0) {
                                    //每次销账金额都会减少
                                    BigDecimal residue = oRemoveAccount_item.getRamount();
                                    for (OPaymentDetail oPaymentDetail : oPaymentDetailList) {
                                        //剩余
                                        //获取销账金额
                                        if (oPaymentDetail.getPayAmount().compareTo(residue) == 0 || oPaymentDetail.getPayAmount().compareTo(residue) == -1) {
                                            //应付金额等于实付金额  或者  应付金额小于实付金额  则是已结清
                                            oPaymentDetail.setPaymentStatus(PaymentStatus.JQ.code);
                                            residue=residue.subtract(oPaymentDetail.getRealPayAmount());
                                            oPaymentDetail.setRealPayAmount(oPaymentDetail.getRealPayAmount());
                                        } else {
                                            oPaymentDetail.setPaymentStatus(PaymentStatus.YF.code);
                                            residue=oPaymentDetail.getRealPayAmount().subtract(residue);
                                            oPaymentDetail.setRealPayAmount(oPaymentDetail.getRealPayAmount().subtract(residue));
                                        }
                                        oPaymentDetail.setSrcId(oRemoveAccount_item.getId());
                                        oPaymentDetail.setSrcType(oRemoveAccount_item.getPayMethod());
                                        oPaymentDetail.setPayTime(Calendar.getInstance().getTime());
                                        oPaymentDetail.setcUser(oRemoveAccount_item.getSubmitPerson());
                                        if (1 != oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail)) {
                                            logger.info("付款单明细修改失败{}:", oRemoveAccount_item.getId());
                                            throw new MessageException("付款单明细修改失败");
                                        }
                                        oPaymentDetail = oPaymentDetailMapper.selectByPrimaryKey(oPaymentDetail.getId());
                                        OPayment oPayment = oPaymentMapper.selectByPrimaryKey(oPaymentDetail.getPaymentId());
                                        if (null == oPayment) {
                                            logger.info("无此数据");
                                            throw new MessageException("无此数据!!!");
                                        }
                                        if (null == oPaymentDetail.getRealPayAmount()) {
                                            oPaymentDetail.setRealPayAmount(new BigDecimal(0));
                                        }
                                        oPayment.setRealAmount(oPayment.getRealAmount().add(oPaymentDetail.getRealPayAmount()));
                                        if (null == oPayment.getOutstandingAmount() || oPayment.getOutstandingAmount().compareTo(new BigDecimal(0)) == 0) {
                                            logger.info("此欠款已还完");
//                                        todo //需提示失败的原因
                                            continue;
                                        }
                                        //待付的付款明细
                                        List<OPaymentDetail> countMap = oPaymentDetailMapper.selectCount(oPaymentDetail.getOrderId(), PamentIdType.ORDER_FKD.code, PaymentStatus.DF.code);
                                        if (oRemoveAccount_item.getRamount().compareTo(oPayment.getOutstandingAmount()) == 1) {
                                            //如果销账金额大于欠款金额
//                                            logger.info("销账金额大于欠款金额");
//                                            throw new MessageException("销账金额大于欠款金额");
                                        } else if (oRemoveAccount_item.getRamount().compareTo(oPayment.getOutstandingAmount()) == 0) {
                                            if (null != countMap || countMap.size() > 0) {
                                                for (OPaymentDetail paymentDetail : countMap) {
                                                    paymentDetail.setPayAmount(new BigDecimal(0));
                                                    paymentDetail.setPaymentStatus(PaymentStatus.JQ.code);
                                                    if (1 != oPaymentDetailMapper.updateByPrimaryKeySelective(paymentDetail)) {
                                                        logger.info("实际付款金额等于欠款金额,更新失败");
                                                        throw new MessageException("更新失败");
                                                    }
                                                }
                                            }
                                            oPayment.setOutstandingAmount(oPayment.getOutstandingAmount().subtract(oPaymentDetail.getRealPayAmount()));
                                            if (1 != oPaymentMapper.updateByPrimaryKeySelective(oPayment)) {
                                                logger.info("付款单修改失败");
                                                throw new MessageException("付款单修改失败");
                                            }
                                        } else {
                                            //销账金额小于欠款金额
                                            oPayment.setOutstandingAmount(oPayment.getOutstandingAmount().subtract(oPaymentDetail.getRealPayAmount()));
                                            if (1 != oPaymentMapper.updateByPrimaryKeySelective(oPayment)) {
                                                logger.info("付款单修改失败");
                                                throw new MessageException("付款单修改失败");
                                            }
                                            BigDecimal payAmount = oPayment.getPayAmount();
                                            //  查询已结清的实际付款金额
                                            BigDecimal realPayAmount = oPaymentDetailMapper.selectRealAmount(oPaymentDetail.getOrderId(), PamentIdType.ORDER_FKD.code);
                                            //如果总金额大于实际已经付款金额  则还有未结清金额


                                            //再查询是否是最后一期的
                                            List<OPaymentDetail> notCountMap = oPaymentDetailMapper.selectCount(oPaymentDetail.getOrderId(), PamentIdType.ORDER_FKD.code, PaymentStatus.DF.code);
                                            //去查询还剩几期待付款
                                            BigDecimal count = new BigDecimal(notCountMap.size());
                                            if (count.compareTo(new BigDecimal(0)) == 0) {
                                                //如果就剩本条待付款  没有结清则新生成一条数据
                                                BigDecimal amount = oPaymentDetail.getPayAmount();//这个是订单需补款金额
                                                if (residue.compareTo(amount) == -1 ) {
                                                    //如果销账金额的剩余小于需补款金额  则新生成一条付款明细
                                                    OPaymentDetail oPaymentDetail_new  = new OPaymentDetail();
                                                    BeanUtils.copyProperties(oPaymentDetail,oPaymentDetail_new);
                                                    oPaymentDetail_new.setId(idService.genId(TabId.o_payment_detail));
                                                    oPaymentDetail_new.setPaymentStatus(PaymentStatus.DF.code);
                                                    oPaymentDetail_new.setRealPayAmount(BigDecimal.ZERO);
                                                    //上一条的需补款金额减去这次销账的金额
                                                    oPaymentDetail_new.setPayAmount(oPaymentDetail.getPayAmount().subtract(residue));
                                                    if(1!= oPaymentDetailMapper.insertSelective(oPaymentDetail_new)){
                                                        logger.info("重新生成新数据失败");
                                                        throw new MessageException("重新生成新数据失败");
                                                    }

                                                } else if (residue.compareTo(amount) == 0) {
                                                    //否则是相等的  则进行更新
                                                    oPaymentDetail.setPayAmount(residue);
                                                    if (1 != oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail)) {
                                                        logger.info("付款金额更新失败");
                                                        throw new MessageException("付款金额更新失败");
                                                    }
                                                }
                                            } else {
                                                BigDecimal money = oPaymentDetail.getPayAmount().subtract(residue);
                                                for (int j =notCountMap.size()-1; j < notCountMap.size(); j++) {
                                                    OPaymentDetail paymentDetail = notCountMap.get(j);
                                                    paymentDetail.setPayAmount(paymentDetail.getPayAmount().add(money));
                                                    if (1 != oPaymentDetailMapper.updateByPrimaryKeySelective(paymentDetail)) {
                                                        logger.info("更新到下期的付款金额失败");
                                                        throw new MessageException("更新到下期的付款金额失败");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (MessageException | ProcessException e) {

                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }
}