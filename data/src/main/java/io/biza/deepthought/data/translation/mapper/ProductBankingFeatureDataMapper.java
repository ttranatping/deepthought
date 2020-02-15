package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductFeatureV1;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.product.DioProductFeature;
import io.biza.deepthought.data.persistence.model.product.ProductBankingFeatureData;
import ma.glasnost.orika.MapperFactory;

public class ProductBankingFeatureDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(ProductBankingFeatureData.class, DioProductFeature.class)
        .field("", "cdrBanking").fieldAToB("id", "id").byDefault().register();
    orikaMapperFactory.classMap(ProductBankingFeatureData.class, BankingProductFeatureV1.class)
        .byDefault().register();

  }

}
