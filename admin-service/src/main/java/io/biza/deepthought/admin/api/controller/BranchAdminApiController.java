package io.biza.deepthought.admin.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.admin.api.BranchAdminApi;
import io.biza.deepthought.admin.api.delegate.BranchAdminApiDelegate;

@Controller
public class BranchAdminApiController implements BranchAdminApi {
  private final BranchAdminApiDelegate delegate;


  public BranchAdminApiController(@Autowired(required = false) BranchAdminApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new BranchAdminApiDelegate() {

    });
  }

  @Override
  public BranchAdminApiDelegate getDelegate() {
    return delegate;
  }
}
