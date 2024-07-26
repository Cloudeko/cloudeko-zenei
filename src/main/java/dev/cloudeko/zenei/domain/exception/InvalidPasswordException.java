package dev.cloudeko.zenei.domain.exception;

public class InvalidPasswordException extends BusinessException {
  public InvalidPasswordException() {
    super(4, "invalid password");
  }
}
