package com.iot.platform.Controller.Private;

import com.iot.platform.Const.SystemConst;
import com.iot.platform.Entity.RoleEntity;
import com.iot.platform.Interface.Controller.RoleControllerInterface;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SystemConst.ApiPrefix + SystemConst.RoleUrl)
public class RoleController extends BasePrivateController<RoleEntity> implements RoleControllerInterface<RoleEntity> {

}