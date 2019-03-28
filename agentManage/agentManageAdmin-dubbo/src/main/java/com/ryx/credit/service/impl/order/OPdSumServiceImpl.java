package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.BeanUtils;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.CapitalMapper;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.pojo.admin.agent.Capital;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.IOPdSumService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service("OPdSumService")
public class OPdSumServiceImpl implements IOPdSumService {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(OPdSumServiceImpl.class);

    @Autowired
    OPaymentDetailMapper oPaymentDetailMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private OPdSumMapper oPdSumMapper;

    @Autowired
    private CapitalMapper capitalMapper;

    @Autowired
    private OCashReceivablesMapper oCashReceivablesMapper;
    @Autowired
    OPaymentMapper oPaymentMapper;
    @Autowired
    OOrderMapper oOrderMapper;

    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    @Override
    public void insertOPdSum()throws MessageException {
        String month = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE).substring(0, 7);
        Map<String, Object> mapMonth = new HashMap<>();
        mapMonth.put("month", month);
        List<Map<String, Object>> listOPd = oPaymentDetailMapper.getOpdSum(mapMonth);

        for (Map mapOPd : listOPd) {
            String agentId = (String) mapOPd.get("AGENT_ID");
            String orderPlatform = (String) mapOPd.get("ORDER_PLATFORM");
            BigDecimal sumAmount = (BigDecimal) mapOPd.get("SUM_AMOUNT");
            mapOPd.put("month", month);
          /*  Calendar c = Calendar.getInstance();
            c.set(Calendar.MONTH, +1);
            String date = DateUtil.format(c.getTime(), DateUtil.DATE_FORMAT_yyyyMM);*/
            OPdSum oPdSum = new OPdSum();
            oPdSum.setId(idService.genId(TabId.o_pd_sum));
            List<Map<String, Object>> listId = oPaymentDetailMapper.getOPaymentDetailID(mapOPd);
            for (Map<String, Object> mapid : listId) {
                String id = (String) mapid.get("ID");
                OPaymentDetail oPaymentDetail = oPaymentDetailMapper.selectByPrimaryKey(id);
                oPaymentDetail.setoPdSumId(oPdSum.getId());
                if(1!=oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail)){
                    logger.info("分期欠款汇总更新数据失败"+agentId+":"+month+":"+oPaymentDetail.getId()+":"+oPdSum.getId());
                    throw new MessageException("更新数据库失败"+agentId+":"+month+":"+oPaymentDetail.getId()+":"+oPdSum.getId());
                }
            }

