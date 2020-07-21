package com.iot.platform.Interface.Controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.iot.platform.Core.Request.RequestLogin;
import com.iot.platform.Core.Response.ResponseData;
import com.iot.platform.Core.Response.ResponseToken;
import com.iot.platform.Core.System.PasswordData;
import com.iot.platform.Core.System.SystemUser;
import com.iot.platform.Interface.Entity.UserInterface;
import com.iot.platform.Interface.Repository.UserRepositoryInterface;
import com.iot.platform.Interface.Service.UserServiceInterface;
import com.iot.platform.Provider.JwtProvider;
import com.iot.platform.Validator.FileSize;
import com.iot.platform.Validator.MimeType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

public interface UserControllerInterface<T extends UserInterface>
        extends RestfulWithAuthControllerInterface<T>, UploadControllerInterface {

    JwtProvider getJwtProvider();

    public default <U extends UserRepositoryInterface<T>> U getUserRepository() {
        return this.getRepository();
    }

    UserServiceInterface getUserService();

    @RequestMapping(method = RequestMethod.POST, value = "/signin", produces = "application/json")
    @ApiOperation(value = "Login to system")
    public default ResponseEntity<ResponseData<ResponseToken>> signIn(@Valid @RequestBody RequestLogin requestLogin)
            throws Exception {
        ResponseData<T> responseData = this.getUserRepository().login(requestLogin.getUsername(),
                requestLogin.getPassword());
        if (responseData.getStatus()) {
            T user = responseData.getData();
            final String token = this.getJwtProvider().createToken(user.toSystemUser());
            return ResponseDataEntity(ResponseData.success(new ResponseToken(token)));
        }
        throw new Exception("Invalid credential");
    }

    @Transactional
    @ApiOperation(value = "Create new account")
    @RequestMapping(method = RequestMethod.POST, value = "/signup", produces = "application/json")
    public default ResponseEntity<ResponseData<T>> signUp(@Valid @RequestBody T userEntity) throws Exception {
        ResponseData<T> responseData = this.getUserRepository().signup(userEntity);
        return ResponseDataEntity(responseData);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile", produces = "application/json")
    @ApiOperation(value = "Get current user profile")
    public default ResponseEntity<ResponseData<T>> getProfile() throws Exception {
        SystemUser systemUser = this.getUserService().getLoggedInUser();
        ResponseData<T> responseData = this.getUserRepository().read(systemUser.getId());
        return ResponseDataEntity(responseData);
    }

    @Transactional
    @ApiOperation(value = "Update user account (not include password)")
    @RequestMapping(method = RequestMethod.POST, value = "/update-profile", produces = "application/json")
    public default ResponseEntity<ResponseData<String>> updateProfile(@Valid @RequestBody T newUserEntity)
            throws Exception {
        SystemUser systemUser = this.getUserService().getLoggedInUser();
        ResponseData<T> responseData = this.getUserRepository().updateProfile(newUserEntity, systemUser);
        return ResponseDataEntity(
                ResponseData.success(this.getJwtProvider().createToken(responseData.getData().toSystemUser())));
    }

    @Transactional
    @ApiOperation(value = "Update current user's password")
    @RequestMapping(method = RequestMethod.POST, value = "/update-password", produces = "application/json")
    public default ResponseEntity<ResponseData<Boolean>> updatePassword(@Valid @RequestBody PasswordData passwordData)
            throws Exception {
        SystemUser systemUser = this.getUserService().getLoggedInUser();
        if (passwordData.isValid()) {
            ResponseData<Boolean> responseData = this.getUserRepository().updatePassword(systemUser.getId(),
                    passwordData.getPassword());
            return ResponseDataEntity(responseData);
        }
        return ResponseDataEntity(ResponseData.error("Your password is not valid"));
    }

    @Transactional
    @ApiOperation(value = "Send reset password token")
    @RequestMapping(method = RequestMethod.POST, value = "/reset-password-token", produces = "application/json")
    public default ResponseEntity<ResponseData<Boolean>> sendResetPasswordToken(@Valid @RequestBody String emailAddress)
            throws Exception {
        ResponseData<Boolean> responseData = this.getUserRepository().sendResetPasswordToken(emailAddress);
        return ResponseDataEntity(responseData);
    }

    @Transactional
    @RequestMapping(value = "/update-avatar", method = RequestMethod.POST)
    @ApiModelProperty(value = "Upload an image")
    public default ResponseEntity<ResponseData<String>> updateAvatar(@FileSize(param = 1024l * 1024) @MimeType(param = {
            "image/jpeg" }) @RequestParam("file") MultipartFile file) throws Exception {
        SystemUser systemUser = this.getUserService().getLoggedInUser();
        ResponseEntity<ResponseData<String>> responseData = this.uploadFile(file);
        return ResponseDataEntity(
                this.getUserRepository().updateAvatar(systemUser.getId(), responseData.getBody().getData()));
    }

    @Transactional
    @ApiOperation(value = "Update password by token")
    @RequestMapping(method = RequestMethod.POST, value = "/update-password-by-token", produces = "application/json")
    public default ResponseEntity<ResponseData<Boolean>> updatePassowordByToken(
            @Valid @RequestBody PasswordData passwordData, String emailAddress, String token) throws Exception {
        if (passwordData.isValid()) {
            ResponseData<Boolean> responseData = this.getUserRepository().updatePasswordByToken(emailAddress,
                    passwordData.getPassword(), token);
            return ResponseDataEntity(responseData);
        }
        return ResponseDataEntity(ResponseData.error("Your password is not valid"));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/renew-token", produces = "application/json")
    @ApiOperation(value = "Renew token", authorizations = {})
    public default ResponseEntity<ResponseData<ResponseToken>> renewToken() throws Exception {
        SystemUser systemUser = this.getUserService().getLoggedInUser();
        final String token = this.getJwtProvider().createToken(systemUser);
        return ResponseDataEntity(ResponseData.success(new ResponseToken(token)));
    }

}