import com.baomidou.mybatisplus.annotations.TableId;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.service.dict.IdService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by cx on 2018/5/22.
 */
public class DictServiceTest extends BaseSpringTest {


    @Autowired
    private IdService idService;

    @Test
    public void testId(){
        System.out.println("=======testId=====");
        System.out.println("======="+idService.genId(TabId.a_agent));
        System.out.println("=======testId=====");
    }

}
