package com.iot.platform.Controller.Private;

import com.iot.platform.Const.SystemConst;
import com.iot.platform.Entity.DeviceTypeEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SystemConst.ApiPrefix + SystemConst.DeviceTypeUrl)
public class DeviceTypeController extends BasePrivateController<DeviceTypeEntity> {

}