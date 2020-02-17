package io.biza.deepthought.admin.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.admin.api.CustomerAdminApi;
import io.biza.deepthought.admin.api.delegate.CustomerAdminApiDelegate;

@Controller
public class CustomerAdminApiController implements CustomerAdminApi {
  private final CustomerAdminApiDelegate delegate;


  public CustomerAdminApiController(@Autowired(required = false) CustomerAdminApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new CustomerAdminApiDelegate() {

    });
  }

  @Override
  public CustomerAdminApiDelegate getDelegate() {
    return delegate;
  }
}
