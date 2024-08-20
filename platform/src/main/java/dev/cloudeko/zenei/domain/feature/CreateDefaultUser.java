package dev.cloudeko.zenei.domain.feature;

import dev.cloudeko.zenei.infrastructure.config.DefaultUserConfig;

/**
 * The {@code CreateDefaultUser} interface represents a contract for creating a default user based on a configuration. It
 * defines a single method {@code handle} which takes a {@link DefaultUserConfig} object as a parameter and performs the
 * necessary operations to create a default user based on the provided configuration.
 * <p>
 * Implementations of this interface should handle the validation and creation of a default user based on the given
 * configuration. If a user with the same email or username already exists, no action should be taken.
 */
public interface CreateDefaultUser {
    /**
     * The {@code handle} method is used to create a default user based on a given configuration.
     *
     * @param config The {@link DefaultUserConfig} object that contains the user configuration, including email, username,
     * password, and an optional role.
     * @apiNote If a user with the same email or username already exists, no action should be taken.
     */
    void handle(DefaultUserConfig config);
}
