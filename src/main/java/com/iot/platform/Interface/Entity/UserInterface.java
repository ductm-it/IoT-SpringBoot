package com.iot.platform.Interface.Entity;

import java.util.Date;

import com.iot.platform.Core.System.SystemUser;
import com.iot.platform.Enum.System.RoleEnum;
import com.iot.platform.Util.BCryptUtil;

public interface UserInterface extends EntityInterface {

    public static BCryptUtil bCrypt = new BCryptUtil(11);

    public static String hashPassword(String password) {
        return bCrypt.hash(password);
    }

    public static Boolean verifyHash(String str, String hash) {
        return bCrypt.verifyHash(str, hash);
    }

    public Boolean verifyHash(String password);

    public Boolean verifyHashToken(String token);

    public void setPassword(String password);

    public void setAvatar(String avatar);

    public String getFullName();

    public String getEmailAddress();

    public String getHash();

    public void setHash(String hash);

    public String getUsername();

    public void setUsername(String username);

    public RoleEnum getRoleEnum();

    public void setRoleEnum(RoleEnum roleEnum);

    public Date getResetPasswordDate();

    public void setResetPasswordDate(Date resetPasswordDate);

    public String getResetPasswordToken();

    public void setResetPasswordToken(String resetPasswordToken);

    public SystemUser toSystemUser();

}