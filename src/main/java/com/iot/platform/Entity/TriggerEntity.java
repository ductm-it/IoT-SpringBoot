package com.iot.platform.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iot.platform.Const.SystemConst;
import com.iot.platform.Interface.System.EntityPermissionInterface;
import com.iot.platform.Interface.UI.InputDecimalInterface;
import com.iot.platform.Interface.UI.InputSelectFromTableInterface;
import com.iot.platform.Interface.UI.InputTextInterface;
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
@Entity(name = SystemConst.TriggerTable)
@Where(clause = "is_delete='False'")
@EntityPermissionInterface(permissions = { SystemConst.TriggerTable })
public class TriggerEntity extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    @SqlId
    @Size(max = 10)
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "Seq_Trigger")
    @org.hibernate.annotations.GenericGenerator(name = "Seq_Trigger", strategy = "com.iot.platform.Sequence.DefaultSequence", parameters = {
            @org.hibernate.annotations.Parameter(name = BaseSequence.VALUE_PREFIX_PARAMETER, value = "TG"),
            @org.hibernate.annotations.Parameter(name = BaseSequence.NUMBER_FORMAT_PARAMETER, value = "%08d") })
    private String triggerId;

    @InputSelectFromTableInterface(table = "variable")
    @SqlId
    @Size(max = 10)
    @NotNull
    private String variableId;

    @InputDecimalInterface
    private Float min;

    @InputDecimalInterface
    private Float max;

    @InputTextInterface
    @Size(max = 255)
    @NotNull
    private String message;

    @Override
    public String getId() {
        return this.getTriggerId();
    }

    @Override
    public void setId(String id) {
        this.setTriggerId(id);
    }

}