package com.ryx.credit.service.impl.agent;

import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.pojo.admin.agent.AttachmentRel;
import com.ryx.credit.service.agent.AttachmentRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/7/3 11:13
 * @Param
 * @return
 **/
@Service("attachmentRelService")
public class AttachmentRelServiceImpl implements AttachmentRelService {

    @Autowired
    private AttachmentRelMapper attachmentRelMapper;

    @Override
    public int insertSelective(AttachmentRel record){
        return attachmentRelMapper.insertSelective(record);
    }
}
