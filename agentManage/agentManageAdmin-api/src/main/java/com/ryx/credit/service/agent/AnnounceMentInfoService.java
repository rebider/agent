package com.ryx.credit.service.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.agent.AnnounceMentInfo;
import com.ryx.credit.pojo.admin.vo.AnnounceMentInfoVo;

import java.util.List;
import java.util.Map;

/**
 * @Auther: ssx
 * @Date: 2019/10/10 10:14
 * @Description:
 */
public interface  AnnounceMentInfoService {
    PageInfo selectAnnViews(Page pageInfo, Map map);
    ResultVO saveAnn(AnnounceMentInfoVo announceMentInfo);
    ResultVO upStat(AnnounceMentInfo announceMentInfo);
    AnnounceMentInfo queryById(String annId);
    PageInfo selectAnnViewsMaintain(Page page,Map map);
}
