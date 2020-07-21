package com.iot.platform.Interface.System;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

public interface UserAuthInterface extends UserDetails {

    Claims getClaims();

}