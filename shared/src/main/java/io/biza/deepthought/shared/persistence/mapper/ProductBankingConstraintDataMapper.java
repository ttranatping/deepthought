package io.biza.deepthought.shared.persistence.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductConstraintV1;
import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.product.DioProductConstraint;
import io.biza.deepthought.shared.persistence.model.product.banking.BankProductConstraintData;
import ma.glasnost.orika.MapperFactory;

public class ProductBankingConstraintDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(BankProductConstraintData.class, DioProductConstraint.class)
        .fieldAToB("id", "id").field("", "cdrBanking").byDefault().register();
    orikaMapperFactory
        .classMap(BankProductConstraintData.class, BankingProductConstraintV1.class).byDefault()
        .register();
  }

}
