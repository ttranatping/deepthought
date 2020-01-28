package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.v1.model.banking.BankingProductEligibility;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payload.DioProductEligibility;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingEligibilityData;
import ma.glasnost.orika.MapperFactory;

public class ProductCdrBankingEligibilityDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory
        .classMap(ProductCdrBankingEligibilityData.class, BankingProductEligibility.class)
        .byDefault().register();
    orikaMapperFactory.classMap(ProductCdrBankingEligibilityData.class, DioProductEligibility.class)
        .field("", "cdrBanking").fieldAToB("id", "id").byDefault().register();
  }

}