package cn.linzs.app.common.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author linzs
 * @Date 2017-12-18 17:32
 * @Description
 */
public class UserRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> permissionsSet = new HashSet<>();
        permissionsSet.add("sys:category:list");
        info.setStringPermissions(permissionsSet);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userId = token.getUsername();
        char[] tokenChar = token.getPassword();

        // 不需要做登录判断逻辑，登录已在LoginController完成，这里只需要直接返回info
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userId, tokenChar, getName());
        return info;
    }

    /**
     * 清除所有的缓存信息
     * */
    public void clearAllCachedInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }

    /**
     *
     * @Title: reloadAuthorizing
     * @Description: 刷新指定用户的权限信息
     @param account
      * @return void
     */
    public void reloadAuthorizing(String account) {
        Subject subject = SecurityUtils.getSubject();
        String realmName = subject.getPrincipals().getRealmNames().iterator().next();
        SimplePrincipalCollection principals = new SimplePrincipalCollection(account, realmName);
        subject.runAs(principals);
        getAuthorizationCache().remove(subject.getPrincipals());
        subject.releaseRunAs();
    }

}
