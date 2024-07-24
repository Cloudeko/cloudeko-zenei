package dev.cloudeko.zenei.service;

import dev.cloudeko.zenei.models.RefreshTokenEntity;
import dev.cloudeko.zenei.models.UserEntity;
import dev.cloudeko.zenei.repository.RefreshTokenRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
public class RefreshTokenService {

    @Inject
    RefreshTokenRepository refreshTokenRepository;

    @WithTransaction
    public Uni<RefreshTokenEntity> swapRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByValidRefreshToken(refreshToken)
                .onItem().ifNotNull().transformToUni(this::finishTokenSwap)
                .onItem().ifNull().failWith(new UnauthorizedException("Invalid refresh token"));
    }

    private Uni<RefreshTokenEntity> finishTokenSwap(RefreshTokenEntity refreshToken) {
        if (refreshToken.isRevoked() || refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            return Uni.createFrom().nullItem();
        }

        refreshToken.setRevoked(true);
        return createRefreshToken(refreshToken.getUser());
    }

    @WithTransaction
    public Uni<RefreshTokenEntity> createRefreshToken(UserEntity user) {
        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setUser(user);
        refreshToken.setExpiresAt(LocalDateTime.now().plusDays(30));
        refreshToken.setToken(BcryptUtil.bcryptHash(user.getEmail() + UUID.randomUUID().toString()));

        return refreshTokenRepository.persist(refreshToken);
    }
}
