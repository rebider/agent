package com.ryx.credit.machine.service.impl;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.IDUtils;
import com.ryx.credit.machine.dao.ImsTermWarehouseDetailMapper;
import com.ryx.credit.machine.entity.ImsTermActive;
import com.ryx.credit.machine.entity.ImsTermWarehouseDetail;
import com.ryx.credit.machine.service.ImsTermActiveService;
import com.ryx.credit.machine.service.ImsTermWarehouseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2018/10/11 10:21
 * @Param
 * @return
 **/
@Service("imsTermWarehouseDetailService")
public class ImsTermWarehouseDetailServiceImpl implements ImsTermWarehouseDetailService {

    @Autowired
    private ImsTermActiveService imsTermActiveService;
    @Autowired
    private ImsTermWarehouseDetailMapper imsTermWarehouseDetailMapper;


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult insertWarehouse(List<String> snList, ImsTermWarehouseDetail imsTermWarehouseDetail)throws MessageException {
        if(null==snList){
            throw new MessageException("sn列表异常");
        }
        if(snList.size()==0){
            throw new MessageException("sn数据有误");
        }
        for (String sn : snList) {
            ImsTermActive imsTermActive = imsTermActiveService.selectByPrimaryKey(sn);
            if(null!=imsTermActive){
                throw new MessageException("Sn机具已激活");
            }

            imsTermWarehouseDetail.setWdId(IDUtils.genImsTermId());
            imsTermWarehouseDetail.setPosSn(sn);
            imsTermWarehouseDetail.setUseStatus("1"); //未使用
            imsTermWarehouseDetail.setStatus("0");  //正常
            imsTermWarehouseDetail.setCreateTime(DateUtil.format(new Date()));
            imsTermWarehouseDetail.setCreatePerson(AppConfig.getProperty("zhyy_create_person"));
            imsTermWarehouseDetail.setPosType("0");  //pos类型 0普通级，1：特价机
            imsTermWarehouseDetail.setPayStatus("1");  //支付状态 0 已付 1 未付

            int insert = imsTermWarehouseDetailMapper.insert(imsTermWarehouseDetail);
            if(insert!=1){
                throw new MessageException("SN库存插入失败");
            }
        }
        return AgentResult.ok();
    }
}
