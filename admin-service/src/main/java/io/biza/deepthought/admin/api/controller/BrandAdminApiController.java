package io.biza.deepthought.admin.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.admin.api.BrandAdminApi;
import io.biza.deepthought.admin.api.delegate.BrandAdminApiDelegate;

@Controller
public class BrandAdminApiController implements BrandAdminApi {
  private final BrandAdminApiDelegate delegate;


  public BrandAdminApiController(@Autowired(required = false) BrandAdminApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new BrandAdminApiDelegate() {

    });
  }

  @Override
  public BrandAdminApiDelegate getDelegate() {
    return delegate;
  }
}
