package io.biza.deepthought.admin.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.admin.api.BankAccountAdminApi;
import io.biza.deepthought.admin.api.delegate.BankAccountAdminApiDelegate;

@Controller
public class BankAccountAdminApiController implements BankAccountAdminApi {
  private final BankAccountAdminApiDelegate delegate;


  public BankAccountAdminApiController(@Autowired(required = false) BankAccountAdminApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new BankAccountAdminApiDelegate() {

    });
  }

  @Override
  public BankAccountAdminApiDelegate getDelegate() {
    return delegate;
  }
}
