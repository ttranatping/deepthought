package io.biza.deepthought.shared.persistence.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductEligibilityV1;
import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.product.DioProductEligibility;
import io.biza.deepthought.shared.persistence.model.product.banking.BankProductEligibilityData;
import ma.glasnost.orika.MapperFactory;

public class ProductBankingEligibilityDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory
        .classMap(BankProductEligibilityData.class, BankingProductEligibilityV1.class)
        .byDefault().register();
    orikaMapperFactory.classMap(BankProductEligibilityData.class, DioProductEligibility.class)
        .field("", "cdrBanking").fieldAToB("id", "id").byDefault().register();
  }

}
