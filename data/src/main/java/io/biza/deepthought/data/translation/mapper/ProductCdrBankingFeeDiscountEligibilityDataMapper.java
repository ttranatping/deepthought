package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductDiscountEligibilityV1;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingFeeDiscountEligibilityData;
import ma.glasnost.orika.MapperFactory;

public class ProductCdrBankingFeeDiscountEligibilityDataMapper
    implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory
        .classMap(ProductCdrBankingFeeDiscountEligibilityData.class,
            BankingProductDiscountEligibilityV1.class)
        .field("discountEligibilityType", "discountEligibilityType").byDefault().register();
    
  }

}
