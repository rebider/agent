package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentColinfoVo;
import com.ryx.credit.service.AgentKafkaService;
import com.ryx.credit.service.agent.AgentColinfoService;
import com.ryx.credit.service.agent.AgentDataHistoryService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.pay.LivenessDetectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.management.resources.agent;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by cx on 2018/5/28.
 */
@Service("agentColinfoService")
public class AgentColinfoServiceImpl implements AgentColinfoService {


    private static Logger logger = LoggerFactory.getLogger(AgentColinfoServiceImpl.class);
    @Autowired
    private IdService idService;
    @Autowired
    private AgentColinfoMapper agentColinfoMapper;
    @Autowired
    private AgentColinfoRelMapper agentColinfoRelMapper;
    @Autowired
    private AttachmentRelMapper attachmentRelMapper;
    @Autowired
    private AgentDataHistoryService agentDataHistoryService;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private AColinfoPaymentMapper colinfoPaymentMapper;
    @Autowired
    private LivenessDetectionService livenessDetectionService;
    @Autowired
    private AgentKafkaService agentKafkaService;
    @Autowired
    private  AgentMapper agentMapper;


    /**
     * 代理商入网添加代理商交款项
     * @param ac
     * @param att
     * @return
     * @throws ProcessException
     */
    @Override
    public AgentColinfo agentColinfoInsert(AgentColinfo ac, List<String> att,String saveType) throws ProcessException {
        try {
            if (StringUtils.isEmpty(ac.getcUser())) {
                throw new ProcessException("操作人不能为空");
            }
            if (StringUtils.isEmpty(ac.getAgentId())) {
                throw new ProcessException("代理商ID不能为空");
            }
            if (null != ac.getCloTaxPoint() && ac.getCloTaxPoint().compareTo(new BigDecimal(1)) >= 0) {
                throw new ProcessException("税点不能大于1");
            }

            if(!"1".equals(saveType)){
                //检查属性
                checkColInfo(ac);
            }

            Date d = Calendar.getInstance().getTime();
            ac.setcTime(d);
            ac.setcUtime(d);
            ac.setStatus(Status.STATUS_1.status);
            ac.setVarsion(Status.STATUS_1.status);
            ac.setId(idService.genId(TabId.a_agent_colinfo));
            //导入的收款账户应该事收款成功
            if (!ac.isImport()) {
                ac.setPayStatus(ColinfoPayStatus.A.getValue());
            } else {
                ac.setPayStatus(ColinfoPayStatus.C.getValue());
            }
            //银行卡扫描件
            boolean isHaveYHKSMJ = false;
            //开户许可证
            boolean isHaveKHXUZ = false;
            //一般纳税人证明
            boolean isHaveYBNSRZM = false;

            if (att != null) {
                for (String s : att) {
                    if (org.apache.commons.lang.StringUtils.isEmpty(s)) continue;

                    Attachment attachment = attachmentMapper.selectByPrimaryKey(s);
                    if (attachment != null) {
                        if (AttDataTypeStatic.YHKSMJ.code.equals(attachment.getAttDataType() + "")) {
                            isHaveYHKSMJ = true;
                        }
                        if (AttDataTypeStatic.KHXUZ.code.equals(attachment.getAttDataType() + "")) {
                            isHaveKHXUZ = true;
                        }
                        if (AttDataTypeStatic.YBNSRZM.code.equals(attachment.getAttDataType() + "")) {
                            isHaveYBNSRZM = true;
                        }
                    }

                    AttachmentRel record = new AttachmentRel();
                    record.setAttId(s);
                    record.setSrcId(ac.getId());
                    record.setcUser(ac.getcUser());
                    record.setcTime(ac.getcTime());
                    record.setStatus(Status.STATUS_1.status);
                    record.setBusType(AttachmentRelType.Proceeds.name());
                    record.setId(idService.genId(TabId.a_attachment_rel));
                    if (1 != attachmentRelMapper.insertSelective(record)) {
                        logger.info("收款账号添加:{}", "收款账号添加附件关系失败");
                        throw new ProcessException("收款账号添加附件关系失败");
                    }
                }
            }

            if (!ac.isImport() && !"1".equals(saveType))
                if (ac.getCloType().compareTo(new BigDecimal("2")) == 0) {//对私
                    if (!isHaveYHKSMJ) {
                        throw new ProcessException("请添加银行卡扫描件");
                    }
                }

            if (!ac.isImport() && !"1".equals(saveType))
                if (ac.getCloType().compareTo(new BigDecimal("1")) == 0) {//对公
                    if (!isHaveKHXUZ) {
                        throw new ProcessException("请添加开户许可证");
                    }
                }

            //对公并且税点等于0.06一般纳税人证明必填
            if (!ac.isImport() && !"1".equals(saveType))
                if (ac.getCloType().compareTo(new BigDecimal("1")) == 0 && ac.getCloTaxPoint().compareTo(new BigDecimal("0.06")) == 0) {
                    if (!isHaveYBNSRZM) {
                        throw new ProcessException("请添加一般纳税人证明");
                    }
                }

            if (1 != agentColinfoMapper.insertSelective(ac)) {
                logger.info("收款账号添加:{}", "收款账号添加失败");
                throw new ProcessException("收款账号添加失败");
            }
            //记录历史收款账户信息
            if (!agentDataHistoryService.saveDataHistory(ac, DataHistoryType.GATHER.getValue()).isOK()) {
                logger.info("收款账号添加:{}", "收款账号添加失败,历史记录保存失败");
                throw new ProcessException("收款账号添加失败");
            }
        } catch (ProcessException e) {
            e.printStackTrace();
            throw new ProcessException(e.getMessage());
        }
        return ac;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public AgentResult saveAgentColinfoRel(AgentColinfoRel agentColinfoRel, String cUser) throws Exception {

        AgentResult result = new AgentResult(500, "参数错误", "");
        if (agentColinfoRel == null) {
            return result;
        }
        if (StringUtils.isBlank(agentColinfoRel.getAgentid()) || StringUtils.isBlank(agentColinfoRel.getBusPlatform())
                || StringUtils.isBlank(agentColinfoRel.getAgentbusid()) || StringUtils.isBlank(agentColinfoRel.getAgentColinfoid())) {
            return result;
        }

        AgentColinfoRelExample example = new AgentColinfoRelExample();
        AgentColinfoRelExample.Criteria criteria = example.createCriteria();
        criteria.andAgentbusidEqualTo(agentColinfoRel.getAgentbusid());
        criteria.andAgentidEqualTo(agentColinfoRel.getAgentid());
        criteria.andBusPlatformEqualTo(agentColinfoRel.getBusPlatform());
        List<AgentColinfoRel> agentColinfoRels = agentColinfoRelMapper.selectByExample(example);
        if (null != agentColinfoRels && agentColinfoRels.size() >= 1) {
            int deleteResult = agentColinfoRelMapper.deleteByExample(example);
            if (deleteResult != 1) {
                new AgentResult(500, "系统异常", "");
            }
        }

        Date d = Calendar.getInstance().getTime();
        agentColinfoRel.setcTime(d);
        agentColinfoRel.setStatus(Status.STATUS_1.status);
        agentColinfoRel.setId(idService.genId(TabId.a_agent_colinfo_rel));
        agentColinfoRel.setcUse(cUser);
        agentColinfoRel.setcSort(new BigDecimal(1));

        int insert = agentColinfoRelMapper.insert(agentColinfoRel);
        if (insert == 1) {
            return AgentResult.ok();
        }
        logger.info("saveAgentColinfoRel保存收款关系失败");
        return new AgentResult(500, "系统异常", "");
    }


    public List<AgentColinfo> queryAgentColinfoService(String agentId, String colId, BigDecimal appStatus) {
        AgentColinfoExample example = new AgentColinfoExample();
        AgentColinfoExample.Criteria c = example.or().andStatusEqualTo(Status.STATUS_1.status);
        if (org.apache.commons.lang.StringUtils.isNotEmpty(agentId)) {
            c.andAgentIdEqualTo(agentId);
        }
        if (org.apache.commons.lang.StringUtils.isNotEmpty(colId)) {
            c.andIdEqualTo(colId);
        }
        if (appStatus != null) {
            c.andCloReviewStatusEqualTo(appStatus);
        }
        example.setOrderByClause(" c_time desc ");
        return agentColinfoMapper.selectByExample(example);
    }


    public int update(AgentColinfo a) {
        return agentColinfoMapper.updateByPrimaryKeySelective(a);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO updateAgentColinfoVo(List<AgentColinfoVo> colinfoVoList, Agent agent, String userId,String saveStatus) throws Exception {
        try {
            if (agent == null) throw new MessageException("代理商信息不能为空");
            for (AgentColinfoVo agentColinfoVo : colinfoVoList) {
                if (null != agentColinfoVo.getCloTaxPoint() && agentColinfoVo.getCloTaxPoint().compareTo(new BigDecimal(1)) >= 0) {
                    throw new MessageException("税点不能大于1");
                }

                checkColInfo(agentColinfoVo);

                //收款账户对私时做校验
                String agentName = agent.getAgName();
                String agLegalName = agent.getAgLegal();
                String trueName = agentColinfoVo.getCloRealname();
                String certNo = agentColinfoVo.getAgLegalCernum();
                String cloBankAccount = agentColinfoVo.getCloBankAccount();
                if (agentColinfoVo.getCloType().compareTo(new BigDecimal(2)) == 0) {
                    //对私时 收款账户名与法人姓名一致时 把法人身份证号拷贝到户主身份证号并进行认证
                    if(StringUtils.isBlank(trueName)){
                        throw new MessageException("请输入结算卡收款账户名");
                    }
                    if(StringUtils.isBlank(certNo)){
                        throw new MessageException("请输入结算卡户主身份证");
                    }
                    if(StringUtils.isBlank(agentColinfoVo.getCloBankAccount())){
                        throw new MessageException("请输入结算卡卡号");
                    }
                    AgentResult result = livenessDetectionService.threeElementsCertificationDetection(trueName, certNo, userId,cloBankAccount);
                    if (!result.isOK()) {
                        throw new ProcessException("收款账户身份认证失败");
                    }

                }
                //对公时 判断收款账户名是否与代理商名称一致 不一致则抛异常提示信息
                if (agentColinfoVo.getCloType().compareTo(new BigDecimal(1)) == 0 && !"1".equals(saveStatus)) {
                    if (agentName.equals(trueName)) {
                        agentColinfoVo.setAgLegalCernum(agent.getAgLegalCernum());
                    } else if (!agentName.equals(trueName)) {
                        throw new ProcessException("收款账户名与代理商名称不一致");
                    }
                }

                agentColinfoVo.setcUser(agent.getcUser());
                agentColinfoVo.setAgentId(agent.getId());
                if (org.apache.commons.lang.StringUtils.isEmpty(agentColinfoVo.getId())) {
                    //直接新曾
                    AgentColinfo result = agentColinfoInsert(agentColinfoVo, agentColinfoVo.getColinfoTableFile(),null);
                    logger.info("代理商收款账户添加:{}{}", "添加代理商收款账户成功", result.getId());
                } else {

                    AgentColinfo db_AgentColinfo = agentColinfoMapper.selectByPrimaryKey(agentColinfoVo.getId());
                    db_AgentColinfo.setAgentId(agent.getId());
                    db_AgentColinfo.setCloType(agentColinfoVo.getCloType());
                    db_AgentColinfo.setCloRealname(agentColinfoVo.getCloRealname());
                    db_AgentColinfo.setCloBank(agentColinfoVo.getCloBank());
                    db_AgentColinfo.setCloBankBranch(agentColinfoVo.getCloBankBranch());
                    db_AgentColinfo.setCloBankAccount(agentColinfoVo.getCloBankAccount());
                    db_AgentColinfo.setRemark(agentColinfoVo.getRemark());
                    db_AgentColinfo.setStatus(agentColinfoVo.getStatus());
                    db_AgentColinfo.setBranchLineNum(agentColinfoVo.getBranchLineNum());
                    db_AgentColinfo.setAllLineNum(agentColinfoVo.getAllLineNum());
                    db_AgentColinfo.setBankRegion(agentColinfoVo.getBankRegion());
                    db_AgentColinfo.setCloInvoice(agentColinfoVo.getCloInvoice());
                    db_AgentColinfo.setCloTaxPoint(agentColinfoVo.getCloTaxPoint());
                    db_AgentColinfo.setCloBankCode(agentColinfoVo.getCloBankCode());
                    if(agentColinfoVo.getPayStatus()==null) {
                        db_AgentColinfo.setPayStatus(ColinfoPayStatus.A.getValue());
                    }
                    if (agLegalName.equals(trueName)) {
                        db_AgentColinfo.setAgLegalCernum(agent.getAgLegalCernum());
                    } else {
                        db_AgentColinfo.setAgLegalCernum(agentColinfoVo.getAgLegalCernum());
                    }
//                    db_AgentColinfo.setAmendStatus(AmendStatus.YXG.status);
                    db_AgentColinfo.setcUtime(Calendar.getInstance().getTime());
                    if (1 != agentColinfoMapper.updateByPrimaryKeySelective(db_AgentColinfo)) {
                        throw new MessageException("更新收款信息失败");
                    } else {
                        if (!agentDataHistoryService.saveDataHistory(db_AgentColinfo, db_AgentColinfo.getId(), DataHistoryType.GATHER.getValue(), userId, db_AgentColinfo.getVarsion()).isOK()) {
                            throw new MessageException("更新收款信息失败");
                        }
                    }
                    //删除老的附件
                    AttachmentRelExample example = new AttachmentRelExample();
                    example.or().andBusTypeEqualTo(AttachmentRelType.Proceeds.name()).andSrcIdEqualTo(db_AgentColinfo.getId()).andStatusEqualTo(Status.STATUS_1.status);
                    List<AttachmentRel> list = attachmentRelMapper.selectByExample(example);
                    for (AttachmentRel attachmentRel : list) {
                        attachmentRel.setStatus(Status.STATUS_0.status);
                        int i = attachmentRelMapper.updateByPrimaryKeySelective(attachmentRel);
                        if (1 != i) {
                            logger.info("修改收款信息附件关系失败{}", attachmentRel.getId());
                            throw new MessageException("更新收款信息信息失败");
                        }
                    }

                    //银行卡扫描件
                    boolean isHaveYHKSMJ = false;
                    //开户许可证
                    boolean isHaveKHXUZ = false;
                    //一般纳税人证明
                    boolean isHaveYBNSRZM = false;
                    //添加新的附件
                    List<String> fileIdList = agentColinfoVo.getColinfoTableFile();
                    if (fileIdList != null) {
                        for (String fileId : fileIdList) {

                            Attachment attachment = attachmentMapper.selectByPrimaryKey(fileId);
                            if (attachment != null) {
                                if (AttDataTypeStatic.YHKSMJ.code.equals(attachment.getAttDataType() + "")) {
                                    isHaveYHKSMJ = true;
                                }
                                if (AttDataTypeStatic.KHXUZ.code.equals(attachment.getAttDataType() + "")) {
                                    isHaveKHXUZ = true;
                                }
                                if (AttDataTypeStatic.YBNSRZM.code.equals(attachment.getAttDataType() + "")) {
                                    isHaveYBNSRZM = true;
                                }
                            }

                            AttachmentRel record = new AttachmentRel();
                            record.setAttId(fileId);
                            record.setSrcId(db_AgentColinfo.getId());
                            record.setcUser(db_AgentColinfo.getcUser());
                            record.setcTime(Calendar.getInstance().getTime());
                            record.setStatus(Status.STATUS_1.status);
                            record.setBusType(AttachmentRelType.Proceeds.name());
                            record.setId(idService.genId(TabId.a_attachment_rel));
                            int i = attachmentRelMapper.insertSelective(record);
                            if (1 != i) {
                                logger.info("收款信息附件关系失败");
                                throw new ProcessException("更新收款信息失败");
                            }
                        }
                    }
                    if (agentColinfoVo.getCloType().compareTo(new BigDecimal("2")) == 0) {//对私
                        if (!isHaveYHKSMJ  && !"1".equals(saveStatus)) {
                            throw new MessageException("请添加银行卡扫描件");
                        }
                    }
                    if (agentColinfoVo.getCloType().compareTo(new BigDecimal("1")) == 0) {//对公
                        if (!isHaveKHXUZ  && !"1".equals(saveStatus)) {
                            throw new MessageException("请添加开户许可证");
                        }
                    }

                    //对公并且税点等于0.06一般纳税人证明必填
                    if (agentColinfoVo.getCloType().compareTo(new BigDecimal("1")) == 0 && agentColinfoVo.getCloTaxPoint().compareTo(new BigDecimal("0.06")) == 0) {
                        if (!isHaveYBNSRZM  && !"1".equals(saveStatus)) {
                            throw new MessageException("请添加一般纳税人证明");
                        }
                    }
                }
            }
            return ResultVO.success(null);
        } catch (ProcessException e) {
            e.printStackTrace();
            throw e;
        } catch (MessageException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 检索原税点
     *
     * @param agentColinfo
     * @return
     */
    @Override
    public AgentColinfo queryPoint(AgentColinfo agentColinfo) {
        if (StringUtils.isBlank(agentColinfo.getAgentId())) {
            return null;
        }
        AgentColinfoExample example = new AgentColinfoExample();
        AgentColinfoExample.Criteria criteria = example.createCriteria();
        criteria.andAgentIdEqualTo(agentColinfo.getAgentId());
        criteria.andCloReviewStatusEqualTo(AgStatus.Approved.status);
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<AgentColinfo> colinfos = agentColinfoMapper.selectByExample(example);
        if (colinfos.size() != 1) {
            return null;
        }
        return colinfos.get(0);
    }

    @Override
    public int updateByPrimaryKeySelective(AgentColinfo record) {
        return agentColinfoMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult checkColInfo(AgentColinfo agentColinfo) throws ProcessException {
        try {
            if (agentColinfo.isImport()) return AgentResult.ok();
            /**
             * cxinfo 系统对开票和税点进行系统控制
             * 2、如果前面是对私户进行打款，那么是否开票默认为否且不可修改
             3、如果代理商前面是对私户进行打款，那么扣税点在代理商填写时默认为6%且不可修改
             4、如果代理商前面是对公户进行打款，且代理商是否开票为否，那么扣税点在代理商填写时默认为6%，且不可修改
             5、如果代理商前面是对公户进行打款，且代理商是否开票为是，那么扣税点在代理商填写时只能选择6%或3%
             */
            if (agentColinfo.getCloType().compareTo(new BigDecimal(2)) == 0) { //对私
                //税点检查
                if (agentColinfo.getCloTaxPoint() == null || !"0.08".equals(agentColinfo.getCloTaxPoint().toString())) { //对私
                    throw new ProcessException("对私户进行打款，那么扣税点在代理商填写时默认为0.08且不可修改");
                }
                //是否开票检查
                if (agentColinfo.getCloInvoice().compareTo(new BigDecimal(0)) != 0) { //对私
                    throw new ProcessException("对私户进行打款，那么是否开票默认为否且不可修改");
                }
            } else if (agentColinfo.getCloType().compareTo(new BigDecimal(1)) == 0) {//对公
                //是否开票检查
                if (agentColinfo.getCloInvoice().compareTo(new BigDecimal(1)) == 0) { //开票
                    //税点检查
                    if (!"0.06".equals(agentColinfo.getCloTaxPoint().toString()) && !"0.03".equals(agentColinfo.getCloTaxPoint().toString())) { //对私
                        throw new ProcessException("对公户进行打款，且代理商是否开票为是，那么扣税点在代理商填写时只能选择0.06或0.03");
                    }
                } else if (agentColinfo.getCloInvoice().compareTo(new BigDecimal(0)) == 0) { //不开票
//                        对公   是否开发票 则对应为是  不可修改
                    throw new ProcessException("对公户进行打款，那么是否开票默认为是且不可修改");
                }


            }
        } catch (ProcessException e) {
            e.printStackTrace();
            throw new ProcessException(e.getMessage());
        }
        return AgentResult.ok();
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertByPayment(AColinfoPayment colinfoPayment) throws Exception {
        String id = idService.genId(TabId.A_COLINFO_PAYMENT);
        colinfoPayment.setId(id);
        colinfoPayment.setBalanceLs(id);  //流水号
        int insert = colinfoPaymentMapper.insert(colinfoPayment);
        AgentColinfo agentColinfo = agentColinfoMapper.selectByPrimaryKey(colinfoPayment.getColinfoId());
        agentColinfo.setPayStatus(ColinfoPayStatus.B.getValue());
        int update = agentColinfoMapper.updateByPrimaryKeySelective(agentColinfo);
        if (insert != 1 || update != 1) {
            logger.info("insertByPayment,insert：{},update：{}", insert, update);
            throw new Exception("更新返回结果异常");
        }
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateByPaymentResult(AColinfoPayment aColinfoPayment, Map<String, Object> resultMap) throws Exception {
        AColinfoPayment upPayment = new AColinfoPayment();
        upPayment.setId(aColinfoPayment.getId());
        upPayment.setFlag(String.valueOf(resultMap.get("flag")));
        if (String.valueOf(resultMap.get("errDesc")) != "null")
            upPayment.setErrDesc(String.valueOf(resultMap.get("errDesc")));
        if (String.valueOf(resultMap.get("channelId")) != "null")
            upPayment.setChannelId(String.valueOf(resultMap.get("channelId")));
        if (String.valueOf(resultMap.get("createBatchTime")) != "null")
            upPayment.setCreateBatchTime(String.valueOf(resultMap.get("createBatchTime")));
        if (String.valueOf(resultMap.get("batchCode")) != "null")
            upPayment.setBatchCode(String.valueOf(resultMap.get("batchCode")));
        if (String.valueOf(resultMap.get("dataSource")) != "null")
            upPayment.setDatasource(String.valueOf(resultMap.get("dataSource")));
        if (String.valueOf(resultMap.get("payDate")) != "null")
            upPayment.setPayDate(String.valueOf(resultMap.get("payDate")));
        if (String.valueOf(resultMap.get("orgAccountId")) != "null")
            upPayment.setOrgAccountId(String.valueOf(resultMap.get("orgAccountId")));
        if (String.valueOf(resultMap.get("reserve")) != "null")
            upPayment.setReserve(String.valueOf(resultMap.get("reserve")));
        if (String.valueOf(resultMap.get("memo")) != "null")
            upPayment.setMemo(String.valueOf(resultMap.get("memo")));
        if (String.valueOf(resultMap.get("sysflowsource")) != "null")
            upPayment.setSysflowsource(String.valueOf(resultMap.get("sysflowsource")));
        if (String.valueOf(resultMap.get("remark")) != "null")
            upPayment.setRemark(String.valueOf(resultMap.get("remark")));
        int upPay = colinfoPaymentMapper.updateByPrimaryKeySelective(upPayment);

        AgentColinfo upColinfo = agentColinfoMapper.selectByPrimaryKey(aColinfoPayment.getColinfoId());
        if (String.valueOf(resultMap.get("flag")).equals(TransFlag.CG.getValue())) {
            upColinfo.setPayStatus(ColinfoPayStatus.C.getValue());
        } else if (String.valueOf(resultMap.get("flag")).equals(TransFlag.SB.getValue())) {
            upColinfo.setPayStatus(ColinfoPayStatus.D.getValue());
        } else {
            upColinfo.setPayStatus(ColinfoPayStatus.E.getValue());
        }
        int upCol = agentColinfoMapper.updateByPrimaryKeySelective(upColinfo);
        if (upPay != 1 || upCol != 1) {
            logger.info("updateByPaymentResult,upPay：{},upCol：{}", upPay, upCol);
            throw new Exception("更新返回结果异常");
        }
    }

    @Override
    public AgentColinfo selectByPrimaryKey(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }
        AgentColinfo agentColinfo = agentColinfoMapper.selectByPrimaryKey(id);
        return agentColinfo;
    }


    @Override
    public AgentColinfo selectByAgentId(String agentId) {
        if (StringUtils.isBlank(agentId)) {
            return null;
        }
        AgentColinfoExample agentColinfoExample = new AgentColinfoExample();
        AgentColinfoExample.Criteria criteria = agentColinfoExample.createCriteria();
        criteria.andAgentIdEqualTo(agentId);
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andCloReviewStatusEqualTo(AgStatus.Approved.status);
        List<AgentColinfo> agentColinfos = agentColinfoMapper.selectByExample(agentColinfoExample);
        if (null == agentColinfos) {
            return null;
        }
        if (agentColinfos.size() == 0) {
            return null;
        }
        return agentColinfos.get(0);
    }

    @Override
    public AgentColinfo selectByAgentIdAndBusId(String agentId, String agentbusId) {
        AgentColinfoRelExample agentColinfoRelExample = new AgentColinfoRelExample();
        AgentColinfoRelExample.Criteria criteria = agentColinfoRelExample.createCriteria();
        criteria.andAgentidEqualTo(agentId);
        criteria.andAgentbusidEqualTo(agentbusId);
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<AgentColinfoRel> agentColinfoRels = agentColinfoRelMapper.selectByExample(agentColinfoRelExample);
        if (null == agentColinfoRels) {
            return null;
        }
        if (agentColinfoRels.size() == 0) {
            return null;
        }
        AgentColinfoRel agentColinfoRel = agentColinfoRels.get(0);
        AgentColinfo agentColinfo = agentColinfoMapper.selectByPrimaryKey(agentColinfoRel.getAgentColinfoid());
        return agentColinfo;
    }

    @Override
    public AgentResult updateAgentColinfo(AgentColinfo agentColinfo) {
        if (1 != agentColinfoMapper.updateByPrimaryKeySelective(agentColinfo)) {
            return AgentResult.fail("更新失败！");
        }
        return AgentResult.ok("更新成功！");
    }

    @Override
    public AgentResult saveAgentColinfo(AgentColinfo agentColinfo) {
        if (1 != agentColinfoMapper.insertSelective(agentColinfo)) {
            return AgentResult.fail("新增失败！");
        }
        return AgentResult.ok("新增成功！");
    }

    /**
     * 查询代理商是否有多个收款账户
     * @return
     */
    @Override
    public AgentResult checkAgentColinfo() {
        List<String> haveColinfo = agentColinfoMapper.queryAgentHaveColinfo();
        if (haveColinfo.size() > 0) {
            StringBuffer sb = new StringBuffer();
            sb.append("代理商" + haveColinfo + "收款账户不唯一");
            AppConfig.sendEmails(sb.toString(), "代理商收款账户不唯一");
            logger.info("checkAgentColinfo: " + sb.toString());
        }
        return AgentResult.ok();
    }

    @Override
    public Attachment selectAttachment(String id) {
        if (null!=attachmentMapper.selectByPrimaryKey(id)){
            return attachmentMapper.selectByPrimaryKey(id);
        }
        return new Attachment();
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO updateAgentColinfoVoNow(List<AgentColinfoVo> colinfoVoList, Agent agent, String userId,String saveStatus) throws MessageException {
        try {
            if (agent == null) throw new MessageException("代理商信息不能为空");
            for (AgentColinfoVo agentColinfoVo : colinfoVoList) {
                if (null != agentColinfoVo.getCloTaxPoint() && agentColinfoVo.getCloTaxPoint().compareTo(new BigDecimal(1)) >= 0) {
                    throw new MessageException("税点不能大于1");
                }
                checkColInfoNow(agentColinfoVo);
                //收款账户对私时做校验
                String agentName = agent.getAgName();
                String agLegalName = agent.getAgLegal();
                String trueName = agentColinfoVo.getCloRealname();
                String certNo = agentColinfoVo.getAgLegalCernum();
                if (agentColinfoVo.getCloType().compareTo(new BigDecimal(2)) == 0) {
                    //对私时 收款账户名与法人姓名一致时 把法人身份证号拷贝到户主身份证号并进行认证
                    if (agLegalName.equals(trueName)) {
                        agentColinfoVo.setAgLegalCernum(agent.getAgLegalCernum());
                    } else {
                        if (StringUtils.isNotBlank(certNo) && !"1".equals(saveStatus) ) {
                            //校验收款账户身份认证
                            AgentResult result = livenessDetectionService.livenessDetection(trueName, certNo, userId);
                            if (!result.isOK()) {
                                throw new MessageException("收款账户身份认证失败");
                            }
                        } else if(!"1".equals(saveStatus)) {
                            throw new MessageException("请输入收款账户名相对应的户主证件号");
                        }
                    }
                }
                //对公时 判断收款账户名是否与代理商名称一致 不一致则抛异常提示信息
                if (agentColinfoVo.getCloType().compareTo(new BigDecimal(1)) == 0 && !"1".equals(saveStatus)) {
                    if (agentName.equals(trueName)) {
                        agentColinfoVo.setAgLegalCernum(agent.getAgLegalCernum());
                    } else if (!agentName.equals(trueName)) {
                        throw new MessageException("收款账户名与代理商名称不一致");
                    }
                }

                agentColinfoVo.setcUser(agent.getcUser());
                agentColinfoVo.setAgentId(agent.getId());
                if (org.apache.commons.lang.StringUtils.isEmpty(agentColinfoVo.getId())) {
                    //直接新曾
                    AgentColinfo result = agentColinfoInsert(agentColinfoVo, agentColinfoVo.getColinfoTableFile(),null);
                    logger.info("代理商收款账户添加:{}{}", "添加代理商收款账户成功", result.getId());
                } else {

                    AgentColinfo db_AgentColinfo = agentColinfoMapper.selectByPrimaryKey(agentColinfoVo.getId());
                    db_AgentColinfo.setAgentId(agent.getId());
                    db_AgentColinfo.setCloType(agentColinfoVo.getCloType());
                    db_AgentColinfo.setCloRealname(agentColinfoVo.getCloRealname());
                    db_AgentColinfo.setCloBank(agentColinfoVo.getCloBank());
                    db_AgentColinfo.setCloBankBranch(agentColinfoVo.getCloBankBranch());
                    db_AgentColinfo.setCloBankAccount(agentColinfoVo.getCloBankAccount());
                    db_AgentColinfo.setRemark(agentColinfoVo.getRemark());
                    db_AgentColinfo.setStatus(agentColinfoVo.getStatus());
                    db_AgentColinfo.setBranchLineNum(agentColinfoVo.getBranchLineNum());
                    db_AgentColinfo.setAllLineNum(agentColinfoVo.getAllLineNum());
                    db_AgentColinfo.setBankRegion(agentColinfoVo.getBankRegion());
                    db_AgentColinfo.setCloInvoice(agentColinfoVo.getCloInvoice());
                    db_AgentColinfo.setCloTaxPoint(agentColinfoVo.getCloTaxPoint());
                    db_AgentColinfo.setCloBankCode(agentColinfoVo.getCloBankCode());
                    db_AgentColinfo.setPayStatus(ColinfoPayStatus.A.getValue());
                    if (agLegalName.equals(trueName)) {
                        db_AgentColinfo.setAgLegalCernum(agent.getAgLegalCernum());
                    } else {
                        db_AgentColinfo.setAgLegalCernum(agentColinfoVo.getAgLegalCernum());
                    }
                    if (1 != agentColinfoMapper.updateByPrimaryKeySelective(db_AgentColinfo)) {
                        throw new MessageException("更新收款信息失败");
                    } else {
                        if (!agentDataHistoryService.saveDataHistory(db_AgentColinfo, db_AgentColinfo.getId(), DataHistoryType.GATHER.getValue(), userId, db_AgentColinfo.getVarsion()).isOK()) {
                            throw new MessageException("更新收款信息失败");
                        }
                    }
                    //删除老的附件
                    AttachmentRelExample example = new AttachmentRelExample();
                    example.or().andBusTypeEqualTo(AttachmentRelType.Proceeds.name()).andSrcIdEqualTo(db_AgentColinfo.getId()).andStatusEqualTo(Status.STATUS_1.status);
                    List<AttachmentRel> list = attachmentRelMapper.selectByExample(example);
                    for (AttachmentRel attachmentRel : list) {
                        attachmentRel.setStatus(Status.STATUS_0.status);
                        int i = attachmentRelMapper.updateByPrimaryKeySelective(attachmentRel);
                        if (1 != i) {
                            logger.info("修改收款信息附件关系失败{}", attachmentRel.getId());
                            throw new MessageException("更新收款信息信息失败");
                        }
                    }

                    //银行卡扫描件
                    boolean isHaveYHKSMJ = false;
                    //开户许可证
                    boolean isHaveKHXUZ = false;
                    //一般纳税人证明
                    boolean isHaveYBNSRZM = false;
                    //添加新的附件
                    List<String> fileIdList = agentColinfoVo.getColinfoTableFile();
                    if (fileIdList != null) {
                        for (String fileId : fileIdList) {

                            Attachment attachment = attachmentMapper.selectByPrimaryKey(fileId);
                            if (attachment != null) {
                                if (AttDataTypeStatic.YHKSMJ.code.equals(attachment.getAttDataType() + "")) {
                                    isHaveYHKSMJ = true;
                                }
                                if (AttDataTypeStatic.KHXUZ.code.equals(attachment.getAttDataType() + "")) {
                                    isHaveKHXUZ = true;
                                }
                                if (AttDataTypeStatic.YBNSRZM.code.equals(attachment.getAttDataType() + "")) {
                                    isHaveYBNSRZM = true;
                                }
                            }

                            AttachmentRel record = new AttachmentRel();
                            record.setAttId(fileId);
                            record.setSrcId(db_AgentColinfo.getId());
                            record.setcUser(db_AgentColinfo.getcUser());
                            record.setcTime(Calendar.getInstance().getTime());
                            record.setStatus(Status.STATUS_1.status);
                            record.setBusType(AttachmentRelType.Proceeds.name());
                            record.setId(idService.genId(TabId.a_attachment_rel));
                            int i = attachmentRelMapper.insertSelective(record);
                            if (1 != i) {
                                logger.info("收款信息附件关系失败");
                                throw new MessageException("更新收款信息失败");
                            }
                        }
                    }
                    if (agentColinfoVo.getCloType().compareTo(new BigDecimal("2")) == 0) {//对私
                        if (!isHaveYHKSMJ  && !"1".equals(saveStatus)) {
                            throw new MessageException("请添加银行卡扫描件");
                        }
                    }
                    if (agentColinfoVo.getCloType().compareTo(new BigDecimal("1")) == 0) {//对公
                        if (!isHaveKHXUZ  && !"1".equals(saveStatus)) {
                            throw new MessageException("请添加开户许可证");
                        }
                    }
                    //对公并且税点等于0.06一般纳税人证明必填
                    if (agentColinfoVo.getCloType().compareTo(new BigDecimal("1")) == 0 && agentColinfoVo.getCloTaxPoint().compareTo(new BigDecimal("0.06")) == 0) {
                        if (!isHaveYBNSRZM  && !"1".equals(saveStatus)) {
                            throw new MessageException("请添加一般纳税人证明");
                        }
                    }
                }
            }
            return ResultVO.success(null);
        } catch (ProcessException e) {
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        } catch (MessageException e) {
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MessageException(e.getMessage());
        }
    }

    private  AgentResult checkColInfoNow(AgentColinfo agentColinfo) throws ProcessException {
        try {
            if (agentColinfo.isImport()) return AgentResult.ok();
            if (agentColinfo.getCloType().compareTo(new BigDecimal(2)) == 0) { //对私
                //税点检查
                if (agentColinfo.getCloTaxPoint() == null || !"0.08".equals(agentColinfo.getCloTaxPoint().toString())) {
                    throw new ProcessException("对私户进行打款，那么扣税点在代理商填写时默认为0.08且不可修改");
                }
                //是否开票检查
                if (agentColinfo.getCloInvoice().compareTo(new BigDecimal(0)) != 0) { //对私
                    throw new ProcessException("对私户进行打款，那么是否开票默认为否且不可修改");
                }
            } else if (agentColinfo.getCloType().compareTo(new BigDecimal(1)) == 0) {//对公
                //是否开票检查
                if (agentColinfo.getCloInvoice().compareTo(new BigDecimal(1)) == 0) { //开票
                    //税点检查
                    if (!"0.06".equals(agentColinfo.getCloTaxPoint().toString()) && !"0.03".equals(agentColinfo.getCloTaxPoint().toString())) { //对私
                        throw new ProcessException("对公户进行打款，且代理商是否开票为是，那么扣税点在代理商填写时只能选择0.06或0.03");
                    }
                } else if (agentColinfo.getCloInvoice().compareTo(new BigDecimal(0)) == 0) { //不开票
//                        对公   是否开发票 则对应为是  不可修改
                    throw new ProcessException("对公户进行打款，那么是否开票默认为是且不可修改");
                }
            }
        } catch (ProcessException e) {
            e.printStackTrace();
            throw new ProcessException(e.getMsg());
        }
        return AgentResult.ok();
    }


    @Override
    public FastMap notifyCardChange(String type,String ag,String userId) throws Exception {
        logger.info("结算卡接口调用:{},{},{}",type,ag,userId);
        if(StringUtils.isBlank(type))return FastMap.fastFailMap();
        if(StringUtils.isBlank(userId))return FastMap.fastFailMap();
        //全量结算卡通知
        if("ALL".equals(type)){
            List<AgentColinfo> list = agentColinfoMapper.selectColInfoByAgent(null, BusStatus.getAvbList());
            for (AgentColinfo agentColinfo : list) {
                try {
                    if(StringUtils.isNotBlank(agentColinfo.getBankRegion())
                            && StringUtils.isNotBlank(agentColinfo.getBranchLineNum())
                            && StringUtils.isNotBlank(agentColinfo.getAccountId())
                            && StringUtils.isNotBlank(agentColinfo.getAccountName())
                            && null!=agentColinfo.getCloInvoice()
                            && null!=agentColinfo.getCloTaxPoint()) {
                        Agent agent = agentMapper.selectByPrimaryKey(agentColinfo.getAgentId());
                        agentKafkaService.sendPayMentMessage(agentColinfo.getAgentId(),
                                agent.getAgName(),
                                agentColinfo.getId(),
                                agentColinfo.getId(),
                                KafkaMessageType.CARD,
                                KafkaMessageTopic.CardChange.code,
                                JSONObject.toJSONString(agentColinfo)
                        );
                    }else{
                        logger.info("kafka接口调用失败 全量结算卡通知 未通知数据不全 {}",agentColinfo.getId());
                    }
                } catch (Exception e) {
                    logger.info("kafka接口调用失败 全量结算卡通知 {}",agentColinfo.getId());
                    e.printStackTrace();
                }
            }
        ///单个结算卡通知
        }else if("NOTALL".equals(type) && StringUtils.isNotBlank(ag)){
            List<AgentColinfo> list = agentColinfoMapper.selectColInfoByAgent(ag, BusStatus.getAvbList());
            for (AgentColinfo agentColinfo : list) {
                try {
                    if(StringUtils.isNotBlank(agentColinfo.getBankRegion())
                            && StringUtils.isNotBlank(agentColinfo.getBranchLineNum())
                            && StringUtils.isNotBlank(agentColinfo.getAccountId())
                            && StringUtils.isNotBlank(agentColinfo.getAccountName())
                            && null!=agentColinfo.getCloInvoice()
                            && null!=agentColinfo.getCloTaxPoint()) {
                        Agent agent = agentMapper.selectByPrimaryKey(agentColinfo.getAgentId());
                        agentKafkaService.sendPayMentMessage(agentColinfo.getAgentId(),
                                agent.getAgName(),
                                agentColinfo.getId(),
                                agentColinfo.getId(),
                                KafkaMessageType.CARD,
                                KafkaMessageTopic.CardChange.code,
                                JSONObject.toJSONString(agentColinfo)
                        );
                    }else{
                        logger.info("kafka接口调用失败 全量结算卡通知 未通知数据不全 {}",agentColinfo.getId());
                    }
                } catch (Exception e) {
                    logger.info("kafka接口调用失败 单个结算卡通知 {}",agentColinfo.getId());
                    e.printStackTrace();
                }
            }
        }
        return FastMap.fastSuccessMap();
    }

    @Override
    public AgentResult amendAgentColinfo(Map map) {
        if(null==map && map.size()==0){
            logger.info("请传入必要参数");
            return AgentResult.fail("请传入必要参数");
        }
        if (StringUtils.isBlank(String.valueOf(map.get("agent_id")))){
            logger.info("请传入代理商唯一编码");
            return AgentResult.fail("请传入代理商唯一编码");
        }
        if (null==map.get("pay_status")){
            logger.info("请传入出款状态");
            return AgentResult.fail("请传入出款状态");
        }
        AgentColinfoExample agentColinfoExample = new AgentColinfoExample();
        AgentColinfoExample.Criteria agent_id = agentColinfoExample.createCriteria().andAgentIdEqualTo(String.valueOf(map.get("agent_id")))
                .andStatusEqualTo(Status.STATUS_1.status)
                .andCloReviewStatusEqualTo(AgStatus.Approved.status);
        List<AgentColinfo> agentColinfoList = agentColinfoMapper.selectByExample(agentColinfoExample);
        if(null==agentColinfoList || agentColinfoList.size()==0){
            logger.info("没有查询到结算卡信息");
            return AgentResult.fail("没有查询到结算卡信息");
        }
        AgentColinfo agentColinfo = agentColinfoList.get(0);
      if(null!=map.get("version")){
          if(agentColinfo.getVarsion().compareTo((BigDecimal)map.get("version"))==0){
              //版本一致则可以进行修改
              AgentResult result = updateAccountCard(map, agentColinfo);
              if(result.isOK()){
                  logger.info("版本一致修改成功");
                  return AgentResult.ok("修改成功");
              }else {
                  logger.info("版本一致修改失败");
                  return AgentResult.fail("修改失败");
              }
          }else {
              //版本不一致 需判断银行卡等信息是否一致
              AgentResult agentResult = update(map, agentColinfo);
              if(agentResult.isOK()){
                  logger.info("修改成功");
                  return AgentResult.ok("修改成功");
              }else {
                  logger.info("修改失败");
                  return AgentResult.fail("修改失败");
              }
          }
      }else{
          logger.info("没有版本号进行修改");
          AgentResult agentResult = update(map, agentColinfo);
          if(agentResult.isOK()){
              logger.info("修改成功");
              return AgentResult.ok("修改成功");
          }else {
              logger.info("修改失败");
              return AgentResult.fail("修改失败");
          }
      }
    }

    AgentResult update(Map map,AgentColinfo agentColinfo){
        if(StringUtils.isNotBlank(String.valueOf(map.get("clo_realname")))
                && StringUtils.isNotBlank(String.valueOf(map.get("clo_bank_account")))
                && StringUtils.isNotBlank(String.valueOf(map.get("branch_line_num")))
                && StringUtils.isNotBlank(String.valueOf(map.get("clo_bank_branch")))
                &&
                String.valueOf(map.get("clo_realname")).equals(agentColinfo.getCloRealname())
                &&
                String.valueOf(map.get("clo_bank_account")).equals(agentColinfo.getCloBankAccount())
                &&
                String.valueOf(map.get("branch_line_num")).equals(agentColinfo.getBranchLineNum())
                &&
                String.valueOf(map.get("clo_bank_branch")).equals(agentColinfo.getCloBankBranch())
                )
        {
            AgentResult result = updateAccountCard(map, agentColinfo);
            if(result.isOK()){
                logger.info("版本一致修改成功");
                return AgentResult.ok("修改成功");
            }else {
                logger.info("版本一致修改失败");
                return AgentResult.fail("修改失败");
            }
        }
        else {
            AgentColinfo agentColinfo_afterChange = selectByAgentId(agentColinfo.getAgentId());
            try {
                logger.info("===================开始执行kafka消息分发");
                agentKafkaService.sendPayMentMessage(agentColinfo.getAgentId(),
                        agentColinfo.getId(),
                        agentColinfo.getId(), "",
                        KafkaMessageType.CARD,
                        KafkaMessageTopic.CardChange.code,
                        JSONObject.toJSONString(agentColinfo_afterChange)
                );
                logger.info("===================结束kafka消息分发");
            } catch (Exception e) {
                logger.info("kafka接口调用失败,AG码", agentColinfo.getAgentId());
                e.printStackTrace();
            }
        }
        return AgentResult.ok("修改成功");
    }

    private AgentResult updateAccountCard(Map map,AgentColinfo agentColinfo){
        if(StringUtils.isNotBlank(String.valueOf(map.get("clo_realname")))){//收款账户名
            agentColinfo.setCloRealname(String.valueOf(map.get("clo_realname")));
        }
        if(StringUtils.isNotBlank(String.valueOf(map.get("clo_bank_account")))){//收款账号
            agentColinfo.setCloBankAccount(String.valueOf(map.get("clo_bank_account")));
        }
        if(StringUtils.isNotBlank(String.valueOf(map.get("branch_line_num")))){//支行联行号
            agentColinfo.setBranchLineNum(String.valueOf(map.get("branch_line_num")));
        }
        if(StringUtils.isNotBlank(String.valueOf(map.get("clo_bank_branch")))){//支行名
            agentColinfo.setCloBankBranch(String.valueOf(map.get("clo_bank_branch")));
        }
        if(null!=map.get("pay_status")){//出款状态
            BigDecimal payStatus = (BigDecimal) map.get("pay_status");
            if(payStatus.compareTo(ColinfoPayStatus.D.code)==0){//验证失败
                agentColinfo.setPayStatus(ColinfoPayStatus.D.code);
                agentColinfo.setAmendStatus(AmendStatus.DXG.status);
            }else if(payStatus.compareTo(ColinfoPayStatus.C.code)==0){
                agentColinfo.setPayStatus(ColinfoPayStatus.C.code);
            }
        }
        if(StringUtils.isNotBlank(String.valueOf(map.get("unusual_explain")))){//失败原因
            agentColinfo.setUnusualExplain(String.valueOf(map.get("unusual_explain")));
        }

        if(1==agentColinfoMapper.updateByPrimaryKeySelective(agentColinfo)){
            logger.info("修改成功");
            return AgentResult.ok("修改成功");
        }
        logger.info("代理商{}修改失败",agentColinfo.getAgentId());
        return AgentResult.fail("代理商["+agentColinfo.getAgentId()+"]修改失败");
    }
}
