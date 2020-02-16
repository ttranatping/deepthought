package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductEligibilityV1;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.product.DioProductEligibility;
import io.biza.deepthought.data.persistence.model.product.ProductBankingEligibilityData;
import ma.glasnost.orika.MapperFactory;

public class ProductBankingEligibilityDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory
        .classMap(ProductBankingEligibilityData.class, BankingProductEligibilityV1.class)
        .byDefault().register();
    orikaMapperFactory.classMap(ProductBankingEligibilityData.class, DioProductEligibility.class)
        .field("", "cdrBanking").fieldAToB("id", "id").byDefault().register();
  }

}
