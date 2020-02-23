package io.biza.deepthought.shared.persistence.mapper;

import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.common.DioAddress;
import io.biza.deepthought.shared.persistence.model.organisation.OrganisationAddressSimpleData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;

@Slf4j
public class OrganisationAddressSimpleDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(OrganisationAddressSimpleData.class, DioAddress.class).byDefault().register();
  }
}
