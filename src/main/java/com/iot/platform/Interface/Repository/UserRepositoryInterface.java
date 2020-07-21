package com.iot.platform.Interface.Repository;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.persistence.TypedQuery;

import com.iot.platform.Const.SystemConst;
import com.iot.platform.Core.Datasource.HqlQueryBuilder;
import com.iot.platform.Core.Datasource.WhereStatement;
import com.iot.platform.Core.Response.ResponseData;
import com.iot.platform.Core.System.Email;
import com.iot.platform.Core.System.SystemUser;
import com.iot.platform.Enum.System.RoleEnum;
import com.iot.platform.Interface.Entity.UserInterface;
import com.iot.platform.Interface.Service.AsyncServiceInterface;
import com.iot.platform.Service.MailService;
import com.iot.platform.Util.StringUtil;

public interface UserRepositoryInterface<T extends UserInterface>
        extends RestfulWithAuthRepositoryInterface<T>, CmsRepositoryInterface<T> {

    public SystemUser getAdminEntity();

    public void setAdminEntity(SystemUser adminEntity);

    public MailService getMailService();

    public AsyncServiceInterface getAsyncService();

    public default void initUpdate(T newEntity, T oldEntity, SystemUser systemUser) {
        RestfulWithAuthRepositoryInterface.super.initUpdate(newEntity, oldEntity, systemUser);
        newEntity.setHash(oldEntity.getHash());
        newEntity.setRoleEnum(oldEntity.getRoleEnum());
    }

    public default ResponseData<Long> countByUsername(String username) {
        HqlQueryBuilder hqlQueryBuilder = new HqlQueryBuilder();
        hqlQueryBuilder.getWhereStatements().clear();
        hqlQueryBuilder.getWhereStatements().add(new WhereStatement(SystemConst.Username, "=", username.toLowerCase()));
        ResponseData<Long> responseData = CmsRepositoryInterface.super.count(hqlQueryBuilder);
        return responseData;
    }

    public default ResponseData<T> login(String username, String password) {
        HqlQueryBuilder hqlQueryBuilder = new HqlQueryBuilder(this.getTable(), this.getPrimaryKey());
        hqlQueryBuilder.getWhereStatements().add(new WhereStatement(SystemConst.Username, "=", username.toLowerCase()));

        Optional<T> optional = this.runHql(hqlQueryBuilder).getResultStream().findFirst();
        if (!optional.isPresent())
            return ResponseData.forbidden("Username not exists");

        T userEntity = optional.get();
        if (userEntity.verifyHash(password)) {
            return ResponseData.success(userEntity);
        }

        return ResponseData.forbidden("Password is not match");
    }

    public default ResponseData<T> signup(T userEntity) throws Exception {
        if (this.countByUsername(userEntity.getUsername()).getData() > 0) {
            return ResponseData.error("This username already exists");
        }
        final String password = StringUtil.randomString(8);
        SystemUser adminEntity = this.getAdminEntity();

        userEntity.setUsername(userEntity.getUsername().toLowerCase());
        userEntity.setPassword(password);
        if (adminEntity == null) {
            userEntity.setRoleEnum(RoleEnum.ROLE_ADMIN);
        } else {
            userEntity.setRoleEnum(RoleEnum.ROLE_USER);
        }

        ResponseData<T> responseData = this.create(userEntity, adminEntity);
        if (responseData.getStatus()) {
            T insertedUser = responseData.getData();
            if (adminEntity == null) {
                insertedUser.setCreatedBy(insertedUser.getId());
                insertedUser = this.update(insertedUser);
                this.setAdminEntity(insertedUser.toSystemUser());
            }

            this.getAsyncService().withoutTransaction(() -> {
                this.getMailService().sendMail(new Email() {
                    {
                        this.setBody("<p>Hi " + userEntity.getFullName() + ": </p><p>username: <b>"
                                + userEntity.getUsername() + "</b></p><p>password: <b>" + password + "</b></p><br>");
                        this.setHtml(true);
                        this.setSendToEmail(Arrays.asList(userEntity.getEmailAddress()));
                        this.setSubject("[IoT-Platform] Welcome to IoTHub System");
                    }
                });
            });

            return ResponseData.success(insertedUser);
        } else {
            return ResponseData.error("We can not create new account now");
        }
    }

    public default ResponseData<Boolean> updatePassword(String userId, String password) {
        ResponseData<T> responseData = this.read(userId);
        if (!responseData.getStatus())
            return ResponseData.notFound();
        T oldDoc = responseData.getData();
        oldDoc.setPassword(password);
        ((RestfulWithAuthRepositoryInterface<T>) this).update(oldDoc);
        return ResponseData.success(true);
    }

    public default ResponseData<T> updateProfile(T newDoc, SystemUser systemUser) {
        ResponseData<T> responseData = this.read(systemUser.getId());
        if (responseData.getStatus())
            return ResponseData.notFound();

        T oldDoc = responseData.getData();
        this.initUpdate(newDoc, oldDoc, systemUser);
        return ((RestfulWithAuthRepositoryInterface<T>) this).update(systemUser.getId(), oldDoc, systemUser);
    }

    public default Optional<String> getUserRoleCode(String userId) {
        String hql = "SELECT c.roleCode FROM " + this.getTable() + " c WHERE c.userId=:userId";
        TypedQuery<String> typedQuery = this.getEntityManager().createQuery(hql, String.class);
        typedQuery.setParameter("userId", userId);
        return typedQuery.getResultStream().findFirst();
    }

    public default ResponseData<Boolean> sendResetPasswordToken(String emailAddress) {
        HqlQueryBuilder hqlQueryBuilder = new HqlQueryBuilder(this.getTable(), this.getPrimaryKey());
        hqlQueryBuilder.getWhereStatements().add(new WhereStatement("emailAddress", "=", emailAddress.trim()));

        Optional<T> optional = this.runHql(hqlQueryBuilder).getResultStream().findFirst();
        if (!optional.isPresent()) {
            return ResponseData.error("This email address is not exists");
        }
        T userEntity = optional.get();
        String token = StringUtil.randomString(6);
        String hashToken = UserInterface.hashPassword(token);
        this.getAsyncService().withoutTransaction(() -> {
            this.sendToken(token, emailAddress.trim());
        });
        userEntity.setResetPasswordToken(hashToken);
        userEntity.setResetPasswordDate(new Date());
        RestfulWithAuthRepositoryInterface.super.update(userEntity.getId(), userEntity);
        return ResponseData.success(true);
    }

    public default void sendToken(String token, String emailAddress) {
        try {
            this.getMailService().sendMail(new Email() {
                {
                    this.setBody("<h1>Your reset token: " + token + " </h1>");
                    this.setHtml(true);
                    this.setSendToEmail(Arrays.asList(emailAddress));
                    this.setSubject("[IoT-Platform]");
                }
            });
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public default ResponseData<String> updateAvatar(String userId, String imagePath) {
        ResponseData<T> responseData = this.read(userId);
        if (responseData.getStatus()) {
            T doc = responseData.getData();
            doc.setAvatar(imagePath);
            RestfulWithAuthRepositoryInterface.super.update(userId, doc);
            return ResponseData.success(imagePath);
        }
        return ResponseData.notFound();
    }

    public default ResponseData<Boolean> updatePasswordByToken(String emailAddress, String password, String token) {
        HqlQueryBuilder hqlQueryBuilder = new HqlQueryBuilder(this.getTable(), this.getPrimaryKey());
        hqlQueryBuilder.getWhereStatements().add(new WhereStatement("emailAddress", "=", emailAddress));
        Optional<T> optional = this.runHql(hqlQueryBuilder).getResultStream().findFirst();
        if (!optional.isPresent()) {
            return ResponseData.notFound();
        }
        T userEntity = optional.get();
        if (userEntity.verifyHashToken(token)) {
            long diff = (new Date().getTime() - userEntity.getResetPasswordDate().getTime()) / 1000;
            if (userEntity.getResetPasswordToken().equals(token) || diff > 300)
                return ResponseData.error("Token timeout");
            String hash = UserInterface.hashPassword(password);
            userEntity.setHash(hash);
            ((RestfulWithAuthRepositoryInterface<T>) this).update(userEntity);
            return ResponseData.success(true);
        }
        return ResponseData.forbidden("Token is not match");
    }

}