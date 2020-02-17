package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.account.payee.scheduled.BankingScheduledPaymentSetV1;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.persistence.model.bank.payments.CustomerBankScheduledPaymentSetData;
import ma.glasnost.orika.MapperFactory;

public class CustomerBankScheduledPaymentSetDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(CustomerBankScheduledPaymentSetData.class, BankingScheduledPaymentSetV1.class)
        .byDefault().register();
    
  }
}
