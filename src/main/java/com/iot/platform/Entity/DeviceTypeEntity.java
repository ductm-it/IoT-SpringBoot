package com.iot.platform.Entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iot.platform.Const.SystemConst;
import com.iot.platform.Core.System.DateRange;
import com.iot.platform.Interface.Entity.DateRangeInterface;
import com.iot.platform.Interface.System.EntityPermissionInterface;
import com.iot.platform.Interface.UI.InputDateRangeInterface;
import com.iot.platform.Interface.UI.InputStringInterface;
import com.iot.platform.Sequence.BaseSequence;
import com.iot.platform.Validator.NotNull;
import com.iot.platform.Validator.Size;
import com.iot.platform.Validator.SqlId;

import org.hibernate.annotations.Where;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = SystemConst.DeviceTypeTable)
@Where(clause = "is_delete='False'")
@EntityPermissionInterface(permissions = { SystemConst.DeviceTypeTable })
@com.iot.platform.Validator.DateRange
public class DeviceTypeEntity extends BaseEntity implements DateRangeInterface {

    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    @SqlId
    @Size(max = 10)
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "Seq_Device_Type")
    @org.hibernate.annotations.GenericGenerator(name = "Seq_Device_Type", strategy = "com.iot.platform.Sequence.DefaultSequence", parameters = {
            @org.hibernate.annotations.Parameter(name = BaseSequence.VALUE_PREFIX_PARAMETER, value = "D"),
            @org.hibernate.annotations.Parameter(name = BaseSequence.NUMBER_FORMAT_PARAMETER, value = "%09d") })
    private String deviceTypeId;

    @InputStringInterface
    @Size(max = 10)
    @NotNull
    private String deviceTypeName;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    Date fromDate;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    Date toDate;

    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    @InputDateRangeInterface
    @Transient
    private DateRange dateRange;

    @Override
    public String getId() {
        return this.getDeviceTypeId();
    }

    @Override
    public void setId(String id) {
        this.setDeviceTypeId(id);
    }

    @Override
    public String getDisplay() {
        return this.getDeviceTypeName();
    }

}