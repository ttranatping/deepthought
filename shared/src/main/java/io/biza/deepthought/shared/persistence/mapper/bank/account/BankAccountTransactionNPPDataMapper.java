package io.biza.deepthought.shared.persistence.mapper.bank.account;

import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.banking.DioBankAccountTransactionNPP;
import io.biza.deepthought.shared.persistence.model.bank.transaction.BankAccountTransactionNPPData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;

@Slf4j
public class BankAccountTransactionNPPDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(BankAccountTransactionNPPData.class, DioBankAccountTransactionNPP.class).fieldAToB("id", "id").byDefault().register();
  }
}
