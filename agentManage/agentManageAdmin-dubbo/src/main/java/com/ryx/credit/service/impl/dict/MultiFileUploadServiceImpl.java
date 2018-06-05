package com.ryx.credit.service.impl.dict;

import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.dao.agent.AttachmentMapper;
import com.ryx.credit.pojo.admin.agent.Attachment;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.dict.MultiFileUploadService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 文件上传
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/5/25 9:29
 */
@Service("multiFileUploadService")
public class MultiFileUploadServiceImpl implements MultiFileUploadService {

    private static final Logger log = Logger.getLogger(RegionServiceImpl.class);
    @Autowired
    private IdService idService;
    @Autowired
    private AttachmentMapper attachmentMapper;

    @Override
    public List<Attachment> saveMultiFile(List<Attachment> attachmentList){

        if(attachmentList.size()==0){
            return null;
        }
        try {
            for (Attachment attachment : attachmentList) {
                attachment.setId(idService.genId(TabId.a_attachment));
                attachment.setcTime(new Date());
                attachment.setStatus(Status.STATUS_1.status);
                attachmentMapper.insertSelective(attachment);
            }
        } catch (Exception e) {
            log.info("添加附件异常:"+e.getMessage());
            e.printStackTrace();
        }
        return attachmentList;
    }
}
