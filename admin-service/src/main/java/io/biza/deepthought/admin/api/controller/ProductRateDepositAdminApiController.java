package io.biza.deepthought.admin.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.admin.api.ProductRateDepositAdminApi;
import io.biza.deepthought.admin.api.delegate.ProductRateDepositAdminApiDelegate;

@Controller
public class ProductRateDepositAdminApiController implements ProductRateDepositAdminApi {
  private final ProductRateDepositAdminApiDelegate delegate;


  public ProductRateDepositAdminApiController(
      @Autowired(required = false) ProductRateDepositAdminApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new ProductRateDepositAdminApiDelegate() {

    });
  }

  @Override
  public ProductRateDepositAdminApiDelegate getDelegate() {
    return delegate;
  }
}
