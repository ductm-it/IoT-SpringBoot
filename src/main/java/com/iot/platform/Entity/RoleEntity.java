package com.iot.platform.Entity;

import java.util.Map;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iot.platform.Const.SystemConst;
import com.iot.platform.Converter.MapConverter;
import com.iot.platform.Enum.System.ServerActionEnum;
import com.iot.platform.Interface.Entity.RoleInterface;
import com.iot.platform.Interface.System.EntityPermissionInterface;
import com.iot.platform.Interface.UI.InputStringInterface;
import com.iot.platform.Interface.UI.InputUserRoleInterface;
import com.iot.platform.Sequence.BaseSequence;
import com.iot.platform.Validator.EntityFieldName;
import com.iot.platform.Validator.Max;
import com.iot.platform.Validator.Min;
import com.iot.platform.Validator.NotNull;
import com.iot.platform.Validator.Size;
import com.iot.platform.Validator.SqlId;

import org.hibernate.annotations.Where;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name = SystemConst.RoleTable)
@Where(clause = "is_delete=false")
@EntityPermissionInterface(permissions = { SystemConst.RoleTable })
public class RoleEntity extends BaseEntity implements RoleInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    @SqlId
    @Size(max = 10)
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "Seq_Role")
    @org.hibernate.annotations.GenericGenerator(name = "Seq_Role", strategy = "com.iot.platform.Sequence.DefaultSequence", parameters = {
            @org.hibernate.annotations.Parameter(name = BaseSequence.VALUE_PREFIX_PARAMETER, value = "R"),
            @org.hibernate.annotations.Parameter(name = BaseSequence.NUMBER_FORMAT_PARAMETER, value = "%09d") })
    private String roleId;

    @InputStringInterface
    @Size(max = 50)
    @NotNull
    private String description;

    @InputUserRoleInterface(packageName = "com.iot.platform.Entity", clazz = ServerActionEnum.class)
    @NotNull
    @Convert(converter = MapConverter.class)
    private Map<@EntityFieldName String, @Min(param = 0) @Max(param = 127l) Long> role;

    @Override
    public String getId() {
        return this.getRoleId();
    }

    @Override
    public void setId(String id) {
        this.setRoleId(id);
    }

    @Override
    public String getDisplay() {
        return this.getDescription();
    }

}