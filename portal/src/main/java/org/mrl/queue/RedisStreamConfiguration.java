package org.mrl.queue;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.mrl.model.entity.Record;
import org.mrl.utils.Constants;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.Duration;

@Slf4j
@Configuration
public class RedisStreamConfiguration implements ApplicationRunner, DisposableBean {

    @Resource
    private RedisStreamProperties properties;

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Resource
    private RedisTemplate<String, Record> redisTemplate;

    @Autowired
    private RedisStreamListener redisStreamListener;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    private StreamMessageListenerContainer<String, ObjectRecord<String, Record>> container;

    private void prepareStreamGroup() {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(properties.getKey()))) {
            StreamInfo.XInfoGroups groups = redisTemplate.opsForStream().groups(properties.getKey());
            if (groups.isEmpty()) {
                redisTemplate.opsForStream().createGroup(properties.getKey(), properties.getGroup());
            }
        } else {
            redisTemplate.opsForStream().createGroup(properties.getKey(), properties.getGroup());
        }
    }
    private StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, Record>> buildContainerOptions() {
        return StreamMessageListenerContainer.StreamMessageListenerContainerOptions.builder()
                .serializer(RedisSerializer.string())
                .targetType(Record.class)
                .executor(threadPoolTaskExecutor)
                .pollTimeout(Duration.ofSeconds(1))
                .batchSize(properties.getBatchSize())
                .errorHandler(throwable -> {
                    log.error(throwable.getMessage());
                }).build();
    }

//    @Bean
//    public Subscription subscriptionOne(StreamMessageListenerContainer<String, ObjectRecord<String, Record>> container) {
//        return container.receive(Consumer.from(properties.getGroup(), Constants.RedisStream.CONSUMER_LIKE_ONE),
//                StreamOffset.create(properties.getKey(), ReadOffset.lastConsumed()), redisStreamListener);
//    }

    @Override
    public void run(ApplicationArguments args) {
        prepareStreamGroup();
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, Record>> options = buildContainerOptions();
        container = StreamMessageListenerContainer.create(redisConnectionFactory, options);
        container.receive(Consumer.from(properties.getGroup(), Constants.RedisStream.CONSUMER_LIKE_ONE),
                StreamOffset.create(properties.getKey(), ReadOffset.lastConsumed()), redisStreamListener);
        container.start();
    }

    @Override
    public void destroy() {
        container.stop();
    }
}
