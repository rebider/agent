package com.ryx.credit.cms.controller.order;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ImportExcelUtil;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.ExcelUtil;
import com.ryx.credit.service.order.SplitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 拆分新老订单
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/4/25 16:03
 * @Param
 * @return
 **/
@RequestMapping("split")
@Controller
public class SplitController  extends BaseController {

    @Autowired
    private SplitService splitService;

    /**
     * 补差价
     * @param request
     * @return
     */
    @RequestMapping("toUploadFileCompensatePage")
    public String toUploadFileCompensatePage(HttpServletRequest request){
        requestAttr(request);
        return "order/uploadFile";
    }

    /**
     * 退货入口
     * @param request
     * @return
     */
    @RequestMapping("toUploadFileReturnOrderPage")
    public String toUploadFileReturnOrderPage(HttpServletRequest request){
        requestAttr(request);
        return "order/uploadFile";
    }

    private void requestAttr(HttpServletRequest request){
        request.setAttribute("type",request.getParameter("type"));
        request.setAttribute("isAgent",getAgentId());
    }


    @RequestMapping(value ="analysisFile")
    @ResponseBody
    public Object analysisFile(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
        if(file.getSize()==0){
            return renderError("请上传文件");
        }
        try {
            InputStream in = file.getInputStream();
            List<List<Object>> excelList = ImportExcelUtil.getListByExcel(in, file.getOriginalFilename());
            if(null==excelList || excelList.size()==0){
                return renderError("文档记录为空");
            }
            List<Map<String, Object>> orderMsgByExcel = splitService.getOrderMsgByExcel(excelList,getStringUserId());
            return orderMsgByExcel;
        } catch (Exception e) {
            e.printStackTrace();
            return renderError(e.getLocalizedMessage());
        }
    }

    @RequestMapping("/exportSplit")
    public void exportSplit(HttpServletRequest request, HttpServletResponse response) throws MessageException {
        String exportType = request.getParameter("exportType");
        List<Map<String, Object>> maps = splitService.exportSplit(exportType, getStringUserId());
        String title = "SN开始,SN结束,数量,机型";
        String column = "startSn,endSn,num,proModel";
        Map<String, Object> param = new HashMap<>(5);
        param.put("path", AppConfig.getProperty("export.path"));
        param.put("title", title);
        param.put("column", column);
        param.put("dataList", maps);
        param.put("response", response);
        ExcelUtil.downLoadExcel(param);
    }

}
