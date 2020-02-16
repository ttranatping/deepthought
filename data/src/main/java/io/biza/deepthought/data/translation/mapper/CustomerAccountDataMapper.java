package io.biza.deepthought.data.translation.mapper;

import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.enumerations.DioCustomerType;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccountCard;
import io.biza.deepthought.data.payloads.dio.common.DioCustomer;
import io.biza.deepthought.data.persistence.model.customer.CustomerAccountData;
import io.biza.deepthought.data.persistence.model.customer.CustomerData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;

@Slf4j
public class CustomerAccountDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(CustomerAccountData.class, DioBankAccountCard.class)
        .field("card.issueDateTime", "issueDateTime")
        .field("card.cardNumber", "cardNumber")
        .field("id", "id")
        .register();
  }
}
