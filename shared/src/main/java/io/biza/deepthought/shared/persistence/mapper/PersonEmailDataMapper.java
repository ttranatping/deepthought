package io.biza.deepthought.shared.persistence.mapper;

import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.common.DioEmail;
import io.biza.deepthought.shared.persistence.model.person.PersonEmailData;
import ma.glasnost.orika.MapperFactory;

public class PersonEmailDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(PersonEmailData.class, DioEmail.class).fieldAToB("id", "id").byDefault().register();
  }
}
