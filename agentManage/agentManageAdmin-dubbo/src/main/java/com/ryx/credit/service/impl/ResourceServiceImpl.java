package com.ryx.credit.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.commons.result.Tree;
import com.ryx.credit.commons.shiro.ShiroUser;
import com.ryx.credit.dao.*;
import com.ryx.credit.dao.agent.PlatFormMapper;
import com.ryx.credit.pojo.admin.CResource;
import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.pojo.admin.agent.PlatFormExample;
import com.ryx.credit.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.util.resources.ga.LocaleNames_ga;

import java.io.Serializable;
import java.util.*;

/**
 *
 * CResource 表数据服务层接口实现类
 *
 */
@Service("resourceService")
public class ResourceServiceImpl extends ServiceImpl<CResourceMapper, CResource> implements IResourceService {
    private static final int RESOURCE_MENU = 0; // 菜单

    @Autowired
    private CResourceMapper resourceMapper;
    @Autowired
    private CUserRoleMapper userRoleMapper;
    @Autowired
    private CRoleMapper roleMapper;
    @Autowired
    private CRoleResourceMapper roleResourceMapper;
    
    @Override
    public List<CResource> selectAll() {
        EntityWrapper<CResource> wrapper = new EntityWrapper<CResource>();
        wrapper.orderBy("seq");
        return resourceMapper.selectList(wrapper);
    }
    
    public List<CResource> selectByType(Integer type) {
        EntityWrapper<CResource> wrapper = new EntityWrapper<CResource>();
        CResource resource = new CResource();
        wrapper.setEntity(resource);
        wrapper.addFilter("resource_type = {0}", type);
        wrapper.orderBy("seq");
        return resourceMapper.selectList(wrapper);
    }
    
    @Override
    public List<Tree> selectAllMenu() {
        List<Tree> trees = new ArrayList<Tree>();
        // 查询所有菜单
        List<CResource> resources = this.selectByType(RESOURCE_MENU);
        if (resources == null) {
            return trees;
        }
        for (CResource resource : resources) {
            Tree tree = new Tree();
            tree.setId(resource.getId()+"");
            tree.setPid(resource.getPid()+"");
            tree.setText(resource.getName());
            tree.setIconCls(resource.getIcon());
            tree.setAttributes(resource.getUrl());
            tree.setState(resource.getOpened());
            trees.add(tree);
        }
        return trees;
    }
    
    @Override
    public List<Tree> selectAllTree() {
        // 获取所有的资源 tree形式，展示
        List<Tree> trees = new ArrayList<Tree>();
        List<CResource> resources = this.selectAll();
        if (resources == null) {
            return trees;
        }
        for (CResource resource : resources) {
            Tree tree = new Tree();
            tree.setId(resource.getId()+"");
            tree.setPid(resource.getPid()+"");
            tree.setText(resource.getName());
            tree.setIconCls(resource.getIcon());
            tree.setAttributes(resource.getUrl());
            tree.setState(resource.getOpened());
            trees.add(tree);
        }
        return trees;
    }
    
    @Override
    public List<Tree> selectTree(ShiroUser shiroUser) {
        List<Tree> trees = new ArrayList<Tree>();
        // shiro中缓存的用户角色
        Set<String> roles = shiroUser.getRoles();
        if (roles == null) {
            return trees;
        }
        // 如果有超级管理员权限
        if (roles.contains("admin")) {
            List<CResource> resourceList = this.selectByType(RESOURCE_MENU);
            if (resourceList == null) {
                return trees;
            }
            for (CResource resource : resourceList) {
                Tree tree = new Tree();
                tree.setId(resource.getId()+"");
                tree.setPid(resource.getPid()+"");
                tree.setText(resource.getName());
                tree.setIconCls(resource.getIcon());
                tree.setAttributes(resource.getUrl());
                tree.setOpenMode(resource.getOpenMode());
                tree.setState(resource.getOpened());
                trees.add(tree);
            }
            return trees;
        }
        // 普通用户
        List<Long> roleIdList = userRoleMapper.selectRoleIdListByUserId(shiroUser.getId());
        if (roleIdList == null) {
            return trees;
        }
        List<CResource> resourceLists = roleMapper.selectResourceListByRoleIdList(roleIdList);
        if (resourceLists == null) {
            return trees;
        }
        for (CResource resource : resourceLists) {
            Tree tree = new Tree();
            tree.setId(resource.getId()+"");
            tree.setPid(resource.getPid()+"");
            tree.setText(resource.getName());
            tree.setIconCls(resource.getIcon());
            tree.setAttributes(resource.getUrl());
            tree.setOpenMode(resource.getOpenMode());
            tree.setState(resource.getOpened());
            trees.add(tree);
        }
        return trees;
    }

	@Override
	public boolean deleteById(Serializable resourceId) {
		roleResourceMapper.deleteByResourceId(resourceId);
		return super.deleteById(resourceId);
	}


	public List<Map> userHasPlatfromPerm(Long userId){
        return resourceMapper.userHasPlatfromPerm(userId);
    }

    @Autowired
    private PlatFormMapper platFormMapper;

//    @Autowired
    public void insert(){
        List<PlatForm> platForms = platFormMapper.selectByExample(new PlatFormExample());
        int i = 0;
        for (PlatForm platForm : platForms) {
            CResource resource = new CResource();
            resource.setName("入网待办任务_"+platForm.getPlatformName());
            resource.setUrl("ACTIVITY_"+platForm.getPlatformNum());
            resource.setOpenMode("ajax");
            resource.setIcon("fi-folder");
            resource.setPid(Long.valueOf(AppConfig.getProperty("netInUrls_pid")));
            resource.setSeq(i);
            resource.setStatus(0);
            resource.setOpened(1);
            resource.setResourceType(1);
            resource.setCreateTime(new Date());
            resourceMapper.insert(resource);
            i++;
        }
    }
}