package com.iot.platform.Core.System;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
class ExceptionFileInfo {
    String method;
    Integer line;
    String file;
}

@Getter
@Setter
@ToString
@AllArgsConstructor
class ExceptionStack {
    private List<ExceptionFileInfo> exceptionFileInfos;
    private String message;
    private String exeptionType;
}

@Getter
@Setter
@ToString
public class ExceptionInfo {
    private Date timestamp;
    private List<ExceptionStack> stacks;
    private String appName;

    public ExceptionInfo(Throwable ex, String appName) {
        this.appName = appName;
        this.timestamp = new Date();
        this.stacks = new ArrayList<>();
        Throwable e = ex;
        Integer i = 0;

        while (e != null && i <= 10) {
            List<ExceptionFileInfo> exceptionFileInfos = new ArrayList<>();
            for (StackTraceElement it : e.getStackTrace()) {
                if (it.getClassName().startsWith("com.iot.platform")) {
                    exceptionFileInfos
                            .add(new ExceptionFileInfo(it.getMethodName(), it.getLineNumber(), it.getClassName()));
                }
            }
            this.stacks.add(new ExceptionStack(exceptionFileInfos, e.getMessage(), e.getClass().getTypeName()));
            e = e.getCause();
            i += 1;
        }
    }

    public List<String> toMessages() {
        return this.getStacks().stream().map(e -> e.getExeptionType() + "(" + e.getMessage() + ")")
                .collect(Collectors.toList());
    }

}