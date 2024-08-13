package dev.cloudeko.zenei.infrastructure.provider.external;

import dev.cloudeko.zenei.domain.exception.InvalidExternalAuthProvider;
import dev.cloudeko.zenei.domain.provider.ExternalAuthProvider;
import dev.cloudeko.zenei.domain.provider.ExternalAuthenticationProvider;
import dev.cloudeko.zenei.infrastructure.config.ApplicationConfig;
import dev.cloudeko.zenei.infrastructure.config.ExternalAuthProviderConfig;
import dev.cloudeko.zenei.infrastructure.provider.external.auth.GithubExternalAuthProvider;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class ConfigurationExternalAuthenticationProvider implements ExternalAuthenticationProvider {

    private final ApplicationConfig applicationConfig;

    @Override
    public ExternalAuthProvider getAuthProvider(String providerName) {
        final var providerConfig = applicationConfig.getExternalAuthProvidersConfig().providers().get(providerName);

        if (providerConfig == null) {
            throw new InvalidExternalAuthProvider();
        }

        return createAuthProvider(providerName, providerConfig);
    }

    private ExternalAuthProvider createAuthProvider(String providerName, ExternalAuthProviderConfig providerConfig) {
        switch (providerName) {
            case "github": {
                return new GithubExternalAuthProvider(providerConfig.baseUri());
            }

            default: {
                throw new InvalidExternalAuthProvider();
            }
        }
    }
}
