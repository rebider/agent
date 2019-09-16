package com.ryx.credit.cms.controller.order;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ImportExcelUtil;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.pojo.admin.order.OLogisticsDetailH;
import com.ryx.credit.pojo.admin.order.OOrder;
import com.ryx.credit.service.order.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.*;

/**
 * @Author Lihl
 * @Date 2018/07/23
 * 排单管理：物流信息
 */
@Controller
@RequestMapping("/logistics")
public class LogisticsController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(LogisticsController.class);
    @Resource
    private OLogisticsService oLogisticsService;
    @Resource
    private OrderService orderService;
    @Autowired
    private IOrderReturnService orderReturnService;
    @Resource(name = "oldOrderReturnService")
    private OldOrderReturnService oldOrderReturnService;
    @Resource
    private OLogisticsDetailHService oLogisticsDetailHService;


    private static String EXPORT_Logistics_PATH = AppConfig.getProperty("export.path");

    @GetMapping("/selectOLogistics")
    public String selectOLogistics(HttpServletRequest request) {
        orderDictData(request);
        optionsData(request);
        List<PlatForm> platFormList = ServiceFactory.businessPlatformService.queryAblePlatForm();
        request.setAttribute("platFormList", platFormList);
        return "order/oLogistics";
    }

    /**
     * 订单物流列表
     * /logistics/orderLogisticsDialog?orderId=
     *
     * @param request
     * @param orderId
     * @return
     * @throws Exception
     */
    @GetMapping("/orderLogisticsDialog")
    public String orderLogisticsDialog(HttpServletRequest request, String orderId, String agentId) throws Exception {
        if (StringUtils.isEmpty(orderId)) {
            throw new Exception("订单ID不可为空");
        }
        OOrder order = orderService.getById(orderId);
        if (!order.getAgentId().equals(agentId)) {
            throw new Exception("信息错误");
        }
        request.setAttribute("orderId", orderId);
        return "order/orderLogistics";
    }

    /**
     * 物流信息:
     * 分页展示
     */
    @RequestMapping("/oLogisticsList")
    @ResponseBody
    public Object oLogisticsList(HttpServletRequest request) {
        Page page = pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(), null, null);
        TreeMap map = getRequestParameter(request);
        map.put("begin", page.getBegin());
        map.put("end", page.getEnd());
        map.put("returnId", request.getParameter("returnId"));
        map.put("AGENT_ID", getAgentId());
        return oLogisticsService.getOLogisticsList(map, pageInfo);
    }

    /**
     * 物流信息:
     * 分页展示
     */
    @RequestMapping("/oLogisticsListRefund")
    @ResponseBody
    public Object oLogisticsListRefund(HttpServletRequest request) {
        Page page = pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(), null, null);
        TreeMap map = getRequestParameter(request);
        map.put("begin", page.getBegin());
        map.put("end", page.getEnd());
        map.put("returnId", request.getParameter("returnId"));
        return oLogisticsService.getOLogisticsList(map, pageInfo);
    }

    /**
     * 物流信息:
     * 导出物流信息
     */
    @RequestMapping("/exportOLogistics")
    public void exportOLogistics(HttpServletRequest request, HttpServletResponse response) {
        TreeMap map = getRequestParameter(request);
        PageInfo pageInfo = oLogisticsService.getOLogisticsList(map, new PageInfo());
        Map<String, Object> param = new HashMap<String, Object>(6);

        String title = "发货日期,订单日期,订单编号,唯一码,平台,代理商名称,机具类型,活动名称,机型,订货厂家,数量,金额,收货人,收货人地址,收货人联系方式,起始SN序列号,结束SN序列号,物流公司,物流单号";
        String column = "SENDTIME,TIME,ORDER_ID,AG_UNIQ_NUM,PLATFORM_NAME,AG_NAME,PRO_TYPE,ACTIVITY_NAME,MODEL,PRO_COM,SEND_NUM,PRO_PRICE,ADDR_REALNAME,ADDR_DETAIL,ADDR_MOBILE,SN_BEGIN_NUM,SN_END_NUM,LOG_COM,W_NUMBER";

        param.put("path", EXPORT_Logistics_PATH);
        param.put("title", title);
        param.put("column", column);
        param.put("dataList", pageInfo.getRows());
        param.put("response", response);
        ExcelUtil.downLoadExcel(param);
    }

    @RequestMapping("/importPage")
    public String importPage() {
        return "order/oLogisticsImport";
    }

    /**
     * 物流信息:
     * 导入物流信息
     */
    @RequestMapping("/importOLogistics")
    @ResponseBody
    public ResultVO importOLogistics(@RequestParam(value = "file", required = false) MultipartFile[] files,String requestType) {
        if (null == files) {
            return ResultVO.fail("文件不能为空");
        }
        try {
            List<List> res_msg = new ArrayList<>();
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
                                if (null == cell) {
                                    li.add("");
                                } else {
                                    li.add(ImportExcelUtil.getCellValue(cell));
                                }
                            }
                            if (li.size() > 0) {
                                list.add(li);
                            } else {
                                logger.info("用户{}导入dubbo调用{}, 数据类型{}数据为空", userid, list.size());
                            }
                        }
                        logger.info("用户{}导入dubbo调用{}, 数据类型{}", userid, list.size());
                        List<String> resd = new ArrayList<>();
                        if(StringUtils.isBlank(requestType)){
                            resd = oLogisticsService.addList(list, userid);
                            logger.info("用户{}导入dubbo调用结果返回{},异常数据{}", userid, resd.toString());
                            res_msg.add(resd);
                        }else if(requestType.equals("orderReturn")){
                            resd = orderReturnService.addList(list, userid);
                            logger.info("用户{}导入dubbo调用结果返回{},数据类型{}", userid, resd.size());
                            res_msg.add(resd);
                        }else if(requestType.equals("old_orderReturn")){
                            resd =  oldOrderReturnService.uploadSnFileList(list, userid);
                            res_msg.add(resd);
                        }else{
                            return ResultVO.fail("错误");
                        }

                    }
                }catch (MessageException e) {
                    e.printStackTrace();
                    return ResultVO.fail(e.getMsg());
                }  catch (Exception e) {
                    e.printStackTrace();
                    logger.info("导入物流异常：", e.getMessage());
                    return ResultVO.fail(e.getMessage());
                } finally {
                    workbook.close();
                }
            }
            return ResultVO.success(res_msg);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.fail(e.getMessage());
        }
    }

    @RequestMapping("/importSn")
    public String importSn() {
        return "order/oSnImport";
    }


    @RequestMapping("/importOSn")
    @ResponseBody
    public ResultVO importOSn(@RequestParam(value = "file", required = false) MultipartFile[] files) throws Exception {
        if (null == files) {
            return ResultVO.fail("文件不能为空");
        }
        InputStream inputStream = null;
        Scanner sc = null;
        int i = 0;
        try {
            String userid = getUserId() + "";
            try {
                for (MultipartFile file : files) {
                    List<List<String>> arrList = new ArrayList<>();
                    String filename = file.getOriginalFilename();
                    inputStream = file.getInputStream();
                    sc = new Scanner(inputStream, "UTF-8");
                    while (sc.hasNextLine()) {
                        String str = sc.nextLine();
                        i++;
                        if (i == 1) continue;
                        String[] split = str.split("\\|");
                        List<String> stringB = Arrays.asList(split);
                        arrList.add(stringB);
                        System.out.println(arrList + "0000");
                    }
                    logger.info("用户{}SN码导入",userid);
                    List<String> resd = oLogisticsService.addSn(arrList, userid);
                    logger.info("用户{}SN码 dubbo调用结果返回{}",userid,resd.size());
                    if (sc.ioException() != null) {
                        throw sc.ioException();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof MessageException) {
                    String msg = ((MessageException) e).getMsg();
                    return ResultVO.fail(msg);
                }
                logger.info("导入SN码异常：", e.getMessage());
                return ResultVO.fail(e.getMessage());
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (sc != null) {
                    sc.close();
                }
            }
            return ResultVO.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.fail(e.getMessage());
        }
    }


    /**
     * 物流明细
     * @return
     */
    @RequestMapping("/logisticsDetail")
    public String logisticsDetail() {
        return "order/oLogisticsDetail";
    }

    @RequestMapping("/oLogisticsDetailList")
    @ResponseBody
    public Object oLogisticsDetailList(HttpServletRequest request) {
        Page page = pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(), null, null);
        TreeMap map = getRequestParameter(request);
        map.put("begin", page.getBegin());
        map.put("end", page.getEnd());
        map.put("orderId", request.getParameter("orderId"));
        map.put("snNum", request.getParameter("snNum"));
        return  oLogisticsService.getOLogisticsDetailList(map, pageInfo);
    }

    /**
     * 手动发送
     * @param request
     *  /logistics/sendToBusSystem
     * @return
     */
    @RequestMapping("/sendToBusSystem")
    @ResponseBody
    public Object resendToSystem(HttpServletRequest request){
        try {
            AgentResult res =  oLogisticsService.sendLgcInfoToBusSystem(request.getParameter("id"),getUserId()+"");
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return AgentResult.fail(e.getLocalizedMessage());
        }
    }


    /**
     * 历史物流明细
     * @return
     */
    @RequestMapping("/historyDetail")
    public String historyDetail() {
        return "order/oLogHistoryDetail";
    }

    @RequestMapping("/oLogHistoryDetailList")
    @ResponseBody
    public Object oLogHistoryDetailList(HttpServletRequest request, OLogisticsDetailH oLogisticsDetailH) {
        Page page = pageProcess(request);
        PageInfo pageInfo = oLogisticsDetailHService.getOLogDetailHistoryList(oLogisticsDetailH, page);
        return pageInfo;
    }

}
