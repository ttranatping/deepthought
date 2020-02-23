package io.biza.deepthought.shared.persistence.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductDiscountEligibilityV1;
import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.persistence.model.product.banking.BankProductFeeDiscountEligibilityData;
import ma.glasnost.orika.MapperFactory;

public class ProductBankingFeeDiscountEligibilityDataMapper
    implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory
        .classMap(BankProductFeeDiscountEligibilityData.class,
            BankingProductDiscountEligibilityV1.class)
        .field("discountEligibilityType", "discountEligibilityType").byDefault().register();
    
  }

}
