package com.ryx.credit.cms.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.order.OProduct;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.order.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 商品控制层
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/7/13 15:30
 */
@RequestMapping("product")
@Controller
public class ProductController extends BaseController{

    @Autowired
    private ProductService productService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private IUserService userService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;


    @RequestMapping(value = "toProductList")
    public String toProductList(HttpServletRequest request,OProduct product){
        List<Dict> modelType = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.MODEL_TYPE.name());
        request.setAttribute("modelType", modelType);
        return "order/productList";
    }

    @RequestMapping(value = "productListDialog")
    public String productListDialog(HttpServletRequest request,OProduct product){
        String data = request.getParameter("data");
        JSONObject jsonObject = JSONObject.parseObject(data);
        if(jsonObject!=null){

            String platformBusId= jsonObject.get("platformBusId")!=null?String.valueOf(jsonObject.get("platformBusId")):null;

            AgentBusInfo busInfo = StringUtils.isNotBlank(platformBusId)?agentBusinfoService.getById(platformBusId):null;

            String platform = (busInfo!=null?busInfo.getBusPlatform():String.valueOf(jsonObject.get("platform")));

            if(StringUtils.isNotBlank(platform)){
                StringBuffer proType = new StringBuffer();
                List<Dict> dicts = dictOptionsService.dictList(DictGroup.RELATION.name(), platform);
                for (Dict dict : dicts) {
                    proType.append(dict.getdItemvalue()+",");
                }
                if(StringUtils.isNotBlank(proType))
                jsonObject.put("proType",proType.substring(0,proType.length()-1).toString());
            }
            request.setAttribute("data",jsonObject);
        }
        return "order/productListDialog";
    }


    /**
     * product/productListDialogList
     * 下订单商品选择窗口数据
     * @param request
     * @param product
     * @return
     */
    @RequestMapping(value = "productListDialogList")
    @ResponseBody
    public Object productListDialogList(HttpServletRequest request,OProduct product){
        Page pageInfo = pageProcess(request);
        PageInfo resultPageInfo = productService.productGroupByList(product,pageInfo);
        List<Map> rows = resultPageInfo.getRows();
        rows.forEach(row ->{
            Dict dict = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MODEL_TYPE.name(), row.get("proType")+"");
            if(dict!=null)
                row.put("proType",dict.getdItemname());
        });
        return resultPageInfo;
    }

    /**
     * 商品管理列表
     * @param request
     * @param product
     * @return
     */
    @RequestMapping(value = "productList")
    @ResponseBody
    public Object productList(HttpServletRequest request,OProduct product){
        Page pageInfo = pageProcess(request);
        PageInfo resultPageInfo = productService.productList(product,pageInfo);
        List<OProduct> rows = resultPageInfo.getRows();
        rows.forEach(row ->{
            if(StringUtils.isNotBlank(row.getProType())) {
                Dict dict = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MODEL_TYPE.name(), row.getProType());
                if (dict != null)
                    row.setProType(dict.getdItemname());
            }
            if(StringUtils.isNotBlank(row.getProCom())) {
                Dict manufacturer = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(), row.getProCom());
                if (null != manufacturer)
                    row.setProCom(manufacturer.getdItemname());
            }
            if(StringUtils.isNotBlank(row.getcUser())){
                CUser cUser = userService.selectById(row.getcUser());
                if(null!=cUser)
                row.setcUser(cUser.getName());
            }
            if(StringUtils.isNotBlank(row.getuUser())){
                CUser uUser = userService.selectById(row.getuUser());
                if(null!=uUser)
                row.setuUser(uUser.getName());
            }
        });
        return resultPageInfo;
    }


    @RequestMapping(value = "toProductAdd")
    public String toProductAdd(HttpServletRequest request,OProduct product){
        orderDictData(request);
        return "order/productAdd";
    }

    @RequestMapping(value = "productAdd")
    @ResponseBody
    public Object productAdd(HttpServletRequest request,OProduct product){
        String userId = String.valueOf(getUserId());
        product.setcUser(userId);
        product.setuUser(userId);
        AgentResult agentResult = productService.saveProduct(product);
        if(agentResult.isOK()){
            return renderSuccess("添加成功！");
        }
        if(agentResult.getStatus()==500){
            return renderSuccess(agentResult.getMsg());
        }
        return renderSuccess("添加失败！");
    }

    @RequestMapping(value = "toProductEdit")
    public String toProductEdit(HttpServletRequest request,OProduct product){
        orderDictData(request);
        OProduct oProduct = productService.findById(product.getId());
        request.setAttribute("oProduct",oProduct);
        return "order/productEdit";
    }

    @RequestMapping(value = "productEdit")
    @ResponseBody
    public Object productEdit(HttpServletRequest request,OProduct product){
        String userId = String.valueOf(getUserId());
        product.setuUser(userId);
        AgentResult agentResult = productService.updateProduct(product);
        if(agentResult.isOK()){
            return renderSuccess("修改成功！");
        }
        if(agentResult.getStatus()==500){
            return renderSuccess(agentResult.getMsg());
        }
        return renderSuccess("修改失败！");
    }


    @RequestMapping(value = "productDel")
    @ResponseBody
    public Object productDel(HttpServletRequest request,String id){
        AgentResult agentResult = productService.deleteById(id);
        if(agentResult.isOK()){
            return renderSuccess("删除成功！");
        }
        return renderSuccess("删除失败！");
    }


}
