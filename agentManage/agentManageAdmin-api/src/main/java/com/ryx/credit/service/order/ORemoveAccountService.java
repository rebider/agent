package com.ryx.credit.service.order;

import com.ryx.credit.common.util.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lrr
 * @Date: 2019/9/20 09:44
 * @Description:销账服务类
 */
public interface ORemoveAccountService {
    /**
     * 销账明细
     */
    PageInfo removeAccountDetail(Map<String, Object> param, PageInfo pageInfo);

    /**
     * 导入销账信息
     */
    public List<String> importExcel(List<List<Object>> list, String userid) throws Exception;
}