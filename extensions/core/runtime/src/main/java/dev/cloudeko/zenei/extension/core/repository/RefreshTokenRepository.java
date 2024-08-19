package dev.cloudeko.zenei.extension.core.repository;

import dev.cloudeko.zenei.extension.core.model.session.SessionRefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {

    SessionRefreshToken createRefreshToken(SessionRefreshToken sessionRefreshToken);

    SessionRefreshToken swapRefreshToken(
            SessionRefreshToken currentSessionRefreshToken, SessionRefreshToken newSessionRefreshToken);

    Optional<SessionRefreshToken> findRefreshTokenByToken(String token);

}
