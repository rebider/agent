package com.ryx.credit.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ryx.credit.commons.utils.BeanUtils;
import com.ryx.credit.commons.utils.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.CUserMapper;
import com.ryx.credit.dao.CUserRoleMapper;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.CUserRole;
import com.ryx.credit.pojo.admin.vo.UserVo;
import com.ryx.credit.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * CUser 表数据服务层接口实现类
 *
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<CUserMapper, CUser> implements IUserService {

    @Autowired
    private CUserMapper userMapper;
    @Autowired
    private CUserRoleMapper userRoleMapper;
    
    @Override
    public List<CUser> selectByLoginName(UserVo userVo) {
        CUser user = new CUser();
        user.setLoginName(userVo.getLoginName());
        EntityWrapper<CUser> wrapper = new EntityWrapper<CUser>(user);
        if (null != userVo.getId()) {
            wrapper.where("id != {0}", userVo.getId());
        }
        //List<CUser> userInfo = userMapper.selectListByLogin(userVo.getLoginName());
        //return userInfo;
        return this.selectList(wrapper);
    }

    @Override
    public void insertByVo(UserVo userVo) {
        CUser user = BeanUtils.copy(userVo, CUser.class);
        user.setCreateTime(new Date());
        this.insert(user);
        
        Long id = user.getId();
        String[] roles = userVo.getRoleIds().split(",");
        CUserRole userRole = new CUserRole();
        for (String string : roles) {
            userRole.setUserId(id);
            userRole.setRoleId(Long.valueOf(string));
            userRoleMapper.insert(userRole);
        }
    }

    @Override
    public UserVo selectVoById(Long id) {
        return userMapper.selectUserVoById(id);
    }

    @Override
    public void updateByVo(UserVo userVo) {
        CUser user = BeanUtils.copy(userVo, CUser.class);
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        }
        this.updateById(user);
        
        Long id = userVo.getId();
        List<CUserRole> userRoles = userRoleMapper.selectByUserId(id);
        if (userRoles != null && !userRoles.isEmpty()) {
            for (CUserRole userRole : userRoles) {
                userRoleMapper.deleteById(userRole.getId());
            }
        }

        String[] roles = userVo.getRoleIds().split(",");
        CUserRole userRole = new CUserRole();
        for (String string : roles) {
            userRole.setUserId(id);
            userRole.setRoleId(Long.valueOf(string));
            userRoleMapper.insert(userRole);
        }
    }

    @Override
    public void updatePwdByUserId(Long userId, String md5Hex) {
        CUser user = new CUser();
        user.setId(userId);
        user.setPassword(md5Hex);
        this.updateById(user);
    }

    @Override
    public String selectDataGrid(PageInfo pageInfo) {
        Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageInfo.getNowpage(), pageInfo.getSize());
        page.setOrderByField(pageInfo.getSort());
        page.setAsc(pageInfo.getOrder().equalsIgnoreCase("asc"));
        List<Map<String, Object>> list =  userMapper.selectUserPage(page, pageInfo.getCondition());
        pageInfo.setRows((ArrayList) list);
        //pageInfo.setTotal(page.getTotal());
        pageInfo.setTotal(list.size());
        return JSON.toJSON(pageInfo).toString();
    }

    @Override
    public void deleteUserById(Long id) {
        this.deleteById(id);
        userRoleMapper.deleteByUserId(id);
    }

	@Override
	public UserVo selectByName(String name) {
		// TODO Auto-generated method stub
		return userMapper.selectbyName(name);
	}

}