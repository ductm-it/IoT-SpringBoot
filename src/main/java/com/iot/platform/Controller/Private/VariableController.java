package com.iot.platform.Controller.Private;

import com.iot.platform.Const.SystemConst;
import com.iot.platform.Entity.VariableEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SystemConst.ApiPrefix + SystemConst.VariableUrl)
public class VariableController extends BasePrivateController<VariableEntity> {

}