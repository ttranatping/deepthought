package io.biza.deepthought.banking.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.banking.api.BankingAccountDirectDebitApi;
import io.biza.deepthought.banking.api.delegate.BankingAccountDirectDebitApiDelegate;

@Controller
public class BankingAccountDirectDebitApiController implements BankingAccountDirectDebitApi {

  private final BankingAccountDirectDebitApiDelegate delegate;

  public BankingAccountDirectDebitApiController(@Autowired(required = false) BankingAccountDirectDebitApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new BankingAccountDirectDebitApiDelegate() {

    });
  }

  @Override
  public BankingAccountDirectDebitApiDelegate getDelegate() {
    return delegate;
  }

}
