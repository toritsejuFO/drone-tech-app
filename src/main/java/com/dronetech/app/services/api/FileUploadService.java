package com.dronetech.app.services.api;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {

    String saveFile(MultipartFile multipartFile, String name);

    String getExtension(MultipartFile file);

    byte[] getFile(String path) throws IOException;

}
