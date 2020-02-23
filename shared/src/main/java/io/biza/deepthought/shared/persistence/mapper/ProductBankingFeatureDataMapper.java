package io.biza.deepthought.shared.persistence.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductFeatureV1;
import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.product.DioProductFeature;
import io.biza.deepthought.shared.persistence.model.product.banking.BankProductFeatureData;
import ma.glasnost.orika.MapperFactory;

public class ProductBankingFeatureDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(BankProductFeatureData.class, DioProductFeature.class)
        .field("", "cdrBanking").fieldAToB("id", "id").byDefault().register();
    orikaMapperFactory.classMap(BankProductFeatureData.class, BankingProductFeatureV1.class)
        .byDefault().register();

  }

}
