package io.biza.deepthought.shared.persistence.mapper;

import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.banking.DioBankAuthorisedEntity;
import io.biza.deepthought.shared.persistence.model.bank.payments.DirectDebitAuthorisedEntityData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;

@Slf4j
public class BankAuthorisedEntityDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    
    orikaMapperFactory.classMap(DirectDebitAuthorisedEntityData.class, DioBankAuthorisedEntity.class).fieldAToB("id", "id")
        .byDefault().register();

  }
}
