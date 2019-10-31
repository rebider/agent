package com.ryx.credit.dao.agent;

import com.ryx.credit.pojo.admin.agent.AnnouncementReadInfo;
import com.ryx.credit.pojo.admin.agent.AnnouncementReadInfoExample;
import java.util.List;

public interface AnnouncementReadInfoMapper {
    long countByExample(AnnouncementReadInfoExample example);

    int deleteByExample(AnnouncementReadInfoExample example);

    int insert(AnnouncementReadInfo record);

    int insertSelective(AnnouncementReadInfo record);

    List<AnnouncementReadInfo> selectByExample(AnnouncementReadInfoExample example);

    AnnouncementReadInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AnnouncementReadInfo record);

    int updateByPrimaryKey(AnnouncementReadInfo record);

    AnnouncementReadInfo selectByRecord(AnnouncementReadInfo announcementReadInfo);
}