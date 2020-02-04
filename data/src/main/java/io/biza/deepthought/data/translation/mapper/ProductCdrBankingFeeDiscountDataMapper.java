package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductDiscountV1;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingFeeDiscountData;
import ma.glasnost.orika.MapperFactory;

public class ProductCdrBankingFeeDiscountDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory
        .classMap(ProductCdrBankingFeeDiscountData.class, BankingProductDiscountV1.class).byDefault()
        .register();
  }

}
