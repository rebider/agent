package com.ryx.credit.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ryx.credit.dao.CRoleResourceMapper;
import com.ryx.credit.pojo.admin.CRoleResource;
import com.ryx.credit.service.IRoleResourceService;
import org.springframework.stereotype.Service;

/**
 *
 * CRoleResource 表数据服务层接口实现类
 *
 */
@Service("roleResourceService")
public class RoleResourceServiceImpl extends ServiceImpl<CRoleResourceMapper, CRoleResource> implements IRoleResourceService {


}