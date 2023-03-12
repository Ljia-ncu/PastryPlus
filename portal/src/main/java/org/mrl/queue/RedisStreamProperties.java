package org.mrl.queue;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "redis-stream")
public class RedisStreamProperties {

    private String key;

    private String group;

    private int batchSize;
}
