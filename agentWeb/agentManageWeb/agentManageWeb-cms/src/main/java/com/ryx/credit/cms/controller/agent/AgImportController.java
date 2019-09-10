package com.ryx.credit.cms.controller.agent;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.IDUtils;
import com.ryx.credit.cms.util.ImportExcelUtil;
import com.ryx.credit.common.enumc.AgImportType;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.agent.ImportAgent;
import com.ryx.credit.service.agent.AimportService;
import com.ryx.credit.service.agent.BusinessCAService;
import com.ryx.credit.service.order.NewOrderImportService;
import com.ryx.credit.service.order.OrderImportService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by cx on 2018/6/11.
 */
@RequestMapping("agImport")
@Controller
public class AgImportController   extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(AgImportController.class);

    @Autowired
    private AimportService aimportService;
    @Autowired
    private OrderImportService orderImportService;
    @Autowired
    private BusinessCAService businessCAService;
    @Autowired
    private NewOrderImportService newOrderImportService;

    private List<String> da_type = Arrays.asList(AgImportType.BASICS.name(),
            AgImportType.BUSINESS.name(),
            AgImportType.PAYMENT.name(),AgImportType.CONTRACT.name(),AgImportType.BASBUSR.name());

    private List<String> order_da_type = Arrays.asList(AgImportType.OBASE.name(), AgImportType.OGOODS.name());

    private List<String> order_da_type_sn = Arrays.asList(AgImportType.OBASE.name(),
            AgImportType.OGOODS.name(),
            AgImportType.OLOGISTICS.name(),AgImportType.ORETURN.name(),AgImportType.ORLOGI.name());


    /**
     * 代理商单表列表
     * agImport/list
     * @param request
     * @param response
     * @param importAgent
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"list"})
    public PageInfo list(HttpServletRequest request, HttpServletResponse response, ImportAgent importAgent){
        Page page =  pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(), null, null);
        pageInfo =  aimportService.queryList(pageInfo,importAgent);
        return pageInfo;
    }


    /**
     * 数据导入界面
     *  agImport/importList
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("importList")
    public String importList(HttpServletRequest request, HttpServletResponse response){
        return  "agent/agentImportList";
    }


    /**
     * 数据导入界面
     * agImport/importView
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("importView")
    public String importView(HttpServletRequest request, HttpServletResponse response){
        return "common/agentImport";
    }


    /**
     * 上传
     * agImport/importExcel
     * @param files
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("importExcel")
    public ResultVO importExcel(@RequestParam(value = "file", required = false)MultipartFile[]  files, HttpServletRequest request){
        if (null == files) {
           return ResultVO.fail("文件不能为空");
        }
        try {
            String userid = getUserId()+"";
            String batchId = IDUtils.genBatchId()+"";
            for (MultipartFile file : files) {
                String filename = file.getOriginalFilename();
                logger.info("用户{}文件{}",userid,filename);
                Workbook workbook = ImportExcelUtil.getWorkbook(file.getInputStream(),filename);
                try {
                    if (null == workbook) {
                        logger.info("用户{}消息",userid,"创建Excel工作薄为空");
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
                                if(null==cell){
                                    li.add("");
                                }else{
                                    li.add(ImportExcelUtil.getCellValue(cell));
                                }
                            }
                            if(li.size()>0) {
                                list.add(li);
                            }else{
                                logger.info("用户{}代理商导入dubbo调用{},数据类型{}数据为空",userid,list.size(),da_type.get(i));
                            }
                        }
                        logger.info("用户{}代理商导入dubbo调用{},数据类型{}",userid,list.size(),da_type.get(i));
                        List<String>  resd =  aimportService.addList(list,da_type.get(i),userid,batchId);
                        logger.info("用户{}代理商导入dubbo调用结果返回{},数据类型{}",userid,resd.size(),da_type.get(i));

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    workbook.close();
                }

            }
            return ResultVO.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.fail(e.getMessage());
        }
    }


    /**
     * 上传
     * agImport/importBusRegionInfoExcel
     * @param files
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("importBusRegionInfoExcel")
    public ResultVO importBusRegionInfoExcel(@RequestParam(value = "file", required = false)MultipartFile[]  files, HttpServletRequest request){
        if (null == files) {
            return ResultVO.fail("文件不能为空");
        }
        try {
            List<String> msg_res = new ArrayList<>();
            String userid = getUserId()+"";
            String batchId = IDUtils.genBatchId()+"";
            for (MultipartFile file : files) {
                String filename = file.getOriginalFilename();
                String fileCode = filename.substring(0,filename.indexOf("."));
                logger.info("用户{}导入文件{}importBusRegionInfoExcel",userid,filename);
                Workbook workbook = ImportExcelUtil.getWorkbook(file.getInputStream(),filename);
                try {
                    if (null == workbook) {
                        logger.info("用户{}消息",userid,"创建Excel工作薄为空");
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
                                if(null==cell){
                                    li.add("");
                                }else{
                                    li.add(ImportExcelUtil.getCellValue(cell));
                                }
                            }
                            if(li.size()>0) {
                                logger.info("用户{}导入行{}",userid,li);
                                ResultVO r = aimportService.importAgentBusInfoBusInfoFromExcel(fileCode,getUserId()+"",li);
                                msg_res.add(j+":"+r.getResInfo());
                            }else{
                                logger.info("用户{}{}{}行无数据",userid,filename,j);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    workbook.close();
                }
            }
            return ResultVO.success(msg_res);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.fail(e.getMessage());
        }
    }


    /**
     * agImport/analysisRecode
     * 解析订单
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("analysisRecode")
    public ResultVO analysisRecode(HttpServletRequest request, HttpServletResponse response){
        try {
            return aimportService.analysisRecode(getUserId()+"");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.fail(e.getMessage());
        }
    }


    /**
     * 上传老订单含有SN信息（含SN）
     * agImport/importOrderExcelSN
     * @param files
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("importOrderExcelSN")
    public ResultVO importOrderExcelSN(@RequestParam(value = "file", required = false)MultipartFile[]  files, HttpServletRequest request){
        if (null == files) {
            return ResultVO.fail("文件不能为空");
        }
        try {
            String userid = getUserId()+"";
            String batchId = IDUtils.genBatchId()+"";
            for (MultipartFile file : files) {
                String filename = file.getOriginalFilename();
                logger.info("用户{}订单文件{}",userid,filename);
                Workbook workbook = ImportExcelUtil.getWorkbook(file.getInputStream(),filename);
                try {
                    if (null == workbook) {
                        logger.info("用户{}消息",userid,"创建Excel工作薄为空");
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
                        if(i>=order_da_type_sn.size()){
                            break;
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
                                if(null==cell){
                                    li.add("");
                                }else{
                                    li.add(ImportExcelUtil.getCellValue(cell));
                                }
                            }
                            if(li.size()>0) {
                                list.add(li);
                            }else{
                                logger.info("用户{}订单导入dubbo调用{},数据类型{}数据为空",userid,list.size(),order_da_type_sn.get(i));
                            }
                        }
                        logger.info("用户{}订单导入dubbo调用{},数据类型{}",userid,list.size(),order_da_type_sn.get(i));
                        List<String> resd = orderImportService.addOrderInfoList(list, order_da_type_sn.get(i), userid,batchId);
                        logger.info("用户{}订单导入dubbo调用结果返回{},数据类型{}",userid,resd.size(),order_da_type_sn.get(i));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    workbook.close();
                }
            }
            return ResultVO.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.fail(e.getMessage());
        }
    }


    /**
     * 解析老订单含有SN数据(含SN)
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("importPareseOrderDataSN")
    public ResultVO importPareseOrderDataSN(HttpServletRequest request, HttpServletResponse response){
        return orderImportService.pareseOrderEnter(getUserId() + "");
    }


    /**
     * 上传订单信息（无SN）
     * agImport/importOrderExcel
     * @param files
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("importOrderExcel")
    public ResultVO importOrderExcel(@RequestParam(value = "file", required = false)MultipartFile[]  files, HttpServletRequest request){
        if (null == files) {
            return ResultVO.fail("文件不能为空");
        }
        try {
            String userid = getUserId()+"";
            String batchId = IDUtils.genBatchId()+"";
            for (MultipartFile file : files) {
                String filename = file.getOriginalFilename();
                logger.info("用户{}订单文件{}",userid,filename);
                Workbook workbook = ImportExcelUtil.getWorkbook(file.getInputStream(),filename);
                try {
                    if (null == workbook) {
                        logger.info("用户{}消息",userid,"创建Excel工作薄为空");
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
                        if(i>=order_da_type.size()){
                            break;
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
                                if(null==cell){
                                    li.add("");
                                }else{
                                    li.add(ImportExcelUtil.getCellValue(cell));
                                }
                            }
                            if(li.size()>0) {
                                list.add(li);
                            }else{
                                logger.info("用户{}订单导入dubbo调用{},数据类型{}数据为空",userid,list.size(),order_da_type.get(i));
                            }
                        }
                        logger.info("用户{}订单导入dubbo调用{},数据类型{}",userid,list.size(),order_da_type.get(i));
                        List<String> resd = newOrderImportService.newAddOrderInfoList(list, order_da_type.get(i), userid, batchId);
                        logger.info("用户{}订单导入dubbo调用结果返回{},数据类型{}",userid,resd.size(),order_da_type.get(i));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    workbook.close();
                }
            }
            return ResultVO.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.fail(e.getMessage());
        }
    }


    /**
     * 解析老订单无SN数据
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("importPareseOrderData")
    public ResultVO importPareseOrderData(HttpServletRequest request, HttpServletResponse response){
        return newOrderImportService.newPareseOrderEnter(getUserId() + "");
    }


    /**
     * 接卸物流到redis
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("pareseOrderLogic")
    public ResultVO pareseOrderLogic(HttpServletRequest request, HttpServletResponse response){
        try {
            AgentResult agentResult = orderImportService.pareseOrderLogic(getUserId()+"");
            if(agentResult.isOK()){
                return ResultVO.success(agentResult.getData());
            }else{
                return ResultVO.fail(agentResult.getMsg()) ;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.fail(e.getLocalizedMessage()) ;
        }
    }


    /**
     * 解析退货订单数据
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("importPareseOrderReturnData")
    public ResultVO importPareseOrderReturnData(HttpServletRequest request, HttpServletResponse response){
        return orderImportService.pareseReturnOrderEnter(getUserId()+"");
    }


    /**
     * 批量对未认证的代理商进行工商认证
     * agImport/caAgentList
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("caAgentList")
    public ResultVO caAgentList(HttpServletRequest request, HttpServletResponse response){
        AgentResult res =  businessCAService.caAgentList();
        return ResultVO.success(res.getData());
    }

}
