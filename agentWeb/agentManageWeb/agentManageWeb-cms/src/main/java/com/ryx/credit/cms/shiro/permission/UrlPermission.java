package com.ryx.credit.cms.shiro.permission;

import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.CResource;
import com.ryx.credit.service.IResourceService;
import org.apache.shiro.authz.Permission;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 作者：cx
 * 时间：2019/4/26
 * 描述：
 */
public class UrlPermission implements Permission {

    //所有url权限

    private static String permission_all_key="permission:all";

    private String url;

    public UrlPermission(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UrlPermission that = (UrlPermission) o;

        return url.equals(that.url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }


    /**
     * 加载权限到缓存当中
     * @param resourceService
     * @param redisService
     */
    public static void loadUrl(IResourceService resourceService, RedisService redisService){
        //所有权限
        redisService.delete(permission_all_key);
        List<CResource> list = resourceService.selectAll();
        Set<String> set_v  =  list.stream().map(res->  res.getUrl()==null?"sys_default_blk":res.getUrl().replace("/","").trim()).collect(Collectors.toSet());
        redisService.sAdd(permission_all_key,set_v.toArray(new String[0]));
    }

    /**
     * 是否包含权限
     * @param redisService
     * @param perm
     */
    public static boolean existPerm(RedisService redisService,String perm){
        //所有权限
        if(StringUtils.isNotBlank(perm))
            return redisService.sismember(permission_all_key,perm);
        return false;
    }


    @Override
    public boolean implies(Permission p) {
        if(p instanceof UrlPermission){
            return this.equals(p);
        }
        return false;
    }


}
