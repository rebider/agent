package com.ryx.credit.service.impl.agent;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.RegexUtil;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.dao.order.OrganizationMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.Organization;
import com.ryx.credit.pojo.admin.vo.AgentBusInfoVo;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentAssProtocolService;
import com.ryx.credit.service.agent.AgentDataHistoryService;
import com.ryx.credit.service.agent.PlatFormService;
import com.ryx.credit.service.dict.DictOptionsService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.dict.IdService;

/**
 * 业务服务类
 * Created by cx on 2018/5/23.
 */
@Service("agentBusinfoService")
public class AgentBusinfoServiceImpl implements AgentBusinfoService {

	private static Logger logger = LoggerFactory.getLogger(AgentBusinfoServiceImpl.class);

    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
	@Autowired
    private AgentAssProtocolService agentAssProtocolService;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentColinfoMapper agentColinfoMapper;
	@Autowired
	private AgentDataHistoryService agentDataHistoryService;
	@Autowired
	private AssProtoColMapper assProtoColMapper;
	@Autowired
	private PlatFormService platFormService;
	@Autowired
	private AgentMapper agentMapper;
	@Autowired
	private PlatFormMapper platFormMapper;
	@Autowired
	private DictOptionsService dictOptionsService;
	@Autowired
	private AgentBusinfoService agentBusinfoService;
	@Autowired
	private OrganizationMapper organizationMapper;
	@Autowired
	private IUserService iUserService;


    /**
     * 代理商查询插件数据获取
     * @param par
     * @return
     */
    @Override
    public PageInfo agentBusInfoSelectViewList(Map par, PageInfo page){
        int count = agentBusInfoMapper.queryAgentBusListCount(par);
        par.put("page",page);
        List<Map<String,Object>> list = agentBusInfoMapper.queryAgentBusList(par);
        page.setTotal(count);
        page.setRows(list);
        return page;
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public AgentBusInfo agentBusInfoInsert(AgentBusInfo agentBusInfo) throws Exception{
    		if(agentBusInfo == null ||
        			StringUtils.isEmpty(agentBusInfo.getAgentId()) ||
        			StringUtils.isEmpty(agentBusInfo.getBusType())
				){
                throw new ProcessException(agentBusInfo.getAgentId()+"业务数据不完整");
        	}
        	agentBusInfo.setId(idService.genId(TabId.a_agent_businfo));
        	agentBusInfo.setcTime(new Date());
        	agentBusInfo.setcUtime(agentBusInfo.getcTime());
        	agentBusInfo.setBusStatus(BusinessStatus.Enabled.status);
			Agent agent = agentMapper.selectByPrimaryKey(agentBusInfo.getAgentId());
			if(null==agentBusInfo.getCloReviewStatus()){
				if(agent.getAgStatus().equals(AgStatus.Create.name())){
					agentBusInfo.setCloReviewStatus(AgStatus.Create.status);
				}else{
					agentBusInfo.setCloReviewStatus(AgStatus.Approving.status);
				}
			}
        	agentBusInfo.setStatus(Status.STATUS_1.status);
			agentBusInfo.setVersion(Status.STATUS_1.status);
			//激活返现如果无值默人填写自己
			if(StringUtils.isEmpty(agentBusInfo.getBusActivationParent())) {
				agentBusInfo.setBusActivationParent(agentBusInfo.getId());
			}
			if(StringUtils.isNotEmpty(agentBusInfo.getBusParent())){
				if(StringUtils.isNotEmpty(agentBusInfo.getBusPlatform())){
					AgentBusInfo busInfoParent = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusParent());
					if(!busInfoParent.getBusPlatform().equals(agentBusInfo.getBusPlatform())){
						throw new ProcessException("代理商上级平台和本业务平台不匹配");
					}
				}
			}
			Dict debitRateLower = dictOptionsService.findDictByName(DictGroup.AGENT.name(), agentBusInfo.getBusPlatform(), "debitRateLower");//借记费率下限（%）
			Dict debitCapping = dictOptionsService.findDictByName(DictGroup.AGENT.name(), agentBusInfo.getBusPlatform(), "debitCapping");//借记封顶额（元）
			Dict debitAppearRate = dictOptionsService.findDictByName(DictGroup.AGENT.name(), agentBusInfo.getBusPlatform(), "debitAppearRate");//借记出款费率（%）
			Dict creditRateFloor = dictOptionsService.findDictByValue(DictGroup.AGENT.name(), agentBusInfo.getBusPlatform(), "creditRateFloor");//贷记费率下限（%）
			Dict creditRateCeiling = dictOptionsService.findDictByValue(DictGroup.AGENT.name(), agentBusInfo.getBusPlatform(), "creditRateCeiling");//贷记费率上限（%）
			if(debitRateLower!=null){
				agentBusInfo.setDebitRateLower(debitRateLower.getdItemvalue());
			}
			if(debitCapping!=null){
				agentBusInfo.setDebitCapping(debitCapping.getdItemvalue());
			}
			if(debitAppearRate!=null){
				agentBusInfo.setDebitAppearRate(debitAppearRate.getdItemvalue());
			}
			if (creditRateFloor != null) {
				agentBusInfo.setCreditRateFloor(creditRateFloor.getdItemname());
			}
			if (creditRateCeiling != null) {
				agentBusInfo.setCreditRateCeiling(creditRateCeiling.getdItemname());
			}

			if(1!=agentBusInfoMapper.insert(agentBusInfo)){
        		throw new ProcessException("业务添加失败");
			}
			//记录历史业务平台
			if(!agentDataHistoryService.saveDataHistory(agentBusInfo, DataHistoryType.BUSINESS.getValue()).isOK()){
				throw new ProcessException("业务添加失败,历史保存失败");
			}
            return agentBusInfo;


    }


