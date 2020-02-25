package io.biza.deepthought.banking.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.banking.api.BankingPayeeApi;
import io.biza.deepthought.banking.api.delegate.BankingPayeeApiDelegate;

@Controller
public class BankingPayeeApiController implements BankingPayeeApi {

  private final BankingPayeeApiDelegate delegate;

  public BankingPayeeApiController(@Autowired(required = false) BankingPayeeApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new BankingPayeeApiDelegate() {

    });
  }

  @Override
  public BankingPayeeApiDelegate getDelegate() {
    return delegate;
  }

}
