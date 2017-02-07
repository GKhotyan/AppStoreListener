package utils;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import java.io.IOException;

public interface MessageSender {
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 2000))
    String send(String message) throws Exception;
}
