package com.ryx.credit.service.agent;

import com.ryx.credit.pojo.admin.agent.AnnoPlatformRela;

import java.util.List;

/**
 * @program: agentManage
 * @description:
 * @author: ssx
 * @create: 2019-10-10 13:10
 **/
public interface AnnoPlatformRelaService {
    int batchSave(List<AnnoPlatformRela> annoPlatformRela);
}
