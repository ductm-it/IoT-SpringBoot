package com.iot.platform.Interface.Core;

import com.iot.platform.Core.System.SystemUser;
import com.iot.platform.Enum.System.ServerActionEnum;
import com.iot.platform.Interface.Entity.EntityInterface;
import com.iot.platform.Interface.System.EntityPermissionInterface;

public interface RoleAccessInterface {
    
    AccessInterface getReadAccess(EntityPermissionInterface perm, ServerActionEnum serverActionEnum, SystemUser systemUser);

    Boolean getWriteAccess(EntityPermissionInterface perm, ServerActionEnum serverActionEnum, SystemUser systemUser, EntityInterface oldEntity);

    public default Boolean getWriteAccess(EntityPermissionInterface perm, ServerActionEnum actionType, SystemUser systemUser) {
        return getWriteAccess(perm, actionType, systemUser, null);
    }

}