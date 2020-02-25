package io.biza.deepthought.shared.persistence.mapper.person;

import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.common.DioAddressSimple;
import io.biza.deepthought.shared.persistence.model.person.PersonAddressSimpleData;
import ma.glasnost.orika.MapperFactory;

public class PersonAddressSimpleDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(PersonAddressSimpleData.class, DioAddressSimple.class).fieldAToB("id", "id").byDefault().register();
  }
}
