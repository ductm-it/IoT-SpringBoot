package com.iot.platform.Interface.Controller;

import java.util.ArrayList;
import java.util.List;

import com.iot.platform.Core.Response.ResponseData;
import com.iot.platform.Interface.Service.FileServiceInterface;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UploadControllerInterface extends ControllerInterface {

    FileServiceInterface getFileService();
    
    public default ResponseEntity<ResponseData<String>> uploadFile(MultipartFile file) throws Exception {
        return ResponseDataEntity(ResponseData.success(this.getFileService().storeFile(file, this.getPrefix())));
    }

    public default ResponseEntity<ResponseData<List<String>>> uploadFile(MultipartFile[] files) throws Exception {
        List<String> paths = new ArrayList<>();
        String prefix = this.getPrefix();
        for (MultipartFile file : files) {
            paths.add(this.getFileService().storeFile(file, prefix));
        }
        return ResponseDataEntity(ResponseData.success(paths));
    }

}