package techit.velog.global.handler;

import org.springframework.scheduling.annotation.Async;
import techit.velog.global.exception.CustomWebException;

import java.util.concurrent.CompletableFuture;

public class AsyncHandler {
    @Async
    public CompletableFuture<String> asyncMethod() {
        try {
            // 비즈니스 로직
            return CompletableFuture.completedFuture("Success");
        } catch (Exception ex) {
            // 예외 처리
            throw new CustomWebException(ex.getMessage());
        }
    }
}
