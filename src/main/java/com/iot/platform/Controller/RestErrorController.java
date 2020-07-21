package com.iot.platform.Controller;

import javax.servlet.http.HttpServletRequest;

import com.iot.platform.Core.Response.ResponseData;
import com.iot.platform.Interface.Controller.ErrorControllerInterface;
import com.iot.platform.Interface.Service.LoggingServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class RestErrorController implements ErrorController, ErrorControllerInterface {

    @Autowired
    @Getter
    private LoggingServiceInterface loggingService;

    @RequestMapping("/error")
    public ResponseEntity<ResponseData<Object>> error(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        if (exception == null) {
            return ResponseDataEntity(ResponseData.fallbackError(HttpStatus.valueOf(statusCode)));
        }
        return ResponseDataEntity(ResponseData.fallbackError(HttpStatus.valueOf(statusCode),
                this.getLoggingService().error(exception)));
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}