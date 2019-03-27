package com.ryx.credit.service;

import java.util.Set;

/**
 * Created by RYX on 2019/3/26.
 */
public interface CRoleService {

    Set<String> selectShiroUrl(Long userId);

}
