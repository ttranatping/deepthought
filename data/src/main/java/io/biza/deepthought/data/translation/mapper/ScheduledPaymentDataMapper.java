package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.account.payee.scheduled.BankingScheduledPaymentRecurrenceV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.scheduled.BankingScheduledPaymentV1;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.banking.DioScheduledPayment;
import io.biza.deepthought.data.persistence.model.bank.payments.BankScheduledPaymentData;
import ma.glasnost.orika.MapperFactory;

public class ScheduledPaymentDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(BankScheduledPaymentData.class, DioScheduledPayment.class)
        .fieldAToB("id", "id")
        .field("", "cdrBanking")
        .byDefault().register();
    
    orikaMapperFactory.classMap(BankScheduledPaymentData.class, BankingScheduledPaymentV1.class)
        .fieldAToB("id", "scheduledPaymentId")
        .field("nickName", "nickname")
        .field("", "recurrence")
        .byDefault().register();
    
    orikaMapperFactory.classMap(BankScheduledPaymentData.class, BankingScheduledPaymentRecurrenceV1.class)
    .field("scheduleType", "type")
    .field("nextPaymentDate", "nextPaymentDate")
    .field("nextPaymentDate", "onceOff.paymentDate")
    .field("finalPaymentDate", "intervalSchedule.finalPaymentDate")
    .field("paymentsRemaining", "intervalSchedule.paymentsRemaining")
    .field("nonBusinessDayTreatment", "intervalSchedule.nonBusinessDayTreatment")
    .field("finalPaymentDate", "lastWeekDay.finalPaymentDate")
    .field("paymentsRemaining", "lastWeekDay.paymentsRemaining")
    .field("dayOfWeek", "lastWeekDay.lastWeekDay")
    .field("nonBusinessDayTreatment", "intervalSchedule.nonBusinessDayTreatment")
    .field("scheduleDescription", "eventBased.description")
    .byDefault().register();
  }
}
