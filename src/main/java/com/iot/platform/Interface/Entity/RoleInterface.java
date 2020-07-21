package com.iot.platform.Interface.Entity;

import java.util.Map;

import com.iot.platform.Core.System.RoleAccess;

public interface RoleInterface extends EntityInterface {

    public Map<String, Long> getRole();

    public default RoleAccess toRoleAccess() {
        RoleAccess roleAccess = new RoleAccess();
        roleAccess.setMap(this.getRole());
        return roleAccess;
    }

}