	@Override
	public List<AgentBusInfo> agentBusInfoList(String agentId) {
		AgentBusInfoExample example = new AgentBusInfoExample();
		example.or().andAgentIdEqualTo(agentId).andStatusEqualTo(Status.STATUS_1.status);
		return agentBusInfoMapper.selectByExample(example);
	}

	public List<AgentBusInfo> agentBusInfoList(String agentId, String id, BigDecimal appStatus) {
		AgentBusInfoExample example = new AgentBusInfoExample();
		AgentBusInfoExample.Criteria c = example.or().andStatusEqualTo(Status.STATUS_1.status);
		if(StringUtils.isNotEmpty(agentId))c.andAgentIdEqualTo(agentId);
		if(StringUtils.isNotEmpty(id))c.andIdEqualTo(id);
		if(null!=appStatus)c.andCloReviewStatusEqualTo(appStatus);
		return agentBusInfoMapper.selectByExample(example);
	}


	public int updateAgentBusInfo(AgentBusInfo agentBusInfo){
    	return agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
	}

	public AgentBusInfo getById(String id){
		AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(id);
		PlatForm platForm = platFormService.selectByPlatformNum(agentBusInfo.getBusPlatform());
		if(null!=platForm){
			agentBusInfo.setBusPlatformType(platForm.getPlatformType());
		}
		Map<String,Object> parentInfo = agentBusInfoMapper.queryBusInfoParent(FastMap.fastMap("id",agentBusInfo.getId()));
		agentBusInfo.setParentInfo(parentInfo);
		if(agentBusInfo!=null)
		//查询业务关联账户
		agentBusInfo.setAgentColinfoList(agentColinfoMapper.queryBusConinfoList(agentBusInfo.getId()));
		return agentBusInfo;
	}

	@Override
	public AgentBusInfo getByBusidAndCode(String platformCode, String busid) {
		AgentBusInfoExample example = new AgentBusInfoExample();
		example.or().andStatusEqualTo(Status.STATUS_1.status).andBusStatusEqualTo(Status.STATUS_1.status).andBusNumEqualTo(busid).andBusPlatformEqualTo(platformCode);
		List<AgentBusInfo>  res = agentBusInfoMapper.selectByExample(example);
		if(res.size()>0){
			return res.get(0);
		}
		return null;
	}

