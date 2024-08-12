package dev.cloudeko.zenei.infrastructure.config;

public interface OAuthProviderConfig {

    String clientId();

    String clientSecret();

    String authorizationUri();

    String tokenUri();

    String userInfoUri();

    String redirectUri();

    String scope();
}
