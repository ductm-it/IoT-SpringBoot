package com.iot.platform.Interface.Controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.iot.platform.Core.Response.ResponseData;
import com.iot.platform.Interface.Entity.EntityInterface;
import com.iot.platform.Interface.Repository.RestfulWithAuthRepositoryInterface;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.ApiModelProperty;

public interface RestfulControllerInterface<T extends EntityInterface> extends ErrorControllerInterface {

    public <U extends RestfulWithAuthRepositoryInterface<T>> U getRepository();

    @Transactional
    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ApiModelProperty(value = "Create new record")
    public default ResponseEntity<ResponseData<T>> create(@Valid @RequestBody T entity) throws Exception {
        ResponseData<T> responseData = this.getRepository().create(entity);
        if (responseData.getStatus() == false) {
            this.getRepository().clearTransaction();
        }
        return ResponseDataEntity(responseData);
    }

    @RequestMapping(value = "read/{id}", method = RequestMethod.GET)
    @ApiModelProperty(value = "Read the record by ID")
    public default ResponseEntity<ResponseData<T>> read(@Valid @PathVariable String id) throws Exception {
        return ResponseDataEntity(this.getRepository().read(id));
    }

    @Transactional
    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    @ApiModelProperty(value = "Update the existing record by ID")
    public default ResponseEntity<ResponseData<T>> update(@Valid @PathVariable String id, @Valid @RequestBody T entity)
            throws Exception {
        ResponseData<T> responseData = this.getRepository().update(id, entity);
        if (responseData.getStatus() == false) {
            this.getRepository().clearTransaction();
        }
        return ResponseDataEntity(responseData);
    }

    @Transactional
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ApiModelProperty(value = "Create new record")
    public default ResponseEntity<ResponseData<T>> delete(@Valid @PathVariable String id) throws Exception {
        ResponseData<T> responseData = this.getRepository().delete(id);
        if (responseData.getStatus() == false) {
            this.getRepository().clearTransaction();
        }
        return ResponseDataEntity(responseData);
    }

}