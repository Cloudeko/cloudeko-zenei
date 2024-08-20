package dev.cloudeko.zenei.extension.core.exception;

public class InvalidExternalAuthProvider extends BusinessException {
    public InvalidExternalAuthProvider() {
        super(4, "invalid external auth provider");
    }
}