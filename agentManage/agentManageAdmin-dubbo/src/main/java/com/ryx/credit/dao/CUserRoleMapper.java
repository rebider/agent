package com.ryx.credit.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.CUserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 * CUserRole 表数据库控制层接口
 *
 */
public interface CUserRoleMapper extends BaseMapper<CUserRole> {

    List<CUserRole> selectByUserId(@Param("userId") Long userId);

    @Select("select role_id AS roleId from c_user_role where user_id = #{userId}")
    @ResultType(Long.class)
    List<Long> selectRoleIdListByUserId(@Param("userId") Long userId);

    @Delete("DELETE FROM c_user_role WHERE user_id = #{userId}")
    int deleteByUserId(@Param("userId") Long userId);

}