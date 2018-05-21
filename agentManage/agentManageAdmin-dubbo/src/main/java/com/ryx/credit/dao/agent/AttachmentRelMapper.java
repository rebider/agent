package com.ryx.credit.dao.agent;


import com.ryx.credit.pojo.admin.agent.AttachmentRel;
import com.ryx.credit.pojo.admin.agent.AttachmentRelExample;

import java.util.List;

public interface AttachmentRelMapper {
    int countByExample(AttachmentRelExample example);

    int deleteByExample(AttachmentRelExample example);

    int insert(AttachmentRel record);

    int insertSelective(AttachmentRel record);

    List<AttachmentRel> selectByExample(AttachmentRelExample example);

    AttachmentRel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AttachmentRel record);

    int updateByPrimaryKey(AttachmentRel record);
}