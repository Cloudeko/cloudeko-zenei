package dev.cloudeko.zenei.extension.external.config;

import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithParentName;

import java.util.Map;

@ConfigRoot(phase = ConfigPhase.RUN_TIME)
@ConfigMapping(prefix = "zenei.external.auth.providers")
public interface ExternalAuthProvidersConfig {

    /**
     * Defines a map of external authentication providers, where the key is the provider name and the value is the provider
     * configuration.
     *
     * @return A map of external authentication provider configurations.
     */
    @WithParentName
    Map<String, ExternalAuthProviderConfig> providers();
}
