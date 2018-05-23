package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.dao.agent.AgentContractMapper;
import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.pojo.admin.agent.AgentContract;
import com.ryx.credit.pojo.admin.agent.AttachmentRel;
import com.ryx.credit.pojo.admin.agent.Dict;
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
            for (String s : attr) {
                if(StringUtils.isEmpty(s))break;
                AttachmentRel record  = new AttachmentRel();
                record.setAttId(s);
                record.setSrcId(contract.getId());
                record.setcUser(contract.getcUser());
                record.setcTime(contract.getcTime());
                record.setStatus(Status.STATUS_1.status);
                record.setBusType(AttachmentRelType.Contract.name());
                record.setId(idService.genId(TabId.a_attachment_rel));
                if(1!=attachmentRelMapper.insertSelective(record)){
                    logger.info("代理商合同添加:{}","添加合同附件关系失败");
                    throw new ProcessException("添加合同附件关系失败");
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
}
