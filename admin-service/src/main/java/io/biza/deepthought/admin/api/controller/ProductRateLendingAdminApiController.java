package io.biza.deepthought.admin.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.admin.api.ProductRateLendingAdminApi;
import io.biza.deepthought.admin.api.delegate.ProductRateLendingAdminApiDelegate;

@Controller
public class ProductRateLendingAdminApiController implements ProductRateLendingAdminApi {
  private final ProductRateLendingAdminApiDelegate delegate;


  public ProductRateLendingAdminApiController(
      @Autowired(required = false) ProductRateLendingAdminApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new ProductRateLendingAdminApiDelegate() {

    });
  }

  @Override
  public ProductRateLendingAdminApiDelegate getDelegate() {
    return delegate;
  }
}
