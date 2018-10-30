package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.CashPayType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.order.OCashReceivablesMapper;
import com.ryx.credit.pojo.admin.order.OCashReceivables;
import com.ryx.credit.pojo.admin.order.OCashReceivablesExample;
import com.ryx.credit.pojo.admin.vo.OCashReceivablesVo;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.OCashReceivablesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 作者：cx
 * 时间：2018/10/29
 * 描述：现款付款明细
 */
@Service("oCashReceivablesService")
public class OCashReceivablesServiceImpl implements OCashReceivablesService {


    private Logger logger = LoggerFactory.getLogger(OCashReceivablesServiceImpl.class);

    @Autowired
    private OCashReceivablesMapper oCashReceivablesMapper;
    @Autowired
    private IdService idService;


    @Override
    public AgentResult check(OCashReceivables oCashReceivables) throws Exception {
        if(null==oCashReceivables)  return AgentResult.fail("内容不能为空");
        if(StringUtils.isBlank(oCashReceivables.getCashpayType()))  return AgentResult.fail("付款对象类型不能为空");
        if(StringUtils.isBlank(oCashReceivables.getSrcId()))  return AgentResult.fail("付款对象不能为空");
        if(StringUtils.isBlank(oCashReceivables.getAgentId()))  return AgentResult.fail("代理商ID不能为空");
        if(null==oCashReceivables.getAmount())  return AgentResult.fail("金额不能为空");
        if(StringUtils.isBlank(oCashReceivables.getPayType()))  return AgentResult.fail("付款类型不能为空");
        return AgentResult.ok();
    }


