package com.ryx.credit.service.agent;

import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.agent.ImportAgent;

import java.util.List;

/**
 * Created by cx on 2018/6/11.
 */
public interface AimportService {

    public int insertAgentImportData(ImportAgent importAgent);


    public List<String> addList(List<List<Object>> data,String dataType,String user,String batch)throws Exception;

    public ResultVO analysisRecode(String userid)throws Exception;

    public PageInfo queryList(PageInfo page, ImportAgent importAgent);

    public  ResultVO analysisBase(List<ImportAgent>  data,String userid)throws Exception;

    public ResultVO analysisPayment(List<ImportAgent>  data,String userid)throws Exception;

    public ResultVO analysisContract(List<ImportAgent>  data,String userid)throws Exception;

    public ResultVO analysisBus(List<ImportAgent>  data,String userid)throws Exception;

    public ResultVO parseParent()throws Exception;
}
