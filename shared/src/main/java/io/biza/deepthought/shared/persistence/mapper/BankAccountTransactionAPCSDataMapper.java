package io.biza.deepthought.shared.persistence.mapper;

import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.banking.DioBankAccountTransactionAPCS;
import io.biza.deepthought.shared.persistence.model.bank.transaction.BankAccountTransactionAPCSData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;

@Slf4j
public class BankAccountTransactionAPCSDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(BankAccountTransactionAPCSData.class, DioBankAccountTransactionAPCS.class).fieldAToB("id", "id").byDefault().register();
  }
}
