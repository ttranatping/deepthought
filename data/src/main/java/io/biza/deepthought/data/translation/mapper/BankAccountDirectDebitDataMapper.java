package io.biza.deepthought.data.translation.mapper;

import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccountCreditCard;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccountDirectDebit;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountCreditCardData;
import io.biza.deepthought.data.persistence.model.bank.payments.BankAccountDirectDebitData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;

@Slf4j
public class BankAccountDirectDebitDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    
    orikaMapperFactory.classMap(BankAccountDirectDebitData.class, DioBankAccountDirectDebit.class).fieldAToB("id", "id")
        .byDefault().register();

  }
}
