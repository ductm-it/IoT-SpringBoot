package com.iot.platform.Interface.Controller;

import com.iot.platform.Core.Response.ResponseData;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ControllerInterface {

    public default <T> ResponseEntity<ResponseData<T>> ResponseDataEntity(final ResponseData<T> responseData,
            final HttpStatus status) {
        return new ResponseEntity<>(responseData, status);
    }

    public default <T> ResponseEntity<ResponseData<T>> ResponseDataEntity(final ResponseData<T> responseData) {
        return ResponseDataEntity(responseData, responseData.getStatusCode());
    }

    public default String getPrefix() {
        return this.getClass().getSimpleName().toLowerCase().replace("controller", "");
    }

}