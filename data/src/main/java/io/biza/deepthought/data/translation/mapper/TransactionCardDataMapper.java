package io.biza.deepthought.data.translation.mapper;

import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.banking.DioBankTransactionCard;
import io.biza.deepthought.data.persistence.model.bank.transaction.BankTransactionCardData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;

@Slf4j
public class TransactionCardDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(BankTransactionCardData.class, DioBankTransactionCard.class).fieldAToB("id", "id").byDefault().register();
  }
}
