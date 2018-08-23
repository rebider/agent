package com.ryx.credit.profit.service.impl;/**
 * @Auther: zhaodw
 * @Date: 2018/8/23 09:57
 * @Description:
 */

import com.ryx.credit.profit.dao.TransProfitDetailMapper;
import com.ryx.credit.profit.pojo.TransProfitDetail;
import com.ryx.credit.profit.service.TransProfitDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分润交易接口实现
 * @author zhaodw
 * @create 2018/8/23
 * @since 1.0.0
 */
@Service
public class TransProfitDetailServiceImpl implements TransProfitDetailService {

    @Autowired
    private TransProfitDetailMapper transProfitDetailMapper;

    @Override
    public void insert(TransProfitDetail transProfitDetail) {
        transProfitDetailMapper.insert(transProfitDetail);
    }
}
