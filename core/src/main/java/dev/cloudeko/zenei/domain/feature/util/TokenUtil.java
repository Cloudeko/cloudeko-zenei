package dev.cloudeko.zenei.domain.feature.util;

import dev.cloudeko.zenei.extension.core.model.session.SessionRefreshToken;
import dev.cloudeko.zenei.extension.core.model.session.SessionToken;
import dev.cloudeko.zenei.extension.core.model.user.User;

import java.time.LocalDateTime;

public class TokenUtil {

    public static SessionToken createToken(User user, String accessToken, SessionRefreshToken sessionRefreshToken) {
        return SessionToken.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .expiresIn(3600)
                .refreshToken(sessionRefreshToken.getToken())
                .build();
    }

    public static SessionRefreshToken createRefreshToken(User user, String refreshToken) {
        final var token = new SessionRefreshToken();
        token.setUser(user);
        token.setToken(refreshToken);
        token.setRevoked(false);
        token.setExpiresAt(LocalDateTime.now().plusDays(30));
        return token;
    }
}
