package com.parkinfo.shiro;

import com.parkinfo.token.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 自定义Realm
 * @author cnyuchu@gmail.com
 * @date 2018/10/19 14:10
 */
@Component
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private TokenUtils tokenUtils;
    /**
     * 必须重写此方法，不然会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UserToken;
    }
    /**
     * 授权
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("权限配置-->doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        AppUser loginUser = tokenUtils.getLoginUser(principals.toString());
//        if (loginUser == null){
//            throw new AuthorizationException("token已过期或不存在");
//        }
        // 赋权
        return authorizationInfo;
    }

    /**
     * 身份验证
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("登录验证->doGetAuthenticationInfo()");
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
//        AppUser adminUser = tokenUtils.getLoginUser(token);
//        if (adminUser == null){
//            throw new AuthenticationException("token已过期或不存在！");
//        }
        return new SimpleAuthenticationInfo(token, token, "UserRealm");
    }
}
