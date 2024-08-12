package dev.cloudeko.zenei.domain.feature.impl;

import dev.cloudeko.zenei.domain.feature.LoginUserWithAuthorizationCode;
import dev.cloudeko.zenei.domain.model.Token;
import dev.cloudeko.zenei.domain.web.client.LoginOAuthClient;
import dev.cloudeko.zenei.infrastructure.config.ApplicationConfig;
import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.net.URI;

@ApplicationScoped
@AllArgsConstructor
public class LoginUserWithAuthorizationCodeImpl implements LoginUserWithAuthorizationCode {

    private final ApplicationConfig config;

    @Override
    public Token handle(String code) {
        final var provider = config.getOAuthProvidersConfig().providers().values().stream().findFirst();
        if (provider.isEmpty()) {
            return null;
        }

        final var client = QuarkusRestClientBuilder.newBuilder()
                .baseUri(URI.create(provider.get().tokenUri()))
                .build(LoginOAuthClient.class);

        final var accessToken = client.getAccessToken(provider.get().clientId(), provider.get().clientSecret(), code, null);

        if (accessToken == null) {
            throw new IllegalArgumentException("Invalid authorization code");
        }

        return Token.builder().build();
    }
}
