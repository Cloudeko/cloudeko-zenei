package dev.cloudeko.zenei.extension.core.feature.impl;

import dev.cloudeko.zenei.extension.core.exception.*;
import dev.cloudeko.zenei.extension.core.feature.AuthorizationManager;
import dev.cloudeko.zenei.extension.core.feature.util.TokenUtil;
import dev.cloudeko.zenei.extension.core.model.account.ExternalAccessToken;
import dev.cloudeko.zenei.extension.core.model.account.ExternalAccount;
import dev.cloudeko.zenei.extension.core.model.email.EmailAddress;
import dev.cloudeko.zenei.extension.core.model.session.SessionToken;
import dev.cloudeko.zenei.extension.core.model.user.User;
import dev.cloudeko.zenei.extension.core.provider.HashProvider;
import dev.cloudeko.zenei.extension.core.provider.RefreshTokenProvider;
import dev.cloudeko.zenei.extension.core.provider.TokenProvider;
import dev.cloudeko.zenei.extension.core.repository.RefreshTokenRepository;
import dev.cloudeko.zenei.extension.core.repository.UserPasswordRepository;
import dev.cloudeko.zenei.extension.core.repository.UserRepository;
import dev.cloudeko.zenei.extension.external.ExternalAuthProvider;
import dev.cloudeko.zenei.extension.external.ExternalAuthResolver;
import dev.cloudeko.zenei.extension.external.ExternalUserProfile;
import dev.cloudeko.zenei.extension.external.providers.AvailableProvider;
import dev.cloudeko.zenei.extension.external.web.client.ExternalProviderAccessToken;
import dev.cloudeko.zenei.extension.external.web.client.LoginOAuthClient;
import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@AllArgsConstructor
public class DefaultAuthorizationManager implements AuthorizationManager {

    private final UserPasswordRepository userPasswordRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    private final RefreshTokenProvider refreshTokenProvider;
    private final ExternalAuthResolver externalAuthenticationProvider;
    private final TokenProvider tokenProvider;
    private final HashProvider hashProvider;

    @Override
    public SessionToken loginWithPassword(String identifier, String password) {
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

    @Override
    public SessionToken loginWithAuthorizationCode(String provider, String code) {
        final var externalProvider = getExternalAuthProvider(provider);

        final var client = QuarkusRestClientBuilder.newBuilder()
                .baseUri(URI.create(externalProvider.getTokenEndpoint()))
                .build(LoginOAuthClient.class);

        final var accessToken = client.getAccessToken("authorization_code",
                externalProvider.config().clientId(),
                externalProvider.config().clientSecret(),
                code, AvailableProvider.getProvider(provider).getRedirectUri());

        if (accessToken == null) {
            throw new IllegalArgumentException("Invalid authorization code");
        }

        final var externalUserProfile = externalProvider.getExternalUserProfile(accessToken);

        final var externalEmail = getEmailFromUserProfile(externalUserProfile);

        if (externalEmail.isEmpty()) {
            throw new EmailNotFoundException();
        }

        var user = userRepository.getByAccountProviderId(externalUserProfile.getId())
                .orElse(createUserFromExternalUserProfile(externalUserProfile, externalEmail.get(), provider, accessToken));

        final var refreshTokenData = refreshTokenProvider.generateRefreshToken(user);
        final var accessTokenData = tokenProvider.generateToken(user);

        final var refreshToken = TokenUtil.createRefreshToken(user, refreshTokenData);
        final var token = refreshTokenRepository.createRefreshToken(refreshToken);

        return TokenUtil.createToken(user, accessTokenData, refreshToken);
    }

    @Override
    public SessionToken swapRefreshToken(String refreshToken) {
        final var token = refreshTokenRepository.findRefreshTokenByToken(refreshToken);

        if (token.isEmpty()) {
            throw new InvalidRefreshTokenException();
        }

        final var user = token.get().getUser();

        final var refreshTokenData = refreshTokenProvider.generateRefreshToken(user);
        final var accessTokenData = tokenProvider.generateToken(user);

        final var newRefreshToken = TokenUtil.createRefreshToken(user, refreshTokenData);
        final var newToken = refreshTokenRepository.swapRefreshToken(token.get(), newRefreshToken);
        if (newToken == null) {
            throw new InvalidRefreshTokenException();
        }

        return TokenUtil.createToken(user, accessTokenData, newRefreshToken);
    }

    private ExternalAuthProvider getExternalAuthProvider(String provider) {
        return externalAuthenticationProvider.getAuthProvider(provider).orElseThrow(InvalidExternalAuthProvider::new);
    }

    private Optional<ExternalUserProfile.ExternalUserEmail> getEmailFromUserProfile(ExternalUserProfile externalUserProfile) {
        return externalUserProfile.getEmails().stream().filter(email -> email.primary() && email.verified()).findFirst();
    }

    private User createUserFromExternalUserProfile(ExternalUserProfile externalUserProfile,
            ExternalUserProfile.ExternalUserEmail externalEmail,
            String provider,
            ExternalProviderAccessToken accessToken) {

        final var emailAddress = EmailAddress.builder().email(externalEmail.email()).emailVerified(true).build();
        final var externalAccessToken = ExternalAccessToken.builder()
                .accessToken(accessToken.getAccessToken())
                .refreshToken(accessToken.getRefreshToken())
                .tokenType(accessToken.getTokenType())
                .scope(accessToken.getScope())
                .build();

        final var account = ExternalAccount.builder()
                .provider(provider)
                .providerId(externalUserProfile.getId())
                .accessTokens(new ArrayList<>(List.of(externalAccessToken)))
                .build();

        final var user = User.builder()
                .username(externalUserProfile.getUsername())
                .primaryEmailAddress(emailAddress.getEmail())
                .emailAddresses(new ArrayList<>(List.of(emailAddress)))
                .accounts(new ArrayList<>(List.of(account)))
                .build();

        checkExistingUsername(user.getUsername());
        checkExistingEmail(user.getPrimaryEmailAddress().getEmail());

        userRepository.createUser(user);

        return user;
    }

    private void checkExistingUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyExistsException();
        }
    }

    private void checkExistingEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException();
        }
    }
}
