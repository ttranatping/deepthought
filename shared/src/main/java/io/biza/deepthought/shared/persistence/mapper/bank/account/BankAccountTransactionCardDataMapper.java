package io.biza.deepthought.shared.persistence.mapper.bank.account;

import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.banking.DioBankAccountTransactionCard;
import io.biza.deepthought.shared.persistence.model.bank.transaction.BankAccountTransactionCardData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;

@Slf4j
public class BankAccountTransactionCardDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(BankAccountTransactionCardData.class, DioBankAccountTransactionCard.class).fieldAToB("id", "id").byDefault().register();
  }
}
