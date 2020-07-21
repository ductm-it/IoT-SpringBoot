package com.iot.platform.Interface.Controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.iot.platform.Core.Response.ResponseData;
import com.iot.platform.Interface.Entity.EntityInterface;
import com.iot.platform.Interface.Repository.SaveMultipleRepositoryInterface;
import com.iot.platform.Validator.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.ApiModelProperty;

public interface SaveMultipleWithAuthControllerInterface<T extends EntityInterface> extends ControllerInterface {

    public <U extends SaveMultipleRepositoryInterface<T>> U getRepository();

    @Transactional
    @RequestMapping(value = "save-multiple", method = RequestMethod.POST)
    @ApiModelProperty(value = "Save multiple")
    public default ResponseEntity<ResponseData<List<T>>> save(@Valid @RequestBody @NotNull List<T> entities)
            throws Exception {
        ResponseData<List<T>> responseData = this.getRepository().save(entities);
        if (responseData.getStatus() == false) {
            this.getRepository().clearTransaction();
        }
        return ResponseDataEntity(responseData);
    }

}