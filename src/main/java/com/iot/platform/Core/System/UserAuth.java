package com.iot.platform.Core.System;

import java.util.Collection;

import com.iot.platform.Interface.System.UserAuthInterface;

import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;

/**
 * UserAuth
 */
public class UserAuth extends org.springframework.security.core.userdetails.User implements UserAuthInterface {

    private Claims claims = null;
    private static final long serialVersionUID = 1L;

    public UserAuth(String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public void setClaims(Claims claims) {
        this.claims = claims;
    }

    @Override
    public Claims getClaims() {
        return this.claims;
    }

}