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

    Region queryByCode(String code);

    String getRegionName(String code);

    List<Region> queryRegion(Region region);

    String getRegionsName(String codes);

    Boolean isCity(String code);
}
