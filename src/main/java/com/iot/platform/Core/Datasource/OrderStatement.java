package com.iot.platform.Core.Datasource;

import com.iot.platform.Validator.ListValue;
import com.iot.platform.Validator.NotBlank;
import com.iot.platform.Validator.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatement {

    public static OrderStatement desc(String active) {
        return new OrderStatement(active, "desc");
    }

    public static OrderStatement asc(String active) {
        return new OrderStatement(active, "asc");
    }

    @NotNull
    @NotBlank
    @ApiModelProperty(example = "id")
    private String active;

    @ListValue(param = { "desc", "asc" })
    @NotNull
    @ApiModelProperty(example = "desc")
    private String direction;

    @Override
    public String toString() {
        return this.getActive() + " " + this.getDirection();
    }

}