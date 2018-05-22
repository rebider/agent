package com.ryx.credit.service.impl.dict;

import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.dao.agent.DictMapper;
import com.ryx.credit.service.dict.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * ID生成服务类
 * Created by cx on 2018/5/22.
 */
@Service
public class IdServiceImpl implements IdService {



    @PostConstruct
    public void init(){
    }

    @Autowired
    private DictMapper dictMapper;

    @Override
    public String genId(TabId tablename) {
        long id  = dictMapper.sqlId(tablename.name());
        return String.format(tablename.patt,DateUtil.getDateToString(new Date()),id);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public String genIdInTran(TabId tablename) {
        long id  = dictMapper.sqlId(tablename.name());
        return String.format(tablename.patt,DateUtil.getDateToString(new Date()),id);
    }
}
