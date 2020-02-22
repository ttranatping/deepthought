package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.account.payee.bpay.BankingBillerPayeeV1;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.persistence.model.bank.payments.PayeeBPAYData;
import ma.glasnost.orika.MapperFactory;

public class CustomerPayeeBPAYDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(PayeeBPAYData.class, BankingBillerPayeeV1.class)
    .byDefault().register();
  }
}
