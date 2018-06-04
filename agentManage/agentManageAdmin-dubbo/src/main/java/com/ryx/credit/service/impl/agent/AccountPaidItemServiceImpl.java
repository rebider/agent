package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.AttachmentRelType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.dao.agent.CapitalMapper;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AttachmentRel;
import com.ryx.credit.pojo.admin.agent.AttachmentRelExample;
import com.ryx.credit.pojo.admin.agent.Capital;
import com.ryx.credit.pojo.admin.vo.CapitalVo;
import com.ryx.credit.service.agent.AccountPaidItemService;
import com.ryx.credit.service.dict.IdService;
import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.data.Stat;
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
 * 缴纳款项服务层
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/5/23 14:20
 */
@Service("accountPaidItemService")
public class AccountPaidItemServiceImpl implements AccountPaidItemService {

    private static Logger log = LoggerFactory.getLogger(AccountPaidItemServiceImpl.class);
    @Autowired
    private CapitalMapper capitalMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private AttachmentRelMapper attachmentRelMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    public AgentResult insertAccountPaid(Capital capital,List<String> fileIdList,String cUser)throws Exception{

        AgentResult result = new AgentResult(500,"参数错误","");
        if(StringUtils.isBlank(cUser)){
            return result;
        }

        if(StringUtils.isBlank(capital.getcType())){
            return result;
        }
        capital.setcIsin(Status.STATUS_0.status);
        capital.setStatus(Status.STATUS_1.status);
        capital.setVersion(Status.STATUS_1.status);
        capital.setId(idService.genId(TabId.a_capital));
        Date nowDate = new Date();
        capital.setcTime(nowDate);
        capital.setcUtime(nowDate);
        int insertResult = capitalMapper.insertSelective(capital);
        if(1==insertResult){
            if(fileIdList!=null) {
                for (String fileId : fileIdList) {
                    AttachmentRel record = new AttachmentRel();
                    record.setAttId(fileId);
                    record.setSrcId(capital.getId());
                    record.setcUser(cUser);
                    record.setcTime(nowDate);
                    record.setStatus(Status.STATUS_1.status);
                    record.setBusType(AttachmentRelType.Capital.name());
                    record.setId(idService.genId(TabId.a_attachment_rel));
                    int i = attachmentRelMapper.insertSelective(record);
                    if (1 != i) {
                        log.info("insertAccountPaid添加缴纳款项附件关系失败");
                        return new AgentResult(500, "系统异常", "");
                    }
                }
            }
            log.info("insertAccountPaid缴纳款项添加:成功");
            return AgentResult.ok();
        }
        return new AgentResult(500,"系统异常","");
    }

    @Override
    public int removeAccountPaid(String id) {
        AttachmentRel attachmentRel = attachmentRelMapper.selectByPrimaryKey(id);
        if(attachmentRel!=null){
            attachmentRel.setStatus(Status.STATUS_0.status);
            return attachmentRelMapper.updateByPrimaryKeySelective(attachmentRel);
        }
        return 0;
    }


    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public ResultVO updateListCapitalVo(List<CapitalVo> capitalVoList, Agent agent) throws Exception {

            try {
                if(agent==null)throw new ProcessException("代理商信息不能为空");
                for (CapitalVo capitalVo : capitalVoList) {
                    capitalVo.setcUser(agent.getcUser());
                    capitalVo.setcAgentId(agent.getId());
                    if(StringUtils.isEmpty(capitalVo.getId())) {
                        //直接新曾
                        AgentResult result =   insertAccountPaid(capitalVo, capitalVo.getCapitalTableFile(), agent.getcUser());
                        if(!result.isOK())throw new ProcessException("新增收款信息失败");
                    }else{

                        Capital db_capital = capitalMapper.selectByPrimaryKey(capitalVo.getId());
                        db_capital.setcAgentId(agent.getId());
                        db_capital.setcType(capitalVo.getcType());
                        db_capital.setcAmount(capitalVo.getcAmount());
                        db_capital.setcSrc(capitalVo.getcSrc());
                        db_capital.setcPaytime(capitalVo.getcPaytime());
                        db_capital.setcUser(capitalVo.getcUser());
                        db_capital.setRemark(capitalVo.getRemark());
                        if(1!=capitalMapper.updateByPrimaryKeySelective(db_capital)){
                            throw new ProcessException("更新收款信息失败");
                        }

                        //删除老的附件
                        AttachmentRelExample example = new AttachmentRelExample();
                        example.or().andBusTypeEqualTo(AttachmentRelType.Capital.name()).andSrcIdEqualTo(db_capital.getId()).andStatusEqualTo(Status.STATUS_1.status);
                        List<AttachmentRel> list = attachmentRelMapper.selectByExample(example);
                        for (AttachmentRel attachmentRel : list) {
                            attachmentRel.setStatus(Status.STATUS_0.status);
                            int i = attachmentRelMapper.updateByPrimaryKeySelective(attachmentRel);
                            if (1 != i) {
                                log.info("修改缴纳款项附件关系失败");
                                throw new ProcessException("更新收款信息失败");
                            }
                        }

                        //添加新的附件
                        List<String> fileIdList = capitalVo.getCapitalTableFile();
                        if(fileIdList!=null) {
                            for (String fileId : fileIdList) {
                                AttachmentRel record = new AttachmentRel();
                                record.setAttId(fileId);
                                record.setSrcId(db_capital.getId());
                                record.setcUser(db_capital.getcUser());
                                record.setcTime(Calendar.getInstance().getTime());
                                record.setStatus(Status.STATUS_1.status);
                                record.setBusType(AttachmentRelType.Capital.name());
                                record.setId(idService.genId(TabId.a_attachment_rel));
                                int i = attachmentRelMapper.insertSelective(record);
                                if (1 != i) {
                                    log.info("修改缴纳款项附件关系失败");
                                    throw new ProcessException("更新收款信息失败");
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
