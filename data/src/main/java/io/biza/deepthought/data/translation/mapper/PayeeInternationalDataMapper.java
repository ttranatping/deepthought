package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.abstracts.payloads.banking.account.payee.international.BankingInternationalPayeeBankDetailsV1;
import io.biza.babelfish.cdr.enumerations.AddressPurpose;
import io.biza.babelfish.cdr.enumerations.PayloadTypeBankingPayee;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.bpay.BankingBillerPayeeV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.domestic.BankingDomesticPayeeV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.international.BankingInternationalPayeeBeneficiaryDetailsV1;
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
import io.biza.deepthought.data.persistence.model.payments.PayeeInternationalData;
import io.biza.deepthought.data.persistence.model.person.PersonAddressData;
import io.biza.deepthought.data.persistence.model.person.PersonData;
import io.biza.deepthought.data.persistence.model.person.PersonEmailData;
import io.biza.deepthought.data.persistence.model.person.PersonPhoneData;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;

public class PayeeInternationalDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(PayeeInternationalData.class, BankingInternationalPayeeV1.class)
        .field("beneficiaryName", "beneficiaryDetails.name")
        .field("beneficiaryCountry", "beneficiaryDetails.country")
        .field("beneficiaryMessage", "beneficiaryDetails.message")
        .field("bankCountry", "bankDetails.country")
        .field("bankAccountNumber", "bankDetails.accountNumber")
        .field("bankAddressName", "bankDetails.bankAddress.name")
        .field("bankAddressAddress", "bankDetails.bankAddress.address")
        .field("bankBeneficiaryBic", "bankDetails.beneficiaryBankBIC")
        .field("bankFedWire", "bankDetails.fedWireNumber")
        .field("bankSortCode", "bankDetails.sortCode").field("bankChip", "bankDetails.chipNumber")
        .field("bankRoutingNumber", "bankDetails.routingNumber")
        .field("legalEntityIdentifier", "bankDetails.legalEntityIdentifier").byDefault().register();

    orikaMapperFactory.classMap(BankingInternationalPayeeBeneficiaryDetailsV1.class,
        BankingInternationalPayeeBeneficiaryDetailsV1.class).byDefault().register();

    orikaMapperFactory.classMap(BankingInternationalPayeeBankDetailsV1.class,
        BankingInternationalPayeeBankDetailsV1.class).byDefault().register();
  }
}
