package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.RegionMapper;
import com.ryx.credit.dao.order.OAddressMapper;
import com.ryx.credit.pojo.admin.agent.Region;
import com.ryx.credit.pojo.admin.agent.RegionExample;
import com.ryx.credit.pojo.admin.order.OAddress;
import com.ryx.credit.pojo.admin.order.OAddressExample;
import com.ryx.credit.pojo.admin.vo.OAddressVo;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.dict.RegionService;
import com.ryx.credit.service.order.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by RYX on 2018/7/13.
 */
@Service(value = "addressService")
public class AddressServiceImpl implements AddressService {

    private Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Autowired
    private OAddressMapper oAddressMapper;
    @Autowired
    private RegionService regionService;
    @Autowired
    private IdService idService;
    @Autowired
    private RegionMapper regionMapper;

    @Override
    public PageInfo queryAddressList(PageInfo page, OAddress oAddress) {
        OAddressExample oAddressExample = new OAddressExample();
        OAddressExample.Criteria c = oAddressExample.or().andStatusEqualTo(Status.STATUS_1.status);
        if (StringUtils.isNotBlank(oAddress.getId())) {
            c.andIdEqualTo(oAddress.getId());
        }
        if (StringUtils.isNotBlank(oAddress.getuId())) {
            c.andUIdEqualTo(oAddress.getuId());
        }
        if (null != oAddress.getuType()) {
            c.andUTypeEqualTo(oAddress.getuType());
        }
        if (StringUtils.isNotBlank(oAddress.getAddrRealname())) {
            c.andAddrRealnameLike("%" + oAddress.getAddrRealname() + "%");
        }
        if (StringUtils.isNotBlank(oAddress.getAddrMobile())) {
            c.andAddrMobileLike("%" + oAddress.getAddrMobile() + "%");
        }
        if (StringUtils.isNotBlank(oAddress.getRemark())) {
            c.andRemarkLike("%" + oAddress.getRemark() + "%");
        }
        if (StringUtils.isNotBlank(oAddress.getAddrProvince())) {
            c.andAddrProvinceEqualTo(oAddress.getAddrProvince());
        }
        if (StringUtils.isNotBlank(oAddress.getAddrCity())) {
            c.andAddrCityEqualTo(oAddress.getAddrCity());
        }
        if (StringUtils.isNotBlank(oAddress.getAddrDistrict())) {
            c.andAddrDistrictEqualTo(oAddress.getAddrDistrict());
        }
        if (StringUtils.isNotBlank(oAddress.getcUser())) {
            c.andCUserEqualTo(oAddress.getcUser());
        }
        if (null != oAddress.getIsdefault()) {
            c.andIsdefaultEqualTo(oAddress.getIsdefault());
        }
        int count = oAddressMapper.countByExample(oAddressExample);
        oAddressExample.setOrderByClause(" u_time desc ");
        oAddressExample.setPage(new Page(page.getFrom(), page.getPagesize()));
        List<OAddress> list = oAddressMapper.selectByExample(oAddressExample);
        List<OAddressVo> oAddressVolist = new ArrayList<>();
        for (OAddress address : list) {
            OAddressVo vo = new OAddressVo();
            vo.from(address);
            if (StringUtils.isNotBlank(address.getAddrProvince())) {
                Region r = regionService.queryByCode(address.getAddrProvince());
                vo.setAddrProvinceString(r == null ? "" : r.getrName());
            }
            if (StringUtils.isNotBlank(address.getAddrCity())) {
                Region r = regionService.queryByCode(address.getAddrCity());
                vo.setAddrCityString(r == null ? "" : r.getrName());
            }
            if (StringUtils.isNotBlank(address.getAddrDistrict())) {
                Region r = regionService.queryByCode(address.getAddrDistrict());
                vo.setAddrDistrictString(r == null ? "" : r.getrName());
            }
            oAddressVolist.add(vo);
        }
        page.setRows(oAddressVolist);
        page.setTotal(count);
        return page;
    }


