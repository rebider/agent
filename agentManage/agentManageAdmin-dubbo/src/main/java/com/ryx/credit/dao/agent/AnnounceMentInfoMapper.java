package com.ryx.credit.dao.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.agent.AnnounceMentInfo;
import com.ryx.credit.pojo.admin.agent.AnnounceMentInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AnnounceMentInfoMapper {
    long countByExample(AnnounceMentInfoExample example);

    int deleteByExample(AnnounceMentInfoExample example);

    int insert(AnnounceMentInfo record);

    int insertSelective(AnnounceMentInfo record);

    List<AnnounceMentInfo> selectByExampleWithBLOBs(AnnounceMentInfoExample example);

    List<AnnounceMentInfo> selectByExample(AnnounceMentInfoExample example);

    AnnounceMentInfo selectByPrimaryKey(String annId);

    int updateByPrimaryKeySelective(AnnounceMentInfo record);

    int updateByPrimaryKeyWithBLOBs(AnnounceMentInfo record);

    int updateByPrimaryKey(AnnounceMentInfo record);

    int updateStatByAnno(AnnounceMentInfo record);

    List<Map<String,Object>> selectAnnMaintain(@Param("map")Map<String, Object> map,@Param("page") Page page);

    int selectCountAnnMaintain(@Param("map")Map<String,Object> map);

    List<Map<String,Object>> selectAnnManage(@Param("map")Map<String, Object> map,@Param("page") Page page);

    int selectCountAnnManage(@Param("map")Map<String,Object> map);

    List<Map<String,Object>> selectAnnReader(@Param("map")Map<String, Object> map,@Param("page") Page page);

    int selectCountAnnReader(@Param("map")Map<String,Object> map);
}