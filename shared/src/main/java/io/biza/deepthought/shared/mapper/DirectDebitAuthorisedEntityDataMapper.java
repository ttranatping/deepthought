package io.biza.deepthought.shared.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.account.directdebit.BankingAuthorisedEntityV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.directdebit.BankingDirectDebitV1;
import io.biza.deepthought.data.payloads.dio.DioBrand;
import io.biza.deepthought.data.persistence.model.BrandData;
import io.biza.deepthought.data.persistence.model.bank.payments.DirectDebitAuthorisedEntityData;
import io.biza.deepthought.data.persistence.model.bank.payments.DirectDebitData;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
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
