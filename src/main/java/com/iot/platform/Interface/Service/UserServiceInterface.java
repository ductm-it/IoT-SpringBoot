package com.iot.platform.Interface.Service;

import com.iot.platform.Core.System.SystemUser;
import com.iot.platform.Core.System.UserAuth;
import com.iot.platform.Enum.System.RoleEnum;
import com.iot.platform.Interface.Core.RoleAccessInterface;
import com.iot.platform.Interface.System.AuthenticationInterface;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import io.jsonwebtoken.Claims;

public interface UserServiceInterface extends AuthenticationInterface {

    public default String getLoggedInId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String userId = ((UserDetails) authentication.getPrincipal()).getUsername();
            return userId;
        } else {
            throw new UsernameNotFoundException("Username not found");
        }
    }

    public default SystemUser getLoggedInUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserAuth) {
            UserAuth userAuth = (UserAuth) authentication.getPrincipal();
            Claims claims = userAuth.getClaims();
            RoleEnum roleEnum = RoleEnum.ROLE_USER;

            if (claims.get("roleEnum") != null) {
                roleEnum = RoleEnum.valueOf(claims.get("roleEnum").toString());
            }
            SystemUser systemUser = new SystemUser();
            systemUser.setUserCode(userAuth.getUsername());
            systemUser.setRoleEnum(roleEnum);
            systemUser.setRoleAccess(this.getRoleAccess(userAuth.getUsername()));
            return systemUser;
        } else {
            throw new UsernameNotFoundException("Username not found");
        }
    }

    public RoleAccessInterface getRoleAccess(String userCode);

}
