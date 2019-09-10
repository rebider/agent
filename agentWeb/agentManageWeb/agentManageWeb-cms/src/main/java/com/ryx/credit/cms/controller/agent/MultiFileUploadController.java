package com.ryx.credit.cms.controller.agent;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.IDUtils;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.pojo.admin.agent.Attachment;
import com.ryx.credit.service.dict.MultiFileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 多文件上传工具类
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/5/24 16:31
 */
@Controller
@RequestMapping("multiFile")
public class MultiFileUploadController extends BaseController{

    private static Logger log  = LoggerFactory.getLogger(MultiFileUploadController.class);

    @Autowired
    private MultiFileUploadService multiFileUploadService;

    @RequestMapping("toUploadPage")
    public String toUploadPage(HttpServletRequest request){
        request.setAttribute("attDataType",request.getParameter("attDataType"));
        return "common/uploadList";
    }

    @RequestMapping("uploadFile")
    @ResponseBody
    public Object uploadFile(@RequestParam(value = "file", required = false)MultipartFile[]  files, HttpServletRequest request){

        List<Attachment> attachmentList = new ArrayList<>();
        for(int i = 0;i<files.length;i++){
            Attachment attachment = new Attachment();
            MultipartFile file = files[i];
            attachment.setAttSize(new BigDecimal(file.getSize()));
            String filename = file.getOriginalFilename();
            attachment.setAttName(filename);
            attachment.setAttType( filename.substring(filename.lastIndexOf(".")+1));
            String filePath = uploadPhoto(file, String.valueOf(getUserId()));
            attachment.setAttDbpath(filePath);
            attachment.setAttDataType(request.getParameter("attDataType"));
            attachment.setcUser(String.valueOf(getUserId()));
            attachmentList.add(attachment);
        }
        List<Attachment> attachments = multiFileUploadService.saveMultiFile(attachmentList);
        if(attachments==null || attachments.size()==0){
            return null;
        }
        String resultJson = JsonUtil.objectToJson(attachments);
        return resultJson;
    }

}
