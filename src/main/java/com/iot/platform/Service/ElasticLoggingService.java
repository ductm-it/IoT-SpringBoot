package com.iot.platform.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.iot.platform.Core.System.ExceptionInfo;
import com.iot.platform.Interface.Service.LoggingServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.Getter;

@Service
@Primary
public class ElasticLoggingService implements LoggingServiceInterface {

    @Getter
    @Value("${spring.application.name:IoTPlatform}")
    private String appName;

    @Autowired
    protected ElasticService elasticService;

    @Value("${elasticsearch.index.error:iot-platform}")
    private String index;

    @Getter
    private Queue<ExceptionInfo> queue = new LinkedList<>();

    public List<String> error(Throwable object) {
        ExceptionInfo exceptionInfo = new ExceptionInfo(object, this.getAppName());
        this.getQueue().add(exceptionInfo);
        return exceptionInfo.toMessages();
    }

    @Scheduled(fixedDelay = 1000)
    public void sendToServer() {
        List<ExceptionInfo> list = this.getList(100);
        if (list.size() > 0) {
            try {
                this.elasticService.bulkIndex(this.index, list);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                this.queue.addAll(list);
                this.error(e);
            }
        }
    }

    public List<ExceptionInfo> getList(Integer size) {
        List<ExceptionInfo> list = new ArrayList<>();
        while (size >= 0) {
            if (this.getQueue().size() > 0) {
                list.add(this.getQueue().remove());
            }
            size -= 1;
        }
        return list;
    }
    
}