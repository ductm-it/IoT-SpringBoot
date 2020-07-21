package com.iot.platform.Core.System;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Email {

    private String subject;
    private String body;
    private List<String> sendToEmail = new ArrayList<>();
    private List<String> sendToCCs = new ArrayList<>();
    private List<String> sendToBCCs = new ArrayList<>();
    private List<File> files = new ArrayList<>();
    private boolean isHtml;

}