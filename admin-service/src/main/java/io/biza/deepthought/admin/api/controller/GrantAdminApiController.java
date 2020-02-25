package io.biza.deepthought.admin.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.admin.api.GrantAdminApi;
import io.biza.deepthought.admin.api.delegate.GrantAdminApiDelegate;

@Controller
public class GrantAdminApiController implements GrantAdminApi {
  private final GrantAdminApiDelegate delegate;


  public GrantAdminApiController(@Autowired(required = false) GrantAdminApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new GrantAdminApiDelegate() {

    });
  }

  @Override
  public GrantAdminApiDelegate getDelegate() {
    return delegate;
  }
}
