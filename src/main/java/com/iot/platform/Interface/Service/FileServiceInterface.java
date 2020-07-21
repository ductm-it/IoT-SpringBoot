package com.iot.platform.Interface.Service;

import java.io.IOException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

public interface FileServiceInterface {

    public String storeFile(MultipartFile file, String prefix) throws IOException;

    public String storeFile(MultipartFile file) throws IOException;

    public InputStreamResource downloadFile(String filePath) throws IOException;

    public String getFullPath(String filePath);

    public String getFullPath(String filePath, String prefix);

}