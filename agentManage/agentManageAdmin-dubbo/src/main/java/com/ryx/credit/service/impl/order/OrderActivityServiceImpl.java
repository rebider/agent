package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.PlatFormMapper;
import com.ryx.credit.dao.order.OActivityMapper;
import com.ryx.credit.dao.order.OProductMapper;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.MposTermBatchVo;
import com.ryx.credit.machine.vo.MposTermTypeVo;
import com.ryx.credit.machine.vo.TermMachineVo;
import com.ryx.credit.pojo.admin.order.OActivity;
import com.ryx.credit.pojo.admin.order.OActivityExample;
import com.ryx.credit.pojo.admin.order.OProduct;
import com.ryx.credit.profit.service.ProfitMonthService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.OrderActivityService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by RYX on 2018/7/13.
 */
@Service("orderActivityService")
public class OrderActivityServiceImpl implements OrderActivityService {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(OrderActivityServiceImpl.class);
    @Autowired
    private OActivityMapper activityMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private OProductMapper oProductMapper;
    @Autowired
    private ProfitMonthService profitMonthService;
    @Autowired
    private PlatFormMapper platFormMapper;
    @Autowired
    private TermMachineService termMachineService;

    @Override
    public PageInfo activityList(OActivity activity, Page page) {
        OActivityExample example = new OActivityExample();
        OActivityExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(activity.getActivityName())) {
            criteria.andActivityNameEqualTo(activity.getActivityName());
        }
        if (StringUtils.isNotBlank(activity.getPlatform())) {
            criteria.andPlatformEqualTo(activity.getPlatform());
        }
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        example.setOrderByClause("C_TIME desc");
        example.setPage(page);
        List<OActivity> activitys = activityMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(activitys);
        pageInfo.setTotal(activityMapper.countByExample(example));
        return pageInfo;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO saveActivity(OActivity activity) throws MessageException {
        if (StringUtils.isBlank(activity.getProductId())) {
            logger.info("请选择商品名称");
            throw new MessageException("请选择商品名称");
        }
        if (StringUtils.isBlank(activity.getVender())) {
            logger.info("请选择厂家");
            throw new MessageException("请选择厂家");
        }
        if (StringUtils.isBlank(activity.getProModel())) {
            logger.info("请选择型号");
            throw new MessageException("请选择型号");
        }
        activity.setId(idService.genId(TabId.o_activity));
        Date nowDate = new Date();
        activity.setcTime(nowDate);
        activity.setuTime(nowDate);
        activity.setStatus(Status.STATUS_1.status);
        activity.setVersion(Status.STATUS_1.status);
        int insert = activityMapper.insert(activity);
        if (insert != 1) {
            throw new MessageException("添加失败");
        }
        return ResultVO.success("");
    }


    @Override
    public AgentResult updateActivity(OActivity activity) {
        AgentResult result = new AgentResult(500, "参数错误", "");
        if (activity == null) {
            return result;
        }
        if (StringUtils.isBlank(activity.getId())) {
            return result;
        }
        String platFormType= platFormMapper.selectPlatType(activity.getPlatform());

        if (StringUtils.isNotBlank(platFormType)){
            if (platFormType.equals(PlatformType.POS.code) || platFormType.equals(PlatformType.ZPOS.code)){
                //如果是POS或者是智能POS  则需要清除终端批次和终端类型的id name
                activity.setTermBatchcode(" ");
                activity.setTermBatchname(" ");
                activity.setTermtype(" ");
                activity.setTermtypename(" ");
            }
        }
        activity.setuTime(new Date());
        int update = activityMapper.updateByPrimaryKeySelective(activity);
        if (update == 1) {
            return AgentResult.ok();
        }
        return result;
    }

    @Override
    public OActivity findById(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }
        return activityMapper.selectByPrimaryKey(id);
    }

    @Override
    public AgentResult deleteById(String id) {
        AgentResult result = new AgentResult(500, "参数错误", "");
        if (StringUtils.isBlank(id)) {
            return result;
        }
        OActivity activity = new OActivity();
        activity.setId(id);
        activity.setuTime(new Date());
        activity.setStatus(Status.STATUS_0.status);
        int update = activityMapper.updateByPrimaryKeySelective(activity);
        if (update == 1) {
            return AgentResult.ok();
        }
        return result;
    }

    @Override
    public List<OActivity> allActivity() {
        OActivityExample example = new OActivityExample();
        OActivityExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<OActivity> activitys = activityMapper.selectByExample(example);
        return activitys;
    }


    @Override
    public List<OActivity> productActivity(String product, String angetId) {
        //TODO 检查代理商销售额
//        BigDecimal transAmt = profitMonthService.getTranByAgentId(angetId);
//        BigDecimal transAmt = new BigDecimal(800000000);
        OProduct productObj = oProductMapper.selectByPrimaryKey(product);
        OActivityExample example = new OActivityExample();
        example.or().andProductIdEqualTo(productObj.getId())
                .andBeginTimeLessThanOrEqualTo(new Date())
                .andEndTimeGreaterThanOrEqualTo(new Date());
        List<OActivity> activitys = activityMapper.selectByExample(example);
//        List<OActivity> newActivitys = new ArrayList<>();
//        for (OActivity activity : activitys) {
//            BigDecimal activityRule = new BigDecimal(activity.getActivityRule());
//            if(activity.getActivityCondition().equals(">")){
//                if(transAmt.compareTo(activityRule) > 0) {
//                    newActivitys.add(activity);
//                }
//            }else if(activity.getActivityCondition().equals(">=")){
//                if(transAmt.compareTo(activityRule) >= 0) {
//                    newActivitys.add(activity);
//                }
//            }else if(activity.getActivityCondition().equals("<=")){
//                if(transAmt.compareTo(activityRule) <= 0) {
//                    newActivitys.add(activity);
//                }
//            }else if(activity.getActivityCondition().equals("<")){
//                if(transAmt.compareTo(activityRule) < 0) {
//                    newActivitys.add(activity);
//                }
//            }
//        }
        return activitys;
    }

    @Override
    public Map selectTermMachine(String platformNum) {
     String platFormType= platFormMapper.selectPlatType(platformNum);
     List termMachineVos = null;
     List mposTermBatchVos=null;
        List mposTermTypeVos=null;
        HashMap<Object, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(platFormType)){
             try {
                 termMachineVos = termMachineService.queryTermMachine(PlatformType.getContentEnum(platFormType));

                 mposTermBatchVos= termMachineService.queryMposTermBatch(PlatformType.getContentEnum(platFormType));

                 mposTermTypeVos = termMachineService.queryMposTermType(PlatformType.getContentEnum(platFormType));
                 map.put("termMachineList",termMachineVos);
                 map.put("mposTermBatchList",mposTermBatchVos);
                 map.put("mposTermTypeList",mposTermTypeVos);
             } catch (Exception e) {
                 e.printStackTrace();
             }


     }

        return map;
    }
}
