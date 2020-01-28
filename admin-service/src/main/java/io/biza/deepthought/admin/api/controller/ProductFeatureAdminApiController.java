package io.biza.deepthought.admin.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.admin.api.ProductFeatureAdminApi;
import io.biza.deepthought.admin.api.delegate.ProductFeatureAdminApiDelegate;

@Controller
public class ProductFeatureAdminApiController implements ProductFeatureAdminApi {
  private final ProductFeatureAdminApiDelegate delegate;


  public ProductFeatureAdminApiController(
      @Autowired(required = false) ProductFeatureAdminApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new ProductFeatureAdminApiDelegate() {

    });
  }

  @Override
  public ProductFeatureAdminApiDelegate getDelegate() {
    return delegate;
  }
}
