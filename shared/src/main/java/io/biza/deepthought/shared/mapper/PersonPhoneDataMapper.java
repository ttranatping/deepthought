package io.biza.deepthought.shared.mapper;

import io.biza.deepthought.data.payloads.dio.common.DioPhoneNumber;
import io.biza.deepthought.data.persistence.model.person.PersonPhoneData;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import ma.glasnost.orika.MapperFactory;

public class PersonPhoneDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(PersonPhoneData.class, DioPhoneNumber.class).fieldAToB("id", "id").byDefault().register();
  }
}
