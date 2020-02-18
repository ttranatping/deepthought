package io.biza.deepthought.admin.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.admin.api.BankAccountDirectDebitAdminApi;
import io.biza.deepthought.admin.api.BankAccountTransactionAdminApi;
import io.biza.deepthought.admin.api.delegate.BankAccountDirectDebitAdminApiDelegate;
import io.biza.deepthought.admin.api.delegate.BankAccountTransactionAdminApiDelegate;

@Controller
public class BankAccountDirectDebitAdminApiController implements BankAccountDirectDebitAdminApi {
  private final BankAccountDirectDebitAdminApiDelegate delegate;


  public BankAccountDirectDebitAdminApiController(@Autowired(required = false) BankAccountDirectDebitAdminApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new BankAccountDirectDebitAdminApiDelegate() {

    });
  }

  @Override
  public BankAccountDirectDebitAdminApiDelegate getDelegate() {
    return delegate;
  }
}
