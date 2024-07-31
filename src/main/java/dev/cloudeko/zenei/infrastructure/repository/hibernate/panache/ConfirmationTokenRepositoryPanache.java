package dev.cloudeko.zenei.infrastructure.repository.hibernate.panache;

import dev.cloudeko.zenei.domain.exception.UserNotFoundException;
import dev.cloudeko.zenei.domain.mapping.ConfirmationTokenMapper;
import dev.cloudeko.zenei.domain.model.mail.ConfirmationToken;
import dev.cloudeko.zenei.domain.model.mail.ConfirmationTokenRepository;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.ConfirmationTokenEntity;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@ApplicationScoped
@AllArgsConstructor
public class ConfirmationTokenRepositoryPanache extends AbstractPanacheRepository<ConfirmationTokenEntity> implements
        ConfirmationTokenRepository {

    private final ConfirmationTokenMapper confirmationTokenMapper;

    @Override
    public ConfirmationToken createConfirmationToken(String email, String token, LocalDateTime expiresAt) {
        final var user = findUserEntityByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        final var confirmationTokenEntity = new ConfirmationTokenEntity();

        confirmationTokenEntity.setToken(token);
        confirmationTokenEntity.setUser(user.get());
        confirmationTokenEntity.setExpiresAt(expiresAt);

        persist(confirmationTokenEntity);

        return confirmationTokenMapper.toDomain(confirmationTokenEntity);
    }

    @Override
    public void deleteConfirmationToken(String token) {
        delete("token", token);
    }

    @Override
    public Optional<ConfirmationToken> findByToken(String token) {
        final var confirmationTokenEntity = find("token", token).firstResult();

        if (confirmationTokenEntity == null) {
            return Optional.empty();
        }

        return Optional.of(confirmationTokenMapper.toDomain(confirmationTokenEntity));
    }
}