	/**
	 * 业务修改
	 * @param busInfoVoList
	 * @param agent
	 * @param userId
	 * @param isPass  是否审批通过  审批通过传true ,未提交审批修改传 false
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
	@Override
	public ResultVO updateAgentBusInfoVo(List<AgentBusInfoVo> busInfoVoList, Agent agent,String userId,Boolean isPass)throws Exception {
		try {
			if(agent==null)throw new ProcessException("代理商信息不能为空");
			Set<String> resultSet = new HashSet<>();
			for (AgentBusInfoVo agentBusInfoVo : busInfoVoList) {
				if(agentBusInfoVo.getBusType().equals(BusType.ZQZF.key) || agentBusInfoVo.getBusType().equals(BusType.ZQBZF.key) || agentBusInfoVo.getBusType().equals(BusType.ZQ.key) ){
					if(com.ryx.credit.commons.utils.StringUtils.isBlank(agentBusInfoVo.getBusParent()))
						throw new ProcessException("直签上级不能为空");
				}

				PlatForm platForm = platFormMapper.selectByPlatFormNum(agentBusInfoVo.getBusPlatform());
				resultSet.add(platForm.getPlatformType());
				List<Organization> organList = null;
				if (null!=agentBusInfoVo.getBusPlatform()){
					PlatformType platformType = platFormService.byPlatformCode(agentBusInfoVo.getBusPlatform());
					if (null!=platformType){
						if(PlatformType.whetherPOS(platformType.code)){
							if (StringUtils.isNotBlank(agentBusInfoVo.getBusNum())){
								if (StringUtils.isBlank(agentBusInfoVo.getBusLoginNum())){
									logger.info("请填写平台登录账号");
									throw new MessageException("请填写平台登录账号");
								}
							}
						}
						if(PlatformType.RDBPOS.code.equals(platformType.code)){
							if (StringUtils.isEmpty(agentBusInfoVo.getBusLoginNum())){
								logger.info("请填写平台登录账号");
								throw new MessageException("请填写平台登录账号");
							}
							if(!RegexUtil.checkInt(agentBusInfoVo.getBusLoginNum())){
								throw new ProcessException("平台登录账号必须是数字");
							}
						}
						if(PlatformType.RHPOS.code.equals(platformType.code)){
							if (StringUtils.isEmpty(agentBusInfoVo.getBusLoginNum())){
								logger.info("请填写平台登录账号");
								throw new MessageException("请填写平台登录账号");
							}
							if(!RegexUtil.checkInt(agentBusInfoVo.getBusLoginNum())){
								throw new ProcessException("平台登录账号必须是数字");
							}
						}
						//判断所选机构是否属于所选平台（机构编号&业务平台）
						if (StringUtils.isNotBlank(agentBusInfoVo.getOrganNum())) {
							organList = organizationMapper.selectOrganization(agentBusInfoVo.getOrganNum());
							for (Organization organization : organList) {
								if (!organization.getPlatId().contains(agentBusInfoVo.getBusPlatform())) {
									throw new ProcessException("所选机构不属于该业务平台");
								}
								agentBusInfoVo.setOrganNum(organization.getOrgId());
							}
						}
					}
				}
				agentBusInfoVo.setcUser(agent.getcUser());
				agentBusInfoVo.setAgentId(agent.getId());
				if(StringUtils.isEmpty(agentBusInfoVo.getId())) {
					//直接新曾
					AgentBusInfo db_AgentBusInfo = agentBusInfoInsert(agentBusInfoVo);
					if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(agentBusInfoVo.getAgentAssProtocol())){
						AssProtoColRel rel = new AssProtoColRel();
						rel.setAgentBusinfoId(db_AgentBusInfo.getId());
						rel.setAssProtocolId(agentBusInfoVo.getAgentAssProtocol());
						AssProtoCol assProtoCol = assProtoColMapper.selectByPrimaryKey(agentBusInfoVo.getAgentAssProtocol());
						if(StringUtils.isNotBlank(agentBusInfoVo.getProtocolRuleValue())){
							String ruleReplace = assProtoCol.getProtocolRule().replace("{}", agentBusInfoVo.getProtocolRuleValue());
							rel.setProtocolRule(ruleReplace);
						}else{
							rel.setProtocolRule(assProtoCol.getProtocolRule());
						}
						rel.setProtocolRuleValue(agentBusInfoVo.getProtocolRuleValue());
						if(1!=agentAssProtocolService.addProtocolRel(rel,agent.getcUser())){
							throw new MessageException("业务分管协议添加失败");
						}
					}
					logger.info("代理商业务添加:{}{}","添加代理商合同成功",agentBusInfoVo.getId());
				}else{
					AgentBusInfo db_AgentBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfoVo.getId());
					db_AgentBusInfo.setAgentId(agentBusInfoVo.getAgentId());
					db_AgentBusInfo.setBusNum(agentBusInfoVo.getBusNum());
					db_AgentBusInfo.setBusPlatform(agentBusInfoVo.getBusPlatform());
					db_AgentBusInfo.setBusType(agentBusInfoVo.getBusType());
					db_AgentBusInfo.setBusParent(agentBusInfoVo.getBusParent());
					db_AgentBusInfo.setBusRiskParent(agentBusInfoVo.getBusRiskParent());
					db_AgentBusInfo.setBusActivationParent(agentBusInfoVo.getBusActivationParent());
					db_AgentBusInfo.setBusRegion(agentBusInfoVo.getBusRegion());
					db_AgentBusInfo.setBusSentDirectly(agentBusInfoVo.getBusSentDirectly());
					db_AgentBusInfo.setBusDirectCashback(agentBusInfoVo.getBusDirectCashback());
					db_AgentBusInfo.setBusIndeAss(agentBusInfoVo.getBusIndeAss());
					db_AgentBusInfo.setBusContact(agentBusInfoVo.getBusContact());
					db_AgentBusInfo.setBusContactMobile(agentBusInfoVo.getBusContactMobile());
					db_AgentBusInfo.setBusContactEmail(agentBusInfoVo.getBusContactEmail());
					db_AgentBusInfo.setBusContactPerson(agentBusInfoVo.getBusContactPerson());
					db_AgentBusInfo.setBusRiskEmail(agentBusInfoVo.getBusRiskEmail());
					db_AgentBusInfo.setCloTaxPoint(agentBusInfoVo.getCloTaxPoint());
					db_AgentBusInfo.setCloInvoice(agentBusInfoVo.getCloInvoice());
					db_AgentBusInfo.setCloReceipt(agentBusInfoVo.getCloReceipt());
					db_AgentBusInfo.setCloPayCompany(agentBusInfoVo.getCloPayCompany());
					db_AgentBusInfo.setCloAgentColinfo(agentBusInfoVo.getCloAgentColinfo());
					db_AgentBusInfo.setAgZbh(agentBusInfoVo.getAgZbh());
					db_AgentBusInfo.setBusStatus(agentBusInfoVo.getBusStatus());
					db_AgentBusInfo.setStatus(agentBusInfoVo.getStatus());
					db_AgentBusInfo.setBusUseOrgan(agentBusInfoVo.getBusUseOrgan());
					db_AgentBusInfo.setBusScope(agentBusInfoVo.getBusScope());
					db_AgentBusInfo.setDredgeS0(agentBusInfoVo.getDredgeS0());
					db_AgentBusInfo.setBusLoginNum(agentBusInfoVo.getBusLoginNum());
					db_AgentBusInfo.setAgDocDistrict(agentBusInfoVo.getAgDocDistrict());
					db_AgentBusInfo.setAgDocPro(agentBusInfoVo.getAgDocPro());
					db_AgentBusInfo.setOrganNum(agentBusInfoVo.getOrganNum());
					if(StringUtils.isNotEmpty(db_AgentBusInfo.getBusParent())){
						if(StringUtils.isNotEmpty(db_AgentBusInfo.getBusPlatform())){
							AgentBusInfo busInfoParent = agentBusInfoMapper.selectByPrimaryKey(db_AgentBusInfo.getBusParent());
							if(!busInfoParent.getBusPlatform().equals(db_AgentBusInfo.getBusPlatform())){
								throw new MessageException("代理商上级平台和本业务平台不匹配");
							}
						}
					}
					Dict debitRateLower = dictOptionsService.findDictByName(DictGroup.AGENT.name(), db_AgentBusInfo.getBusPlatform(), "debitRateLower");//借记费率下限（%）
					Dict debitCapping = dictOptionsService.findDictByName(DictGroup.AGENT.name(), db_AgentBusInfo.getBusPlatform(), "debitCapping");//借记封顶额（元）
					Dict debitAppearRate = dictOptionsService.findDictByName(DictGroup.AGENT.name(), db_AgentBusInfo.getBusPlatform(), "debitAppearRate");//借记出款费率（%）
					Dict creditRateFloor = dictOptionsService.findDictByValue(DictGroup.AGENT.name(), db_AgentBusInfo.getBusPlatform(), "creditRateFloor");//贷记费率下限（%）
					Dict creditRateCeiling = dictOptionsService.findDictByValue(DictGroup.AGENT.name(), db_AgentBusInfo.getBusPlatform(), "creditRateCeiling");//贷记费率上限（%）
					if(debitRateLower!=null){
						db_AgentBusInfo.setDebitRateLower(debitRateLower.getdItemvalue());
					}
					if(debitCapping!=null){
						db_AgentBusInfo.setDebitCapping(debitCapping.getdItemvalue());
					}
					if(debitAppearRate!=null){
						db_AgentBusInfo.setDebitAppearRate(debitAppearRate.getdItemvalue());
					}
					if (creditRateFloor != null) {
						db_AgentBusInfo.setCreditRateFloor(creditRateFloor.getdItemname());
					}
					if (creditRateCeiling != null) {
						db_AgentBusInfo.setCreditRateCeiling(creditRateCeiling.getdItemname());
					}

					if(1!=agentBusInfoMapper.updateByPrimaryKeySelective(db_AgentBusInfo)){
						throw new MessageException("更新业务信息失败");
					}else{
						agentDataHistoryService.saveDataHistory(db_AgentBusInfo,db_AgentBusInfo.getId(), DataHistoryType.BUSINESS.getValue(),userId,db_AgentBusInfo.getVersion());
					}

					//是否已经存在
					boolean is_in = false;
                    //更新分管协议
					if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(agentBusInfoVo.getAgentAssProtocol())){
						List<AssProtoCol> assProtoCol_list = agentAssProtocolService.queryProtoColByBusId(db_AgentBusInfo.getId());
						for (AssProtoCol assProtoCol : assProtoCol_list) {
							if(assProtoCol.getId().equals(agentBusInfoVo.getAgentAssProtocol())){
								is_in = true;
							    break;
							}
						}
						//如果存在就处理下一个业务
						if(is_in)continue;

						List<AssProtoColRel>  rels =agentAssProtocolService.queryProtoColByBusIds(Arrays.asList(db_AgentBusInfo.getId()));
						for (AssProtoColRel rel : rels) {
							rel.setStatus(Status.STATUS_0.status);
							if(1!=agentAssProtocolService.updateAssProtoColRel(rel)){
								throw new MessageException("业务分管协议更新失败");
							}
						}

						AssProtoColRel rel = new AssProtoColRel();
						rel.setAgentBusinfoId(db_AgentBusInfo.getId());
						rel.setAssProtocolId(agentBusInfoVo.getAgentAssProtocol());
						AssProtoCol assProtoCol = assProtoColMapper.selectByPrimaryKey(agentBusInfoVo.getAgentAssProtocol());
						if(StringUtils.isNotBlank(agentBusInfoVo.getProtocolRuleValue())){
							String ruleReplace = assProtoCol.getProtocolRule().replace("{}", agentBusInfoVo.getProtocolRuleValue());
							rel.setProtocolRule(ruleReplace);
						}else{
							rel.setProtocolRule(assProtoCol.getProtocolRule());
						}
						rel.setProtocolRuleValue(agentBusInfoVo.getProtocolRuleValue());
						if(1!=agentAssProtocolService.addProtocolRel(rel,agent.getcUser())){
							throw new MessageException("业务分管协议添加失败");
						}
					//删除分管协议
					}else{
						List<AssProtoColRel>  rels =agentAssProtocolService.queryProtoColByBusIds(Arrays.asList(db_AgentBusInfo.getId()));
						for (AssProtoColRel rel : rels) {
							rel.setStatus(Status.STATUS_0.status);
							if(1!=agentAssProtocolService.updateAssProtoColRel(rel)){
								throw new MessageException("业务分管协议更新失败");
							}
						}

					}
				}

			}
			if(!isPass && resultSet.size()>1){
				throw new MessageException("不同类型平台不能同时提交");
			}
			return ResultVO.success(null);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<Map> agentBus(String agentId,Long userId) {
		List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
		if (orgCodeRes == null && orgCodeRes.size() != 1) {
			return null;
		}
		Map<String, Object> stringObjectMap = orgCodeRes.get(0);
		String organizationCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
		FastMap reqMap = FastMap.fastMap("agentId", agentId);
		if(organizationCode.contains("city"))
		reqMap.putKeyV("organizationCode", organizationCode);
		List<Map> data = agentBusInfoMapper.queryTreeByBusInfo(reqMap);
		for (Map datum : data) {
			datum.put("BUS_TYPE_NAME",BusType.getContentByValue(String.valueOf(datum.get("BUS_TYPE"))));
		}
		return data;
	}

	@Override
	public List<Map> agentBusChild(String platformCode, String angetId) {
		AgentBusInfoExample example = new AgentBusInfoExample();
		example.or().andAgentIdEqualTo(angetId).andStatusEqualTo(Status.STATUS_1.status).andBusPlatformEqualTo(platformCode);
		example.setOrderByClause(" C_TIME asc ");
		List<AgentBusInfo>  busInfos = agentBusInfoMapper.selectByExample(example);
		if(busInfos.size()==1){
			return agentBusChild(busInfos.get(0).getId());
		}
		return Arrays.asList();
	}

	@Override
	public List<Map> agentBusChild(String busId) {
		AgentBusInfo info = agentBusInfoMapper.selectByPrimaryKey(busId);
		if(null==info)return Arrays.asList();
		return agentBusInfoMapper.queryTreeByBusInfo(FastMap.fastMap("busParent",busId).putKeyV("busPlatform",info.getBusPlatform()));
	}




	@Override
	public Map getRootFromBusInfo(List<Map> list,String busId) {
		if(list==null)list=new ArrayList<Map>();

		List<Map>  map = agentBusInfoMapper.queryTreeByBusInfo(FastMap.fastMap("id",busId));
		if(map.size()>0){
			list.add(map.get(0));
		}
		if(map.size()>0 && map.get(0).get("BUS_PARENT")!=null &&  StringUtils.isNotEmpty(map.get(0).get("BUS_PARENT")+"")){
			return getRootFromBusInfo(list,map.get(0).get("BUS_PARENT")+"");
		}
        if(map.size()!=1){
			return null;
		}
		return map.get(0);
	}

	@Override
	public List<Map> getParentListFromBusInfo(List<Map> list, String busId) {
		if(list==null)list=new ArrayList<Map>();
		if(list.size()>=10){
			return list;
		}
		FastMap par = FastMap.fastMap("id", busId)
				.putKeyV("busStatus", 2);
		List<Map>  map = agentBusInfoMapper.queryTreeByBusInfo(par);
		if(map.size()>0){
			list.add(map.get(0));
		}
		if(map.size()>0 && map.get(0).get("BUS_PARENT")!=null &&  StringUtils.isNotEmpty(map.get(0).get("BUS_PARENT")+"")){
			return getParentListFromBusInfo(list,map.get(0).get("BUS_PARENT")+"");
		}
		if(map.size()!=1){
			return list;
		}
		return list;
	}

	/**
	 * 业务编码可为空
	 * @param list
	 * @param busId
	 * @return
	 */
	@Override
	public List<AgentBusInfo> queryParenLevel(List<AgentBusInfo> list, String busId) {
		if(list==null) {
			list = new ArrayList<AgentBusInfo>();
		}
		AgentBusInfo plat = agentBusInfoMapper.selectByPrimaryKey(busId);
		if(plat==null)
			return list;
		if(StringUtils.isNotEmpty(plat.getBusParent())) {
			AgentBusInfo parent = agentBusInfoMapper.selectByPrimaryKey(plat.getBusParent());
			if(parent!=null){
				if(parent.getBusStatus()!=null && parent.getBusStatus().equals(Status.STATUS_1.status)){
					list.add(parent);
					return queryParenLevel(list, parent.getId());
				}else{
					return list;
				}
			}else{
				return list;
			}
		}else{
			return list;
		}
	}

