package io.biza.deepthought.data.translation.mapper;

import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccountTermDeposit;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountTermDepositData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;

@Slf4j
public class AccountTermDepositDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    
    orikaMapperFactory.classMap(BankAccountTermDepositData.class, DioBankAccountTermDeposit.class).fieldAToB("id", "id")
        .byDefault().register();

  }
}
