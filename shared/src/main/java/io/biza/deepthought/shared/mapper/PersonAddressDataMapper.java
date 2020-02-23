package io.biza.deepthought.shared.mapper;

import io.biza.deepthought.data.payloads.dio.common.DioAddress;
import io.biza.deepthought.data.persistence.model.person.PersonAddressData;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import ma.glasnost.orika.MapperFactory;

public class PersonAddressDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(PersonAddressData.class, DioAddress.class).fieldAToB("id", "id").byDefault().register();
  }
}
