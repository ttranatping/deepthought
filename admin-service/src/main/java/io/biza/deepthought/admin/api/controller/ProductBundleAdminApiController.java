package io.biza.deepthought.admin.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.admin.api.ProductBundleAdminApi;
import io.biza.deepthought.admin.api.delegate.ProductBundleAdminApiDelegate;

@Controller
public class ProductBundleAdminApiController implements ProductBundleAdminApi {
  private final ProductBundleAdminApiDelegate delegate;


  public ProductBundleAdminApiController(
      @Autowired(required = false) ProductBundleAdminApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new ProductBundleAdminApiDelegate() {

    });
  }

  @Override
  public ProductBundleAdminApiDelegate getDelegate() {
    return delegate;
  }
}
