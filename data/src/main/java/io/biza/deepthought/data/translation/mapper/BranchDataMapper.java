package io.biza.deepthought.data.translation.mapper;

import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.banking.DioBranch;
import io.biza.deepthought.data.persistence.model.bank.BankBranchData;
import ma.glasnost.orika.MapperFactory;

public class BranchDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(BankBranchData.class, DioBranch.class).fieldAToB("id", "id")
        .byDefault().register();
    
  }
}
