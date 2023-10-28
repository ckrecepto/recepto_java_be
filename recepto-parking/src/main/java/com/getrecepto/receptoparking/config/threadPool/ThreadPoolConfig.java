package com.getrecepto.receptoparking.config.threadPool;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;


@Component
public class ThreadPoolConfig {
    @Bean("ReceptoThreadPool")
    private ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(5000);
        executor.setThreadNamePrefix("RECEPTO_THREAD_POOL");
        executor.initialize();
        return executor;
    }
}
