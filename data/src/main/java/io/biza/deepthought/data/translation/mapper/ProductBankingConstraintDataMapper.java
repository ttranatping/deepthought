package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductConstraintV1;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.product.DioProductConstraint;
import io.biza.deepthought.data.persistence.model.bank.product.ProductBankingConstraintData;
import ma.glasnost.orika.MapperFactory;

public class ProductBankingConstraintDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(ProductBankingConstraintData.class, DioProductConstraint.class)
        .fieldAToB("id", "id").field("", "cdrBanking").byDefault().register();
    orikaMapperFactory
        .classMap(ProductBankingConstraintData.class, BankingProductConstraintV1.class).byDefault()
        .register();
  }

}
