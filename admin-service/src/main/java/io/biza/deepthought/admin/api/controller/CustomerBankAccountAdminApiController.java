package io.biza.deepthought.admin.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.admin.api.CustomerBankAccountAdminApi;
import io.biza.deepthought.admin.api.delegate.CustomerBankAccountAdminApiDelegate;

@Controller
public class CustomerBankAccountAdminApiController implements CustomerBankAccountAdminApi {
  private final CustomerBankAccountAdminApiDelegate delegate;


  public CustomerBankAccountAdminApiController(@Autowired(required = false) CustomerBankAccountAdminApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new CustomerBankAccountAdminApiDelegate() {

    });
  }

  @Override
  public CustomerBankAccountAdminApiDelegate getDelegate() {
    return delegate;
  }
}
