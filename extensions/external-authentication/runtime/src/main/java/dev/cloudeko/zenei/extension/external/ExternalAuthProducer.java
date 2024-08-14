package dev.cloudeko.zenei.extension.external;

import dev.cloudeko.zenei.extension.external.config.ExternalAuthProvidersConfig;
import dev.cloudeko.zenei.extension.external.providers.ConfigurationExternalAuthResolver;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class ExternalAuthProducer {

    private final ExternalAuthProvidersConfig configuration;

    @Default
    @Produces
    public ExternalAuthResolver getExternalAuthenticationProducer() {
        return new ConfigurationExternalAuthResolver(configuration);
    }
}
