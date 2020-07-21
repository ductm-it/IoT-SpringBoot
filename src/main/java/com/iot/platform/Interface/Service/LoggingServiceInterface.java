package com.iot.platform.Interface.Service;

import java.util.List;

public interface LoggingServiceInterface {

    List<String> error(Throwable ex);

}