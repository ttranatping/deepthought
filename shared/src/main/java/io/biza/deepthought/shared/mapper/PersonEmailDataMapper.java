package io.biza.deepthought.shared.mapper;

import io.biza.deepthought.data.payloads.dio.common.DioEmail;
import io.biza.deepthought.data.persistence.model.person.PersonEmailData;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import ma.glasnost.orika.MapperFactory;

public class PersonEmailDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(PersonEmailData.class, DioEmail.class).fieldAToB("id", "id").byDefault().register();
  }
}
