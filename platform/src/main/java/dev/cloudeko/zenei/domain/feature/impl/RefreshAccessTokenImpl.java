package dev.cloudeko.zenei.domain.feature.impl;

import dev.cloudeko.zenei.domain.exception.InvalidRefreshTokenException;
import dev.cloudeko.zenei.domain.feature.RefreshAccessToken;
import dev.cloudeko.zenei.domain.feature.util.TokenUtil;
import dev.cloudeko.zenei.domain.provider.RefreshTokenProvider;
import dev.cloudeko.zenei.domain.provider.TokenProvider;
import dev.cloudeko.zenei.extension.core.model.session.SessionToken;
import dev.cloudeko.zenei.extension.core.repository.RefreshTokenRepository;
import dev.cloudeko.zenei.extension.core.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class RefreshAccessTokenImpl implements RefreshAccessToken {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final RefreshTokenProvider refreshTokenProvider;
    private final TokenProvider tokenProvider;

    @Override
    public SessionToken handle(String token) {
        final var refreshToken = refreshTokenRepository.findRefreshTokenByToken(token);

        if (refreshToken.isEmpty()) {
            throw new InvalidRefreshTokenException();
        }

        final var user = refreshToken.get().getUser();

        final var refreshTokenData = refreshTokenProvider.generateRefreshToken(user);
        final var accessTokenData = tokenProvider.generateToken(user);

        final var newRefreshToken = TokenUtil.createRefreshToken(user, refreshTokenData);
        final var newToken = refreshTokenRepository.swapRefreshToken(refreshToken.get(), newRefreshToken);
        if (newToken == null) {
            throw new InvalidRefreshTokenException();
        }

        return TokenUtil.createToken(user, accessTokenData, newRefreshToken);
    }
}
