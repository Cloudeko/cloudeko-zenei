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
public class SignupAttempt {
    private long id;

    private Strategy strategy;

    private String username;
    private String email;
    private String firstName;
    private String lastName;

    private boolean emailVerified;
    private boolean passwordEnabled;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
