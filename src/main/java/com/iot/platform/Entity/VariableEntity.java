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
@Entity(name = SystemConst.VariableTable)
@Where(clause = "is_delete='False'")
@EntityPermissionInterface(permissions = { SystemConst.VariableTable })
public class VariableEntity extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    @SqlId
    @Size(max = 10)
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "Seq_Variable")
    @org.hibernate.annotations.GenericGenerator(name = "Seq_Variable", strategy = "com.iot.platform.Sequence.DefaultSequence", parameters = {
            @org.hibernate.annotations.Parameter(name = BaseSequence.VALUE_PREFIX_PARAMETER, value = "V"),
            @org.hibernate.annotations.Parameter(name = BaseSequence.NUMBER_FORMAT_PARAMETER, value = "%09d") })
    private String variableId;

    @InputStringInterface
    @Size(max = 10)
    @NotNull
    private String variableName;

    @InputSelectFromTableInterface(table = "device")
    @Size(max = 10)
    @NotNull
    private String deviceId;

    @Override
    public String getId() {
        return this.getVariableId();
    }

    @Override
    public void setId(String id) {
        this.setVariableId(id);
    }

    @Override
    public String getDisplay() {
        return this.getVariableName();
    }

}