package org.mrl.componet;

import cn.hutool.core.io.IoUtil;
import io.minio.*;
import io.minio.http.Method;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.mrl.exception.PastryRuntimeException;
import org.mrl.property.MinioProperty;
import org.mrl.utils.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MinioManager {
    private static final String PATH_SPLIT = "/";

    @Resource
    private MinioClient client;

    @Autowired
    private MinioProperty property;

    public Boolean checkBucket(String bucket) {
        try {
            return client.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        } catch (Exception e) {
            throw new PastryRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "failed to check bucket");
        }
    }

    public void buildBucket(String bucket) {
        try {
            client.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        } catch (Exception e) {
            throw new PastryRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "failed to build bucket");
        }
    }

    public void removeBucket(String bucket) {
        try {
            client.removeBucket(RemoveBucketArgs.builder().bucket(property.getBucket()).build());
        } catch (Exception e) {
            throw new PastryRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "failed to remove bucket");
        }
    }

    public String upload(MultipartFile file) {
        try {
            Long userId = SecurityContext.getCurrentUserID();
            String filename = userId + "_" + file.getOriginalFilename();
            var putObjectArgs = PutObjectArgs.builder().bucket(property.getBucket()).object(filename)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType()).build();
            client.putObject(putObjectArgs);
            return property.getEndpoint() + PATH_SPLIT + property.getBucket() + PATH_SPLIT + filename;
        } catch (Exception e) {
            throw new PastryRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "failed to upload file");
        }
    }

    public String preview(String filename) {
        try {
            GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                    .bucket(property.getBucket()).object(filename).method(Method.GET).build();
            return client.getPresignedObjectUrl(args);
        } catch (Exception e) {
            throw new PastryRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "failed to preview file");
        }
    }

    public void download(String fileName, HttpServletResponse response) {
        try {
            GetObjectArgs args = GetObjectArgs.builder().bucket(property.getBucket()).object(fileName).build();
            GetObjectResponse file = client.getObject(args);
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            response.setContentType("application/octet-stream");
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(IoUtil.readBytes(file));
            outputStream.flush();
        } catch (Exception e) {
            throw new PastryRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "failed to download file");
        }
    }

    public boolean deleteFile(String fileName) {
        boolean result = false;
        try {
            client.removeObject(RemoveObjectArgs.builder()
                    .bucket(property.getBucket()).object(fileName).build());
            result = true;
        } catch (Exception e) {
            // log
            // throw new PastryRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "failed to delete file");
        }
        return result;
    }
}
