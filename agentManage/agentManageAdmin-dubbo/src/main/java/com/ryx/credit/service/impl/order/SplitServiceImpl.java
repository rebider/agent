package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.RedisCachKey;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.MapUtil;
import com.ryx.credit.dao.order.OLogisticsDetailMapper;
import com.ryx.credit.pojo.admin.order.OLogisticsDetailExample;
import com.ryx.credit.service.order.OLogisticsService;
import com.ryx.credit.service.order.SplitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/4/25 16:45
 * @Param
 * @return
 **/
@Service("splitService")
public class SplitServiceImpl implements SplitService {

    @Autowired
    private OLogisticsDetailMapper logisticsDetailMapper;
    @Autowired
    private OLogisticsService logisticsService;
    @Autowired
    private RedisService redisService;

    @Override
    public List<Map<String,Object>> getOrderMsgByExcel(List<List<Object>> excelList,String cUser)throws MessageException {
        List<Map<String,Object>> resSeg = new ArrayList<>();
        for (List<Object> excel : excelList) {
            String snBegin = "";
            String snEnd = "";
            String count = "";
            String proModel = "";
            String logStr = "";
            try {
                snBegin = String.valueOf(excel.get(0)).trim();
                snEnd = String.valueOf(excel.get(1)).trim();
                count = String.valueOf(excel.get(2)).trim();
                proModel = String.valueOf(excel.get(3)).trim();
                logStr = snBegin + "-" + snEnd;
            } catch (Exception e) {
                throw new MessageException(snBegin + "-" + snEnd+ ", 导入解析文件失败");
            }

            List<String> snList = logisticsService.idList(snBegin, snEnd, 0, 0, "XDL");
            if(snList.size()!=Integer.parseInt(count)){
                throw new MessageException(logStr + ",解析明细失败与数量不符");
            }
            FastMap aMap = null;
            for (int i=0;i<snList.size();i++){
                OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
                OLogisticsDetailExample.Criteria criteria = oLogisticsDetailExample.createCriteria();
                criteria.andSnNumEqualTo(snList.get(i));
                long snCount = logisticsDetailMapper.countByExample(oLogisticsDetailExample);
                if(aMap==null){
                    aMap = FastMap.fastMap("startSn",snList.get(i))
                            .putKeyV("endSn",snList.get(i))
                            .putKeyV("num",1)
                            .putKeyV("proModel",proModel)
                            .putKeyV("flag",snCount>0?1:0);
                }else if(MapUtil.getInt(aMap,"flag")==(snCount>0?1:0)){
                    aMap.putKeyV("endSn",snList.get(i))
                    .putKeyV("num",MapUtil.getInt(aMap,"num")+1);
                }else if(MapUtil.getInt(aMap,"flag")!=(snCount>0?1:0)){
                    resSeg.add(aMap);
                    aMap = FastMap.fastMap("startSn",snList.get(i))
                            .putKeyV("endSn",snList.get(i))
                            .putKeyV("num",1)
                            .putKeyV("proModel",proModel)
                            .putKeyV("flag",snCount>0?1:0);
                }
            }
            resSeg.add(aMap);
        }
        List<Map<String, Object>> maps = redisService.popListMap(RedisCachKey.APP_SPLIT+":"+cUser);
        if(maps.size()!=0){
            redisService.delete(RedisCachKey.APP_SPLIT+":"+cUser);
        }
        Long count = redisService.pushListMap(RedisCachKey.APP_SPLIT+":"+cUser, resSeg,60 * 60 * 24);
        if(count==0){
            throw new MessageException("上传SN失败");
        }
        return resSeg;
    }

    @Override
    public List<Map<String, Object>> exportSplit(String exportType,String cUser)throws MessageException {

        List<Map<String, Object>> redisList = redisService.popListMap(RedisCachKey.APP_SPLIT+":"+cUser);
        if(redisList==null){
            throw new MessageException("导出数据不存在");
        }
        if(redisList.size()==0){
            return redisList;
        }
        for (int i=0;i<redisList.size();i++){
            String flag = String.valueOf(redisList.get(i).get("flag"));
            if(!exportType.equals(flag)){
                redisList.remove(redisList.get(i));
            }
        }
        return redisList;
    }
}