            oPdSum.setAgentid(agentId);
            oPdSum.setPlatform(orderPlatform);
            oPdSum.setSumAmount(sumAmount);
            oPdSum.setSumStatus(DeductionSumStatus.Deduction_for.code);
            oPdSum.setStatus(Status.STATUS_1.status);
            oPdSum.setVersion(Status.STATUS_1.status);
            oPdSum.setSumMouth(month);
            /*  oPdSum.setcUser();*/
            oPdSum.setcTime(new Date());
            oPdSum.setuTime(new Date());
            if(1!=oPdSumMapper.insert(oPdSum)){
                logger.info("分期欠款汇总插入数据失败"+agentId+":"+month+":"+oPdSum.getId());
            }

        }

    }

    @Override
    public int updateByPrimaryKeySelective(OPdSum record) {
        return oPdSumMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO uploadStatus(List<Map<String, Object>> maps, BigDecimal payStatus) {
        if (null == maps && maps.size() < 0) {
            logger.info("更新数据为空:{}", maps);
            return ResultVO.fail("更新数据为空");
        }
        if (null == payStatus) {
            logger.info("支付状态为空:{}", maps);
            return ResultVO.fail("支付状态为空");
        }

        if (payStatus.compareTo(PaySign.JQ.code) == 0) {

            for (Map<String, Object> map : maps) {
                String detailId = (String) map.get("detailId");//付款汇总id
                String srcId = (String) map.get("srcId");//付款源id
                String srcType = map.get("srcType").toString();//付款源类型
                String payAmount = (String) map.get("mustDeductionAmtSum");//应扣
                String realPayAmount = (String) map.get("actualDeductionAmtSum");//实扣
                String notDeductionAmt = (String) map.get("notDeductionAmt");//未扣足
                String deductTime = (String) map.get("deductTime");//扣款时间
                if (StringUtils.isBlank(detailId)) {
                    logger.info("付款明细ID为空:{}", detailId);
                    throw new ProcessException("付款明细ID为空");
                }
                if (StringUtils.isBlank(srcId)) {
                    logger.info("付款源ID为空:{}", srcId);
                    throw new ProcessException("付款源ID为空");
                }
                if (StringUtils.isBlank(payAmount)) {
                    logger.info("应扣金额为空:{}", payAmount);
                    throw new ProcessException("应扣金额为空");
                }
                if (StringUtils.isBlank(realPayAmount)) {
                    logger.info("实扣金额为空:{}", realPayAmount);
                    throw new ProcessException("实扣金额为空");
                }
                if (StringUtils.isBlank(notDeductionAmt)) {
                    logger.info("未扣足金额为空:{}", notDeductionAmt);
                    throw new ProcessException("未扣足金额为空");
                }
                if (StringUtils.isBlank(deductTime)) {
                    logger.info("扣款时间为空:{}", deductTime);
                    throw new ProcessException("扣款时间为空");
                }
                if (StringUtils.isBlank(srcType)) {
                    logger.info("付款源类型为空:{}", srcType);
                    throw new ProcessException("付款源类型为空");
                }
                OPdSumExample oPdSumExample = new OPdSumExample();
                OPdSumExample.Criteria criteria = oPdSumExample.createCriteria();
                criteria.andIdEqualTo(detailId);
                criteria.andStatusEqualTo(Status.STATUS_1.status);
                List<OPdSum> oPdSums = oPdSumMapper.selectByExample(oPdSumExample);
                if (1 != oPdSums.size())
                    throw new ProcessException("没有查找到相关数据");
                OPdSum oPdSum = oPdSums.get(0);
                oPdSum.setRealDatetime(Calendar.getInstance().getTime());
                if (payStatus.compareTo(PaySign.JQ.code) == 0) {//已结清
                    if (new BigDecimal(payAmount).compareTo(oPdSum.getSumAmount()) != 0) {//判断传的参数应扣款和库里是否一致
                        logger.info("应扣金额有误");
                        throw new ProcessException("应扣金额有误");
                    }
                    oPdSum.setRealAmount(new BigDecimal(realPayAmount));
                    oPdSum.setId(detailId);
                    oPdSum.setPaySrc(srcId);
                    oPdSum.setPaySrcType(srcType);
                    oPdSum.setSumStatus(DeductionSumStatus.Deduction_Finish.code);
                    oPdSumMapper.updateByPrimaryKeySelective(oPdSum);
                }
                //判断未扣足金额是否等于应扣减去实扣
                BigDecimal notDeduction = new BigDecimal(payAmount).subtract(new BigDecimal(realPayAmount));
                if (notDeduction.compareTo(new BigDecimal(notDeductionAmt)) != 0) {
                    logger.info("未扣足金额有误");
                    throw new ProcessException("未扣足金额有误");
                }
                OPaymentDetailExample oPaymentDetailExample = new OPaymentDetailExample();
                OPaymentDetailExample.Criteria criteria1 = oPaymentDetailExample.createCriteria();
                criteria1.andOPdSumIdEqualTo(detailId);
                oPaymentDetailExample.setOrderByClause("plan_pay_time asc");
                List<OPaymentDetail> listOPaymentDetail = oPaymentDetailMapper.selectByExample(oPaymentDetailExample);
                //每次需要减去明细中的的金额
                BigDecimal subtractAmt = new BigDecimal(realPayAmount);
                for (OPaymentDetail oPaymentDetail : listOPaymentDetail) {
                    //未扣足金额为0是，说明所有扣款都已经扣完，即把明细表里的对应的扣款明细改为已结清
                    if (notDeduction.compareTo(BigDecimal.ZERO) == 0) {
                        oPaymentDetail.setPaymentStatus(PaymentStatus.JQ.code);
                        oPaymentDetail.setSrcId(srcId);
                        oPaymentDetail.setSrcType(srcType);
                        oPaymentDetail.setRealPayAmount(oPaymentDetail.getPayAmount());
                        oPaymentDetail.setPayTime(Calendar.getInstance().getTime());

                    } else if (notDeduction.compareTo(BigDecimal.ZERO) == 1) {

                        //未扣足金额大于0是，说明扣款是部分扣款，按照计划时间还款时间依次进行扣款

                        if (subtractAmt.compareTo(oPaymentDetail.getPayAmount()) == 1 || subtractAmt.compareTo(oPaymentDetail.getPayAmount()) == 0) {
                            oPaymentDetail.setPaymentStatus(PaymentStatus.YF.code);
                            oPaymentDetail.setSrcId(srcId);
                            oPaymentDetail.setSrcType(srcType);
                            oPaymentDetail.setRealPayAmount(oPaymentDetail.getPayAmount());
                            oPaymentDetail.setPayTime(Calendar.getInstance().getTime());
                            subtractAmt = subtractAmt.subtract(oPaymentDetail.getPayAmount());
                        } else if (subtractAmt.compareTo(oPaymentDetail.getPayAmount()) == -1) {

                            //最后一次不等于0时的付款，还款一部分，并将未还部分生成新的扣款记录，此条状态改为已还款，并将实扣金额清0
                            if (subtractAmt.compareTo(BigDecimal.ZERO) == 1) {
                                oPaymentDetail.setPaymentStatus(PaymentStatus.YF.code);
                                oPaymentDetail.setSrcId(srcId);
                                oPaymentDetail.setSrcType(srcType);
                                oPaymentDetail.setRealPayAmount(subtractAmt);
                                oPaymentDetail.setPayTime(Calendar.getInstance().getTime());


                                OPaymentDetail detail = new OPaymentDetail();
                                BeanUtils.copyProperties(oPaymentDetail, detail);
                                detail.setId(idService.genId(TabId.o_payment_detail));
                                detail.setPayAmount(oPaymentDetail.getPayAmount().subtract(subtractAmt));
                                detail.setPaymentStatus(PaymentStatus.DF.code);
                                detail.setRealPayAmount(BigDecimal.ZERO);
                                detail.setoPdSumId(null);
                                detail.setcDate(Calendar.getInstance().getTime());
                                detail.setSrcId(null);
                                detail.setSrcType(null);
                                detail.setVersion(oPaymentDetail.getVersion().add(new BigDecimal(1)));
                                if (1 != oPaymentDetailMapper.insertSelective(detail)) {
                                    logger.info("部分扣款失败拆分失败");
                                    throw new ProcessException("部分扣款失败拆分失败");
                                }
                                //将实扣款金额清0
                                subtractAmt = BigDecimal.ZERO;
                            }else  if (subtractAmt.compareTo(BigDecimal.ZERO) == 0) {
                            //剩余的全部更新为待付款

                                oPaymentDetail.setPaymentStatus(PaymentStatus.DF.code);
                                oPaymentDetail.setoPdSumId(null);
                                oPaymentDetail.setRealPayAmount(BigDecimal.ZERO);
                                oPaymentDetailMapper.updateByPrimaryKey(oPaymentDetail);
                            }

                        }

                    }

                    //判断源类型   如果是付款单则更新付款金额   如果是保证金则更新资金表的抵扣金额
                    if (PamentIdType.ORDER_BZJ.code.equals(oPaymentDetail.getPaymentType())) {

                        Capital capital = capitalMapper.selectByPrimaryKey(oPaymentDetail.getPaymentId());

                        BigDecimal cFqInAmount = capital.getcFqInAmount() == null ? new BigDecimal(0) : capital.getcFqInAmount();

                        capital.setcFqInAmount(cFqInAmount.add(oPaymentDetail.getRealPayAmount()));

                        if (1 != capitalMapper.updateByPrimaryKeySelective(capital)) {
                            logger.info("资金记录抵扣金额更新失败");
                            throw new ProcessException("资金记录抵扣金额更新失败");
                        }
                        //付款单分期抵扣处理
                    } else if (PamentIdType.ORDER_FKD.code.equals(oPaymentDetail.getPaymentType())) {
                        OPaymentExample oPaymentExample = new OPaymentExample();
                        OPaymentExample.Criteria criteri = oPaymentExample.createCriteria();
                        criteri.andStatusEqualTo(Status.STATUS_1.status);
                        criteri.andIdEqualTo(oPaymentDetail.getPaymentId());
                        List<OPayment> oPayments = oPaymentMapper.selectByExample(oPaymentExample);
                        if (1 != oPayments.size())
                            throw new ProcessException("没有查找到相关数据");
                        OPayment oPaymentss = oPayments.get(0);
                        if (oPaymentss.getRealAmount() == null) {
                            oPaymentss.setRealAmount(BigDecimal.ZERO);
                        }
                        //已付款金额
                        oPaymentss.setRealAmount(oPaymentss.getRealAmount().add(oPaymentDetail.getRealPayAmount()));
                        //待付款金额
                        oPaymentss.setOutstandingAmount(oPaymentss.getOutstandingAmount().subtract(oPaymentDetail.getRealPayAmount()));
                        if (1 != oPaymentMapper.updateByPrimaryKeySelective(oPaymentss)) {
                            logger.info("付款单更新数据失败");
                            throw new ProcessException("付款单更新数据失败");
                        }
                        //查询当前订单是否还有未结清的订单
                        OPaymentDetailExample detailExample = new OPaymentDetailExample();
                        OPaymentDetailExample.Criteria riteria = detailExample.createCriteria();
                        riteria.andPaymentIdEqualTo(oPaymentDetail.getPaymentId());
                        riteria.andStatusEqualTo(Status.STATUS_1.status);
                        riteria.andPaymentStatusIn(Arrays.asList(PaymentStatus.DF.code, PaymentStatus.BF.code, PaymentStatus.YQ.code, PaymentStatus.FKING.code));
                        List<OPaymentDetail> oPaymentDetai = oPaymentDetailMapper.selectByExample(detailExample);

                        if (oPaymentDetai.size() == 0 && oPaymentss.getOutstandingAmount().compareTo(BigDecimal.ZERO) == 0) {
                            //说明没有未结清的订单更新付款单状态
                            OPayment payment = new OPayment();
                            payment.setId(oPaymentDetail.getPaymentId());
                            payment.setPayStatus(PayStatus.CLOSED.code);
                            payment.setPayCompletTime(Calendar.getInstance().getTime());
                            if (1 != oPaymentMapper.updateByPrimaryKeySelective(payment)) {
                                logger.info("更新付款明细数据失败");
                                throw new ProcessException("付款明细更新数据失败");
                            }

                            //更新订单状态
                            OOrder oOrder = new OOrder();
                            oOrder.setId(oPaymentss.getOrderId());
                            oOrder.setClearStatus(ClearStatus.CLEARED.status);
                            if (1 != oOrderMapper.updateByPrimaryKeySelective(oOrder)) {
                                logger.info("订单更新数据失败");
                                throw new ProcessException("订单更新数据失败");
                            }
                        }
                    } else if (PamentIdType.ORDER_XX.code.equals(oPaymentDetail.getPaymentType())) {
                        OPaymentDetail oPaymentDe = oPaymentDetailMapper.selectById(detailId);
                        if (null == oPaymentDe) {
                            logger.info("没有付款明细数据");
                            throw new ProcessException("没有付款明细数据");
                        }
                        OCashReceivables oCashReceivables = oCashReceivablesMapper.selectByPrimaryKey(oPaymentDe.getPaymentId());
                        OCashReceivables receivables = new OCashReceivables();
                        if (oCashReceivables.getAmount().compareTo(oCashReceivables.getRealAmount()) == 0) {
                            receivables.setId(oPaymentDe.getPaymentId());
                            receivables.setPayStatus(PaySign.JQ.code);
                            if (1 != oCashReceivablesMapper.updateByPrimaryKeySelective(receivables)) {
                                logger.info("现款明细数据更新失败");
                                throw new ProcessException("现款明细数据更新失败");
                            }
                        } else {
                            receivables.setId(oPaymentDe.getPaymentId());
                            receivables.setPayStatus(PaySign.FKING.code);
                            receivables.setRealAmount(receivables.getRealAmount().add(oPaymentDe.getRealPayAmount()));
                            if (1 != oCashReceivablesMapper.updateByPrimaryKeySelective(receivables)) {
                                logger.info("更新现款明细数据失败");
                                throw new ProcessException("更新现款明细数据失败");
                            }
                        }
                    }


                    if (1 != oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail)) {
                        logger.info("付款明细更新数据失败");
                        throw new ProcessException("付款明细更新数据失败");


                    }

                }

            }

        }


        if (payStatus.compareTo(PaySign.FKING.code) == 0) {
            if(maps.size() > 0){
                for (Map map  :maps) {
                    OPdSum oPdSum = new OPdSum();
                    oPdSum.setId(map.get("detailId").toString());
                    oPdSum.setSumStatus(DeductionSumStatus.Deduction_lok.code);
                    oPdSum.setPaySrc(map.get("srcId").toString());

                    try{
                        oPdSumMapper.updateByPrimaryKeySelective(oPdSum);
                    }catch (Exception e){
                        e.getMessage();
                        throw new ProcessException("机具付款汇总更新数据失败");
                    }

                    //机具汇总传数据
                        String detailId = (String) map.get("detailId");//付款汇总id
                        String srcId = (String) map.get("srcId");//付款源id
                        if (StringUtils.isBlank(detailId)) {
                            logger.info("付款明细ID为空:{}", detailId);
                            throw new ProcessException("付款明细ID为空");
                        }
                        if (StringUtils.isBlank(srcId)) {
                            logger.info("付款源ID为空:{}", srcId);
                            throw new ProcessException("付款源ID为空");
                        }
                        //付款明细的数据
                        OPaymentDetailExample oPaymentDetailExample = new OPaymentDetailExample();
                        OPaymentDetailExample.Criteria criteria = oPaymentDetailExample.createCriteria();
                        criteria.andOPdSumIdEqualTo(detailId);
                        criteria.andStatusEqualTo(Status.STATUS_1.status);
                        List<OPaymentDetail> oPaymentDetails = oPaymentDetailMapper.selectByExample(oPaymentDetailExample);
                        if (oPaymentDetails.size()==0)
                            throw new ProcessException("没有查找到相关数据");
                        for (OPaymentDetail oPaymentDetail:oPaymentDetails) {
                            oPaymentDetail.setPayTime(Calendar.getInstance().getTime());
                            oPaymentDetail.setPaymentStatus(PaymentStatus.FKING.code);
                            oPaymentDetail.setSrcId(srcId);
                            if (1 != oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail)) {
                                logger.info("付款明细更新数据失败");
                                throw new ProcessException("付款明细更新数据失败");
                            }
                        }

                }

            }

        }

        return ResultVO.success("");
    }


}
