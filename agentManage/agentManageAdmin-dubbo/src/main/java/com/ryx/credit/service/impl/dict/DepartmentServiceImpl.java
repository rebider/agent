package com.ryx.credit.service.impl.dict;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.commons.result.Tree;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.COrganizationMapper;
import com.ryx.credit.dao.CUserMapper;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.pojo.admin.agent.Region;
import com.ryx.credit.pojo.admin.vo.UserVo;
import com.ryx.credit.service.dict.DepartmentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private CUserMapper cUserMapper;



    @Override
    public List<Tree> selectAllDepartment(String pCode) {
        List<Tree> rootTree = new ArrayList<Tree>();
        List<COrganization> cOrganizationList = null;
        EntityWrapper<COrganization> wrapper = new EntityWrapper<COrganization>();
        wrapper.orderBy("seq");
        if (null!=pCode&&pCode!="")
        wrapper.eq("PID",pCode);
        else
        wrapper.isNull("PID");
        cOrganizationList = cOrganizationMapper.selectList(wrapper);
        for (COrganization cOrganization : cOrganizationList) {
            rootTree.add(departmentToTree(cOrganization));
        }
        return rootTree;
    }


    private Tree departmentToTree(COrganization cOrganization){
        Tree tree = new Tree();
        tree.setId(Long.valueOf(cOrganization.getId())+"");
        if (null==cOrganization.getPid()){
            tree.setPid(Long.valueOf(0)+"");
        }else
       tree.setPid(Long.valueOf(cOrganization.getPid())+"");
        tree.setText(cOrganization.getName());
        tree.setIconCls(cOrganization.getIcon());
        EntityWrapper<COrganization> wrapper = new EntityWrapper<COrganization>();
        wrapper.orderBy("seq");

        if (null!=cOrganization.getId())
            wrapper.eq("PID",cOrganization.getId());
        else
            wrapper.isNull("PID");

        wrapper.eq("PID",cOrganization.getId());
        tree.setState(cOrganizationMapper.selectCount(wrapper)==0?1:0);
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

    @Override
    public COrganization getById(String id) {
        return cOrganizationMapper.selectById(id);
    }

    @Override
    public COrganization getByName(String name) {
        List<Map> list = cOrganizationMapper.selectByOrgName(name);
        if(list.size()>0){

            try {
                return cOrganizationMapper.selectById(Integer.valueOf(list.get(0).get("ID")+""));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                throw e;
            }

        }
        return null;
    }


    @Override
    public COrganization getByUserName(String userName) {
        UserVo userVo = cUserMapper.selectbyName(userName);
        if(userVo!=null && userVo.getOrganizationId()!=null){
            COrganization organization = cOrganizationMapper.selectByPrimaryKey(userVo.getOrganizationId());
            if(organization!=null){
                return organization;
            }
        }
        return null;
    }

    @Override
    public COrganization getByUserNameParent(String userName) {
        UserVo userVo = cUserMapper.selectbyName(userName);
        if(userVo!=null && userVo.getOrganizationId()!=null){
            COrganization organization = cOrganizationMapper.selectByPrimaryKey(userVo.getOrganizationId());
            if(organization!=null){
                COrganization organization_p = cOrganizationMapper.selectByPrimaryKey(organization.getPid().intValue());
                return organization_p;
            }
        }
        return null;
    }
}
