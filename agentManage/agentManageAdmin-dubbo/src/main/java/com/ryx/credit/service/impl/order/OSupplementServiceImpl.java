package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.dao.order.OPaymentDetailMapper;
import com.ryx.credit.dao.order.OSupplementMapper;
import com.ryx.credit.pojo.admin.agent.AttachmentRel;
import com.ryx.credit.pojo.admin.order.OPaymentDetail;
import com.ryx.credit.pojo.admin.order.OSupplement;
import com.ryx.credit.pojo.admin.vo.OsupplementVo;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.impl.agent.AgentServiceImpl;
import com.ryx.credit.service.order.OSupplementService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.management.resources.agent;

import java.math.BigDecimal;
import java.util.*;

@Service("oSupplementService")
public class OSupplementServiceImpl implements OSupplementService {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(OSupplementServiceImpl.class);
    @Autowired
    private OSupplementMapper oSupplementMapper;
    @Autowired
    private OPaymentDetailMapper oPaymentDetailMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private AttachmentRelMapper attachmentRelMapper;

    @Override
    public PageInfo selectAll(Page page, OSupplement oSupplement, String time) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(oSupplement.getPkType())) {
            map.put("pkType", oSupplement.getPkType());
        }
        if (StringUtils.isNotBlank(oSupplement.getPayMethod())) {
            map.put("payMethod", oSupplement.getPayMethod());
        }
        if (!StringUtils.isEmpty(oSupplement.getReviewStatus())) {
            map.put("reviewStatus", oSupplement.getReviewStatus());
        }
        if (StringUtils.isNotBlank(time)) {
            String reltime = time.substring(0, 10);
            map.put("time", reltime);
        }
        List<Map<String, Object>> supplementList = oSupplementMapper.selectAll(map, page);
        for (Map<String, Object> maps : supplementList) {
            maps.put("PK_TYPE", PkType.gePkTypeValue(String.valueOf(maps.get("PK_TYPE"))));//补款类型
            maps.put("PAY_METHOD", PayMethod.getPayMethod(String.valueOf(maps.get("PAY_METHOD"))));//付款方式
            maps.put("REVIEW_STATUS", AgStatus.getMsg((BigDecimal) (maps.get("REVIEW_STATUS"))));//审核状态
            maps.put("SCHSTATUS", SchStatus.getMsg((BigDecimal) maps.get("SCHSTATUS")));//补款状态
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(supplementList);
        pageInfo.setTotal(oSupplementMapper.getCount(map));
        return pageInfo;
    }

    @Override
    public OPaymentDetail selectById(String id) {
        OPaymentDetail oPaymentDetail = oPaymentDetailMapper.selectById(id);
        return oPaymentDetail;
    }

    @Override
    public ResultVO supplementSave(OsupplementVo osupplementVo) {
        OSupplement oSupplement = osupplementVo.getSupplement();
        if (oSupplement == null) {
            logger.info("补款添加:{}", "补款添加信息为空");
            throw new ProcessException("补款信息为空");
        }
        if (StringUtils.isEmpty(oSupplement.getcUser())) {
            logger.info("补款添加:{}", "操作用户不能为空");
            throw new ProcessException("操作用户不能为空");
        }
        if (StringUtils.isEmpty(oSupplement.getReviewStatus())) {
            logger.info("补款添加：{}", "审批状态不能为空");
            throw new ProcessException("审批状态不能为空");
        }
        Date date = Calendar.getInstance().getTime();
        oSupplement.setId(idService.genId(TabId.o_Supplement));
        oSupplement.setcTime(date);
        oSupplement.setStatus(Status.STATUS_1.status);
        oSupplement.setVersion(Status.STATUS_1.status);
        if (1 == oSupplementMapper.insertSelective(oSupplement)) {
            osupplementVo.setSupplement(oSupplement);
            List<String> file = osupplementVo.getAgentTableFile();
            if (null != file) {
                for (String s : file) {
                    if (org.apache.commons.lang.StringUtils.isEmpty(s)) continue;
                    AttachmentRel record = new AttachmentRel();
                    record.setAttId(s);
                    record.setSrcId(oSupplement.getId());
                    record.setcUser(oSupplement.getcUser());
                    record.setcTime(oSupplement.getcTime());
                    record.setStatus(Status.STATUS_1.status);
                    record.setBusType(AttachmentRelType.Agent.name());
                    record.setId(idService.genId(TabId.a_attachment_rel));
                    if (1 != attachmentRelMapper.insertSelective(record)) {
                        logger.info("补款添加:{}", "上传打款截图失败");
                        throw new ProcessException("上传打款截图失败");
                    }
                }
            }
            logger.info("补款添加:成功");
        }
        return ResultVO.success(osupplementVo);
    }
}
