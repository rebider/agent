package com.ryx.credit.service.bank;

import com.ryx.credit.commons.result.Tree;

import java.util.List;

/**
 * Created by RYX on 2018/8/6.
 */
public interface PosRegionService {

    List<String> findRegionByProvinceId(String provinceId);

    List<String> findRegionByCityId(String cityId);

    List<Tree> queryAllRegion(String pCode);
}
