package io.biza.deepthought.shared.persistence.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.account.payee.scheduled.BankingScheduledPaymentRecurrenceV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.scheduled.BankingScheduledPaymentV1;
import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.banking.DioCustomerScheduledPayment;
import io.biza.deepthought.shared.persistence.model.bank.payments.ScheduledPaymentData;
import ma.glasnost.orika.MapperFactory;

public class CustomerBankScheduledPaymentDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(ScheduledPaymentData.class, DioCustomerScheduledPayment.class)
        .fieldAToB("id", "id")
        .field("", "cdrBanking")
        .byDefault().register();
    
    orikaMapperFactory.classMap(ScheduledPaymentData.class, BankingScheduledPaymentV1.class)
        .fieldAToB("id", "scheduledPaymentId")
        .field("nickName", "nickname")
        .field("paymentSet", "paymentSet")
        .field("", "recurrence")
        .byDefault().register();
    
    orikaMapperFactory.classMap(ScheduledPaymentData.class, BankingScheduledPaymentRecurrenceV1.class)
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
