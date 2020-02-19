package io.biza.deepthought.data.translation.mapper;

import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAuthorisedEntity;
import io.biza.deepthought.data.persistence.model.bank.payments.BankAuthorisedEntityData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;

@Slf4j
public class BankAuthorisedEntityDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    
    orikaMapperFactory.classMap(BankAuthorisedEntityData.class, DioBankAuthorisedEntity.class).fieldAToB("id", "id")
        .byDefault().register();

  }
}
