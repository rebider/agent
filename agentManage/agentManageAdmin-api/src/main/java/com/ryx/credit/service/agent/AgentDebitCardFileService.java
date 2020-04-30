package com.ryx.credit.service.agent;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 结算卡文件管理
 *  Created by Chen Qiutian on 2019/7/31.
 */
public interface AgentDebitCardFileService {

    /**
     * 智慧POS/PLUS的一级代理商的结算卡信息，放置对账文件的FTP
     * @return
     */
    FastMap exportsForZHposOrPlus() throws Exception;


}
