package com.iot.platform.Interface.Controller;

import com.iot.platform.Core.Response.ResponseData;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;

public interface UploadExcelControllerInterface extends UploadControllerInterface {
    
    @RequestMapping(value = "upload-excel", method = RequestMethod.POST)
    @ApiModelProperty(value = "Upload an excel")
    public default ResponseEntity<ResponseData<String>> uploadExcelFile(@RequestParam("file") MultipartFile file) throws Exception {
        return this.uploadFile(file);
    }

}