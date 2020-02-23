package io.biza.deepthought.shared.persistence.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.account.directdebit.BankingDirectDebitV1;
import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.persistence.model.bank.payments.DirectDebitData;
import ma.glasnost.orika.MapperFactory;

public class DirectDebitDataMapper implements OrikaFactoryConfigurerInterface {
  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(DirectDebitData.class, BankingDirectDebitV1.class)
    .byDefault()
        .register();
  }
}
