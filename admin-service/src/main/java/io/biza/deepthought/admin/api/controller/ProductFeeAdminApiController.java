package io.biza.deepthought.admin.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.admin.api.ProductFeeAdminApi;
import io.biza.deepthought.admin.api.delegate.ProductFeeAdminApiDelegate;

@Controller
public class ProductFeeAdminApiController implements ProductFeeAdminApi {
  private final ProductFeeAdminApiDelegate delegate;


  public ProductFeeAdminApiController(
      @Autowired(required = false) ProductFeeAdminApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new ProductFeeAdminApiDelegate() {

    });
  }

  @Override
  public ProductFeeAdminApiDelegate getDelegate() {
    return delegate;
  }
}
