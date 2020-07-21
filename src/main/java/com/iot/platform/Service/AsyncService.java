package com.iot.platform.Service;

import java.util.Date;

import com.iot.platform.Interface.Function.AsyncFunctionInterface;
import com.iot.platform.Interface.Service.AsyncServiceInterface;
import com.iot.platform.Interface.Service.LoggingServiceInterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService implements AsyncServiceInterface {

    @Autowired
    private LoggingServiceInterface loggingService;

    private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);

    private void run(AsyncFunctionInterface callback) {
        Date date = new Date();
        logger.info("Start executeAsync");
        try {
            callback.run();
        } catch (Exception ex) {
            this.loggingService.error(ex);
        }
        logger.info("End executeAsync: " + ((new Date()).getTime() - date.getTime()) + " (ms)");
    }

    @Override
    @Async("asyncServiceExecutor")
    public void withTransaction(AsyncFunctionInterface callback) {
        run(callback);
    }

    @Override
    @Async("asyncServiceExecutor")
    public void withoutTransaction(AsyncFunctionInterface callback) {
        run(callback);
    }

}