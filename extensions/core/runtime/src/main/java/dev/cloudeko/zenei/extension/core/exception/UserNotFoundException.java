package dev.cloudeko.zenei.extension.core.exception;

public class UserNotFoundException extends BusinessException {
  public UserNotFoundException() {
    super(1, "user not found");
  }
}
