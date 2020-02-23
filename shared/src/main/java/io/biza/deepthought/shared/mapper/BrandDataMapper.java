package io.biza.deepthought.shared.mapper;

import io.biza.deepthought.data.payloads.dio.DioBrand;
import io.biza.deepthought.data.persistence.model.BrandData;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import ma.glasnost.orika.MapperFactory;

public class BrandDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(BrandData.class, DioBrand.class).fieldAToB("id", "id").byDefault()
        .register();
  }
}
