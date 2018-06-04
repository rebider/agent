package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.dao.agent.AgentContractMapper;
import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentContractVo;
import com.ryx.credit.pojo.admin.vo.CapitalVo;
import com.ryx.credit.service.agent.AgentContractService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import org.apache.commons.lang.StringUtils;
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
 * Created by cx on 2018/5/22.
 */
@Service("agentContractService")
public class AgentContractServiceImpl implements AgentContractService {

    private static Logger logger = LoggerFactory.getLogger(AgentContractServiceImpl.class);

    @Autowired
    private IdService idService;

    @Autowired
    private AgentContractMapper agentContractMapper;

    @Autowired
    private AttachmentRelMapper attachmentRelMapper;

    @Autowired
    private DictOptionsService dictOptionsService;


    /**
     * 获取合同类型
     * @return
     */
    public List<Dict> queryContractType(){
        return dictOptionsService.dictList(DictGroup.AGENT.name(),DictGroup.CONTRACT_TYPE.name());
    }



    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentContract insertAgentContract(AgentContract contract, List<String> attr) throws ProcessException {
        if(contract==null){
            logger.info("代理商合同添加:{}","合同信息为空");
            throw new ProcessException("合同信息为空");
        }
        if(StringUtils.isEmpty(contract.getAgentId())){
            logger.info("代理商合同添加:{}","代理商id不能为空");
            throw new ProcessException("合同代理商信息不能为空");
        }
        if(StringUtils.isEmpty(contract.getcUser())){
            logger.info("代理商合同添加:{}","操作用户不能为空");
            throw new ProcessException("操作用户不能为空");
        }
        if(StringUtils.isEmpty(contract.getContNum())){
            logger.info("代理商合同添加:{}","合同编号不能为空");
            throw new ProcessException("合同编号不能为空");
        }
        if(null == contract.getContType()){
            logger.info("代理商合同添加:{}","合同类型不能为空");
            throw new ProcessException("合同类型不能为空");
        }
        if(null == contract.getContDate()){
            logger.info("代理商合同添加:{}","签约时间不能为空");
            throw new ProcessException("签约时间不能为空");
        }
        if(null == contract.getContEndDate()){
            logger.info("代理商合同添加:{}","签约到期时间不能为空");
            throw new ProcessException("签约到期时间不能为空");
        }
        if(StringUtils.isEmpty(contract.getcUser())){
            logger.info("代理商合同添加:{}","操作人不能为空");
            throw new ProcessException("操作人不能为空");
        }
        if(StringUtils.isEmpty(contract.getcUser())){
            logger.info("代理商合同添加:{}","操作人不能为空");
            throw new ProcessException("操作人不能为空");
        }
        Date date = Calendar.getInstance().getTime();
        contract.setStatus(Status.STATUS_1.status);
        contract.setVersion(Status.STATUS_1.status);
        contract.setCloReviewStatus(AgStatus.Create.status);
        contract.setcTime(date);
        contract.setcUtime(date);
        contract.setId(idService.genId(TabId.a_agent_contract));
        if(1==agentContractMapper.insertSelective(contract)){
            if(attr!=null) {
                for (String s : attr) {
                    if (StringUtils.isEmpty(s)) continue;
                    AttachmentRel record = new AttachmentRel();
                    record.setAttId(s);
                    record.setSrcId(contract.getId());
                    record.setcUser(contract.getcUser());
                    record.setcTime(contract.getcTime());
                    record.setStatus(Status.STATUS_1.status);
                    record.setBusType(AttachmentRelType.Contract.name());
                    record.setId(idService.genId(TabId.a_attachment_rel));
                    if (1 != attachmentRelMapper.insertSelective(record)) {
                        logger.info("代理商合同添加:{}", "添加合同附件关系失败");
                        throw new ProcessException("添加合同附件关系失败");
                    }
                }
            }
            logger.info("代理商合同添加:成功");
            return contract;
        }
        logger.info("代理商合同添加:{}","添加代理商合同失败");
        throw new ProcessException("添加代理商合同失败");
    }


