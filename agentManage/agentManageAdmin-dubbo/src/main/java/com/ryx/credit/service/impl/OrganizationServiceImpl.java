package com.ryx.credit.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ryx.credit.commons.result.Tree;
import com.ryx.credit.dao.COrganizationMapper;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.service.IOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * COrganization 表数据服务层接口实现类
 *
 */
@Service("organizationService")
public class OrganizationServiceImpl extends ServiceImpl<COrganizationMapper, COrganization> implements IOrganizationService {

    @Autowired
    private COrganizationMapper organizationMapper;
    
    @Override
    public List<Tree> selectTree() {
        List<COrganization> organizationList = selectTreeGrid();

        List<Tree> trees = new ArrayList<Tree>();
        if (organizationList != null) {
            for (COrganization organization : organizationList) {
                Tree tree = new Tree();
                tree.setId(organization.getId()+"");
                tree.setText(organization.getName());
                tree.setIconCls(organization.getIcon());
                tree.setPid(organization.getPid()+"");
                trees.add(tree);
            }
        }
        return trees;
    }

    @Override
    public List<COrganization> selectTreeGrid() {
        EntityWrapper<COrganization> wrapper = new EntityWrapper<COrganization>();
        wrapper.orderBy("seq");
        return organizationMapper.selectList(wrapper);
    }


}