package com.iot.platform.Sequence;

import java.io.Serializable;

public class DefaultSequence extends BaseSequence<Object> {

    @Override
    public Serializable generate(Object object, Serializable currentCounter) {
        return valuePrefix + String.format(numberFormat, currentCounter);
    }

}