package io.biza.deepthought.data.translation.mapper;

import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.DioBankAccount;
import io.biza.deepthought.data.persistence.model.account.AccountData;
import ma.glasnost.orika.MapperFactory;

public class AccountDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(AccountData.class, DioBankAccount.class).fieldAToB("id", "id")
        .byDefault().register();
    
  }
}
