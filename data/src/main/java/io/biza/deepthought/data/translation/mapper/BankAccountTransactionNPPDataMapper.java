package io.biza.deepthought.data.translation.mapper;

import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccountTransactionNPP;
import io.biza.deepthought.data.persistence.model.bank.transaction.BankAccountTransactionNPPData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;

@Slf4j
public class BankAccountTransactionNPPDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(BankAccountTransactionNPPData.class, DioBankAccountTransactionNPP.class).fieldAToB("id", "id").byDefault().register();
  }
}
