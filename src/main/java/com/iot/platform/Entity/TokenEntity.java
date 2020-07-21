package com.iot.platform.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iot.platform.Const.SystemConst;
import com.iot.platform.Enum.TokenTypeEnum;
import com.iot.platform.Interface.System.EntityPermissionInterface;
import com.iot.platform.Interface.UI.InputSelectFromEnumInterface;
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
@Entity(name = SystemConst.TokenTable)
@Where(clause = "is_delete='False'")
@EntityPermissionInterface(permissions = { SystemConst.TokenTable })
public class TokenEntity extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    @SqlId
    @Size(max = 10)
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "Seq_Token")
    @org.hibernate.annotations.GenericGenerator(name = "Seq_Token", strategy = "com.iot.platform.Sequence.DefaultSequence", parameters = {
            @org.hibernate.annotations.Parameter(name = BaseSequence.VALUE_PREFIX_PARAMETER, value = "T"),
            @org.hibernate.annotations.Parameter(name = BaseSequence.NUMBER_FORMAT_PARAMETER, value = "%09d") })
    private String tokenId;

    @InputStringInterface
    @Size(max = 10)
    @NotNull
    private String token;

    @InputSelectFromEnumInterface(clazz = TokenTypeEnum.class)
    @Size(max = 10)
    @NotNull
    private TokenTypeEnum type;

    @InputSelectFromTableInterface(table = "device")
    @Size(max = 10)
    @NotNull
    private String deviceId;

    @Override
    public String getId() {
        return this.getTokenId();
    }

    @Override
    public void setId(String id) {
        this.setTokenId(id);
    }

}