package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.DataChangeApyType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.dao.agent.AgentDebitCardMapper;
import com.ryx.credit.dao.agent.DateChangeRequestMapper;
import com.ryx.credit.pojo.admin.vo.AgentColinfoVo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.agent.AgentDebitCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 结算卡管理实现雷
 * Created by Chen Qiutian on 2019/7/31.
 */
@Service("agentDebitCardService")
public class AgentDebitCardServiceImpl implements AgentDebitCardService {

    @Autowired
    private AgentDebitCardMapper agentDebitCardMapper;
    @Autowired
    private DateChangeRequestMapper dateChangeRequestMapper;

    @Override
    public PageInfo getDebitCardList(Map<String, String> map, Page page) {
        PageInfo pageInfo = new PageInfo();
        List<Map<String,String>> list = agentDebitCardMapper.queryForList(map,page);
        pageInfo.setRows(list);
        pageInfo.setTotal(agentDebitCardMapper.countQuery(map));
        return pageInfo;
    }

    @Override
    public List<Map<String,Object>> exports(Map<String,String> map){
        return  agentDebitCardMapper.exports(map);
    }

    @Override
    public List<Map<String,String>> getBusInfoById(String id){
        return agentDebitCardMapper.getBusInfoById(id);
    }

    @Override
    public Map<String,String> getColAndAgentById(String id){
        Map<String,String> stringMap = new HashMap<String,String>();
        stringMap.put("agentId",id);
        return agentDebitCardMapper.queryForList(stringMap,null).get(0);

    }

    @Override
    public void updateSuggestStatusById(String id,String statu)throws MessageException{
        try {
            agentDebitCardMapper.updateSuggestStatusById(id,statu);
        }catch (Exception e){
            throw new MessageException("修改失败");
        }
    }

    @Override
    public PageInfo getNoticeList(String orgId,Page page){
        PageInfo pageInfo = new PageInfo();
        List<Map<String,String>> list = agentDebitCardMapper.getNoticeList(orgId,page);
        pageInfo.setRows(list);
        pageInfo.setTotal(agentDebitCardMapper.getNoticeCount(orgId));
        return pageInfo;
    }

    @Override
    public PageInfo queryAgentColinfo(Map<String, String> map, Page page) {
        PageInfo pageInfo = new PageInfo();
        List<Map<String,String>> list = agentDebitCardMapper.queryAgentColinfoList(map,page);
        pageInfo.setRows(list);
        pageInfo.setTotal(agentDebitCardMapper.queryAgentColinfoCount(map));
        return pageInfo;
    }

    @Override
    public PageInfo serchDataChangeReqByAg(Map<String, Object> param, PageInfo pageInfo) throws IOException, SQLException {
        param.put("DC_AG_Colinfo", DataChangeApyType.DC_AG_Colinfo.name());
        param.put("DC_Colinfo",DataChangeApyType.DC_Colinfo.name());
        Long count = dateChangeRequestMapper.serchDataChangeReqByAgCount(param);
        pageInfo.setTotal(count.intValue());
        List<Map<String, Object>> dataChangeList = dateChangeRequestMapper.serchDataChangeReqByAgList(param);
        List<Map<String, Object>>  list = new ArrayList<>();
        for (Map<String, Object> dateChangeReq : dataChangeList) {
            HashMap<String, Object> map = new HashMap<>();
            Clob data_content = (Clob) dateChangeReq.get("DATA_CONTENT");
            AgentVo vo = JSONObject.parseObject(ClobToString(data_content), AgentVo.class);
            if(null!=vo){
                if (null != vo.getColinfoVoList() && vo.getColinfoVoList().size() > 0) {
                    List<AgentColinfoVo> colinfoVoList = vo.getColinfoVoList();
                    AgentColinfoVo agentColinfoVo = colinfoVoList.get(0);
                    map.put("CLO_BANK_ACCOUNT",agentColinfoVo.getCloBankAccount());
                    map.put("CLO_TYPE",agentColinfoVo.getCloType());
                    map.put("CLO_BANK",agentColinfoVo.getCloBank());
                    map.put("CLO_BANK_BRANCH",agentColinfoVo.getCloBankBranch());
                    map.put("ALL_LINE_NUM",agentColinfoVo.getAllLineNum());
                    map.put("BRANCH_LINE_NUM",agentColinfoVo.getBranchLineNum());

                }
            }
            Clob data_pre_content = (Clob) dateChangeReq.get("DATA_PRE_CONTENT");
            AgentVo voPre = JSONObject.parseObject(ClobToString(data_pre_content), AgentVo.class);
            if(null!=voPre){
                if (null != voPre.getColinfoVoList() && voPre.getColinfoVoList().size() > 0) {
                    List<AgentColinfoVo> colinfoVoList = voPre.getColinfoVoList();
                    AgentColinfoVo agentColinfoVo = colinfoVoList.get(0);
                    map.put("CLO_BANK_ACCOUNT",agentColinfoVo.getCloBankAccount());
                    map.put("CLO_TYPE_PRE",agentColinfoVo.getCloType());
                    map.put("CLO_BANK_PRE",agentColinfoVo.getCloBank());
                    map.put("CLO_BANK_BRANCH_PRE",agentColinfoVo.getCloBankBranch());
                    map.put("ALL_LINE_NUM_PRE",agentColinfoVo.getAllLineNum());
                    map.put("BRANCH_LINE_NUM_PRE",agentColinfoVo.getBranchLineNum());
                }
            }
            map.put("ID",dateChangeReq.get("ID"));
            map.put("C_TIME",dateChangeReq.get("C_TIME"));
            map.put("APPY_STATUS",dateChangeReq.get("APPY_STATUS"));
            map.put("C_UPDATE",dateChangeReq.get("C_UPDATE"));
            list.add(map);
        }
        pageInfo.setRows(list);
        return pageInfo;
    }
    public String ClobToString(Clob sc) throws SQLException, IOException {
        String reString = "";
        Reader is = sc.getCharacterStream();// 得到流
        BufferedReader br = new BufferedReader(is);
        String s = br.readLine();
        StringBuffer sb = new StringBuffer();
        while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
            sb.append(s);
            s = br.readLine();
        }
        reString = sb.toString();
        return reString;
    }

}
