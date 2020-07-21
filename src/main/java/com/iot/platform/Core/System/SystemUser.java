package com.iot.platform.Core.System;

import com.iot.platform.Enum.System.RoleEnum;
import com.iot.platform.Interface.Core.RoleAccessInterface;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SystemUser {

    private String userCode;
    private RoleEnum roleEnum;
    private RoleAccessInterface roleAccess;

    public String getId() {
        return this.getUserCode();
    }

}