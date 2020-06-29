package com.ryx.credit.dao.agent;

import com.ryx.credit.common.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AgentDebitCardMapper {

    List<Map<String,String>> queryForList(@Param("map") Map<String,String> map,@Param("page") Page page);

    int countQuery(@Param("map") Map<String,String> map);

    List<Map<String,String>> queryAgentColinfoList(@Param("map") Map<String,String> map,@Param("page") Page page);

    int queryAgentColinfoCount(@Param("map") Map<String,String> map);

    List<Map<String,Object>> exports(@Param("map") Map<String,String> map);

    List<Map<String,String>> getBusInfoById(String id);

    void updateSuggestStatusById(@Param("id") String id,@Param("statu") String statu);

    List<Map<String,String>> getNoticeList(@Param("orgId") String orgId,@Param("page") Page page);

    int getNoticeCount(String orgId);

    List<Map<String,String>> exportsForZHposOrPlus();
}
