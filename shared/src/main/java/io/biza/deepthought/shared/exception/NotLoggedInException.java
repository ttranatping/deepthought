package io.biza.deepthought.shared.exception;

public class NotLoggedInException extends Exception {
  private static final long serialVersionUID = 1L;

  public NotLoggedInException(String message) {
    super(message);
  }
}
