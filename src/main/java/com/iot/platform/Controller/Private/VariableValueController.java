package com.iot.platform.Controller.Private;

import com.iot.platform.Const.SystemConst;
import com.iot.platform.Entity.VariableValueEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SystemConst.ApiPrefix + SystemConst.VariableValueUrl)
public class VariableValueController extends BasePrivateController<VariableValueEntity> {

}