package io.biza.deepthought.data.translation.mapper;

import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccountTransactionCard;
import io.biza.deepthought.data.persistence.model.bank.transaction.BankAccountTransactionCardData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;

@Slf4j
public class BankAccountTransactionCardDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(BankAccountTransactionCardData.class, DioBankAccountTransactionCard.class).fieldAToB("id", "id").byDefault().register();
  }
}
