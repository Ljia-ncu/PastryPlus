package org.mrl.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadService {

    String uploadFile(MultipartFile file);

    Integer deleteFile(List<String> files);

}
