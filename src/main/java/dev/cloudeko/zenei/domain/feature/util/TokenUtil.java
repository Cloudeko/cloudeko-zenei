package dev.cloudeko.zenei.domain.feature.util;

import dev.cloudeko.zenei.domain.model.Token;
import dev.cloudeko.zenei.domain.model.token.RefreshToken;
import dev.cloudeko.zenei.domain.model.user.User;

import java.time.LocalDateTime;

public class TokenUtil {

    public static Token createToken(User user, String accessToken, RefreshToken refreshToken) {
        return Token.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .expiresIn(3600)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    public static RefreshToken createRefreshToken(User user, String refreshToken) {
        final var token = new RefreshToken();
        token.setUser(user);
        token.setToken(refreshToken);
        token.setRevoked(false);
        token.setExpiresAt(LocalDateTime.now().plusDays(30));
        return token;
    }
}
