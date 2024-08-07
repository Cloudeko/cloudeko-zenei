package dev.cloudeko.zenei.domain.exception;

public class InvalidUserLoginException extends BusinessException {
    public InvalidUserLoginException() {
        super(4, "invalid user login");
    }
}
