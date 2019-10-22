package com.ryx.credit.service.impl.agent;

import com.ryx.credit.dao.agent.AnnoPlatformRelaMapper;
import com.ryx.credit.pojo.admin.agent.AnnoPlatformRela;
import com.ryx.credit.service.agent.AnnoPlatformRelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: agentManage
 * @description:
 * @author: ssx
 * @create: 2019-10-11 16:59
 **/
@Service("annoPlatformRelaService")
public class AnnoPlatformRelaServiceImpl implements AnnoPlatformRelaService {

    @Autowired
    private AnnoPlatformRelaMapper annoPlatformRelaMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    public int batchSave(List<AnnoPlatformRela> annoPlatformRelas) {
        return  annoPlatformRelaMapper.saveBatch(annoPlatformRelas);
    }
}
