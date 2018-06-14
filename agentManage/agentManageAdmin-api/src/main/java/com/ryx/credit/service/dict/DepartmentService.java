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
    List<Tree> selectAllDepartment();

    public COrganization getById(String id);

    public COrganization getByName(String id);
}
