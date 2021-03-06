package com.ryx.credit.profit.service;/**
 * @Auther: zhaodw
 * @Date: 2018/8/1 11:33
 * @Description:
 */

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.ProfitOrganTranMonth;
import com.ryx.credit.profit.pojo.TranCheckData;
import com.ryx.credit.profit.pojo.TranCheckPlatForm;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 每月交易核对
 *
 * @author zhaodw
 * @create 2018/8/1
 * @since 1.0.0
 */
public interface ProfitOrganTranMonthService {
    /***
    * @Description:修改交易信息
    * @Param: profitOrganTranMonth 交易信息
    * @Author: zhaodw
    * @Date: 2018/8/1
    */
    void insert(ProfitOrganTranMonth profitOrganTranMonth);

    /***
    * @Description:修改交易信息
    * @Param: profitOrganTranMonth 交易信息
    * @Author: zhaodw
    * @Date: 2018/8/1
    */
    void update(ProfitOrganTranMonth profitOrganTranMonth);

    /*** 
    * @Description: 查询列表
    * @Param:  profitOrganTranMonth 交易信息
    * @return:  分页对象
    * @Author: zhaodw 
    * @Date: 2018/8/1 
    */ 
    PageInfo getProfitOrganTranMonthList(ProfitOrganTranMonth profitOrganTranMonth, Page page);
    
    /*** 
    * @Description: 根据分润月份删除对应交易数据
    * @Param:  profitOrganTranMonth 交易数据信息
    * @Author: zhaodw
    * @Date: 2018/8/2 
    */ 
    void delete(ProfitOrganTranMonth profitOrganTranMonth);

    /*** 
    * @Description: 重新导入数据
    * @Param:  profitOrganTranMonth 交易信息
    * @Author: zhaodw
    * @Date: 2018/8/10 
    */
    AgentResult importData(String type);

    /**
     * 调用清结算接口 查询消费交易总额
     * @param param
     * @return
     */
    String doSettleTranAmount(Map<String,String> param);

    /**
     * 调用日结分润接口 获取月结分润
     * @param param
     * @return
     */
    String doProfitNewMonth(Map<String,String> param);

    /**
     * 获取自主品牌交易量及手续费
     * @param tranMonth
     * @return
     */
    Map<String,Object> getTranAmtByMonth(String tranMonth);

    /**
     * 获取TRANCHECK_PLATFORM表中所有业务类型对象
     * @return
     */
    List<TranCheckPlatForm> getAllPlatForm();

    /**
     * 添加数据
     * @param tranCheckData
     * @return
     */
    int insertTranCheckData(TranCheckData tranCheckData);

    /**
     * 同步数据
     * @param list
     * @return
     */
    int synchronizeTranCheckData(List<TranCheckData> list);

    Map<String,String> doSynchronizeTranCheckData();

    int updateTranCheckData(TranCheckData tranCheckData);

    List<Map<String,String>> getAllPlatFormType();

    TranCheckData getTranCheckDataByProfitMonthAndPlatFormId(String profitMonth, BigDecimal platFormId);
    /**
     * 根据分润月份获取核对数据
     * @param profitMonth
     * @return
     */
    List<TranCheckData> getAllCheckDataByProfitMonth(String profitMonth);

    /**
     * 根据分润月份和业务平台获取核对数据
     * @param plarFormType
     * @param profitMonth
     * @return
     */
    List<TranCheckData> getAllCheckDataByPlatFormTypeAndProfitMonth(String plarFormType, String profitMonth);
}
