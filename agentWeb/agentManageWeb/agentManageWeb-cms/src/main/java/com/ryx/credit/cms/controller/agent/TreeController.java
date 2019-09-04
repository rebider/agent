package com.ryx.credit.cms.controller.agent;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.commons.result.Tree;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.pojo.admin.agent.Region;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.bank.PosRegionService;
import com.ryx.credit.service.dict.DepartmentService;
import com.ryx.credit.service.dict.RegionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 获取地区控制层
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/5/22 18:02
 */
@Controller
@RequestMapping("/region")
public class TreeController extends BaseController {

    private static final Logger log = Logger.getLogger(TreeController.class);
    private static final String REGIONS_KEY = "agent_regions_list";
    private static final String REGIONS_BEAN_KEY = "regions:PCODE-";
    @Autowired
    private RegionService regionService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private PosRegionService posRegionService;

    /**
     * /region/toRegionPage
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("toRegionPage")
    public String toRegionPage(HttpServletRequest request, Model model){
        model.addAttribute("isCheckbox",request.getParameter("isCheckbox"));
        model.addAttribute("url",request.getParameter("url"));
        return "common/regionList";
    }

    /**
     * /region/toSynRegionPage
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("toSynRegionPage")
    public String toSynRegionPage(HttpServletRequest request, Model model){
        model.addAttribute("isCheckbox",request.getParameter("isCheckbox"));
        model.addAttribute("url",request.getParameter("url"));
        return "common/regionSynList";
    }

    /**
     * /region/toBusRegionList
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("toBusRegionList")
    public String toBusRegionList(HttpServletRequest request, Model model){
        model.addAttribute("busId",request.getParameter("busId"));
        String bufdata = request.getParameter("bufdata");
        if(StringUtils.isNotBlank(bufdata)){
            model.addAttribute("bufdata",bufdata);
        }else{
            model.addAttribute("bufdata","");
        }
        return "common/busRegionList";
    }


    /**
     * 查询子节点
     *  /region/queryRegions
     * @param rCode
     * @return
     */
    @RequestMapping("queryRegions")
    @ResponseBody
    public List<Region> queryRegions(String rCode){
        List<Region>  regions = null;
        try {
            String regions_string =   ServiceFactory.redisService.getValue(REGIONS_BEAN_KEY + rCode);
            if(StringUtils.isNotBlank(regions_string)) {
                regions = JSONArray.parseArray(regions_string, Region.class);
            }else{
                Region regionPar = new Region();
                regionPar.setpCode(rCode);
                regions = regionService.queryRegion(regionPar);
                ServiceFactory.redisService.setValue(REGIONS_BEAN_KEY + rCode,JSONObject.toJSONString(regions),86400000L);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("从redis去取地区错误");
        } finally {
            if(regions==null) {
                Region regionPar = new Region();
                regionPar.setpCode(rCode);
                regions = regionService.queryRegion(regionPar);
            }
            return regions;
        }
    }

    /**
     * 地区数据不显示区
     * @param pCode
     * @return
     */
    @RequestMapping("regionTree")
    @ResponseBody
    public Object regionTree(String pCode){
        long timeStart = System.currentTimeMillis();
        if(StringUtils.isBlank(pCode)){
            pCode = "0";
        }
        List<Tree> trees = null;
//        try {
//            String treeJson = ServiceFactory.redisService.getValue(REGIONS_KEY + ":" + pCode);
//            if(StringUtils.isNotBlank(treeJson)){
//                trees = JsonUtil.jsonToList(treeJson, Tree.class);
//                if(trees!=null){
//                    return trees;
//                }
//            }
//        } catch (Exception e) {
//            log.info("redis异常:"+e.getMessage());
//        }
        trees = regionService.selectAllRegion(pCode,"false");
        long timeEnd = System.currentTimeMillis();
        log.info("运行时间："+(timeEnd - timeStart)+"ms.");
        return trees;
    }

    /**
     * 地区数据显示区
     * @param pCode
     * @return
     */
    @RequestMapping("regionShowAreaTree")
    @ResponseBody
    public Object regionShowAreaTree(String pCode){
        long timeStart = System.currentTimeMillis();
        if(StringUtils.isBlank(pCode)){
            pCode = "0";
        }
        List<Tree> trees = regionService.selectAllRegion(pCode,"true");
        long timeEnd = System.currentTimeMillis();
        log.info("运行时间："+(timeEnd - timeStart)+"ms.");
        return trees;
    }


    @RequestMapping("posRegionTree")
    @ResponseBody
    public Object posRegionTree(HttpServletRequest req,String pCode){
        long timeStart = System.currentTimeMillis();

        //选中的数据
        String bufdata =req.getParameter("bufdata");

        List bufdata_id = Arrays.asList(StringUtils.isNotBlank(bufdata)?bufdata.split(","):new String[]{});
        List<Tree> trees = new ArrayList<>();
        if(StringUtils.isBlank(pCode)){
            trees = posRegionService.queryPosRegion(null,"1");
            for (Tree tree : trees) {
                if(bufdata_id.contains(tree.getId())) {
                    tree.setChecked(true);
                }
                List<Tree> trees_child =  posRegionService.queryPosRegion(tree.getId(),"2");
                for (Tree tree1 : trees_child) {
                    if(bufdata_id.contains(tree1.getId())){
                        tree1.setChecked(true);
                    }
                }
                tree.setChildren(trees_child);
            }
            return trees;
        }
        return trees ;
    }


    /**
     * 部门数据
     */
    @RequestMapping("departmentTree")
    @ResponseBody
    public Object departmentTree(String pCode){
        return departmentService.selectRegion(pCode);
    }

    /**
     *
     * 部门数据（全部部门）
     */
    @RequestMapping("departmentTreeAll")
    @ResponseBody
    public Object departmentTreeAll(String pCode){
        List<Tree> trees = null;
        trees = departmentService.selectAllDepartment(pCode);
        return trees;
    }

    /**
     * 选项json数据
     * region/options
     * @param request
     * @return
     */
    @RequestMapping("options")
    @ResponseBody
    public Object options(HttpServletRequest request){
        return optionsData(request);
    }


    /**
     * 业务树结构
     * /regionbusTreee
     * @param pCode
     * @return
     */
    @RequestMapping("busTreee")
    @ResponseBody
    public List<Tree> busTreee(String pCode,String root,String currentId){
        if(StringUtils.isBlank(pCode) && StringUtils.isBlank(root) && StringUtils.isBlank(currentId))return Arrays.asList();
        List<Map>  treesmap = new ArrayList<>();
        if(StringUtils.isNotBlank(currentId)){
            treesmap = agentBusinfoService.getParentListFromBusInfo(treesmap,currentId);
        }
        List<Tree> trees = new ArrayList<>();
        if(treesmap.size()>0) {
            Tree t = null;
            for (int i = treesmap.size() - 1; i >=0; i--) {
                Map map = treesmap.get(i);
                if(t==null){
                    t = new Tree();
                    t.setId(map.get("ID")+"");
                    if (null != map.get("BUS_PARENT") && StringUtils.isNotBlank(map.get("BUS_PARENT")+"")){
                        t.setPid(map.get("BUS_PARENT")+"");
                    }else
                        t.setPid("0");
                    if(null !=map.get("BUS_NUM"))
                    t.setText(map.get("PLATFORM_NAME")+"-"+map.get("D_ITEMNAME")+"-"+map.get("AG_NAME")+"-"+map.get("BUS_NUM"));
                    else
                    t.setText(map.get("PLATFORM_NAME")+"-"+map.get("D_ITEMNAME")+"-"+map.get("AG_NAME")+"");
                    t.setIconCls(null);
                    t.setState(1);
                    trees.add(t);
                }else{
                    Tree tc = new Tree();
                    tc.setId(map.get("ID")+"");
                    if (null != map.get("BUS_PARENT") && StringUtils.isNotBlank(map.get("BUS_PARENT")+"")){
                        tc.setPid(map.get("BUS_PARENT")+"");
                    }else
                        tc.setPid("0");
                    if(null !=map.get("BUS_NUM"))
                    tc.setText(map.get("PLATFORM_NAME")+":"+map.get("D_ITEMNAME")+":"+map.get("AG_NAME")+"-"+map.get("BUS_NUM"));
                     else
                    t.setText(map.get("PLATFORM_NAME")+"-"+map.get("D_ITEMNAME")+"-"+map.get("AG_NAME")+"");
                    tc.setIconCls(null);
                    tc.setState(1);
                    if(t!=null){
                        t.setChildren(Arrays.asList(tc));
                        t=tc;
                    }
                }
            }
        }
        return trees;
    }
}
