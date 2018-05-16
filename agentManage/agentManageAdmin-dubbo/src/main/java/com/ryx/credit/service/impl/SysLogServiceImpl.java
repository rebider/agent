package com.ryx.credit.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ryx.credit.commons.utils.PageInfo;
import com.ryx.credit.dao.CSysLogMapper;
import com.ryx.credit.pojo.admin.CSysLog;
import com.ryx.credit.service.ISysLogService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 *
 * CSysLog 表数据服务层接口实现类
 *
 */
@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<CSysLogMapper, CSysLog> implements ISysLogService {
    
    @Override
    public PageInfo selectDataGrid(PageInfo pageInfo) {
        Page<CSysLog> page = new Page<CSysLog>(pageInfo.getNowpage(), pageInfo.getSize());
        EntityWrapper<CSysLog> wrapper = new EntityWrapper<CSysLog>();
        wrapper.orderBy(pageInfo.getSort(), pageInfo.getOrder().equalsIgnoreCase("ASC"));
        selectPage(page, wrapper);
        pageInfo.setRows((ArrayList) page.getRecords());
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }
    
}