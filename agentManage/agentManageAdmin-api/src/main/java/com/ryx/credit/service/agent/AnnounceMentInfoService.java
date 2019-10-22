package com.ryx.credit.service.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.agent.AnnounceMentInfo;
import com.ryx.credit.pojo.admin.agent.Attachment;
import com.ryx.credit.pojo.admin.vo.AnnounceMentInfoVo;

import java.util.List;
import java.util.Map;

/**
 * @Auther: ssx
 * @Date: 2019/10/10 10:14
 * @Description:
 */
public interface  AnnounceMentInfoService {
    ResultVO saveAnn(AnnounceMentInfoVo announceMentInfo);
    ResultVO upStat(AnnounceMentInfo announceMentInfo,String orgStat);
    AnnounceMentInfo queryById(String annId);
    //公告运维
    PageInfo selectAnnViewsMaintain(Page page,Map map);
    //公告管理
    PageInfo selectAnnViewsManage(Page page,Map map);
    //代理商查看公告
    PageInfo selectAnnViewsAgent(Page page,Map map,Long userId);
    //非代理商查看公告
    PageInfo selectAnnViewsRead(Page page,Map map);

    //查询附件
    List<Attachment> queryAttByAnnoid(String id,String busType);
}
