package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.dao.agent.PlatFormMapper;
import com.ryx.credit.dao.order.OrganizationMapper;
import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.pojo.admin.agent.PlatFormExample;
import com.ryx.credit.pojo.admin.order.Organization;
import com.ryx.credit.service.agent.PlatFormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Lihl
 * @Date 2018/07/19
 * 业务平台
 */
@Service("platformService")
public class PlatFormServiceImpl implements PlatFormService{

    private static Logger logger = LoggerFactory.getLogger(PlatFormServiceImpl.class);
    @Autowired
    private PlatFormMapper platFormMapper;
    @Autowired
    private OrganizationMapper organizationMapper;


    /*
     * 分页查询
     */
    @Override
    public Object platFormList(PageInfo pageInfo) {
        Map<String, Object> condition = new HashMap<>();
        int offset = pageInfo.getNowpage();
        int pageSize = pageInfo.getPagesize();
        condition = pageInfo.getCondition();
        condition.put("pageNumBegin", (offset - 1) * pageSize + 1);
        if (offset <= 1) {
            condition.put("pageNumStop", pageSize);
        } else {
            condition.put("pageNumStop", offset * pageSize);
        }
        int size = platFormMapper.countPlatForm(condition);

        List<PlatForm> configShareList = platFormMapper.selectPlatForm(condition);
        pageInfo.setRows((ArrayList) configShareList);
        pageInfo.setTotal(size);
        return pageInfo;
    }

    @Override
    public PlatForm selectByPrimaryKey(String id) {
        PlatForm platForm = platFormMapper.selectByPrimaryKey(id);
        if (platForm == null) {
            return platForm;
        }
        return platForm;
    }

    /*
     * 添加（状态值默认为1）
     */
    @Override
    public boolean insertPlatForm(PlatForm platForm) {
        if(StringUtils.isEmpty(platForm.getId()))platForm.setId(platFormMapper.seqId()+"");
        if(1 == platFormMapper.insertSelective(platForm)) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * 删除
     * （编辑此条数据的状态）
     */
    @Override
    public int updateByPrimaryKeySelective(PlatForm record) {
        record.setStatus(Status.STATUS_0.status);
        return platFormMapper.updateByPrimaryKeySelective(record);
    }

    /*
     * 编辑
     */
    @Override
    public int updateByPrimaryKey(PlatForm record) {
        return platFormMapper.updateByPrimaryKey(record);
    }


    @Override
    public PlatForm selectByPlatformNum(String platformNum){
        return platFormMapper.selectByPlatFormNum(platformNum);
    }

    @Override
    public PlatformType byPlatformCode(String platformCode) {
        PlatFormExample example = new PlatFormExample();
        example.or().andPlatformNumEqualTo(platformCode).andStatusEqualTo(Status.STATUS_1.status);
        List<PlatForm> list = platFormMapper.selectByExample(example);
        if (list.size() > 0) {
            PlatForm p = list.get(0);
            return PlatformType.getContentEnum(p.getPlatformType());
        }
        return null;
    }


    /**
     * 获取机构下拉数据
     * @param platId 业务平台id
     * @param orgParent 机构上级（机构上级为空的就是顶级机构）
     * @param orgType 机构类型（不等于虚拟机构）
     * @return
     */
    @Override
    public List<Organization> queryByOrganName(String platId, String orgParent, String orgType) {
        logger.info("获取业务所对应的机构：", platId, orgParent, orgType);
        List<Organization> organizationList = organizationMapper.queryByOrganName(
                FastMap.fastMap("platId", platId)
                );
        logger.info("queryByOrganName().organizationList：", organizationList);
        return organizationList;
    }

    /**
     * 获取业务平台路径
     * @param platId
     * @return
     */
    @Override
    public PlatForm queryByPlatId(String platId) {
        logger.info("获取业务平台路径：", platId);
        PlatFormExample platFormExample = new PlatFormExample();
        platFormExample.createCriteria()
                .andStatusEqualTo(Status.STATUS_1.status)
                .andPlatformNumEqualTo(platId);
        List<PlatForm> platFormList = platFormMapper.selectByExample(platFormExample);
        logger.info("queryByPlatId().platFormList：", platFormList);
        return platFormList.get(0);
    }

}
