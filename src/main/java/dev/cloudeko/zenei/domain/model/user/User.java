package dev.cloudeko.zenei.domain.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private UUID id;
    private String username;
    private String email;
    private boolean emailVerified;
    private String image;
    private boolean admin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
