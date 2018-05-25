package com.ryx.credit.service.dict;

import com.ryx.credit.pojo.admin.agent.Attachment;

import java.util.List;

/**
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/5/25 9:33
 */
public interface MultiFileUploadService {

    List<Attachment> saveMultiFile(List<Attachment> attachmentList);
}
