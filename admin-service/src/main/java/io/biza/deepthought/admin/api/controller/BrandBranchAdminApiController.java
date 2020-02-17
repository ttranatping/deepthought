package io.biza.deepthought.admin.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.admin.api.BrandAdminApi;
import io.biza.deepthought.admin.api.BrandBranchAdminApi;
import io.biza.deepthought.admin.api.delegate.BrandAdminApiDelegate;
import io.biza.deepthought.admin.api.delegate.BrandBranchAdminApiDelegate;

@Controller
public class BrandBranchAdminApiController implements BrandBranchAdminApi {
  private final BrandBranchAdminApiDelegate delegate;


  public BrandBranchAdminApiController(@Autowired(required = false) BrandBranchAdminApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new BrandBranchAdminApiDelegate() {

    });
  }

  @Override
  public BrandBranchAdminApiDelegate getDelegate() {
    return delegate;
  }
}
