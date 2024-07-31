package dev.cloudeko.zenei.domain.model.email;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ConfirmationTokenRepository {

    ConfirmationToken createConfirmationToken(String email, String token, LocalDateTime expiresAt);

    void deleteConfirmationToken(String token);

    Optional<ConfirmationToken> findByToken(String token);
}
