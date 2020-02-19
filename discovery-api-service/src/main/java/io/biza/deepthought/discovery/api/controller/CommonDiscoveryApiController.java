package io.biza.deepthought.discovery.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.discovery.api.CommonDiscoveryApi;
import io.biza.deepthought.discovery.api.delegate.CommonDiscoveryApiDelegate;

@Controller
public class CommonDiscoveryApiController implements CommonDiscoveryApi {

  private final CommonDiscoveryApiDelegate delegate;

  public CommonDiscoveryApiController(@Autowired(required = false) CommonDiscoveryApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new CommonDiscoveryApiDelegate() {

    });
  }

  @Override
  public CommonDiscoveryApiDelegate getDelegate() {
    return delegate;
  }

}
