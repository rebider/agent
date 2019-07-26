package com.ryx.credit.service.pay;

import com.ryx.credit.common.result.AgentResult;

/**
 * Created by RYX on 2018/9/10.
 */
public interface LivenessDetectionService {

    AgentResult livenessDetection(String trueName,String certNo,String userId);
    AgentResult threeElementsCertificationDetection(String trueName,String certNo,String userId,String bankNo);
}
