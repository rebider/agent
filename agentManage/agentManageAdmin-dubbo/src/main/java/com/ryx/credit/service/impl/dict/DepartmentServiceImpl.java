package com.ryx.credit.service.impl.dict;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.commons.result.Tree;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.COrganizationMapper;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.pojo.admin.agent.Region;
import com.ryx.credit.service.dict.DepartmentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DepartmentServiceImpl
 * @Description 部门实现类
 * @Author lrr
 * @Date 2018/5/29
 **/
@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {
    private static final Logger log = Logger.getLogger(RegionServiceImpl.class);

    @Autowired
    private COrganizationMapper cOrganizationMapper;
    @Override
    public List<Tree> selectAllDepartment() {

        List<COrganization> cOrganizationList = null;
        EntityWrapper<COrganization> wrapper = new EntityWrapper<COrganization>();
        wrapper.orderBy("seq");
        cOrganizationList = cOrganizationMapper.selectList(wrapper);

        List<Tree> rootTree = new ArrayList<Tree>();
        //根目录
        List<Tree> menuList = new ArrayList<Tree>();
        for (COrganization cOrganization : cOrganizationList) {
            rootTree.add(departmentToTree(cOrganization));
            if(null==cOrganization.getPid()){
                menuList.add(departmentToTree(cOrganization));
            }
        }
        for (Tree tree : menuList) {
            tree.setChildren(getChild(String.valueOf(tree.getId()),rootTree));
        }
        return menuList;
    }


    private Tree departmentToTree(COrganization cOrganization){
        Tree tree = new Tree();
        tree.setId(Long.valueOf(cOrganization.getId()));
        if (null==cOrganization.getPid()){
            tree.setPid(Long.valueOf(0));
        }else
       tree.setPid(Long.valueOf(cOrganization.getPid()));
        tree.setText(cOrganization.getName());
        tree.setState(1);
        tree.setIconCls(cOrganization.getIcon());
        return tree;
    }

    private List<Tree> getChild(String id, List<Tree> rootTree) {

        //子菜单
        List<Tree> childList = new ArrayList<>();
        for (Tree region : rootTree) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (StringUtils.isNotBlank(String.valueOf(region.getPid()))) {
                if (String.valueOf(region.getPid()).equals(id)) {
                    childList.add(region);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (Tree tree : childList) {
            //3表示最后一级
                //递归
                tree.setChildren(getChild(String.valueOf(tree.getId()), rootTree));
        }
        //递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }
}
