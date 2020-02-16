package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.enumerations.AddressPurpose;
import io.biza.babelfish.cdr.enumerations.PayloadTypeBankingPayee;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.domestic.BankingDomesticPayeeV1;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.banking.DioBranch;
import io.biza.deepthought.data.payloads.dio.banking.DioPayee;
import io.biza.deepthought.data.payloads.dio.common.DioAddress;
import io.biza.deepthought.data.payloads.dio.common.DioEmail;
import io.biza.deepthought.data.payloads.dio.common.DioPerson;
import io.biza.deepthought.data.payloads.dio.common.DioPhoneNumber;
import io.biza.deepthought.data.persistence.model.bank.BranchData;
import io.biza.deepthought.data.persistence.model.payments.PayeeData;
import io.biza.deepthought.data.persistence.model.payments.PayeeDomesticData;
import io.biza.deepthought.data.persistence.model.person.PersonAddressData;
import io.biza.deepthought.data.persistence.model.person.PersonData;
import io.biza.deepthought.data.persistence.model.person.PersonEmailData;
import io.biza.deepthought.data.persistence.model.person.PersonPhoneData;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;

public class PayeeDomesticDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(PayeeDomesticData.class, BankingDomesticPayeeV1.class)
    .field("type", "payeeAccountType")
    .field("accountName", "account.accountName")
    .field("accountBsb", "account.bsb")
    .field("accountNumber", "account.accountNumber")
    .field("cardNumber", "card.cardNumber")
    .field("payIdName", "payId.name")
    .field("payIdIdentifier", "payId.identifier")
    .field("payIdType", "payId.type")
    .byDefault().register();

  }
}
