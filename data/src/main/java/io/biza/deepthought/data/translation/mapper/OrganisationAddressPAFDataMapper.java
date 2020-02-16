package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.enumerations.AddressPurpose;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.common.DioAddress;
import io.biza.deepthought.data.payloads.dio.common.DioOrganisation;
import io.biza.deepthought.data.persistence.model.organisation.OrganisationAddressData;
import io.biza.deepthought.data.persistence.model.organisation.OrganisationAddressPAFData;
import io.biza.deepthought.data.persistence.model.organisation.OrganisationData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;

@Slf4j
public class OrganisationAddressPAFDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(OrganisationAddressPAFData.class, DioAddress.class).byDefault().register();
  }
}
