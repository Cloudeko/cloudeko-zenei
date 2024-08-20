package dev.cloudeko.zenei.extension.core.exception;

public class EmailNotFoundException extends BusinessException {

    public EmailNotFoundException() {
        super(3, "email not found");
    }
}
