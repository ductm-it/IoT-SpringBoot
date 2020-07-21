package com.iot.platform.Interface.Controller;

import com.iot.platform.Core.Response.ResponseData;
import com.iot.platform.Interface.Service.LoggingServiceInterface;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public interface ErrorControllerInterface extends ControllerInterface {

    LoggingServiceInterface getLoggingService();

    @ExceptionHandler({ Exception.class })
    public default ResponseEntity<ResponseData<String>> handleException(final Exception ex) {
        return ResponseDataEntity(ResponseData.error("Unhandle error, please see in the message list",
                this.getLoggingService().error(ex)));
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public default ResponseEntity<ResponseData<String>> handleException(final MethodArgumentNotValidException ex) {
        return ResponseDataEntity(ResponseData.invalidParam(ex.getBindingResult()));
    }

}