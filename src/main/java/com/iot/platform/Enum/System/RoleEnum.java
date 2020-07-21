package com.iot.platform.Enum.System;

import com.iot.platform.Interface.Enum.EnumInterface;

import org.springframework.security.core.GrantedAuthority;

public enum RoleEnum implements GrantedAuthority, EnumInterface {
    ROLE_ADMIN, ROLE_USER;

    @Override
    public String getAuthority() {
        return this.toString();
    }

}