package dev.cloudeko.zenei.infrastructure.repository.hibernate.panache;

import dev.cloudeko.zenei.domain.exception.InvalidRefreshTokenException;
import dev.cloudeko.zenei.domain.mapping.RefreshTokenMapper;
import dev.cloudeko.zenei.extension.core.model.session.SessionRefreshToken;
import dev.cloudeko.zenei.extension.core.repository.RefreshTokenRepository;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.RefreshTokenEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;

import java.util.Optional;

@ApplicationScoped
@AllArgsConstructor
public class RefreshTokenRepositoryPanache extends AbstractPanacheRepository<RefreshTokenEntity> implements
        RefreshTokenRepository {

    private final RefreshTokenMapper refreshTokenMapper;

    @Override
    public SessionRefreshToken createRefreshToken(SessionRefreshToken sessionRefreshToken) {
        final var refreshTokenEntity = new RefreshTokenEntity();

        refreshTokenEntity.setToken(sessionRefreshToken.getToken());
        refreshTokenEntity.setUser(findUserEntityById(sessionRefreshToken.getUser().getId()));
        refreshTokenEntity.setRevoked(sessionRefreshToken.isRevoked());
        refreshTokenEntity.setExpiresAt(sessionRefreshToken.getExpiresAt());

        persist(refreshTokenEntity);

        sessionRefreshToken.setCreatedAt(refreshTokenEntity.getCreatedAt());
        sessionRefreshToken.setUpdatedAt(refreshTokenEntity.getUpdatedAt());

        return sessionRefreshToken;
    }

    @Override
    public SessionRefreshToken swapRefreshToken(
            SessionRefreshToken currentSessionRefreshToken, SessionRefreshToken newSessionRefreshToken) {
        final var currentRefreshTokenEntity = findRefreshTokenEntityByToken(currentSessionRefreshToken.getToken())
                .orElseThrow(InvalidRefreshTokenException::new);
        final var newRefreshTokenEntity = new RefreshTokenEntity();

        newRefreshTokenEntity.setToken(newSessionRefreshToken.getToken());
        newRefreshTokenEntity.setUser(findUserEntityById(newSessionRefreshToken.getUser().getId()));
        newRefreshTokenEntity.setRevoked(newSessionRefreshToken.isRevoked());
        newRefreshTokenEntity.setExpiresAt(newSessionRefreshToken.getExpiresAt());

        currentRefreshTokenEntity.setRevoked(true);

        persist(newRefreshTokenEntity, currentRefreshTokenEntity);

        newSessionRefreshToken.setCreatedAt(newRefreshTokenEntity.getCreatedAt());
        newSessionRefreshToken.setUpdatedAt(newRefreshTokenEntity.getUpdatedAt());

        return newSessionRefreshToken;
    }

    @Override
    public Optional<SessionRefreshToken> findRefreshTokenByToken(String token) {
        final var refreshTokenResult = findRefreshTokenEntityByToken(token);
        return refreshTokenResult.map(refreshTokenMapper::toDomain);
    }

    private Optional<RefreshTokenEntity> findRefreshTokenEntityByToken(String token) {
        try {
            return Optional.of(
                    getEntityManager().createNamedQuery("RefreshTokenEntity.findByValidToken", RefreshTokenEntity.class)
                            .setParameter("token", token)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
