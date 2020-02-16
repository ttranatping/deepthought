package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.enumerations.AddressPurpose;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.common.DioAddress;
import io.biza.deepthought.data.payloads.dio.common.DioEmail;
import io.biza.deepthought.data.payloads.dio.common.DioPerson;
import io.biza.deepthought.data.payloads.dio.common.DioPhoneNumber;
import io.biza.deepthought.data.persistence.model.person.PersonAddressData;
import io.biza.deepthought.data.persistence.model.person.PersonData;
import io.biza.deepthought.data.persistence.model.person.PersonEmailData;
import io.biza.deepthought.data.persistence.model.person.PersonPhoneData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;

@Slf4j
public class PersonDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(PersonData.class, DioPerson.class).fieldAToB("id", "id")
        .field("occupationCode", "cdrCommon.occupationCode")
        .customize(new CustomMapper<PersonData, DioPerson>() {
          @Override
          public void mapAtoB(PersonData from, DioPerson to, MappingContext context) {

            if (from.emailAddress() != null) {
              for (PersonEmailData emailAddress : from.emailAddress()) {
                if (emailAddress.isPreferred()) {
                  to.email(orikaMapperFactory.getMapperFacade().map(emailAddress, DioEmail.class));
                }
              }
            }

            if (from.phoneNumber() != null) {
              for (PersonPhoneData phone : from.phoneNumber()) {
                if (phone.isPreferred()) {
                  to.phone(orikaMapperFactory.getMapperFacade().map(phone, DioPhoneNumber.class));
                }
              }
            }
            
            if(from.physicalAddress() != null) {
              for(PersonAddressData address : from.physicalAddress()) {
                if(address.purpose().equals(AddressPurpose.MAIL)) {
                  to.address(orikaMapperFactory.getMapperFacade().map(address, DioAddress.class));
                  break;
                }
                if(address.purpose().equals(AddressPurpose.REGISTERED)) {
                  to.address(orikaMapperFactory.getMapperFacade().map(address, DioAddress.class));
                }
              }
            }
          }
        }).byDefault().register();
  }
}
