package com.iot.platform.Core.System;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iot.platform.Validator.Password;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class PasswordData {

    @Password
    private String password;
    @Password
    private String rePassword;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    public boolean isValid() {
        return this.password.equals(this.rePassword);
    }

}