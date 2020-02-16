package io.biza.deepthought.data.translation.mapper;

import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.banking.DioTransactionAPCS;
import io.biza.deepthought.data.persistence.model.bank.transaction.BankTransactionAPCSData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;

@Slf4j
public class TransactionAPCSDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(BankTransactionAPCSData.class, DioTransactionAPCS.class).fieldAToB("id", "id").byDefault().register();
  }
}
