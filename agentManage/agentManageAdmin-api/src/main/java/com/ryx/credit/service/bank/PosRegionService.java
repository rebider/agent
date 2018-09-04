package com.ryx.credit.service.bank;

import com.ryx.credit.commons.result.Tree;
import com.ryx.credit.pojo.admin.bank.DPosRegion;

import java.util.List;
import java.util.Set;

/**
 * Created by RYX on 2018/8/6.
 */
public interface PosRegionService {

    List<String> findRegionByProvinceId(String provinceId);

    List<String> findRegionByCityId(String cityId);

    List<Tree> queryAllRegion(String pCode);

    List<Tree> queryPosRegion(String code,String level);

    List<String> queryPosRegionProviceByCity(List<String> codes);

    List<DPosRegion> queryByCodes(String codes);

    List<DPosRegion> queryByParentCode(String parentCode);

    Set<String> queryCityByCode(String codes);
}
