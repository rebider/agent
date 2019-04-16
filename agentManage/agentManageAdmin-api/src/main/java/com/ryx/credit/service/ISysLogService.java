package com.ryx.credit.service;

import com.ryx.credit.common.util.Page;;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.CSysLog;

/**
 *
 * CSysLog 表数据服务层接口
 *
 */
public interface ISysLogService{

    PageInfo selectDataGrid(Page page);

    void insert(CSysLog sysLog);
}