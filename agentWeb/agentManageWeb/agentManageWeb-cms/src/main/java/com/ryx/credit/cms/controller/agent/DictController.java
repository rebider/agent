package com.ryx.credit.cms.controller.agent;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.service.dict.DictOptionsService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Lihl
 * @Date 2018/07/19
 * 选项服务类---字典维护
 */
@Controller
@RequestMapping("/dict")
public class DictController extends BaseController{
    @Resource
    private DictOptionsService dictOptionsService;

    @GetMapping("/selectDict")
    public String selectDict() {
        return "agent/dict";
    }

    /**
     * 分页展示
     */
    @PostMapping("/dictList")
    @ResponseBody
    public Object dictList(Dict dict, Integer page, Integer rows, String sort, String order) {
        PageInfo pageInfo = new PageInfo(page, rows, sort, order);
        Map<String, Object> condition = new HashMap<String, Object>();
        if(dict.getdGroup()!=null){
            condition.put("dGroup", dict.getdGroup());
        }
        if(dict.getdArtifact()!=null){
            condition.put("dArtifact", dict.getdArtifact());
        }
        if(dict.getdItemvalue()!=null){
            condition.put("dItemvalue", dict.getdItemvalue());
        }
        if(dict.getdItemname()!=null){
            condition.put("dItemname", dict.getdItemname());
        }
        if(dict.getdItemnremark()!=null){
            condition.put("dItemnremark", dict.getdItemnremark());
        }
        condition.put("dStatus", Status.STATUS_1.status);

        pageInfo.setCondition(condition);
        return dictOptionsService.dictList(pageInfo);
    }

    @GetMapping("/addDictPage")
    public String addDictPage() {
        return "agent/dictAdd";
    }

    /**
     * 新增
     */
    @RequestMapping("/addDict")
    @ResponseBody
    public Object addDict(Dict dict, @Param("tableName")String tableName) {
        dict.setdStatus(Status.STATUS_1.status);
        dictOptionsService.insertDict(dict, tableName);

        return renderSuccess("添加成功！");
    }

    /**
     * 删除
     * （编辑此条数据的状态）
     */
    @RequestMapping("/deleteDict")
    @ResponseBody
    public Object deleteDict(Dict dict){
        dictOptionsService.updateByPrimaryKeySelective(dict);

        return renderSuccess("编辑状态成功！");
    }

}
