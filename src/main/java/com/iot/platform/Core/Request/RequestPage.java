package com.iot.platform.Core.Request;

import com.iot.platform.Validator.Max;
import com.iot.platform.Validator.Min;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RequestPage extends RequestFilter {

    @ApiModelProperty(example = "10")
    @Max(param = 100l)
    @Min(param = 1l)
    Integer pageSize;

    @ApiModelProperty(example = "1")
    @Min(param = 1l)
    Integer pageIndex;

    @ApiModelProperty(hidden = true)
    public Integer getFirstResult() {
        return (this.getPageIndex() - 1) * this.getPageSize();
    }

    @ApiModelProperty(hidden = true)
    public Integer getMaxResults() {
        return this.getPageSize();
    }

}