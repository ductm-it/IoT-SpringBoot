package com.iot.platform.Controller.Private;

import com.iot.platform.Controller.BaseController;
import com.iot.platform.Interface.Entity.EntityInterface;
import com.iot.platform.Interface.Service.UserServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;

public class BasePrivateController<T extends EntityInterface> extends BaseController<T> {

    @Autowired
    protected UserServiceInterface userService;

}