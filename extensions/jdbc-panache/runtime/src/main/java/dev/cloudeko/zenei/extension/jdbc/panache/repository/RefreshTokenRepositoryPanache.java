package dev.cloudeko.zenei.extension.jdbc.panache.repository;

import dev.cloudeko.zenei.extension.core.model.session.SessionRefreshToken;
import dev.cloudeko.zenei.extension.core.repository.RefreshTokenRepository;
import dev.cloudeko.zenei.extension.jdbc.panache.entity.RefreshTokenEntity;
import dev.cloudeko.zenei.extension.jdbc.panache.entity.UserEntity;
import dev.cloudeko.zenei.extension.jdbc.panache.mapping.RefreshTokenMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.Optional;

import static io.quarkus.panache.common.Parameters.with;

@ApplicationScoped
@AllArgsConstructor
public class RefreshTokenRepositoryPanache extends AbstractPanacheRepository<RefreshTokenEntity> implements
        RefreshTokenRepository {

    private final RefreshTokenMapper refreshTokenMapper;

    @Override
    public SessionRefreshToken createRefreshToken(SessionRefreshToken sessionRefreshToken) {
        Optional<UserEntity> userEntity = findUserEntityById(sessionRefreshToken.getUser().getId());
        if (userEntity.isEmpty()) {
            return null;
        }

        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();

        refreshTokenEntity.setToken(sessionRefreshToken.getToken());
        refreshTokenEntity.setUser(userEntity.get());
        refreshTokenEntity.setRevoked(sessionRefreshToken.isRevoked());
        refreshTokenEntity.setExpiresAt(sessionRefreshToken.getExpiresAt());

        persist(refreshTokenEntity);

        sessionRefreshToken.setCreatedAt(refreshTokenEntity.getCreatedAt());
        sessionRefreshToken.setUpdatedAt(refreshTokenEntity.getUpdatedAt());

        return sessionRefreshToken;
    }

    @Override
    public SessionRefreshToken swapRefreshToken(SessionRefreshToken currentSessionRefreshToken,
            SessionRefreshToken newSessionRefreshToken) {
        Optional<RefreshTokenEntity> currentRefreshTokenEntity = findRefreshTokenEntityByToken(
                currentSessionRefreshToken.getToken());

        if (currentRefreshTokenEntity.isEmpty()) {
            return null;
        }

        Optional<UserEntity> userEntity = findUserEntityById(newSessionRefreshToken.getUser().getId());
        if (userEntity.isEmpty()) {
            return null;
        }

        RefreshTokenEntity newRefreshTokenEntity = new RefreshTokenEntity();

        newRefreshTokenEntity.setToken(newSessionRefreshToken.getToken());
        newRefreshTokenEntity.setUser(userEntity.get());
        newRefreshTokenEntity.setRevoked(newSessionRefreshToken.isRevoked());
        newRefreshTokenEntity.setExpiresAt(newSessionRefreshToken.getExpiresAt());

        currentRefreshTokenEntity.get().setRevoked(true);

        persist(newRefreshTokenEntity, currentRefreshTokenEntity.get());

        newSessionRefreshToken.setCreatedAt(newRefreshTokenEntity.getCreatedAt());
        newSessionRefreshToken.setUpdatedAt(newRefreshTokenEntity.getUpdatedAt());

        return newSessionRefreshToken;
    }

    @Override
    public Optional<SessionRefreshToken> findRefreshTokenByToken(String token) {
        return findRefreshTokenEntityByToken(token).map(refreshTokenMapper::toDomain);
    }

    private Optional<RefreshTokenEntity> findRefreshTokenEntityByToken(String token) {
        return find("#RefreshTokenEntity.findByValidToken", with("token", token)).firstResultOptional();
    }
}
