package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.dao.order.OReturnOrderMapper;
import com.ryx.credit.pojo.admin.order.OReturnOrder;
import com.ryx.credit.pojo.admin.order.OReturnOrderExample;
import com.ryx.credit.service.order.IOrderReturnService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Zhang Lei
 * @Description: 退货
 * @Date: 14:23 2018/7/25
 */
@Service("orderReturnService")
public class OrderReturnServiceImpl implements IOrderReturnService {
    @Autowired
    OReturnOrderMapper returnOrderMapper;

    /**
     * @Author: Zhang Lei
     * @Description: 退货列表查询
     * @Date: 14:24 2018/7/25
     */
    @Override
    public PageInfo orderList(OReturnOrder returnOrder, PageInfo page) {
        OReturnOrderExample example = new OReturnOrderExample();
        OReturnOrderExample.Criteria c = example.or();
        if(returnOrder!=null){
            if(StringUtils.isNotEmpty(returnOrder.getId())){
                c.andIdEqualTo(returnOrder.getId());
            }
            if(StringUtils.isNotEmpty(returnOrder.getAgentId())){
                c.andAgentIdEqualTo(returnOrder.getAgentId());
            }
        }
        example.setPage(new Page(page.getFrom(),page.getPagesize()));
        long count = returnOrderMapper.countByExample(example);
        example.setOrderByClause(" u_time desc ");
        List<OReturnOrder> list = returnOrderMapper.selectByExample(example);
        page.setRows(list);
        page.setTotal(Integer.parseInt(count+""));
        return page;
    }
}