    @Override
    public int removeAgentContract(String id) {
        AgentContract contract = agentContractMapper.selectByPrimaryKey(id);
        if(contract!=null){
            contract.setStatus(Status.STATUS_0.status);
           return agentContractMapper.updateByPrimaryKeySelective(contract);
        }
        return 0;
    }



    @Override
    public List<AgentContract> queryAgentContract(String agentId, String contractId, BigDecimal approveStatus) {
        AgentContractExample example  = new AgentContractExample();
        AgentContractExample.Criteria c = example.or().andStatusEqualTo(Status.STATUS_1.status);
        if(StringUtils.isNotEmpty(agentId)){
            c.andAgentIdEqualTo(agentId);
        }
        if(StringUtils.isNotEmpty(contractId)){
            c.andIdEqualTo(contractId);
        }
        if(null!=approveStatus){
            c.andCloReviewStatusEqualTo(approveStatus);
        }
        return agentContractMapper.selectByExample(example);
    }


    @Override
    public int update(AgentContract a) {
        return agentContractMapper.updateByPrimaryKeySelective(a);
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public ResultVO updateAgentContractVo(List<AgentContractVo> volist, Agent agent) {
        try {
            if(agent==null)throw new ProcessException("代理商信息不能为空");
            for (AgentContractVo agentContractVo : volist) {
                agentContractVo.setcUser(agent.getcUser());
                agentContractVo.setAgentId(agent.getId());
                if(StringUtils.isEmpty(agentContractVo.getId())) {
                    //直接新曾

                    AgentContract result =   insertAgentContract(agentContractVo, agentContractVo.getContractTableFile());
                    logger.info("代理商合同添加:{}{}","添加代理商合同成功",result.getId());
                }else{

                    AgentContract db_AgentContract = agentContractMapper.selectByPrimaryKey(agentContractVo.getId());

                    db_AgentContract.setAgentId(agent.getId());
                    db_AgentContract.setContNum(agentContractVo.getContNum());
                    db_AgentContract.setContType(agentContractVo.getContType());
                    db_AgentContract.setContDate(agentContractVo.getContDate());
                    db_AgentContract.setContEndDate(agentContractVo.getContEndDate());
                    db_AgentContract.setRemark(agentContractVo.getRemark());
                    db_AgentContract.setcUser(agentContractVo.getcUser());
                    if(1!=agentContractMapper.updateByPrimaryKeySelective(db_AgentContract)){
                        throw new ProcessException("更新收款信息失败");
                    }

                    //删除老的附件
                    AttachmentRelExample example = new AttachmentRelExample();
                    example.or().andBusTypeEqualTo(AttachmentRelType.Contract.name()).andSrcIdEqualTo(db_AgentContract.getId()).andStatusEqualTo(Status.STATUS_1.status);
                    List<AttachmentRel> list = attachmentRelMapper.selectByExample(example);
                    for (AttachmentRel attachmentRel : list) {
                        attachmentRel.setStatus(Status.STATUS_0.status);
                        int i = attachmentRelMapper.updateByPrimaryKeySelective(attachmentRel);
                        if (1 != i) {
                            logger.info("修改合同附件关系失败{}",attachmentRel.getId());
                            throw new ProcessException("更新合同信息失败");
                        }
                    }

                    //添加新的附件
                    List<String> fileIdList = agentContractVo.getContractTableFile();
                    if(fileIdList!=null) {
                        for (String fileId : fileIdList) {
                            AttachmentRel record = new AttachmentRel();
                            record.setAttId(fileId);
                            record.setSrcId(db_AgentContract.getId());
                            record.setcUser(db_AgentContract.getcUser());
                            record.setcTime(Calendar.getInstance().getTime());
                            record.setStatus(Status.STATUS_1.status);
                            record.setBusType(AttachmentRelType.Contract.name());
                            record.setId(idService.genId(TabId.a_attachment_rel));
                            int i = attachmentRelMapper.insertSelective(record);
                            if (1 != i) {
                                logger.info("合同附件关系失败");
                                throw new ProcessException("更新合同失败");
                            }
                        }
                    }

                }
            }
            return ResultVO.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
