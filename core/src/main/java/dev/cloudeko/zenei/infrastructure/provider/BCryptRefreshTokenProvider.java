package dev.cloudeko.zenei.infrastructure.provider;

import dev.cloudeko.zenei.domain.model.user.User;
import dev.cloudeko.zenei.domain.provider.RefreshTokenProvider;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Base64;
import java.util.UUID;

@ApplicationScoped
public class BCryptRefreshTokenProvider implements RefreshTokenProvider {
    @Override
    public String generateRefreshToken(User user) {
        final var hash = generateHash(user);
        final var base64 = Base64.getEncoder().encodeToString(hash.getBytes());
        final var token = "zrt_" + base64;
        return token;
    }

    private String generateHash(User user) {
        return BcryptUtil.bcryptHash(user.getPrimaryEmailAddress().getEmail() + System.currentTimeMillis() + UUID.randomUUID().toString());
    }
}
