package com.ryx.credit.service.impl.dict;

import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.commons.result.Tree;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.RegionMapper;
import com.ryx.credit.pojo.admin.CResource;
import com.ryx.credit.pojo.admin.agent.Region;
import com.ryx.credit.service.agent.RegionService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 地区树实现类
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/5/22 13:46
 */
@Service("regionService")
public class RegionServiceImpl implements RegionService {

    private static final String REGIONS_KEY = "agent_regions_list";
    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private RedisService redisService;


    @Override
    public List<Tree> selectAllRegion() {

        String regionsValue = redisService.getValue(REGIONS_KEY);
        List<Region> regionsList = null;
        if(StringUtils.isBlank(regionsValue)){
            regionsList = regionMapper.selectAll();
            String regionsJson = JsonUtil.objectToJson(regionsList);
            redisService.setValue(REGIONS_KEY,regionsJson,86400L);
        }else{
            regionsList = JsonUtil.jsonToList(regionsValue, Region.class);
        }
        List<Tree> rootTree = new ArrayList<Tree>();
        //根目录
        List<Tree> menuList = new ArrayList<Tree>();
        for (Region region : regionsList) {
            rootTree.add(regionToTree(region));
            if(region.getpCode().equals("0")){
                menuList.add(regionToTree(region));
            }
        }
        for (Tree tree : menuList) {
            tree.setChildren(getChild(String.valueOf(tree.getId()),rootTree));
        }
        return menuList;
    }

    private Tree regionToTree(Region region){
        Tree tree = new Tree();
        tree.setId(Long.valueOf(region.getrCode()));
        tree.setPid(Long.valueOf(region.getpCode()));
        tree.setText(region.getrName());
        tree.setState(String.valueOf(region.getStatus()));
        tree.settType(region.gettType());
        return tree;
    }


    private List<Tree> getChild(String id, List<Tree> rootTree) {

        //子菜单
        List<Tree> childList = new ArrayList<>();
        for (Tree region : rootTree) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (StringUtils.isNotBlank(String.valueOf(region.getPid()))) {
                if (String.valueOf(region.getPid()).equals(id)) {
                    childList.add(region);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (Tree tree : childList) {
            //3表示最后一级
            if (!tree.gettType().equals("3")) {
                //递归
                tree.setChildren(getChild(String.valueOf(tree.getId()), rootTree));
            }
        }
        //递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }


}
