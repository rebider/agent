package com.ryx.credit.dao;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.CBranchInner;
import com.ryx.credit.pojo.admin.CBranchInnerExample;
import org.apache.ibatis.annotations.Param;
import org.apache.kafka.common.protocol.types.Field;

import java.util.List;
import java.util.Map;

/**
 * 内管账号关联省区表
 * @by zhaoYd 2019/10/24 16:28
 */
public interface CBranchInnerMapper {

    long countByExample(CBranchInnerExample example);

    int deleteByExample(CBranchInnerExample example);

    int insert(CBranchInner record);

    CBranchInner selectByPrimaryKey(String id);

    int updateByPrimaryKey(CBranchInner record);

    /**
     * 条件查询
     * @param example
     * @return
     */
    List<Map<String, Object>> selectByExample(CBranchInnerExample example);

    /**
     * 条件插入
     * @param branchInner
     * @return
     */
    int insertSelective(CBranchInner branchInner);

    /**
     * 条件更新
     * @param branchInner
     * @return
     */
    int updateByPrimaryKeySelective(CBranchInner branchInner);

    /**
     * 查询要解除的内管账号
     * @param id
     * @return
     */
    int deleteInnerByIds(String id);

    int countByIdforUpdate(String id);

    int updateByPrimaryId(String id);

    List<String> selectInnerLogin(Map<String, Object> map);

    /**
     * 分页查询
     * @param map
     * @param page
     * @return
     */
    List<Map<String,Object>> selectBranchInnerByPage(@Param("map")Map<String, Object> map, @Param("page") Page page);

    /**
     * 查询总行数
     * @param map
     * @return
     */
    int countByPage(@Param("map")Map<String, Object> map);

    /**
     * 查询是否存在要插入的关联账号
     * @param map
     * @return
     */
    int selectByBranchAndInnerLogin(@Param("map")Map<String, Object> map);

    List<CBranchInner> selectByMap(@Param("map")Map map);

    List<String> selectInnerLoginByBranch(String agDocCode);
}
