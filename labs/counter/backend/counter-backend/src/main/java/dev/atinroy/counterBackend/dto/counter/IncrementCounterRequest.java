package dev.atinroy.counterBackend.dto.counter;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

// 6. IncrementCounterRequest.java
@Data
public class IncrementCounterRequest {
    @NotNull(message = "Increment value is required")
    private Long incrementBy;  // Can be positive or negative
}
