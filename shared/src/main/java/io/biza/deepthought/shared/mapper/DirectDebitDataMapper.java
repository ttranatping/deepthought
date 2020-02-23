package io.biza.deepthought.shared.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import io.biza.babelfish.cdr.models.payloads.banking.account.directdebit.BankingDirectDebitV1;
import io.biza.deepthought.data.payloads.dio.DioBrand;
import io.biza.deepthought.data.persistence.model.BrandData;
import io.biza.deepthought.data.persistence.model.bank.payments.DirectDebitData;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import ma.glasnost.orika.MapperFactory;

public class DirectDebitDataMapper implements OrikaFactoryConfigurerInterface {
  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(DirectDebitData.class, BankingDirectDebitV1.class)
    .byDefault()
        .register();
  }
}
