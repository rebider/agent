package com.ryx.credit.cms.controller;

import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.commons.result.Result;
import com.ryx.credit.commons.shiro.ShiroUser;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 *
 * 基础controller
 *
 * @author wangqi
 * @version 1.0
 * @date 2015年8月5日 下午14:18:17
 * @since 1.0
 */
@ControllerAdvice
public class BaseController {
//    @Autowired
//    private ProcessService processService;
    /**
     * log日志
     */
    private static final Logger log = Logger.getLogger(BaseController.class);
    public TreeMap getRequestParameter(HttpServletRequest request){
        Map<String, String[]> hashMap = request.getParameterMap();
        TreeMap<String, String> treeMap = new TreeMap();
        for (String key : hashMap.keySet()) {
            if (StringUtils.isBlank(key)) continue;
            treeMap.put(key, hashMap.get(key)[0]);
            log.info("parameters--"+key+":"+hashMap.get(key)[0]);
        }
        return  treeMap;
    }


    /**
     * 处理分页用到的信息
     * @param req 需要从request中获取数据
     * @return
     */
    protected Page pageProcess(HttpServletRequest req) {
        int numPerPage = null==req.getParameter("rows")?20:Integer.parseInt(req.getParameter("rows"));
        int currentPage = null==req.getParameter("page")?1:Integer.parseInt(req.getParameter("page"));
        Page page = new Page();
        page.setCurrent(currentPage);
        page.setLength(numPerPage);
        page.setBegin((currentPage-1)*numPerPage);
        page.setEnd(currentPage*numPerPage);
        return page;
    }

    /**
     * 处理分页用到的信息
     * @param req 需要从request中获取数据
     * @return
     */
    protected Page pageProcessAll(HttpServletRequest req,int size) {
        int numPerPage = null==req.getParameter("rows")?size:Integer.parseInt(req.getParameter("numPerPage"));
        int currentPage = null==req.getParameter("page")?1:Integer.parseInt(req.getParameter("pageNum"));
        Page page = new Page();
        page.setCurrent(currentPage);
        page.setLength(numPerPage);
        page.setBegin((currentPage-1)*numPerPage);
        page.setEnd(currentPage*numPerPage);
        return page;
    }

    /**
     * 处理分页用到的信息
     * @param req 需要从request中获取数据
     * @return
     */
    protected Page pageProcessAll(int size) {
        int numPerPage = size;
        int currentPage = 1;
        Page page = new Page();
        page.setCurrent(currentPage);
        page.setLength(numPerPage);
        page.setBegin((currentPage-1)*numPerPage);
        page.setEnd(currentPage*numPerPage);
        return page;
    }

    public List<String> uploadFiles(HttpServletRequest request,String userId){
        List list =new ArrayList();
        try {
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                Iterator<String> iter = multiRequest.getFileNames();
                int i =1;
                while (iter.hasNext()) {
                    //记录上传过程起始时的时间，用来计算上传时间
                    int pre = (int) System.currentTimeMillis();
                    //取得上传文件
                    MultipartFile file = multiRequest.getFile(iter.next());
                    if (file != null) {
                        //取得当前上传文件的文件名称
                        String myFileName = file.getOriginalFilename();
                        //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                        if (myFileName.trim() != "") {
                            String path_dir = AppConfig.getProperty("upload_path") + userId + "/" + DateUtils.dateToString(new
                                            Date()) + i+"/";
                            String path = path_dir+myFileName;
                            File localFile = new File(AppConfig.getProperty("USER_BASE_PATH") +path);
                            if (!localFile.getParentFile().exists()) {
                                //如果目标文件所在的目录不存在，则创建父目录
                                log.info("目标文件所在目录不存在，准备创建它！");
                                if (!localFile.getParentFile().mkdirs()) {
                                    log.info("创建目标文件所在目录失败！");
                                }
                            }
//                            try {
//                                TransferFileToFtp.transferToFtp(file.getInputStream(), file.getOriginalFilename(), path_dir);
//                                //给上传后的图片加读权限
//                                processService.excuteProcess755(CrowdfundUtil.getPropertiesByName("config.properties", "ng.static"));
//                            } catch (Exception e) {
//                                log.error("TransferFileToFtp",e);
//                            }
                            file.transferTo(localFile);
                            list.add(path);
                            i++;
                            //todo FTP上传

                        }
                    }
                    //记录上传该文件后的时间
                    int finaltime = (int) System.currentTimeMillis();
                    System.out.println(finaltime - pre);
                }

            }
        } catch (Exception e) {
            log.error("uploadFiles error",e);
        }
        return  list;
    }

    public Object renderSuccess(String msg) {
        Result result = new Result();
        result.setSuccess(true);
        result.setMsg(msg);
        return result;
    }
    
    public Object renderError(String msg) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMsg(msg);
        return result;
    }

    /**
     * code
     * 100:长度异常
     * 200:正常
     * 300:缺失要素
     * 400:格式错误
     * 500:违反唯一约束
     */
    public Object renderResult(String code,String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
    
    /**
     * 获取当前登录用户对象
     * @return {ShiroUser}
     */
    public ShiroUser getShiroUser() {
        return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取当前登录用户id
     * @return {Long}
     */
    public Long getUserId() {
        return this.getShiroUser().getId();
    }

    /**
     * 获取当前登录用户名
     * @return {String}
     */
    public String getStaffName() {
        return this.getShiroUser().getName();
    }
}
