
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.machine.vo.AdjustmentMachineVo;
import com.ryx.credit.machine.vo.ChangeActMachineVo;
import com.ryx.credit.machine.vo.LowerHairMachineVo;
import com.ryx.credit.machine.vo.MposSnVo;
import com.ryx.credit.pojo.admin.order.OLogisticsDetail;
import com.ryx.credit.pojo.admin.vo.AgentNotifyVo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by cx on 2018/6/12.
 */
public class TestMain {

    public static void main(String[] args) {
        String str="8850003000010saw";
        String substring = str.substring(0, str.length() - 1);
        str.substring(0, str.length() - 1);
        str.substring(str.length()-1);
        System.out.println(substring+"---");
        System.out.println();
    }
}
