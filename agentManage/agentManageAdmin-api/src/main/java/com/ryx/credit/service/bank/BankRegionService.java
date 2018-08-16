package com.ryx.credit.service.bank;

import java.util.List;

/**
 * Created by RYX on 2018/7/17.
 */
public interface BankRegionService {

    String findNameByRegionName(String regionName);

    String findNameByRegionId(String regionName);

    List<String> findRegionByProvinceId(String provinceId);

    List<String> findRegionByCityId(String cityId);
}
