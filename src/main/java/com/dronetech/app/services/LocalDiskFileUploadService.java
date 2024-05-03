package com.dronetech.app.services;

import com.dronetech.app.exceptions.FileOperationException;
import com.dronetech.app.services.api.FileUploadService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Slf4j
@Service
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class LocalDiskFileUploadService implements FileUploadService {

    @Value("${upload.path}")
    private String UPLOAD_PATH;

    public String saveFile(MultipartFile multipartFile, String name) {

        log.info("Saving file: {}", multipartFile.getOriginalFilename());
        String uniqueName = UUID.randomUUID() + "_" + name + getExtension(multipartFile);
        String filePath = UPLOAD_PATH + uniqueName;

        File file = new File(filePath);

        try {
            multipartFile.transferTo(file.toPath());
        } catch (Exception e) {
            log.error("Error saving file: {}", e.getMessage(), e);
            throw new FileOperationException("Error saving file");
        }

        return filePath;
    }

    public String getExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;

        int extI = originalFilename.lastIndexOf(".");

        if (extI == -1) {
            return ".jpg";
        }

        return originalFilename.substring(extI);
    }

    public byte[] getFile(String path) throws IOException {
        File file = new File(path);
        return Files.readAllBytes(file.toPath());
    }
}
