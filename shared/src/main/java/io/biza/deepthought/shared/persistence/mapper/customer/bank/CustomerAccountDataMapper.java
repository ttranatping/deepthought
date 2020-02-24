package io.biza.deepthought.shared.persistence.mapper.customer.bank;

import io.biza.babelfish.cdr.models.payloads.banking.account.BankingAccountV1;
import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.common.DioCustomerAccount;
import io.biza.deepthought.shared.persistence.model.customer.bank.CustomerAccountData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;

@Slf4j
public class CustomerAccountDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(CustomerAccountData.class, DioCustomerAccount.class)
        .field("bankAccount", "bankAccount")
        .byDefault()
        .register();
  }
}
