package dev.cloudeko.zenei.extension.external.config;

import io.smallrye.config.WithDefault;
import io.smallrye.config.WithName;

import java.util.Optional;

/**
 * Configuration for a single external authentication provider in Zenei. This class encapsulates the configuration settings
 * required for an individual external authentication provider.
 */
public interface ExternalAuthProviderConfig {

    /**
     * Indicates whether the external authentication provider is enabled or not. Defaults to 'true'.
     *
     * @return 'true' if enabled, 'false' otherwise
     */
    @WithName("enabled")
    @WithDefault("true")
    boolean enabled();

    /**
     * The client ID provided by the external authentication provider.
     *
     * @return The client ID
     */
    @WithName("client-id")
    String clientId();

    /**
     * The client secret provided by the external authentication provider.
     *
     * @return The client secret
     */
    @WithName("client-secret")
    String clientSecret();

    /**
     * The authorization URI used by the external authentication provider.
     *
     * @return The authorization URI (optional)
     */
    @WithName("authorization-uri")
    Optional<String> authorizationUri();

    /**
     * The token URI used by the external authentication provider.
     *
     * @return The token URI (optional)
     */
    @WithName("token-uri")
    Optional<String> tokenUri();

    /**
     * The base URI of the external authentication provider's API.
     *
     * @return The base URI (optional)
     */
    @WithName("base-uri")
    Optional<String> baseUri();

    /**
     * The redirect URI to which the external authentication provider should redirect after authentication.
     *
     * @return The redirect URI (optional)
     */
    @WithName("redirect-uri")
    Optional<String> redirectUri();

    /**
     * The scope of access requested from the external authentication provider.
     *
     * @return The scope (optional)
     */
    @WithName("scope")
    Optional<String> scope();
}
