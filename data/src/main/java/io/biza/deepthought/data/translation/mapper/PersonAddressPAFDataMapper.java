package io.biza.deepthought.data.translation.mapper;

import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.common.DioAddressPAF;
import io.biza.deepthought.data.persistence.model.person.PersonAddressPAFData;
import ma.glasnost.orika.MapperFactory;

public class PersonAddressPAFDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(PersonAddressPAFData.class, DioAddressPAF.class).fieldAToB("id", "id").byDefault().register();
  }
}
