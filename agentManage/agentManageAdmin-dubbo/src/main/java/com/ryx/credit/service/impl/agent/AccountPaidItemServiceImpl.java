package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.AttachmentRelType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.dao.agent.CapitalMapper;
import com.ryx.credit.pojo.admin.agent.AttachmentRel;
import com.ryx.credit.pojo.admin.agent.Capital;
import com.ryx.credit.service.agent.AccountPaidItemService;
import com.ryx.credit.service.dict.IdService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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







}
