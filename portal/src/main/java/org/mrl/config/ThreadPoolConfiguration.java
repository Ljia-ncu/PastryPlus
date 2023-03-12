package org.mrl.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Slf4j
@Data
@EnableAsync
@Configuration
@ConfigurationProperties(prefix = "thread-pool")
public class ThreadPoolConfiguration implements AsyncConfigurer {

    private int maxPoolSize = 20;

    private int corePoolSize = 10;

    private int queueCapacity = 2000;

    private int keepAliveSeconds = 60;

    private String threadNamePrefix = "portal-";

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setRejectedExecutionHandler((r, e) -> {
            if (!e.isShutdown()) {
                r.run();
            }
        });
        executor.initialize();
        return executor;
    }
    @Override
    public Executor getAsyncExecutor() {
        return threadPoolTaskExecutor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) ->
                log.error("async-thread-pool unCaughtException: message:{},method:{},params:{}", ex.getMessage(), method, params);
    }
}
