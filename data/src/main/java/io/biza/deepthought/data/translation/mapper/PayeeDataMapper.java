package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.enumerations.AddressPurpose;
import io.biza.babelfish.cdr.enumerations.PayloadTypeBankingPayee;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.bpay.BankingBillerPayeeV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.domestic.BankingDomesticPayeeV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.international.BankingInternationalPayeeV1;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.banking.DioBranch;
import io.biza.deepthought.data.payloads.dio.banking.DioPayee;
import io.biza.deepthought.data.payloads.dio.common.DioAddress;
import io.biza.deepthought.data.payloads.dio.common.DioEmail;
import io.biza.deepthought.data.payloads.dio.common.DioPerson;
import io.biza.deepthought.data.payloads.dio.common.DioPhoneNumber;
import io.biza.deepthought.data.persistence.model.bank.BranchData;
import io.biza.deepthought.data.persistence.model.payments.PayeeData;
import io.biza.deepthought.data.persistence.model.person.PersonAddressData;
import io.biza.deepthought.data.persistence.model.person.PersonData;
import io.biza.deepthought.data.persistence.model.person.PersonEmailData;
import io.biza.deepthought.data.persistence.model.person.PersonPhoneData;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;

public class PayeeDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(PayeeData.class, DioPayee.class)
        .fieldAToB("id", "id").field("id", "cdrBanking.payeeId")
        .field("description", "cdrBanking.description")
        .field("nickName", "cdrBanking.nickname").field("creationDateTime", "cdrBanking.creationDate")
        .field("domestic", "cdrBanking.domestic")
        .field("bpay", "cdrBanking.biller")
        .field("international", "cdrBanking.international")
        .customize(new CustomMapper<PayeeData, DioPayee>() {
          @Override
          public void mapAtoB(PayeeData from, DioPayee to, MappingContext context) {
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
