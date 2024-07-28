package dev.cloudeko.zenei.domain.feature.impl;

import dev.cloudeko.zenei.domain.exception.EmailAlreadyExistsException;
import dev.cloudeko.zenei.domain.exception.UserNotFoundException;
import dev.cloudeko.zenei.domain.feature.LoginUserWithPassword;
import dev.cloudeko.zenei.domain.feature.util.TokenUtil;
import dev.cloudeko.zenei.domain.model.Token;
import dev.cloudeko.zenei.domain.model.token.LoginPasswordInput;
import dev.cloudeko.zenei.domain.model.token.RefreshToken;
import dev.cloudeko.zenei.domain.model.token.RefreshTokenRepository;
import dev.cloudeko.zenei.domain.model.user.User;
import dev.cloudeko.zenei.domain.model.user.UserPassword;
import dev.cloudeko.zenei.domain.model.user.UserPasswordRepository;
import dev.cloudeko.zenei.domain.model.user.UserRepository;
import dev.cloudeko.zenei.domain.provider.HashProvider;
import dev.cloudeko.zenei.domain.provider.RefreshTokenProvider;
import dev.cloudeko.zenei.domain.provider.TokenProvider;
import dev.cloudeko.zenei.infrastructure.provider.BCryptRefreshTokenProvider;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

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
    public Token handle(LoginPasswordInput loginPasswordInput) {
        final var userPassword = userPasswordRepository.getUserPasswordByEmail(loginPasswordInput.getEmail());

        if (userPassword.isEmpty()) {
            throw new UserNotFoundException();
        }

        if (hashProvider.checkPassword(loginPasswordInput.getPassword(), userPassword.get().getPasswordHash())) {
            final var user = userRepository.getUserById(userPassword.get().getUser());

            if (user.isEmpty()) {
                // This should never happen
                throw new UserNotFoundException();
            }

            final var refreshTokenData = refreshTokenProvider.generateRefreshToken(user.get());
            final var accessTokenData = tokenProvider.generateToken(user.get());

            final var refreshToken = TokenUtil.createRefreshToken(user.get(), refreshTokenData);
            final var token = refreshTokenRepository.createRefreshToken(refreshToken);

            return TokenUtil.createToken(user.get(), accessTokenData, refreshToken);
        } else {
            throw new UserNotFoundException();
        }
    }
}
