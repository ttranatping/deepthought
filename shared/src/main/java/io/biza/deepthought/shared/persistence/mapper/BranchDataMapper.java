package io.biza.deepthought.shared.persistence.mapper;

import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.banking.DioBankBranch;
import io.biza.deepthought.shared.persistence.model.bank.BankBranchData;
import ma.glasnost.orika.MapperFactory;

public class BranchDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(BankBranchData.class, DioBankBranch.class).fieldAToB("id", "id")
        .byDefault().register();
    
  }
}
