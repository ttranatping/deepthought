package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductDiscount;
import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductFee;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingFeeData;
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
