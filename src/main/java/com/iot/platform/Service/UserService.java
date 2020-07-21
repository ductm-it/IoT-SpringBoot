package com.iot.platform.Service;

import com.iot.platform.Core.Response.ResponseData;
import com.iot.platform.Core.System.RoleAccess;
import com.iot.platform.Interface.Core.RoleAccessInterface;
import com.iot.platform.Interface.Entity.RoleInterface;
import com.iot.platform.Interface.Repository.RoleRepositoryInterface;
import com.iot.platform.Interface.Service.UserServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceInterface {

    protected RoleRepositoryInterface<RoleInterface> roleRepository;

    @Autowired
    @SuppressWarnings("unchecked")
    private void context(ApplicationContext context) {
        this.roleRepository = context.getBean(RoleRepositoryInterface.class);
    }

    @Override
    public RoleAccessInterface getRoleAccess(String userCode) {
        ResponseData<RoleInterface> responseData = this.roleRepository.getCurrentUserRole(userCode);
        if (responseData.getStatus()) {
            RoleInterface roleEntity = responseData.getData();
            if (roleEntity != null) {
                return roleEntity.toRoleAccess();
            }
        }
        return new RoleAccess();
    }

}