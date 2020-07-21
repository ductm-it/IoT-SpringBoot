package com.iot.platform.Core.Request;

import java.util.List;
import java.util.Map;

import com.iot.platform.Core.Datasource.OrderStatement;
import com.iot.platform.Validator.EntityFieldName;
import com.iot.platform.Validator.FilterValue;
import com.iot.platform.Validator.NotNull;
import com.iot.platform.Validator.SqlId;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RequestFilter {

    @FilterValue
    @ApiModelProperty(example = " ")
    protected String search;

    protected Map<@NotNull @EntityFieldName String, @NotNull List<@NotNull @FilterValue Object>> filter;

    protected List<@SqlId @NotNull String> excludeIds;

    protected List<OrderStatement> orders;

}