package com.iot.platform.Controller.Private;

import com.iot.platform.Const.SystemConst;
import com.iot.platform.Entity.DeviceAttributeEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SystemConst.ApiPrefix + SystemConst.DeviceAttributeUrl)
public class DeviceAttributeController extends BasePrivateController<DeviceAttributeEntity> {

}