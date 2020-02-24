package io.biza.deepthought.shared.persistence.mapper.bank.payments;

import io.biza.babelfish.cdr.models.payloads.banking.account.payee.domestic.BankingDomesticPayeeV1;
import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.persistence.model.bank.payments.PayeeDomesticData;
import ma.glasnost.orika.MapperFactory;

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
