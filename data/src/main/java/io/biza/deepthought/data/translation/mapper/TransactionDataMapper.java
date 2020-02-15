package io.biza.deepthought.data.translation.mapper;

import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.banking.DioTransaction;
import io.biza.deepthought.data.persistence.model.transaction.TransactionData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;

@Slf4j
public class TransactionDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(TransactionData.class, DioTransaction.class).fieldAToB("id", "id").byDefault().register();
  }
}
