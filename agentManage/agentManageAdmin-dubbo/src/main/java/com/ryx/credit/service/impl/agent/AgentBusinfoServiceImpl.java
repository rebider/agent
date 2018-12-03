package com.ryx.credit.service.impl.agent;

import java.math.BigDecimal;
import java.util.*;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.dao.agent.AgentColinfoMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.AssProtoColMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentBusInfoVo;
import com.ryx.credit.service.agent.AgentAssProtocolService;
import com.ryx.credit.service.agent.AgentDataHistoryService;
import com.ryx.credit.service.agent.PlatFormService;
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
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
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
        			StringUtils.isEmpty(agentBusInfo.getBusPlatform()) ||
        			StringUtils.isEmpty(agentBusInfo.getBusType())
				){
                throw new ProcessException("业务数据不完整");
        	}
        	agentBusInfo.setId(idService.genId(TabId.a_agent_businfo));
        	agentBusInfo.setcTime(new Date());
        	agentBusInfo.setcUtime(agentBusInfo.getcTime());
        	agentBusInfo.setBusStatus(BusinessStatus.Enabled.status);
			Agent agent = agentMapper.selectByPrimaryKey(agentBusInfo.getAgentId());
			if(agent.getAgStatus().equals(AgStatus.Create.name())){
				agentBusInfo.setCloReviewStatus(AgStatus.Create.status);
			}else{
				agentBusInfo.setCloReviewStatus(AgStatus.Approving.status);
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

	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
	@Override
	public ResultVO updateAgentBusInfoVo(List<AgentBusInfoVo> busInfoVoList, Agent agent,String userId)throws Exception {
		try {
			if(agent==null)throw new ProcessException("代理商信息不能为空");

			for (AgentBusInfoVo agentBusInfoVo : busInfoVoList) {
				if (null!=agentBusInfoVo.getBusPlatform()){
					PlatformType platformType = platFormService.byPlatformCode(agentBusInfoVo.getBusPlatform());
					if (null!=platformType){
						if(platformType.code.equals(PlatformType.POS.code) || platformType.code.equals(PlatformType.ZPOS.code)){
							if (StringUtils.isNotBlank(agentBusInfoVo.getBusNum())){
								if (StringUtils.isBlank(agentBusInfoVo.getBusLoginNum())){
									logger.info("请填写平台登录账号");
									throw new MessageException("请填写平台登录账号");
								}
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
					if(StringUtils.isNotEmpty(db_AgentBusInfo.getBusParent())){
						if(StringUtils.isNotEmpty(db_AgentBusInfo.getBusPlatform())){
							AgentBusInfo busInfoParent = agentBusInfoMapper.selectByPrimaryKey(db_AgentBusInfo.getBusParent());
							if(!busInfoParent.getBusPlatform().equals(db_AgentBusInfo.getBusPlatform())){
								throw new MessageException("代理商上级平台和本业务平台不匹配");
							}
						}
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
			return ResultVO.success(null);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<Map> agentBus(String agentId) {
		List<Map> data = agentBusInfoMapper.queryTreeByBusInfo(FastMap.fastMap("agentId",agentId));
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
	 * 业务编码不为空
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
		example.or().andBusNumEqualTo(busNum)
				.andBusPlatformEqualTo(platformCode)
				.andStatusEqualTo(Status.STATUS_1.status)
				.andBusStatusEqualTo(Status.STATUS_1.status);
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
		List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
		if(null==agentBusInfos){
			return null;
		}
		if(agentBusInfos.size()==1){
			return agentBusInfos.get(0);
		}
		return null;
	}
}

