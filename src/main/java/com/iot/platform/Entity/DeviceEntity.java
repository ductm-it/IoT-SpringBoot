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
@Entity(name = SystemConst.DeviceTable)
@Where(clause = "is_delete='False'")
@EntityPermissionInterface(permissions = { SystemConst.DeviceTable, "Master" })
public class DeviceEntity extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    @SqlId
    @Size(max = 10)
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "Seq_Device")
    @org.hibernate.annotations.GenericGenerator(name = "Seq_Device", strategy = "com.iot.platform.Sequence.DefaultSequence", parameters = {
            @org.hibernate.annotations.Parameter(name = BaseSequence.VALUE_PREFIX_PARAMETER, value = "D"),
            @org.hibernate.annotations.Parameter(name = BaseSequence.NUMBER_FORMAT_PARAMETER, value = "%09d") })
    private String deviceId;

    @InputStringInterface
    @Size(max = 10)
    @NotNull
    private String deviceName;

    @InputSelectFromTableInterface(table = "device-type")
    @Size(max = 10)
    @NotNull
    private String deviceTypeId;

    @Override
    public String getId() {
        return this.getDeviceId();
    }

    @Override
    public void setId(String id) {
        this.setDeviceId(id);
    }

    @Override
    public String getDisplay() {
        return this.getDeviceName();
    }

}