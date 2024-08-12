package dev.cloudeko.zenei.domain.feature;

import dev.cloudeko.zenei.infrastructure.config.DefaultUserConfig;

public interface CreateDefaultUser {
    void handle(DefaultUserConfig config);
}
