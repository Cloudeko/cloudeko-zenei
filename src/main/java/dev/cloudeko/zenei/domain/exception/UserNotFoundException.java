package dev.cloudeko.zenei.domain.exception;

public class UserNotFoundException extends BusinessException {

  public UserNotFoundException() {
    super(1, "user not found");
  }
}
