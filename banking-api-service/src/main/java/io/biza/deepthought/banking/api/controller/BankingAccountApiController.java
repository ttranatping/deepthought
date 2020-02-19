package io.biza.deepthought.banking.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.banking.api.BankingAccountApi;
import io.biza.deepthought.banking.api.delegate.BankingAccountApiDelegate;

@Controller
public class BankingAccountApiController implements BankingAccountApi {

  private final BankingAccountApiDelegate delegate;

  public BankingAccountApiController(@Autowired(required = false) BankingAccountApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new BankingAccountApiDelegate() {

    });
  }

  @Override
  public BankingAccountApiDelegate getDelegate() {
    return delegate;
  }

}
