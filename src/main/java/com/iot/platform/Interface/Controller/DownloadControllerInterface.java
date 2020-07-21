package com.iot.platform.Interface.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.regex.Pattern;

import com.iot.platform.Interface.Service.FileServiceInterface;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.ApiOperation;

public interface DownloadControllerInterface extends ControllerInterface {

    FileServiceInterface getFileService();
    
    @RequestMapping(value = "file/{year}/{month}/{date}/{fileName}", method = RequestMethod.GET)
    @ApiOperation(value = "Download file")
    public default ResponseEntity<byte[]> downloadFile(@PathVariable Integer year, @PathVariable Integer month,
            @PathVariable Integer date, @PathVariable String fileName) throws Exception {
        String filePath = year + "/" + month + "/" + date + "/" + fileName;
        Pattern pattern = Pattern.compile("^\\d+\\/\\d+\\/\\d+\\/\\d+\\.([a-zA-Z0-9]+)$");
        if (!pattern.matcher(filePath).matches()) {
            return ResponseEntity.badRequest().contentLength(0).body(null);
        }
        String path = this.getFileService().getFullPath(filePath, this.getPrefix());
        InputStream resource = new FileInputStream(path);
        String mimeType = Files.probeContentType(new File(path).toPath());
        HttpHeaders headers = new HttpHeaders();
        
        headers.add("Content-Type", mimeType);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        byte[] bytes = IOUtils.toByteArray(resource);
        return ResponseEntity.ok().headers(headers).contentLength(bytes.length).body(bytes);
    }

}