package com.ryx.credit.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 * CUser 表数据库控制层接口
 *
 */
public interface CUserMapper extends BaseMapper<CUser> {

    UserVo selectUserVoById(@Param("id") Long id);

    List<CUser> selectListByLogin(@Param("login_name") String login_name);
    
    List<Map<String, Object>> selectUserPage(Pagination page, Map<String, Object> params);

    int selectUserCount(Map<String, Object> params);

    UserVo selectbyName(String name);

    List<Map<String, Object>> selectOrganizationCodeById(@Param("id") Long id);

    List<UserVo> selectUserByOrgId(Long orgId);

    List<UserVo>  selectListByName(String name);

    int updateStatusByPrimaryKey(Long id);

    UserVo selectByLoginName(String loginName);
}