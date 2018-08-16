package com.ryx.credit.service.impl.bank;

import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.bank.BankRegionMapper;
import com.ryx.credit.pojo.admin.agent.Region;
import com.ryx.credit.pojo.admin.bank.BankRegion;
import com.ryx.credit.pojo.admin.bank.BankRegionExample;
import com.ryx.credit.service.bank.BankRegionService;
import com.ryx.credit.service.dict.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RYX on 2018/7/17.
 */
@Service("bankRegionService")
public class BankRegionServiceImpl implements BankRegionService {

    @Autowired
    private BankRegionMapper bankRegionMapper;
    @Autowired
    private RegionService regionService;

    /**
     * 根据名字取出银行城市ID
     * @param regionName
     * @return
     */
    @Override
    public String findNameByRegionName(String regionName){
        if(StringUtils.isBlank(regionName)){
            return "";
        }
        BankRegionExample example = new BankRegionExample();
        BankRegionExample.Criteria criteria = example.createCriteria();
        criteria.andBRegionEqualTo(regionName);
        List<BankRegion> bankRegions = bankRegionMapper.selectByExample(example);
        if(null==bankRegions){
            return "";
        }
        if(bankRegions.size()!=1){
            return "";
        }
        return  bankRegions.get(0).getId();
    }

    /**
     * 根据城市id取出银行城市ID
     * @param regionId
     * @return
     */
    @Override
    public String findNameByRegionId(String regionId){
        if(StringUtils.isBlank(regionId)){
            return "";
        }
        Region region = regionService.queryByCode(regionId);
        String nameByRegionName = findNameByRegionName(region.getrName());
        return nameByRegionName;
    }

    /**
     * 根据省id查出所有市
     * @param provinceId
     * @return
     */
    @Override
    public List<String> findRegionByProvinceId(String provinceId){

        Region region = regionService.queryByCode(provinceId);
        BankRegionExample bankRegionExample = new BankRegionExample();
        BankRegionExample.Criteria criteria = bankRegionExample.createCriteria();
        criteria.andBProvinceLike(region.getrName()+"%");
        List<String> bLevels = new ArrayList<>();
        bLevels.add("省级");
        bLevels.add("地级");
        criteria.andBLevelIn(bLevels);
        List<BankRegion> bankRegions = bankRegionMapper.selectByExample(bankRegionExample);
        List<String> resultList = new ArrayList<>();
        bankRegions.forEach(row->{
            resultList.add(row.getId());
        });
        return resultList;
    }

    @Override
    public List<String> findRegionByCityId(String cityId){

        Region region = regionService.queryByCode(cityId);
        BankRegionExample bankRegionExample = new BankRegionExample();
        BankRegionExample.Criteria criteria = bankRegionExample.createCriteria();
        criteria.andBRegionEqualTo(region.getrName());
        List<BankRegion> bankRegions = bankRegionMapper.selectByExample(bankRegionExample);
        if(null==bankRegions){
            return null;
        }
        if(bankRegions.size()!=1){
            return null;
        }
        List<String> resultList = new ArrayList<>();
        resultList.add(bankRegions.get(0).getId());
        return resultList;
    }
}

