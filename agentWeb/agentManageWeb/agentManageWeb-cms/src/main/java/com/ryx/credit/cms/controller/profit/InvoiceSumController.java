package com.ryx.credit.cms.controller.profit;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ExcelUtil;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.pojo.InvoiceSum;
import com.ryx.credit.profit.service.IInvoiceSumService;
import com.ryx.credit.service.IUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/profit/InvoiceSumController")
public class InvoiceSumController extends BaseController {

    private static final Logger LOGGER = Logger.getLogger(InvoiceSumController.class);
    @Autowired
    IInvoiceSumService invoiceSumService;
    @Autowired
    private IUserService userService;

    private  String JD_PATH = AppConfig.getProperty("jindie.path");
    private String CLIENT_ID =  AppConfig.getProperty("jindie.clientId");
    private String CLIENT_SECRET = AppConfig.getProperty("jindie.clientSecret");
    private  String TIN = AppConfig.getProperty("rui.tin");


    /**
     * 加载欠票汇总展示页面
     *
     * @return
     */
    @RequestMapping("/InvoiceSumPage")
    public String InvoiceSumPageInit(ModelMap modelMap){
        LOGGER.info("加载欠票汇总展示页面。。。。。。。。。。");
        try {
            List<Map<String, Object>> list = userService.orgCode(getUserId());
            String eid = getAgentId();
            String sourceType = "socket";
            String ticketParam = "0000";
            long timestamp = System.currentTimeMillis();
            double random = new Random(15).nextDouble();
            String sign = encodeByMd5(CLIENT_ID+CLIENT_SECRET+TIN+eid+timestamp);
            String content = JD_PATH + "?timestamp="+timestamp+"&client_id="+CLIENT_ID+"&tin="+TIN+"&sign="+sign+"&eid="+
                    eid+"&random="+random+"&sourceType="+sourceType+"&ticketParam="+ticketParam;
            modelMap.put("timestamp",timestamp);
            modelMap.put("random",random);
            modelMap.put("invoice_Result",AppConfig.getProperty("invoice.result"));
            modelMap.put("content",content);
            if(list.size()>0){
                if( Objects.equals(list.get(0).get("ORGANIZATIONCODE"), "agent")){
                    modelMap.put("numType","AG");
                }else{
                    modelMap.put("numType","FI");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        String finalStatus = ServiceFactory.redisService.getValue("commitFinal");
        if (StringUtils.isBlank(finalStatus)) {
            modelMap.addAttribute("noEdit", "0");
        } else {
            modelMap.addAttribute("noEdit", "1");
        }
        return "profit/InvoiceDetail/InvoiceSumList";
    }


    /**
     * 获取数据列表
     */
    @RequestMapping(value = "/getInvoiceSumList")
    @ResponseBody
    public Object getInvoiceSumList(HttpServletRequest request) {
        LOGGER.info("获取数据列表。");
        Page page = pageProcess(request);
      /*  PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(), null, null);*/
        TreeMap<String, String> param = getRequestParameter(request);

        List<Map<String, Object>>  list = userService.orgCode(getUserId());
        if(list == null || list.isEmpty()){
            return new PageInfo();
        }
        Map<String,Object> map = list.get(0);

        if( Objects.equals(map.get("ORGANIZATIONCODE"), "agent")){
            map = null;
            param.put("agentId",getAgentId());
        }else if( Objects.equals(map.get("ORGANIZATIONCODE"), "finance")){
            map = null;
        }
        return invoiceSumService.selectByMap(page,param,map);
    }

    /**
     * 获取数据列表
     */
    @RequestMapping(value = "/gotoAdjustInvoiceSumPage")
    @ResponseBody
    public ModelAndView gotoAdjustInvoiceSumPage(String id) {
        InvoiceSum invoiceSum = invoiceSumService.selectByPrimaryKey(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id", invoiceSum.getId());
        modelAndView.addObject("agentName", invoiceSum.getAgentName());
        modelAndView.addObject("agentId", invoiceSum.getAgentId());
        modelAndView.setViewName("/profit/InvoiceDetail/AdjustInvoiceSumPage");
        return modelAndView;
    }

    /**
     * 设置调整金额
     *
     * @return
     */
    @RequestMapping("/setInvoiceSumAdjustAMT")
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public ResultVO setInvoiceSumAdjustAMT(HttpServletRequest request) {
        InvoiceSum invoiceSum = invoiceSumService.selectByPrimaryKey(request.getParameter("id"));
        BigDecimal oldOwnInvoice = invoiceSum.getOwnInvoice() == null ? BigDecimal.ZERO : invoiceSum.getOwnInvoice();
        invoiceSum.setAdjustAmt(invoiceSum.getAdjustAmt()==null?BigDecimal.ZERO:invoiceSum.getAdjustAmt().add(new BigDecimal(request.getParameter("adjustAmt"))));
        invoiceSum.setOwnInvoice((invoiceSum.getPreLeftAmt()==null?BigDecimal.ZERO:invoiceSum.getPreLeftAmt()).add(invoiceSum.getDayBackAmt()).add(invoiceSum.getDayProfitAmt()).add(invoiceSum.getPreProfitMonthAmt()).subtract(invoiceSum.getAddInvoiceAmt()==null?BigDecimal.ZERO:invoiceSum.getAddInvoiceAmt()).add(invoiceSum.getAdjustAmt()==null?BigDecimal.ZERO:invoiceSum.getAdjustAmt()));
        invoiceSum.setAdjustReson(request.getParameter("adjustReson"));
        String adjustAccount = getShiroUser().getLoginName();//设置调整人和调整时间
        invoiceSum.setAdjustAccount(adjustAccount);
        Date now = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        invoiceSum.setAdjustTime(f.format(now));
        try {
            //欠票金额大于0，通过调整后小于等于0时触发；
            if ((oldOwnInvoice.compareTo(BigDecimal.ZERO) == 1) && (invoiceSum.getOwnInvoice().compareTo(BigDecimal.ZERO) != 1)) {
                Map<String, Object> mapParam = new HashMap();
                mapParam.put("AGENT_ID", invoiceSum.getAgentId());
                invoiceSumService.AdjustFreeze(mapParam);
                invoiceSum.setInvoiceStatus("99");
                //欠票金额小于等于0，通过调整后大于0时触发；
            } else if ((oldOwnInvoice.compareTo(BigDecimal.ZERO) != 1) && (invoiceSum.getOwnInvoice().compareTo(BigDecimal.ZERO) == 1)) {
                Map<String, Object> mapParam = new HashMap();
                mapParam.put("AGENT_ID", invoiceSum.getAgentId());
                mapParam.put("AGENT_NAME", invoiceSum.getAgentName());
                invoiceSumService.againFreeze(mapParam);
                invoiceSum.setInvoiceStatus("00");
            }
            invoiceSumService.updateByPrimaryKeySelective(invoiceSum);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("调整失败。");
            return ResultVO.fail("调整失败。");
        }
        return ResultVO.success("调整成功。");
    }



    /**
     * 跳转调整详情页
     * @param request
     * @return
     */
    @RequestMapping("/examineAdjust")
    public ModelAndView examineAdjust(HttpServletRequest request){
        ModelAndView view=new ModelAndView();
        Map<String,Object> map=new HashMap<>();
        map.put("id",request.getParameter("id"));
        view.addObject("param",map);
        view.setViewName("profit/InvoiceDetail/adjustSumDetail");
        return view;
    }
    /**
     * 跳转调整详情页
     * @return
     */
    @RequestMapping("/adjustdetail")
    @ResponseBody
    public List<InvoiceSum> adjustdetail(String id){
        InvoiceSum invoiceSum = invoiceSumService.selectByPrimaryKey(id);
        List<InvoiceSum> list = new ArrayList<>();
        list.add(invoiceSum);
        return list;
    }

    /**
     * 欠票模板导出
     *
     * @param response
     * @Author chen liang
     * @Date 2019 06/25
     */
    @RequestMapping("exportTemplate")
    @ResponseBody
    public void exportTemplate(HttpServletResponse response, HttpServletRequest request) throws Exception {
        String filePath = request.getSession().getServletContext().getRealPath("/") + "ExcelTemplate/导入欠票模板.xlsx";
        String fileName = "导入欠票模板.xlsx";
        downloadFile(response,filePath,fileName);
    }

    /**
     * 将文件输出到页面
     * @param response
     * @param filePath 文件路径
     * @param fileName  输出文件名
     * @throws Exception
     * @Author chen liang
     * @Date 2019 06/25
     */
    public void downloadFile(HttpServletResponse response, String filePath, String fileName) throws Exception {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        InputStream is = null;
        File file = new File(filePath);
        try {
            is = new FileInputStream(file);
            response.reset();
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader(
                    "Content-disposition",
                    "attachment; filename="
                            + new String(fileName.getBytes("GBK"),
                            "ISO8859-1"));
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html");
            response.getWriter().print("下载失败");
        }finally {
            try {
                if (is != null)
                    is.close();
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 获取数据列表
     */
    @RequestMapping(value = "/importInvoiceSumPage")
    @ResponseBody
    public ModelAndView importInvoiceSumPage() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/profit/InvoiceDetail/InvoiceSumImport");
        return modelAndView;
    }




    /**
     * 1、导入欠票基数
     */
    @RequestMapping(value = "importInvoiceSumFile")
    @ResponseBody
    public ResultVO importInvoiceSumFile(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "profitMonth") String profitMonth) {
        if (null == file) {
            return ResultVO.fail("文件不能为空");
        }
        try {
            List<List<Object>> invoiceSumList = ExcelUtil.getListByExcel(file.getInputStream(), file.getOriginalFilename());
            invoiceSumService.invoicePreLeftAmt(invoiceSumList, profitMonth);
        } catch (MessageException e) {
            LOGGER.info("欠票导入失败，" + e.getMsg());
            return ResultVO.fail(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.fail(e.getMessage());
        }
        return ResultVO.success("导入成功。");
    }

    private String encodeByMd5(String str) throws Exception{
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes());
        System.out.println(md);
        return new BigInteger(1, md.digest()).toString(16);
    }






}