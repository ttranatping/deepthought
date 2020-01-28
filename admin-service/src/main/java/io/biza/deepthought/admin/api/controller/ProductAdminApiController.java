package io.biza.deepthought.admin.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.admin.api.ProductAdminApi;
import io.biza.deepthought.admin.api.delegate.ProductAdminApiDelegate;

@Controller
public class ProductAdminApiController implements ProductAdminApi {
  private final ProductAdminApiDelegate delegate;


  public ProductAdminApiController(@Autowired(required = false) ProductAdminApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new ProductAdminApiDelegate() {

    });
  }

  @Override
  public ProductAdminApiDelegate getDelegate() {
    return delegate;
  }
}
