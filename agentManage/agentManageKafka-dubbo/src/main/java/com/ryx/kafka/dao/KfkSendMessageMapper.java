package com.ryx.kafka.dao;

import com.ryx.kafka.pojo.KfkSendMessage;
import com.ryx.kafka.pojo.KfkSendMessageExample;
import java.util.List;

public interface KfkSendMessageMapper {
    long countByExample(KfkSendMessageExample example);

    int deleteByExample(KfkSendMessageExample example);

    int insert(KfkSendMessage record);

    int insertSelective(KfkSendMessage record);

    List<KfkSendMessage> selectByExampleWithBLOBs(KfkSendMessageExample example);

    List<KfkSendMessage> selectByExample(KfkSendMessageExample example);

    KfkSendMessage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(KfkSendMessage record);

    int updateByPrimaryKeyWithBLOBs(KfkSendMessage record);

    int updateByPrimaryKey(KfkSendMessage record);
}