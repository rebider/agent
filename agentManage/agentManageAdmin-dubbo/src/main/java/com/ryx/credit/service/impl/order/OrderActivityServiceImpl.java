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
import com.ryx.credit.dao.order.*;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.TermMachineVo;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.profit.service.ProfitMonthService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.OrderActivityService;
import org.apache.poi.hssf.usermodel.HSSFAnchor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.sn;

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
    @Autowired
    private OActivityVisibleMapper activityVisibleMapper;
    @Autowired
    private ORefundPriceDiffDetailMapper oRefundPriceDiffDetailMapper;


    @Override
    public PageInfo activityList(OActivity activity, Page page) {
        OActivityExample example = new OActivityExample();
        OActivityExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(activity.getActivityName())) {
            criteria.andActivityNameLike("%"+activity.getActivityName()+"%");
        }
        if (StringUtils.isNotBlank(activity.getPlatform())) {
            String platform =activity.getPlatform();
            if (platform.contains(",")) {
                List<String> platformList = Arrays.asList(platform.split(","));
                criteria.andPlatformIn(platformList);
            } else {
                criteria.andPlatformEqualTo(platform);
            }
        }
        if (StringUtils.isNotBlank(activity.getActCode())) {
            criteria.andActCodeEqualTo(activity.getActCode());
        }
        if (StringUtils.isNotBlank(activity.getVender())) {
            Dict vender = dictOptionsService.findDictByName(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(),activity.getVender());
            if(vender!=null) {
                criteria.andVenderEqualTo(vender.getdItemvalue());
            }
        }
        if (StringUtils.isNotBlank(activity.getProModel())) {
            criteria.andProModelEqualTo(activity.getProModel());
        }
        if (StringUtils.isNotBlank(activity.getProductId())) {
            String productId =activity.getProductId();
            if (productId.contains(",")) {
                List<String> productIdList = Arrays.asList(productId.split(","));
                criteria.andProductIdIn(productIdList);
            } else {
                criteria.andProductIdEqualTo(productId);
            }
        }
        if (StringUtils.isNotBlank(activity.getBusProName())) {
            criteria.andBusProNameLike("%"+activity.getBusProName()+"%");
        }
        if (StringUtils.isNotBlank(activity.getTermBatchname())) {
            criteria.andTermBatchnameLike("%"+activity.getTermBatchname()+"%");
        }
        if (StringUtils.isNotBlank(activity.getTermtypename())) {
            criteria.andTermtypenameEqualTo(activity.getTermtypename());
        }
        if (StringUtils.isNotBlank(activity.getPosType())) {
            String postype =activity.getPosType();
            if (postype.contains(",")) {
                List<String> postypeList = Arrays.asList(postype.split(","));
                criteria.andPosTypeIn(postypeList);
            } else {
                criteria.andPosTypeEqualTo(postype);
            }
        }
        if (StringUtils.isNotBlank(activity.getBusProCode())) {
            criteria.andBusProCodeEqualTo(activity.getBusProCode());
        }
        if (null!=activity.getPrice()) {
            criteria.andPriceEqualTo(activity.getPrice());
        }
        if (null!=activity.getOriginalPrice()) {
            criteria.andOriginalPriceEqualTo(activity.getOriginalPrice());
        }
        if (StringUtils.isNotBlank(activity.getId())) {
            criteria.andIdEqualTo(activity.getId());
        }
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        example.setOrderByClause(" ACT_CODE,c_time desc");
        example.setPage(page);
        List<OActivity> activitys = activityMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(activitys);
        pageInfo.setTotal((int) activityMapper.countByExample(example));
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
        }else {
            Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(), activity.getVender());
            if(null==dictByValue){
                throw new MessageException("请选择或者填写正确的厂家");
            }
        }
        if (StringUtils.isBlank(activity.getProModel())) {
            logger.info("请选择型号");
            throw new MessageException("请选择型号");
        } else {
            Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.PROMODE.name(), activity.getProModel());
            if(null==dictByValue){
                throw new MessageException("请选择或者填写正确的型号");
            }
        }
        if (StringUtils.isBlank(activity.getPlatform())) {
            logger.info("请选择平台类型");
            throw new MessageException("请选择平台类型");
        }
        if (StringUtils.isBlank(activity.getActCode())) {
            logger.info("活动代码不能为空");
            throw new MessageException("活动代码不能为空");
        }
        if (StringUtils.isNotBlank(activity.getActCode())) {
            List<String> actCodeList = dictOptionsService.dictValueList("AGENT", "ACTCODE");
           if(actCodeList.contains(activity.getActCode())){
               logger.info(actCodeList.toString() + "活动代码禁止使用");
               throw new MessageException(actCodeList.toString() + "活动代码禁止使用");
           }
        }
        if (activity.getPrice() == null) {
            logger.info("活动价格不能为空");
            throw new MessageException("活动价格不能为空");
        }
        if (activity.getOriginalPrice() == null) {
            logger.info("商品原价格不能为空");
            throw new MessageException("商品原价格不能为空");
        }
        if (null == activity.getQuantityLimit()) {
            logger.info("订货数量下限不能为空！");
            throw new MessageException("订货数量下限不能为空！");
        }

        String regex = "^\\+?[1-9][0-9]*$";
        if (!activity.getQuantityLimit().toString().matches(regex)) {
            throw new MessageException("订货数量下限输入框,请输入大于等于1的正整数！");
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
                List<TermMachineVo> termMachineVos = termMachineService.queryTermMachine(PlatformType.getContentEnum(platFormType),FastMap.fastSuccessMap());
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
        List<OActivity> list = activityMapper.selectByExample(oActivityExample);

        for (OActivity oActivity : list) {
            if (oActivity.getPrice() != null && oActivity.getPrice().compareTo(activity.getPrice()) != 0) {
                throw new MessageException("相同活动代码价格必须相同");
            }
        }
        OProductExample oProductExample = new OProductExample();
        OProductExample.Criteria criteria = oProductExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andIdEqualTo(activity.getProductId());
        List<OProduct> oProductList = oProductMapper.selectByExample(oProductExample);
        if (null!=oProductList && oProductList.size()>0){
            OProduct oProduct = oProductList.get(0);
            activity.setProType(oProduct.getProType());
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
        if (activity.getOriginalPrice() == null) {
            return new AgentResult(500, "商品原价不能为空", "");
        }
        if (activity.getPrice() == null) {
            return new AgentResult(500, "活动价格不能为空", "");
        }
        if (StringUtils.isBlank(activity.getVender())) {
            logger.info("请选择厂家");
            return AgentResult.fail("请选择厂家");
        }else {
            Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(), activity.getVender());
            if(null==dictByValue){
                logger.info("请选择或者填写正确的厂家");
                return AgentResult.fail("请选择或者填写正确的厂家");
            }
        }
        if (StringUtils.isBlank(activity.getProModel())) {
            logger.info("请选择型号");
            return AgentResult.fail("请选择型号");

        }else {
            Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.PROMODE.name(), activity.getProModel());
            if(null==dictByValue){
                logger.info("请选择或者填写正确的型号");
                return AgentResult.fail("请选择或者填写正确的型号");
            }
        }
        if (null == activity.getQuantityLimit()) {
            logger.info("订货数量下限不能为空！");
            return AgentResult.fail("订货数量下限不能为空！");
        }
        if (!activity.getQuantityLimit().toString().matches("^\\+?[1-9][0-9]*$")) {
            return AgentResult.fail("订货数量下限输入框,请输入大于等于1的正整数！");
        }
        String platFormType = platFormMapper.selectPlatType(activity.getPlatform());

        if (StringUtils.isNotBlank(platFormType)) {
            if (PlatformType.whetherPOS(platFormType)) {
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
                List<TermMachineVo> termMachineVos = termMachineService.queryTermMachine(PlatformType.getContentEnum(platFormType),FastMap.fastSuccessMap());
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
        List<OActivity> list = activityMapper.selectByExample(oActivityExample);

        for (OActivity oActivity : list) {
            if (oActivity.getPrice() != null && oActivity.getPrice().compareTo(activity.getPrice()) != 0) {
                return AgentResult.fail("相同活动代码价格必须相同");
            }
        }
        OActivity db_activity = activityMapper.selectByPrimaryKey(activity.getId());
        if (StringUtils.isNotBlank(activity.getActCode())) {
            List<String> actCodeList = dictOptionsService.dictValueList("AGENT", "ACTCODE");
            if((actCodeList.contains(db_activity.getActCode())) && (!actCodeList.contains(activity.getActCode()))){
                logger.info(actCodeList.toString() + "活动代码禁止使用");
                return AgentResult.fail(actCodeList.toString() + "活动代码禁止使用");
            }
        }
        OProductExample oProductExample = new OProductExample();
        OProductExample.Criteria criteria = oProductExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andIdEqualTo(activity.getProductId());
        List<OProduct> oProductList = oProductMapper.selectByExample(oProductExample);
        if (null!=oProductList && oProductList.size()>0){
            OProduct oProduct = oProductList.get(0);
            activity.setProType(oProduct.getProType());
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

    /**
     * 活动复制
     * @param activity
     * @return
     */
    @Override
    public AgentResult activityCopy(OActivity activity) {
        AgentResult result = new AgentResult(500, "参数错误", "");
        if (activity == null) {
            return result;
        }
        if (StringUtils.isBlank(activity.getId())) {
            return result;
        }
        if (activity.getOriginalPrice() == null) {
            return new AgentResult(500, "商品原价不能为空", "");
        }
        if (activity.getPrice() == null) {
            return new AgentResult(500, "活动价格不能为空", "");
        }
        if (StringUtils.isBlank(activity.getVender())) {
            logger.info("请选择厂家");
            return AgentResult.fail("请选择厂家");
        }else {
            Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(), activity.getVender());
            if(null==dictByValue){
                logger.info("请选择或者填写正确的厂家");
                return AgentResult.fail("请选择或者填写正确的厂家");
            }
        }

        if (StringUtils.isBlank(activity.getProModel())) {
            logger.info("请选择型号");
            return AgentResult.fail("请选择型号");

        }else {
            Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.PROMODE.name(), activity.getProModel());
            if(null==dictByValue){
                logger.info("请选择或者填写正确的型号");
                return AgentResult.fail("请选择或者填写正确的型号");
            }
        }
        if (null == activity.getQuantityLimit()) {
            logger.info("订货数量下限不能为空！");
            return AgentResult.fail("订货数量下限不能为空！");
        }

        if (!activity.getQuantityLimit().toString().matches("^\\+?[1-9][0-9]*$")) {
            return AgentResult.fail("订货数量下限输入框,请输入大于等于1的正整数！");
        }
        String platFormType = platFormMapper.selectPlatType(activity.getPlatform());
        if (StringUtils.isNotBlank(platFormType)) {
            if (PlatformType.whetherPOS(platFormType)) {
                //如果是POS或者是智能POS  则需要清除终端批次和终端类型的id name
                activity.setTermBatchcode(" ");
                activity.setTermBatchname(" ");
                activity.setTermtype(" ");
                activity.setTermtypename(" ");
            }
        }
        activity.setcTime(new Date());
        activity.setuTime(new Date());

        if (StringUtils.isNotBlank(platFormType)) {
            try {
                List<TermMachineVo> termMachineVos = termMachineService.queryTermMachine(PlatformType.getContentEnum(platFormType),FastMap.fastSuccessMap());
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
        List<OActivity> list = activityMapper.selectByExample(oActivityExample);

        for (OActivity oActivity : list) {
            if (oActivity.getPrice() != null && oActivity.getPrice().compareTo(activity.getPrice()) != 0) {
                return AgentResult.fail("相同活动代码价格必须相同");
            }
        }

        if (StringUtils.isNotBlank(activity.getActCode())) {
            if (StringUtils.isNotBlank(activity.getActCode())) {
                List<String> actCodeList = dictOptionsService.dictValueList("AGENT", "ACTCODE");
                if(actCodeList.contains(activity.getActCode())){
                    logger.info(actCodeList.toString() + "活动活动代码禁止使用");
                    return AgentResult.fail(actCodeList.toString() + "活动活动代码禁止使用");
                }
            }
        }

        int add_copy = activityMapper.insertSelective(activity);
        if (add_copy == 1) {
            return AgentResult.ok();
        }
        return result;
    }

    @Override
    public OActivity toActivityCopy(OActivity activity) {
        OActivity oActivity = new OActivity();
        oActivity.setId(idService.genId(TabId.o_activity));
        return oActivity;
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
    public List<OActivity> productActivity(String product, String angetId, String orderAgentBusifo, String oldActivityId) {

        //查询条件
        Date date = new Date();
        FastMap par = FastMap.fastMap("beginTime", date)
                             .putKeyV("endTime", date);
        ArrayList<Object> productIdList = new ArrayList<>();
        //如果有历史订单，说明是换互动进行跨平台活动查询
        if (StringUtils.isNotBlank(oldActivityId)) {
            //如果变更活动传递老活动，排除老的活动代码并匹配 相同的厂商和型号。
            OActivity oldActivity = activityMapper.selectByPrimaryKey(oldActivityId);
            //源平台可跨平台变更的平台
            List<Dict> listCanChange =  dictOptionsService.dictList(DictGroup.COMPENSATE_PLATFORM_TYPE.name(),oldActivity.getPlatform());
            //可选的平台活动
            List<String>  listCanChangePlat =new ArrayList<>();
            if(listCanChange.size()>0) {
                listCanChangePlat = listCanChange.stream().map(item -> {
                    return item.getdItemvalue();
                }).collect(Collectors.toList());
                listCanChangePlat.add(oldActivity.getPlatform());
                //可选的平台代码
                par.putKeyV("listCanChangePlat", listCanChangePlat);
            //如果没有配置跨平台信息，就采用历史活动的平台
            }else{
                listCanChangePlat.add(oldActivity.getPlatform());
                par.putKeyV("listCanChangePlat", listCanChangePlat);
            }
            //活动代码不等于老的活动代码
            par.putKeyV("notEqActcode", oldActivity.getActCode())
                    //变更的活动的厂商要等于原有老活动的厂商
                    .putKeyV("vender", oldActivity.getVender())
                    //变更的活动的机型要等于原有老活动的机型
                    .putKeyV("proModel", oldActivity.getProModel());
        //如果没有历史订单，根据商品来查询活动
        }else{
            //商品
            //OProduct productObj = oProductMapper.selectByPrimaryKey(product);
            productIdList.add(product);
            par.putKeyV("productIdList", productIdList);
        }
        //业务id
        if (StringUtils.isNotBlank(orderAgentBusifo) && !"null".equals(orderAgentBusifo)) {
            //查询平台活动
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(orderAgentBusifo);
            par.putKeyV("platform", agentBusInfo.getBusPlatform());
        }
        List<Map<String, Object>> actList = activityMapper.productActivityOrderBuild(par);
        List<OActivity> activitys = new ArrayList<OActivity>();
        for (Map<String, Object> stringObjectMap : actList) {
            OActivity oActivity = new OActivity();
            oActivity.setId(stringObjectMap.get("ID") + "");
            oActivity.setActivityName(stringObjectMap.get("ACTIVITYNAME") + "");
            oActivity.setPrice(new BigDecimal(stringObjectMap.get("PRICE") + ""));
            oActivity.setActCode(stringObjectMap.get("ACT_CODE") + "");
            oActivity.setOriginalPrice(new BigDecimal(stringObjectMap.get("ORIGINALPRICE") + ""));
            oActivity.setProductName(stringObjectMap.get("PRO_NAME")+"");
            String posSpePrice = String.valueOf(stringObjectMap.get("POS_SPE_PRICE"));
            if(StringUtils.isNotBlank(posSpePrice) && !posSpePrice.equals("null") && RegexUtil.checkNum(posSpePrice)){
                oActivity.setPosSpePrice(new BigDecimal(posSpePrice));
            }
            String standTime = String.valueOf(stringObjectMap.get("STAND_TIME"));
            if(StringUtils.isNotBlank(standTime) && !standTime.equals("null") && RegexUtil.checkNum(standTime)){
                oActivity.setStandTime(new BigDecimal(standTime));
            }
            //查询活动是否可见
            OActivity activity = activityMapper.selectByPrimaryKey(oActivity.getId());
            if(StringUtils.isBlank(activity.getVisible())){
                continue;
            }
            if(activity.getVisible().equals(VisibleStatus.TWO.getValue())){
                List<String> agentIdList = activityVisibleMapper.selectConfiguredReturnAgentId(oActivity.getActCode());
                if(!agentIdList.contains(angetId)){
                    continue;
                }
            }

            activitys.add(oActivity);
        }

        return activitys;
    }


    @Override
    public Map selectTermMachine(String platformNum) throws MessageException {
        if (StringUtils.isBlank(platformNum)) {
            throw new MessageException("平台类型为空");
        }
        HashMap<Object, Object> map = new HashMap<>();

        //查询平台
        PlatFormExample example = new PlatFormExample();
        example.or().andPlatformNumEqualTo(platformNum).andStatusEqualTo(Status.STATUS_1.status).andPlatformStatusEqualTo(Status.STATUS_1.status);
        List<PlatForm>  platFormList = platFormMapper.selectByExample(example);
        if(platFormList.size()==0){
            return map;
        }
        PlatForm  platForm  = platFormList.get(0);
        String platFormType = platForm.getPlatformType();
        List termMachineVos = null;
        List mposTermBatchVos = null;
        List mposTermTypeVos = null;

        //查询参数
        Map<String,String> par = new HashMap<>();
        //num 品牌编号 实时使用了
        par.put("busplatform",platForm.getBusplatform());
        //type 品牌类型 瑞嘉中使用
        par.put("busPlatForm",platFormType);
        if (StringUtils.isNotBlank(platFormType)) {
            try {
                termMachineVos = termMachineService.queryTermMachine(PlatformType.getContentEnum(platFormType),par);

                mposTermBatchVos = termMachineService.queryMposTermBatch(PlatformType.getContentEnum(platFormType));

                mposTermTypeVos = termMachineService.queryMposTermType(PlatformType.getContentEnum(platFormType));
                map.put("termMachineList", termMachineVos);
                map.put("mposTermBatchList", mposTermBatchVos);
                map.put("mposTermTypeList", mposTermTypeVos);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }


    @Override
    public List<Map<String, String>> planChoiseProComAndModel(String productId, String orderId) {
        Date date = new Date();
        List<OActivity> OActivityList = activityMapper.planChoiseProComAndModel(
                FastMap.fastMap("productId", productId)
                        .putKeyV("orderId", orderId));
        List<Map<String, String>> resList = new ArrayList<>();
        for (OActivity oActivity : OActivityList) {
            Map<String, String> item = new HashMap<>();
            item.put("proCom", oActivity.getVender());
            item.put("proModel", oActivity.getProModel());
            item.put("standTime", oActivity.getStandTime() + "");
            item.put("standAmt", oActivity.getStandAmt() + "");
            item.put("backType", BackType.getContentByValue(oActivity.getBackType()) + "");
            item.put("busProName", oActivity.getBusProName() + "");
            item.put("id", oActivity.getId());
            Dict dict = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(), oActivity.getVender());
            if (dict != null) {
                item.put("proComName", dict.getdItemname());
            } else {
                item.put("proComName", "系统未配置该厂商信息");
            }
            resList.add(item);
        }
        return resList;
    }


    /**
     * 历史Sn查询各个平台
     * @param snStart
     * @param snEnd
     * @param count
     * @param proModel POS 手刷
     * @return
     * @throws MessageException
     */
    @Override
    public AgentResult querySnInfoFromBusSystem(String snStart, String snEnd, String count, String proModel) throws MessageException {

        FastMap res = FastMap.fastSuccessMap();
        Set<OActivity> actSet = new HashSet<>();
        //历史sn查询，分平台
        if (proModel.equals(PlatformType.RDBPOS.msg)) {
            try {
                AgentResult agentResult = termMachineService.querySnMsg(PlatformType.RDBPOS, snStart, snEnd);
                if (!agentResult.isOK()) {
                    throw new MessageException(agentResult.getMsg());
                }
                logger.info("根据SN查询业务系统返回:" + agentResult.getData());
                Map<String, Object> resMap = (Map<String, Object>) agentResult.getData();
                if(null == resMap.get("busProCode")) throw new MessageException("在瑞大宝平台未查到该活动！");
                if(null == resMap.get("busNum")) throw new MessageException("在瑞大宝平台未查到该代理商！");
                String busProCode = (String) resMap.get("busProCode");
                String busNum = (String) resMap.get("busNum");

                //活动校验
                OActivityExample oActivityExample = new OActivityExample();
                OActivityExample.Criteria activityCriteria = oActivityExample.createCriteria();
                activityCriteria.andStatusEqualTo(Status.STATUS_1.status);
                activityCriteria.andBusProCodeEqualTo(busProCode);
                List<OActivity> oActivities = activityMapper.selectByExample(oActivityExample);
                if (oActivities == null || oActivities.size() == 0) {
                    throw new MessageException("活动未找到，请配置相应活动！");
                }
                Set<BigDecimal> priceSet = new HashSet<>();
                OActivity rActivity = null;
                here:
                for (OActivity oActivity : oActivities) {
                    if (null != oActivity.getVisible()) {
                        if(oActivity.getVisible().equals(VisibleStatus.TWO.getValue())){
                            AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
                            AgentBusInfoExample.Criteria criteria = agentBusInfoExample.createCriteria();
                            criteria.andStatusEqualTo(Status.STATUS_1.status);
                            criteria.andCloReviewStatusEqualTo(AgStatus.Approved.getValue());
                            criteria.andBusNumEqualTo(busNum);
                            List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
                            if(agentBusInfos.size()==0){
                                continue;
                            }
                            if(agentBusInfos.size()!=1){
                                throw new MessageException("业务编号不唯一");
                            }
                            AgentBusInfo agentBusInfo = agentBusInfos.get(0);

                            OActivityVisibleExample oActivityVisibleExample = new OActivityVisibleExample();
                            OActivityVisibleExample.Criteria visibleCriteria = oActivityVisibleExample.createCriteria();
                            visibleCriteria.andActivityIdEqualTo(oActivity.getActCode());
                            List<OActivityVisible> oActivityVisibles = activityVisibleMapper.selectByExample(oActivityVisibleExample);
                            for (OActivityVisible oActivityVisible : oActivityVisibles) {
                                if(oActivityVisible.getAgentId().equals(agentBusInfo.getAgentId())){
                                    rActivity = oActivity;
                                    break here;
                                }
                            }
                        }else{
                            priceSet.add(oActivity.getPrice());
                        }
                    }
                }
                if (priceSet.size() != 1) {
                    throw new MessageException("价格配置错误");
                }
                if(rActivity==null) rActivity = oActivities.get(0);
                actSet.add(rActivity);
                //号段活动存储在redis中
                redisService.delete(snStart + "," + snEnd + "_act");
                for (OActivity activity : actSet) {
                    redisService.lpushList(snStart + "," + snEnd + "_act", activity.getId());
                }
                //放入代理商信息
                redisService.set(snStart + "," + snEnd + "_org", busNum);
                redisService.set(snStart + "," + snEnd + "_plat", PlatformType.RDBPOS.code);
                OActivity oActivity = actSet.iterator().next();
                res.putKeyV("snStart", snStart)
                        .putKeyV("snEnd", snEnd)
                        .putKeyV("count", count)
                        .putKeyV("price", oActivity.getPrice())
                        .putKeyV("amt", oActivity.getPrice().multiply(new BigDecimal(count)))
                        .putKeyV("activity", actSet)
                        .putKeyV("modelType", PlatformType.RDBPOS.code);
            } catch (Exception e) {
                e.printStackTrace();
                throw new MessageException("查询机具sn异常:" + e.getLocalizedMessage());
            }
        } else if (proModel.equals(PlatformType.MPOS.msg)) {
            try {
                AgentResult agentResult = termMachineService.querySnMsg(PlatformType.MPOS, snStart, snEnd);
                if (!agentResult.isOK()) {
                    throw new MessageException("查询手刷失败");
                }
                logger.info("根据sn查询业务系统返回:" + agentResult.getData());
                List<Map<String, Object>> data = (List<Map<String, Object>>) agentResult.getData();
                if (data.size() != Integer.parseInt(count)) {
                    logger.info("查询手刷根据SN号段查询机具信息数量：{},count:{}", data.size(), count);
                    throw new MessageException("sn数量有误");
                }
                String orgid = "";
                for (Map<String, Object> map : data) {
                    if (StringUtils.isBlank(orgid)) {
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
                    if (oActivities == null) {
                        throw new MessageException(sn + "活动未找到");
                    }
                    if (oActivities.size() == 0) {
                        throw new MessageException(sn + "活动未找到");
                    }
                    Set<BigDecimal> priceSet = new HashSet<>();
                    for (OActivity oActivity : oActivities) {
                        priceSet.add(oActivity.getPrice());
                    }
                    if (priceSet.size() != 1) {
                        throw new MessageException(sn + "价格配置错误");
                    }
                    actSet.add(oActivities.get(0));
                }
                if (actSet.size() != 1) {
                    throw new MessageException(snStart + "到" + snEnd + ":活动不一致,请分开上传");
                }
                for (Map<String, Object> map : data) {
                    String sn = String.valueOf(map.get("termId"));
                    Map<String, String> redisMap = new HashMap<>();
                    redisMap.put("sn", sn);
                    redisMap.put("termActiveId", String.valueOf(map.get("termActiveId")));
                    redisMap.put("termActiveName", String.valueOf(map.get("termActiveName")));
                    redisMap.put("termBatchId", String.valueOf(map.get("termBatchId")));
                    redisMap.put("termTypeId", String.valueOf(map.get("termTypeId")));
                    redisMap.put("termCheck", String.valueOf(map.get("termCheck")));
                    redisMap.put("agencyId", String.valueOf(map.get("agencyId")));
                    redisMap.put("agencyName", String.valueOf(map.get("agencyName")));
                    redisService.hSet(snStart + "," + snEnd, sn, JsonUtil.objectToJson(redisMap));
                }
                redisService.delete(snStart + "," + snEnd + "_act");
                for (OActivity activity : actSet) {
                    redisService.lpushList(snStart + "," + snEnd + "_act", activity.getId());
                }

                //放入代理商信息
                redisService.setValue(snStart + "," + snEnd + "_org", orgid, 60 * 60 * 24L);

                OActivity oActivity = actSet.iterator().next();
                res.putKeyV("snStart", snStart)
                        .putKeyV("snEnd", snEnd)
                        .putKeyV("count", count)
                        .putKeyV("price", oActivity.getPrice())
                        .putKeyV("amt", oActivity.getPrice().multiply(new BigDecimal(count)))
                        .putKeyV("activity", actSet)
                        .putKeyV("modelType", PlatformType.MPOS.code);
            } catch (Exception e) {
                e.printStackTrace();
                throw new MessageException("查询机具sn异常:" + e.getLocalizedMessage());
            }
        } else if (proModel.equals(PlatformType.POS.code) || proModel.equals(PlatformType.SSPOS.code)) {
            try {
                AgentResult agentResult = termMachineService.querySnMsg(PlatformType.POS, snStart, snEnd);
                if (!agentResult.isOK()) {
                    throw new MessageException("业务平台未获取到此SN相关信息！");
                }
                logger.info("根据sn查询业务系统返回:" + agentResult.getMsg());
                JSONObject jsonObject = JSONObject.parseObject(agentResult.getMsg());
                JSONObject data = JSONObject.parseObject(String.valueOf(jsonObject.get("data")));
                logger.info("POS系统返回活动数据={}",data.get("termMachineList"));
                List<Map<String, Object>> termMachineListMap = (List<Map<String, Object>>) JSONArray.parse(String.valueOf(data.get("termMachineList")));
                if (termMachineListMap.size() != Integer.parseInt(count)) {
                    logger.info("查询pos根据SN号段查询机具信息数量：{},count:{}", termMachineListMap.size(), count);
                    throw new MessageException("sn数量有误");
                }
                String orgid = "";
                for (Map<String, Object> map : termMachineListMap) {
                    if (StringUtils.isBlank(orgid)) {
                        orgid = String.valueOf(map.get("orgId"));
                    }
                    String posSn = String.valueOf(map.get("posSn"));
                    String tmsModel = String.valueOf(map.get("tmsModel"));
                    String machineManufName = String.valueOf(map.get("machineManufName"));
                    String machineId = String.valueOf(map.get("machineId"));
                    String posType = String.valueOf(map.get("posType"));
                    Dict manufaName = dictOptionsService.findDictByName(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(), machineManufName);
                    if (manufaName == null) {
                        throw new MessageException(machineManufName + "厂商不存在");
                    }
                    String manufaValue = manufaName.getdItemvalue();//厂商
                    OActivityExample oActivityExample = new OActivityExample();
                    OActivityExample.Criteria activityCriteria = oActivityExample.createCriteria();
                    activityCriteria.andStatusEqualTo(Status.STATUS_1.status);
                    activityCriteria.andPosTypeEqualTo(posType);
                    if (null != map.get("posActivityId")) {
                        activityCriteria.andBusProCodeEqualTo(String.valueOf(map.get("posActivityId")));
                    } else {
                        activityCriteria.andBusProCodeEqualTo(machineId);
                    }

                    List<OActivity> oActivities = activityMapper.selectByExample(oActivityExample);
                    if (oActivities == null) {
                        throw new MessageException(posSn + "活动未找到");
                    }
                    if (oActivities.size() == 0) {
                        throw new MessageException(posSn + "活动未找到");
                    }
                    //POS系统，当有部分可见的时候取部分可见的价格，标志
                    boolean actVisible = true;
                    for (OActivity oActivity : oActivities) {
                        if(oActivity.getVisible().equals(VisibleStatus.TWO.getValue())){
                            AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
                            AgentBusInfoExample.Criteria criteria = agentBusInfoExample.createCriteria();
                            criteria.andStatusEqualTo(Status.STATUS_1.status);
                            criteria.andCloReviewStatusEqualTo(AgStatus.Approved.getValue());
                            criteria.andBusNumEqualTo(orgid);
                            List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
                            if(agentBusInfos.size()==0){
                                continue;
                            }
                            if(agentBusInfos.size()!=1){
                                throw new MessageException("业务编号不唯一");
                            }
                            AgentBusInfo agentBusInfo = agentBusInfos.get(0);
                            OActivityVisibleExample oActivityVisibleExample = new OActivityVisibleExample();
                            OActivityVisibleExample.Criteria visibleCriteria = oActivityVisibleExample.createCriteria();
                            visibleCriteria.andActivityIdEqualTo(oActivity.getActCode());
                            List<OActivityVisible> oActivityVisibles = activityVisibleMapper.selectByExample(oActivityVisibleExample);
                            for (OActivityVisible oActivityVisible : oActivityVisibles) {
                                if(oActivityVisible.getAgentId().equals(agentBusInfo.getAgentId())){
                                    actVisible = false;
                                    break;
                                }
                            }
                        }
                    }
                    Set<BigDecimal> priceSet = new HashSet<>();
                    OActivity rActivity = null;
                    here:
                    for (OActivity oActivity : oActivities) {
                        if(oActivity.getVisible().equals(VisibleStatus.TWO.getValue())){
                            AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
                            AgentBusInfoExample.Criteria criteria = agentBusInfoExample.createCriteria();
                            criteria.andStatusEqualTo(Status.STATUS_1.status);
                            criteria.andCloReviewStatusEqualTo(AgStatus.Approved.getValue());
                            criteria.andBusNumEqualTo(orgid);
                            List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
                            if(agentBusInfos.size()==0){
                                continue;
                            }
                            if(agentBusInfos.size()!=1){
                                throw new MessageException("业务编号不唯一");
                            }
                            AgentBusInfo agentBusInfo = agentBusInfos.get(0);

                            OActivityVisibleExample oActivityVisibleExample = new OActivityVisibleExample();
                            OActivityVisibleExample.Criteria visibleCriteria = oActivityVisibleExample.createCriteria();
                            visibleCriteria.andActivityIdEqualTo(oActivity.getActCode());
                            List<OActivityVisible> oActivityVisibles = activityVisibleMapper.selectByExample(oActivityVisibleExample);
                            for (OActivityVisible oActivityVisible : oActivityVisibles) {
                                if(oActivityVisible.getAgentId().equals(agentBusInfo.getAgentId())){
                                    rActivity = oActivity;
                                    priceSet.add(oActivity.getPrice());
                                    break here;
                                }
                            }
                        } else if (actVisible) {
                            priceSet.add(oActivity.getPrice());
                        }
                    }
                    if (priceSet.size() != 1) {
                        throw new MessageException(posSn + "价格配置错误");
                    }
                    if(rActivity==null){
                        rActivity = oActivities.get(0);
                    }
                    actSet.add(rActivity);
                }
                if (actSet.size() != 1) {
                    throw new MessageException(snStart + "到" + snEnd + ":活动不一致,请分开上传");
                }
                for (Map<String, Object> map : termMachineListMap) {
                    Map<String, String> redisMap = new HashMap<>();
                    String posSn = String.valueOf(map.get("posSn"));
                    redisMap.put("posSn", posSn);
                    redisMap.put("tmsModel", String.valueOf(map.get("tmsModel")));
                    String machineManufName = String.valueOf(map.get("machineManufName"));
                    redisMap.put("machineManufName", machineManufName);
                    redisMap.put("machineId", String.valueOf(map.get("machineId")));
                    redisMap.put("posType", String.valueOf(map.get("posType")));
                    Dict manufaName = dictOptionsService.findDictByName(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(), machineManufName);
                    if (manufaName == null) {
                        throw new MessageException(machineManufName + "厂商不存在");
                    }
                    String manufaValue = manufaName.getdItemvalue();//厂商
                    redisMap.put("manufaValue", manufaValue);
                    redisService.hSet(snStart + "," + snEnd, posSn, JsonUtil.objectToJson(redisMap));
                }
                //号段活动存储在redis中
                redisService.delete(snStart + "," + snEnd + "_act");
                for (OActivity activity : actSet) {
                    redisService.lpushList(snStart + "," + snEnd + "_act", activity.getId());
                }
                //放入代理商信息
                redisService.setValue(snStart + "," + snEnd + "_org", orgid, 60 * 60 * 24L);
                OActivity oActivity = actSet.iterator().next();
                res.putKeyV("snStart", snStart)
                        .putKeyV("snEnd", snEnd)
                        .putKeyV("count", count)
                        .putKeyV("price", oActivity.getPrice())
                        .putKeyV("amt", oActivity.getPrice().multiply(new BigDecimal(count)))
                        .putKeyV("activity", actSet)
                        .putKeyV("modelType", PlatformType.POS.code);
            } catch (Exception e) {
                e.printStackTrace();
                throw new MessageException(e.getMessage());
            }
        } else {
            throw new MessageException(proModel + "平台暂不支持退货或换活动！");
        }
        AgentResult agentResult = AgentResult.ok();
        agentResult.setMapData(res);
        return agentResult;
    }

    /**
     * 设置活动可见权限
     *
     * @param activityId
     * @param visible
     * @param agentIds
     * @param userId
     * @throws MessageException
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public void saveActivityVisible(String activityId, String visible, String[] agentIds, String userId) throws MessageException {

        if (visible.equals(VisibleStatus.ONT.getValue()) || visible.equals(VisibleStatus.TWO.getValue())) {
            OActivityVisibleExample oActivityVisibleExample = new OActivityVisibleExample();
            OActivityVisibleExample.Criteria criteria = oActivityVisibleExample.createCriteria();
            criteria.andActivityIdEqualTo(activityId);
            activityVisibleMapper.deleteByExample(oActivityVisibleExample);
            OActivity oActivity = new OActivity();
            oActivity.setActCode(activityId);
            oActivity.setVisible(visible);
            oActivity.setuTime(new Date());
            oActivity.setuUser(userId);
            int i = activityMapper.updateByActCode(oActivity);
            if (i == 0) {
                throw new MessageException("设置失败");
            }
            if (visible.equals(VisibleStatus.TWO.getValue())) {
                if(agentIds==null){
                    throw new MessageException("部分可见必须选择代理商");
                }
                if(agentIds.length==0){
                    throw new MessageException("部分可见必须选择代理商");
                }
                Set<String> agentSet = new HashSet<>();
                for (int j = 0; j < agentIds.length; j++) {
                    OActivityVisible oActivityVisible = new OActivityVisible();
                    oActivityVisible.setActivityId(activityId);
                    oActivityVisible.setAgentId(agentIds[j]);
                    oActivityVisible.setcTime(new Date());
                    activityVisibleMapper.insert(oActivityVisible);
                    agentSet.add(agentIds[j]);
                }
                if(agentSet.size()!=agentIds.length){
                    throw new MessageException("请去除重复选择的代理商");
                }
            }
        } else {
            OActivityVisibleExample oActivityVisibleExample = new OActivityVisibleExample();
            OActivityVisibleExample.Criteria criteria = oActivityVisibleExample.createCriteria();
            criteria.andActivityIdEqualTo(activityId);
            activityVisibleMapper.deleteByExample(oActivityVisibleExample);
            OActivity oActivity = new OActivity();
            oActivity.setActCode(activityId);
            oActivity.setVisible("");
            oActivity.setuTime(new Date());
            oActivity.setuUser(userId);
            int i = activityMapper.updateByActCode(oActivity);
            if (i == 0) {
                throw new MessageException("设置失败");
            }
        }
    }

    /**
     * 查询已配置的代理商
     * @param activityId
     */
    @Override
    public List<Map<String,String>> selectConfigured(String activityId){
        return activityVisibleMapper.selectConfigured(activityId);
    }

    /**
     * RDB查询换活动的taskID(退补差价ID)
     * @param detailId
     * @return
     */
    @Override
    public Map<String, Object> queryTaskIdForChangeActive(String detailId) {
        return oRefundPriceDiffDetailMapper.selectById(detailId);
    }
}
