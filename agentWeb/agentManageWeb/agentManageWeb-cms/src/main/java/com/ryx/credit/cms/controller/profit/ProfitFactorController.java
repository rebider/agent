package com.ryx.credit.cms.controller.profit;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ExcelUtil;
import com.ryx.credit.cms.util.ImportExcelUtil;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.pojo.PProfitFactor;
import com.ryx.credit.profit.service.ProfitFactorService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @Author Lihl
 * @Date 2018/08/02
 * 分润管理：商业保理数据录入
 */
@Controller
@RequestMapping("/profitFactor")
public class ProfitFactorController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(ProfitFactorController.class);

    @Resource
    private ProfitFactorService profitFactorService;

    @RequestMapping(value = "pageList")
    public String profitFactorPage(Model model) {
        logger.info("加载商业保理管理页面...");
        //终审后不能进行操作
        String finalStatus = ServiceFactory.redisService.getValue("commitFinal");
        if (StringUtils.isBlank(finalStatus)) {
            model.addAttribute("noEdit","0");
        }else{
            model.addAttribute("noEdit","1");
        }
        return "profit/profitFactor/profitFactorList";
    }

    /**
     * 商业保理:
     * 1、分页展示
     * @return
     */
    @RequestMapping(value = "getList")
    @ResponseBody
    public Object getProfitFactorList(HttpServletRequest request, PProfitFactor profitFactor){
        String agentPid = ServiceFactory.redisService.hGet("agent",getUserId().toString());
        Page page = pageProcess(request);
        if (StringUtils.isNotBlank(agentPid)) {
            profitFactor.setAgentPid(agentPid);
        }
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(), null, null);
        TreeMap map = getRequestParameter(request);

        Set<String> roles = getShiroUser().getRoles();
        if (roles != null && roles.contains("代理商")) {
            map.put("AGENT_PID",getAgentId());
            map.put("AGENT_NAME",getStaffName());
        }
        map.put("begin", page.getBegin());
        map.put("end", page.getEnd());
        return profitFactorService.getProfitFactorList(map, pageInfo);
    }


    @RequestMapping(value = "profitCount")
    public ModelAndView profitCount(HttpServletRequest request) {
        TreeMap param=getRequestParameter(request);
        logger.info("\n\n"+param.toString()+"\n\n");
        String agentName=param.get("AGENT_NAME").toString();
        try {
            if (agentName.equals(new String(agentName.getBytes("iso8859-1"),"iso8859-1"))){
                agentName=new String(agentName.getBytes("iso8859-1"),"utf-8");
                param.put("AGENT_NAME",agentName);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ModelAndView view = new ModelAndView();
        Map<String,Object> map;
        map=profitFactorService.profitCount(param);
        view.addObject("totalNum",map.get("TOTALNUM"));
        view.addObject("totalMoney",map.get("TOTALMONEY")==null?0:map.get("TOTALMONEY"));
        view.setViewName("profit/profitCount");
        return view;
    }

    @RequestMapping("/importPage")
    public String importPage(){
        logger.info("加载导入页面...");
        return "profit/profitFactor/profitFactorImport";
    }

    /**
     * 商业保理：
     * 1、导入保理数据
     * @return
     */
    @RequestMapping(value = "importFile")
    @ResponseBody
    public ResultVO importFile(@RequestParam(value = "file")MultipartFile file){
        if (null == file || file.getSize() == -1) {
            return ResultVO.fail("文件不能为空");
        }
        String name = file.getOriginalFilename();
        String str = name.substring(name.lastIndexOf(".")+1);
        if(!"xls".equals(str) && !"xlsx".equals(str)){
            return ResultVO.fail("上传文件只支持Excel文件");
        }
        try {
            List<List<Object>> list = ExcelUtil.getListByExcel(file.getInputStream(), file.getOriginalFilename());
            profitFactorService.addList(list, getUserId().toString());
            return ResultVO.success("导入成功。");
        }catch (MessageException e){
            e.printStackTrace();
            return ResultVO.fail(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.fail("导入失败。");
        }

    }

    /**
     * @desc 清除本月导入数据
     * @return
     */
    @RequestMapping(value = "resetDataFactor")
    @ResponseBody
    public ResultVO resetData() {
        int count = profitFactorService.resetDataFactor();
        if(count > 0) {
            return ResultVO.success("清除"+count+"条记录");
        }
        return ResultVO.success("无上月记录");
    }

    /**
     * @desc 对表格中含有公式的单元格的数值进行格式化
     * @param cell
     * @return
     */
    public static String getNumericCellValue(Cell cell) {
        int cellType = cell.getCellType();
        String cellValue = "";
        switch (cellType) {
            case HSSFCell.CELL_TYPE_NUMERIC:
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;

            case HSSFCell.CELL_TYPE_FORMULA:
                try {
                    cellValue = cell.getStringCellValue();
                } catch (IllegalStateException e) {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                }
                break;

            default:
                cellValue = cell.getStringCellValue();
        }

        return cellValue.trim();
    }


}
