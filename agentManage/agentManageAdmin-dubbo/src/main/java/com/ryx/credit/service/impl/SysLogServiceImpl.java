package com.ryx.credit.service.impl;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.dao.CSysLogMapper;
import com.ryx.credit.pojo.admin.CSysLog;
import com.ryx.credit.pojo.admin.CSysLogExample;
import com.ryx.credit.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 *
 * CSysLog 表数据服务层接口实现类
 *
 */
@Service("sysLogService")
public class SysLogServiceImpl implements ISysLogService {

    @Autowired
    private CSysLogMapper cSysLogMapper;

    @Override
    public PageInfo selectDataGrid(Page page) {

        CSysLogExample cSysLogExample = new CSysLogExample();
        cSysLogExample.setPage(page);
        cSysLogExample.setOrderByClause(" create_time desc ");
        List<CSysLog> cSysLogs = cSysLogMapper.selectByExample(cSysLogExample);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(cSysLogs);
        pageInfo.setTotal((int)cSysLogMapper.countByExample(cSysLogExample));
        return pageInfo;

    }

    @Override
    public void insert(CSysLog sysLog){
        cSysLogMapper.insert(sysLog);
    }
}