package io.biza.deepthought.common.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import io.biza.babelfish.cdr.models.responses.ResponseCommonCustomerDetailV1;
import io.biza.babelfish.cdr.models.responses.ResponseCommonCustomerV1;
import io.biza.babelfish.cdr.models.responses.container.ResponseCommonCustomerDataV1;
import io.biza.babelfish.cdr.models.responses.container.ResponseCommonCustomerDetailDataV1;
import io.biza.deepthought.common.api.delegate.CommonCustomerApiDelegate;
import io.biza.deepthought.common.service.CustomerService;
import io.biza.deepthought.data.persistence.model.customer.CustomerData;
import io.biza.deepthought.shared.controller.DeepThoughtMapper;
import io.biza.deepthought.shared.exception.InvalidSubjectException;
import io.biza.deepthought.shared.support.CDRContainerAttributes;

@Validated
@Controller
public class CommonCustomerApiDelegateImpl implements CommonCustomerApiDelegate {
  
  @Autowired
  CustomerService customerService;

  @Autowired
  private DeepThoughtMapper mapper;
  
  @Override
  public ResponseEntity<ResponseCommonCustomerV1> getCustomer() throws InvalidSubjectException {
    CustomerData customer = customerService.getCustomer();
    ResponseCommonCustomerV1 customerResponse = new ResponseCommonCustomerV1();
    customerResponse.meta(CDRContainerAttributes.toMeta());
    customerResponse.links(CDRContainerAttributes.toLinks());
    customerResponse.data(mapper.map(customer, ResponseCommonCustomerDataV1.class));
    return ResponseEntity.ok(customerResponse);
  }
  
  @Override
  public ResponseEntity<ResponseCommonCustomerDetailV1> getCustomerDetail() throws InvalidSubjectException {
    CustomerData customer = customerService.getCustomer();
    ResponseCommonCustomerDetailV1 customerResponse = new ResponseCommonCustomerDetailV1();
    customerResponse.meta(CDRContainerAttributes.toMeta());
    customerResponse.links(CDRContainerAttributes.toLinks());
    customerResponse.data(mapper.map(customer, ResponseCommonCustomerDetailDataV1.class));
    return ResponseEntity.ok(customerResponse);
  }

}
