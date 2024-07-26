package dev.cloudeko.zenei.infrastructure.repository.hibernate.panache;

import dev.cloudeko.zenei.domain.model.token.RefreshToken;
import dev.cloudeko.zenei.domain.model.token.RefreshTokenRepository;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.RefreshTokenEntity;

import java.util.Optional;

public class RefreshTokenRepositoryPanache extends AbstractPanacheRepository<RefreshTokenEntity> implements
        RefreshTokenRepository {

    @Override
    public RefreshToken createRefreshToken(RefreshToken refreshToken) {
        final var refreshTokenEntity = new RefreshTokenEntity();

        refreshTokenEntity.setToken(refreshToken.getToken());
        refreshTokenEntity.setUser(findUserEntityById(refreshToken.getUserId()));
        refreshTokenEntity.setRevoked(refreshToken.isRevoked());
        refreshTokenEntity.setExpiresAt(refreshToken.getExpiresAt());
        refreshTokenEntity.setCreatedAt(refreshToken.getCreatedAt());
        refreshTokenEntity.setUpdatedAt(refreshToken.getUpdatedAt());

        persist(refreshTokenEntity);

        return refreshToken;
    }

    @Override
    public Optional<RefreshToken> findRefreshTokenByToken(String token) {
        final var refreshToken = getEntityManager().createQuery(
                        "SELECT s.user FROM RefreshTokenEntity s WHERE s.token = :token AND s.expiresAt > CURRENT_TIMESTAMP AND s.revoked = false",
                        RefreshTokenEntity.class)
                .setParameter("token", token)
                .getSingleResult();

        if (refreshToken == null) {
            return Optional.empty();
        }

        final var refreshTokenModel = new RefreshToken();

        refreshTokenModel.setUserId(refreshToken.getUser().getId());
        refreshTokenModel.setToken(refreshToken.getToken());
        refreshTokenModel.setRevoked(refreshToken.isRevoked());
        refreshTokenModel.setExpiresAt(refreshToken.getExpiresAt());
        refreshTokenModel.setCreatedAt(refreshToken.getCreatedAt());
        refreshTokenModel.setUpdatedAt(refreshToken.getUpdatedAt());

        return Optional.of(refreshTokenModel);
    }
}
