package dev.cloudeko.zenei.domain.exception;

public class InvalidConfirmationTokenException extends BusinessException {
    public InvalidConfirmationTokenException() {
        super(12, "invalid confirmation token");
    }
}
