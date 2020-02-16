package io.biza.deepthought.data.translation.mapper;

import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccountLoanAccount;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountLoanAccountData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;

@Slf4j
public class AccountLoanAccountDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    
    orikaMapperFactory.classMap(BankAccountLoanAccountData.class, DioBankAccountLoanAccount.class).fieldAToB("id", "id")
        .byDefault().register();

  }
}
