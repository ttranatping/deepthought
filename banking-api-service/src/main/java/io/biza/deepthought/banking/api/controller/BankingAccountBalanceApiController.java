package io.biza.deepthought.banking.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.banking.api.BankingAccountBalanceApi;
import io.biza.deepthought.banking.api.delegate.BankingAccountBalanceApiDelegate;

@Controller
public class BankingAccountBalanceApiController implements BankingAccountBalanceApi {

  private final BankingAccountBalanceApiDelegate delegate;

  public BankingAccountBalanceApiController(@Autowired(required = false) BankingAccountBalanceApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new BankingAccountBalanceApiDelegate() {

    });
  }

  @Override
  public BankingAccountBalanceApiDelegate getDelegate() {
    return delegate;
  }

}
