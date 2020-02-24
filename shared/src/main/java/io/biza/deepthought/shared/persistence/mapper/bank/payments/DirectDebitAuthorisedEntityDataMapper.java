package io.biza.deepthought.shared.persistence.mapper.bank.payments;

import io.biza.babelfish.cdr.models.payloads.banking.account.directdebit.BankingAuthorisedEntityV1;
import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.persistence.model.bank.payments.DirectDebitAuthorisedEntityData;
import ma.glasnost.orika.MapperFactory;

public class DirectDebitAuthorisedEntityDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(DirectDebitAuthorisedEntityData.class, BankingAuthorisedEntityV1.class)
    .fieldAToB("branch.bankName", "financialInstitution")
    .byDefault()
        .register();
  }
}
