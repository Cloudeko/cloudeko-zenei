package dev.cloudeko.zenei.infrastructure.repository.hibernate.panache;

import dev.cloudeko.zenei.domain.model.token.RefreshToken;
import dev.cloudeko.zenei.domain.model.token.RefreshTokenRepository;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.RefreshTokenEntity;

import java.util.Optional;

public class RefreshTokenRepositoryPanache extends AbstractPanacheRepository<RefreshTokenEntity> implements
        RefreshTokenRepository {

    @Override
    public RefreshToken createRefreshToken(RefreshToken refreshToken) {
        return null;
    }

    @Override
    public Optional<RefreshToken> findRefreshTokenByToken(String token) {
        RefreshTokenEntity refreshToken = getEntityManager().createQuery(
                        "SELECT s.user FROM RefreshTokenEntity s WHERE s.token = :token AND s.expiresAt > CURRENT_TIMESTAMP AND s.revoked = false",
                        RefreshTokenEntity.class)
                .setParameter("token", token)
                .getSingleResult();

        if (refreshToken == null) {
            return Optional.empty();
        }

        RefreshToken refreshTokenModel = new RefreshToken();
        refreshToken.setToken(refreshToken.getToken());

        return Optional.of(refreshTokenModel);
    }
}
