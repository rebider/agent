package com.ryx.credit.service;

import com.baomidou.mybatisplus.service.IService;
import com.ryx.credit.commons.utils.PageInfo;
import com.ryx.credit.pojo.admin.CSysLog;

/**
 *
 * CSysLog 表数据服务层接口
 *
 */
public interface ISysLogService extends IService<CSysLog> {

    PageInfo selectDataGrid(PageInfo pageInfo);

}