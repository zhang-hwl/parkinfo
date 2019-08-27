package com.parkinfo.shiro;

import org.apache.shiro.authc.AuthenticationToken;

import java.io.Serializable;

/**
 * @author cnyuchu@gmail.com
 * @date 2018/11/22 9:40
 */
public class UserToken implements AuthenticationToken, Serializable {

    private static final long serialVersionUID = 1841491628743017587L;

    private String token;


    public UserToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
