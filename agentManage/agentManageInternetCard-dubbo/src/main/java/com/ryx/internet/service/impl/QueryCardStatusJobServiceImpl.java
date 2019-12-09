package com.ryx.internet.service.impl;

import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.util.Page;
import com.ryx.internet.dao.OInternetCardMapper;
import com.ryx.internet.pojo.OInternetCard;
import com.ryx.internet.pojo.OInternetCardExample;
import com.ryx.internet.service.QueryCardStatusJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/12/9 16:12
 * @Param
 * @return
 **/
@Service("queryCardStatusJobService")
public class QueryCardStatusJobServiceImpl implements QueryCardStatusJobService {

    private static Logger log = LoggerFactory.getLogger(QueryCardStatusJobServiceImpl.class);
    @Autowired
    private OInternetCardMapper internetCardMapper;


    @Override
    public List<OInternetCard> fetchDataUpdateCardStatus(){
        log.info("fetchDataUpdateCardStatus查询流量卡更新卡状态开始");
        OInternetCardExample oInternetCardExample = new OInternetCardExample();
        OInternetCardExample.Criteria criteria = oInternetCardExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);



        oInternetCardExample.setPage(new Page(0,100));
        List<OInternetCard> internetCards = internetCardMapper.selectByExample(oInternetCardExample);
        log.info("fetchDataUpdateCardStatus查询流量卡更新卡状态数量为:{}",internetCards.size());
        return internetCards;
    }


}
