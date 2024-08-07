package dev.cloudeko.zenei.domain.model.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailAddress {
    private Long id;

    private String email;
    private Boolean emailVerified = true;

    private String emailVerificationToken;
    private LocalDateTime emailVerificationTokenExpiresAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
