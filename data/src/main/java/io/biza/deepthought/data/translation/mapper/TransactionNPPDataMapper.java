package io.biza.deepthought.data.translation.mapper;

import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.banking.DioBankTransactionNPP;
import io.biza.deepthought.data.persistence.model.bank.transaction.BankTransactionNPPData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;

@Slf4j
public class TransactionNPPDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(BankTransactionNPPData.class, DioBankTransactionNPP.class).fieldAToB("id", "id").byDefault().register();
  }
}
