package com.ryx.credit.service.impl.agent;

import java.math.BigDecimal;
import java.util.*;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.dao.agent.AgentColinfoMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentBusInfoVo;
import com.ryx.credit.service.agent.AgentAssProtocolService;
import com.ryx.credit.service.agent.AgentDataHistoryService;
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

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public AgentBusInfo agentBusInfoInsert(AgentBusInfo agentBusInfo) throws Exception{
    		if(agentBusInfo == null ||
        			StringUtils.isEmpty(agentBusInfo.getAgentId()) ||
        			StringUtils.isEmpty(agentBusInfo.getBusPlatform()) ||
        			StringUtils.isEmpty(agentBusInfo.getBusType())
//					||
//        			null == agentBusInfo.getBusSentDirectly() ||
//        			null == agentBusInfo.getBusDirectCashback() ||
//        			StringUtils.isEmpty(agentBusInfo.getBusContact()) ||
//        			StringUtils.isEmpty(agentBusInfo.getBusContactMobile()) ||
//        			StringUtils.isEmpty(agentBusInfo.getBusContactEmail()) ||
//        			StringUtils.isEmpty(agentBusInfo.getBusContactPerson()) ||
//        			StringUtils.isEmpty(agentBusInfo.getBusRiskEmail()) ||
//        			null == agentBusInfo.getCloReceipt() ||
//        			StringUtils.isEmpty(agentBusInfo.getcUser())
        			){
                throw new ProcessException("业务数据不完整");
        	}
        	agentBusInfo.setId(idService.genId(TabId.a_agent_businfo));
        	agentBusInfo.setcTime(new Date());
        	agentBusInfo.setcUtime(agentBusInfo.getcTime());
        	agentBusInfo.setBusStatus(BusinessStatus.Enabled.status);
        	agentBusInfo.setCloReviewStatus(AgStatus.Create.status);
        	agentBusInfo.setStatus(Status.STATUS_1.status);
			agentBusInfo.setVersion(Status.STATUS_1.status);

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
		//查询业务关联账户
		agentBusInfo.setAgentColinfoList(agentColinfoMapper.queryBusConinfoList(agentBusInfo.getId()));
		return agentBusInfo;
	}


	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
	@Override
	public ResultVO updateAgentBusInfoVo(List<AgentBusInfoVo> busInfoVoList, Agent agent)throws Exception {
		try {
			if(agent==null)throw new ProcessException("代理商信息不能为空");

			outer:
			for (AgentBusInfoVo agentBusInfoVo : busInfoVoList) {
				agentBusInfoVo.setcUser(agent.getcUser());
				agentBusInfoVo.setAgentId(agent.getId());
				if(StringUtils.isEmpty(agentBusInfoVo.getId())) {
					//直接新曾
					AgentBusInfo db_AgentBusInfo = agentBusInfoInsert(agentBusInfoVo);
					if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(agentBusInfoVo.getAgentAssProtocol())){
						AssProtoColRel rel = new AssProtoColRel();
						rel.setAgentBusinfoId(db_AgentBusInfo.getId());
						rel.setAssProtocolId(agentBusInfoVo.getAgentAssProtocol());
						if(1!=agentAssProtocolService.addProtocolRel(rel,agent.getcUser())){
							throw new ProcessException("业务分管协议添加失败");
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

					if(StringUtils.isNotEmpty(db_AgentBusInfo.getBusParent())){
						if(StringUtils.isNotEmpty(db_AgentBusInfo.getBusPlatform())){
							AgentBusInfo busInfoParent = agentBusInfoMapper.selectByPrimaryKey(db_AgentBusInfo.getBusParent());
							if(!busInfoParent.getBusPlatform().equals(db_AgentBusInfo.getBusPlatform())){
								throw new ProcessException("代理商上级平台和本业务平台不匹配");
							}
						}
					}

					if(1!=agentBusInfoMapper.updateByPrimaryKeySelective(db_AgentBusInfo)){
						throw new ProcessException("更新业务信息失败");
					}
                    //更新分管协议
					if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(agentBusInfoVo.getAgentAssProtocol())){
						List<AssProtoCol> assProtoCol_list = agentAssProtocolService.queryProtoColByBusId(db_AgentBusInfo.getId());
						for (AssProtoCol assProtoCol : assProtoCol_list) {
							if(assProtoCol.getId().equals(agentBusInfoVo.getAgentAssProtocol())){
							 break outer;
							}
						}


						List<AssProtoColRel>  rels =agentAssProtocolService.queryProtoColByBusIds(Arrays.asList(db_AgentBusInfo.getId()));
						for (AssProtoColRel rel : rels) {
							rel.setStatus(Status.STATUS_0.status);
							if(1!=agentAssProtocolService.updateAssProtoColRel(rel)){
								throw new ProcessException("业务分管协议更新失败");
							}
						}

						AssProtoColRel rel = new AssProtoColRel();
						rel.setAgentBusinfoId(db_AgentBusInfo.getId());
						rel.setAssProtocolId(agentBusInfoVo.getAgentAssProtocol());
						if(1!=agentAssProtocolService.addProtocolRel(rel,agent.getcUser())){
							throw new ProcessException("业务分管协议添加失败");
						}
					//删除分管协议
					}else{
						List<AssProtoColRel>  rels =agentAssProtocolService.queryProtoColByBusIds(Arrays.asList(db_AgentBusInfo.getId()));
						for (AssProtoColRel rel : rels) {
							rel.setStatus(Status.STATUS_0.status);
							if(1!=agentAssProtocolService.updateAssProtoColRel(rel)){
								throw new ProcessException("业务分管协议更新失败");
							}
						}

					}
				}
				agentDataHistoryService.saveDataHistory(agentBusInfoVo, DataHistoryType.BUSINESS.getValue());
			}
			return ResultVO.success(null);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
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
	public Map getRootFromBusInfo(String busId) {
		List<Map>  map = agentBusInfoMapper.queryTreeByBusInfo(FastMap.fastMap("id",busId));
		if(map.size()>0 && map.get(0).get("BUS_PARENT")!=null &&  StringUtils.isNotEmpty(map.get(0).get("BUS_PARENT")+"")){
			return getRootFromBusInfo(map.get(0).get("BUS_PARENT")+"");
		}
        if(map.size()!=1){
			return null;
		}
		return map.get(0);
	}


	@Override
	public List<AgentBusInfo> queryParenFourLevel(List<AgentBusInfo> list, String platformCode, String agentId) {

		if(list.size()==4)
			return list;
		AgentBusInfoExample example = new AgentBusInfoExample();
		example.or().andAgentIdEqualTo(agentId)
                .andBusPlatformEqualTo(platformCode).
                andStatusEqualTo(Status.STATUS_1.status);
		List<AgentBusInfo> plats = agentBusInfoMapper.selectByExample(example);
		if(plats.size()==0)
			return list;
		AgentBusInfo platInfo = plats.get(0);
		if(StringUtils.isNotEmpty(platInfo.getBusParent())) {
			AgentBusInfo parent = agentBusInfoMapper.selectByPrimaryKey(platInfo.getBusParent());
			if(parent!=null){
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

	}

	@Deprecated
	@Override
	public List<AgentBusInfo> queryChildFourLevel(List<AgentBusInfo> list, String platformCode, String agentId) {

		if(list.size()==4)
			return list;
		//当前代理商
		AgentBusInfoExample example = new AgentBusInfoExample();
		example.or().andAgentIdEqualTo(agentId)
				.andBusPlatformEqualTo(platformCode).
				andStatusEqualTo(Status.STATUS_1.status);
		List<AgentBusInfo> plats = agentBusInfoMapper.selectByExample(example);

		if(plats.size()==0)
			return list;

		//查询下级代理商
		AgentBusInfo platInfo = plats.get(0);

		AgentBusInfoExample child_example = new AgentBusInfoExample();
		child_example.or().andBusParentEqualTo(platInfo.getAgentId())
				.andBusPlatformEqualTo(platformCode).
				andStatusEqualTo(Status.STATUS_1.status);

		List<AgentBusInfo> child_plat = agentBusInfoMapper.selectByExample(child_example);

        //没有下级别就返回
		if(child_plat.size()==0)
			return list;
		//有就添加
		AgentBusInfo child = child_plat.get(0);

		list.add(child);

        //并返回获取的下一级
        return queryChildFourLevel(list,child.getBusPlatform(),child.getAgentId());
	}
}
