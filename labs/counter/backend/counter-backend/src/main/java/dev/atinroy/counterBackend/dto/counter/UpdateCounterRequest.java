package dev.atinroy.counterBackend.dto.counter;

import jakarta.validation.constraints.Size;
import lombok.Data;

// 5. UpdateCounterRequest.java
@Data
public class UpdateCounterRequest {
    @Size(min = 1, max = 100, message = "Counter name must be between 1 and 100 characters")
    private String counterName;  // Optional - only if user wants to rename

    private Long counterValue;  // Optional - only if user wants to set specific value
}
