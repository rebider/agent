package com.ryx.jobOrder.service;

import com.ryx.credit.common.util.FastMap;

import java.util.Map;

public interface JobOrderTaskService {

    FastMap createJobOrderTask(Map taskMap);

    FastMap queryJobOrderTask(Map queryMap);

    FastMap updateJobOrderTask(Map queryMap);
}
