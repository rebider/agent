package com.ryx.credit.profit.service;


import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.PosRewardDetail;

import java.util.Map;


public interface PosRewardSDetailService {
    PageInfo getRewardDetailList(Map<String, Object> param, PageInfo pageInfo);

    void insert(PosRewardDetail posrewardDetail);

    PosRewardDetail getPosRewardDetail(PosRewardDetail posRewardDetail);

    void updatePosRewardDetail(PosRewardDetail posRewardDetail);

    void clearPosRewardDetail(String currentDate);

    void deleteCurrentDate(String currentDate);
}
