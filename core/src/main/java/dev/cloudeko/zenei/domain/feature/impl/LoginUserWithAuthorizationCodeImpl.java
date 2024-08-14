package dev.cloudeko.zenei.domain.feature.impl;

import dev.cloudeko.zenei.domain.exception.EmailAlreadyExistsException;
import dev.cloudeko.zenei.domain.exception.EmailNotFoundException;
import dev.cloudeko.zenei.domain.exception.InvalidExternalAuthProvider;
import dev.cloudeko.zenei.domain.exception.UsernameAlreadyExistsException;
import dev.cloudeko.zenei.domain.feature.LoginUserWithAuthorizationCode;
import dev.cloudeko.zenei.domain.feature.util.TokenUtil;
import dev.cloudeko.zenei.domain.model.Token;
import dev.cloudeko.zenei.domain.model.account.Account;
import dev.cloudeko.zenei.domain.model.account.AccountRepository;
import dev.cloudeko.zenei.domain.model.email.EmailAddress;
import dev.cloudeko.zenei.domain.model.token.RefreshTokenRepository;
import dev.cloudeko.zenei.domain.model.user.User;
import dev.cloudeko.zenei.domain.model.user.UserRepository;
import dev.cloudeko.zenei.domain.provider.HashProvider;
import dev.cloudeko.zenei.domain.provider.RefreshTokenProvider;
import dev.cloudeko.zenei.domain.provider.TokenProvider;
import dev.cloudeko.zenei.extension.external.ExternalAuthProvider;
import dev.cloudeko.zenei.extension.external.ExternalAuthResolver;
import dev.cloudeko.zenei.extension.external.ExternalUserProfile;
import dev.cloudeko.zenei.extension.external.web.client.ExternalAccessToken;
import dev.cloudeko.zenei.extension.external.web.client.LoginOAuthClient;
import dev.cloudeko.zenei.infrastructure.config.ApplicationConfig;
import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@AllArgsConstructor
public class LoginUserWithAuthorizationCodeImpl implements LoginUserWithAuthorizationCode {

    private final ApplicationConfig config;

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final RefreshTokenProvider refreshTokenProvider;
    private final ExternalAuthResolver externalAuthenticationProvider;
    private final TokenProvider tokenProvider;
    private final HashProvider hashProvider;

    @Override
    public Token handle(String provider, String code) {
        final var externalProvider = getExternalAuthProvider(provider);

        final var client = QuarkusRestClientBuilder.newBuilder()
                .baseUri(URI.create(externalProvider.getTokenEndpoint()))
                .build(LoginOAuthClient.class);

        final var accessToken = client.getAccessToken("authorization_code",
                externalProvider.config().clientId(),
                externalProvider.config().clientSecret(),
                code,
                externalProvider.config().redirectUri().orElseThrow());

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

    private ExternalAuthProvider getExternalAuthProvider(String provider) {
        return externalAuthenticationProvider.getAuthProvider(provider).orElseThrow(InvalidExternalAuthProvider::new);
    }

    private Optional<ExternalUserProfile.ExternalUserEmail> getEmailFromUserProfile(ExternalUserProfile externalUserProfile) {
        return externalUserProfile.getEmails().stream().filter(email -> email.primary() && email.verified()).findFirst();
    }

    private User createUserFromExternalUserProfile(ExternalUserProfile externalUserProfile,
            ExternalUserProfile.ExternalUserEmail externalEmail,
            String provider,
            ExternalAccessToken accessToken) {

        final var emailAddress = EmailAddress.builder().email(externalEmail.email()).emailVerified(true).build();
        final var account = Account.builder()
                .provider(provider)
                .providerId(externalUserProfile.getId())
                .scope(accessToken.getScope())
                .accessToken(accessToken.getAccessToken())
                .refreshToken(accessToken.getRefreshToken())
                .tokenType(accessToken.getTokenType())
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