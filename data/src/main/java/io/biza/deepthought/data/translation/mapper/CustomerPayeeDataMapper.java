package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.enumerations.PayloadTypeBankingPayee;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.banking.DioCustomerPayee;
import io.biza.deepthought.data.persistence.model.bank.payments.CustomerBankPayeeData;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;

public class CustomerPayeeDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(CustomerBankPayeeData.class, DioCustomerPayee.class)
        .fieldAToB("id", "id").field("id", "cdrBanking.payeeId")
        .field("description", "cdrBanking.description")
        .field("nickName", "cdrBanking.nickname").field("creationDateTime", "cdrBanking.creationDate")
        .field("domestic", "cdrBanking.domestic")
        .field("bpay", "cdrBanking.biller")
        .field("international", "cdrBanking.international")
        .customize(new CustomMapper<CustomerBankPayeeData, DioCustomerPayee>() {
          @Override
          public void mapAtoB(CustomerBankPayeeData from, DioCustomerPayee to, MappingContext context) {
            if (from.domestic() != null) {
              to.cdrBanking.type(PayloadTypeBankingPayee.DOMESTIC);
            }
            if (from.bpay() != null) {
              to.cdrBanking.type(PayloadTypeBankingPayee.BILLER);
            }

            if (from.international() != null) {
              to.cdrBanking.type(PayloadTypeBankingPayee.INTERNATIONAL);
            }
          }          
        }).byDefault().register();

  }
}
