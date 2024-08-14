package dev.cloudeko.zenei.extension.external.providers;

import dev.cloudeko.zenei.extension.external.ExternalAuthProvider;
import dev.cloudeko.zenei.extension.external.ExternalAuthResolver;
import dev.cloudeko.zenei.extension.external.config.ExternalAuthProviderConfig;
import dev.cloudeko.zenei.extension.external.config.ExternalAuthProvidersConfig;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class ConfigurationExternalAuthResolver implements ExternalAuthResolver {

    private final ExternalAuthProvidersConfig config;

    @Override
    public Optional<ExternalAuthProvider> getAuthProvider(String providerName) {
        final var providerConfig = config.providers().get(providerName);

        if (providerConfig == null || !providerConfig.enabled()) {
            return Optional.empty();
        }

        return Optional.ofNullable(createAuthProvider(providerName, providerConfig));
    }

    private ExternalAuthProvider createAuthProvider(String providerName, ExternalAuthProviderConfig providerConfig) {
        return switch (providerName) {
            case "github": {
                yield new GithubExternalAuthProvider(providerConfig);
            }

            case "discord": {
                yield new DiscordExternalAuthProvider(providerConfig);
            }

            default: {
                yield null;
            }
        };
    }
}
