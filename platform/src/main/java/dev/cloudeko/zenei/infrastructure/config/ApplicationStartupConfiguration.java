package dev.cloudeko.zenei.infrastructure.config;

import dev.cloudeko.zenei.extension.core.config.ApplicationConfig;
import dev.cloudeko.zenei.extension.core.feature.CreateDefaultUser;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class ApplicationStartupConfiguration {

    private final ApplicationConfig applicationConfig;
    private final CreateDefaultUser createDefaultUser;

    @Startup
    @Transactional
    public void onStart() {
        applicationConfig.getDefaultUsersConfig().users().values().forEach(createDefaultUser::handle);
    }
}
