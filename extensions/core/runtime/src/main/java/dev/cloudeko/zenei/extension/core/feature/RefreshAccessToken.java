package dev.cloudeko.zenei.extension.core.feature;

import dev.cloudeko.zenei.extension.core.model.session.SessionToken;

/**
 * Interface for handling the refresh of an access token using a refresh token.
 */
public interface RefreshAccessToken {

    /**
     * Handles the process of refreshing an access token using a provided refresh token.
     *
     * @param token the refresh token used to obtain a new access token
     * @return a SessionToken representing the new session
     */
    SessionToken handle(String token);
}