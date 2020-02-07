package io.biza.deepthought.admin.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.admin.api.ProductCardArtAdminApi;
import io.biza.deepthought.admin.api.delegate.ProductCardArtAdminApiDelegate;

@Controller
public class ProductCardArtAdminApiController implements ProductCardArtAdminApi {
  private final ProductCardArtAdminApiDelegate delegate;


  public ProductCardArtAdminApiController(
      @Autowired(required = false) ProductCardArtAdminApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new ProductCardArtAdminApiDelegate() {

    });
  }

  @Override
  public ProductCardArtAdminApiDelegate getDelegate() {
    return delegate;
  }
}
