package com.iot.platform.Entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iot.platform.Const.SystemConst;
import com.iot.platform.Core.System.SystemUser;
import com.iot.platform.Enum.System.RoleEnum;
import com.iot.platform.Interface.Config.SortInterface;
import com.iot.platform.Interface.Entity.UserInterface;
import com.iot.platform.Interface.System.EntityPermissionInterface;
import com.iot.platform.Interface.UI.InputEmailInterface;
import com.iot.platform.Interface.UI.InputImageInterface;
import com.iot.platform.Interface.UI.InputPhoneNumberInterface;
import com.iot.platform.Interface.UI.InputSelectFromEnumInterface;
import com.iot.platform.Interface.UI.InputSelectFromTableInterface;
import com.iot.platform.Interface.UI.InputStringInterface;
import com.iot.platform.Interface.UI.InputUsernameInterface;
import com.iot.platform.Sequence.BaseSequence;
import com.iot.platform.Validator.Email;
import com.iot.platform.Validator.NotEmpty;
import com.iot.platform.Validator.NotNull;
import com.iot.platform.Validator.Phone;
import com.iot.platform.Validator.Size;
import com.iot.platform.Validator.SqlId;
import com.iot.platform.Validator.Username;

import org.hibernate.annotations.Where;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = SystemConst.UserTable)
@Where(clause = "is_delete='False'")
@EntityPermissionInterface(permissions = { SystemConst.UserTable })
public class UserEntity extends BaseEntity implements UserInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    @SqlId
    @Size(max = 10)
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "Seq_User")
    @org.hibernate.annotations.GenericGenerator(name = "Seq_User", strategy = "com.iot.platform.Sequence.DefaultSequence", parameters = {
            @org.hibernate.annotations.Parameter(name = BaseSequence.VALUE_PREFIX_PARAMETER, value = "U"),
            @org.hibernate.annotations.Parameter(name = BaseSequence.NUMBER_FORMAT_PARAMETER, value = "%09d") })
    private String userId;

    @InputUsernameInterface
    @Size(max = 20)
    @NotNull
    @NotEmpty
    @Username
    @SortInterface
    private String username;

    @InputEmailInterface
    @Size(max = 50)
    @NotNull
    @Email
    @SortInterface
    private String emailAddress;

    @InputStringInterface
    @Size(max = 100)
    @NotNull
    @SortInterface
    private String fullName;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String hash;

    @InputPhoneNumberInterface
    @Size(max = 10)
    @NotNull
    @Phone
    private String phoneNumber;

    @InputSelectFromTableInterface(table = "role")
    @Size(max = 10)
    @SqlId
    @SortInterface
    private String roleId;

    @InputSelectFromEnumInterface(clazz = RoleEnum.class)
    @Size(max = 10)
    @NotNull
    @Getter(value = AccessLevel.NONE)
    @SortInterface
    private RoleEnum roleEnum;

    public RoleEnum getRoleEnum() {
        if (this.roleEnum == null)
            return RoleEnum.ROLE_USER;
        return this.roleEnum;
    }

    @InputStringInterface
    @Size(max = 100)
    private String address;

    @InputImageInterface
    @Size(max = 100)
    private String avatar;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String resetPasswordToken;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Date resetPasswordDate;

    @Override
    public String getId() {
        return this.getUserId();
    }

    @Override
    public void setId(String id) {
        this.setUserId(id);
    }

    @JsonIgnore
    public void setPassword(String password) {
        this.hash = UserInterface.hashPassword(password);
    }

    public Boolean verifyHash(String password) {
        return UserInterface.verifyHash(password, this.hash);
    }

    public Boolean verifyHashToken(String token) {
        return UserInterface.verifyHash(token, this.resetPasswordToken);
    }

    public SystemUser toSystemUser() {
        SystemUser systemUser = new SystemUser();
        systemUser.setUserCode(this.getUserId());
        systemUser.setRoleEnum(this.getRoleEnum());
        return systemUser;
    }

    @Override
    public String getDisplay() {
        return this.getFullName();
    }

}