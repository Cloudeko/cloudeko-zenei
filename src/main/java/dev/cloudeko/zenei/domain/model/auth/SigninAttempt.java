package dev.cloudeko.zenei.domain.model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SigninAttempt {
    private long id;
    private Strategy strategy;

    private boolean pending;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
