package junit;


import com.ryx.credit.service.ActivityService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author wangqi
 * @version 1.0
 * @date 2016年8月24日 21:18:17
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/test/spring-context.xml" })
public class Test {
    @Autowired
    private ActivityService activityService;



    @org.junit.Test
    public void repay() throws Exception{
        System.out.println(activityService.getImage("11"));
    }


}

