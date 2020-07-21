package com.iot.platform.Interface.Repository;

import java.util.List;
import java.util.stream.Collectors;

import com.iot.platform.Core.Response.ResponseData;
import com.iot.platform.Interface.Entity.EntityInterface;

public interface SaveMultipleRepositoryInterface<T extends EntityInterface> extends RestfulRepositoryInterface<T> {

    public default ResponseData<List<T>> save(List<T> entities) {
        return ResponseData.success(entities.stream().map(entity -> {
            if (entity.isNewItem()) {
                this.create(entity);
            } else {
                this.update(entity.getId(), entity);
            }
            return entity;
        }).collect(Collectors.toList()));
    }

}