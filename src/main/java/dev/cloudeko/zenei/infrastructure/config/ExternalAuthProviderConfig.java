package dev.cloudeko.zenei.infrastructure.config;

public interface ExternalAuthProviderConfig {

    String clientId();

    String clientSecret();

    String authorizationUri();

    String tokenUri();

    String baseUri();

    String redirectUri();

    String scope();
}
