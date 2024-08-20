package dev.cloudeko.zenei.extension.core.feature;

import dev.cloudeko.zenei.extension.core.model.user.User;

/**
 * The {@code FindUserByIdentifier} interface represents a contract for finding a User object based on a provided identifier. It
 * defines a single method {@code handle} which takes a {@link Long} identifier as a parameter and returns the corresponding
 * {@link User} object if found.
 *
 * <p>Implementations of this interface should handle the retrieval of a User object based on the given identifier. If a user
 * with the provided identifier is found, the corresponding
 * User object should be returned. Otherwise, an appropriate exception should be thrown.</p>
 *
 * @see User
 */
public interface FindUserByIdentifier {
    /**
     * The {@code handle} method handles the retrieval of a User object based on the provided identifier. It takes a Long
     * identifier as a parameter and returns the corresponding User object if found.
     *
     * @param identifier the identifier of the user
     * @return the User object corresponding to the provided identifier
     */
    User handle(long identifier);
}
