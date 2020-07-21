package com.iot.platform.Interface.Controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.iot.platform.Core.Response.ResponseData;
import com.iot.platform.Core.System.SystemUser;
import com.iot.platform.Interface.Entity.EntityInterface;
import com.iot.platform.Interface.Repository.SaveMultipleWithAuthRepositoryInterface;
import com.iot.platform.Interface.Service.UserServiceInterface;
import com.iot.platform.Validator.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.ApiModelProperty;

public interface SaveMultipleControllerInterface<T extends EntityInterface> extends ControllerInterface {

    public UserServiceInterface getUserService();

    public <U extends SaveMultipleWithAuthRepositoryInterface<T>> U getRepository();

    @Transactional
    @RequestMapping(value = "save-multiple", method = RequestMethod.POST)
    @ApiModelProperty(value = "Save multiple")
    public default ResponseEntity<ResponseData<List<T>>> create(@Valid @RequestBody @NotNull List<T> entities)
            throws Exception {
        SystemUser systemUser = this.getUserService().getLoggedInUser();
        ResponseData<List<T>> responseData = this.getRepository().save(entities, systemUser);
        if (responseData.getStatus() == false) {
            this.getRepository().clearTransaction();
        }
        return ResponseDataEntity(responseData);
    }

}