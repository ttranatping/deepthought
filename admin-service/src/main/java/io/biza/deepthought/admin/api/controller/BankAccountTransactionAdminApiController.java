package io.biza.deepthought.admin.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.admin.api.BankAccountTransactionAdminApi;
import io.biza.deepthought.admin.api.delegate.BankAccountTransactionAdminApiDelegate;

@Controller
public class BankAccountTransactionAdminApiController implements BankAccountTransactionAdminApi {
  private final BankAccountTransactionAdminApiDelegate delegate;


  public BankAccountTransactionAdminApiController(@Autowired(required = false) BankAccountTransactionAdminApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new BankAccountTransactionAdminApiDelegate() {

    });
  }

  @Override
  public BankAccountTransactionAdminApiDelegate getDelegate() {
    return delegate;
  }
}
