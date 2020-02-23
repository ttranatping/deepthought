package io.biza.deepthought.shared.persistence.mapper;

import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.common.DioPhoneNumber;
import io.biza.deepthought.shared.persistence.model.person.PersonPhoneData;
import ma.glasnost.orika.MapperFactory;

public class PersonPhoneDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(PersonPhoneData.class, DioPhoneNumber.class).fieldAToB("id", "id").byDefault().register();
  }
}