    @Override
    public AgentResult saveAddress(OAddress oAddress, String user) {
        if (null == oAddress) return AgentResult.fail("数据不能为空");
        if (null == user) return AgentResult.fail("操作用户不能为空");
        if (StringUtils.isBlank(oAddress.getAddrProvince())) return AgentResult.fail("省不能为空");
        if (StringUtils.isBlank(oAddress.getAddrCity())) return AgentResult.fail("市不能为空");
        if (StringUtils.isBlank(oAddress.getAddrDistrict())) return AgentResult.fail("区不能为空");
        if (StringUtils.isBlank(oAddress.getAddrRealname())) return AgentResult.fail("真实姓名不能为空");
        if (StringUtils.isBlank(oAddress.getAddrMobile())) return AgentResult.fail("联系方式不能为空");
        if (StringUtils.isBlank(oAddress.getuId())) return AgentResult.fail("用户信息不能为空");
        if (StringUtils.isBlank(oAddress.getAddrDetail())) return AgentResult.fail("详细地址不能为空");
        if (StringUtils.isBlank(oAddress.getZipCode())) return AgentResult.fail("邮编不能为空");
        if (null == oAddress.getuType()) oAddress.setuType(Status.STATUS_0.status);
        if (null == oAddress.getIsdefault()) oAddress.setIsdefault(Status.STATUS_0.status);

        Date date = Calendar.getInstance().getTime();
        oAddress.setId(idService.genId(TabId.o_address));
        oAddress.setcTime(date);
        oAddress.setcUser(user);
        oAddress.setStatus(Status.STATUS_1.status);
        oAddress.setuTime(date);
        oAddress.setuUser(user);
        oAddress.setVersion(Status.STATUS_0.status);
        //更新默认地址
        if (oAddress.getIsdefault().equals(Status.STATUS_1.status)) {
            OAddressExample example = new OAddressExample();
            example.or().andUIdEqualTo(oAddress.getuId()).andIsdefaultEqualTo(Status.STATUS_1.status).andStatusEqualTo(Status.STATUS_1.status);
            List<OAddress> oas = oAddressMapper.selectByExample(example);
            for (OAddress oa : oas) {
                oa.setIsdefault(Status.STATUS_0.status);
                oAddressMapper.updateByPrimaryKeySelective(oa);
            }
        }
        if (1 == oAddressMapper.insertSelective(oAddress)) {
            return AgentResult.ok(oAddress);
        }
        return AgentResult.fail();
    }

    @Override
    public AgentResult updateAddress(OAddress oAddress, String user) {
        if (null == oAddress) return AgentResult.fail("数据不能为空");
        if (null == user) return AgentResult.fail("操作用户不能为空");
        if (StringUtils.isBlank(oAddress.getId())) return AgentResult.fail("ID不能为空");
        if (StringUtils.isBlank(oAddress.getAddrProvince())) return AgentResult.fail("省不能为空");
        if (StringUtils.isBlank(oAddress.getAddrCity())) return AgentResult.fail("市不能为空");
        if (StringUtils.isBlank(oAddress.getAddrDistrict())) return AgentResult.fail("区不能为空");
        if (StringUtils.isBlank(oAddress.getAddrRealname())) return AgentResult.fail("真实姓名不能为空");
        if (StringUtils.isBlank(oAddress.getAddrMobile())) return AgentResult.fail("联系方式不能为空");
        if (StringUtils.isBlank(oAddress.getuId())) return AgentResult.fail("用户信息不能为空");
        if (StringUtils.isBlank(oAddress.getAddrDetail())) return AgentResult.fail("详细地址不能为空");
        if (StringUtils.isBlank(oAddress.getZipCode())) return AgentResult.fail("邮编不能为空");
        if (null == oAddress.getuType()) oAddress.setuType(Status.STATUS_0.status);
        if (null == oAddress.getIsdefault()) oAddress.setIsdefault(Status.STATUS_0.status);
        //更新默认地址
        if (oAddress.getIsdefault().equals(Status.STATUS_1.status)) {
            OAddressExample example = new OAddressExample();
            example.or().andUIdEqualTo(oAddress.getuId()).andIsdefaultEqualTo(Status.STATUS_1.status).andStatusEqualTo(Status.STATUS_1.status);
            List<OAddress> oas = oAddressMapper.selectByExample(example);
            for (OAddress oa : oas) {
                if (!oa.getId().equals(oAddress.getId())) {
                    oa.setIsdefault(Status.STATUS_0.status);
                    oAddressMapper.updateByPrimaryKeySelective(oa);
                }
            }
        }

        OAddress db = oAddressMapper.selectByPrimaryKey(oAddress.getId());
        db.setAddrRealname(oAddress.getAddrRealname());
        db.setAddrMobile(oAddress.getAddrMobile());
        db.setAddrProvince(oAddress.getAddrProvince());
        db.setAddrCity(oAddress.getAddrCity());
        db.setAddrDistrict(oAddress.getAddrDistrict());
        db.setStatus(oAddress.getStatus());
        db.setAddrDetail(oAddress.getAddrDetail());
        db.setZipCode(oAddress.getZipCode());
        db.setIsdefault(oAddress.getIsdefault());
        db.setuId(oAddress.getuId());
        db.setuType(oAddress.getuType());
        Date date = Calendar.getInstance().getTime();
        oAddress.setuTime(date);
        oAddress.setuUser(user);
        if (1 == oAddressMapper.updateByPrimaryKeySelective(oAddress)) {
            return AgentResult.ok(oAddress);
        }
        return AgentResult.fail();
    }


