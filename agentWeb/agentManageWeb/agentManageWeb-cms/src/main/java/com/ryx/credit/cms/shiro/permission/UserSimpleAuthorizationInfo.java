package com.ryx.credit.cms.shiro.permission;

import org.apache.shiro.authz.SimpleAuthorizationInfo;

import java.util.HashSet;
import java.util.Set;

/**
 * 作者：cx
 * 时间：2019/4/26
 * 描述：
 */
public class UserSimpleAuthorizationInfo extends SimpleAuthorizationInfo {

    protected Set<String> stringCheckPermissions;

    public Set<String> getStringCheckPermissions() {
        return stringCheckPermissions;
    }

    public void setStringCheckPermissions(Set<String> stringCheckPermissions) {
        Set<String> per = new HashSet<>();
        stringCheckPermissions.forEach(perm -> {
            per.add(perm.replace("/","").trim());
        });
        this.stringCheckPermissions = per;
    }
}