	/**
	 * 业务编码不为空
	 * @param list
	 * @param platformCode
	 * @param agentId
	 * @return
	 */
	@Override
	public List<AgentBusInfo> queryParenFourLevel(List<AgentBusInfo> list, String platformCode, String agentId) {
		if(list==null) {
			list = new ArrayList<AgentBusInfo>();
		}
		if(list.size()==4) {
			return list;
		}
		AgentBusInfoExample example = new AgentBusInfoExample();
		example.or().andAgentIdEqualTo(agentId)
                .andBusPlatformEqualTo(platformCode)
				.andBusStatusEqualTo(Status.STATUS_1.status)
				.andStatusEqualTo(Status.STATUS_1.status)
				.andBusNumIsNotNull();
		List<AgentBusInfo> plats = agentBusInfoMapper.selectByExample(example);
		if(plats.size()==0)
			return list;
		AgentBusInfo platInfo = plats.get(0);
		if(StringUtils.isNotEmpty(platInfo.getBusParent())) {
			AgentBusInfo parent = agentBusInfoMapper.selectByPrimaryKey(platInfo.getBusParent());
			if(parent!=null && StringUtils.isNotEmpty(parent.getBusNum())){
				if(parent.getBusStatus()!=null && parent.getBusStatus().equals(Status.STATUS_1.status)){
					list.add(parent);
					if (StringUtils.isNotEmpty(parent.getAgentId())) {
						return queryParenFourLevel(list, platformCode, parent.getAgentId());
					}else{
						return list;
					}
				}else{
					return list;
				}
			}else{
				return list;
			}
		}else{
			return list;
		}

	}


