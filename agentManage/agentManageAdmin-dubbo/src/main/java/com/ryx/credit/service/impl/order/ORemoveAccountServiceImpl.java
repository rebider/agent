package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.RemoveAccotunMethod;
import com.ryx.credit.common.enumc.RemoveAccountStatus;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.order.ORemoveAccountMapper;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentExample;
import com.ryx.credit.pojo.admin.order.ORemoveAccount;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

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
                oRemoveAccount.setAgId(agent.getId());
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
            oRemoveAccount.setRstatus(RemoveAccountStatus.WCL.code);
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
}