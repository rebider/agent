package com.ryx.credit.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ryx.credit.pojo.admin.CResource;
import com.ryx.credit.pojo.admin.CRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * CRole 表数据库控制层接口
 *
 */
public interface CRoleMapper extends BaseMapper<CRole> {

    List<Long> selectResourceIdListByRoleId(@Param("id") Long id);

    List<CResource> selectResourceListByRoleIdList(@Param("list") List<Long> list);

    List<Map<Long, String>> selectResourceListByRoleId(@Param("id") Long id);

	CRole selectByName(String name);

	Set<String> selectShiroUrl(@Param("userId") Long userId,@Param("pid") String pid,@Param("url") String url);
}