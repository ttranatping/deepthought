package io.biza.deepthought.data.translation.mapper;

import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.DioAddress;
import io.biza.deepthought.data.persistence.model.person.PersonAddressData;
import ma.glasnost.orika.MapperFactory;

public class PersonAddressDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(PersonAddressData.class, DioAddress.class).fieldAToB("id", "id").byDefault().register();
  }
}
