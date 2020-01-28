package io.biza.deepthought.admin.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.biza.deepthought.admin.api.TypeApi;
import io.biza.deepthought.admin.api.delegate.TypeApiDelegate;

@Controller
public class TypeApiController implements TypeApi {

  private final TypeApiDelegate delegate;


  public TypeApiController(@Autowired(required = false) TypeApiDelegate delegate) {
    this.delegate = Optional.ofNullable(delegate).orElse(new TypeApiDelegate() {

    });
  }

  @Override
  public TypeApiDelegate getDelegate() {
    return delegate;
  }

}
