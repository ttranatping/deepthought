package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductDetailV2;
import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductV2;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.DioBankAccount;
import io.biza.deepthought.data.payloads.DioBranch;
import io.biza.deepthought.data.payloads.DioProduct;
import io.biza.deepthought.data.persistence.model.account.AccountData;
import io.biza.deepthought.data.persistence.model.bank.BranchData;
import io.biza.deepthought.data.persistence.model.product.ProductData;
import ma.glasnost.orika.MapperFactory;

public class AccountDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(AccountData.class, DioBankAccount.class).fieldAToB("id", "id")
        .byDefault().register();
    
  }
}