	/**
	 * 业务编码不为空
	 * @param list
	 * @param platformCode
	 * @param busNum
	 * @return
	 */
	@Override
	public List<AgentBusInfo> queryParenFourLevelBusNum(List<AgentBusInfo> list, String platformCode, String busNum) {
		if(list==null) {
			list = new ArrayList<AgentBusInfo>();
		}
		if(list.size()==4) {
			return list;
		}
		if(platformCode==null) {
			return  new ArrayList<AgentBusInfo>();
		}
		AgentBusInfoExample example = new AgentBusInfoExample();
		example.or().andBusNumEqualTo(busNum)
				.andBusPlatformEqualTo(platformCode)
				.andBusStatusEqualTo(Status.STATUS_1.status)
				.andStatusEqualTo(Status.STATUS_1.status);
		List<AgentBusInfo> plats = agentBusInfoMapper.selectByExample(example);
		if(plats.size()==0)
			return list;
		AgentBusInfo platInfo = plats.get(0);
		if(StringUtils.isNotEmpty(platInfo.getBusParent())) {
			AgentBusInfo parent = agentBusInfoMapper.selectByPrimaryKey(platInfo.getBusParent());
			if(parent!=null && StringUtils.isNotEmpty(parent.getBusNum())){
				if(parent.getBusStatus()!=null && parent.getBusStatus().equals(Status.STATUS_1.status)){
					list.add(parent);
					if (StringUtils.isNotEmpty(parent.getAgentId())) {
						return queryParenFourLevel(list, platformCode, parent.getAgentId());
					}else{
						return list;
					}
				}else{
					return list;
				}
			}else{
				return list;
			}
		}else{
			return list;
		}
	}

