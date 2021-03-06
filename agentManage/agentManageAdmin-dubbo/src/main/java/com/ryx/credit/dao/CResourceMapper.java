package com.ryx.credit.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ryx.credit.pojo.admin.CResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 * CResource 表数据库控制层接口
 *
 */
public interface CResourceMapper extends BaseMapper<CResource> {

    public List<Map> userHasPlatfromPerm(@Param("userId")Long userId);

}