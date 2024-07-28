package dev.cloudeko.zenei.domain.feature.impl;

import dev.cloudeko.zenei.domain.exception.InvalidRefreshTokenException;
import dev.cloudeko.zenei.domain.feature.RefreshAccessToken;
import dev.cloudeko.zenei.domain.feature.util.TokenUtil;
import dev.cloudeko.zenei.domain.model.Token;
import dev.cloudeko.zenei.domain.model.token.RefreshToken;
import dev.cloudeko.zenei.domain.model.token.RefreshTokenInput;
import dev.cloudeko.zenei.domain.model.token.RefreshTokenRepository;
import dev.cloudeko.zenei.domain.model.user.User;
import dev.cloudeko.zenei.domain.model.user.UserPasswordRepository;
import dev.cloudeko.zenei.domain.model.user.UserRepository;
import dev.cloudeko.zenei.domain.provider.HashProvider;
import dev.cloudeko.zenei.domain.provider.RefreshTokenProvider;
import dev.cloudeko.zenei.domain.provider.TokenProvider;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

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

        final var user = userRepository.getUserById(refreshToken.get().getUserId());

        if (user.isEmpty()) {
            // This should never happen
            throw new RuntimeException();
        }

        final var refreshTokenData = refreshTokenProvider.generateRefreshToken(user.get());
        final var accessTokenData = tokenProvider.generateToken(user.get());

        final var newRefreshToken = TokenUtil.createRefreshToken(user.get(), refreshTokenData);
        final var newToken = refreshTokenRepository.swapRefreshToken(refreshToken.get(), newRefreshToken);

        return TokenUtil.createToken(user.get(), accessTokenData, newRefreshToken);
    }
}
