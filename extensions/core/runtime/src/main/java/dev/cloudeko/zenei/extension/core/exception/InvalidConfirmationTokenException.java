package dev.cloudeko.zenei.extension.core.exception;

public class InvalidConfirmationTokenException extends BusinessException {
    public InvalidConfirmationTokenException() {
        super(12, "invalid confirmation token");
    }
}
