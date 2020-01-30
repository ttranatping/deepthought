package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductConstraint;
import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductFee;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.DioProductFee;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingConstraintData;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingFeeData;
import ma.glasnost.orika.MapperFactory;

public class ProductCdrBankingFeeDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(ProductCdrBankingFeeData.class, DioProductFee.class)
        .field("", "cdrBanking").fieldAToB("id", "id").byDefault().register();

    orikaMapperFactory.classMap(ProductCdrBankingFeeData.class, BankingProductFee.class).byDefault()
        .register();
  }

}
