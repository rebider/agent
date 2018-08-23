package com.ryx.credit.service.bank;

import com.ryx.credit.commons.result.Tree;
import com.ryx.credit.pojo.admin.bank.DPosRegion;

import java.util.List;

/**
 * Created by RYX on 2018/8/6.
 */
public interface PosRegionService {

    List<String> findRegionByProvinceId(String provinceId);

    List<String> findRegionByCityId(String cityId);

    List<Tree> queryAllRegion(String pCode);

    List<Tree> queryPosRegion(String code,String level);

    List<DPosRegion> queryByCodes(String codes);

    List<DPosRegion> queryByParentCode(String parentCode);

    List<String> queryCityByCode(String codes);
}
