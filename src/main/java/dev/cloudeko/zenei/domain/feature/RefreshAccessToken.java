package dev.cloudeko.zenei.domain.feature;

import dev.cloudeko.zenei.domain.model.Token;
import dev.cloudeko.zenei.domain.model.token.RefreshTokenInput;

/**
 * The {@code RefreshAccessToken} interface represents a contract for refreshing an access token based on a provided refresh
 * token. It defines a single method {@code handle} which takes a {@link RefreshTokenInput} object as a parameter and returns a
 * {@link Token} object representing the refreshed access token.
 *
 * <p>Implementations of this interface should handle the validation and generation of a new access token based on the provided
 * refresh token. If the refresh token is invalid or expired, an appropriate exception should be thrown. Otherwise, a new access
 * token should be generated and returned.</p>
 *
 * @see Token
 * @see RefreshTokenInput
 */
public interface RefreshAccessToken {
    /**
     * The {@code handle} method is a part of the {@code RefreshAccessToken} interface. It takes a {@link RefreshTokenInput}
     * object as a parameter and returns a {@link Token} object representing the refreshed access token.
     *
     * <p>Implementations of this method should handle the validation and generation of a new access token based on the provided
     * refresh token.
     * If the refresh token is invalid or expired, an appropriate exception should be thrown. Otherwise, a new access token
     * should be generated and returned.</p>
     *
     * @param refreshTokenInput the input object that contains the refresh token
     * @return the refreshed access token
     * @see Token
     * @see RefreshTokenInput
     */
    Token handle(RefreshTokenInput refreshTokenInput);
}
