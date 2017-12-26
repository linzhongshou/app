package cn.linzs.app.common.shiro;

import cn.linzs.app.common.utils.token.ITokenManager;
import cn.linzs.app.common.utils.token.model.TokenModel;
import cn.linzs.app.domain.User;
import cn.linzs.app.repo.IUserRepo;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author linzs
 * @Date 2017-12-18 17:32
 * @Description
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private IUserRepo userRepo;
    @Autowired
    private ITokenManager tokenManager;

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
//        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
//        String account = token.getUsername();
//        User user = userRepo.findByAccount(account);
//        if(user == null) {
//            throw new UnknownAccountException("账号或密码不正确");
//        }
//
////        Map<String, Object> dataMap = new HashMap<>();
////        dataMap.put("userId", user.getId());
////        TokenModel tokenModel = tokenManager.generateToken(dataMap);
//
//        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(account, user.getPassword(), getName());
//        return info;

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String account = token.getUsername();
        char[] password = token.getPassword();

        // 不需要做登录判断逻辑，登录已在LoginController完成，这里只需要直接返回info
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(account, password, getName());
        return info;
    }
}
