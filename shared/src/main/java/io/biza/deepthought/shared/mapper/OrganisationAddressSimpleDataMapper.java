package io.biza.deepthought.shared.mapper;

import io.biza.deepthought.data.payloads.dio.common.DioAddress;
import io.biza.deepthought.data.persistence.model.organisation.OrganisationAddressSimpleData;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;

@Slf4j
public class OrganisationAddressSimpleDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(OrganisationAddressSimpleData.class, DioAddress.class).byDefault().register();
  }
}
