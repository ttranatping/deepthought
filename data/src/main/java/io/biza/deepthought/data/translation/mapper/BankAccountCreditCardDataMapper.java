package io.biza.deepthought.data.translation.mapper;

import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccountCreditCard;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountCreditCardData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;

@Slf4j
public class BankAccountCreditCardDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    
    orikaMapperFactory.classMap(BankAccountCreditCardData.class, DioBankAccountCreditCard.class).fieldAToB("id", "id")
        .byDefault().register();

  }
}
