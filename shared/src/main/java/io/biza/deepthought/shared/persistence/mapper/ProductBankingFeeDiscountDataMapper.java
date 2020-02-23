package io.biza.deepthought.shared.persistence.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductDiscountV1;
import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.persistence.model.product.banking.BankProductFeeDiscountData;
import ma.glasnost.orika.MapperFactory;

public class ProductBankingFeeDiscountDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory
        .classMap(BankProductFeeDiscountData.class, BankingProductDiscountV1.class).byDefault()
        .register();
  }

}
