package io.biza.deepthought.data.translation.mapper;

import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.DioBrand;
import io.biza.deepthought.data.persistence.model.bank.BrandData;
import ma.glasnost.orika.MapperFactory;

public class BrandDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(BrandData.class, DioBrand.class).fieldAToB("id", "id").byDefault()
        .register();
  }
}
