package io.biza.deepthought.admin.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.admin.api.CustomerAccountAdminApi;
import io.biza.deepthought.admin.api.delegate.CustomerAccountAdminApiDelegate;

@Controller
public class CustomerAccountAdminApiController implements CustomerAccountAdminApi {
  private final CustomerAccountAdminApiDelegate delegate;


  public CustomerAccountAdminApiController(@Autowired(required = false) CustomerAccountAdminApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new CustomerAccountAdminApiDelegate() {

    });
  }

  @Override
  public CustomerAccountAdminApiDelegate getDelegate() {
    return delegate;
  }
}
