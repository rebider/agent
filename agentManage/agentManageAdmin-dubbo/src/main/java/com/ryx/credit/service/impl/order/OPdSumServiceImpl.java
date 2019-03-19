package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.dao.order.OPaymentDetailMapper;
import com.ryx.credit.dao.order.OPdSumMapper;
import com.ryx.credit.pojo.admin.order.OPaymentDetail;
import com.ryx.credit.pojo.admin.order.OPdSum;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.IOPdSumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("OPdSumService")
public class OPdSumServiceImpl implements IOPdSumService {

    @Autowired
    OPaymentDetailMapper oPaymentDetailMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private OPdSumMapper oPdSumMapper;

    @Override
    public void insertOPdSum() {
        String month = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE).substring(0,7);
        Map<String, Object> mapMonth = new HashMap<>();
        mapMonth.put("month",month);
        List<Map<String, Object>> listOPd = oPaymentDetailMapper.getOpdSum(mapMonth);

        for (Map mapOPd : listOPd) {
            String agentId = (String) mapOPd.get("AGENT_ID");
            String orderPlatform = (String) mapOPd.get("ORDER_PLATFORM");
            BigDecimal sumAmount = (BigDecimal) mapOPd.get("SUM_AMOUNT");
            mapOPd.put("month",month);
          /*  Calendar c = Calendar.getInstance();
            c.set(Calendar.MONTH, +1);
            String date = DateUtil.format(c.getTime(), DateUtil.DATE_FORMAT_yyyyMM);*/
            OPdSum oPdSum = new OPdSum();
            oPdSum.setId(idService.genId(TabId.o_pd_sum));
            List<Map<String, Object>> listId = oPaymentDetailMapper.getOPaymentDetailID(mapOPd);
            for (Map<String, Object> mapid : listId) {
                String id = (String) mapid.get("ID");
                OPaymentDetail oPaymentDetail = new OPaymentDetail();
                oPaymentDetail.setId(id);
                oPaymentDetail.setoPdSumId(oPdSum.getId());
                /*oPaymentDetail.setPaymentStatus(Status.STATUS_5.status);*/
                oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail);
            }

            oPdSum.setAgentid(agentId);
            oPdSum.setPlatform(orderPlatform);
            oPdSum.setSumAmount(sumAmount);
            oPdSum.setSumStatus("Deduction_for");
            oPdSum.setStatus(Status.STATUS_1.status);
            oPdSum.setVersion(Status.STATUS_1.status);
            oPdSum.setSumMouth(month);
          /*  oPdSum.setcUser();*/
            oPdSum.setcTime(new Date());
            oPdSum.setuTime(new Date());
            oPdSumMapper.insert(oPdSum);

        }

    }

    @Override
    public int updateByPrimaryKeySelective(OPdSum record) {
        return oPdSumMapper.updateByPrimaryKeySelective(record);
    }






}
