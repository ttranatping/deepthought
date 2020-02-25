package io.biza.deepthought.shared.persistence.mapper.person;

import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.common.DioAddressPAF;
import io.biza.deepthought.shared.persistence.model.person.PersonAddressPAFData;
import ma.glasnost.orika.MapperFactory;

public class PersonAddressPAFDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(PersonAddressPAFData.class, DioAddressPAF.class).fieldAToB("id", "id").byDefault().register();
  }
}
