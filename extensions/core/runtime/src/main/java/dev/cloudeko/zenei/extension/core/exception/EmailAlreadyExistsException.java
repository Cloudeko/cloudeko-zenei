package dev.cloudeko.zenei.extension.core.exception;

public class EmailAlreadyExistsException extends BusinessException {

  public EmailAlreadyExistsException() {
    super(3, "email already exists");
  }
}
