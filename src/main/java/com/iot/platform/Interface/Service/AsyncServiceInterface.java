package com.iot.platform.Interface.Service;

import javax.transaction.Transactional;

import com.iot.platform.Interface.Function.AsyncFunctionInterface;

public interface AsyncServiceInterface {

    @Transactional
    void withTransaction(AsyncFunctionInterface callback);

    void withoutTransaction(AsyncFunctionInterface callback);

}