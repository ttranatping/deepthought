package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductConstraint;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.DioProductConstraint;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingConstraintData;
import ma.glasnost.orika.MapperFactory;

public class ProductCdrBankingConstraintDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(ProductCdrBankingConstraintData.class, DioProductConstraint.class)
        .fieldAToB("id", "id").field("", "cdrBanking").byDefault().register();
    orikaMapperFactory
        .classMap(ProductCdrBankingConstraintData.class, BankingProductConstraint.class).byDefault()
        .register();
  }

}
