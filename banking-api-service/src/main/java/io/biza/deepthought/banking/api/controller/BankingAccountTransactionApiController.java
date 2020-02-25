package io.biza.deepthought.banking.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.banking.api.BankingAccountTransactionApi;
import io.biza.deepthought.banking.api.delegate.BankingAccountTransactionApiDelegate;

@Controller
public class BankingAccountTransactionApiController implements BankingAccountTransactionApi {

  private final BankingAccountTransactionApiDelegate delegate;

  public BankingAccountTransactionApiController(@Autowired(required = false) BankingAccountTransactionApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new BankingAccountTransactionApiDelegate() {

    });
  }

  @Override
  public BankingAccountTransactionApiDelegate getDelegate() {
    return delegate;
  }

}
