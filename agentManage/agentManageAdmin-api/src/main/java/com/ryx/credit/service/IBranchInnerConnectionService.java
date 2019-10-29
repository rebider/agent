package com.ryx.credit.service;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.vo.UserVo;

import java.util.List;
import java.util.Map;

/**
 * @Author ZhaoYd
 * @Date 2019/10/24 11:36
 * @Description 省区和总管账户关联关系
 */
public interface IBranchInnerConnectionService {

    /**
     * 查询能关联的省区账号
     * @return
     */
    List<Map<String, Object>> queryBranchList();

    /**
     * 查询能关联的内管账号
     * @return
     */
    List<Map<String, String>> queryinnerList();

    /**
     * 条件分页查询
     * @return
     */
    PageInfo queruAbleBranchInner(Map<String, Object> param, PageInfo pageInfo) throws Exception;

    /**
     * 解除和内管的关联关系
     * @param id
     * @return
     */
    Map<String, Object> removeBranchInnerConnection(String id);

    /**
     * 关联操作
     * @param str
     * @return
     */
    Map<String, Object> branchConnectionInner(String str, Long userId) throws Exception;

    /**
     * 内管账号的建立
     * @param userVo
     * @return
     */
    Map<String, Object> buildInnerAccout(UserVo userVo, Map<String, String> param) throws Exception;

    /**
     * 导入账号
     * @param data
     * @param user
     * @return
     * @throws Exception
     */
    List<String> addList(List<List<Object>> data, String user)throws Exception;

    /**
     * 导入大区账号
     * @param data
     * @param user
     * @return
     * @throws Exception
     */
    AgentResult addListItem(List<Object> data, String user) throws Exception;


}
