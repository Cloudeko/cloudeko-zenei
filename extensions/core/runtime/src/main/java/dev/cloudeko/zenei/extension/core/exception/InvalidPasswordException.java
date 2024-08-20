package dev.cloudeko.zenei.extension.core.exception;

public class InvalidPasswordException extends BusinessException {
  public InvalidPasswordException() {
    super(4, "invalid password");
  }
}
