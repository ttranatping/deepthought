package io.biza.deepthought.admin.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.admin.api.CustomerAdminApi;
import io.biza.deepthought.admin.api.CustomerScheduledPaymentAdminApi;
import io.biza.deepthought.admin.api.delegate.CustomerAdminApiDelegate;
import io.biza.deepthought.admin.api.delegate.CustomerScheduledPaymentAdminApiDelegate;

@Controller
public class CustomerScheduledPaymentAdminApiController implements CustomerScheduledPaymentAdminApi {
  private final CustomerScheduledPaymentAdminApiDelegate delegate;


  public CustomerScheduledPaymentAdminApiController(@Autowired(required = false) CustomerScheduledPaymentAdminApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new CustomerScheduledPaymentAdminApiDelegate() {

    });
  }

  @Override
  public CustomerScheduledPaymentAdminApiDelegate getDelegate() {
    return delegate;
  }

}
