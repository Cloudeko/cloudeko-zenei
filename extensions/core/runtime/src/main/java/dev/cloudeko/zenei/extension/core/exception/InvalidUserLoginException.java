package dev.cloudeko.zenei.extension.core.exception;

public class InvalidUserLoginException extends BusinessException {
    public InvalidUserLoginException() {
        super(4, "invalid user login");
    }
}
