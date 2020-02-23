package io.biza.deepthought.shared.mapper;

import io.biza.deepthought.data.payloads.dio.common.DioAddressSimple;
import io.biza.deepthought.data.persistence.model.person.PersonAddressSimpleData;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import ma.glasnost.orika.MapperFactory;

public class PersonAddressSimpleDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(PersonAddressSimpleData.class, DioAddressSimple.class).fieldAToB("id", "id").byDefault().register();
  }
}
