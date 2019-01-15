package com.ryx.credit.service;

import com.baomidou.mybatisplus.service.IService;
import com.ryx.credit.commons.utils.PageInfo;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.vo.UserVo;

import java.util.List;
import java.util.Map;

/**
 *
 * CUser 表数据服务层接口
 *
 */
public interface IUserService extends IService<CUser> {

    List<CUser> selectByLoginName(UserVo userVo);

    void insertByVo(UserVo userVo);

    UserVo selectVoById(Long id);

    void updateByVo(UserVo userVo);

    void updatePwdByUserId(Long userId, String md5Hex);

    String selectDataGrid(PageInfo pageInfo);

    void deleteUserById(Long id);

    UserVo selectByName(String name);

    List<Map<String, Object>> orgCode(Long userID);

    List<UserVo> selectListByName(String name);

    UserVo selectByLoginName(String loginName);
}