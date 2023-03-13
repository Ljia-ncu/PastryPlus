package org.mrl.service.impl;

import org.mrl.componet.MinioManager;
import org.mrl.exception.PastryRuntimeException;
import org.mrl.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private MinioManager manager;

    private final MediaType IMAGE_TYPES = MediaType.valueOf("image/*");

    @Override
    public String uploadFile(MultipartFile file) {
        String type = file.getContentType();
        if (type != null && IMAGE_TYPES.includes(MediaType.valueOf(type))) {
            return manager.upload(file);
        } else {
            throw new PastryRuntimeException(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), "Unsupported Media Type");
        }
    }

    @Override
    public Integer deleteFile(List<String> files) {
        int count = 0;
        for (String file : files) {
            if (manager.deleteFile(file)) {
                count++;
            }
        }
        return count;
    }
}
