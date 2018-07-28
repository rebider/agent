package junit.profit;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.agent.DateChangeRequest;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.service.ProfitDeductionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 加载配置文件
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:spring-mybatis.xml" })
public class ProfitDeductionServiceTest {

    private Logger  logger = LoggerFactory.getLogger(ProfitDeductionServiceTest.class);

    @Autowired
    private ProfitDeductionService profitDeductionServiceImpl;


    @Test
    public void testgetProfitDeductionList(){
        ProfitDeduction deduction = new ProfitDeduction();
        PageInfo pageInfo =  profitDeductionServiceImpl.getProfitDeductionList(deduction,null);
        logger.info("返回查询结果。。。。。"+pageInfo.getRows());
    }
    


}
