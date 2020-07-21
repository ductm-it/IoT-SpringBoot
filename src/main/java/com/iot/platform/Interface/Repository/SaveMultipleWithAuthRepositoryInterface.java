package com.iot.platform.Interface.Repository;

import java.util.List;
import java.util.stream.Collectors;

import com.iot.platform.Core.Response.ResponseData;
import com.iot.platform.Core.System.SystemUser;
import com.iot.platform.Enum.System.ServerActionEnum;
import com.iot.platform.Interface.Entity.EntityInterface;

public interface SaveMultipleWithAuthRepositoryInterface<T extends EntityInterface>
        extends RestfulWithAuthRepositoryInterface<T> {

    public default ResponseData<List<T>> save(List<T> entities, SystemUser systemUser) {
        if (systemUser.getRoleAccess().getWriteAccess(this.getEntityPerm(), ServerActionEnum.CREATE, systemUser)) {
            return ResponseData.success(entities.stream().map(entity -> {
                if (entity.isNewItem()) {
                    this.create(entity, systemUser);
                } else {
                    this.update(entity.getId(), entity, systemUser);
                }
                return entity;
            }).collect(Collectors.toList()));
        }
        return ResponseData.forbidden();
    }

}