	/**
	 * 把给定的代理商指定的平台的下级节点全部放回 业务编码不为空
	 * @param list
	 * @param platformCode
	 * @param agentId
	 * @return
	 */
	@Override
	public List<AgentBusInfo> queryChildLevel(List<AgentBusInfo> list, String platformCode, String agentId) {
		if(list==null) {
			list = new ArrayList<AgentBusInfo>();
		}
		if(list.size()==4) {
			return list;
		}
		//当前代理商
		AgentBusInfoExample example = new AgentBusInfoExample();
		example.or().andAgentIdEqualTo(agentId)
				.andBusPlatformEqualTo(platformCode)
				.andStatusEqualTo(Status.STATUS_1.status)
				.andBusStatusEqualTo(Status.STATUS_1.status)
				.andBusNumIsNotNull();
		List<AgentBusInfo> plats = agentBusInfoMapper.selectByExample(example);

		if(plats.size()==0) {
			return list;
		}
		//查询下级代理商
		AgentBusInfo platInfo = plats.get(0);

		AgentBusInfoExample child_example = new AgentBusInfoExample();
		child_example.or().andBusParentEqualTo(platInfo.getId())
				.andBusPlatformEqualTo(platformCode)
				.andBusStatusEqualTo(Status.STATUS_1.status)
				.andStatusEqualTo(Status.STATUS_1.status)
				.andBusNumIsNotNull();
		List<AgentBusInfo> child_plat = agentBusInfoMapper.selectByExample(child_example);
        //没有下级别就返回
		if(child_plat.size()==0) {
			return list;
		}
		//有就添加
		list.addAll(child_plat);
		//把孩子节点也加到list
		for (AgentBusInfo agentBusInfo : child_plat) {
			queryChildLevel(list,agentBusInfo.getBusPlatform(),agentBusInfo.getAgentId());
		}

        return list;
	}


