package com.iot.platform.Repository;

import java.util.Arrays;
import java.util.Optional;

import com.iot.platform.Core.Datasource.HqlQueryBuilder;
import com.iot.platform.Core.Datasource.WhereStatement;
import com.iot.platform.Core.Response.ResponseData;
import com.iot.platform.Core.System.Email;
import com.iot.platform.Core.System.SystemUser;
import com.iot.platform.Entity.UserEntity;
import com.iot.platform.Enum.System.RoleEnum;
import com.iot.platform.Interface.Repository.UserRepositoryInterface;
import com.iot.platform.Interface.Service.AsyncServiceInterface;
import com.iot.platform.Service.MailService;
import com.iot.platform.Util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.Getter;

@Repository
public class UserRepository extends BaseRepository<UserEntity> implements UserRepositoryInterface<UserEntity> {

    @Autowired
    @Getter
    public MailService mailService;

    @Autowired
    @Getter
    protected AsyncServiceInterface asyncService;

    protected static SystemUser AdminEntity = null;

    public SystemUser getAdminEntity() {
        if (UserRepository.AdminEntity == null) {
            HqlQueryBuilder hqlQueryBuilder = new HqlQueryBuilder();
            hqlQueryBuilder.getWhereStatements().add(new WhereStatement("roleEnum", "=", RoleEnum.ROLE_ADMIN));
            Optional<UserEntity> optional = this.runHql(hqlQueryBuilder).getResultStream().findFirst();
            if (optional.isPresent()) {
                SystemUser adminUser = optional.get().toSystemUser();
                UserRepository.AdminEntity = adminUser;
            } else {
                return null;
            }
        }
        return UserRepository.AdminEntity;
    }

    @Override
    public void setAdminEntity(SystemUser systemUser) {
        if (RoleEnum.ROLE_ADMIN.equals(systemUser.getRoleEnum())) {
            UserRepository.AdminEntity = systemUser;
        }
    }

    @Override
    public ResponseData<UserEntity> update(String id, UserEntity newDoc, SystemUser systemUser) {
        ResponseData<UserEntity> responseData = this.read(newDoc.getId(), systemUser);
        if (!responseData.getStatus())
            return ResponseData.notFound();
        UserEntity oldDoc = responseData.getData();
        this.initUpdate(newDoc, oldDoc, systemUser);
        return ResponseData.success(this.update(newDoc));
    }

    @Override
    public ResponseData<UserEntity> create(UserEntity entity, SystemUser systemUser) {
        final String password = StringUtil.randomString(8);
        entity.setPassword(password);
        ResponseData<UserEntity> responseData = super.create(entity, systemUser);
        this.asyncService.withoutTransaction(() -> {
            this.mailService.sendMail(new Email() {
                {
                    this.setBody("<p>Hi " + entity.getFullName() + ": </p><br><p>username: <b>" + entity.getUsername()
                            + "</b></p><p>password: <b>" + password + "</b></p>");
                    this.setHtml(true);
                    this.setSendToEmail(Arrays.asList(entity.getEmailAddress()));
                    this.setSubject("[IoT-Platform] Welcome to IoTHub System");
                }
            });
        });
        return responseData;
    }

}