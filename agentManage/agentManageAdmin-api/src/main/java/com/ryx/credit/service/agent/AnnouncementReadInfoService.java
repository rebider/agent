package com.ryx.credit.service.agent;

import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.agent.AnnouncementReadInfo;

/**
 * @program: agentManage
 * @description:
 * @author: ssx
 * @create: 2019-10-15 10:32
 **/
public interface AnnouncementReadInfoService {
    ResultVO saveResdInfo(AnnouncementReadInfo announcementReadInfo);
    AnnouncementReadInfo queryByRecord(AnnouncementReadInfo announcementReadInfo);
}
