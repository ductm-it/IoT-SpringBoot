package com.iot.platform.Interface.Controller;

import com.iot.platform.Core.Response.ResponseData;
import com.iot.platform.Interface.Service.AsyncServiceInterface;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;

public interface UploadBigFileControllerInterface extends UploadControllerInterface {

    public AsyncServiceInterface getAsyncService();

    @RequestMapping(value = "process-big-file", method = RequestMethod.POST)
    @ApiModelProperty(value = "Process big file")
    public default ResponseEntity<ResponseData<String>> processBigFile(@RequestParam("file") MultipartFile file)
            throws Exception {
        this.getAsyncService().withoutTransaction(() -> {
            int i = 0;
            this.uploadFile(file);
            while (i++ < 100) {
                System.out.println("Processing... " + i + "%");
            }
        });
        return ResponseDataEntity(ResponseData.success("Your file is uploaded. We are trying to precess your file. The result of the processing will be sent via Websocket."));
    }

}