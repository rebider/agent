package com.ryx.credit.cms.controller.order;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.IDUtils;
import com.ryx.credit.cms.util.ImportExcelUtil;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.order.OAddress;
import com.ryx.credit.pojo.admin.vo.OAddressVo;
import com.ryx.credit.service.dict.RegionService;
import com.ryx.credit.service.order.AddressService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RYX on 2018/7/13.
 */
@RequestMapping("address")
@Controller
public class AddressController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(AddressController.class);
    @Autowired
    private AddressService addressService;
    @Autowired
    private RegionService regionService;

    /**
     * /address/addressManageList
     *
     * @param request
     * @param oAddress
     * @return
     */
    @RequestMapping(value = "addressManageList")
    public String addressManageList(HttpServletRequest request, OAddress oAddress) {
        return "order/addressManageList";
    }

    /**
     * /address/addressListDialog
     *
     * @param request
     * @param oAddress
     * @return
     */
    @RequestMapping(value = "addressListDialog")
    public String addressListDialog(HttpServletRequest request, OAddress oAddress) {
        return "order/addressListDialog";
    }

    /**
     * address/addressManageDataList
     *
     * @param request
     * @param oAddress
     * @return
     */
    @RequestMapping(value = "addressManageDataList")
    @ResponseBody
    public Object addressManageDataList(HttpServletRequest request, OAddress oAddress) {
        Page page = pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(), null, null);
        oAddress.setuId(getUserId() + "");
        pageInfo = addressService.queryAddressList(pageInfo, oAddress);
        return pageInfo;
    }


    /**
     * /address/addressManageAddView
     *
     * @param request
     * @param oAddress
     * @return
     */
    @RequestMapping(value = "addressManageAddView")
    public String addressManageAddView(HttpServletRequest request, OAddress oAddress, String flag, Model model) {
        if (StringUtils.isNotBlank(flag)){
            model.addAttribute("flag",flag);
        }

        return "order/addressManageAdd";
    }

    /**
     * /address/addressManageAddView
     *
     * @param request
     * @param oAddress
     * @return
     */
    @RequestMapping(value = "addressManageEditView")
    public String addressManageEditView(HttpServletRequest request, OAddress oAddress) {
        request.setAttribute("address", addressService.queryById(oAddress.getId()));
        return "order/addressManageAdd";
    }


    /**
     * 地址添加
     * /address/addressManageAdd
     *
     * @param request
     * @param oAddress
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "addressManageAdd")
    public AgentResult addressManageAdd(HttpServletRequest request, OAddressVo oAddress) {
        try {
            oAddress.setuId(getUserId() + "");
            if (StringUtils.isNotBlank(oAddress.getId())) {
                AgentResult res = addressService.updateAddress(oAddress, getUserId() + "");
                return res;
            } else {
                AgentResult res = addressService.saveAddress(oAddress, getUserId() + "");
                return res;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AgentResult.fail(e.getMessage());
        }
    }

    /**
     * /address/addressManageDele?id=
     *
     * @param request
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "addressManageDele")
    public AgentResult addressManageDele(HttpServletRequest request, @RequestParam("id") String id) {
        OAddress db = addressService.queryById(id);
        if (!db.getuId().equals(getUserId() + "")) {
            return AgentResult.fail("只能删除自己的地址");
        }
        if (db.getIsdefault().equals(Status.STATUS_1.status)) {
            return AgentResult.fail("不能删除默认地址");
        }
        db.setStatus(Status.STATUS_0.status);
        return addressService.updateAddress(db, getUserId() + "");
    }

    /**
     * 数据导入界面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("importView")
    public String importView(HttpServletRequest request, HttpServletResponse response) {
        return  "order/addressImport";
    }

    /**
     * 上传
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
                                logger.info("用户{}地址信息导入dubbo调用{}",userid,list.size());
                            }
                        }
                        logger.info("用户{}地址信息导入dubbo调用{}",userid,list.size());
                        List<String>  resd =  addressService.addList(list,userid);
                        logger.info("用户{}代理商导入dubbo调用结果返回{}",userid,resd.size());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if(e instanceof MessageException){
                        String msg = ((MessageException) e).getMsg();
                        return ResultVO.fail(msg);
                    }
                    logger.info("导入地址信息异常：",e.getMessage());
                    return ResultVO.fail(e.getMessage());
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

}
