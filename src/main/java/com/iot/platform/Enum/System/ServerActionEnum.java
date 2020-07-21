package com.iot.platform.Enum.System;

import com.iot.platform.Interface.Enum.EnumInterface;

import lombok.Getter;

@Getter
public enum ServerActionEnum implements EnumInterface {

    CREATE(1l), READ(2l), READ_ALL(6l), UPDATE(10l), UPDATE_ALL(30l), DELETE(42l), DELETE_ALL(62l);

    private Long data;

    private ServerActionEnum(Long data) {
        this.data = data;
    }

}