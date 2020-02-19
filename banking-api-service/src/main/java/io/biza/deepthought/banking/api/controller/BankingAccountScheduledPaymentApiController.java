package io.biza.deepthought.banking.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.banking.api.BankingAccountScheduledPaymentApi;
import io.biza.deepthought.banking.api.delegate.BankingAccountScheduledPaymentApiDelegate;

@Controller
public class BankingAccountScheduledPaymentApiController implements BankingAccountScheduledPaymentApi {

  private final BankingAccountScheduledPaymentApiDelegate delegate;

  public BankingAccountScheduledPaymentApiController(@Autowired(required = false) BankingAccountScheduledPaymentApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new BankingAccountScheduledPaymentApiDelegate() {

    });
  }

  @Override
  public BankingAccountScheduledPaymentApiDelegate getDelegate() {
    return delegate;
  }

}
