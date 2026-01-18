package dev.atinroy.counterBackend.dto.counter;

import lombok.Data;

import java.time.LocalDateTime;

// 7. CounterResponse.java
@Data
public class CounterResponse {
    private Long counterId;
    private String counterName;
    private Long counterValue;
}
