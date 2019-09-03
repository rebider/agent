package com.ryx.credit.cms.controller.profit;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ExcelUtil;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.service.ProfitDeductionService;
import com.ryx.credit.profit.service.StagingService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.management.Agent;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 其他扣款
 *
 * @author zhaodw
 * @create 2018/7/24
 * @since 1.0.0
 */
@Controller
@RequestMapping("/profit/other/")
public class ProfitOtherDeductionController extends BaseController {

    private static final Logger LOGGER = Logger.getLogger(ProfitOtherDeductionController.class);

    @Autowired
    private ProfitDeductionService profitDeductionServiceImpl;

    @Autowired
    private StagingService stagingServiceImpl;

    @Autowired
    private IUserService userService;
    @Autowired
    private AgentService agentService;

    private static final String FINANCE ="finance";
    private static final String AGENT = "agent";

    /***
    * @Description: 加载管理页面
    * @Author: zhaodw
    * @Date: 2018/8/3
    */
    @RequestMapping("gotoProfitDeductionList")
    public String gotoProfitDeductionList(Model model) {
        LOGGER.info("加载其他扣款管理页面。");
        // 终审后不能做修改
        String finalStatus = ServiceFactory.redisService.getValue("commitFinal");
        if (StringUtils.isBlank(finalStatus)) {
            model.addAttribute("noEdit","0");
        }else{
            model.addAttribute("noEdit","1");
        }
        return "profit/otherDeduction/profitOtherDeductionList";
    }

    /***
    * @Description:  加载新增页面
    * @Author: zhaodw
    * @Date: 2018/8/3
    */
    @RequestMapping("gotoAddPage")
    public String gotoAddPage() {
        LOGGER.info("加载导入页面。");
        return "profit/otherDeduction/importDeduction";
    }

    /**
     * 获取扣款列表
     *
     * @param request
     * @param profitDeduction 扣款参数
     * @return 扣款数据
     */
    @RequestMapping(value = "getProfitDeductionList", method = RequestMethod.POST)
    @ResponseBody
    public PageInfo getProfitDeductionList(HttpServletRequest request, ProfitDeduction profitDeduction) {
        LOGGER.info("加载其他扣款列表数据。");
        Page page = pageProcess(request);
        profitDeduction.setDeductionType("'03','06','07'");

        List<Map<String, Object>>  list = userService.orgCode(getUserId());
        if(list == null || list.isEmpty()){
            return new PageInfo();
        }
        Map<String, Object> map = list.get(0);

        if(Objects.equals(map.get("ORGANIZATIONCODE"),FINANCE)){  // 财务不做限制
            map = null;
        }else if( Objects.equals(map.get("ORGANIZATIONCODE"), AGENT)){
            map = null;
            profitDeduction.setAgentId(getAgentId());
            profitDeduction.setAgentName(agentService.getAgentById(getAgentId()).getAgName());
        }
        return profitDeductionServiceImpl.getProfitDeductionList(map, profitDeduction, page);
    }


    /***
     * @Description: 导入excel
     * @Param:
     * @return:
     * @Author: zhaodw
     * @Date: 2018/7/31
     */
    @RequestMapping("importDeduction")
    @ResponseBody
    public ResultVO importDeduction(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
        if (null == file) {
            return ResultVO.fail("文件不能为空");
        }
        try {
            List<List<Object>> deductionist = ExcelUtil.getListByExcel(file.getInputStream(), file.getOriginalFilename());
            profitDeductionServiceImpl.batchInsertOtherDeduction(deductionist, getUserId().toString());
        }catch (ProcessException e){
            LOGGER.info("导入失败。"+e.getMessage());
            return ResultVO.fail(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("导入失败。"+e.getMessage());
            return ResultVO.fail("导入失败。");
        }
        return ResultVO.success("导入成功。");
    }

    /**
     * 清楚其他扣款上月数据，不包括线上审批数据
     * @param request
     * @return
     */
    @RequestMapping(value = "resetDataDeduction")
    @ResponseBody
    public ResultVO resetDataDeduction(HttpServletRequest request) {
        int count = profitDeductionServiceImpl.resetDataDeduction("03");
        if(count>0){
            return ResultVO.success("清除"+count+"条记录");
        }
        return ResultVO.success("无上月记录");
    }

    /**
     * 加载考核扣款页面
     * @author CQT
     * @Created 2018/12/15
     * @param model
     * @return
     */
    @RequestMapping("/gotoProfitCheckDeductionList")
    public String gotoProfitCheckDeduction(Model model){
        LOGGER.info("加载考核扣款管理页面。");
        String finalStatus = ServiceFactory.redisService.getValue("commitFinal");
        if(StringUtils.isBlank(finalStatus)){
            model.addAttribute("noEdit","0");
        }else{
            model.addAttribute("noEdit","1");
        }
        return "profit/checkDeduction/profitCheckDeductionList";
    }

    /**
     * 获取考核扣款列表
     * @param request
     * @param profitDeduction
     * @return
     */
    @RequestMapping(value = "/getProfitCheckDeductionList", method = RequestMethod.POST)
    @ResponseBody
    public PageInfo getProfitCheckDeductionList(HttpServletRequest request, ProfitDeduction profitDeduction) {
        LOGGER.info("加载考核扣款列表数据。");
        Page page = pageProcess(request);
        profitDeduction.setDeductionType("04");

        List<Map<String, Object>>  list = userService.orgCode(getUserId());
        if(list == null || list.isEmpty()){
            return new PageInfo();
        }
        Map<String, Object> map = list.get(0);
        if(Objects.equals(map.get("ORGANIZATIONCODE"),FINANCE)) {
            map = null;
        }else if(Objects.equals(map.get("ORGANIZATIONCODE"), AGENT)){
            map = null;
            profitDeduction.setAgentId(getAgentId());
        }

        return profitDeductionServiceImpl.getProfitDeductionList(map,profitDeduction,page);
    }

    /***
     * @Description:  加载导入页面
     * @Author: zhaodw
     * @Date: 2018/8/3
     */
        @RequestMapping("/gotoCheckAddPage")
        public String gotoCheckAddPage() {
            LOGGER.info("加载导入页面。");
            return "profit/checkDeduction/importCheckDeduction";
        }

    /**
     * 导入考核扣款数据
     * @param file
     * @param request
     * @return  importDeduction
     */
    @RequestMapping("importCheckDeduction")
    @ResponseBody
    public ResultVO importCheckDeduction(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
        if (null == file) {
            return ResultVO.fail("文件不能为空");
        }
        try {
            List<List<Object>> deductionist = ExcelUtil.getListByExcel(file.getInputStream(), file.getOriginalFilename());
            profitDeductionServiceImpl.batchInsertCheckDeduction(deductionist,getUserId().toString());
        }catch (MessageException e){
            LOGGER.info("导入失败。"+e.getMessage());
            return ResultVO.fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("导入失败。"+e.getMessage());
            return ResultVO.fail("导入失败。");
        }
        return ResultVO.success("导入成功。");
    }

    /**
     * 清楚考核扣款上月数据
     */
    @RequestMapping(value = "resetCheckDataDeduction")
    @ResponseBody
    public ResultVO resetCheckDataDeduction(HttpServletRequest request) {
        int count = profitDeductionServiceImpl.resetDataDeduction("04");
        if(count>0){
            return ResultVO.success("清除"+count+"条记录");
        }
        return ResultVO.success("无上月记录");
    }

}
