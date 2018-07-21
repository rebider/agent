package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.dao.agent.PlatFormMapper;
import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.service.agent.PlatFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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

    public final static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    private PlatFormMapper platFormMapper;

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
        if(1 == platFormMapper.insertPlatForm(platForm)) {
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
        record.setStatus(new BigDecimal(String.valueOf(Status.STATUS_0.status)));
        return platFormMapper.updateByPrimaryKeySelective(record);
    }

    /*
     * 编辑
     */
    @Override
    public int updateByPrimaryKey(PlatForm record) {
        return platFormMapper.updateByPrimaryKey(record);
    }

}
