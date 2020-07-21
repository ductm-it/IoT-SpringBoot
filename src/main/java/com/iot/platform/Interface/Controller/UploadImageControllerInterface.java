package com.iot.platform.Interface.Controller;

import com.iot.platform.Core.Response.ResponseData;
import com.iot.platform.Validator.FileSize;
import com.iot.platform.Validator.MimeType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;

public interface UploadImageControllerInterface extends UploadControllerInterface {

    @RequestMapping(value = "upload-image", method = RequestMethod.POST)
    @ApiModelProperty(value = "Upload an image")
    public default ResponseEntity<ResponseData<String>> uploadImageFile(@FileSize(param = 1024l
            * 1024) @MimeType(param = { "image/jpeg" }) @RequestParam("file") MultipartFile file) throws Exception {
        return this.uploadFile(file);
    }

}