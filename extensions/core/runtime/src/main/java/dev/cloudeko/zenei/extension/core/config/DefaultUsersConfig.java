package dev.cloudeko.zenei.extension.core.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithParentName;

import java.util.Map;

@ConfigMapping(prefix = "zenei.user.default")
public interface DefaultUsersConfig {
    @WithParentName
    Map<String, DefaultUserConfig> users();
}
