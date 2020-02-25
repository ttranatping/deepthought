package io.biza.deepthought.shared.persistence.mapper.product.banking;

import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductEligibilityV1;
import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.product.DioProductEligibility;
import io.biza.deepthought.shared.persistence.model.product.banking.ProductBankEligibilityData;
import ma.glasnost.orika.MapperFactory;

public class ProductBankEligibilityDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory
        .classMap(ProductBankEligibilityData.class, BankingProductEligibilityV1.class)
        .byDefault().register();
    orikaMapperFactory.classMap(ProductBankEligibilityData.class, DioProductEligibility.class)
        .field("", "cdrBanking").fieldAToB("id", "id").byDefault().register();
  }

}
