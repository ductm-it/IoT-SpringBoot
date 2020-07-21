package com.iot.platform.Core.System;

import java.util.Arrays;
import java.util.Map;

import com.iot.platform.Core.Datasource.HqlQueryBuilder;
import com.iot.platform.Core.Datasource.WhereStatement;
import com.iot.platform.Enum.System.RoleEnum;
import com.iot.platform.Enum.System.ServerActionEnum;
import com.iot.platform.Interface.Core.AccessInterface;
import com.iot.platform.Interface.Core.RoleAccessInterface;
import com.iot.platform.Interface.Entity.EntityInterface;
import com.iot.platform.Interface.System.EntityPermissionInterface;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RoleAccess implements RoleAccessInterface {

    private Map<String, Long> map;

    protected Long getPermissionValue(EntityPermissionInterface perm) {
        if (this.map == null || perm.permissions() == null)
            return 0l;
        return Arrays.asList(perm.permissions()).stream().map(t -> {
            if (this.map.containsKey(t))
                return this.map.get(t);
            return 0l;
        }).reduce(0l, (a, b) -> {
            return a | b;
        });
    }

    @Override
    public AccessInterface getReadAccess(EntityPermissionInterface perm, ServerActionEnum serverActionEnum,
            SystemUser systemUser) {
        AccessInterface access = new Access();
        if (serverActionEnum.equals(ServerActionEnum.READ)) {
            if (RoleEnum.ROLE_ADMIN.equals(systemUser.getRoleEnum())) {
                HqlQueryBuilder hqlQueryBuilder = new HqlQueryBuilder();
                access.setCanAccess(true);
                access.setHqlQueryBuilder(hqlQueryBuilder);
                return access;
            }
        }
        Long val = this.getPermissionValue(perm);
        if (val != 0) {
            Long fullAction = serverActionEnum.getData() << 1;
            Boolean fullPerm = ((val & fullAction) == fullAction);
            if (fullPerm || (val & serverActionEnum.getData()) == serverActionEnum.getData()) {
                HqlQueryBuilder hqlQueryBuilder = new HqlQueryBuilder();

                // List<OwnerCallback> ownerCallbacks = HqlQueryBuilder.getCallbacks(perm.permissions());
                // if (ownerCallbacks != null) {
                //     ownerCallbacks.forEach(
                //             ownerCallback -> ownerCallback.getReadCallback().update(hqlQueryBuilder, systemUser));
                // }

                access.setHqlQueryBuilder(hqlQueryBuilder);
                access.setCanAccess(true);
                if (fullPerm) {
                    return access;
                }
                hqlQueryBuilder.getWhereStatements().add(WhereStatement.equal("createdBy", systemUser.getId()));
                return access;
            }
        }

        return access;
    }

    @Override
    public Boolean getWriteAccess(EntityPermissionInterface perm, ServerActionEnum serverActionEnum,
            SystemUser systemUser, EntityInterface oldEntity) {
        if (RoleEnum.ROLE_ADMIN.equals(systemUser.getRoleEnum())) {
            return true;
        }

        // List<OwnerCallback> ownerCallbacks =
        // HqlQueryBuilder.getCallbacks(perm.table());
        // if (ownerCallbacks != null) {
        // Boolean flag = false;
        // for (OwnerCallback ownerCallback : ownerCallbacks) {
        // if (ownerCallback.getWriteCallback().valid(doc, systemUser) == true) {
        // flag = true;
        // break;
        // }
        // }
        // if (flag == false)
        // return false;
        // }

        Long val = this.getPermissionValue(perm);
        if (val != 0) {
            Long fullAction = serverActionEnum.getData() << 1;
            Boolean fullPerm = ((val & fullAction) == fullAction);
            if (fullPerm || (val & serverActionEnum.getData()) == serverActionEnum.getData()) {
                if (fullPerm)
                    return true;
                if (ServerActionEnum.CREATE.equals(serverActionEnum)) {
                    return true;
                }
                if (oldEntity != null && systemUser.getId().equals(oldEntity.getCreatedBy())) {
                    return true;
                }
            }
        }
        return false;
    }

}