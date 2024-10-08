package dev.cloudeko.zenei.extension.core.model.session;

import dev.cloudeko.zenei.extension.core.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    private Long id;
    private User user;
    private LocalDateTime expiresAt;
    private LocalDateTime issuedAt;
}