    /**
     * 独立事物审批任务
     * @param cpt
     * @param srcId
     * @param userId
     * @param checkDate
     * @return
     * @throws Exception
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    @Override
    public AgentResult approveTashBusiness(CashPayType cpt, String srcId, String userId,Date checkDate) throws Exception{
        List<OCashReceivables>  ocashList =  query(null,null,cpt,srcId, Arrays.asList(AgStatus.Approving.status));
        Calendar c = Calendar.getInstance();
        for (OCashReceivables oCashReceivables : ocashList) {
            logger.info("操作人[{}]审批[{}][{}][{}][{}]",userId,cpt.msg,srcId,checkDate,JSONObject.toJSONString(oCashReceivables));
            oCashReceivables.setCheckDate(checkDate);
            oCashReceivables.setCheckUser(userId);
            oCashReceivables.setuUser(userId);
            oCashReceivables.setuTime(c.getTime());
            if(1!=oCashReceivablesMapper.updateByPrimaryKeySelective(oCashReceivables)){
                throw new MessageException("更新现款付款明细失败");
            }
        }
        return AgentResult.ok();
    }

    /**
     * 添加及修改记录
     * @param oCashReceivablesList
     * @param user
     * @param agentId
     * @param cpt
     * @param srcId
     * @return
     * @throws Exception
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public  AgentResult addOCashReceivables(List<OCashReceivablesVo> oCashReceivablesList, String user,String agentId, CashPayType cpt, String srcId)throws Exception {
        logger.info("操作人[{}]操作付款信息[{}]",user, JSONObject.toJSONString(oCashReceivablesList));
        Date date = new Date();
        BigDecimal total = BigDecimal.ZERO;
        List<OCashReceivables> list = query(null,agentId,cpt,srcId,Arrays.asList(AgStatus.Create.status));
        for (OCashReceivables oCashReceivables : list) {
            if(!dele(oCashReceivables,user).isOK()){
                throw new MessageException("更新打开明细失败");
            }
        }
        if(oCashReceivablesList!=null){
            for (OCashReceivablesVo oCashReceivables : oCashReceivablesList) {
                oCashReceivables.setCashpayType(cpt.code);
                oCashReceivables.setSrcId(srcId);
                oCashReceivables.setAgentId(agentId);
                AgentResult checkRes = check(oCashReceivables);
                if(!checkRes.isOK()){
                    logger.info("操作人[{}]检查异常[{}]",user,checkRes.getMsg());
                    throw new MessageException(checkRes.getMsg());
                }
                if(oCashReceivables!=null && StringUtils.isNotBlank(oCashReceivables.getId())){
                    logger.info("操作人[{}]修改付款信息[{}]",user, JSONObject.toJSONString(oCashReceivables));
                    checkRes = update(oCashReceivables,user);
                    if(!checkRes.isOK()){
                        logger.info("操作人[{}]检查异常[{}]",user,checkRes.getMsg());
                        throw new MessageException(checkRes.getMsg());
                    }
                    total = total.add(oCashReceivables.getAmount());
                }else if(oCashReceivables!=null && StringUtils.isBlank(oCashReceivables.getId())){
                    logger.info("操作人[{}]添加付款信息[{}]",user, JSONObject.toJSONString(oCashReceivables));
                    checkRes = add(oCashReceivables,user);
                    if(!checkRes.isOK()){
                        logger.info("操作人[{}]检查异常[{}]",user,checkRes.getMsg());
                        throw new MessageException(checkRes.getMsg());
                    }
                    total = total.add(oCashReceivables.getAmount());
                }else{
                    logger.info("操作人[{}]修改付款信息[{}]",user, JSONObject.toJSONString(oCashReceivables));
                    throw new MessageException("内容不能为空");
                }
            }
        }
        total = total.setScale(2,BigDecimal.ROUND_HALF_UP);
        return AgentResult.ok(total);
    }

    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public AgentResult add(OCashReceivables oCashReceivables, String user) throws Exception{
        Calendar c = Calendar.getInstance();
        //新建状态
        oCashReceivables.setReviewStatus(AgStatus.Create.status);
        oCashReceivables.setcUser(user);
        if(null==oCashReceivables.getcTime())
        oCashReceivables.setcTime(c.getTime());
        if(null==oCashReceivables.getuTime())
        oCashReceivables.setuTime(c.getTime());
        if(null==oCashReceivables.getuUser())
        oCashReceivables.setuUser(user);
        oCashReceivables.setStatus(Status.STATUS_1.status);
        oCashReceivables.setVersion(Status.STATUS_1.status);
        oCashReceivables.setId(idService.genId(TabId.o_cash_receivables));
        if(1==oCashReceivablesMapper.insertSelective(oCashReceivables)){
            return AgentResult.ok();
        }
        return AgentResult.fail();
    }
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public AgentResult update(OCashReceivables oCashReceivables, String user)throws Exception {
        OCashReceivables db = oCashReceivablesMapper.selectByPrimaryKey(oCashReceivables.getId());
        db.setReviewStatus(oCashReceivables.getReviewStatus());
        db.setCashpayType(oCashReceivables.getCashpayType());
        db.setSrcId(oCashReceivables.getSrcId());
        db.setAgentId(oCashReceivables.getAgentId());
        db.setAmount(oCashReceivables.getAmount());
        db.setRealAmount(oCashReceivables.getRealAmount());
        db.setPayUser(oCashReceivables.getPayUser());
        db.setPayTime(oCashReceivables.getPayTime());
        db.setCollectCompany(oCashReceivables.getCollectCompany());
        db.setCheckDate(oCashReceivables.getCheckDate());
        db.setPayType(oCashReceivables.getPayType());
        db.setBillnum(oCashReceivables.getBillnum());
        db.setRemark(oCashReceivables.getRemark());
        Calendar c = Calendar.getInstance();
        if(null==oCashReceivables.getcUser())
            db.setcUser(user);
        if(null==oCashReceivables.getcTime())
            db.setcTime(c.getTime());
        db.setuTime(c.getTime());
        db.setuUser(user);
        db.setVersion(Status.STATUS_1.status);
        db.setStatus(Status.STATUS_1.status);
        if(1==oCashReceivablesMapper.updateByPrimaryKeySelective(db)){
            return AgentResult.ok();
        }
        return AgentResult.fail();
    }
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public AgentResult dele(OCashReceivables oCashReceivables, String user)throws Exception {
        OCashReceivables db = oCashReceivablesMapper.selectByPrimaryKey(oCashReceivables.getId());
        db.setStatus(Status.STATUS_0.status);
        db.setuUser(user);
        db.setuTime(Calendar.getInstance().getTime());
        if(1==oCashReceivablesMapper.updateByPrimaryKeySelective(db)){
            return AgentResult.ok();
        }
        return  AgentResult.fail();
    }


    /**
     * 启动流程更新为审批中
     * @param cpt
     * @param srcId
     * @return
     * @throws Exception
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public AgentResult startProcing(CashPayType cpt, String srcId,String userId) throws Exception {
        List<OCashReceivables>  ocashList =  query(null,null,cpt,srcId, Arrays.asList(AgStatus.Create.status));
        Calendar c = Calendar.getInstance();
        for (OCashReceivables oCashReceivables : ocashList) {
            oCashReceivables.setReviewStatus(AgStatus.Approving.status);
            if(StringUtils.isNotBlank(userId))
            oCashReceivables.setuUser(userId);
            oCashReceivables.setuTime(c.getTime());
            if(1!=oCashReceivablesMapper.updateByPrimaryKeySelective(oCashReceivables)){
                throw new MessageException("更新现款付款明细失败");
            }
        }
        return AgentResult.ok();
    }
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public AgentResult refuseProcing(CashPayType cpt, String srcId,String userId) throws Exception {
        List<OCashReceivables>  ocashList =  query(null,null,cpt,srcId, Arrays.asList(AgStatus.Approving.status));
        Calendar c = Calendar.getInstance();
        for (OCashReceivables oCashReceivables : ocashList) {
            oCashReceivables.setReviewStatus(AgStatus.Refuse.status);
            if(StringUtils.isNotBlank(userId))
            oCashReceivables.setuUser(userId);
            oCashReceivables.setuTime(c.getTime());
            if(1!=oCashReceivablesMapper.updateByPrimaryKeySelective(oCashReceivables)){
                throw new MessageException("更新现款付款明细失败");
            }
        }
        return AgentResult.ok();
    }
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public AgentResult finishProcing(CashPayType cpt, String srcId,String userId) throws Exception {
        List<OCashReceivables>  ocashList =  query(null,null,cpt,srcId, Arrays.asList(AgStatus.Approving.status));
        Calendar c = Calendar.getInstance();
        for (OCashReceivables oCashReceivables : ocashList) {
            oCashReceivables.setReviewStatus(AgStatus.Approved.status);
            if(StringUtils.isNotBlank(userId))
            oCashReceivables.setuUser(userId);
            oCashReceivables.setuTime(c.getTime());
            if(1!=oCashReceivablesMapper.updateByPrimaryKeySelective(oCashReceivables)){
                throw new MessageException("更新现款付款明细失败");
            }
        }
        return AgentResult.ok();
    }

    /**
     * 查询现付款明细
     * @param id
     * @param agentId
     * @param cpt
     * @param srcId
     * @param reviewStatus
     * @return
     */
    @Override
    public List<OCashReceivables> query(String id,String agentId, CashPayType cpt, String srcId, List<BigDecimal> reviewStatus) {
        OCashReceivablesExample example = new OCashReceivablesExample();
        OCashReceivablesExample.Criteria c = example.or().andStatusEqualTo(Status.STATUS_1.status);
        if(StringUtils.isNotBlank(id))
           c.andIdEqualTo(id);
        if(StringUtils.isNotBlank(agentId))
            c.andAgentIdEqualTo(agentId);
        if(StringUtils.isNotBlank(srcId))
            c.andSrcIdEqualTo(srcId);
        if(cpt!=null)
            c.andCashpayTypeEqualTo(cpt.code);
        if(reviewStatus!=null)
            c.andReviewStatusIn(reviewStatus);
        return oCashReceivablesMapper.selectByExample(example);
    }
}
