package dev.cloudeko.zenei.extension.core.exception;

public class InvalidRefreshTokenException extends BusinessException {
    public InvalidRefreshTokenException() {
        super(5, "invalid refresh token");
    }
}
