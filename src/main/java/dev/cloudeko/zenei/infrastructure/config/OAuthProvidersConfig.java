package dev.cloudeko.zenei.infrastructure.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithParentName;

import java.util.Map;

@ConfigMapping(prefix = "zenei.oauth.providers")
public interface OAuthProvidersConfig {

    @WithParentName
    Map<String, OAuthProviderConfig> providers();
}
