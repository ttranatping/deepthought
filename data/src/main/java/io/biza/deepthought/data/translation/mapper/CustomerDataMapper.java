package io.biza.deepthought.data.translation.mapper;

import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.enumerations.DioCustomerType;
import io.biza.deepthought.data.payloads.DioCustomer;
import io.biza.deepthought.data.persistence.model.customer.CustomerData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;

@Slf4j
public class CustomerDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(CustomerData.class, DioCustomer.class).fieldAToB("id", "id").customize(new CustomMapper<CustomerData, DioCustomer>() {
      @Override
      public void mapAtoB(CustomerData from, DioCustomer to,
          MappingContext context) {
        
        if(from.person() != null) {
          to.customerType(DioCustomerType.PERSON);
        } else if(from.organisation() != null) {
          to.customerType(DioCustomerType.ORGANISATION);
        } else {
          LOG.error("Neither Organisation nor Person are populated in CustomerData so cannot set customer type");
        }
      }
    }).byDefault().register();
  }
}
