package com.iot.platform.Controller.Private;

import com.iot.platform.Const.SystemConst;
import com.iot.platform.Entity.UserEntity;
import com.iot.platform.Interface.Controller.DownloadControllerInterface;
import com.iot.platform.Interface.Controller.UserControllerInterface;
import com.iot.platform.Interface.Service.AsyncServiceInterface;
import com.iot.platform.Interface.Service.FileServiceInterface;
import com.iot.platform.Provider.JwtProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;

@RestController
@RequestMapping(SystemConst.ApiPrefix + SystemConst.UserUrl)
public class UserController extends BasePrivateController<UserEntity>
        implements DownloadControllerInterface, UserControllerInterface<UserEntity> {

    @Autowired
    @Getter
    protected AsyncServiceInterface asyncService;

    @Autowired
    @Getter
    protected FileServiceInterface fileService;

    @Autowired
    @Getter
    protected JwtProvider jwtProvider;

}