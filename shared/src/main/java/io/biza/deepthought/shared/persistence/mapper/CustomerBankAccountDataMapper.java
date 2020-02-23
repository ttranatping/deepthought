package io.biza.deepthought.shared.persistence.mapper;

import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.common.DioCustomerBankAccount;
import io.biza.deepthought.shared.persistence.model.customer.bank.CustomerBankAccountData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;

@Slf4j
public class CustomerBankAccountDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(CustomerBankAccountData.class, DioCustomerBankAccount.class)
        .field("account", "bankAccount")
        .byDefault()
        .register();
  }
}
