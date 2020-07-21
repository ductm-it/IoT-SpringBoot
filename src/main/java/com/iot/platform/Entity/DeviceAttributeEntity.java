package com.iot.platform.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iot.platform.Const.SystemConst;
import com.iot.platform.Interface.System.EntityPermissionInterface;
import com.iot.platform.Interface.UI.InputSelectFromTableInterface;
import com.iot.platform.Interface.UI.InputStringInterface;
import com.iot.platform.Sequence.BaseSequence;
import com.iot.platform.Validator.NotNull;
import com.iot.platform.Validator.Size;
import com.iot.platform.Validator.SqlId;

import org.hibernate.annotations.Where;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = SystemConst.DeviceAttributeTable)
@Where(clause = "is_delete='False'")
@EntityPermissionInterface(permissions = { SystemConst.DeviceAttributeTable, "Master" })
public class DeviceAttributeEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    @SqlId
    @Size(max = 10)
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "Seq_DeviceAttribute")
    @org.hibernate.annotations.GenericGenerator(name = "Seq_DeviceAttribute", strategy = "com.iot.platform.Sequence.DefaultSequence", parameters = {
            @org.hibernate.annotations.Parameter(name = BaseSequence.VALUE_PREFIX_PARAMETER, value = "DA"),
            @org.hibernate.annotations.Parameter(name = BaseSequence.NUMBER_FORMAT_PARAMETER, value = "%08d") })
    private String deviceAttributeId;

    @InputSelectFromTableInterface(table = "device")
    @Size(max = 10)
    @NotNull
    private String deviceId;

    @InputStringInterface
    @Size(max = 50)
    @NotNull
    private String name;

    @InputStringInterface
    @Size(max = 20)
    @NotNull
    private String value;

    @Override
    public String getId() {
        return this.getDeviceAttributeId();
    }

    @Override
    public void setId(String id) {
        this.setDeviceAttributeId(id);
    }

    @Override
    public String getDisplay() {
        return this.getName();
    }

}