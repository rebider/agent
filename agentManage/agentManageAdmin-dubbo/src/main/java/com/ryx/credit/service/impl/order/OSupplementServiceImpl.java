package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.dao.agent.BusActRelMapper;
import com.ryx.credit.dao.order.OPaymentDetailMapper;
import com.ryx.credit.dao.order.OSupplementMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.OPaymentDetail;
import com.ryx.credit.pojo.admin.order.OSupplement;
import com.ryx.credit.pojo.admin.vo.OsupplementVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.impl.agent.AgentServiceImpl;
import com.ryx.credit.service.order.OSupplementService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private ActivityService activityService;

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
        oSupplement.setPkType("1");
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

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO startSuppActivity(String id, String userId) throws ProcessException {
        logger.info("========用户{}启动补款审核{}", userId, id);
        if (StringUtils.isBlank(id)) {
            logger.info("补款审批,补款ID为空{}:{}", id, userId);
            return ResultVO.fail("补款审批中，补款ID为空");
        }
        if (StringUtils.isBlank(userId)) {
            logger.info("补款审批,操作用户为空{}:{}", id, userId);
            return ResultVO.fail("补款审批中，操作用户为空");
        }
        OSupplement oSupplement = oSupplementMapper.selectByPrimaryKey(id);
        //检查是否有审批中补款
        BusActRelExample example = new BusActRelExample();
        example.or().andBusIdEqualTo(id).andActivStatusEqualTo(AgStatus.Approving.name()).andStatusEqualTo(Status.STATUS_1.status);
        if (busActRelMapper.selectByExample(example).size() > 0) {
            logger.info("补款审批,禁止重复提交审批{}:{}", id, userId);
            return ResultVO.fail("补款审批中，禁止重复提交审批");
        }
        List<Dict> actlist = dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.ACT_RETURN_FINANCE.name());
        String workId = null;
        for (Dict dict : actlist) {
            workId = dict.getdItemvalue();
        }
        oSupplement.setReviewStatus(AgStatus.Approving.status);

        if (1 != oSupplementMapper.updateByPrimaryKeySelective(oSupplement)) {
            logger.info("补款审批，启动审批异常，更新记录状态{}:{}", oSupplement.getId(), userId);
            throw new ProcessException("更新记录状态异常");
        }
        if (StringUtils.isEmpty(workId)) {
            logger.info("========用户{}启动数据修改申请{}{}", id, userId, "审批流启动失败字典中未配置部署流程");
            throw new ProcessException("审批流启动失败字典中未配置部署流程!");
        }
        Map startPar = agentEnterService.startPar(userId);
        if (null == startPar) {
            logger.info("========用户{}启动补款审批{}{}启动部门参数为空", id, userId, "审批流启动失败字典中未配置部署流程");
            throw new ProcessException("启动部门参数为空!");
        }

        String proce = activityService.createDeloyFlow(null, workId, null, null, startPar);
        if (proce == null) {
            logger.info("========用户{}启动补款审批申请{}{}", id, userId, "补款审批，审批流启动失败");
            logger.info("补款审批，审批流启动失败{}:{}", id, userId);
            throw new ProcessException("审批流启动失败!");
        }
        //补款业务流程关系
        BusActRel record = new BusActRel();
        record.setBusId(oSupplement.getId());
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(userId);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.PkType.name());//流程关系类型是数据申请类型
        record.setActivStatus(AgStatus.Approving.name());
        if (1 != busActRelMapper.insertSelective(record)) {
            logger.info("补款审批审批，启动审批异常，添加审批关系失败{}:{}", oSupplement.getId(), proce);
            throw new ProcessException("添加审批关系失败");

        }
        return ResultVO.success(null);
    }

    @Override
    public OSupplement informationQuery(String id) {
        return  oSupplementMapper.selectByPrimaryKey(id);
    }
}
