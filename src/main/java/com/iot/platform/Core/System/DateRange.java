package com.iot.platform.Core.System;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.iot.platform.Const.SystemConst;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DateRange {

    @JsonFormat(shape = Shape.STRING, pattern = SystemConst.DateFormat)
    @ApiModelProperty(example = SystemConst.DefaultDate)
    Date fromDate;

    @JsonFormat(shape = Shape.STRING, pattern = SystemConst.DateFormat)
    @ApiModelProperty(example = SystemConst.DefaultDate)
    Date toDate;

}