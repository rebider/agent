package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.order.OPayDetailMapper;
import com.ryx.credit.dao.order.OPaymentDetailMapper;
import com.ryx.credit.dao.order.OPaymentMapper;
import com.ryx.credit.dao.order.OSupplementMapper;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.OrderOffsetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static com.ryx.credit.common.enumc.OffsetPaytype.DDBK;

@Service("orderOffsetService")
public class OrderOffsetServiceImpl implements OrderOffsetService {

    @Autowired
    private OPaymentDetailMapper oPaymentDetailMapper;
    @Autowired
    private OPayDetailMapper oPayDetailMapper;
    @Autowired
    private OSupplementMapper oSupplementMapper;
    @Autowired
    private OPaymentMapper oPaymentMapper;
    @Autowired
    private IdService idService;

    private Logger logger = LoggerFactory.getLogger(OrderOffsetServiceImpl.class);
    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AgentResult OffsetArrears(List<OPaymentDetail> opaymentDetailList, BigDecimal amount, String paytype, String srcId) throws MessageException {
        //1、判断付款类型
        //2、检查欠款明细,总金额与抵扣金额
        //3、计算冲抵欠款明细，生成付款明细实体，修改欠款明细为付款中，
        //4、更新数据库
        logger.info("["+srcId+"]开始进行补款生成付款明细,补款类型["+ OffsetPaytype.getContentByValue(paytype) +"]");
        if ( null == opaymentDetailList || !(opaymentDetailList.size()>0) || amount.compareTo(BigDecimal.ZERO)==0 || StringUtils.isBlank(srcId)){
           return AgentResult.fail("参数传入错误");
        }
        Map<String,Object> resultMap = new HashMap<>();
        List<OPaymentDetail> resPaymentDetail = new ArrayList<>();
        BigDecimal resAmt = BigDecimal.ZERO;
        AgentResult result = AgentResult.ok();
        if (paytype.equals(DDBK.code)){ //订单补款
            //待还金额
            BigDecimal arrearsAmt = BigDecimal.ZERO;
            for(OPaymentDetail oPaymentDetail:opaymentDetailList){
                if (oPaymentDetail.getPaymentStatus().compareTo(PaymentStatus.DF.code)==0
                        || oPaymentDetail.getPaymentStatus().compareTo(PaymentStatus.BF.code)==0
                        || oPaymentDetail.getPaymentStatus().compareTo(PaymentStatus.YQ.code)==0){
                    arrearsAmt = arrearsAmt.add(oPaymentDetail.getPayAmount());
                }
            }
            if(amount.compareTo(arrearsAmt)>0){
                return AgentResult.fail("补款金额不能大于欠款金额!");
            }
            //付款明细
            List<OPayDetail> oPayDetails = new ArrayList<>();
            OSupplement oSupplement = oSupplementMapper.selectByPrimaryKey(srcId);
            boolean flag=true;
                boolean f=true;
                //1.获取补款实际支付金额
                BigDecimal residue=oSupplement.getPayAmount();

                //多条补款
                for (OPaymentDetail paymentDetail : opaymentDetailList) {
                    //2.累计还款金额
                    BigDecimal initialize = new BigDecimal(0);
                    if(f==false){
                        break;
                    }
                    if(residue.compareTo(new BigDecimal(0))==0 ||flag==false){
                        //如果销账金额已抵扣完销账则停止循环
                        f=false;
                        break;
                    }
                    OPayDetail oPayDetail = new OPayDetail();
                    if(residue.compareTo(paymentDetail.getPayAmount())==0){
                        initialize=paymentDetail.getPayAmount();
                        flag=false;
                        logger.info("还款-------:"+initialize);
                        oPayDetail.setAmount(residue);
                        oPayDetail.setSrcId(oSupplement.getId());
                        residue = residue.subtract(initialize);
                    }else if(residue.compareTo(paymentDetail.getPayAmount())==-1){
                        initialize.add(residue);
                        flag=false;
                        logger.info("还款-------:"+initialize.add(residue));
                        oPayDetail.setAmount(residue);
                        oPayDetail.setSrcId(oSupplement.getId());
                        residue = BigDecimal.ZERO;
                    }else if(residue.compareTo(paymentDetail.getPayAmount())==1){
                        residue = residue.subtract(paymentDetail.getPayAmount());
                        oPayDetail.setAmount(paymentDetail.getPayAmount());
                        oPayDetail.setSrcId(oSupplement.getId());
                        logger.info("还款--------:"+paymentDetail.getPayAmount());
                    }
                    resPaymentDetail.add(paymentDetail);
                    //更新付款单明细为付款中
//                    paymentDetail.setPaymentStatus(PaymentStatus.FKING.code);
//                    oPaymentDetailMapper.updateByPrimaryKeySelective(paymentDetail);
                    //进行添加付款明细数据
                    oPayDetail.setId(idService.genId(TabId.O_PAY_DETAIL));
                    oPayDetail.setArrId(paymentDetail.getId());
                    oPayDetail.setPayType(DDBK.code);
                    oPayDetail.setBusStat(Status.STATUS_0.status);
                    oPayDetail.setStatus(Status.STATUS_1.status);
                    oPayDetail.setVersion(Status.STATUS_1.status);
                    oPayDetail.setcTm(new Date());
                    oPayDetail.setcUser(oSupplement.getcUser());
                    oPayDetails.add(oPayDetail);
                    if(1!=oPayDetailMapper.insertSelective(oPayDetail)){
                        logger.info("付款明细添加失败");
                        throw new MessageException("付款明细添加失败");
                    }
                }
                resultMap.put("offsetPaymentDetails",resPaymentDetail);
                resultMap.put("residueAmt",residue);
        }
        return AgentResult.okMap(resultMap);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AgentResult OffsetArrearsCommit(BigDecimal amount, String paytype, String srcId) throws MessageException{
        List<OPayDetail> oPayDetails = getOpayMentDetails(srcId,paytype);
        if (null == oPayDetails || oPayDetails.size() == 0){
            return AgentResult.fail("未查询到付款明细信息");
        }

        BigDecimal offsetAmt = BigDecimal.ZERO;
        for (OPayDetail oPayDetail : oPayDetails) {
            offsetAmt = offsetAmt.add(oPayDetail.getAmount());
        }
        if (offsetAmt.compareTo(amount)!=0) return AgentResult.fail("冲抵金额与申请不一致");

        if (paytype.equals(DDBK.code    )){
            //更新付款单明细
            for (OPayDetail oPayDetail:oPayDetails){
                oPayDetail.setBusStat(Status.STATUS_1.status);
                if(oPayDetailMapper.updateByPrimaryKeySelective(oPayDetail)!=1){
                    logger.info("付款明细更新失败");
                    throw new MessageException("付款明细更新失败");
                };
                OPaymentDetail oPaymentDetail = oPaymentDetailMapper.selectById(oPayDetail.getArrId());
                oPaymentDetail.setRealPayAmount(oPaymentDetail.getRealPayAmount().add(oPayDetail.getAmount()));
                if (oPaymentDetail.getRealPayAmount().compareTo(oPaymentDetail.getPayAmount())==0){
                    oPaymentDetail.setPaymentStatus(PaymentStatus.JQ.code);
                }else if (oPaymentDetail.getRealPayAmount().compareTo(oPaymentDetail.getPayAmount())==-1){
                    oPaymentDetail.setPaymentStatus(PaymentStatus.BF.code);
                }
                if (oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail)!=0){
                    logger.info("付款单明细更新失败");
                    throw new MessageException("付款单明细更新失败");
                };
                //更新付款单
                OPaymentDetail oPaymentDetail1 = oPaymentDetailMapper.selectById(oPaymentDetail.getPaymentId());
                OPayment oPayment = oPaymentMapper.selectByPrimaryKey(oPaymentDetail1.getPaymentId());
                oPayment.setRealAmount(oPayment.getRealAmount().add(amount));
                oPayment.setPayAmount(oPayment.getOutstandingAmount());
                if(oPaymentMapper.updateByPrimaryKeySelective(oPayment)!=1){
                    logger.info("付款单更新失败");
                    throw new MessageException("付款单更新失败");
                };
            }
        }

        return AgentResult.ok();
    }

