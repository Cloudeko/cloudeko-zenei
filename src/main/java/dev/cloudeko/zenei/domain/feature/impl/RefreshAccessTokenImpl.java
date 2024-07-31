package dev.cloudeko.zenei.domain.feature.impl;

import dev.cloudeko.zenei.domain.exception.InvalidRefreshTokenException;
import dev.cloudeko.zenei.domain.feature.RefreshAccessToken;
import dev.cloudeko.zenei.domain.feature.util.TokenUtil;
import dev.cloudeko.zenei.domain.model.Token;
import dev.cloudeko.zenei.domain.model.token.RefreshTokenInput;
import dev.cloudeko.zenei.domain.model.token.RefreshTokenRepository;
import dev.cloudeko.zenei.domain.model.user.UserRepository;
import dev.cloudeko.zenei.domain.provider.RefreshTokenProvider;
import dev.cloudeko.zenei.domain.provider.TokenProvider;
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
    public Token handle(RefreshTokenInput refreshTokenInput) {
        final var refreshToken = refreshTokenRepository.findRefreshTokenByToken(refreshTokenInput.getRefreshToken());

        if (refreshToken.isEmpty()) {
            throw new InvalidRefreshTokenException();
        }

        final var user = refreshToken.get().getUser();

        final var refreshTokenData = refreshTokenProvider.generateRefreshToken(user);
        final var accessTokenData = tokenProvider.generateToken(user);

        final var newRefreshToken = TokenUtil.createRefreshToken(user, refreshTokenData);
        final var newToken = refreshTokenRepository.swapRefreshToken(refreshToken.get(), newRefreshToken);

        return TokenUtil.createToken(user, accessTokenData, newRefreshToken);
    }
}
