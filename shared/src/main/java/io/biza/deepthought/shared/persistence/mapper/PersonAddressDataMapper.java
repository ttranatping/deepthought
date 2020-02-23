package io.biza.deepthought.shared.persistence.mapper;

import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.common.DioAddress;
import io.biza.deepthought.shared.persistence.model.person.PersonAddressData;
import ma.glasnost.orika.MapperFactory;

public class PersonAddressDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(PersonAddressData.class, DioAddress.class).fieldAToB("id", "id").byDefault().register();
  }
}
