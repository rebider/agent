package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.CUserMapper;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.UserVo;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.IPaymentDetailService;
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

import static com.ryx.credit.common.enumc.OffsetPaytype.*;

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
    private OrderAdjMapper orderAdjMapper;
    @Autowired
    private ORemoveAccountMapper oRemoveAccountMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private CUserMapper cUserMapper;
    @Autowired
    private OReturnOrderMapper oReturnOrderMapper;
    @Autowired
    private IPaymentDetailService paymentDetailService;
    @Autowired
    private ORefundPriceDiffMapper refundPriceDiffMapper;

    private Logger logger = LoggerFactory.getLogger(OrderOffsetServiceImpl.class);
    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AgentResult OffsetArrears(List<OPaymentDetail> opaymentDetailList, BigDecimal amount, String paytype, String srcId) throws MessageException {
        //1、判断付款类型
        //2、检查欠款明细,总金额与抵扣金额
        //3、计算冲抵欠款明细，生成付款明细实体，修改欠款明细为付款中，
        //4、更新数据库
        logger.info("["+srcId+"]开始进行补款生成付款明细,补款类型["+ OffsetPaytype.getContentByValue(paytype) +"]");

        Map<String,Object> resultMap = new HashMap<>();
        List<OPaymentDetail> resPaymentDetail = new ArrayList<>();
        BigDecimal resAmt = BigDecimal.ZERO;
        AgentResult result = AgentResult.ok();
        if ( null == opaymentDetailList || !(opaymentDetailList.size()>0) || amount.compareTo(BigDecimal.ZERO)==0 || StringUtils.isBlank(srcId)){
            resultMap.put("offsetPaymentDetails",resPaymentDetail);
            resultMap.put("residueAmt",amount);
           return AgentResult.okMap(resultMap);
        }

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
                    //进行添加付款明细数据
                    oPayDetail.setId(idService.genId(TabId.O_PAY_DETAIL));
                    oPayDetail.setArrId(paymentDetail.getId());
                    oPayDetail.setPayType(paytype);
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
        }else if (paytype.equals(DDXZ.code)){ //线下销账
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
            ORemoveAccount oRemoveAccount = oRemoveAccountMapper.selectByPrimaryKey(srcId);
            boolean flag=true;
            boolean f=true;
            //1.获取补款实际到账金额
            BigDecimal residue=oRemoveAccount.getRamount();

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
                    logger.info("销账还款-------:"+initialize);
                    oPayDetail.setAmount(residue);
                    oPayDetail.setSrcId(oRemoveAccount.getId());
                    residue = residue.subtract(initialize);
                    resAmt = resAmt.add(oPayDetail.getAmount());
                }else if(residue.compareTo(paymentDetail.getPayAmount())==-1){
                    initialize.add(residue);
                    flag=false;
                    logger.info("销账还款-------:"+initialize.add(residue));
                    oPayDetail.setAmount(residue);
                    oPayDetail.setSrcId(oRemoveAccount.getId());
                    residue = BigDecimal.ZERO;
                    resAmt = resAmt.add(oPayDetail.getAmount());
                }else if(residue.compareTo(paymentDetail.getPayAmount())==1){
                    residue = residue.subtract(paymentDetail.getPayAmount());
                    oPayDetail.setAmount(paymentDetail.getPayAmount());
                    oPayDetail.setSrcId(oRemoveAccount.getId());
                    resAmt = resAmt.add(oPayDetail.getAmount());
                    logger.info("销账还款--------:"+paymentDetail.getPayAmount());
                }
                resPaymentDetail.add(paymentDetail);
                //进行添加付款明细数据
                oPayDetail.setId(idService.genId(TabId.O_PAY_DETAIL));
                oPayDetail.setArrId(paymentDetail.getId());
                oPayDetail.setPayType(paytype);
                oPayDetail.setBusStat(Status.STATUS_0.status);
                oPayDetail.setStatus(Status.STATUS_1.status);
                oPayDetail.setVersion(Status.STATUS_1.status);
                oPayDetail.setcTm(new Date());
                oPayDetail.setcUser(oRemoveAccount.getSubmitPerson());
                oPayDetails.add(oPayDetail);
                if(1!=oPayDetailMapper.insertSelective(oPayDetail)){
                    logger.info("付款明细添加失败");
                    throw new MessageException("付款明细添加失败");
                }
            }
            resultMap.put("offsetPaymentDetails",resPaymentDetail);
            resultMap.put("OfffsetAmt",resAmt);
            resultMap.put("residueAmt",residue);
        }else if (paytype.equals(DDTZ.code)){
            //待还金额
            BigDecimal arrearsAmt = BigDecimal.ZERO;
            for(OPaymentDetail oPaymentDetail:opaymentDetailList){
                if (oPaymentDetail.getPaymentStatus().compareTo(PaymentStatus.DF.code)==0
                        || oPaymentDetail.getPaymentStatus().compareTo(PaymentStatus.BF.code)==0
                        || oPaymentDetail.getPaymentStatus().compareTo(PaymentStatus.YQ.code)==0){
                    arrearsAmt = arrearsAmt.add(oPaymentDetail.getPayAmount());
                }
            }
            //付款明细
            List<OPayDetail> oPayDetails = new ArrayList<>();
            OrderAdj orderAdj = orderAdjMapper.selectByPrimaryKey(srcId);
            boolean flag=true;
            boolean f=true;
            //1.获取补款实际支付金额
            BigDecimal residue=orderAdj.getRefundAmount().subtract(orderAdj.getProRefundAmount());

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
                    oPayDetail.setSrcId(srcId);
                    residue = residue.subtract(initialize);
                }else if(residue.compareTo(paymentDetail.getPayAmount())==-1){
                    initialize.add(residue);
                    flag=false;
                    logger.info("还款-------:"+initialize.add(residue));
                    oPayDetail.setAmount(residue);
                    oPayDetail.setSrcId(srcId);
                    residue = BigDecimal.ZERO;
                }else if(residue.compareTo(paymentDetail.getPayAmount())==1){
                    residue = residue.subtract(paymentDetail.getPayAmount());
                    oPayDetail.setAmount(paymentDetail.getPayAmount());
                    oPayDetail.setSrcId(srcId);
                    logger.info("还款--------:"+paymentDetail.getPayAmount());
                }
                resPaymentDetail.add(paymentDetail);
                //进行添加付款明细数据
                oPayDetail.setId(idService.genId(TabId.O_PAY_DETAIL));
                oPayDetail.setArrId(paymentDetail.getId());
                oPayDetail.setPayType(paytype);
                oPayDetail.setBusStat(Status.STATUS_0.status);
                oPayDetail.setStatus(Status.STATUS_1.status);
                oPayDetail.setVersion(Status.STATUS_1.status);
                oPayDetail.setcTm(new Date());
                oPayDetail.setcUser(orderAdj.getAdjUserId());
                oPayDetails.add(oPayDetail);
                if(1!=oPayDetailMapper.insertSelective(oPayDetail)){
                    logger.info("付款明细添加失败");
                    throw new MessageException("付款明细添加失败");
                }
                paymentDetail.setPaymentStatus(PaymentStatus.FKING.code);
                if(1!=oPaymentDetailMapper.updateByPrimaryKeySelective(paymentDetail)){
                    logger.info("付款明细添加失败");
                    throw new MessageException("付款明细添加失败");
                }
            }
            resultMap.put("offsetPaymentDetails",resPaymentDetail);
            resultMap.put("residueAmt",residue);
        }else if (paytype.equals(THTK.code)){
            logger.info("退货退款进行抵扣入库");
            //待还金额
            BigDecimal arrearsAmt = BigDecimal.ZERO;
            for(OPaymentDetail oPaymentDetail:opaymentDetailList){
                if (oPaymentDetail.getPaymentStatus().compareTo(PaymentStatus.DF.code)==0
                        || oPaymentDetail.getPaymentStatus().compareTo(PaymentStatus.BF.code)==0
                        || oPaymentDetail.getPaymentStatus().compareTo(PaymentStatus.YQ.code)==0){
                    arrearsAmt = arrearsAmt.add(oPaymentDetail.getPayAmount());
                }
            }
            //付款明细
            List<OPayDetail> oPayDetails = new ArrayList<>();
            OReturnOrder oReturnOrder = oReturnOrderMapper.selectByPrimaryKey(srcId);
            boolean flag=true;
            boolean f=true;
            //1.获取补款实际支付金额
            BigDecimal residue=oReturnOrder.getReturnAmo();

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
                    oPayDetail.setSrcId(srcId);
                    residue = residue.subtract(initialize);
                }else if(residue.compareTo(paymentDetail.getPayAmount())==-1){
                    initialize.add(residue);
                    flag=false;
                    logger.info("还款-------:"+initialize.add(residue));
                    oPayDetail.setAmount(residue);
                    oPayDetail.setSrcId(srcId);
                    residue = BigDecimal.ZERO;
                }else if(residue.compareTo(paymentDetail.getPayAmount())==1){
                    residue = residue.subtract(paymentDetail.getPayAmount());
                    oPayDetail.setAmount(paymentDetail.getPayAmount());
                    oPayDetail.setSrcId(srcId);
                    logger.info("还款--------:"+paymentDetail.getPayAmount());
                }
                resPaymentDetail.add(paymentDetail);
                //进行添加付款明细数据
                oPayDetail.setId(idService.genId(TabId.O_PAY_DETAIL));
                oPayDetail.setArrId(paymentDetail.getId());
                oPayDetail.setPayType(paytype);
                oPayDetail.setBusStat(Status.STATUS_0.status);
                oPayDetail.setStatus(Status.STATUS_1.status);
                oPayDetail.setVersion(Status.STATUS_1.status);
                oPayDetail.setcTm(new Date());
                oPayDetail.setcUser(oReturnOrder.getcUser());
                oPayDetails.add(oPayDetail);
                if(1!=oPayDetailMapper.insertSelective(oPayDetail)){
                    logger.info("付款明细添加失败");
                    throw new MessageException("付款明细添加失败");
                }
                paymentDetail.setPaymentStatus(PaymentStatus.FKING.code);
                if(1!=oPaymentDetailMapper.updateByPrimaryKeySelective(paymentDetail)){
                    logger.info("付款明细添加失败");
                    throw new MessageException("付款明细添加失败");
                }
            }
            resultMap.put("offsetPaymentDetails",resPaymentDetail);
            resultMap.put("residueAmt",residue);
        }else if (paytype.equals(FRDK.code)){
            //待还金额
            BigDecimal arrearsAmt = BigDecimal.ZERO;
            for(OPaymentDetail oPaymentDetail:opaymentDetailList){
                if (oPaymentDetail.getPaymentStatus().compareTo(PaymentStatus.DF.code)==0
                        || oPaymentDetail.getPaymentStatus().compareTo(PaymentStatus.BF.code)==0
                        || oPaymentDetail.getPaymentStatus().compareTo(PaymentStatus.YQ.code)==0){
                    arrearsAmt = arrearsAmt.add(oPaymentDetail.getPayAmount());
                }
            }
            //付款明细
            List<OPayDetail> oPayDetails = new ArrayList<>();
            OReturnOrder oReturnOrder = oReturnOrderMapper.selectByPrimaryKey(srcId);
            boolean flag=true;
            boolean f=true;
            //1.获取补款实际支付金额
            BigDecimal residue=amount;

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
                    oPayDetail.setSrcId(srcId);
                    residue = residue.subtract(initialize);
                }else if(residue.compareTo(paymentDetail.getPayAmount())==-1){
                    initialize.add(residue);
                    flag=false;
                    logger.info("还款-------:"+initialize.add(residue));
                    oPayDetail.setAmount(residue);
                    oPayDetail.setSrcId(srcId);
                    residue = BigDecimal.ZERO;
                }else if(residue.compareTo(paymentDetail.getPayAmount())==1){
                    residue = residue.subtract(paymentDetail.getPayAmount());
                    oPayDetail.setAmount(paymentDetail.getPayAmount());
                    oPayDetail.setSrcId(srcId);
                    logger.info("还款--------:"+paymentDetail.getPayAmount());
                }
                resPaymentDetail.add(paymentDetail);
                //进行添加付款明细数据
                oPayDetail.setId(idService.genId(TabId.O_PAY_DETAIL));
                oPayDetail.setArrId(paymentDetail.getId());
                oPayDetail.setPayType(paytype);
                oPayDetail.setBusStat(Status.STATUS_0.status);
                oPayDetail.setStatus(Status.STATUS_1.status);
                oPayDetail.setVersion(Status.STATUS_1.status);
                oPayDetail.setcTm(new Date());
                oPayDetail.setcUser(oReturnOrder.getcUser());
                oPayDetails.add(oPayDetail);
                if(1!=oPayDetailMapper.insertSelective(oPayDetail)){
                    logger.info("付款明细添加失败");
                    throw new MessageException("付款明细添加失败");
                }
                paymentDetail.setPaymentStatus(PaymentStatus.FKING.code);
                if(1!=oPaymentDetailMapper.updateByPrimaryKeySelective(paymentDetail)){
                    logger.info("付款明细添加失败");
                    throw new MessageException("付款明细添加失败");
                }
            }
            resultMap.put("offsetPaymentDetails",resPaymentDetail);
            resultMap.put("residueAmt",residue);
        }else if (paytype.equals(DDMD.code)){
            //付款明细
            List<OPayDetail> oPayDetails = new ArrayList<>();
            ORefundPriceDiff diff = refundPriceDiffMapper.selectByPrimaryKey(srcId);
            boolean flag=true;
            boolean f=true;
            //1.获取补款实际支付金额
            BigDecimal residue=amount;

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
                    oPayDetail.setSrcId(srcId);
                    residue = residue.subtract(initialize);
                }else if(residue.compareTo(paymentDetail.getPayAmount())==-1){
                    initialize.add(residue);
                    flag=false;
                    logger.info("还款-------:"+initialize.add(residue));
                    oPayDetail.setAmount(residue);
                    oPayDetail.setSrcId(srcId);
                    residue = BigDecimal.ZERO;
                }else if(residue.compareTo(paymentDetail.getPayAmount())==1){
                    residue = residue.subtract(paymentDetail.getPayAmount());
                    oPayDetail.setAmount(paymentDetail.getPayAmount());
                    oPayDetail.setSrcId(srcId);
                    logger.info("还款--------:"+paymentDetail.getPayAmount());
                }
                resPaymentDetail.add(paymentDetail);
                //进行添加付款明细数据
                oPayDetail.setId(idService.genId(TabId.O_PAY_DETAIL));
                oPayDetail.setArrId(paymentDetail.getId());
                oPayDetail.setPayType(paytype);
                oPayDetail.setBusStat(Status.STATUS_0.status);
                oPayDetail.setStatus(Status.STATUS_1.status);
                oPayDetail.setVersion(Status.STATUS_1.status);
                oPayDetail.setcTm(new Date());
                oPayDetail.setcUser(diff.getcUser());
                oPayDetails.add(oPayDetail);
                if(1!=oPayDetailMapper.insertSelective(oPayDetail)){
                    logger.info("付款明细添加失败");
                    throw new MessageException("付款明细添加失败");
                }
                paymentDetail.setPaymentStatus(PaymentStatus.FKING.code);
                if(1!=oPaymentDetailMapper.updateByPrimaryKeySelective(paymentDetail)){
                    logger.info("付款明细添加失败");
                    throw new MessageException("付款明细添加失败");
                }

                if(diff!=null){
                    if(diff.getMachOweAmt()!=null && diff.getMachOweAmt().compareTo(BigDecimal.ZERO)>0){
                        logger.info("======错误抵扣=已抵扣{}",JSONObject.toJSONString(diff));
                        throw new MessageException("已抵扣");
                    }
                    if(diff.getMachOweAmt()!=null && diff.getMachOweAmt().compareTo(BigDecimal.ZERO)==0){
                        logger.info("======抵扣{}",JSONObject.toJSONString(diff));
                        diff.setMachOweAmt(amount.subtract(residue));
                        if(1!=refundPriceDiffMapper.updateByPrimaryKeySelective(diff)){
                            logger.info("======抵扣，更新补差记录失败{}",JSONObject.toJSONString(diff));
                            throw new MessageException("更新补差记录失败");
                        }
                    }
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

        if (paytype.equals(DDBK.code) || paytype.equals(DDXZ.code) ||  paytype.equals(DDTZ.code) || paytype.equals(THTK.code) || paytype.equals(FRDK.code)){
            //更新付款单明细
            for (OPayDetail oPayDetail:oPayDetails){
                oPayDetail.setBusStat(Status.STATUS_1.status);
                if(oPayDetailMapper.updateByPrimaryKeySelective(oPayDetail)!=1){
                    logger.info("付款明细更新失败");
                    throw new MessageException("付款明细更新失败");
                };
                OPaymentDetail oPaymentDetail = oPaymentDetailMapper.selectById(oPayDetail.getArrId());
                BigDecimal realAmt = oPaymentDetail.getRealPayAmount()==null?BigDecimal.ZERO:oPaymentDetail.getRealPayAmount();
                oPaymentDetail.setRealPayAmount(realAmt.add(oPayDetail.getAmount()));
                if (oPaymentDetail.getRealPayAmount().compareTo(oPaymentDetail.getPayAmount())>= 0){
                    oPaymentDetail.setPaymentStatus(PaymentStatus.JQ.code);
                    oPaymentDetail.setRealPayAmount(oPaymentDetail.getPayAmount());
                }else if (oPaymentDetail.getRealPayAmount().compareTo(oPaymentDetail.getPayAmount())==-1){
                    oPaymentDetail.setPaymentStatus(PaymentStatus.BF.code);
                }
                if (paytype.equals(DDBK.code)){
                    oPaymentDetail.setSrcType(PamentSrcType.XXBK.code);
                }else if (paytype.equals(DDXZ.code)){
                    oPaymentDetail.setSrcType(PamentSrcType.XXXZ.code);
                }else if (paytype.equals(DDTZ.code)){
                    oPaymentDetail.setSrcType(PamentSrcType.ORDER_ADJ_SETTLE.code);
                }else if (paytype.equals(THTK.code)){
                    oPaymentDetail.setSrcType(PamentSrcType.TUIKUAN_DIKOU.code);
                }else if (paytype.equals(FRDK.code)){
                    oPaymentDetail.setSrcType(PamentSrcType.FENRUN_DIKOU.code);
                }else if (paytype.equals(DDMD.code)){
                    oPaymentDetail.setSrcType(PamentSrcType.TUICHAJIA_DIKOU.code);
                }

                oPaymentDetail.setSrcId(srcId);
                oPaymentDetail.setPayTime(new Date());
                if (oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail)!=1){
                    logger.info("付款单明细更新失败");
                    throw new MessageException("付款单明细更新失败");
                };
                //更新付款单
                OPayment oPayment = oPaymentMapper.selectByPrimaryKey(oPaymentDetail.getPaymentId());
                oPayment.setRealAmount(oPayment.getRealAmount().add(oPaymentDetail.getRealPayAmount()));
                oPayment.setOutstandingAmount(oPayment.getOutstandingAmount().subtract(oPaymentDetail.getRealPayAmount()));
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
            if (oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail)!=1){
                logger.info("付款单明细更新失败");
                throw new MessageException("付款单明细更新失败");
            };
        }

        return AgentResult.ok();
    }

    @Override
    public List<OPayDetail> OffsetArrearsQuery(String paytype, String srcId) {

        OPayDetailExample oPayDetailExample = new OPayDetailExample();
        if (paytype.equals(QueryType.ARRID.code)){
            oPayDetailExample.or().andArrIdEqualTo(srcId)
                    .andBusStatEqualTo(Status.STATUS_1.status)
                    .andStatusEqualTo(Status.STATUS_1.status);
        }else if (paytype.equals(QueryType.SRCID.code)){
            oPayDetailExample.or().andSrcIdEqualTo(srcId)
                    .andBusStatEqualTo(Status.STATUS_1.status)
                    .andStatusEqualTo(Status.STATUS_1.status);
        }
        List<OPayDetail> oPayDetails = oPayDetailMapper.selectByExample(oPayDetailExample);

        for (OPayDetail oPayDetail:oPayDetails){
            UserVo userVo = cUserMapper.selectUserVoById(Long.valueOf(oPayDetail.getcUser()));
            oPayDetail.setcUser(userVo.getName());
        }

        return oPayDetails;
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
    public Map<String, Object> createTkeRecord(OPayment payment, BigDecimal thisPaymentOutstandingAmt) {
        Map<String, Object> oneTakeoutRecord = new HashMap<>();
        oneTakeoutRecord.put("orderId", payment.getOrderId());
        oneTakeoutRecord.put("paymentId", payment.getId());
        oneTakeoutRecord.put("payType", payment.getPayMethod());
        oneTakeoutRecord.put("payAmt", thisPaymentOutstandingAmt.setScale(2));
        oneTakeoutRecord.put("payment", payment);
        return oneTakeoutRecord;
    }

    public void updatePaymentDetail(OPaymentDetail updateOPaymentDetail, String srcId, String srcType) throws ProcessException {
        updateOPaymentDetail.setPaymentStatus(PaymentStatus.JQ.code);
        updateOPaymentDetail.setRealPayAmount(updateOPaymentDetail.getPayAmount());
        updateOPaymentDetail.setPayTime(new Date());
        updateOPaymentDetail.setSrcId(srcId);
        updateOPaymentDetail.setSrcType(srcType);
        int counts = oPaymentDetailMapper.updateByPrimaryKeySelective(updateOPaymentDetail);
        if (counts <= 0) {
            throw new ProcessException("更新付款明细失败");
        }
    }

    public void updatePaymentOutstandingAmt(String paymentId, BigDecimal payAmt) throws ProcessException {
        OPayment payment = paymentDetailService.getPaymentById(paymentId);
        payment.setRealAmount(payment.getRealAmount().add(payAmt));
        payment.setOutstandingAmount(payment.getOutstandingAmount().subtract(payAmt));
        payment.setPayStatus(PayStatus.PART_PAYMENT.code);
        int counts = oPaymentMapper.updateByPrimaryKeySelective(payment);
        if (counts <= 0) {
            throw new ProcessException("更新订单部分金额支付完成失败");
        }
    }

    public void updatePaymentComplete(String paymentId, String srcId, String srcType) throws ProcessException {
        OPayment payment = paymentDetailService.getPaymentById(paymentId);
        payment.setRealAmount(payment.getPayAmount());
        payment.setOutstandingAmount(BigDecimal.ZERO);
        payment.setPayCompletTime(new Date());
        payment.setPayStatus(PayStatus.CLOSED.code);
        payment.setPlanSucTime(new Date());
        int counts = oPaymentMapper.updateByPrimaryKeySelective(payment);
        if (counts <= 0) {
            throw new ProcessException("更新订单全部付款记录为完成");
        }
    }
    public void insertToPaymentDetail(String paymentId, OPaymentDetail paymentDetail, BigDecimal thisDetailPayAmt, String agentId, String srcId, String srcType, String userid) throws ProcessException {
        try {
            OPaymentDetail newDeatil = new OPaymentDetail();
            newDeatil.setId(idService.genId(TabId.o_payment_detail));
            newDeatil.setPaymentId(paymentId);
            newDeatil.setPaymentType(paymentDetail.getPaymentType());
            newDeatil.setOrderId(paymentDetail.getOrderId());
            newDeatil.setPayType(paymentDetail.getPayType());
            newDeatil.setPayAmount(thisDetailPayAmt);
            newDeatil.setRealPayAmount(thisDetailPayAmt);
            newDeatil.setPayTime(new Date());
            newDeatil.setAgentId(agentId);
            newDeatil.setSrcId(srcId);
            newDeatil.setSrcType(srcType);
            newDeatil.setPaymentStatus(PaymentStatus.JQ.code);
            newDeatil.setcDate(new Date());
            newDeatil.setcUser(userid);
            newDeatil.setPlanNum(BigDecimal.ZERO);
            newDeatil.setPlanPayTime(new Date());
            if(1!=oPaymentDetailMapper.insertSelective(newDeatil)){
                throw new MessageException("添加付款明细异常");
            }
        } catch (Exception e) {
            logger.error("插入抵扣付款明细失败srcId={" + srcId + "},srcType{" + srcType + "}", e);
            throw new ProcessException("插入抵扣付款明细失败srcId={" + srcId + "},srcType{" + srcType + "}");
        }
    }
}
