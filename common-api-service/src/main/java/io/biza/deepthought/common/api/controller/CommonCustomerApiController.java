package io.biza.deepthought.common.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.common.api.CommonCustomerApi;
import io.biza.deepthought.common.api.delegate.CommonCustomerApiDelegate;

@Controller
public class CommonCustomerApiController implements CommonCustomerApi {

  private final CommonCustomerApiDelegate delegate;

  public CommonCustomerApiController(@Autowired(required = false) CommonCustomerApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new CommonCustomerApiDelegate() {

    });
  }

  @Override
  public CommonCustomerApiDelegate getDelegate() {
    return delegate;
  }

}
