package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.enumerations.PayloadTypeBankingScheduledPaymentTo;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.scheduled.BankingScheduledPaymentSetV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.scheduled.BankingScheduledPaymentToV1;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.persistence.model.bank.payments.ScheduledPaymentSetData;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;

public class CustomerBankScheduledPaymentSetDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory
        .classMap(ScheduledPaymentSetData.class, BankingScheduledPaymentSetV1.class)
        .customize(
            new CustomMapper<ScheduledPaymentSetData, BankingScheduledPaymentSetV1>() {
              @Override
              public void mapAtoB(ScheduledPaymentSetData from,
                  BankingScheduledPaymentSetV1 to, MappingContext context) {
                if (from.account() != null) {
                  to.to(BankingScheduledPaymentToV1.builder()
                      .accountId(from.account().id().toString())
                      .type(PayloadTypeBankingScheduledPaymentTo.ACCOUNT_ID).build());
                }
                if (from.payee() != null) {
                  to.to(BankingScheduledPaymentToV1.builder().payeeId(from.payee().id().toString())
                      .type(PayloadTypeBankingScheduledPaymentTo.PAYEE_ID).build());
                }
                // TODO: Support domestic, biller and international payment sets
              }
            })
        .byDefault().register();

  }
}
