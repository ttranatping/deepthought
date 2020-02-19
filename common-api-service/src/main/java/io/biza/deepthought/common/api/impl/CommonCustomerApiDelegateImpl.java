package io.biza.deepthought.common.api.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import io.biza.babelfish.cdr.models.responses.ResponseCommonCustomerDetailV1;
import io.biza.babelfish.cdr.models.responses.ResponseCommonCustomerV1;
import io.biza.deepthought.common.api.delegate.CommonCustomerApiDelegate;

@Validated
@Controller
public class CommonCustomerApiDelegateImpl implements CommonCustomerApiDelegate {
  @Override
  public ResponseEntity<ResponseCommonCustomerV1> getCustomer() {
    return ResponseEntity.ok(ResponseCommonCustomerV1.builder().build());
  }
  
  @Override
  public ResponseEntity<ResponseCommonCustomerDetailV1> getCustomerDetail() {
    return ResponseEntity.ok(ResponseCommonCustomerDetailV1.builder().build());
  }

}
