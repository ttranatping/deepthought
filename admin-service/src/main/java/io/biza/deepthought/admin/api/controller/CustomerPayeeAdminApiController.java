package io.biza.deepthought.admin.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.admin.api.CustomerPayeeAdminApi;
import io.biza.deepthought.admin.api.delegate.CustomerPayeeAdminApiDelegate;

@Controller
public class CustomerPayeeAdminApiController implements CustomerPayeeAdminApi {
  private final CustomerPayeeAdminApiDelegate delegate;


  public CustomerPayeeAdminApiController(@Autowired(required = false) CustomerPayeeAdminApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new CustomerPayeeAdminApiDelegate() {

    });
  }

  @Override
  public CustomerPayeeAdminApiDelegate getDelegate() {
    return delegate;
  }
}
