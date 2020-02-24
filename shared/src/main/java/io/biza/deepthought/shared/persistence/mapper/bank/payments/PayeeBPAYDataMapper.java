package io.biza.deepthought.shared.persistence.mapper.bank.payments;

import io.biza.babelfish.cdr.models.payloads.banking.account.payee.bpay.BankingBillerPayeeV1;
import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.persistence.model.bank.payments.PayeeBPAYData;
import ma.glasnost.orika.MapperFactory;

public class PayeeBPAYDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(PayeeBPAYData.class, BankingBillerPayeeV1.class)
    .byDefault().register();
  }
}
