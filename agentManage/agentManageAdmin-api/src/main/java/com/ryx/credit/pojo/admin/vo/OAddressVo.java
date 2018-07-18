package com.ryx.credit.pojo.admin.vo;

import com.ryx.credit.pojo.admin.order.OAddress;

/**
 * Created by RYX on 2018/7/13.
 */
public class OAddressVo extends OAddress {

    public OAddressVo from(OAddress address){
        this.setId(address.getId());
        this.setuId(address.getuId());
        this.setuType(address.getuType());
        this.setAddrRealname(address.getAddrRealname());
        this.setAddrMobile(address.getAddrMobile());
        this.setAddrProvince(address.getAddrProvince());
        this.setAddrCity(address.getAddrCity());
        this.setAddrDistrict(address.getAddrDistrict());
        this.setAddrDetail(address.getAddrDetail());
        this.setZipCode(address.getZipCode());
        this.setIsdefault(address.getIsdefault());
        this.setRemark(address.getRemark());
        this.setStatus(address.getStatus());
        this.setcTime(address.getcTime());
        this.setuTime(address.getuTime());
        this.setcUser(address.getcUser());
        this.setuUser(address.getuUser());
        this.setVersion(address.getVersion());
        return this;
    }

    private String addrProvinceString;

    private String addrCityString;

    private String addrDistrictString;


    public String getAddrProvinceString() {
        return addrProvinceString;
    }

    public void setAddrProvinceString(String addrProvinceString) {
        this.addrProvinceString = addrProvinceString;
    }

    public String getAddrCityString() {
        return addrCityString;
    }

    public void setAddrCityString(String addrCityString) {
        this.addrCityString = addrCityString;
    }

    public String getAddrDistrictString() {
        return addrDistrictString;
    }

    public void setAddrDistrictString(String addrDistrictString) {
        this.addrDistrictString = addrDistrictString;
    }
}
