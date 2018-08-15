package com.scushare.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class ThreadConfig {

    private int corePoolSize = 10;//�̳߳�ά���̵߳���������

    private int maxPoolSize = 30;//�̳߳�ά���̵߳��������

    private int queueCapacity = 8; //�������

    private int keepAlive = 60;//����Ŀ���ʱ��

    @Bean
    public Executor myExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("mqExecutor-");
        // rejection-policy����pool�Ѿ��ﵽmax size��ʱ����δ���������  
        // CALLER_RUNS���������߳���ִ�����񣬶����ɵ��������ڵ��߳���ִ��  
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); //�Ծܾ�task�Ĵ������
        executor.setKeepAliveSeconds(keepAlive);
        executor.initialize();
        return executor;
    }
}
