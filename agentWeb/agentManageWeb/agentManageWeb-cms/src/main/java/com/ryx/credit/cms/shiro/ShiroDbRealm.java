package com.ryx.credit.cms.shiro;

import com.ryx.credit.cms.shiro.permission.UrlPermission;
import com.ryx.credit.cms.shiro.permission.UserSimpleAuthorizationInfo;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.commons.shiro.ShiroUser;
import com.ryx.credit.pojo.admin.CResource;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.vo.UserVo;
import com.ryx.credit.service.IResourceService;
import com.ryx.credit.service.IRoleService;
import com.ryx.credit.service.IUserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @description：shiro权限认证
 * @author：zhixuan.wang
 * @date：2015/10/1 14:51
 */
public class ShiroDbRealm extends AuthorizingRealm {
    private static final Logger LOGGER = LogManager.getLogger(ShiroDbRealm.class);

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IResourceService resourceService;
    @Autowired
    private RedisService redisService;


    
    public ShiroDbRealm(CacheManager cacheManager, CredentialsMatcher matcher) {
        super(cacheManager, matcher);

    }
    @PostConstruct
    public void initService(){
        UrlPermission.loadUrl(resourceService,redisService);
    }
    
    /**
     * Shiro登录认证(原理：用户提交 用户名和密码  --- shiro 封装令牌 ---- realm 通过用户名将密码查询返回 ---- shiro 自动去比较查询出密码和用户输入密码是否一致---- 进行登陆控制 )
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {
        LOGGER.info("Shiro开始登录认证");
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        UserVo uservo = new UserVo();
        uservo.setLoginName(token.getUsername());
        List<CUser> list = userService.selectByLoginName(uservo);
        // 账号不存在
        if (list == null || list.isEmpty()) {
            return null;
        }
        CUser user = list.get(0);
        // 账号未启用
        if (user.getStatus() == 1) {
            return null;
        }
        // 读取用户的url和角色
        Map<String, Set<String>> resourceMap = roleService.selectResourceMapByUserId(user.getId());
        Set<String> urls = resourceMap.get("urls");
        Set<String> roles = resourceMap.get("roles");
        ShiroUser shiroUser = new ShiroUser(user.getId(), user.getLoginName(), user.getName(), urls);
        shiroUser.setRoles(roles);
        // 认证缓存信息
        return new SimpleAuthenticationInfo(shiroUser, user.getPassword().toCharArray(),
                ShiroByteSource.of(user.getSalt()), getName());
    }

    /**
     * Shiro权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        UserSimpleAuthorizationInfo info = new UserSimpleAuthorizationInfo();
        info.setRoles(shiroUser.getRoles());
        info.addStringPermissions(shiroUser.getUrlSet());
        info.setStringCheckPermissions(shiroUser.getUrlSet());
        return info;
    }

    @Override
    public void onLogout(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        removeUserCache(shiroUser);
    }

    /**
     * 清除用户缓存
     * @param shiroUser
     */
    public void removeUserCache(ShiroUser shiroUser){
        removeUserCache(shiroUser.getLoginName());
    }

    /**
     * 清除用户缓存
     * @param loginName
     */
    public void removeUserCache(String loginName){
        SimplePrincipalCollection principals = new SimplePrincipalCollection();
        principals.add(loginName, super.getName());
        super.clearCachedAuthenticationInfo(principals);
    }


    /**
     * 鉴权
     * @param permission
     * @param info
     * @return
     */
    @Override
    protected boolean isPermitted(Permission permission, AuthorizationInfo info) {
        //url权限检查
        if(permission instanceof UrlPermission) {
            if(info!=null) {
                //用户分配的权限
                UserSimpleAuthorizationInfo authorizationInfo = (UserSimpleAuthorizationInfo) info;
                //用户分配的权限
                Set<String> url_user_check = authorizationInfo.getStringCheckPermissions();
                //权限是否在权限库中管理
                //用户请求连接 是否包含在权限管理库中
                String url_req = ((UrlPermission) permission).getUrl();
                //去除斜杠 和空格
                url_req = (url_req+"").replace("/","").trim();
                //检查是否包含
                if (UrlPermission.existPerm(redisService,url_req)) {
                    //用户权限是否包含请求的去斜杠，去空格的连接
                    if (url_user_check.contains(url_req)) {
                        //有就放过
                        LOGGER.debug("请求权限认证："+url_req+"，有权访问");
                        return true;
                        //无就拦截
                    } else {
                        LOGGER.debug("请求权限认证："+url_req+"，无权访问");
                        return false;
                    }
                } else {
                    LOGGER.debug("请求权限认证："+url_req+"，不在权限库");
                    //不包含在权限库中就放过
                    return true;
                }
            }
            return false;
        }else {
            //默认方法实现
            Collection<Permission> perms = getPermissions(info);
            if (perms != null && !perms.isEmpty()) {
                for (Permission perm : perms) {
                    if (perm.implies(permission)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