	/**
	 * 查询所有下级
	 * @param list
	 * @param platformCode
	 * @param busNum
	 * @return
	 */
	@Override
	public List<AgentBusInfo> queryChildLevelByBusNum(List<AgentBusInfo> list, String platformCode, String busNum) {
		if(list==null) {
			list = new ArrayList<AgentBusInfo>();
		}

		if(list.size()==100) {
			return list;
		}
		if(platformCode==null) {
			return  new ArrayList<AgentBusInfo>();
		}
		//当前代理商
		AgentBusInfoExample example = new AgentBusInfoExample();
		List<BigDecimal> busStatusList = new ArrayList<>();
		busStatusList.add(BusinessStatus.Enabled.status);
		busStatusList.add(BusinessStatus.inactive.status);
		example.or().andBusNumEqualTo(busNum)
				.andBusPlatformEqualTo(platformCode)
				.andStatusEqualTo(Status.STATUS_1.status)
				.andBusStatusIn(busStatusList);
		List<AgentBusInfo> plats = agentBusInfoMapper.selectByExample(example);
		if(plats.size()==0) {
			return list;
		}
		//查询下级代理商
		AgentBusInfo platInfo = plats.get(0);

		AgentBusInfoExample child_example = new AgentBusInfoExample();
		child_example.or().andBusParentEqualTo(platInfo.getId())
				.andBusPlatformEqualTo(platformCode)
				.andBusStatusIn(busStatusList)
				.andBusNumIsNotNull();
		List<AgentBusInfo> child_plat = agentBusInfoMapper.selectByExample(child_example);
		//没有下级别就返回
		if(child_plat.size()==0) {
			return list;
		}
		//有就添加
		list.addAll(child_plat);
		//把孩子节点也加到list
		for (AgentBusInfo agentBusInfo : child_plat) {
			queryChildLevelByBusNum(list,platformCode,agentBusInfo.getBusNum());
		}
		return list;
	}

	@Override
	public List<AgentBusInfo> selectByAgenId(String agentId) {
		return agentBusInfoMapper.selectByAgenId(agentId);
	}

	@Override
	public AgentBusInfo selectBusInfo(String busNum){
		AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
		AgentBusInfoExample.Criteria criteria = agentBusInfoExample.createCriteria();
		criteria.andBusNumEqualTo(busNum);
		criteria.andCloReviewStatusEqualTo(AgStatus.Approved.getValue());
		criteria.andBusStatusEqualTo(AgentInStatus.IN.status);
		criteria.andStatusEqualTo(Status.STATUS_1.status);
		List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
		if(null==agentBusInfos){
			return null;
		}
		if(agentBusInfos.size()==1){
			return agentBusInfos.get(0);
		}
		return null;
	}

	@Override
	public List<AgentBusInfo> selectExistsById(String id){
		AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
		AgentBusInfoExample.Criteria criteria = agentBusInfoExample.createCriteria();
		criteria.andStatusEqualTo(Status.STATUS_1.status);
		criteria.andBusStatusNotEqualTo(BusinessStatus.pause.status);
		criteria.andIdEqualTo(id);
		List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
		return agentBusInfos;
	}


	@Override
	public AgentResult completAllAgentBusInfoCompany() {
		String dg="Q000029564";
			//对公开发票的---
		List<Map<String,Object>> listMap=agentBusInfoMapper.selectDgKfp(dg);
		for (Map<String, Object> map : listMap) {
			String id =(String) map.get("ID");
			String CLO_PAY_COMPANY = (String)map.get("CLO_PAY_COMPANY");//打款公司
			AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(id);
			agentBusInfo.setCloPayCompany(dg);
			agentBusInfo.setcUtime(Calendar.getInstance().getTime());
			agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
		}
		//对公不开发票
		List<Map<String,Object>> bkfaMap=agentBusInfoMapper.selectDgBkfp();
		updateMap(bkfaMap);

		//对私
		List<Map<String,Object>> bsMap=agentBusInfoMapper.selectDs();
		updateMap(bsMap);
		return AgentResult.ok();
	}

