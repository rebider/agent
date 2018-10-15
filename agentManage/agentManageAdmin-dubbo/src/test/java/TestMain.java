
import com.alibaba.fastjson.JSONObject;
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

        //sn号码段
        LowerHairMachineVo lowerHairMachineVo = new LowerHairMachineVo();
        lowerHairMachineVo.setBusNum("busNum");
        lowerHairMachineVo.setOptUser("user");
        lowerHairMachineVo.setSnStart("E001");
        lowerHairMachineVo.setSnEnd("E002");
        //sn明细
        List<MposSnVo> listSn = new ArrayList<MposSnVo>();
        listSn.add(new MposSnVo("批次号"
                    ,"SN码"
                    ,"密钥"
                    ,"活动编号"
                    ,"终端类型"));
        listSn.add(new MposSnVo("批次号"
                ,"SN码"
                ,"密钥"
                ,"活动编号"
                ,"终端类型"));
        lowerHairMachineVo.setListSn(listSn);

        System.out.println(JSONObject.toJSONString(lowerHairMachineVo));


        //修改 业务系统数据集合
        List<ChangeActMachineVo> changeActMachineVos = new ArrayList<ChangeActMachineVo>();
        ChangeActMachineVo cav = new ChangeActMachineVo();
        cav.setBusNum("业务编号");
        cav.setNewAct("新活动1");
        cav.setOldAct("老活动1");
        cav.setOptUser("操作人");
        cav.setSnStart("开始sn");
        cav.setSnEnd("结束sn");
        cav.setPlatformType("MPOS");
        //待调整集合
        changeActMachineVos.add(cav);

        cav = new ChangeActMachineVo();
        cav.setBusNum("业务编号");
        cav.setNewAct("新活动2");
        cav.setOldAct("老活动2");
        cav.setOptUser("操作人");
        cav.setSnStart("开始sn");
        cav.setSnEnd("结束sn");
        cav.setPlatformType("MPOS");
        //待调整集合
        changeActMachineVos.add(cav);

        System.out.println(JSONObject.toJSONString(changeActMachineVos));


    }
}
