package com.iot.platform.Controller;

import com.iot.platform.Interface.Controller.CmsControllerInterface;
import com.iot.platform.Interface.Controller.RestfulWithAuthControllerInterface;
import com.iot.platform.Interface.Entity.EntityInterface;
import com.iot.platform.Interface.Service.LoggingServiceInterface;
import com.iot.platform.Interface.Service.UserServiceInterface;
import com.iot.platform.Repository.BaseRepository;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;

public class BaseController<T extends EntityInterface>
        implements RestfulWithAuthControllerInterface<T>, CmsControllerInterface<T> {

    @Autowired
    @Getter
    protected BaseRepository<T> repository;

    @Autowired
    @Getter
    protected LoggingServiceInterface loggingService;

    @Autowired
    @Getter
    protected UserServiceInterface userService;

}