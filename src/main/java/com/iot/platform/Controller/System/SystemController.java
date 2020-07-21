package com.iot.platform.Controller.System;

import javax.transaction.Transactional;

import com.iot.platform.Core.Response.ResponseData;
import com.iot.platform.Entity.UserEntity;
import com.iot.platform.Interface.Controller.ErrorControllerInterface;
import com.iot.platform.Interface.Service.LoggingServiceInterface;
import com.iot.platform.Repository.System.SystemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;

@RestController
@RequestMapping("api/v1.0/system")
public class SystemController implements ErrorControllerInterface {

    @Autowired
    @Getter
    private LoggingServiceInterface loggingService;

    @Autowired
    private SystemRepository systemRepository;

    @Transactional
    @RequestMapping(value = "init", method = RequestMethod.POST)
    public ResponseEntity<ResponseData<UserEntity>> init() {
        return ResponseDataEntity(this.systemRepository.init());
    }

}