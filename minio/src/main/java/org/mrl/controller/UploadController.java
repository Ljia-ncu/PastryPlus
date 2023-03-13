package org.mrl.controller;

import org.mrl.mvc.RestResult;
import org.mrl.mvc.RestResultUtils;
import org.mrl.service.UploadService;
import org.mrl.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(Constants.Minio.PASTRY_MINIO_CONTEXT)
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public RestResult<String> uploadPicture(@RequestPart("file") MultipartFile file) {
        return RestResultUtils.success(uploadService.uploadFile(file));
    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/delete")
    public RestResult<Integer> deletePicture(@RequestBody List<String> files) {
        return RestResultUtils.success(uploadService.deleteFile(files));
    }
}
