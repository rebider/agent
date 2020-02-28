package com.ryx.jobOrder.service;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.util.FastMap;
import com.ryx.jobOrder.pojo.JoExpandKey;
import com.ryx.jobOrder.pojo.JoOrder;

import javax.management.Query;
import java.util.List;
import java.util.Map;

public interface JobOrderStartService {

    FastMap createJobOrder(JoOrder joOrder, Map orderMap) throws Exception;

    List<FastMap> getStartLaunchVo(JSONObject para);

    FastMap saveJobOrderExband(JoExpandKey joExpandKey);

    JoOrder queryListByTaskId(String taskId);
}
