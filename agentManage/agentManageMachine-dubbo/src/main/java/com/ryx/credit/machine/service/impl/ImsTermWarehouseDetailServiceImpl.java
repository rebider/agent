package com.ryx.credit.machine.service.impl;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.IDUtils;
import com.ryx.credit.machine.dao.ImsTermTransferDetailMapper;
import com.ryx.credit.machine.dao.ImsTermTransferMapper;
import com.ryx.credit.machine.dao.ImsTermWarehouseDetailMapper;
import com.ryx.credit.machine.entity.ImsTermActive;
import com.ryx.credit.machine.entity.ImsTermTransfer;
import com.ryx.credit.machine.entity.ImsTermTransferDetail;
import com.ryx.credit.machine.entity.ImsTermWarehouseDetail;
import com.ryx.credit.machine.service.ImsTermActiveService;
import com.ryx.credit.machine.service.ImsTermWarehouseDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger log = LoggerFactory.getLogger(ImsTermWarehouseDetailServiceImpl.class);

    private final static String ZHYY_CREATE_PERSON = AppConfig.getProperty("zhyy_create_person");
    private final static String ZHYY_ROOT_ORG_ID = AppConfig.getProperty("zhyy_root_org_id");

    @Autowired
    private ImsTermActiveService imsTermActiveService;
    @Autowired
    private ImsTermWarehouseDetailMapper imsTermWarehouseDetailMapper;
    @Autowired
    private ImsTermTransferMapper imsTermTransferMapper;
    @Autowired
    private ImsTermTransferDetailMapper imsTermTransferDetailMapper;

    /**
     * POS入库划拨操作
     * @param snList
     * @param imsTermWarehouseDetail
     * @return
     * @throws MessageException
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult insertWarehouseAndTransfer(List<String> snList, ImsTermWarehouseDetail imsTermWarehouseDetail)throws MessageException {
        if(null==snList){
            throw new MessageException("sn列表异常");
        }
        if(snList.size()==0){
            throw new MessageException("sn数据有误");
        }
        log.info("同步POS入库划拨数据开始:snList:{},请求参数:{}",snList.size(),imsTermWarehouseDetail);
        for (String sn : snList) {
            ImsTermActive imsTermActive = imsTermActiveService.selectByPrimaryKey(sn);
            //有记录就表示已激活
            if(null!=imsTermActive){
                throw new MessageException("Sn机具已激活");
            }
            String createTime = DateUtil.format(new Date());
            imsTermWarehouseDetail.setWdId(IDUtils.genImsTermId());
            imsTermWarehouseDetail.setPosSn(sn);
            imsTermWarehouseDetail.setUseStatus("1"); //未使用
            imsTermWarehouseDetail.setStatus("0");  //正常
            imsTermWarehouseDetail.setCreateTime(createTime);
            imsTermWarehouseDetail.setCreatePerson(ZHYY_CREATE_PERSON);
            imsTermWarehouseDetail.setPosType("0");  //pos类型 0普通级，1：特价机
            imsTermWarehouseDetail.setPayStatus("1");  //支付状态 0 已付 1 未付

            int i = imsTermWarehouseDetailMapper.insert(imsTermWarehouseDetail);
            log.info("同步POS入库返回结果:{}",i);
            if(i!=1){
                throw new MessageException("SN库存插入失败");
            }
            String transferId = IDUtils.genImsTermId();
            ImsTermTransfer imsTermTransfer = new ImsTermTransfer();
            imsTermTransfer.setTransferId(transferId);
            imsTermTransfer.setStatus("0");  //0：处理完成
            imsTermTransfer.setOrgId(ZHYY_ROOT_ORG_ID);
            imsTermTransfer.setCreateTime(createTime);
            imsTermTransfer.setCreatePerson(ZHYY_CREATE_PERSON);
            imsTermTransfer.setTransferType("0");   //0:划拨

            int j = imsTermTransferMapper.insert(imsTermTransfer);
            log.info("同步POS划拨返回结果:{}",j);
            if(j!=1){
                throw new MessageException("SN划拨插入失败");
            }
            ImsTermTransferDetail imsTermTransferDetail = new ImsTermTransferDetail();
            imsTermTransferDetail.setId(IDUtils.genImsTermId());
            imsTermTransferDetail.setMachineId(imsTermWarehouseDetail.getMachineId());
            imsTermTransferDetail.setPosSn(sn);
            imsTermTransferDetail.setyOrgId(ZHYY_ROOT_ORG_ID);
            imsTermTransferDetail.setnOrgId(imsTermWarehouseDetail.getOrgId());
            imsTermTransferDetail.setCreateTime(createTime);
            imsTermTransferDetail.setCreatePerson(ZHYY_CREATE_PERSON);
            imsTermTransferDetail.setOperOrgId(imsTermWarehouseDetail.getOrgId());
            imsTermTransferDetail.setTransferId(transferId);

            int k = imsTermTransferDetailMapper.insert(imsTermTransferDetail);
            log.info("同步POS划拨明细返回结果:{}",k);
            if(k!=1){
                throw new MessageException("SN划拨明细插入失败");
            }
        }
        return AgentResult.ok();
    }
}