	private void updateMap(List<Map<String,Object>> map){
		String ds="Q1";
	if (null!=map && map.size()>0){
		for (Map<String, Object> mapBkfp : map) {
			String agent_id =(String) mapBkfp.get("AGENT_ID");
			if (StringUtils.isNotBlank(agent_id)){
				AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
				AgentBusInfoExample.Criteria criteria = agentBusInfoExample.createCriteria().andAgentIdEqualTo(agent_id);
				List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
				if (null!=agentBusInfos && agentBusInfos.size()>0){
					for (AgentBusInfo agentBusInfo : agentBusInfos) {
						agentBusInfo.setCloPayCompany(ds);
						agentBusInfo.setcUtime(Calendar.getInstance().getTime());
						agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
					}
				}
			}
		}
	}
	}
	@Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRES_NEW)
	@Override
	public AgentResult completAgentBusInfoCompany(String agentId) throws Exception{

		//如果 公户收款，开票，瑞银信出款，缺一不可
		//如果公户，不开票，非瑞银信出款
		//如果私户，不可能开票，非瑞银信出款
		//检查收款账户是否是对公 开票 修复打款公司为瑞银信打款公司(Q000029564)
		AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
		agentBusInfoExample.or().andAgentIdEqualTo(agentId).andStatusEqualTo(Status.STATUS_1.status).andCloPayCompanyIsNotNull();
		agentBusInfoExample.setOrderByClause(" C_UTIME desc");
		List<AgentBusInfo> agentBusInfoList = agentBusInfoMapper.selectByExample(agentBusInfoExample);
		//分组统计
		Map<String,Long> group = agentBusInfoList.stream().collect(Collectors.groupingBy(AgentBusInfo::getCloPayCompany,Collectors.counting()));
		logger.info("分组结果：{}",group);
		Iterator<String> ite = group.keySet().iterator();
		Long a = 0L; String comp=null;
		//取出最多的打款公司
		while (ite.hasNext()){
			String cop = ite.next();
			if(group.get(cop)>a){a=group.get(cop);comp=cop;}else{}
		}

		final String c = comp;
		logger.info("分组结果：{}，最大值：{}",group,comp);
		if(StringUtils.isNotEmpty(c)) {
			//更新为company
		    agentBusInfoExample = new AgentBusInfoExample();
			agentBusInfoExample.or().andAgentIdEqualTo(agentId).andStatusEqualTo(Status.STATUS_1.status).andCloPayCompanyNotEqualTo(c);
			agentBusInfoList = agentBusInfoMapper.selectByExample(agentBusInfoExample);
			if(agentBusInfoList.size()>0) {
				agentBusInfoList.forEach(agentBusInfo -> {
					agentDataHistoryService.saveDataHistory(agentBusInfo,agentBusInfo.getId(),DataHistoryType.BUSINESS.code,"-1",agentBusInfo.getVersion());
					logger.info("分组结果：{}，最大值：{},更新{}",group,c,agentBusInfo.getId());
					agentBusInfo.setCloPayCompany(c);
					if (1 != agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo)) {
						logger.info("更新打开公司失败");
					}
				});
				return AgentResult.ok();
			}else{
				return AgentResult.ok();
			}
		}
		return AgentResult.fail();
	}

	@Override
	public Map selectComp(String busId) {
		AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(busId);
		if (null!=agentBusInfo){
			List<Map<String, Object>> maps = agentBusInfoMapper.selectComp(agentBusInfo.getAgentId());
			if (null!=maps && maps.size()>0){
				return  maps.get(0);
			}
		}
		return null;
	}


	public AgentBusInfo queryBusInfo(String busNum)throws MessageException{
		if(StringUtils.isBlank(busNum)){
			throw new MessageException("业务编号不能为空");
		}
		AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
		AgentBusInfoExample.Criteria criteria = agentBusInfoExample.createCriteria();
		criteria.andStatusEqualTo(Status.STATUS_1.status);
		criteria.andBusStatusNotEqualTo(BusinessStatus.pause.status);
		criteria.andBusNumEqualTo(busNum);
		List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
		if(agentBusInfos==null){
			throw new MessageException("业务不存在");
		}
		if(agentBusInfos.size()==0){
			throw new MessageException("业务不存在");
		}
		if(agentBusInfos.size()!=1){
			throw new MessageException("业务不唯一");
		}
		AgentBusInfo agentBusInfo = agentBusInfos.get(0);
		return agentBusInfo;
	}

	/**
	 * 为瑞大宝提供 — 根据平台编号查询agentId
	 * @param busNum
	 * @return
	 * @throws MessageException
	 */
	@Override
	public String queryAgentId(String busNum)throws MessageException{
		AgentBusInfo agentBusInfo = queryBusInfo(busNum);
		return agentBusInfo.getAgentId();
	}

	/**
	 * 为瑞大宝提供 — 根据平台编号修改登陆手机号
	 * @param oldBusLoginNum
	 * @param busLoginNum
	 * @throws MessageException
	 */
	@Override
	public void updateBusLoginNum(String oldBusLoginNum,String busLoginNum)throws MessageException{
		logger.info("根据平台编号修改登陆手机号请求参数:{},{}",oldBusLoginNum,busLoginNum);
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("oldBusLoginNum",oldBusLoginNum);
		reqMap.put("busLoginNum",busLoginNum);
		reqMap.put("platformType",PlatformType.RDBPOS.code);
		int i = agentBusInfoMapper.updateBusLoginNum(reqMap);
		logger.info("根据平台编号修改登陆手机号处理结果:{}",i);
		if(i==0){
			throw new MessageException("更新失败");
		}
	}

}

