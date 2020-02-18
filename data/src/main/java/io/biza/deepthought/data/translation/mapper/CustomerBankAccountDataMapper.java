package io.biza.deepthought.data.translation.mapper;

import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.common.DioCustomerBankAccount;
import io.biza.deepthought.data.persistence.model.customer.bank.CustomerBankAccountData;
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
