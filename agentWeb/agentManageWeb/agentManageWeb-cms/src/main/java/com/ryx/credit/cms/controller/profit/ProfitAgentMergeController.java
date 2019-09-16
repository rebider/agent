package com.ryx.credit.cms.controller.profit;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.enumc.RewardStatus;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.profit.pojo.PAgentMerge;
import com.ryx.credit.profit.pojo.PTaxAdjust;
import com.ryx.credit.profit.service.IPTaxAdjustService;
import com.ryx.credit.profit.service.IProfitAgentMergerService;
import com.ryx.credit.service.agent.BusActRelService;
import net.sf.ehcache.search.impl.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
/**
 * 代理商合并控制层
 * @version V1.0
 * @Description:
 * @author: LiuQY
 * @date: 2018/9/27 09:30
 */
@RequestMapping("profitAgentMerge")
@Controller
public class ProfitAgentMergeController extends BaseController {
    @Autowired
    private IProfitAgentMergerService profitAgentMergerService;
    @Autowired
    private IPTaxAdjustService taxAdjustService;
    @Autowired
    private BusActRelService busActRelService;

    /**
     * @Description: 代理商合并
     * @author: LiuQY
     * @date: 2018/9/27 09:30
     */
    @RequestMapping(value = {"mainMerge"})
    public String profitByAgentMerge(HttpServletRequest request){
        return "profit/profitAgentMergeList";
    }

    /**
     * @Description: 代理商合并list
     * @author: LiuQY
     * @date: 2018/9/27 10:8
     */
    @RequestMapping(value = "getProfitAgentMergeList")
    @ResponseBody
    public Object getProfitAgentMergeList(HttpServletRequest request) {
        Page page = pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(),null,null);
        TreeMap map = getRequestParameter(request);
        map.put("begin", page.getBegin());
        map.put("end", page.getEnd());
        return profitAgentMergerService.getProfitAgentMergeList(map, pageInfo);
    }

    @RequestMapping(value = {"buildAgentMerge"})
    public String buildAgentMerge(){
        return "profit/AgentMergeList";
    }

    /**代理商合并申请提交*/
    @ResponseBody
    @RequestMapping(value = {"agentMergeTaxEnterIn"}, method = RequestMethod.POST)
    public Object agentEnterIn(@RequestParam("agUniqNum")String agUniqNum, @RequestParam("agUniqNumT")String agUniqNumT,
                               @RequestParam("agName")String agName, @RequestParam("subHeadT")String subHeadT, @RequestParam("agNameT")String agNameT,
                               @RequestParam("mainHeadT")String mainHeadT,@RequestParam("subHeadMobileT")String subHeadMobileT) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            PAgentMerge pAgentMerge = new PAgentMerge();
            pAgentMerge.setMainAgentId(agUniqNum);//主要代理商ID
            pAgentMerge.setSubAgentId(agUniqNumT);//附代理商ID
            pAgentMerge.setMainAgentName(agName);//主代理商名称
            pAgentMerge.setSubAgentName(agNameT);//附代理商名称
            pAgentMerge.setMainHead(mainHeadT);//主代理商负责人
            pAgentMerge.setMainHeadMobile(subHeadMobileT);//主代理商负责人联系电话
            pAgentMerge.setSubnHead(subHeadT);//附代理商负责人
            pAgentMerge.setSubHeadMobile(subHeadMobileT);//附代理商负责人联系电话
            pAgentMerge.setMergeDate(df.format(new Date()));//合并日期
            profitAgentMergerService.agentMergeTaxEnterIn(pAgentMerge, getUserId());
            return renderSuccess("代理商合并申请添加成功！");
        } catch (ProcessException e) {
            if (e instanceof ProcessException) {
                String msg = ((ProcessException) e).getMsg();
                return ResultVO.fail(msg);
            }
            return ResultVO.fail(e.getMessage());
        } catch (MessageException e) {
            if (e instanceof MessageException) {
                String msg = ((MessageException) e).getMsg();
                return ResultVO.fail(msg);
            }
            return ResultVO.fail(e.getMessage());
        }
    }

    @RequestMapping("/editMergePage")
    public ModelAndView getMergeData(String id){
        if(StringUtils.isNotBlank(id)){
            PAgentMerge pAgentMerge = profitAgentMergerService.getMergeById(id);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("profit/agentMerge/agentMergeInfo");
            modelAndView.addObject("pAgentMerge", pAgentMerge);
            return modelAndView;
        }
        return null;
    }

    /**
     * 驳回修改--代理商合并
     * 审批修改申请信息
     */
    @RequestMapping("/editMergeRegect")
    @ResponseBody
    public Object editMergeRegect(@RequestParam("agUniqNum")String agUniqNum, @RequestParam("agUniqNumT")String agUniqNumT,
                                  @RequestParam("agName")String agName, @RequestParam("subHeadT")String subHeadT, @RequestParam("agNameT")String agNameT,
                                  @RequestParam("mainHeadT")String mainHeadT,@RequestParam("subHeadMobileT")String subHeadMobileT,
                                  @RequestParam("id")String id){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            PAgentMerge pAgentMerge = new PAgentMerge();
            pAgentMerge.setId(id);
            pAgentMerge.setMainAgentId(agUniqNum);//主要代理商ID
            pAgentMerge.setSubAgentId(agUniqNumT);//附代理商ID
            pAgentMerge.setMainAgentName(agName);//主代理商名称
            pAgentMerge.setSubAgentName(agNameT);//附代理商名称
            pAgentMerge.setMainHead(mainHeadT);//主代理商负责人
            pAgentMerge.setMainHeadMobile(subHeadMobileT);//主代理商负责人联系电话
            pAgentMerge.setSubnHead(subHeadT);//附代理商负责人
            pAgentMerge.setSubHeadMobile(subHeadMobileT);//附代理商负责人联系电话
            pAgentMerge.setMergeDate(df.format(new Date()));//合并日期
            profitAgentMergerService.editMergeRegect(pAgentMerge);
            return renderSuccess("代理商合并申请信息修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return renderError("系统异常，请联系维护人员！");
    }

    /**
     * 手动更改手刷、POS代理商名称
     * @param id
     * @return
     */
    @RequestMapping(value = "updateAgentName")
    @ResponseBody
    public AgentResult updateAgentName(String id) {
        try {
            BusActRel busActRel = busActRelService.findByBusIdAndType(id, BusActRelBusType.MERGE.name());
            AgentResult agentResult = profitAgentMergerService.updateAgentName(busActRel.getActivId());
            if(agentResult.isOK()){
                return AgentResult.ok(agentResult.getMsg());
            }else{
                return AgentResult.fail(agentResult.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AgentResult.fail("手动更改失败！");
    }

}
