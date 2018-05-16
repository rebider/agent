package com.ryx.credit.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ryx.credit.dao.CRoleMapper;
import com.ryx.credit.dao.CUserRoleMapper;
import com.ryx.credit.pojo.admin.CRole;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.CUserRole;
import com.ryx.credit.pojo.admin.vo.UserVo;
import com.ryx.credit.service.IUserRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * CUserRole 表数据服务层接口实现类
 *
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<CUserRoleMapper, CUserRole> implements IUserRoleService {

	@Autowired
	private CRoleMapper cRoleMapper;
	@Autowired
	private CUserRoleMapper cUserRoleMapper;

	@Override
	public void insertUserRole(UserVo userVo) {
		/*CRole cRole = new CRole();
		cRole.setName(userVo.getName());
		cRole.setSeq(0);
		cRole.setDescription("代理商");
		cRole.setStatus(0);
		cRoleMapper.insert(cRole);*/
		//根据name查询id
		CRole cRoleId = cRoleMapper.selectByName("DLS");

		CUserRole cUserRole = new CUserRole();
		cUserRole.setRoleId(cRoleId.getId());
		cUserRole.setUserId(userVo.getId());
		cUserRoleMapper.insert(cUserRole);
	}

}