    @Override
    public AgentResult OffsetArrearsCancle(BigDecimal amount, String paytype, String srcId) throws MessageException{

        List<OPayDetail> opayDetails = getOpayMentDetails(srcId, paytype);
        if (null == opayDetails || opayDetails.size() == 0){
            logger.info("付款明细不存在");
            return AgentResult.fail("付款明细不存在");
        }
        BigDecimal offsetAmt = BigDecimal.ZERO;
        for (OPayDetail oPayDetail : opayDetails) {
            offsetAmt = offsetAmt.add(oPayDetail.getAmount());
        }
        if (offsetAmt.compareTo(amount)!=0) return AgentResult.fail("冲抵金额与申请不一致");
        for (OPayDetail oPayDetail:opayDetails){
            oPayDetail.setStatus(Status.STATUS_0.status);
            if(oPayDetailMapper.updateByPrimaryKeySelective(oPayDetail)!=1){
                logger.info("付款明细更新失败");
                throw new MessageException("付款明细更新失败");
            };
            OPaymentDetail oPaymentDetail = oPaymentDetailMapper.selectById(oPayDetail.getArrId());
            oPaymentDetail.setPaymentStatus(PaymentStatus.DF.code);
            if (oPaymentDetail.getRealPayAmount().compareTo(BigDecimal.ZERO)>0){
                oPaymentDetail.setPaymentStatus(PaymentStatus.BF.code);
            }
            if (oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail)!=0){
                logger.info("付款单明细更新失败");
                throw new MessageException("付款单明细更新失败");
            };
        }

        return AgentResult.ok();
    }

    @Override
    public AgentResult OffsetArrearsQuery(String paytype, String srcId) {
        return null;
    }

    public List<OPayDetail> getOpayMentDetails(String srcId,String paytype){
        OPayDetailExample oPayDetailExample = new OPayDetailExample();
        oPayDetailExample.or().andStatusEqualTo(Status.STATUS_1.status)
                .andSrcIdEqualTo(srcId)
                .andPayTypeEqualTo(paytype)
                .andBusStatEqualTo(Status.STATUS_0.status);
        List<OPayDetail> oPayDetails = oPayDetailMapper.selectByExample(oPayDetailExample);
        return oPayDetails;
    }

}
