package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentQuitService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Author RYX
 * @Date 2019/1/26
 * @Desc 代理商退出
 */
@Service("agentQuitService")
public class AgentQuitServiceImpl extends AgentMergeServiceImpl implements AgentQuitService {

    private static Logger logger = LoggerFactory.getLogger(AgentQuitServiceImpl.class);
    @Autowired
    private AgentQuitMapper agentQuitMapper;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private PlatFormMapper platFormMapper;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private CapitalMapper capitalMapper;
    @Autowired
    private AttachmentRelMapper attachmentRelMapper;


    /**
     * 退出列表
     * @param agentQuit
     * @param page
     * @param dataRole
     * @param userId
     * @return
     */
    @Override
    public PageInfo queryAgentQuitList(AgentQuit agentQuit, Page page, String dataRole, Long userId) {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("status", Status.STATUS_1.status);
        if (StringUtils.isNotBlank(agentQuit.getId())) {
            reqMap.put("id", agentQuit.getId());
        }
        if (StringUtils.isNotBlank(agentQuit.getAgentId())) {
            reqMap.put("agentId", agentQuit.getAgentId());
        }
        if (StringUtils.isNotBlank(agentQuit.getAgentName())) {
            reqMap.put("agentName", agentQuit.getAgentName());
        }
        if (null != agentQuit.getCloReviewStatus()) {
            reqMap.put("cloReviewStatus", agentQuit.getCloReviewStatus());
        }
//        if(StringUtils.isBlank(dataRole)){
//            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
//            if(orgCodeRes == null && orgCodeRes.size() != 1){
//                return null;
//            }
//            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
//            reqMap.put("orgId", String.valueOf(stringObjectMap.get("ORGID")));
//        }
        List<Map<String, Object>> agentMergeList = agentQuitMapper.queryAgentQuitList(reqMap, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(agentMergeList);
        pageInfo.setTotal(agentQuitMapper.queryAgentQuitCount(reqMap));
        return pageInfo;
    }

    /**
     * 保存
     * @param agentQuit
     * @param agentQuitFiles
     * @param cUser
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult saveAgentQuit(AgentQuit agentQuit, String[] agentQuitFiles, String cUser)throws Exception {

        if(StringUtils.isBlank(agentQuit.getAgentId())){
            throw new MessageException("代理商ID为空！");
        }
        if(StringUtils.isBlank(agentQuit.getQuitPlatform())){
            throw new MessageException("代理商退出平台为空！");
        }
        Agent agent = agentMapper.selectByPrimaryKey(agentQuit.getAgentId());
        if(null==agent){
            throw new MessageException("代理商不存在！");
        }
        String quitId = idService.genId(TabId.A_AGENT_QUIT);
        agentQuit.setId(quitId);
        agentQuit.setQuitBusId(quitPlatformIds(agentQuit.getQuitPlatform(),agentQuit.getAgentId()));
        agentQuit.setContractStatus(Status.STATUS_0.status);
        agentQuit.setRefundAmtStatus(Status.STATUS_0.status);
        agentQuit.setAppRefund(PlatformStatus.NODISPOSE.getValue());
        agentQuit.setCloReviewStatus(AgStatus.Create.getValue());
        agentQuit.setAgentName(agent.getAgName());
        agentQuit.setAgentOweTicket(getSubAgentOweTicket(agent.getId()));
        agentQuit.setSuppTicket(getSubAgentOweTicket(agent.getId()));
        BigDecimal profitDebt = profitDebt(agent.getId());
        BigDecimal orderDebt = getOrderDebt(agent.getId());
        BigDecimal capitalDebt = getCapitalDebt(agent.getId());
        agentQuit.setProfitDebt(profitDebt);
        agentQuit.setOrderDebt(orderDebt);
        agentQuit.setCapitalDebt(capitalDebt);
        agentQuit.setAgentDept(getSubAgentDebt(agent.getId()));

        BigDecimal capitalSumAmt = getCapitalSumAmt(agent.getId());
        BigDecimal suppDept = capitalSumAmt.subtract(profitDebt).subtract(orderDebt).subtract(capitalDebt);
        String suppDeptStr = String.valueOf(suppDept);
        if(suppDeptStr.contains("-")){
            agentQuit.setSuppType(SuppType.D.getValue());
            String substring = suppDeptStr.substring(0, suppDeptStr.length() - 1);
            agentQuit.setSuppDept(new BigDecimal(substring));
        }else{
            agentQuit.setSuppType(SuppType.G.getValue());
            agentQuit.setSuppDept(suppDept);
        }
        agentQuit.setcTime(new Date());
        agentQuit.setuTime(new Date());
        agentQuit.setcUser(cUser);
        agentQuit.setuUser(cUser);
        agentQuit.setStatus(Status.STATUS_1.status);
        agentQuit.setVersion(BigDecimal.ONE);
        int i = agentQuitMapper.insertSelective(agentQuit);
        if(i!=1){
            throw new MessageException("保存退出申请失败！");
        }

        //添加新的附件
        if (agentQuitFiles != null && agentQuitFiles.length!=0) {
            for(int j=0;j<agentQuitFiles.length;j++){
                AttachmentRel record = new AttachmentRel();
                record.setAttId(agentQuitFiles[j]);
                record.setSrcId(quitId);
                record.setcUser(cUser);
                record.setcTime(Calendar.getInstance().getTime());
                record.setStatus(Status.STATUS_1.status);
                record.setBusType(AttachmentRelType.agentQuit.name());
                record.setId(idService.genId(TabId.a_attachment_rel));
                int f = attachmentRelMapper.insertSelective(record);
                if (1 != f) {
                    logger.info("代理商退出保存附件关系失败");
                    throw new ProcessException("保存附件失败");
                }
            }
        }
        return AgentResult.ok();
    }

    /**
     * 查询所有业务id
     * @param quitPlatform
     * @param agentId
     * @return
     */
    public String quitPlatformIds(String quitPlatform,String agentId){
        PlatFormExample platFormExample = new PlatFormExample();
        PlatFormExample.Criteria criteria = platFormExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        if(quitPlatform.equals(QuitPlatform.POS.getValue())){
            List<String> platformTypeList = new ArrayList<>();
            platformTypeList.add(PlatformType.POS.getValue());
            platformTypeList.add(PlatformType.MPOS.getValue());
            criteria.andPlatformTypeIn(platformTypeList);
        }else if(quitPlatform.equals(QuitPlatform.MPOS.getValue())){
            criteria.andPlatformTypeEqualTo(PlatformType.MPOS.getValue());
        }else if(quitPlatform.equals(QuitPlatform.POSANDMPOS.getValue())){
            List<String> platformTypeList = new ArrayList<>();
            platformTypeList.add(PlatformType.POS.getValue());
            platformTypeList.add(PlatformType.MPOS.getValue());
            platformTypeList.add(PlatformType.ZPOS.getValue());
            criteria.andPlatformTypeIn(platformTypeList);
        }
        List<PlatForm> platForms = platFormMapper.selectByExample(platFormExample);

        List<String> busPlatformList = new ArrayList<>();
        for (PlatForm platForm : platForms) {
            busPlatformList.add(platForm.getPlatformNum());
        }
        AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
        AgentBusInfoExample.Criteria agentBusInfoCriteria = agentBusInfoExample.createCriteria();
        agentBusInfoCriteria.andStatusEqualTo(Status.STATUS_1.status);
        List<BigDecimal> busStatusList = new ArrayList<>();
        busStatusList.add(BusinessStatus.Enabled.status);
        busStatusList.add(BusinessStatus.inactive.status);
        agentBusInfoCriteria.andBusStatusIn(busStatusList);
        agentBusInfoCriteria.andAgentIdEqualTo(agentId);
        agentBusInfoCriteria.andBusPlatformIn(busPlatformList);
        List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
        String busIds = "";
        for (AgentBusInfo agentBusInfo : agentBusInfos) {
            busIds += agentBusInfo.getId()+",";
        }
        if(StringUtils.isNotBlank(busIds)){
            busIds.substring(0,busIds.length()-1);
        }
        return busIds;
    }

    /**
     * 获取缴纳款总金额
     * @param agentId
     * @return
     */
    public BigDecimal getCapitalSumAmt(String agentId){
        CapitalExample capitalExample = new CapitalExample();
        CapitalExample.Criteria criteria = capitalExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andCPayTypeEqualTo(PayType.YHHK.getValue());
        criteria.andCAgentIdEqualTo(agentId);
        List<Capital> capitals = capitalMapper.selectByExample(capitalExample);
        BigDecimal sumAmt = new BigDecimal(0);
        for (Capital capital : capitals) {
            sumAmt = sumAmt.add(capital.getcAmount());
        }
        return sumAmt;
    }
}
