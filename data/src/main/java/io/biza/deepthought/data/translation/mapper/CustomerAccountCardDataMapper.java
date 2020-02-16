package io.biza.deepthought.data.translation.mapper;

import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccountCard;
import io.biza.deepthought.data.persistence.model.customer.bank.CustomerBankAccountCardData;
import ma.glasnost.orika.MapperFactory;

public class CustomerAccountCardDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(CustomerBankAccountCardData.class, DioBankAccountCard.class).fieldAToB("id", "id").byDefault().register();

  }
}
