package io.biza.deepthought.admin.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.admin.api.ProductEligibilityAdminApi;
import io.biza.deepthought.admin.api.delegate.ProductEligibilityAdminApiDelegate;

@Controller
public class ProductEligibilityAdminApiController implements ProductEligibilityAdminApi {
  private final ProductEligibilityAdminApiDelegate delegate;


  public ProductEligibilityAdminApiController(
      @Autowired(required = false) ProductEligibilityAdminApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new ProductEligibilityAdminApiDelegate() {

    });
  }

  @Override
  public ProductEligibilityAdminApiDelegate getDelegate() {
    return delegate;
  }
}
