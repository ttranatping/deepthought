package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductDiscountV1;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.persistence.model.product.banking.BankProductFeeDiscountData;
import ma.glasnost.orika.MapperFactory;

public class ProductBankingFeeDiscountDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory
        .classMap(BankProductFeeDiscountData.class, BankingProductDiscountV1.class).byDefault()
        .register();
  }

}
