package com.ryx.credit.service;

import com.baomidou.mybatisplus.service.IService;
import com.ryx.credit.commons.result.Tree;
import com.ryx.credit.commons.shiro.ShiroUser;
import com.ryx.credit.pojo.admin.CResource;

import java.util.List;

/**
 *
 * CResource 表数据服务层接口
 *
 */
public interface IResourceService extends IService<CResource> {

    List<CResource> selectAll();

    List<Tree> selectAllMenu();

    List<Tree> selectAllTree();

    List<Tree> selectTree(ShiroUser shiroUser);

}