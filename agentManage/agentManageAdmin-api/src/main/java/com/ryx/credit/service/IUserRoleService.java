package com.ryx.credit.service;

import com.baomidou.mybatisplus.service.IService;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.CUserRole;
import com.ryx.credit.pojo.admin.vo.UserVo;

/**
 *
 * CUserRole 表数据服务层接口
 *
 */
public interface IUserRoleService extends IService<CUserRole> {

	void insertUserRole(UserVo userVo);


}