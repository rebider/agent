package com.ryx.credit.dao.agent;


import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.Attachment;
import com.ryx.credit.pojo.admin.agent.AttachmentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AttachmentMapper {
    int countByExample(AttachmentExample example);

    int deleteByExample(AttachmentExample example);

    int insert(Attachment record);

    int insertSelective(Attachment record);

    List<Attachment> selectByExample(AttachmentExample example);

    Attachment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Attachment record);

    int updateByPrimaryKey(Attachment record);

    public List<Attachment> accessoryQuery(@Param("id") String id, @Param("busType")String busType);

    List<Map<String,Object>> selectAll(@Param("map")Map<String, Object> map,@Param("page") Page page);

    int getCount(@Param("map")Map<String, Object> map);
}