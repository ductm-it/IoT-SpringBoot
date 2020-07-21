package com.iot.platform.Interface.Controller;

import com.iot.platform.Core.Response.ResponseData;
import com.iot.platform.Core.System.SystemUser;
import com.iot.platform.Interface.Entity.RoleInterface;
import com.iot.platform.Interface.Repository.RoleRepositoryInterface;
import com.iot.platform.Interface.Service.UserServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.ApiOperation;

public interface RoleControllerInterface<T extends RoleInterface> extends RestfulWithAuthControllerInterface<T> {

    @Autowired
    public UserServiceInterface getUserService();

    public default <U extends RoleRepositoryInterface<T>> U getRoleRepository() {
        return this.getRepository();
    }

    @ApiOperation(value = "Get current user role")
    @RequestMapping(method = RequestMethod.GET, value = "/current-user-role", produces = "application/json")
    public default ResponseEntity<ResponseData<T>> getCurrentUserRole() throws Exception {
        SystemUser systemUser = this.getUserService().getLoggedInUser();
        ResponseData<T> responseData = this.getRoleRepository().getCurrentUserRole(systemUser.getId());
        return ResponseDataEntity(responseData);
    }

}