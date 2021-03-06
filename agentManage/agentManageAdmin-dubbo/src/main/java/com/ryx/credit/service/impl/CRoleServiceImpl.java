package com.ryx.credit.service.impl;

import com.ryx.credit.dao.CRoleMapper;
import com.ryx.credit.service.CRoleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/3/26 17:09
 * @Param
 * @return
 **/
@Service("cRoleService")
public class CRoleServiceImpl implements CRoleService {

    @Autowired
    private CRoleMapper roleMapper;

    @Override
    public Set<String> selectShiroUrl(Long userId,String pid,String url){
        return roleMapper.selectShiroUrl(userId,pid,url);
    }

    @Override
    public Set<String> findFinanceRole(Long userId){
        return roleMapper.findFinanceRole(userId);
    }

    @Override
    public Set<String> findFinanceRoleId(Long userId) {
        return roleMapper.findFinanceRoleId(userId);
    }

}
