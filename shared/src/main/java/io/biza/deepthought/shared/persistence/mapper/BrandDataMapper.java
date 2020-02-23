package io.biza.deepthought.shared.persistence.mapper;

import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.DioBrand;
import io.biza.deepthought.shared.persistence.model.BrandData;
import ma.glasnost.orika.MapperFactory;

public class BrandDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(BrandData.class, DioBrand.class).fieldAToB("id", "id").byDefault()
        .register();
  }
}
