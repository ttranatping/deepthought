package io.biza.deepthought.data.translation.mapper;

import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payload.DioProductFeature;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingFeatureData;
import ma.glasnost.orika.MapperFactory;

public class ProductCdrBankingFeatureDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(ProductCdrBankingFeatureData.class, DioProductFeature.class)
        .field("", "cdrBanking").fieldAToB("id", "id").byDefault().register();

  }

}
