package com.ryx.credit.activity.dao;

import com.ryx.credit.activity.entity.KafkaSendMessage;
import com.ryx.credit.activity.entity.KafkaSendMessageExample;
import java.util.List;

public interface KafkaSendMessageMapper {
    long countByExample(KafkaSendMessageExample example);

    int deleteByExample(KafkaSendMessageExample example);

    int insert(KafkaSendMessage record);

    int insertSelective(KafkaSendMessage record);

    List<KafkaSendMessage> selectByExampleWithBLOBs(KafkaSendMessageExample example);

    List<KafkaSendMessage> selectByExample(KafkaSendMessageExample example);

    KafkaSendMessage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(KafkaSendMessage record);

    int updateByPrimaryKeyWithBLOBs(KafkaSendMessage record);

    int updateByPrimaryKey(KafkaSendMessage record);
}