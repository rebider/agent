package com.ryx.credit.cms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.activity.entity.ActHiVarinst;
import com.ryx.credit.cms.util.IDUtils;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.ApprovalType;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.enumc.NotifyType;
import com.ryx.credit.common.enumc.RedisCachKey;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.result.Result;
import com.ryx.credit.commons.shiro.ShiroUser;

import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.CuserAgent;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.agent.DictKey;
import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.pojo.admin.vo.UserVo;
import com.ryx.credit.service.ActHiVarinstService;
import com.ryx.credit.service.ICuserAgentService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.ApprovalFlowRecordService;
import com.ryx.credit.service.agent.BusActRelService;
import org.activiti.engine.identity.User;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.Account;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

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


    private String PICTURE_PATH = AppConfig.getProperty("picture.path");
    private String EXCEL_PATH = AppConfig.getProperty("excel.path");
    private String PICTURE_IMG_PATH = AppConfig.getProperty("picture.img.path");

    @Autowired
    private BusActRelService busActRelService;
    @Autowired
    private ActHiVarinstService actHiVarinstService;
    @Autowired
    private IUserService  iUserService;
    @Autowired
    private ICuserAgentService cuserAgentService;
    @Autowired
    private ApprovalFlowRecordService approvalFlowRecordService;


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

    protected static Page pageProcess(Integer currentPage, Integer pageSize) {
        Page page = new Page();
        page.setCurrent(currentPage);
        page.setLength(pageSize);
        page.setBegin((currentPage-1)*pageSize);
        page.setEnd(currentPage*pageSize);
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
     * @param size 需要从request中获取数据
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

    /**
     * ajax成功
     * @return {Object}
     */
    public Object renderSuccess() {
        Result result = new Result();
        result.setSuccess(true);
        return result;
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
        Object shiroUser = SecurityUtils.getSubject().getPrincipal();
        if(shiroUser==null){
            throw new UnauthenticatedException();
        }else{
            return (ShiroUser)shiroUser;
        }
    }

    /**
     * 获取当前登录用户id
     * @return {Long}
     */
    public Long getUserId() {
        ShiroUser shiroUser = this.getShiroUser();
        return shiroUser.getId();

    }

    /**
     * 获取当前登录用户id
     * @return {Long}
     */
    public String getStringUserId() {
        return String.valueOf(this.getShiroUser().getId());
    }


    protected String getUserName(Long userId){
        if(userId==null)return "";
        String name = ServiceFactory.redisService.hGet(RedisCachKey.USER_NAMES.code,userId+"");
        if(StringUtils.isEmpty(name)){
            UserVo uv = iUserService.selectVoById(userId);
            ServiceFactory.redisService.hSet(RedisCachKey.USER_NAMES.code,uv.getId()+"",uv.getName());
            return uv.getName();
        }else{
            return name;
        }
    }

    /**
     * @Author: Zhang Lei
     * @Description: 获取代理商ID
     * @Date: 18:51 2018/8/1
     */
    public String getAgentId(){
        String agentId = null;
        agentId =  ServiceFactory.redisService.hGet("agent",getUserId()+"");
        if(StringUtils.isEmpty(agentId)) {
            CuserAgent cuserAgent = cuserAgentService.selectByUserId(getStringUserId());
            if(null!=cuserAgent){
                ServiceFactory.redisService.hSet("agent",cuserAgent.getUserid(),cuserAgent.getAgentid());
                agentId = cuserAgent.getAgentid();
            }
        }
        return agentId;
    }

    /**
     * 获取当前登录用户名
     * @return {String}
     */
    public String getStaffName() {
        return this.getShiroUser().getName();
    }


    /**
     * 上传附件
     * @param file
     * @param merchId
     * @return
     */
    public String uploadPhoto(MultipartFile file, String merchId) {
        //上传服务器
        log.info("start uploadPhoto");
        String savePath = "";
        String imgPath = "";
        try {
            String fileName = file.getOriginalFilename();
            String rootPath = PICTURE_PATH;
            String picturePath = createStorePath(rootPath, merchId);
            String onlyId = IDUtils.genImageName();
            String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            //保存附件
            savePath = rootPath + picturePath + File.separator + onlyId + suffix;
            log.info("上传附件 save root path：" + savePath);

            imgPath = picturePath + File.separator + onlyId + suffix;
            file.transferTo(new File(savePath));
            log.info("上传附件 服务器路径：" + savePath);
        } catch (Exception e) {
            log.info("io异常");
        }
        return imgPath.replace("\\","/");
    }

    private static String createStorePath(String servicePath, String path) {
        String yearDay = com.ryx.credit.common.util.DateUtil.getDays();
        String picturePath = "files" + File.separator + yearDay.substring(0, 4) + File.separator + yearDay.substring(4, 6) + File.separator + yearDay.substring(6, 8) + File.separator + path;
        String lastPath = servicePath + picturePath;
        File file = new File (lastPath);
        if(!file.exists()) {
            file.mkdirs();
        }
        return picturePath;
    }

    public String uploadExcel(MultipartFile file,String excelName) throws MessageException{
        //上传服务器
        String savePath = "";
        try {
            String fileName = file.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            if(!suffix.equals(".xlsx")){
                throw new MessageException("只支持导入xlsx格式文件");
            }
            //保存附件
            File files = new File (EXCEL_PATH);
            if(!files.exists()) {
                files.mkdirs();
            }
            savePath = EXCEL_PATH + File.separator + excelName + suffix;
            log.info("上传附件 save root path：" + savePath);
            file.transferTo(new File(savePath));
            log.info("上传附件 服务器路径：" + savePath);
        } catch (Exception e) {
            e.getStackTrace();
            log.info("io异常");
        }
        return savePath.replace("\\","/");
    }


    /**
     * list 转 map
     * @param dictList
     * @return
     */
    public static Map<String,String> getDict2Map(List<Dict>dictList){
        return dictList.stream().collect(Collectors.toMap(DictKey::getdItemvalue,Dict::getdItemname));
    }

    public static Map<String,String> getPlatFormMap(List<PlatForm>dictList){
        return dictList.stream().collect(Collectors.toMap(PlatForm::getPlatformNum,PlatForm::getPlatformName));
    }

    public static Map<String,String> getPlatForm2Map(List<PlatForm>dictList){
        return dictList.stream().collect(Collectors.toMap(PlatForm::getPlatformNum,PlatForm::getPlatformName));
    }
    /**
     * 下拉选项
     * @param request
     */
    public Map optionsData(HttpServletRequest request){
        JSONObject obj = null ;
        try{

            if(ServiceFactory.dictGroup!=null){
                try {
                    obj = ServiceFactory.dictGroup;
                    Set<String>  set = obj.keySet();
                    Iterator<String> ite = set.iterator();
                    while (ite.hasNext()){
                        String key = ite.next();
                        request.setAttribute(key,obj.get(key));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    obj = null;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null==obj) {

                //选项数据
                List<Dict> certType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.CERT_TYPE.name());
                List<Dict> agNatureType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.AGNATURE_TYPE.name());
                List<Dict> capitalType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.CAPITAL_TYPE.name());
                List<Dict> contractType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.CONTRACT_TYPE.name());
                List<Dict> colInfoType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.COLINFO_TYPE.name());
                List<Dict> yesOrNo = ServiceFactory.dictOptionsService.dictList(DictGroup.ALL.name(), DictGroup.YESORNO.name());
                List<Dict> yesOrNoIsYes = ServiceFactory.dictOptionsService.dictList(DictGroup.ALL.name(), DictGroup.YESORNOISYES.name());
                List<Dict> busType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.BUS_TYPE.name());
                List<Dict> approvalType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT_AUDIT.name(), DictGroup.APPROVAL_TYPE.name());
                List<Dict> agentInStatus = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.AGENT_IN_STATUS.name());
                List<PlatForm> platFormList = ServiceFactory.businessPlatformService.queryAblePlatForm();
                List<Dict> agStatuss = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.AG_STATUS_S.name());
                List<Dict> agStatusi = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.AG_STATUS_I.name());
                List<Dict> approvalPassType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT_AUDIT.name(), DictGroup.APPROVAL_PASS_TYPE.name());
                List<Dict> busStatus = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.BUS_STATUS.name());
                List<Dict> data_change_type = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.DATA_CHANGE_TYPE.name());
                List<Dict> busActRelBustype = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.BUS_ACT_REL_BUSTYPE.name());
                List<Dict> useScope = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.USE_SCOPE.name());
                List<Dict> busScope = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.BUS_SCOPE.name());
                List<Dict> payType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.PAY_TYPE.name());

                List<Dict> orderStatus = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.ORDER_STATUS.name());
                List<Dict> settlementType = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.SETTLEMENT_TYPE.name());
                List<Dict> paymenttype = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.PAYMENTTYPE.name());
                List<Dict> paymentstatus = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.PAYMENTSTATUS.name());
                List<Dict> cType = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.C_TYPE.name());
                List<Dict> paymentSrcTypes = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.PAYMENT_SRC_TYPE.name());
                Dict passDict = ServiceFactory.dictOptionsService.findDictByValue(DictGroup.AGENT_AUDIT.name(), DictGroup.APPROVAL_TYPE.name(), ApprovalType.PASS.getValue());
                Dict rejectDict = ServiceFactory.dictOptionsService.findDictByValue(DictGroup.AGENT_AUDIT.name(), DictGroup.APPROVAL_TYPE.name(), ApprovalType.REJECT.getValue());
                Dict cancelDict = ServiceFactory.dictOptionsService.findDictByValue(DictGroup.AGENT_AUDIT.name(), DictGroup.APPROVAL_CANCEL_TYPE.name(), ApprovalType.CANCEL.getValue());
                Dict backDict = ServiceFactory.dictOptionsService.findDictByValue(DictGroup.AGENT_AUDIT.name(), DictGroup.APPROVAL_TYPE.name(), ApprovalType.BACK.getValue());
                List<Dict> orgType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.ORG_TYPE.name());
                List<Dict> reportStatus = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.REPORT_STATUS.name());
                List<Dict> allApprovalType = new ArrayList<>();
                allApprovalType.addAll(approvalType);//添加退出、通过、退回
                allApprovalType.add(cancelDict);//添加撤销

                String notifyTypeJson = NotifyType.getKeyValueJson();

                request.setAttribute("orderStatus",orderStatus);
                request.setAttribute("settlementType",settlementType);
                request.setAttribute("paymentType",paymenttype);
                request.setAttribute("paymentStatus",paymentstatus);
                request.setAttribute("paymentSrcTypes",paymentSrcTypes);


                request.setAttribute("dataChangeType",data_change_type);
                request.setAttribute("ablePlatForm", ServiceFactory.businessPlatformService.queryAblePlatForm());
                request.setAttribute("compList", ServiceFactory.apaycompService.compList());
                request.setAttribute("certType", certType);
                request.setAttribute("agNatureType", agNatureType);
                request.setAttribute("capitalType", capitalType);//交款类型
                request.setAttribute("contractType", contractType);
                request.setAttribute("colInfoType", colInfoType);//收款账户类型
                request.setAttribute("yesOrNo", yesOrNo);
                request.setAttribute("yesOrNoIsYes", yesOrNoIsYes);
                request.setAttribute("busType", busType);
                request.setAttribute("approvalType", approvalType);
                request.setAttribute("allApprovalType", allApprovalType);
                request.setAttribute("agentInStatus", agentInStatus);
                request.setAttribute("platFormList", platFormList);
                request.setAttribute("agStatuss", agStatuss);
                request.setAttribute("agStatusi", agStatusi);
                request.setAttribute("approvalPassType", approvalPassType);
                request.setAttribute("busStatus", busStatus);
                request.setAttribute("dataChangeType", data_change_type);
                request.setAttribute("busActRelBustype", busActRelBustype);
                request.setAttribute("useScope", useScope);
                request.setAttribute("busScope", busScope);
                request.setAttribute("cType", cType);
                request.setAttribute("notifyTypeJson", notifyTypeJson);
                request.setAttribute("payType", payType);
                request.setAttribute("passDict", passDict);
                request.setAttribute("rejectDict", rejectDict);
                request.setAttribute("cancelDict", cancelDict);
                request.setAttribute("backDict", backDict);
                request.setAttribute("orgType",orgType);
                request.setAttribute("reportStatusList",reportStatus);
                FastMap data = FastMap.fastMap("ablePlatForm", ServiceFactory.businessPlatformService.queryAblePlatForm())
                        .putKeyV("certType", certType)
                        .putKeyV("agNatureType", agNatureType)
                        .putKeyV("capitalType", capitalType)
                        .putKeyV("contractType", contractType)
                        .putKeyV("colInfoType", colInfoType)
                        .putKeyV("yesOrNo", yesOrNo)
                        .putKeyV("yesOrNoIsYes", yesOrNoIsYes)
                        .putKeyV("busType", busType)
                        .putKeyV("approvalType", approvalType)
                        .putKeyV("allApprovalType", allApprovalType)
                        .putKeyV("agentInStatus", agentInStatus)
                        .putKeyV("platFormList", platFormList)
                        .putKeyV("agStatuss", agStatuss)
                        .putKeyV("agStatusi", agStatusi)
                        .putKeyV("busStatus", busStatus)
                        .putKeyV("compList", ServiceFactory.apaycompService.compList())
                        .putKeyV("dataChangeType", data_change_type)
                        .putKeyV("busActRelBustype", busActRelBustype)
                        .putKeyV("approvalPassType",approvalPassType)
                        .putKeyV("useScope",useScope)
                        .putKeyV("busScope",busScope)
                        .putKeyV("orderStatus", orderStatus)
                        .putKeyV("settlementType",settlementType)
                        .putKeyV("paymentType",paymenttype)
                        .putKeyV("cType",cType)
                        .putKeyV("paymentSrcTypes",paymentSrcTypes)
                        .putKeyV("paymentStatus",paymentstatus)
                        .putKeyV("notifyTypeJson",notifyTypeJson)
                        .putKeyV("payType",payType)
                        .putKeyV("passDict",passDict)
                        .putKeyV("rejectDict",rejectDict)
                        .putKeyV("cancelDict",cancelDict)
                        .putKeyV("backDict",backDict)
                        .putKeyV("orgType",orgType)
                        .putKeyV("reportStatusList",reportStatus)

                        .putKeyV("certType_map",getDict2Map(certType))
                        .putKeyV("agNatureType_map",getDict2Map(agNatureType))
                        .putKeyV("capitalType_map",getDict2Map(capitalType))
                        .putKeyV("contractType_map",getDict2Map(contractType))
                        .putKeyV("colInfoType_map",getDict2Map(colInfoType))
                        .putKeyV("yesOrNo_map",getDict2Map(yesOrNo))
                        .putKeyV("yesOrNoIsYes_map",getDict2Map(yesOrNoIsYes))
                        .putKeyV("busType_map",getDict2Map(busType))
                        .putKeyV("approvalType_map",getDict2Map(approvalType))
                        .putKeyV("allApprovalType_map",getDict2Map(allApprovalType))
                        .putKeyV("agentInStatus_map",getDict2Map(agentInStatus))
                        .putKeyV("platFormList_map",getPlatFormMap(platFormList))
                        .putKeyV("agStatuss_map",getDict2Map(agStatuss))
                        .putKeyV("agStatusi_map",getDict2Map(agStatusi))
                        .putKeyV("approvalPassType_map",getDict2Map(approvalPassType))
                        .putKeyV("busStatus_map",getDict2Map(busStatus))
                        .putKeyV("data_change_type_map",getDict2Map(data_change_type))
                        .putKeyV("busActRelBustype_map",getDict2Map(busActRelBustype))
                        .putKeyV("useScope_map",getDict2Map(useScope))
                        .putKeyV("busScope_map",getDict2Map(busScope))
                        .putKeyV("payType_map",getDict2Map(payType))
                        .putKeyV("orderStatus_map",getDict2Map(orderStatus))
                        .putKeyV("settlementType_map",getDict2Map(settlementType))
                        .putKeyV("paymenttype_map",getDict2Map(paymenttype))
                        .putKeyV("paymentstatus_map",getDict2Map(paymentstatus))
                        .putKeyV("cType_map",getDict2Map(cType))
                        .putKeyV("paymentSrcTypes_map",getDict2Map(paymentSrcTypes))
                        .putKeyV("reportStatus_map",getDict2Map(reportStatus));

                ServiceFactory.dictGroup=JSONObject.parseObject(JSONObject.toJSONString(data));
                return data;
            }else{
                return obj;
            }
        }

    }

    public Map orderDictData(HttpServletRequest request){
        JSONObject obj = null ;
        try{
            if(ServiceFactory.orderDictGroup!=null){
                try {
                    obj = ServiceFactory.orderDictGroup;
                    Set<String>  set = obj.keySet();
                    Iterator<String> ite = set.iterator();
                    while (ite.hasNext()){
                        String key = ite.next();
                        request.setAttribute(key,obj.get(key));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    obj = null;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null==obj) {
                //选项数据
                List<Dict> payType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.PAY_TYPE.name());
                List<Dict> modelType = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.MODEL_TYPE.name());
                List<Dict> manufacturer = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name());
                List<Dict> proMode = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.PROMODE.name());
                List<PlatForm> ablePlatForm = ServiceFactory.businessPlatformService.queryAblePlatForm();
                List<Dict> activityDisType = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.ACTIVITY_DIS_TYPE.name());
                List<Dict> activityCondition = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.ACTIVITY_CONDITION.name());
                request.setAttribute("modelType", modelType);
                request.setAttribute("manufacturer", manufacturer);
                request.setAttribute("ablePlatForm",ablePlatForm);
                request.setAttribute("activityDisType",activityDisType);
                request.setAttribute("activityCondition",activityCondition);
                request.setAttribute("proMode", proMode);
                request.setAttribute("payType", payType);
                FastMap data = FastMap.fastMap("modelType",modelType)
                        .putKeyV("ablePlatForm",ablePlatForm)
                        .putKeyV("activityDisType",activityDisType)
                        .putKeyV("manufacturer",manufacturer)
                        .putKeyV("proMode",proMode)
                        .putKeyV("payType",payType)
                        .putKeyV("activityCondition",activityCondition)


                        .putKeyV("payType_map",getDict2Map(payType))
                        .putKeyV("modelType_map",getDict2Map(modelType))
                        .putKeyV("manufacturer_map",getDict2Map(manufacturer))
                        .putKeyV("proMode_map",getDict2Map(proMode))
                        .putKeyV("ablePlatForm_map",getPlatForm2Map(ablePlatForm))
                        .putKeyV("activityDisType_map",getDict2Map(activityDisType))
                        .putKeyV("activityCondition_map",getDict2Map(activityCondition));
                ServiceFactory.orderDictGroup=JSONObject.parseObject(JSONObject.toJSONString(data));
                return data;
            }else{
                return obj;
            }
        }

    }


    /**
     * 参数：业务编号，业务类型
     * 查询审批记录
     */
    public List<Map<String,Object>> queryApprovalRecord(String busId,String BusType){
        BusActRel busActRel =  busActRelService.findByBusIdAndType(busId,BusType);
        List<Map<String,Object>> actRecordList = new ArrayList<>();
        if(busActRel==null) return actRecordList;
        actRecordList = approvalFlowRecordService.queryFlowByExecutionId(busActRel.getActivId());
        return actRecordList;
    }

    /**
     * 参数：实例Id
     * 查询审批记录
     * @param proIns
     */
    public List<Map<String,Object>> queryApprovalRecord(String proIns){
        List<Map<String, Object>> actRecordList = approvalFlowRecordService.queryFlowByExecutionId(proIns);
        return actRecordList;
    }


    @ExceptionHandler({UnauthenticatedException.class})
    public ModelAndView  authenticationInfoException(HttpServletRequest request, HttpServletResponse response){
        String type = request.getHeader("x-requested-with");
        if(StringUtils.isNotEmpty(type) && "XMLHttpRequest".equals(type)) {
            response.setStatus(org.springframework.http.HttpStatus.UNAUTHORIZED.value());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            try (PrintWriter out = response.getWriter()) {
                String data = JSON.toJSONString(new ResultVO("401", "登录已过期，请重新登录"));
                out.append(data);
            } catch (IOException e) {
                e.printStackTrace();
                log.error("response401:" + e.getMessage());
            }
            return null;
        }else{
            try {
                response.sendRedirect("/login?ss=1");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
