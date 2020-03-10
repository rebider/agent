package com.ryx.credit.service.impl.order;

import com.ryx.credit.dao.order.OOrderMapper;
import com.ryx.credit.dao.order.OPayDetailMapper;
import com.ryx.credit.dao.order.OPaymentDetailMapper;
import com.ryx.credit.pojo.admin.order.OPayDetail;
import com.ryx.credit.pojo.admin.order.OPayDetailExample;
import com.ryx.credit.pojo.admin.order.OPaymentDetail;
import com.ryx.credit.pojo.admin.order.ORefundAgent;
import com.ryx.credit.pojo.admin.vo.OPayDetailVo;
import com.ryx.credit.service.order.OPayDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service("oPayDetailService")
public class OPayDetailServiceImpl implements OPayDetailService {

    private Logger logger = LoggerFactory.getLogger(OPayDetailServiceImpl.class);
    @Autowired
    private OPayDetailMapper oPayDetailMapper;
    @Autowired
    private OPaymentDetailMapper oPaymentDetailMapper;

    @Override
    public Map<String, Object> getAdjDetail(String srcId, String payType) {
        logger.info("查询付款明细");
        Map<String,Object> resMap = new HashMap<>();
        BigDecimal takeAmount = BigDecimal.ZERO;
        OPayDetailExample oPayDetailExample = new OPayDetailExample();
        oPayDetailExample.or().andSrcIdEqualTo(srcId)
                .andPayTypeEqualTo(payType);
        List<OPayDetail> oPayDetails = oPayDetailMapper.selectByExample(oPayDetailExample);
        List<OPayDetailVo> oPayDetailVos = new ArrayList<>();
        if (oPayDetails != null && oPayDetails.size() > 0){
            for (OPayDetail oPayDetail:oPayDetails){
                OPayDetailVo oPayDetailVo = new OPayDetailVo();
                oPayDetailVo.setArrId(oPayDetail.getArrId());
                oPayDetailVo.setAmount(oPayDetail.getAmount());
                OPaymentDetail oPaymentDetail = oPaymentDetailMapper.selectById(oPayDetailVo.getArrId());
                oPayDetailVo.setOrderId(oPaymentDetail.getOrderId());
                oPayDetailVos.add(oPayDetailVo);
                takeAmount = takeAmount.add(oPayDetail.getAmount());
                resMap.put("viewDetails",oPayDetailVos);
                resMap.put("takeAmount",takeAmount);
            }
        }

        return resMap;
    }
}
