package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.vo.AgentoutVo;
import com.ryx.credit.profit.pojo.PosCheck;
import com.ryx.credit.profit.pojo.PosCheckExample;
import com.ryx.credit.profit.pojo.ProfitDirect;
import com.ryx.credit.profit.pojo.ProfitDirectExample;
import com.sun.corba.se.spi.ior.ObjectKey;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * IPosCheckService
 * Created by IntelliJ IDEA.
 *
 * @Author Wang y
 * @Date 2017/7/31
 * @Time: 16:42
 * To change this template use File | Settings | File Templates.
 */

public interface IProfitDirectService {

    PageInfo getProfitDirectList(Map<String, Object> param, PageInfo pageInfo);

    long countByExample(ProfitDirectExample example);

    int deleteByExample(ProfitDirectExample example);

    int insert(ProfitDirect record);

    int insertSelective(ProfitDirect record);

    List<ProfitDirect> selectByExample(ProfitDirectExample example);

    List<ProfitDirect> selectByMonth(ProfitDirect record);

    ProfitDirect selectByPrimaryKey(String id);

    ProfitDirect selectByAgentAndMon(ProfitDirect record);

    int updateByPrimaryKeySelective(ProfitDirect record);

    int updateByPrimaryKey(ProfitDirect record);

    BigDecimal getSubBuckleByMonth(ProfitDirect record);

    //void updateByAgentId(ProfitDirect ProfitDirect);

    //直发分润页面导出
    public List<ProfitDirect> exportProfitDirect(Map<String,Object> param);

    //直发分润查询
    List<ProfitDirect> selectByFristAgentPid(ProfitDirect profitDirect);

    //根据唯一码查询业务平台编码
    List<String> selectByAgUniqNum(String agUniqNum);

    //直发分润修改状态
    void updateByStatus(ProfitDirect profitDirectSingleList);

    Map<String, Object> profitCount(Map<String,Object> param);
}
