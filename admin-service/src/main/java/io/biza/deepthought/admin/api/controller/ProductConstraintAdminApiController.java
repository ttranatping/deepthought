package io.biza.deepthought.admin.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.admin.api.ProductConstraintAdminApi;
import io.biza.deepthought.admin.api.delegate.ProductConstraintAdminApiDelegate;

@Controller
public class ProductConstraintAdminApiController implements ProductConstraintAdminApi {
  private final ProductConstraintAdminApiDelegate delegate;


  public ProductConstraintAdminApiController(
      @Autowired(required = false) ProductConstraintAdminApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new ProductConstraintAdminApiDelegate() {

    });
  }

  @Override
  public ProductConstraintAdminApiDelegate getDelegate() {
    return delegate;
  }
}
