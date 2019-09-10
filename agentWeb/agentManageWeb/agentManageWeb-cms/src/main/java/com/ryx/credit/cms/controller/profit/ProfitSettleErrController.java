package com.ryx.credit.cms.controller.profit;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.ExcelUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitSettleErrLs;
import com.ryx.credit.profit.pojo.ProfitSupply;
import com.ryx.credit.profit.service.ProfitDeductionService;
import com.ryx.credit.profit.service.ProfitSettleErrLsService;
import com.ryx.credit.profit.service.ProfitSupplyService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 退单管理页面逻辑处理
 * @author zhaodw
 * @create 2018/7/24
 * @since 1.0.0
 */
@Controller
@RequestMapping("/profit/settleErr/")
public class ProfitSettleErrController extends BaseController {

    private static  final  Logger LOGGER = Logger.getLogger(ProfitSettleErrController.class);

    private static String TITLE = "登账编号,退单日期,交易日期,一级代理商唯一码,代理商唯一码,商户编号,商户名称,交易金额,交易净额,销账金额,剩余未销账金额,实扣分润,记损,补还分润,分润标识,登帐类型,备注,差错状态,交易卡号,检索参考号,出款日期,合作模式,省份,业务类型,应扣分润,应补分润,应冲抵分润,应收未收,用户编号,归属机构,姓名,手机号,终端号";
    private static String COLUME = "errCode,chargebackDate,tranDate,parentAgentId,agentId,merchId,merchName,tranAmt,netAmt,offsetBalanceAm,balanceAmt,realDeductAmt,lossAmt,makeAmt,fenrunFlag,longShortType,errDesc,errFlag,cardNo,hostLs,balanceDate,cooperationMode,provinces,businessType,mustDeductionAmt,mustSupplyAmt,mustCdAmt,yswsAmt,hbMerchId,hbOrg,hbName,hbPhone,hbTermId";
    private static String PATH = AppConfig.getProperty(ProfitDeductionController.getOsName()+".deduction.path");

    @Autowired
    private ProfitSettleErrLsService profitSettleErrLsServiceImpl;

    @Autowired
    private ProfitDeductionService profitDeductionServiceImpl;

    @Autowired
    private ProfitSupplyService profitSupplyServiceImpl;

    /***
    * @Description:  加载列表页面
    * @Author: zhaodw
    * @Date: 2018/8/3
    */
    @RequestMapping("gotoProfitSettleErrList")
    public String gotoProfitSettleErrList(Model model, String sourceId, String type) {
        LOGGER.info("加载退单管理页面。");
        model.addAttribute("sourceId",sourceId);
        if ("2".equals(type)) {
            ProfitSupply profitSupply =  profitSupplyServiceImpl.selectByPrimaryKey(sourceId);
            model.addAttribute("agentId",profitSupply.getAgentId());
            model.addAttribute("parentAgentId",profitSupply.getParentAgentId());
        }else{
            ProfitDeduction profitDeduction =profitDeductionServiceImpl.getProfitDeductionById(sourceId);
            model.addAttribute("agentId",profitDeduction.getAgentId());
            model.addAttribute("parentAgentId",profitDeduction.getParentAgentId());
        }
        return "profit/settleErrls/profitSettleErrList";
    }

    /***
    * @Description:  加载数据列表
    * @Author: zhaodw
    * @Date: 2018/8/3
    */
    @RequestMapping(value = "getProfitSettleErrList", method = RequestMethod.POST)
    @ResponseBody
    public PageInfo getProfitSettleErrList(HttpServletRequest request, ProfitSettleErrLs profitSettleErrLs)
    {
        LOGGER.info("加载退单列表数据。");
        Page page = pageProcess(request);
        PageInfo resultPageInfo = profitSettleErrLsServiceImpl.getProfitSettleErrList(profitSettleErrLs, page);
        if (resultPageInfo.getTotal() > 0) {
            final String agentId = request.getParameter("agentId");
            final String parentAgentId = request.getParameter("parentAgentId");
            List<ProfitSettleErrLs> profitSettleErrLsList = resultPageInfo.getRows();
            resultPageInfo.setRows(profitSettleErrLsList.stream().map(profitSettleErrLs1 -> {
                Map<String, String> settleErrMap = null;
                try {
                    settleErrMap = BeanUtils.describe(profitSettleErrLs1);
                    settleErrMap.put("agentId", agentId);
                    settleErrMap.put("parentAgentId", parentAgentId);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                return settleErrMap;

            }).collect(Collectors.toList()));
        }
        return resultPageInfo;
    }


    /**
     * 导出
     * @param request
     * @param response
     */
    @RequestMapping("export")
    public void exportProdeduction(HttpServletRequest request, HttpServletResponse response, ProfitSettleErrLs profitSettleErrLs) {

        PageInfo resultPageInfo = profitSettleErrLsServiceImpl.getProfitSettleErrList(profitSettleErrLs, null);
        List<Map<String, String>> settleErrs = new ArrayList<>(resultPageInfo.getTotal());
        if (resultPageInfo.getTotal() > 0) {
            final String agentId = request.getParameter("agentId");
            final String parentAgentId = request.getParameter("parentAgentId");
            List<ProfitSettleErrLs> profitSettleErrLsList = resultPageInfo.getRows();
            profitSettleErrLsList.forEach(profitSettleErrLs1 -> {
                Map<String, String> settleErrMap = null;
                try {
                    settleErrMap = BeanUtils.describe(profitSettleErrLs1);
                    settleErrMap.put("agentId", agentId);
                    settleErrMap.put("parentAgentId", parentAgentId);
                    settleErrs.add(settleErrMap);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }

            });
        }
        Map<String, Object> param = new HashMap<>(10);
        param.put("path", PATH);
        param.put("title", TITLE);
        param.put("column", COLUME);
        param.put("dataList", settleErrs);
        param.put("response", response);
        ExcelUtil.downLoadExcel(param);
    }
}
