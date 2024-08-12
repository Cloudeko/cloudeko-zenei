package dev.cloudeko.zenei.domain.feature;

import dev.cloudeko.zenei.domain.model.Token;
import dev.cloudeko.zenei.domain.model.token.LoginPasswordInput;

/**
 * The {@code LoginUserWithPassword} interface represents a contract for authenticating a user with a password. It defines a
 * single method {@code handle} which takes a {@link LoginPasswordInput} object as a parameter and returns a {@link Token}
 * object if the authentication is successful.
 * <p>
 * Implementations of this interface should handle the authentication process by verifying the user's password. If the password
 * is valid, a token should be generated and returned . Otherwise, an appropriate exception should be thrown.
 * </p>
 * The {@link Token} object represents an authentication token and contains information such as the access token, token type,
 * and expiration time.
 *
 * @see Token
 * @see LoginPasswordInput
 */
public interface LoginUserWithPassword {
    /**
     * The {@code handle} method is a part of the {@code LoginUserWithPassword} interface. It takes a {@link LoginPasswordInput}
     * object as a parameter and returns a {@link Token} object if the authentication is successful.
     *
     * <p>Implementations of this method should handle the authentication process by verifying the user's password. If the
     * password
     * is valid, a token should be generated and returned. Otherwise, an appropriate exception should be thrown.</p>
     *
     * @param loginPasswordInput the input object that contains the user's login credentials (email and password)
     * @return the authentication token if the authentication is successful
     * @see Token
     * @see LoginPasswordInput
     */
    Token handle(LoginPasswordInput loginPasswordInput);
}
