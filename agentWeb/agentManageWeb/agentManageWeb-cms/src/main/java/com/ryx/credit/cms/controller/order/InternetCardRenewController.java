package com.ryx.credit.cms.controller.order;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.PoiUtil;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.cms.util.WriteExcelDataDelegated;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.agent.PayComp;
import com.ryx.credit.pojo.admin.order.OCashReceivables;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.order.OCashReceivablesService;
import com.ryx.internet.pojo.*;
import com.ryx.internet.service.OInternetRenewService;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2018/12/4 16:22
 * @Param
 * @return
 **/
@Controller
@RequestMapping("internetRenew")
public class InternetCardRenewController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(InternetCardRenewController.class);
    @Autowired
    private OInternetRenewService internetRenewService;
    @Autowired
    private OCashReceivablesService oCashReceivablesService;


    @RequestMapping(value = "toInternetRenewList")
    public String toInternetRenewList(HttpServletRequest request){
        request.setAttribute("internetRenewWayList", internetRenewService.getInternetRenewWay(getUserId()));
        List<Dict> agStatusList = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.AG_STATUS_I.name());
        for(int i=0;i<agStatusList.size();i++){
            Dict dict = agStatusList.get(i);
            if(dict.getdItemvalue().equals(AgStatus.Create.status.toString()) || dict.getdItemvalue().equals(AgStatus.Reject.status.toString())){
                agStatusList.remove(dict);
            }
        }
        request.setAttribute("agStatusList", agStatusList);
        request.setAttribute("isAgent", StringUtils.isNotEmpty(getAgentId())?true:false);
        return "order/internetRenewList";
    }

    @RequestMapping(value = "internetRenewList")
    @ResponseBody
    public Object internetRenewList(HttpServletRequest request,OInternetRenew internetRenew){
        Page page = pageProcess(request);
        PageInfo pageInfo = internetRenewService.internetRenewList(internetRenew, page,getAgentId());
        return pageInfo;
    }

    @RequestMapping(value = "toInternetCardRenewQuery")
    public String toInternetCardRenewQuery(HttpServletRequest request) {
        optionsData(request);
        String id = request.getParameter("id");
        String cloReviewStatus = request.getParameter("cloReviewStatus");
        OInternetRenew oInternetRenew = internetRenewService.selectByPrimaryKey(id);
        request.setAttribute("oInternetRenew",oInternetRenew);
        List<Map<String, Object>> actRecordList = queryApprovalRecord(id, BusActRelBusType.cardRenew.name());
        request.setAttribute("actRecordList", actRecordList);
        //线下打款信息
        List<OCashReceivables> listoCashReceivables  = oCashReceivablesService.query(null,null, CashPayType.INTERNETRENEW,id, null);
        request.setAttribute("paylist", listoCashReceivables);
        //收款公司
        List<PayComp> payComp_list = ServiceFactory.apaycompService.recCompList();
        request.setAttribute("recCompList", payComp_list);
        request.setAttribute("payTypeSelect",PayType.getYHHKOption());
        request.setAttribute("busIdImg", id);
        request.setAttribute("busTypeImg", BusActRelBusType.cardRenew.name());
        request.setAttribute("internetRenewWayList", internetRenewService.getInternetRenewWay(getUserId()));
        request.setAttribute("reviewStatus",cloReviewStatus);
        return "order/internetCardRenewQuery";
    }


    @RequestMapping(value = "toInternetCardRenewApproval")
    public String toInternetCardRenewApproval(HttpServletRequest request) {
        optionsData(request);
        String id = request.getParameter("busId");
        String taskId = request.getParameter("taskId");
        String cloReviewStatus = request.getParameter("cloReviewStatus");
        OInternetRenew oInternetRenew = internetRenewService.selectByPrimaryKey(id);
        request.setAttribute("oInternetRenew",oInternetRenew);
        List<Map<String, Object>> actRecordList = queryApprovalRecord(id, BusActRelBusType.cardRenew.name());
        request.setAttribute("actRecordList", actRecordList);
        //线下打款信息
        List<OCashReceivables> listoCashReceivables  = oCashReceivablesService.query(null,null, CashPayType.INTERNETRENEW,id, null);
        request.setAttribute("paylist", listoCashReceivables);
        request.setAttribute("paylist_approve", listoCashReceivables);
        //收款公司
        List<PayComp> payComp_list = ServiceFactory.apaycompService.recCompList();
        request.setAttribute("recCompList", payComp_list);
        request.setAttribute("payTypeSelect",PayType.getYHHKOption());
        request.setAttribute("busIdImg", id);
        request.setAttribute("busTypeImg", BusActRelBusType.cardRenew.name());
        request.setAttribute("internetRenewWayList", internetRenewService.getInternetRenewWay(getUserId()));
        request.setAttribute("reviewStatus",cloReviewStatus);
        request.setAttribute("taskId",taskId);
        request.setAttribute("paylist_model", Calendar.getInstance().getTime().getTime());
        return "activity/internetCardRenewApproval";
    }


    @RequestMapping(value = "toInternetRenewDetailList")
    public String toInternetCardList(HttpServletRequest request){
        request.setAttribute("internetRenewWayList", internetRenewService.getInternetRenewWay(getUserId()));
        request.setAttribute("internetRenewStatusList", InternetRenewStatus.getContentMap());
        request.setAttribute("isAgent", StringUtils.isNotEmpty(getAgentId())?true:false);
        return "order/internetRenewDetailList";
    }


    @RequestMapping(value = "internetRenewDetailList")
    @ResponseBody
    public Object internetRenewDetailList(HttpServletRequest request,OInternetRenewDetail internetRenewDetail){
        Page page = pageProcess(request);
        PageInfo pageInfo = internetRenewService.internetRenewDetailList(internetRenewDetail, page,getAgentId());
        return pageInfo;
    }


    @RequestMapping(value = "toInternetRenewAdd")
    public String toInternetRenewAdd(HttpServletRequest request){
        Dict cardAmt = ServiceFactory.dictOptionsService.findDictByName(DictGroup.ORDER.name(), DictGroup.INTERNET_RENEW.name(), DictGroup.CARD_AMT.name());
        Dict offsetAmt = ServiceFactory.dictOptionsService.findDictByName(DictGroup.ORDER.name(), DictGroup.INTERNET_RENEW.name(), DictGroup.OFFSET_AMT.name());
        request.setAttribute("cardAmt", cardAmt.getdItemvalue());
        request.setAttribute("offsetAmt", offsetAmt.getdItemvalue());
        request.setAttribute("internetRenewWayList", internetRenewService.getInternetRenewWay(getUserId()));
        request.setAttribute("agentId",request.getParameter("agentId"));
        //打款记录参数 begin
        request.setAttribute("payTypeSelect",PayType.getYHHKOption());
        request.setAttribute("paylist_model", Calendar.getInstance().getTime().getTime());
        List<PayComp> payComp_list = ServiceFactory.apaycompService.recCompList();
        request.setAttribute("recCompList", payComp_list);
        //打款记录参数 end
        return "order/internetRenewAdd";
    }

    @ResponseBody
    @RequestMapping(value = {"internetRenewSave"}, method = RequestMethod.POST)
    public Object internetRenewSave(@RequestBody InternetCardVo internetCardVo) {
        try {
            String iccidNumIdsStr = internetCardVo.getInternetRenew().getIccidNumIds();
            List<String> iccidNumIds = Arrays.asList(iccidNumIdsStr.split(","));
            AgentResult agentResult = internetRenewService.saveAndApprove(internetCardVo.getInternetRenew(), iccidNumIds, getStringUserId(), internetCardVo.getoCashReceivablesVoList());
            if(agentResult.isOK()){
                return AgentResult.ok("成功");
            }
        }catch (MessageException e) {
            e.printStackTrace();
            return AgentResult.fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return AgentResult.fail(e.getLocalizedMessage());
        }
        return AgentResult.fail("提交失败");
    }


    /**
     * 处理任务
     * @return
     */
    @ResponseBody
    @RequestMapping("taskApproval")
    public ResultVO taskApproval(@RequestBody AgentVo agentVo){
        try {
            AgentResult result = internetRenewService.approvalTask(agentVo, String.valueOf(getUserId()));
            if(result.isOK()){
                return ResultVO.success("处理成功");
            }else{
                return ResultVO.fail("处理失败");
            }
        } catch (MessageException e) {
            e.printStackTrace();
            return ResultVO.fail(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.fail(e.getMessage());
        }
    }


    @RequestMapping("/internetCardRenewDetailExport")
    public void internetCardRenewDetailExport(HttpServletRequest request, HttpServletResponse response,OInternetRenewDetail internetRenewDetail) throws Exception {

        Integer totalRowCount = internetRenewService.queryInternetRenewDetailCount(internetRenewDetail,getAgentId());
        Integer perSheetRowCount = totalRowCount;
        Integer perWriteRowCount = 2000;
        // 导出EXCEL文件名称
        String filaName = "物联网卡续费信息";
        // 标题
        String[] titles = {"明细编号", "申请编号", "iccid号", "订单号", "机具SN", "代理商ID", "代理商名称",
                            "商户名称", "商户编码", "物联卡号", "开户日期", "到期日期", "续费方式",
                            "轧差金额", "续费金额", "应扣金额", "实扣金额","续费状态","创建时间","本次抵扣金额"};
        // 开始导入
        PoiUtil.exportExcelToWebsite(response, totalRowCount, filaName, titles, new WriteExcelDataDelegated() {
            @Override
            public void writeExcelData(SXSSFSheet eachSheet, Integer startRowCount, Integer endRowCount, Integer currentPage, Integer pageSize) throws Exception {
                Page page = pageProcess(currentPage,pageSize);
                List<OInternetRenewDetail> oInternetCards = internetRenewService.queryInternetRenewDetailList(internetRenewDetail,page,getAgentId());
                if (!CollectionUtils.isEmpty(oInternetCards)) {
                    for (int i = startRowCount; i <= endRowCount; i++) {
                        SXSSFRow eachDataRow = eachSheet.createRow(i);
                        if ((i - startRowCount) < oInternetCards.size()) {
                            OInternetRenewDetail internetRenewDetail = oInternetCards.get(i - startRowCount);
                            eachDataRow.createCell(0).setCellValue(internetRenewDetail.getId());
                            eachDataRow.createCell(1).setCellValue(internetRenewDetail.getRenewId());
                            eachDataRow.createCell(2).setCellValue(internetRenewDetail.getIccidNum());
                            eachDataRow.createCell(3).setCellValue(internetRenewDetail.getOrderId());
                            eachDataRow.createCell(4).setCellValue(internetRenewDetail.getSnNum());
                            eachDataRow.createCell(5).setCellValue(internetRenewDetail.getAgentId());
                            eachDataRow.createCell(6).setCellValue(internetRenewDetail.getAgentName());
                            eachDataRow.createCell(7).setCellValue(internetRenewDetail.getMerName());
                            eachDataRow.createCell(8).setCellValue(internetRenewDetail.getMerId());
                            eachDataRow.createCell(9).setCellValue(internetRenewDetail.getInternetCardNum());
                            eachDataRow.createCell(10).setCellValue(internetRenewDetail.getOpenAccountTime()!=null? DateUtil.formatDay(internetRenewDetail.getOpenAccountTime()):"");
                            eachDataRow.createCell(11).setCellValue(internetRenewDetail.getExpireTime()!=null? DateUtil.formatDay(internetRenewDetail.getExpireTime()):"");
                            eachDataRow.createCell(12).setCellValue(InternetRenewWay.getContentByValue(internetRenewDetail.getRenewWay()));
                            eachDataRow.createCell(13).setCellValue(internetRenewDetail.getOffsetAmt()==null?"":String.valueOf(internetRenewDetail.getOffsetAmt()));
                            eachDataRow.createCell(14).setCellValue(internetRenewDetail.getRenewAmt()==null?"":String.valueOf(internetRenewDetail.getRenewAmt()));
                            eachDataRow.createCell(15).setCellValue(internetRenewDetail.getOughtAmt()==null?"":String.valueOf(internetRenewDetail.getOughtAmt()));
                            eachDataRow.createCell(16).setCellValue(internetRenewDetail.getRealityAmt()==null?"":String.valueOf(internetRenewDetail.getRealityAmt()));
                            eachDataRow.createCell(17).setCellValue(InternetRenewStatus.getContentByValue(internetRenewDetail.getRenewStatus()));
                            eachDataRow.createCell(18).setCellValue(DateUtil.formatDay(internetRenewDetail.getcTime()));
                            eachDataRow.createCell(19).setCellValue("");
                        }
                    }
                }
            }
        },perSheetRowCount,perWriteRowCount);
    }


    @RequestMapping(value = "toInternetRenewOffsetList")
    public String toInternetRenewOffsetList(HttpServletRequest request,InternetRenewOffset internetRenewOffset){
        request.setAttribute("internetRenewOffset",internetRenewOffset);
        request.setAttribute("isAgent", StringUtils.isNotEmpty(getAgentId())?true:false);
        return "order/internetRenewOffsetList";
    }

    @RequestMapping(value = "internetRenewOffsetList")
    @ResponseBody
    public Object internetRenewOffsetList(HttpServletRequest request,InternetRenewOffset internetRenewOffset){
        Page page = pageProcess(request);
        PageInfo pageInfo = internetRenewService.internetRenewOffsetList(internetRenewOffset, page,getAgentId());
        return pageInfo;
    }


    @RequestMapping(value = "renewVerify")
    @ResponseBody
    public AgentResult renewVerify(HttpServletRequest request,String iccidNumIds){
        try {
            internetRenewService.renewVerify(iccidNumIds);
        } catch (MessageException e) {
            e.printStackTrace();
            return AgentResult.fail(e.getMsg());
        }
        return AgentResult.ok();
    }

    @RequestMapping(value = "toInternetRenewOffsetDetailList")
    public String toInternetRenewOffsetDetailList(HttpServletRequest request,InternetRenewOffsetDetail internetRenewOffsetDetail){
        request.setAttribute("internetRenewOffset",internetRenewOffsetDetail);
        request.setAttribute("isAgent", StringUtils.isNotEmpty(getAgentId())?true:false);
        return "order/internetRenewOffsetDetailList";
    }

    @RequestMapping(value = "internetRenewOffsetDetailList")
    @ResponseBody
    public Object internetRenewOffsetDetailList(HttpServletRequest request,InternetRenewOffsetDetail internetRenewOffsetDetail){
        Page page = pageProcess(request);
        PageInfo pageInfo = internetRenewService.internetRenewOffsetDetailList(internetRenewOffsetDetail, page,getAgentId());
        return pageInfo;
    }


    @RequestMapping("/internetRenewOffsetDetailExport")
    public void internetRenewOffsetDetailExport(HttpServletRequest request, HttpServletResponse response,InternetRenewOffsetDetail internetRenewOffsetDetail) throws Exception {

        Integer totalRowCount = internetRenewService.queryInternetRenewOffsetDetailCount(internetRenewOffsetDetail,getAgentId());
        Integer perSheetRowCount = totalRowCount;
        Integer perWriteRowCount = 100;
        // 导出EXCEL文件名称
        String filaName = "物联网卡轧差流水信息";
        // 标题
        String[] titles = {"明细流水号", "轧差流水号", "申请编号", "明细编号", "iccid号", "代理商ID", "代理商名称",
                "商户编码", "商户名称", "预轧差金额", "已轧差金额", "今日轧差金额", "清算状态", "处理日期", "处理时间"};
        // 开始导入
        PoiUtil.exportExcelToWebsite(response, totalRowCount, filaName, titles, new WriteExcelDataDelegated() {
            @Override
            public void writeExcelData(SXSSFSheet eachSheet, Integer startRowCount, Integer endRowCount, Integer currentPage, Integer pageSize) throws Exception {
                Page page = pageProcess(currentPage,pageSize);
                List<InternetRenewOffsetDetail> internetRenewOffsetDetails = internetRenewService.queryInternetRenewOffsetDetailList(internetRenewOffsetDetail,page,getAgentId());
                if (!CollectionUtils.isEmpty(internetRenewOffsetDetails)) {
                    for (int i = startRowCount; i <= endRowCount; i++) {
                        SXSSFRow eachDataRow = eachSheet.createRow(i);
                        if ((i - startRowCount) < internetRenewOffsetDetails.size()) {
                            InternetRenewOffsetDetail internetRenewOffsetDetail = internetRenewOffsetDetails.get(i - startRowCount);
                            eachDataRow.createCell(0).setCellValue(internetRenewOffsetDetail.getId());
                            eachDataRow.createCell(1).setCellValue(internetRenewOffsetDetail.getFlowId());
                            eachDataRow.createCell(2).setCellValue(internetRenewOffsetDetail.getRenewId());
                            eachDataRow.createCell(3).setCellValue(internetRenewOffsetDetail.getRenewDetailId());
                            eachDataRow.createCell(4).setCellValue(internetRenewOffsetDetail.getIccidNum());
                            eachDataRow.createCell(5).setCellValue(internetRenewOffsetDetail.getAgentId());
                            eachDataRow.createCell(6).setCellValue(internetRenewOffsetDetail.getAgentName());
                            eachDataRow.createCell(7).setCellValue(internetRenewOffsetDetail.getMerId());
                            eachDataRow.createCell(8).setCellValue(internetRenewOffsetDetail.getMerName());
                            eachDataRow.createCell(9).setCellValue(internetRenewOffsetDetail.getOffsetAmt()==null?"":String.valueOf(internetRenewOffsetDetail.getOffsetAmt()));
                            eachDataRow.createCell(10).setCellValue(internetRenewOffsetDetail.getAlreadyOffsetAmt()==null?"":String.valueOf(internetRenewOffsetDetail.getAlreadyOffsetAmt()));
                            eachDataRow.createCell(11).setCellValue(internetRenewOffsetDetail.getTodayOffsetAmt()==null?"":String.valueOf(internetRenewOffsetDetail.getTodayOffsetAmt()));
                            eachDataRow.createCell(12).setCellValue(InternetCleanStatus.getContentByValue(internetRenewOffsetDetail.getCleanStatus()));
                            eachDataRow.createCell(13).setCellValue(internetRenewOffsetDetail.getProcessDate());
                            eachDataRow.createCell(14).setCellValue(internetRenewOffsetDetail.getProcessTime());
                        }
                    }
                }
            }
        },perSheetRowCount,perWriteRowCount);
    }

}

