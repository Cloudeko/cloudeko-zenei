package dev.cloudeko.zenei.extension.core.feature;

import dev.cloudeko.zenei.extension.core.model.session.SessionToken;

/**
 * Interface for handling user login with a password.
 */
public interface LoginUserWithPassword {

    /**
     * Handles the login process for a user using their identifier and password.
     *
     * @param identifier the unique identifier of the user (e.g., username or email)
     * @param password the password of the user
     * @return a SessionToken representing the user's session
     */
    SessionToken handle(String identifier, String password);
}
