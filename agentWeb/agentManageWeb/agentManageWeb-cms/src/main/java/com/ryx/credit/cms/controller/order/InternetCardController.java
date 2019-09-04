package com.ryx.credit.cms.controller.order;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.PoiUtil;
import com.ryx.credit.cms.util.WriteExcelDataDelegated;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.internet.pojo.OInternetCard;
import com.ryx.internet.pojo.OInternetCardImport;
import com.ryx.internet.pojo.OInternetCardPostpone;
import com.ryx.internet.pojo.OInternetRenewDetail;
import com.ryx.internet.service.InternetCardService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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
@RequestMapping("internet")
public class InternetCardController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(InternetCardController.class);
    @Autowired
    private InternetCardService internetCardService;

    @RequestMapping(value = "toInternetCardList")
    public String toInternetCardList(HttpServletRequest request,OInternetCard internetCard){
        request.setAttribute("internetRenewStatusList", InternetRenewStatus.getContentMap());
        request.setAttribute("internetCardStatusList", InternetCardStatus.getSelectMap());
        request.setAttribute("isAgent", com.alibaba.dubbo.common.utils.StringUtils.isNotEmpty(getAgentId())?true:false);
        return "order/internetCardList";
    }

    @RequestMapping(value = "internetCardList")
    @ResponseBody
    public Object internetCardList(HttpServletRequest request,OInternetCard internetCard){
        Page page = pageProcess(request);
        PageInfo pageInfo = internetCardService.internetCardList(internetCard, page,getAgentId());
        return pageInfo;
    }

    @RequestMapping(value = "toInternetCardAdd")
    public String toInternetCardAdd(HttpServletRequest request){
        request.setAttribute("cardImportType", CardImportType.getSelectMap());
        return "order/internetCardAdd";
    }

    @RequestMapping(value ="analysisFile")
    @ResponseBody
    public Object analysisFile(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request,String importType) {

        try {
            if(file==null){
                return renderError("请上传文件");
            }
            if(file.getSize()==0){
                return renderError("请上传文件");
            }
            if(StringUtils.isBlank(importType)){
                return renderError("导入类型错误");
            }
            String fileName = file.getOriginalFilename();
            String name = fileName.substring(0,fileName.lastIndexOf("."));
            if(!name.equals(CardImportType.getContentByValue(importType)) && !name.equals(CardImportType.getContentCodeByValue(importType))){
                return renderError("导入类型与导入文件不匹配");
            }
            String batchNo = IDUtils.getBatchNo();
            String fileUrl = uploadExcel(file, batchNo);
            internetCardService.importInternetCard(fileUrl,importType,getStringUserId(),batchNo);
            return renderSuccess(batchNo);
        } catch (MessageException e) {
            return renderError(e.getMsg());
        } catch (Exception e) {
            e.getStackTrace();
            return renderError(e.getLocalizedMessage());
        }
    }

    @RequestMapping(value = "toInternetCardImportList")
    public String toInternetCardImportList(HttpServletRequest request,OInternetCard internetCard){
        request.setAttribute("importStatusList",OInternetCardImportStatus.getSelectMap());
        request.setAttribute("cardImportTypeList",CardImportType.getSelectMap());
        return "order/internetCardImportList";
    }

    @RequestMapping(value = "internetCardImportList")
    @ResponseBody
    public Object internetCardImportList(HttpServletRequest request,OInternetCardImport internetCardImport){
        Page page = pageProcess(request);
        PageInfo pageInfo = internetCardService.internetCardImportList(internetCardImport, page);
        return pageInfo;
    }

    @RequestMapping("/exportInternetCard")
    public void exportInternetCard(HttpServletRequest request, HttpServletResponse response,OInternetCardImport internetCardImport){
        List<OInternetCardImport> resultList = internetCardService.exportErrorExcel(internetCardImport);
        String title = "";
        String column = "";
        if(internetCardImport.getImportType().equals(CardImportType.A.getValue())){
            title = "发卡方,物联卡号,ICCID,开户日期";
            column = "issuer,internetCardNum,iccidNum,openAccountTime";
        }else if(internetCardImport.getImportType().equals(CardImportType.B.getValue())){
            title = "发货方,发货日期,订单号,代理商名称,机具SN,iccid,收货人";
            column = "manufacturer,deliverTime,orderId,agentName,snNum,iccidNum,consignee";
        }else if(internetCardImport.getImportType().equals(CardImportType.C.getValue())){
            title = "订单编号,代理商名称,数量,发货日期,iccid开始号段,iccid结束号段";
            column = "orderId,agentName,snCount,deliverTime,beginSn,endSn";
        }else if(internetCardImport.getImportType().equals(CardImportType.D.getValue())){
            title = "订单号,公司名称,厂家,机具sn起始编号,机具sn终端编号,数量,发货日期";
            column = "orderId,agentName,manufacturer,beginSn,endSn,snCount,deliverTime";
        }else if(internetCardImport.getImportType().equals(CardImportType.E.getValue())){
            title = "ICCID,物联卡状态,开户日期,商户编号,最近交易日期,商户名称,代理商名称";
            column = "iccidNum,internetCardStatus,openAccountTime,merId,latelyPayTime,merName,agentName";
        }else if(internetCardImport.getImportType().equals(CardImportType.F.getValue())){
            title = "明细编号,申请编号,iccid号,订单号,机具SN,代理商ID,代理商名称,商户名称,商户编码,物联卡号,开户日期,到期日期,续费方式,轧差金额,续费金额,应扣金额,实扣金额,续费状态,创建时间,本次抵扣金额";
            column = "id,renewId,iccidNum,orderId,snNum,agentId,agentName,merName,merId,internetCardNum,openAccountTime,expireTime,renewWay,offsetAmt,renewAmt,oughtAmt,realityAmt,renewStatus,cTime,theRealityAmt";
        }
        List<Map<String, Object>> listMaps = new ArrayList<>();
        for (OInternetCardImport oInternetCardImport : resultList) {
            Map<String, Object> resultMap = new HashMap<>();
            if(internetCardImport.getImportType().equals(CardImportType.A.getValue())){
                OInternetCard internetCard = JsonUtil.jsonToPojo(oInternetCardImport.getImportMsg(), OInternetCard.class);
                resultMap.put("issuer",internetCard.getIssuer());
                resultMap.put("internetCardNum",internetCard.getInternetCardNum());
                resultMap.put("iccidNum",internetCard.getIccidNum());
                resultMap.put("openAccountTime",internetCard.getOpenAccountTime()!=null? DateUtil.formatDay(internetCard.getOpenAccountTime()):"");
            }else if(internetCardImport.getImportType().equals(CardImportType.B.getValue())){
                OInternetCard internetCard = JsonUtil.jsonToPojo(oInternetCardImport.getImportMsg(), OInternetCard.class);
                resultMap.put("manufacturer",internetCard.getManufacturer());
                resultMap.put("deliverTime",internetCard.getDeliverTime()!=null? DateUtil.formatDay(internetCard.getDeliverTime()):"");
                resultMap.put("orderId",internetCard.getOrderId());
                resultMap.put("agentName",internetCard.getAgentName());
                resultMap.put("snNum",internetCard.getSnNum());
                resultMap.put("iccidNum",internetCard.getIccidNum());
                resultMap.put("consignee",internetCard.getConsignee());
            }else if(internetCardImport.getImportType().equals(CardImportType.C.getValue())){
                OInternetCard internetCard = JsonUtil.jsonToPojo(oInternetCardImport.getImportMsg(), OInternetCard.class);
                resultMap.put("orderId",internetCard.getOrderId());
                resultMap.put("agentName",internetCard.getAgentName());
                resultMap.put("snCount",internetCard.getSnCount());
                resultMap.put("deliverTime",internetCard.getDeliverTime()!=null? DateUtil.formatDay(internetCard.getDeliverTime()):"");
                resultMap.put("beginSn",internetCard.getBeginSn());
                resultMap.put("endSn",internetCard.getEndSn());
            }else if(internetCardImport.getImportType().equals(CardImportType.D.getValue())){
                OInternetCard internetCard = JsonUtil.jsonToPojo(oInternetCardImport.getImportMsg(), OInternetCard.class);
                resultMap.put("orderId",internetCard.getOrderId());
                resultMap.put("agentName",internetCard.getAgentName());
                resultMap.put("manufacturer",internetCard.getManufacturer());
                resultMap.put("beginSn",internetCard.getBeginSn());
                resultMap.put("endSn",internetCard.getEndSn());
                resultMap.put("snCount",internetCard.getSnCount());
                resultMap.put("deliverTime",internetCard.getDeliverTime()!=null? DateUtil.formatDay(internetCard.getDeliverTime()):"");
            }else if(internetCardImport.getImportType().equals(CardImportType.E.getValue())){
                OInternetCard internetCard = JsonUtil.jsonToPojo(oInternetCardImport.getImportMsg(), OInternetCard.class);
                resultMap.put("iccidNum",internetCard.getIccidNum());
                resultMap.put("internetCardStatus", InternetCardStatus.getContentByValue(internetCard.getInternetCardStatus()));
                resultMap.put("openAccountTime",internetCard.getOpenAccountTime());
                resultMap.put("merId",internetCard.getMerId());
                resultMap.put("latelyPayTime",internetCard.getLatelyPayTime());
                resultMap.put("merName",internetCard.getMerName());
                resultMap.put("agentName",internetCard.getAgentName());
            }else if(internetCardImport.getImportType().equals(CardImportType.F.getValue())){
                OInternetRenewDetail internetRenewDetail = JsonUtil.jsonToPojo(oInternetCardImport.getImportMsg(), OInternetRenewDetail.class);
                resultMap.put("id",internetRenewDetail.getId());
                resultMap.put("renewId",internetRenewDetail.getRenewId().equals("null")?"":internetRenewDetail.getRenewId());
                resultMap.put("iccidNum",internetRenewDetail.getIccidNum().equals("null")?"":internetRenewDetail.getIccidNum());
                resultMap.put("orderId",internetRenewDetail.getOrderId().equals("null")?"":internetRenewDetail.getOrderId());
                resultMap.put("snNum",internetRenewDetail.getSnNum().equals("null")?"":internetRenewDetail.getSnNum());
                resultMap.put("agentId",internetRenewDetail.getAgentId().equals("null")?"":internetRenewDetail.getAgentId());
                resultMap.put("agentName",internetRenewDetail.getAgentName().equals("null")?"":internetRenewDetail.getAgentName());
                resultMap.put("merName",internetRenewDetail.getMerName().equals("null")?"":internetRenewDetail.getMerName());
                resultMap.put("merId",internetRenewDetail.getMerId().equals("null")?"":internetRenewDetail.getMerId());
                resultMap.put("internetCardNum",internetRenewDetail.getInternetCardNum().equals("null")?"":internetRenewDetail.getInternetCardNum());
                resultMap.put("openAccountTime",internetRenewDetail.getOpenAccountTime()!=null? DateUtil.formatDay(internetRenewDetail.getOpenAccountTime()):"");
                resultMap.put("expireTime",internetRenewDetail.getExpireTime()!=null? DateUtil.formatDay(internetRenewDetail.getExpireTime()):"");
                resultMap.put("renewWay",InternetRenewWay.getContentByValue(internetRenewDetail.getRenewWay()));
                resultMap.put("offsetAmt",internetRenewDetail.getOffsetAmt()==null?"":String.valueOf(internetRenewDetail.getOffsetAmt()));
                resultMap.put("renewAmt",internetRenewDetail.getRenewAmt()==null?"":String.valueOf(internetRenewDetail.getRenewAmt()));
                resultMap.put("oughtAmt",internetRenewDetail.getOughtAmt()==null?"":String.valueOf(internetRenewDetail.getOughtAmt()));
                resultMap.put("realityAmt",internetRenewDetail.getRealityAmt()==null?"":String.valueOf(internetRenewDetail.getRealityAmt()));
                resultMap.put("renewStatus",InternetRenewStatus.getContentByValue(internetRenewDetail.getRenewStatus()));
                resultMap.put("cTime",DateUtil.formatDay(internetRenewDetail.getcTime()));
                resultMap.put("theRealityAmt",internetRenewDetail.getTheRealityAmt()==null?"":String.valueOf(internetRenewDetail.getTheRealityAmt()));
            }
            listMaps.add(resultMap);
        }
        Map<String, Object> param = new HashMap<>(5);
        param.put("path", AppConfig.getProperty("export.path"));
        param.put("title", title);
        param.put("column", column);
        param.put("dataList", listMaps);
        param.put("response", response);
        ExcelUtil.downLoadExcel(param);
    }


    @RequestMapping("/internetCardexport")
    public void internetCardexport(HttpServletRequest request, HttpServletResponse response,OInternetCard internetCard) throws Exception {

        Integer totalRowCount = internetCardService.queryInternetCardCount(internetCard,getAgentId());
        Integer perSheetRowCount = totalRowCount;
        Integer perWriteRowCount = 20000;
        // 导出EXCEL文件名称
        String filaName = "物联网卡信息";
        // 标题
        String[] titles = {"iccid号", "批次号", "sn号", "发货时间", "收货人", "订单号", "代理商编码","代理商名称",
                            "物联卡卡号", "发卡方", "开户日期", "到期时间", "物联卡状态", "商户编号",
                            "最近交易日期", "商户名称", "厂商", "是否需续费", "是否需关停", "关停原因", "续费状态"};
        // 开始导入
        PoiUtil.exportExcelToWebsite(response, totalRowCount, filaName, titles, new WriteExcelDataDelegated() {
            @Override
            public void writeExcelData(SXSSFSheet eachSheet, Integer startRowCount, Integer endRowCount, Integer currentPage, Integer pageSize) throws Exception {
                Page page = pageProcess(currentPage,pageSize);
                List<OInternetCard> oInternetCards = internetCardService.queryInternetCardList(internetCard,page,getAgentId());
                if (!CollectionUtils.isEmpty(oInternetCards)) {
                    for (int i = startRowCount; i <= endRowCount; i++) {
                        SXSSFRow eachDataRow = eachSheet.createRow(i);
                        if ((i - startRowCount) < oInternetCards.size()) {
                            OInternetCard internetCard = oInternetCards.get(i - startRowCount);
                            eachDataRow.createCell(0).setCellValue(internetCard.getIccidNum());
                            eachDataRow.createCell(1).setCellValue(internetCard.getBatchNum());
                            eachDataRow.createCell(2).setCellValue(internetCard.getSnNum());
                            eachDataRow.createCell(3).setCellValue(internetCard.getDeliverTime()!=null? DateUtil.formatDay(internetCard.getDeliverTime()):"");
                            eachDataRow.createCell(4).setCellValue(internetCard.getConsignee());
                            eachDataRow.createCell(5).setCellValue(internetCard.getOrderId());
                            eachDataRow.createCell(6).setCellValue(internetCard.getAgentId());
                            eachDataRow.createCell(7).setCellValue(internetCard.getAgentName());
                            eachDataRow.createCell(8).setCellValue(internetCard.getInternetCardNum());
                            eachDataRow.createCell(9).setCellValue(internetCard.getIssuer());
                            eachDataRow.createCell(10).setCellValue(internetCard.getOpenAccountTime()!=null? DateUtil.formatDay(internetCard.getOpenAccountTime()):"");
                            eachDataRow.createCell(11).setCellValue(internetCard.getExpireTime()!=null? DateUtil.formatDay(internetCard.getExpireTime()):"");
                            eachDataRow.createCell(12).setCellValue(internetCard.getInternetCardStatus()==null?"":InternetCardStatus.getContentByValue(internetCard.getInternetCardStatus()));
                            eachDataRow.createCell(13).setCellValue(internetCard.getMerId());
                            eachDataRow.createCell(14).setCellValue(internetCard.getLatelyPayTime());
                            eachDataRow.createCell(15).setCellValue(internetCard.getMerName());
                            eachDataRow.createCell(16).setCellValue(internetCard.getManufacturer());
                            eachDataRow.createCell(17).setCellValue(internetCard.getRenew()==null?"":internetCard.getRenew().compareTo(BigDecimal.ONE)==0?"是":"否");
                            eachDataRow.createCell(18).setCellValue(internetCard.getStop()==null?"":internetCard.getStop().compareTo(BigDecimal.ONE)==0?"是":"否");
                            eachDataRow.createCell(19).setCellValue(internetCard.getStopReason());
                            eachDataRow.createCell(20).setCellValue(internetCard.getRenewStatus()==null?"":InternetRenewStatus.getContentByValue(internetCard.getRenewStatus()));
                        }
                    }
                }
            }
        },perSheetRowCount,perWriteRowCount);
    }

    @RequestMapping(value = "updateMechIsNull")
    @ResponseBody
    public AgentResult updateMechIsNull(){
        internetCardService.taskUpdateMechIsNull();
        return AgentResult.ok();
    }


    @RequestMapping(value = "toInternetCardPostpone")
    public String toInternetCardPostpone(HttpServletRequest request,String iccidNum){
        request.setAttribute("iccidNum",iccidNum);
        return "order/internetCardPostpone";
    }

    @RequestMapping(value = "internetCardPostpone")
    @ResponseBody
    public AgentResult internetCardPostpone(HttpServletRequest request,OInternetCardPostpone internetCardPostpone){
        try {
            internetCardService.internetCardPostpone(internetCardPostpone,getStringUserId());
        } catch (MessageException e) {
            e.printStackTrace();
            return AgentResult.fail(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return AgentResult.fail(e.getMessage());
        }
        return AgentResult.ok("设置成功");
    }

    @RequestMapping(value = "toInternetCardPostponeSee")
    public String toInternetCardPostponeSee(HttpServletRequest request,OInternetCardPostpone internetCardPostpone){
        request.setAttribute("internetCardPostpone",internetCardPostpone);
        return "order/internetCardPostponeSee";
    }


    @RequestMapping(value = "internetCardPostponeSee")
    @ResponseBody
    public Object internetCardPostponeSee(HttpServletRequest request,OInternetCardPostpone internetCardPostpone){
        Page page = pageProcess(request);
        PageInfo pageInfo  = internetCardService.queryInternetCardPostponeList(internetCardPostpone,page);
        return pageInfo;
    }

}
