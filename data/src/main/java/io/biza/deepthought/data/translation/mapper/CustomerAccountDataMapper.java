package io.biza.deepthought.data.translation.mapper;

import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccountCard;
import io.biza.deepthought.data.persistence.model.customer.bank.CustomerBankAccountData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;

@Slf4j
public class CustomerAccountDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(CustomerBankAccountData.class, DioBankAccountCard.class)
        .field("card.issueDateTime", "issueDateTime")
        .field("card.cardNumber", "cardNumber")
        .field("id", "id")
        .register();
  }
}
