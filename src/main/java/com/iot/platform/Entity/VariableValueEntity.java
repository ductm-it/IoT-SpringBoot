package com.iot.platform.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iot.platform.Const.SystemConst;
import com.iot.platform.Interface.System.EntityPermissionInterface;
import com.iot.platform.Interface.UI.InputDecimalInterface;
import com.iot.platform.Interface.UI.InputSelectFromTableInterface;
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
@Entity(name = SystemConst.VariableValueTable)
@Where(clause = "is_delete='False'")
@EntityPermissionInterface(permissions = { SystemConst.VariableValueTable })
public class VariableValueEntity extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    @Size(max = 10)
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "Seq_Variable_Value")
    @org.hibernate.annotations.GenericGenerator(name = "Seq_Variable_Value", strategy = "com.iot.platform.Sequence.DefaultSequence", parameters = {
            @org.hibernate.annotations.Parameter(name = BaseSequence.VALUE_PREFIX_PARAMETER, value = "VV"),
            @org.hibernate.annotations.Parameter(name = BaseSequence.NUMBER_FORMAT_PARAMETER, value = "%08d") })
    private String variableValueId;

    @InputDecimalInterface
    @NotNull
    private Float value;

    @InputSelectFromTableInterface(table = "variable")
    @Size(max = 10)
    @NotNull
    @SqlId
    private String variableId;

    @Override
    public String getId() {
        return this.getVariableValueId();
    }

    @Override
    public void setId(String id) {
        this.setVariableValueId(id);
    }

}