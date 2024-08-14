package dev.cloudeko.zenei.infrastructure.repository.hibernate.panache;

import dev.cloudeko.zenei.domain.exception.InvalidRefreshTokenException;
import dev.cloudeko.zenei.domain.mapping.RefreshTokenMapper;
import dev.cloudeko.zenei.domain.model.token.RefreshToken;
import dev.cloudeko.zenei.domain.model.token.RefreshTokenRepository;
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
    public RefreshToken createRefreshToken(RefreshToken refreshToken) {
        final var refreshTokenEntity = new RefreshTokenEntity();

        refreshTokenEntity.setToken(refreshToken.getToken());
        refreshTokenEntity.setUser(findUserEntityById(refreshToken.getUser().getId()));
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
        newRefreshTokenEntity.setUser(findUserEntityById(newRefreshToken.getUser().getId()));
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
