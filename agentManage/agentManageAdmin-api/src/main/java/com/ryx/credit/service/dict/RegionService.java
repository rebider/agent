package com.ryx.credit.service.dict;

import com.ryx.credit.commons.result.Tree;
import com.ryx.credit.pojo.admin.agent.Region;

import java.util.List;

/**
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/5/22 13:47
 */
public interface RegionService {

    List<Tree> selectAllRegion(String pCode);

    public Region queryByCode(String code);

    public String getRegionName(String code);


    public List<Region> queryRegion(Region region);
}
