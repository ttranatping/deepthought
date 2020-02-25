package io.biza.deepthought.product.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.product.api.ProductApi;
import io.biza.deepthought.product.api.delegate.ProductApiDelegate;

@Controller
public class ProductApiController implements ProductApi {
  private final ProductApiDelegate delegate;


  public ProductApiController(@Autowired(required = false) ProductApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new ProductApiDelegate() {

    });
  }

  @Override
  public ProductApiDelegate getDelegate() {
    return delegate;
  }
}
