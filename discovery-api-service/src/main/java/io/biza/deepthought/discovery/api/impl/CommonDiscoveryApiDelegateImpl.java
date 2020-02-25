package io.biza.deepthought.discovery.api.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import io.biza.babelfish.cdr.models.responses.ResponseCommonDiscoveryStatusV1;
import io.biza.deepthought.discovery.api.delegate.CommonDiscoveryApiDelegate;

@Validated
@Controller
public class CommonDiscoveryApiDelegateImpl implements CommonDiscoveryApiDelegate {
  @Override
  public ResponseEntity<ResponseCommonDiscoveryStatusV1> getStatus() {
    return ResponseEntity.ok(ResponseCommonDiscoveryStatusV1.builder().build());
  }
  
}
