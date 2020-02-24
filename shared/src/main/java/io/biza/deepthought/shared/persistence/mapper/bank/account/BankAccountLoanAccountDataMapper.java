package io.biza.deepthought.shared.persistence.mapper.bank.account;

import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.banking.DioBankAccountLoanAccount;
import io.biza.deepthought.shared.persistence.model.bank.account.BankAccountLoanAccountData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;

@Slf4j
public class BankAccountLoanAccountDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    
    orikaMapperFactory.classMap(BankAccountLoanAccountData.class, DioBankAccountLoanAccount.class).fieldAToB("id", "id")
        .byDefault().register();

  }
}
