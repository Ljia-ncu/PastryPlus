package org.mrl.config;

import io.minio.MinioClient;
import org.mrl.property.MinioProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class MinioConfiguration {
    @Autowired
    private MinioProperty property;
    @Bean
    public MinioClient buildMinioClient() {
        return MinioClient.builder()
                .endpoint(property.getEndpoint())
                .credentials(property.getAccessKey(), property.getSecretKey())
                .build();
    }
}
