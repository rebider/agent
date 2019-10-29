package com.ryx.credit.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ryx.credit.pojo.admin.COrganization;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 * COrganization 表数据库控制层接口
 *
 */
public interface COrganizationMapper extends BaseMapper<COrganization> {

    public List<Map> selectByOrgName(@Param("name") String name);

    List<COrganization> selectByOrgPid(String orgPid);

    COrganization selectByPrimaryKey(Integer id);

    COrganization selectByCode(String code);

    List<COrganization> selectRegion(@Param("pCode") Integer pCode);

    /**
     * 根据用户id查询是否是省区或者大区
     * @param cUser
     * @return
     */
    List<COrganization> selectCityRegion(@Param("cUser") Long cUser);

    /**
     * 查询所有的省区
     * @return
     */
    List<Map<String, Object>> selectBranchList();
}