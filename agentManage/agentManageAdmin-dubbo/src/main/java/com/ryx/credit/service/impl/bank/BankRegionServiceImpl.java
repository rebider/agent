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
}

