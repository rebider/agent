package com.ryx.credit.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ryx.credit.commons.result.Tree;
import com.ryx.credit.commons.utils.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.*;
import com.ryx.credit.pojo.admin.CRole;
import com.ryx.credit.pojo.admin.CRoleResource;
import com.ryx.credit.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * CRole 表数据服务层接口实现类
 *
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<CRoleMapper, CRole> implements IRoleService {

    @Autowired
    private CRoleMapper roleMapper;
    @Autowired
    private CUserRoleMapper userRoleMapper;
    @Autowired
    private CRoleResourceMapper roleResourceMapper;
    
    public List<CRole> selectAll() {
        EntityWrapper<CRole> wrapper = new EntityWrapper<CRole>();
        wrapper.orderBy("seq");
        return roleMapper.selectList(wrapper);
    }
    
    @Override
    public PageInfo selectDataGrid(PageInfo pageInfo) {
        Page<CRole> page = new Page<CRole>(pageInfo.getNowpage(), pageInfo.getSize());
        
        EntityWrapper<CRole> wrapper = new EntityWrapper<CRole>();
        wrapper.orderBy(pageInfo.getSort(), pageInfo.getOrder().equalsIgnoreCase("ASC"));
        selectPage(page, wrapper);
        
        pageInfo.setRows((ArrayList) page.getRecords());
       // pageInfo.setTotal(page.getTotal());
        pageInfo.setTotal(Integer.valueOf(page.getRecords().size()));
        return pageInfo;
    }

    @Override
    public Object selectTree() {
        List<Tree> trees = new ArrayList<Tree>();
        List<CRole> roles = this.selectAll();
        for (CRole role : roles) {
            Tree tree = new Tree();
            tree.setId(role.getId()+"");
            tree.setText(role.getName());

            trees.add(tree);
        }
        return trees;
    }

    @Override
    public void updateRoleResource(Long roleId, String resourceIds) {
        // 先删除后添加,有点爆力
        CRoleResource roleResource = new CRoleResource();
        roleResource.setRoleId(roleId);
        roleResourceMapper.delete(new EntityWrapper<CRoleResource>(roleResource));
        
        String[] resourceIdArray = resourceIds.split(",");
        for (String resourceId : resourceIdArray) {
            roleResource = new CRoleResource();
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(Long.parseLong(resourceId));
            roleResourceMapper.insert(roleResource);
        }
    }

    @Override
    public List<Long> selectResourceIdListByRoleId(Long id) {
        return roleMapper.selectResourceIdListByRoleId(id);
    }
    
    @Override
    public Map<String, Set<String>> selectResourceMapByUserId(Long userId) {
        Map<String, Set<String>> resourceMap = new HashMap<String, Set<String>>();
        List<Long> roleIdList = userRoleMapper.selectRoleIdListByUserId(userId);
        Set<String> urlSet = new HashSet<String>();
        Set<String> roles = new HashSet<String>();
        for (Long roleId : roleIdList) {
            List<Map<Long, String>> resourceList = roleMapper.selectResourceListByRoleId(roleId);
            if (resourceList != null) {
                for (Map<Long, String> map : resourceList) {
                    if (map!=null && StringUtils.isNotBlank(map.get("URL"))) {
                        urlSet.add(map.get("URL"));
                    }
                }
            }
            CRole role = roleMapper.selectById(roleId);
            if (role != null) {
                roles.add(role.getName());
            }
        }
        resourceMap.put("urls", urlSet);
        resourceMap.put("roles", roles);
        return resourceMap;
    }

}