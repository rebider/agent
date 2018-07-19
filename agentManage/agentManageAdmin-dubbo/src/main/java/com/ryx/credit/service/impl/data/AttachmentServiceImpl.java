package com.ryx.credit.service.impl.data;

import com.ryx.credit.pojo.admin.agent.AttachmentRel;
import com.ryx.credit.service.data.AttachmentService;
import com.ryx.credit.common.enumc.AttachmentRelType;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.dao.agent.AttachmentMapper;
import com.ryx.credit.pojo.admin.agent.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("attachmentService")
public class AttachmentServiceImpl implements AttachmentService {
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Override
    public PageInfo selectAll(Page page, Attachment attachment,AttachmentRel attachmentRel) {
        Map<String, Object> map = new HashMap<>();
        if (null!=attachment.getAttName() && !attachment.getAttName().equals("")){
            map.put("attName",attachment.getAttName());
        }
        if (null!=attachmentRel.getBusType()&& !attachmentRel.getBusType().equals("")){
            map.put("busType",attachmentRel.getBusType());
        }
        List<Map<String,Object>> attachmentList=  attachmentMapper.selectAll(map,page);
        for (Map<String, Object> maps : attachmentList) {
           maps.put("BUS_TYPE", AttachmentRelType.getItemsString(String.valueOf(maps.get("BUS_TYPE"))));
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(attachmentList);
        pageInfo.setTotal(attachmentMapper.getCount(map));
        return pageInfo;
    }
}
