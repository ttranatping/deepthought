package io.biza.deepthought.shared.persistence.mapper;

import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.banking.DioBankAccountTermDeposit;
import io.biza.deepthought.shared.persistence.model.bank.account.BankAccountTermDepositData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;

@Slf4j
public class BankAccountTermDepositDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    
    orikaMapperFactory.classMap(BankAccountTermDepositData.class, DioBankAccountTermDeposit.class).fieldAToB("id", "id")
        .byDefault().register();

  }
}
