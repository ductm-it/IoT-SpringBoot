package com.iot.platform.Repository.System;

import com.iot.platform.Core.Response.ResponseData;
import com.iot.platform.Entity.UserEntity;
import com.iot.platform.Enum.System.RoleEnum;
import com.iot.platform.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SystemRepository {

    @Autowired
    private UserRepository userRepository;

    public ResponseData<UserEntity> init() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("admin");
        userEntity.setPassword("Abc@@123");
        userEntity.setEmailAddress("huutho321@gmail.com");
        userEntity.setFullName("Admin");
        userEntity.setPhoneNumber("0123456789");
        userEntity.setRoleEnum(RoleEnum.ROLE_ADMIN);
        userEntity.setCreatedBy("000000");
        userEntity.setChangedBy("000000");
        this.userRepository.create(userEntity);

        userEntity.setCreatedBy(userEntity.getId());
        userEntity.setChangedBy(userEntity.getId());
        this.userRepository.update(userEntity);

        return ResponseData.success(userEntity);
    }

}