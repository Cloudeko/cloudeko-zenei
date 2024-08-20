package dev.cloudeko.zenei.domain.exception;

public class InvalidRefreshTokenException extends BusinessException {
    public InvalidRefreshTokenException() {
        super(5, "invalid refresh token");
    }
}
