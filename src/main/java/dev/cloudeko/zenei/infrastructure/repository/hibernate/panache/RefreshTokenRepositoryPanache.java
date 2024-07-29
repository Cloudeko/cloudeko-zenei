package dev.cloudeko.zenei.infrastructure.repository.hibernate.panache;

import dev.cloudeko.zenei.domain.exception.InvalidRefreshTokenException;
import dev.cloudeko.zenei.domain.model.token.RefreshToken;
import dev.cloudeko.zenei.domain.model.token.RefreshTokenRepository;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.RefreshTokenEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;

import java.util.Optional;

@ApplicationScoped
public class RefreshTokenRepositoryPanache extends AbstractPanacheRepository<RefreshTokenEntity> implements
        RefreshTokenRepository {

    @Override
    public RefreshToken createRefreshToken(RefreshToken refreshToken) {
        final var refreshTokenEntity = new RefreshTokenEntity();

        refreshTokenEntity.setToken(refreshToken.getToken());
        refreshTokenEntity.setUser(findUserEntityById(refreshToken.getUserId()));
        refreshTokenEntity.setRevoked(refreshToken.isRevoked());
        refreshTokenEntity.setExpiresAt(refreshToken.getExpiresAt());

        persist(refreshTokenEntity);

        refreshToken.setCreatedAt(refreshTokenEntity.getCreatedAt());
        refreshToken.setUpdatedAt(refreshTokenEntity.getUpdatedAt());

        return refreshToken;
    }

    @Override
    public RefreshToken swapRefreshToken(RefreshToken currentRefreshToken, RefreshToken newRefreshToken) {
        final var currentRefreshTokenEntity = findRefreshTokenEntityByToken(currentRefreshToken.getToken())
                .orElseThrow(InvalidRefreshTokenException::new);
        final var newRefreshTokenEntity = new RefreshTokenEntity();

        newRefreshTokenEntity.setToken(newRefreshToken.getToken());
        newRefreshTokenEntity.setUser(findUserEntityById(newRefreshToken.getUserId()));
        newRefreshTokenEntity.setRevoked(newRefreshToken.isRevoked());
        newRefreshTokenEntity.setExpiresAt(newRefreshToken.getExpiresAt());

        currentRefreshTokenEntity.setRevoked(true);

        persist(newRefreshTokenEntity, currentRefreshTokenEntity);

        newRefreshToken.setCreatedAt(newRefreshTokenEntity.getCreatedAt());
        newRefreshToken.setUpdatedAt(newRefreshTokenEntity.getUpdatedAt());

        return newRefreshToken;
    }

    @Override
    public Optional<RefreshToken> findRefreshTokenByToken(String token) {
        final var refreshTokenResult = findRefreshTokenEntityByToken(token);

        if (refreshTokenResult.isEmpty()) {
            return Optional.empty();
        }

        final var refreshToken = refreshTokenResult.get();
        final var refreshTokenModel = new RefreshToken();

        refreshTokenModel.setUserId(refreshToken.getUser().getId());
        refreshTokenModel.setToken(refreshToken.getToken());
        refreshTokenModel.setRevoked(refreshToken.isRevoked());
        refreshTokenModel.setExpiresAt(refreshToken.getExpiresAt());
        refreshTokenModel.setCreatedAt(refreshToken.getCreatedAt());
        refreshTokenModel.setUpdatedAt(refreshToken.getUpdatedAt());

        return Optional.of(refreshTokenModel);
    }

    private Optional<RefreshTokenEntity> findRefreshTokenEntityByToken(String token) {
        try {
            return Optional.of(getEntityManager().createQuery(
                            "SELECT s FROM RefreshTokenEntity s WHERE s.token = :token AND s.expiresAt > CURRENT_TIMESTAMP AND s.revoked = false",
                            RefreshTokenEntity.class)
                    .setParameter("token", token)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
