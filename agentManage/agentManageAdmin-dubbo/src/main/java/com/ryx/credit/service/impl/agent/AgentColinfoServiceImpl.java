package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.AttachmentRelType;
import com.ryx.credit.common.enumc.DataHistoryType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentColinfoMapper;
import com.ryx.credit.dao.agent.AgentColinfoRelMapper;
import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentColinfoVo;
import com.ryx.credit.pojo.admin.vo.AgentContractVo;
import com.ryx.credit.service.agent.AgentColinfoService;
import com.ryx.credit.service.agent.AgentDataHistoryService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    /**
     * 代理商入网添加代理商交款项
     * @param ac
     * @param att
     * @return
     * @throws ProcessException
     */
    @Override
    public AgentColinfo agentColinfoInsert(AgentColinfo ac, List<String> att)throws ProcessException{
        if(StringUtils.isEmpty(ac.getcUser())){
            throw new ProcessException("操作人不能为空");
        }
        if(StringUtils.isEmpty(ac.getAgentId())){
            throw new ProcessException("代理商ID不能为空");
        }
//        if(StringUtils.isEmpty(ac.getCloBank())){
//            throw new ProcessException("收款开户行不能为空");
//        }
//        if(StringUtils.isEmpty(ac.getCloRealname())){
//            throw new ProcessException("收款账户名不能为空");
//        }
//        if(StringUtils.isEmpty(ac.getCloBankAccount())){
//            throw new ProcessException("收款账号不能为空");
//        }
//        if(StringUtils.isEmpty(ac.getCloType())){
//            throw new ProcessException("收款账户类型不能为空");
//        }
        Date d = Calendar.getInstance().getTime();
        ac.setcTime(d);
        ac.setcUtime(d);
        ac.setStatus(Status.STATUS_1.status);
        ac.setVarsion(Status.STATUS_1.status);
        ac.setId(idService.genId(TabId.a_agent_colinfo));
        if(att!=null) {
            for (String s : att) {
                if (org.apache.commons.lang.StringUtils.isEmpty(s)) continue;
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
        if(1!=agentColinfoMapper.insertSelective(ac)){
            logger.info("收款账号添加:{}", "收款账号添加失败");
            throw new ProcessException("收款账号添加失败");
        }
        return ac;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    public AgentResult saveAgentColinfoRel(AgentColinfoRel agentColinfoRel,String cUser)throws Exception{

        AgentResult result = new AgentResult(500,"参数错误","");
        if(agentColinfoRel==null){
            return result;
        }
        if(StringUtils.isBlank(agentColinfoRel.getAgentid()) || StringUtils.isBlank(agentColinfoRel.getBusPlatform())
            ||StringUtils.isBlank(agentColinfoRel.getAgentbusid()) || StringUtils.isBlank(agentColinfoRel.getAgentColinfoid())){
            return result;
        }

        AgentColinfoRelExample example = new AgentColinfoRelExample();
        AgentColinfoRelExample.Criteria criteria = example.createCriteria();
        criteria.andAgentbusidEqualTo(agentColinfoRel.getAgentbusid());
        criteria.andAgentidEqualTo(agentColinfoRel.getAgentid());
        criteria.andBusPlatformEqualTo(agentColinfoRel.getBusPlatform());
        List<AgentColinfoRel> agentColinfoRels = agentColinfoRelMapper.selectByExample(example);
        if(null!=agentColinfoRels && agentColinfoRels.size()>=1){
            int deleteResult = agentColinfoRelMapper.deleteByExample(example);
            if(deleteResult!=1){
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
        if(insert==1){
            return AgentResult.ok();
        }
        logger.info("saveAgentColinfoRel保存收款关系失败");
        return new AgentResult(500, "系统异常", "");
    }


    public List<AgentColinfo> queryAgentColinfoService(String agentId,String colId,BigDecimal appStatus){
        AgentColinfoExample example = new AgentColinfoExample();
        AgentColinfoExample.Criteria c = example.or().andStatusEqualTo(Status.STATUS_1.status);
        if(org.apache.commons.lang.StringUtils.isNotEmpty(agentId)){
            c.andAgentIdEqualTo(agentId);
        }
        if(org.apache.commons.lang.StringUtils.isNotEmpty(colId)){
            c.andIdEqualTo(colId);
        }
        if(appStatus!=null){
            c.andCloReviewStatusEqualTo(appStatus);
        }
        return agentColinfoMapper.selectByExample(example);
    }


    public int update(AgentColinfo a){
        return agentColinfoMapper.updateByPrimaryKeySelective(a);
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public ResultVO updateAgentColinfoVo(List<AgentColinfoVo> colinfoVoList, Agent agent) {
        try {
            if(agent==null)throw new ProcessException("代理商信息不能为空");
            for (AgentColinfoVo agentColinfoVo : colinfoVoList) {
                agentColinfoVo.setcUser(agent.getcUser());
                agentColinfoVo.setAgentId(agent.getId());
                if(org.apache.commons.lang.StringUtils.isEmpty(agentColinfoVo.getId())) {
                    //直接新曾
                    AgentColinfo result =   agentColinfoInsert(agentColinfoVo, agentColinfoVo.getColinfoTableFile());
                    logger.info("代理商收款账户添加:{}{}","添加代理商收款账户成功",result.getId());
                }else{

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
                    if(1!=agentColinfoMapper.updateByPrimaryKeySelective(db_AgentColinfo)){
                        throw new ProcessException("更新收款信息失败");
                    }
                    //删除老的附件
                    AttachmentRelExample example = new AttachmentRelExample();
                    example.or().andBusTypeEqualTo(AttachmentRelType.Proceeds.name()).andSrcIdEqualTo(db_AgentColinfo.getId()).andStatusEqualTo(Status.STATUS_1.status);
                    List<AttachmentRel> list = attachmentRelMapper.selectByExample(example);
                    for (AttachmentRel attachmentRel : list) {
                        attachmentRel.setStatus(Status.STATUS_0.status);
                        int i = attachmentRelMapper.updateByPrimaryKeySelective(attachmentRel);
                        if (1 != i) {
                            logger.info("修改收款信息附件关系失败{}",attachmentRel.getId());
                            throw new ProcessException("更新收款信息信息失败");
                        }
                    }

                    //添加新的附件
                    List<String> fileIdList = agentColinfoVo.getColinfoTableFile();
                    if(fileIdList!=null) {
                        for (String fileId : fileIdList) {
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

                }
                agentDataHistoryService.saveDataHistory(agentColinfoVo, DataHistoryType.GATHER.getValue());
            }

            return ResultVO.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
