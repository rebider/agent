import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotations.TableId;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.agent.DateChangeRequest;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.service.agent.AimportService;
import com.ryx.credit.service.agent.DateChangeReqService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.aspectj.AspectJAsyncConfiguration;

import java.util.List;
import java.util.Map;


/**
 * Created by cx on 2018/5/22.
 */
public class DictServiceTest extends BaseSpringTest {

    private Logger  logger = LoggerFactory.getLogger(DictServiceTest.class);

    @Autowired
    private IdService idService;
    @Autowired
    private DictOptionsService dictOptionsService;

    @Autowired
    private DateChangeReqService dateChangeReqService;

    @Autowired
    private AimportService aimportService;
    
    @Test
    public void testId(){
        logger.info("=======testId=====");
        logger.info("======="+idService.genId(TabId.a_agent));
        logger.info("=======testId=====");
    }

    @Test
    public void testOptions(){
        logger.info("=======testOptions=====");
        List<Dict> list =  dictOptionsService.dictList("AGENT","CAPITAL_TYPE");
        logger.info("=======testOptions====="+ JSONObject.toJSONString(list));
    }
    


    @Test
    public void testDataChange(){
        logger.info("=======testDataChange=====");
        DateChangeRequest data = dateChangeReqService.getById("DC20180606000000000000022");
        logger.info("=======testDataChange====="+data);
    }


    @Test
    public void testImPortAgent(){
        logger.info("=======testImPortAgent=====");
        try {
          ResultVO data = aimportService.analysisRecode("1");
          logger.info(data.getResInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("=======testImPortAgent=====");
    }

}
