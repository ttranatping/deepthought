package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.v1.model.banking.BankingProductDiscount;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingFeeDiscountData;
import ma.glasnost.orika.MapperFactory;

public class ProductCdrBankingFeeDiscountDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory
        .classMap(ProductCdrBankingFeeDiscountData.class, BankingProductDiscount.class).byDefault()
        .register();
  }

}
