package com.ryx.jobOrder.service;

import com.ryx.credit.common.util.FastMap;
import com.ryx.jobOrder.pojo.JoTask;
import com.ryx.jobOrder.vo.JoTaskVo;

import java.util.List;
import java.util.Map;

public interface JobOrderTaskService {

    FastMap createJobOrderTask(JoTask taskMap) throws Exception;

    List<JoTask> queryJobOrderTask(JoTask queryMap);

    FastMap updateJobOrderTask(JoTask queryMap) throws Exception;

    List<JoTaskVo> queryJobOrderTaskVo(JoTaskVo joTaskVo);
}
