package io.biza.deepthought.shared.exception;

import lombok.Builder;

@Builder
public class NotFoundException extends Exception {
  private static final long serialVersionUID = 1L;
  
  public NotFoundException() {
    super("Not Found Exception");
  }

  public NotFoundException(String message) {
    super(message);
  }
}
