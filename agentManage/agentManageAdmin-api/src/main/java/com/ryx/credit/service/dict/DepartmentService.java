package com.ryx.credit.service.dict;

import com.ryx.credit.commons.result.Tree;
import com.ryx.credit.pojo.admin.COrganization;

import java.util.List;

/**
 * @ClassName DepartmentService
 * @Description
 * @Author lrr
 * @Date 2018/5/29
 **/
public interface DepartmentService {
    List<Tree> selectAllDepartment(String pCode);

    public COrganization getById(String id);

    public COrganization getByName(String id);


    /**
     * 根据用户名获取所属部门
     * @param userName
     * @return
     */
    public COrganization getByUserName(String userName);

    /**
     * 根据用户名获取对应部门的上级部门
     * @param userName
     * @return
     */
    public COrganization getByUserNameParent(String userName);

}
