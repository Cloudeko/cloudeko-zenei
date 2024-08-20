package dev.cloudeko.zenei.domain.feature.impl;

import dev.cloudeko.zenei.domain.exception.InvalidPasswordException;
import dev.cloudeko.zenei.domain.exception.UserNotFoundException;
import dev.cloudeko.zenei.domain.feature.LoginUserWithPassword;
import dev.cloudeko.zenei.domain.feature.util.TokenUtil;
import dev.cloudeko.zenei.domain.provider.HashProvider;
import dev.cloudeko.zenei.domain.provider.RefreshTokenProvider;
import dev.cloudeko.zenei.domain.provider.TokenProvider;
import dev.cloudeko.zenei.extension.core.model.session.SessionToken;
import dev.cloudeko.zenei.extension.core.repository.RefreshTokenRepository;
import dev.cloudeko.zenei.extension.core.repository.UserPasswordRepository;
import dev.cloudeko.zenei.extension.core.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class LoginUserWithPasswordImpl implements LoginUserWithPassword {

    private final UserPasswordRepository userPasswordRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    private final RefreshTokenProvider refreshTokenProvider;
    private final TokenProvider tokenProvider;
    private final HashProvider hashProvider;

    @Override
    public SessionToken handle(String identifier, String password) {
        final var userPassword = userPasswordRepository.getUserPasswordByEmail(identifier);

        if (userPassword.isEmpty()) {
            throw new UserNotFoundException();
        }

        if (hashProvider.checkPassword(password, userPassword.get().getPasswordHash())) {
            final var user = userPassword.get().getUser();

            final var refreshTokenData = refreshTokenProvider.generateRefreshToken(user);
            final var accessTokenData = tokenProvider.generateToken(user);

            final var refreshToken = TokenUtil.createRefreshToken(user, refreshTokenData);
            final var token = refreshTokenRepository.createRefreshToken(refreshToken);

            return TokenUtil.createToken(user, accessTokenData, refreshToken);
        } else {
            throw new InvalidPasswordException();
        }
    }
}
