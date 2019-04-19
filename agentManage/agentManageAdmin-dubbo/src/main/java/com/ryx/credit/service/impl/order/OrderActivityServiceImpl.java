package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.PlatFormMapper;
import com.ryx.credit.dao.order.OActivityMapper;
import com.ryx.credit.dao.order.OProductMapper;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.TermMachineVo;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.order.OActivity;
import com.ryx.credit.pojo.admin.order.OActivityExample;
import com.ryx.credit.pojo.admin.order.OProduct;
import com.ryx.credit.profit.service.ProfitMonthService;
import com.ryx.credit.service.dict.DictOptionsService;
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
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private RedisService redisService;


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
        example.setOrderByClause(" ACT_CODE desc");
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
        if (StringUtils.isBlank(activity.getPlatform())) {
            logger.info("请选择平台类型");
            throw new MessageException("请选择平台类型");
        }
        if (StringUtils.isBlank(activity.getActCode())) {
            logger.info("活动代码不能为空");
            throw new MessageException("活动代码不能为空");
        }
        if (activity.getPrice()==null) {
            logger.info("活动价格不能为空");
            throw new MessageException("活动价格不能为空");
        }
        if (activity.getOriginalPrice()==null) {
            logger.info("商品原价格不能为空");
            throw new MessageException("商品原价格不能为空");
        }
        if (activity.getBusProCode()==null) {
            logger.info("BusProCode不能为空");
            throw new MessageException("BusProCode不能为空");
        }
        activity.setId(idService.genId(TabId.o_activity));
        Date nowDate = new Date();
        activity.setcTime(nowDate);
        activity.setuTime(nowDate);
        activity.setStatus(Status.STATUS_1.status);
        activity.setVersion(Status.STATUS_1.status);

        String platFormType = platFormMapper.selectPlatType(activity.getPlatform());
        if (StringUtils.isNotBlank(platFormType)) {
            try {
                List<TermMachineVo> termMachineVos = termMachineService.queryTermMachine(PlatformType.getContentEnum(platFormType));
                for (TermMachineVo termMachineVo : termMachineVos) {
                    if (activity.getBusProCode().equals(termMachineVo.getId())) {
                        activity.setStandAmt(BigDecimal.valueOf(Integer.valueOf(termMachineVo.getStandAmt())));
                        activity.setBackType(termMachineVo.getBackType());
                    }
                }
            } catch (Exception e) {
                logger.info(e.getMessage());
                e.printStackTrace();
            }
        }

        OActivityExample oActivityExample = new OActivityExample();
        oActivityExample.or().andActCodeEqualTo(activity.getActCode()).andStatusEqualTo(Status.STATUS_1.status);
        oActivityExample.setOrderByClause(" c_time desc ");
        List<OActivity>  list = activityMapper.selectByExample(oActivityExample);

        for (OActivity oActivity : list) {
            if(oActivity.getPrice()!=null && oActivity.getPrice().compareTo(activity.getPrice())!=0){
                throw new MessageException("相同活动代码价格必须相同");
            }
        }

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
        if (activity.getOriginalPrice()==null) {
            return new AgentResult(500, "商品原价不能为空", "");
        }
        if (activity.getPrice()==null) {
            return new AgentResult(500, "活动价格不能为空", "");
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

        if (StringUtils.isNotBlank(platFormType)) {
            try {
                List<TermMachineVo> termMachineVos = termMachineService.queryTermMachine(PlatformType.getContentEnum(platFormType));
                for (TermMachineVo termMachineVo : termMachineVos) {
                    if (activity.getBusProCode().equals(termMachineVo.getId())) {
                        activity.setStandAmt(BigDecimal.valueOf(Integer.valueOf(termMachineVo.getStandAmt())));
                        activity.setBackType(termMachineVo.getBackType());
                    }
                }
            } catch (Exception e) {
                logger.info(e.getMessage());
                e.printStackTrace();
            }
        }

        OActivityExample oActivityExample = new OActivityExample();
        oActivityExample.or().andActCodeEqualTo(activity.getActCode()).andStatusEqualTo(Status.STATUS_1.status).andIdNotEqualTo(activity.getId());
        oActivityExample.setOrderByClause(" c_time desc ");
        List<OActivity>  list = activityMapper.selectByExample(oActivityExample);

        for (OActivity oActivity : list) {
            if(oActivity.getPrice()!=null && oActivity.getPrice().compareTo(activity.getPrice())!=0){
                return AgentResult.fail("相同活动代码价格必须相同");
            }
        }

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
    public List<OActivity> productActivity(String product, String angetId,String orderAgentBusifo,String oldActivityId) {
        //TODO 检查代理商销售额
//        BigDecimal transAmt = profitMonthService.getTranByAgentId(angetId);
//        BigDecimal transAmt = new BigDecimal(800000000);
        OProduct productObj = oProductMapper.selectByPrimaryKey(product);
//        OActivityExample example = new OActivityExample();
//        example.or().andProductIdEqualTo(productObj.getId())
//                .andBeginTimeLessThanOrEqualTo(new Date())
//                .andEndTimeGreaterThanOrEqualTo(new Date());
//        List<OActivity> activitys = activityMapper.selectByExample(example);
        //如果传递老的活动就排除老的活动

        //查询条件
        Date date = new Date();
        FastMap par = FastMap.fastMap("productId",productObj.getId())
                .putKeyV("beginTime",date)
                .putKeyV("endTime",date);

        OActivity oldActivity = null;
        if(StringUtils.isNotBlank(oldActivityId)) {
            //如果变更活动传递老活动，排除老的活动代码并匹配 相同的厂商和型号。
            oldActivity = activityMapper.selectByPrimaryKey(oldActivityId);
            par.putKeyV("notEqActcode",oldActivity.getActCode()).putKeyV("vender",oldActivity.getVender()).putKeyV("proModel",oldActivity.getProModel());
        }
        if(StringUtils.isNotBlank(orderAgentBusifo) && !"null".equals(orderAgentBusifo)) {
            //查询平台活动
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(orderAgentBusifo);
            par.putKeyV("platform",agentBusInfo.getBusPlatform());
        }


        List<Map<String,Object>> actList = activityMapper.productActivityOrderBuild(par);
        List<OActivity> activitys = new ArrayList<OActivity>();
        for (Map<String, Object> stringObjectMap : actList) {
            OActivity oActivity = new OActivity();
            oActivity.setId(stringObjectMap.get("ID")+"");
            oActivity.setActivityName(stringObjectMap.get("ACTIVITYNAME")+"");
            oActivity.setPrice(new BigDecimal(stringObjectMap.get("PRICE")+""));
            oActivity.setActCode(stringObjectMap.get("ACT_CODE")+"");
            oActivity.setOriginalPrice(new BigDecimal(stringObjectMap.get("ORIGINALPRICE")+""));
            activitys.add(oActivity);
        }

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
    public Map selectTermMachine(String platformNum) throws MessageException {
        if (StringUtils.isBlank(platformNum)){
            throw new MessageException("平台类型为空");
        }
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


    @Override
    public List<Map<String,String>> planChoiseProComAndModel(String productId, String orderId) {
        Date date = new Date();
        List<OActivity> OActivityList  = activityMapper.planChoiseProComAndModel(
                FastMap.fastMap("productId",productId)
                        .putKeyV("orderId",orderId)
                        .putKeyV("beginTime",date)
                        .putKeyV("endTime",date));
        List<Map<String,String>> resList = new ArrayList<>();
        for (OActivity oActivity : OActivityList) {
            Map<String,String> item = new HashMap<>();
            item.put("proCom",oActivity.getVender());
            item.put("proModel",oActivity.getProModel());
            item.put("standTime",oActivity.getStandTime()+"");
            item.put("standAmt",oActivity.getStandAmt()+"");
            item.put("backType", BackType.getContentByValue(oActivity.getBackType())+"");
            item.put("busProName",oActivity.getBusProName()+"");
            item.put("id",oActivity.getId());
            Dict dict = dictOptionsService.findDictByValue(DictGroup.ORDER.name(),DictGroup.MANUFACTURER.name(),oActivity.getVender());
            if(dict!=null){
                item.put("proComName",dict.getdItemname());
            }else{
                item.put("proComName","系统未配置该厂商信息");
            }
            resList.add(item);
        }
        return resList;
    }


    @Override
    public AgentResult querySnInfoFromBusSystem(String snStart, String snEnd, String count, String proModel)throws MessageException {

        Dict modelType = dictOptionsService.findDictByName(DictGroup.ORDER.name(), DictGroup.MODEL_TYPE.name(),proModel);
        if(modelType==null){
            throw new MessageException("导入类型错误");
        }
        FastMap res = FastMap.fastSuccessMap();
        Set<OActivity> actSet = new HashSet<>();
        if(modelType.getdItemvalue().equals(PlatformType.MPOS.code)){
            try {
                AgentResult agentResult = termMachineService.querySnMsg(PlatformType.MPOS,snStart, snEnd);
                if(!agentResult.isOK()){
                    throw new MessageException("查询手刷失败");
                }
                logger.info("根据sn查询业务系统返回:"+agentResult.getData());
                List<Map<String,Object>> data = (List<Map<String,Object>>)agentResult.getData();
                if(data.size()!=Integer.parseInt(count)){
                    logger.info("查询手刷根据SN号段查询机具信息数量：{},count:{}",data.size(),count);
                    throw new MessageException("sn数量有误");
                }
                String orgid = "";
                for (Map<String, Object> map : data) {
                    if(StringUtils.isBlank(orgid)) {
                        orgid = String.valueOf(map.get("agencyId"));
                    }
                    String termActiveId = String.valueOf(map.get("termActiveId"));
                    String termActiveName = String.valueOf(map.get("termActiveName"));
                    String termBatchId = String.valueOf(map.get("termBatchId"));
                    String termTypeId = String.valueOf(map.get("termTypeId"));
                    String sn = String.valueOf(map.get("termId"));

                    OActivityExample oActivityExample = new OActivityExample();
                    OActivityExample.Criteria activityCriteria = oActivityExample.createCriteria();
                    activityCriteria.andStatusEqualTo(Status.STATUS_1.status);
                    activityCriteria.andBusProCodeEqualTo(termActiveId);
                    // 活动E - > 满5K返120+20+20(第2、3个月20月结)
                    //activityCriteria.andBusProNameEqualTo(termActiveName);
                    activityCriteria.andTermBatchcodeEqualTo(termBatchId);
                    activityCriteria.andTermtypeEqualTo(termTypeId);
                    List<OActivity> oActivities = activityMapper.selectByExample(oActivityExample);
                    if(oActivities==null){
                        throw new MessageException(sn+"活动未找到");
                    }
                    if(oActivities.size()==0){
                        throw new MessageException(sn+"活动未找到");
                    }
                    Set<BigDecimal> priceSet = new HashSet<>();
                    for (OActivity oActivity : oActivities) {
                        priceSet.add(oActivity.getPrice());
                    }
                    if(priceSet.size()!=1){
                        throw new MessageException(sn+"价格配置错误");
                    }
                    actSet.add(oActivities.get(0));
                }
                if(actSet.size()!=1){
                    throw new MessageException(snStart+"到"+snEnd+":活动不一致,请分开上传");
                }
                for (Map<String, Object> map : data) {
                    String sn = String.valueOf(map.get("termId"));
                    Map<String,String> redisMap = new HashMap<>();
                    redisMap.put("sn",sn);
                    redisMap.put("termActiveId",String.valueOf(map.get("termActiveId")));
                    redisMap.put("termActiveName",String.valueOf(map.get("termActiveName")));
                    redisMap.put("termBatchId",String.valueOf(map.get("termBatchId")));
                    redisMap.put("termTypeId",String.valueOf(map.get("termTypeId")));
                    redisMap.put("termCheck",String.valueOf(map.get("termCheck")));
                    redisMap.put("agencyId",String.valueOf(map.get("agencyId")));
                    redisMap.put("agencyName",String.valueOf(map.get("agencyName")));
                    redisService.hSet(snStart+","+snEnd,sn, JsonUtil.objectToJson(redisMap));
                }
                redisService.delete(snStart+","+snEnd+"_act");
                for (OActivity activity : actSet) {
                    redisService.lpushList(snStart+","+snEnd+"_act",activity.getId());
                }

                //放入代理商信息
                redisService.setValue(snStart+","+snEnd+"_org",orgid,60*60*24L);

                OActivity oActivity = actSet.iterator().next();
                res.putKeyV("snStart",snStart)
                        .putKeyV("snEnd",snEnd)
                        .putKeyV("count",count)
                        .putKeyV("price",oActivity.getPrice())
                        .putKeyV("amt",oActivity.getPrice().multiply(new BigDecimal(count)))
                        .putKeyV("activity",actSet)
                        .putKeyV("modelType",modelType.getdItemvalue());
            } catch (Exception e) {
                e.printStackTrace();
                throw new MessageException("查询机具sn异常:"+e.getLocalizedMessage());
            }
        }else {
            try {
                AgentResult agentResult = termMachineService.querySnMsg(PlatformType.POS,snStart, snEnd);
                if(!agentResult.isOK()){
                    throw new MessageException("查询pos失败");
                }
                logger.info("根据sn查询业务系统返回:"+agentResult.getMsg());
                JSONObject jsonObject = JSONObject.parseObject(agentResult.getMsg());
                JSONObject data = JSONObject.parseObject(String.valueOf(jsonObject.get("data")));
                logger.info(String.valueOf(data.get("termMachineList")));
                List<Map<String,Object>> termMachineListMap = (List<Map<String,Object>>) JSONArray.parse(String.valueOf(data.get("termMachineList")));
                if(termMachineListMap.size()!=Integer.parseInt(count)){
                    logger.info("查询pos根据SN号段查询机具信息数量：{},count:{}",termMachineListMap.size(),count);
                    throw new MessageException("sn数量有误");
                }
                String orgid = "";
                for (Map<String, Object> map : termMachineListMap) {
                    if(StringUtils.isBlank(orgid)) {
                        orgid = String.valueOf(map.get("orgId"));
                    }
                    String posSn = String.valueOf(map.get("posSn"));
                    String tmsModel = String.valueOf(map.get("tmsModel"));
                    String machineManufName = String.valueOf(map.get("machineManufName"));
                    String machineId = String.valueOf(map.get("machineId"));
                    String posType = String.valueOf(map.get("posType"));
                    Dict manufaName = dictOptionsService.findDictByName(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(), machineManufName);
                    if (manufaName == null) {
                        throw new MessageException(machineManufName+"厂商不存在");
                    }
                    String manufaValue = manufaName.getdItemvalue();//厂商
                    OActivityExample oActivityExample = new OActivityExample();
                    OActivityExample.Criteria activityCriteria = oActivityExample.createCriteria();
                    activityCriteria.andStatusEqualTo(Status.STATUS_1.status);
//                    activityCriteria.andVenderEqualTo(manufaValue);
//                    activityCriteria.andProModelEqualTo(tmsModel);
                    activityCriteria.andPosTypeEqualTo(posType);
                    activityCriteria.andBusProCodeEqualTo(machineId);
                    List<OActivity> oActivities = activityMapper.selectByExample(oActivityExample);
                    if(oActivities==null){
                        throw new MessageException(posSn+"活动未找到");
                    }
                    if(oActivities.size()==0){
                        throw new MessageException(posSn+"活动未找到");
                    }
                    Set<BigDecimal> priceSet = new HashSet<>();
                    for (OActivity oActivity : oActivities) {
                        priceSet.add(oActivity.getPrice());
                    }
                    if(priceSet.size()!=1){
                        throw new MessageException(posSn+"价格配置错误");
                    }
                    actSet.add(oActivities.get(0));
                }
                if(actSet.size()!=1){
                    throw new MessageException(snStart+"到"+snEnd+":活动不一致,请分开上传");
                }
                for (Map<String, Object> map : termMachineListMap) {
                    Map<String,String> redisMap = new HashMap<>();
                    String posSn = String.valueOf(map.get("posSn"));
                    redisMap.put("posSn",posSn);
                    redisMap.put("tmsModel",String.valueOf(map.get("tmsModel")));
                    String machineManufName = String.valueOf(map.get("machineManufName"));
                    redisMap.put("machineManufName",machineManufName);
                    redisMap.put("machineId",String.valueOf(map.get("machineId")));
                    redisMap.put("posType",String.valueOf(map.get("posType")));
                    Dict manufaName = dictOptionsService.findDictByName(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(), machineManufName);
                    if (manufaName == null) {
                        throw new MessageException(machineManufName + "厂商不存在");
                    }
                    String manufaValue = manufaName.getdItemvalue();//厂商
                    redisMap.put("manufaValue",manufaValue);
                    redisService.hSet(snStart+","+snEnd,posSn, JsonUtil.objectToJson(redisMap));
                }
                //号段活动存储在redis中
                redisService.delete(snStart+","+snEnd+"_act");
                for (OActivity activity : actSet) {
                    redisService.lpushList(snStart+","+snEnd+"_act",activity.getId());
                }
                //放入代理商信息
                redisService.setValue(snStart+","+snEnd+"_org",orgid,60*60*24L);
                OActivity oActivity = actSet.iterator().next();
                res.putKeyV("snStart",snStart)
                        .putKeyV("snEnd",snEnd)
                        .putKeyV("count",count)
                        .putKeyV("price",oActivity.getPrice())
                        .putKeyV("amt",oActivity.getPrice().multiply(new BigDecimal(count)))
                        .putKeyV("activity",actSet)
                        .putKeyV("modelType",modelType.getdItemvalue());
            } catch (Exception e) {
                e.printStackTrace();
                throw new MessageException("sn信息查询异常:"+e.getMessage());
            }
        }
        AgentResult agentResult = AgentResult.ok();
        agentResult.setMapData(res);
        return agentResult;
    }
}
