package techit.velog.global.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "customTaskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // 기본적으로 유지할 스레드 수
        executor.setMaxPoolSize(10); // 최대 생성할 수 있는 스레드 수
        executor.setQueueCapacity(25); // 작업 큐의 크기
        executor.setThreadNamePrefix("CustomTaskExecutor-"); // 생성되는 스레드 이름에 붙일 접두사
        executor.initialize(); // 메서드 설정 초기화
        return executor;
    }
}
