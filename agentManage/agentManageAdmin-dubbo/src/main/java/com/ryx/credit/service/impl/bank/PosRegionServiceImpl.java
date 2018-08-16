package com.ryx.credit.service.impl.bank;

import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.commons.result.Tree;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.bank.DPosRegionMapper;
import com.ryx.credit.pojo.admin.agent.Region;
import com.ryx.credit.pojo.admin.bank.DPosRegion;
import com.ryx.credit.pojo.admin.bank.DPosRegionExample;
import com.ryx.credit.service.bank.PosRegionService;
import com.ryx.credit.service.dict.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RYX on 2018/7/17.
 */
@Service("posRegionService")
public class PosRegionServiceImpl implements PosRegionService {

    @Autowired
    private DPosRegionMapper posRegionMapper;
    @Autowired
    private RegionService regionService;

    /**
     * 根据省id查出所有市
     *
     * @param provinceId
     * @return
     */
    @Override
    public List<String> findRegionByProvinceId(String provinceId) {

        Region region = regionService.queryByCode(provinceId);
        if (null == region) {
            return null;
        }
        List<DPosRegion> bankRegions = posRegionMapper.findRegionByProvinceName(region.getrName() + "%");
        List<String> resultList = new ArrayList<>();
        bankRegions.forEach(row -> {
            resultList.add(row.getCode());
        });
        return resultList;
    }

    @Override
    public List<String> findRegionByCityId(String cityId) {

        Region region = regionService.queryByCode(cityId);
        DPosRegionExample dPosRegionExample = new DPosRegionExample();
        DPosRegionExample.Criteria criteria = dPosRegionExample.createCriteria();
        criteria.andNameEqualTo(region.getrName());
        criteria.andCodeLevelEqualTo("2");
        List<DPosRegion> posRegion = posRegionMapper.selectByExample(dPosRegionExample);
        if (null == posRegion) {
            return null;
        }
        if (posRegion.size() != 1) {
            return null;
        }
        List<String> resultList = new ArrayList<>();
        resultList.add(posRegion.get(0).getCode());
        return resultList;
    }

    @Override
    public List<Tree> queryAllRegion(String pCode) {
        List<Tree> rootTree  = new ArrayList<>();
        if (StringUtils.isBlank(pCode)) {
            pCode = "1";
        }
        List<DPosRegion> regionsList = posRegionMapper.findByPcode(pCode);
        if (null != regionsList && regionsList.size() > 0) {
            for (DPosRegion dPosRegion : regionsList) {
                DPosRegion dPosRegions = new DPosRegion();
                dPosRegions.setParentCode(dPosRegion.getCode());
                dPosRegions.setCodeLevel("2");
                List<DPosRegion> regions = posRegionMapper.findByPosRegion(dPosRegions);
                rootTree.add(regionToTree(dPosRegion));
               for (DPosRegion region : regions) {
                    rootTree.add(regionToTree(region));
                }

            }
        }
        return rootTree;
    }


    private Tree regionToTree(DPosRegion region) {
        Tree tree = new Tree();
        tree.setId(Long.valueOf(region.getCode()) + "");
        tree.setPid(Long.valueOf(region.getParentCode()) + "");
        tree.setText(region.getName());
        tree.setState(posRegionMapper.findCountByCode(region.getCode()) == 0 ? 1 : 0);
        tree.settType(new BigDecimal(region.getCodeType()));
        return tree;
    }


    /**
     * 构建pos业务范围树
     * @param code
     * @param level
     * @return
     */
    @Override
    public List<Tree> queryPosRegion(String code, String level) {
        DPosRegionExample example = new DPosRegionExample();
        DPosRegionExample.Criteria c = example.or();
        if(StringUtils.isBlank(code)){
            c.andCodeLevelEqualTo(level);
        }else{
            c.andCodeLevelEqualTo(level);
            c.andParentCodeEqualTo(code);
        }
        List<DPosRegion> list = posRegionMapper.selectByExample(example);
        List<Tree> trees = new ArrayList<Tree>();
        for (DPosRegion dPosRegion : list) {
            Tree tree = new Tree();
            tree.setId(Long.valueOf(dPosRegion.getCode()) + "");
            tree.setPid(Long.valueOf(dPosRegion.getParentCode()) + "");
            tree.setText(dPosRegion.getName());
            tree.setState("2".equals(level)?1:0);
            tree.settType(new BigDecimal(dPosRegion.getCodeType()));
            trees.add(tree);
        }
        return trees;
    }
}

