package io.biza.deepthought.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.biza.deepthought.shared.service.GrantService;
import io.biza.deepthought.data.persistence.model.customer.CustomerData;
import io.biza.deepthought.shared.exception.InvalidSubjectException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerService {

  @Autowired
  private GrantService grantService;

  public CustomerData getCustomer() throws InvalidSubjectException {
    return grantService.getGrantCustomer().customer();
  }

}
