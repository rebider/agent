package com.ryx.credit.service.impl.order;

import com.ryx.credit.dao.order.OPayDetailMapper;
import com.ryx.credit.dao.order.OPaymentDetailMapper;
import com.ryx.credit.pojo.admin.order.OPayDetail;
import com.ryx.credit.pojo.admin.order.OPayDetailExample;
import com.ryx.credit.pojo.admin.order.OPaymentDetail;
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

    @Override
    public Map<String, Object> getAdjDetail(String srcId, String payType, String userId, String agentId) {
        logger.info("查询付款明细");
        Map<String,Object> resMap = new HashMap<>();
        BigDecimal refundAmount = BigDecimal.ZERO;
        OPayDetailExample oPayDetailExample = new OPayDetailExample();
        oPayDetailExample.or().andSrcIdEqualTo(srcId)
                .andPayTypeEqualTo(payType)
                .andCUserEqualTo(userId);
        List<OPayDetail> oPayDetails = oPayDetailMapper.selectByExample(oPayDetailExample);
        if (oPayDetails != null && oPayDetails.size() > 0){
            for (OPayDetail oPayDetail:oPayDetails){
                refundAmount = refundAmount.add(oPayDetail.getAmount());
            }
            resMap.put("details",oPayDetails);
        }
        resMap.put("refundAmount",refundAmount);
        return resMap;
    }
}
