package com.iot.platform.Core.Request;

import com.iot.platform.Validator.NotEmpty;
import com.iot.platform.Validator.NotNull;
import com.iot.platform.Validator.Password;
import com.iot.platform.Validator.Size;
import com.iot.platform.Validator.Username;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestLogin {

    @Size(max = 20)
    @NotNull
    @NotEmpty
    @Username
    private String username;

    @NotNull
    @NotEmpty
    @Password
    private String password;

}