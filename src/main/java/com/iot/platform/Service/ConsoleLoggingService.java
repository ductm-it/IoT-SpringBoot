package com.iot.platform.Service;

import java.util.Arrays;
import java.util.List;

import com.iot.platform.Interface.Service.LoggingServiceInterface;

import org.springframework.stereotype.Service;

@Service
public class ConsoleLoggingService implements LoggingServiceInterface {

    @Override
    public List<String> error(Throwable ex) {
        System.out.println(ex.getMessage());
        return Arrays.asList(ex.getMessage());
    }

}