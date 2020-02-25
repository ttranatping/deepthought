package io.biza.deepthought.shared.persistence.mapper.bank.payments;

import io.biza.babelfish.cdr.abstracts.payloads.banking.account.payee.international.BankingInternationalPayeeBankDetailsV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.international.BankingInternationalPayeeBeneficiaryDetailsV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.international.BankingInternationalPayeeV1;
import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.persistence.model.bank.payments.PayeeInternationalData;
import ma.glasnost.orika.MapperFactory;

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