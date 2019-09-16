package com.ryx.credit.cms.controller.profit;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ImportExcelUtil;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.profit.pojo.PCityApplicationDetail;
import com.ryx.credit.profit.pojo.ProfitSupply;
import com.ryx.credit.profit.service.IProfitCityApplicationService;
import com.ryx.credit.profit.service.ProfitSupplyService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.agent.TaskApprovalService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;

import static java.lang.String.valueOf;

/**
 * @Author Lihl
 * @Date 2018/08/02
 * 分润管理：补款数据维护
 */
@Controller
@RequestMapping("/profitSupply")
public class ProfitSupplyController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ProfitSupplyController.class);

    @Resource
    private ProfitSupplyService profitSupplyService;
    @Autowired
    private IProfitCityApplicationService profitCityApplicationService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private IUserService userService;
    @Autowired
    private AgentService agentService;

    private static final String AGENT = "agent";


    @RequestMapping(value = "pageList")
    public String profitSupplyPage(Model model) {
        logger.info("加载补款数据维护页面...");
        // 终审后不能做修改
        String finalStatus = ServiceFactory.redisService.getValue("commitFinal");
        if (StringUtils.isBlank(finalStatus)) {
            model.addAttribute("noEdit","0");
        }else{
            model.addAttribute("noEdit","1");
        }
        return "profit/supply/profitSupplyList";
    }

    @RequestMapping(value = "machineList")
    public String profitMachinePage(Model model) {
        logger.info("加载补款数据维护页面...");
        // 终审后不能做修改
        String finalStatus = ServiceFactory.redisService.getValue("commitFinal");
        if (StringUtils.isBlank(finalStatus)) {
            model.addAttribute("noEdit","0");
        }else{
            model.addAttribute("noEdit","1");
        }
        return "profit/supply/machineCashback";
    }
    @RequestMapping(value = "moneyBackList")
    public String MoneyBackPage(Model model) {
        logger.info("加载补款数据维护页面...");
        // 终审后不能做修改
        String finalStatus = ServiceFactory.redisService.getValue("commitFinal");
        if (StringUtils.isBlank(finalStatus)) {
            model.addAttribute("noEdit","0");
        }else{
            model.addAttribute("noEdit","1");
        }
        return "profit/supply/MoneyBack";
    }

    /**
     * 1、其他补款分页展示
     */
    @RequestMapping(value = "getList")
    @ResponseBody
    public Object getProfitSupplyList(HttpServletRequest request, String sign){
        Page page = pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(), null, null);
        TreeMap map = getRequestParameter(request);

        List<Map<String, Object>>  list = userService.orgCode( getUserId());
        if(list == null || list.isEmpty()){
            return new PageInfo();
        }
        if(list.get(0).get("ORGANIZATIONCODE").toString().contains("north") || list.get(0).get("ORGANIZATIONCODE").toString().contains("south") ){
            map.put("orgId",list.get(0).get("ORGID").toString());  // 省区
        }else if(Objects.equals("south", list.get(0).get("ORGANIZATIONCODE").toString() )|| Objects.equals("north", list.get(0).get("ORGANIZATIONCODE").toString())){
            map.put("daQu",list.get(0).get("ORGID").toString());  // 大区
        }else if(Objects.equals(list.get(0).get("ORGANIZATIONCODE"),AGENT)){
            map.put("AGENT_ID",getAgentId());  //代理商
        }
        map.put("begin", page.getBegin());
        map.put("end", page.getEnd());
        map.put("BUS_BIG_TYPE",sign);
        return profitSupplyService.getProfitSupplyList(map, pageInfo);
    }


    @RequestMapping(value = "profitCount")
    public ModelAndView profitCount(HttpServletRequest request) {
        String agentName=request.getParameter("AGENT_NAME");
        try {
            if (agentName.equals(new String(agentName.getBytes("iso8859-1"),"iso8859-1"))){
                agentName=new String(agentName.getBytes("iso8859-1"),"utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ModelAndView view = new ModelAndView();
        Map<String, Object> param ;
        param=getRequestParameter(request);
        param.put("BUS_BIG_TYPE",request.getParameter("BUS_BIG_TYPE"));
        //其它补款添加了90的大类型
        param.put("sign",request.getParameter("temp"));
        param.put("AGENT_NAME",agentName);
        param.put("AGENT_ID",request.getParameter("AGENT_ID"));
        param.put("SUPPLY_TYPE",request.getParameter("SUPPLY_TYPE"));
        param.put("BUS_TYPE",request.getParameter("BUS_TYPE"));
        param.put("SUPPLY_DATE_START",request.getParameter("SUPPLY_DATE_START"));
        param.put("SUPPLY_DATE_END",request.getParameter("SUPPLY_DATE_END"));
        Map<String,Object> map=profitSupplyService.profitCount(param);
        view.addObject("totalNum",map.get("TOTALNUM"));
        view.addObject("totalMoney",map.get("TOTALMONEY")==null?0:map.get("TOTALMONEY"));
        view.setViewName("profit/profitCount");
        return view;
    }

    /**
     * 1、机具返现分页展示
     */
    @RequestMapping(value = "machineBackList")
    @ResponseBody
    public Object getProfitMachineList(HttpServletRequest request, ProfitSupply profitSupply,String sign){
        String agentPid = ServiceFactory.redisService.hGet("agent",getUserId().toString());
        TreeMap map = getRequestParameter(request);
        Page page = pageProcess(request);
        if (StringUtils.isNotBlank(agentPid)) {
            profitSupply.setAgentPid(agentPid);
            map.put("AGENT_ID",agentPid);
            map.put("AGENT_NAME",getStaffName());
        }
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(), null, null);
        map.put("begin", page.getBegin());
        map.put("end", page.getEnd());
        map.put("BUS_BIG_TYPE",sign);
        return profitSupplyService.getProfitSupplyList(map, pageInfo);
    }


    /**
     * 1、退单补款分页展示
     */
    @RequestMapping(value = "moneyList")
    @ResponseBody
    public Object getProfitMoneyList(HttpServletRequest request, ProfitSupply profitSupply,String sign){
        String agentPid = ServiceFactory.redisService.hGet("agent",getUserId().toString());
        Page page = pageProcess(request);

        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(), null, null);
        TreeMap map = getRequestParameter(request);

        if (StringUtils.isNotBlank(agentPid)) {
            map.put("AGENT_ID",agentPid);
            map.put("AGENT_NAME",getStaffName());
        }
        map.put("begin", page.getBegin());
        map.put("end", page.getEnd());
        map.put("BUS_BIG_TYPE",sign);
        return profitSupplyService.getProfitSupplyList(map, pageInfo);
    }


    /**
     * 机具返现导入页面
     * @return
     */
    @RequestMapping("/machineCashbackImport")
    public String machineCashbackImportPage(){
        return "profit/supply/machineCashbackImport";
    }
    /**
     * 其他补款导入页面展示
     * @return
     */
    @RequestMapping("/importPage")
    public String moneyBackImportImportPage(){
        return "profit/supply/profitSupplyImport";
    }


    /**
     * 补款数据维护:
     * 1、导入补款数据
     */
    @RequestMapping(value = "importFile")
    @ResponseBody
    public ResultVO importFile(@RequestParam(value = "file", required = false)MultipartFile[] files,@RequestParam(value = "sign", required = false)String sign){
        if ("".equals(files[0].getOriginalFilename())||null==files[0].getOriginalFilename()) {
            return ResultVO.fail("文件不能为空");
        }
        try {
            String userid = getUserId() + "";
            for (MultipartFile file : files) {
                String filename = file.getOriginalFilename();
                logger.info("用户{}文件{}", userid, filename);
                Workbook workbook = ImportExcelUtil.getWorkbook(file.getInputStream(), filename);
                try {
                    if (null == workbook) {
                        logger.info("用户{}消息", userid, "创建Excel工作薄为空");
                        return ResultVO.fail("创建Excel工作薄为空");
                    }
                    Sheet sheet = null;
                    Row row = null;
                    Cell cell = null;
                    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                        sheet = workbook.getSheetAt(i);
                        if (sheet == null) {
                            continue;
                        }
                        List list = new ArrayList<List<Object>>();
                        // 遍历当前sheet中的所有行
                        for (int j = sheet.getFirstRowNum(); j < sheet.getLastRowNum() + 1; j++) { // 这里的加一是因为下面的循环跳过取第一行表头的数据内容了
                            row = sheet.getRow(j);
                            if (row == null || row.getFirstCellNum() == j) {
                                continue;
                            }
                            // 遍历所有的列
                            List<Object> li = new ArrayList<Object>();
                            for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                                cell = row.getCell(y);
                                if(null == cell){
                                    li.add("");
                                }else{
                                    li.add(ImportExcelUtil.getCellValue(cell));
                                }
                            }
                            if(li.size()>0) {
                                list.add(li);
                            }else{
                                logger.info("用户{}导入dubbo调用{}, 数据类型{}数据为空", userid, list.size());
                            }
                        }
                        logger.info("用户{}导入dubbo调用{}, 数据类型{}", userid, list.size());
                        List<String> resd = profitSupplyService.importSupplyList(list,sign);
                        logger.info("用户{}导入dubbo调用结果返回{},数据类型{}", userid, resd.size());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("导入补款数据异常：",e.getMessage());
                    return ResultVO.fail(e.getMessage());
                }finally {
                    workbook.close();
                }
            }
            return ResultVO.success("导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "resetData")
    @ResponseBody
    public ResultVO resetData(HttpServletRequest request,String busBigType) {
        int count = profitSupplyService.resetData(busBigType);
        if(count>0){
            return ResultVO.success("清除"+count+"条记录");
        }
        return ResultVO.success("无上月记录");
    }

    /**
     * 查看补款明细
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("queryDetail")
    public String gotoProfitSettleErrList(Model model, String id) {
        logger.info("加载补款明细页面...");
        model.addAttribute("id", id);

        return null;
    }

    /**
     * 省区加载其他补款申请页面
     */
    @RequestMapping(value = "gotoCitySupplementPage")
    public String gotoCitySupplementPage() {
        logger.info("加载其他补款申请页面。");
        return "profit/supply/profitSupplyApplyList";
    }

    /**
     * 获取省区其他补款申请
     */
    @RequestMapping(value = "gotoCityApplicationPage")
    public String gotoApplicationPage() {
        logger.info("加载申请补款页面。");
        return "profit/supply/ApplySupplyPage";
    }

    /**
     * 获取省区其他补款数据列表
     */
    @RequestMapping("getApplyList")
    @ResponseBody
    public Object getOtherSupplyApplyList(HttpServletRequest request,PCityApplicationDetail pCityApplicationDetail){
        //获取传入参数
        Page page = pageProcess(request);
        //获得当前登录对象
        String userId = getStringUserId();
        //将map传入获取该省区数据列表
        PageInfo pageInfo = profitCityApplicationService.getSupplyAppList(page, userId, pCityApplicationDetail);
        return pageInfo;
    }

    /**
     * 提交其他补款申请
     * @param pCityApplicationDetail
     * @return
     */
    @RequestMapping("commitInfo")
    @ResponseBody
    public Object commitApplication(PCityApplicationDetail pCityApplicationDetail) {
        String loginName = getStringUserId();
        pCityApplicationDetail.setCreateDate(new Date());
        pCityApplicationDetail.setCreateName(loginName);

        List<Map<String, Object>>  list = userService.orgCode(getUserId());
        if(list.size() >0){
            if(list.get(0).get("ORGANIZATIONCODE").toString().contains("north") || list.get(0).get("ORGANIZATIONCODE").toString().contains("south") ){
                Map<String,String> map = new HashMap<String,String>();
                map.put("id",pCityApplicationDetail.getAgentId());
                map.put("orgId",list.get(0).get("ORGID").toString());
                List<Agent> list1 = agentService.getListByORGAndId(map);
                if(list1.size()<=0){
                    return renderError("所申请代理商不属于该省区");
                }
            }
        }

        try {
            String workId = "otherRepair1.0";
            String cUser = getStringUserId();
            profitCityApplicationService.applyOtherSupply(pCityApplicationDetail, valueOf(getUserId()), workId, cUser);
            return renderSuccess("已提交审批");
        } catch (ProcessException e) {
            e.printStackTrace();
            return renderError("其他补款申请审批流启动失败");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return renderError("系统异常，请联系维护人员！");
    }

    /**
     * 处理任务：其他补款申请任务处理
     */
    @RequestMapping("toOtherSupplyApprovalView")
    public String approvalCompensateView(HttpServletRequest request, HttpServletResponse response, Model model) {
        String taskId = request.getParameter("taskId");
        String proIns = request.getParameter("proIns");
        String busId = request.getParameter("busId");
        String busType = request.getParameter("busType");

        PCityApplicationDetail pCityApplicationDetail = profitCityApplicationService.getDataById(busId);
        model.addAttribute("pCityApplicationDetail", pCityApplicationDetail);

        if (pCityApplicationDetail != null) {
            PCityApplicationDetail pc = profitCityApplicationService.getDataById(pCityApplicationDetail.getId());
            model.addAttribute("pCityApplicationDetail", pc);
        }

        //查询审批记录
        List<Map<String, Object>> actRecordList = queryApprovalRecord(busId, BusActRelBusType.CityApplySupply.name());
        //下级审批部门
        List<Dict> TOOLS_APR_BUSNISS = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), "OTHER_DEDUCT");
        //审批结果
        List<Dict> approvalType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT_AUDIT.name(), DictGroup.APPROVAL_TYPE.name());

        model.addAttribute("tools_apr_busniss", TOOLS_APR_BUSNISS);
        model.addAttribute("approvalType", approvalType);
        model.addAttribute("actRecordList", actRecordList);
        model.addAttribute("taskId", taskId);
        model.addAttribute("proIns", proIns);
        model.addAttribute("id", busId);

        return "profit/supply/OtherSupplyTaskApproval";
    }


    /**
     * 查看审批数据：查看审批信息和审批记录
     */
    @RequestMapping(value = "toAgentMergeQuery")
    public String toAgentMergeQuery(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");

        PCityApplicationDetail pCityApplicationDetail = profitCityApplicationService.getDataById(id);
        model.addAttribute("pCityApplicationDetail", pCityApplicationDetail);

        BusActRel busActRel = new BusActRel();
        busActRel.setBusId(id);
        BusActRel rel = null;
        try {
            rel = taskApprovalService.queryBusActRel(busActRel);
            if (rel != null) {
                model.addAttribute("actRecordList", queryApprovalRecord(rel.getActivId()));
                model.addAttribute("proIns", rel.getActivId());
                List<Dict> approvalType = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT_AUDIT.name(), DictGroup.APPROVAL_TYPE.name());
                model.addAttribute("approvalType", approvalType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "profit/supply/OtherSupplyApplyDetail";
    }







}
