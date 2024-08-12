package dev.cloudeko.zenei.domain.feature;

import dev.cloudeko.zenei.domain.model.user.User;

import java.util.List;

/**
 * The {@code ListUsers} interface represents a contract for retrieving a list of users based on the provided offset and limit.
 * It defines a single method {@code listUsers} which takes the offset and limit as parameters and returns a list of User
 * objects.
 * <p>
 * Implementations of this interface should handle the logic for retrieving the users from a data source and returning the
 * list.
 *
 * @see User
 */
public interface ListUsers {
    /**
     * Retrieves a list of users based on the provided offset and limit.
     *
     * @param offset the starting position of the users in the list (inclusive)
     * @param limit the maximum number of users to retrieve
     * @return a {@code List} of {@link User} objects representing the users in the specified range
     */
    List<User> listUsers(int offset, int limit);
}