    @Override
    public OAddress queryById(String id) {
        return oAddressMapper.selectByPrimaryKey(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public List<String> addList(List<List<Object>> list, String userid) throws Exception {
        List<String> addressList = new ArrayList<>();
        if (null == list && list.size() == 0) {
            logger.info("导入的数据为空");
            throw new MessageException("导入的数据为空");
        }
        int i=2;
        for (List<Object> objectList : list) {
            int j=i++;
            if (StringUtils.isBlank(String.valueOf(objectList.get(0)))) {
                logger.info("收货人为空:{}", String.valueOf(objectList.get(0)));
                throw new MessageException("请填写第"+j+"行数据的收货人姓名");
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(1)))) {
                logger.info("联系电话为空:{}", String.valueOf(objectList.get(1)));
                throw new MessageException("请填写第"+j+"行数据的联系电话");
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(2)))) {
                logger.info("省为空:{}", String.valueOf(objectList.get(2)));
                throw new MessageException("请填写第"+j+"行数据的地址---省");
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(3)))) {
                logger.info("市为空:{}", String.valueOf(objectList.get(3)));
                throw new MessageException("请填写第"+j+"行数据的地址---市");
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(4)))) {
                logger.info("区为空:{}", String.valueOf(objectList.get(4)));
                throw new MessageException("请填写第"+j+"行数据的地址---区");
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(5)))) {
                logger.info("详细地址为空:{}", String.valueOf(objectList.get(5)));
                throw new MessageException("请填写第"+j+"行数据的详细地址");
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(6)))) {
                logger.info("邮编为空:{}", String.valueOf(objectList.get(6)));
                throw new MessageException("请填写第"+j+"行数据的邮编");
            }
            OAddress oAddress = new OAddress();
            oAddress.setId(idService.genId(TabId.o_address));
            oAddress.setuId(userid);
            oAddress.setcUser(userid);
            oAddress.setuUser(userid);
            oAddress.setcTime(Calendar.getInstance().getTime());
            oAddress.setuTime(Calendar.getInstance().getTime());
            oAddress.setIsdefault(Status.STATUS_0.status);
            oAddress.setVersion(Status.STATUS_1.status);
            oAddress.setStatus(Status.STATUS_1.status);
            oAddress.setuType(Status.STATUS_0.status);
            oAddress.setAddrRealname(String.valueOf(objectList.get(0)));
            oAddress.setAddrMobile(String.valueOf(objectList.get(1)));
            if (StringUtils.isNotBlank(String.valueOf(objectList.get(5))))
                oAddress.setAddrDetail(String.valueOf(objectList.get(5)));

            if (StringUtils.isNotBlank(String.valueOf(objectList.get(6))))
                oAddress.setZipCode(String.valueOf(objectList.get(6)));

            if (StringUtils.isNotBlank(String.valueOf(objectList.get(7))))
                oAddress.setRemark(String.valueOf(objectList.get(7)));

            //匹配省
            RegionExample regionExample = new RegionExample();
            RegionExample.Criteria criteria = regionExample.createCriteria();
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            criteria.andRNameEqualTo(String.valueOf(objectList.get(2)));
            List<Region> regions = regionMapper.selectByExample(regionExample);
            if (1 != regions.size()) {
                logger.info("第"+j+"行数据没有该省");
                throw new MessageException("第"+j+"行数据没有该省");
            }
            Region region = regions.get(0);
            oAddress.setAddrProvince(region.getrCode());

            //匹配市
            RegionExample regionExam = new RegionExample();
            RegionExample.Criteria criteriaCity = regionExam.createCriteria();
            criteriaCity.andStatusEqualTo(Status.STATUS_1.status);
            criteriaCity.andRNameEqualTo(String.valueOf(objectList.get(3)));
            criteriaCity.andPCodeEqualTo(region.getrCode());
            List<Region> regionsCity = regionMapper.selectByExample(regionExam);
            if (1 != regionsCity.size()) {
                logger.info("第"+j+"行数据没有该市");
                throw new MessageException("第"+j+"行数据没有该市");
            }
            Region regionCity = regionsCity.get(0);
            oAddress.setAddrCity(regionCity.getrCode());

            //匹配区
            RegionExample regionExa = new RegionExample();
            RegionExample.Criteria criteriaDis = regionExa.createCriteria();
            criteriaDis.andStatusEqualTo(Status.STATUS_1.status);
            criteriaDis.andRNameEqualTo(String.valueOf(objectList.get(4)));
            criteriaDis.andPCodeEqualTo(regionCity.getrCode());
            List<Region> regionsDis = regionMapper.selectByExample(regionExa);
            if (1 != regionsDis.size()) {
                logger.info("第"+j+"行数据没有该区");
                throw new MessageException("第"+j+"行数据没有该区");
            }
            Region regionDis = regionsDis.get(0);
            oAddress.setAddrDistrict(regionDis.getrCode());
            //进行添加
            if (1 != oAddressMapper.insertSelective(oAddress)) {
                logger.info("插入失败!");
                throw new MessageException("插入失败！");
            }
            addressList.add(oAddress.getId());


        }

        return addressList;
    }
}
