package io.biza.deepthought.shared.persistence.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import io.biza.babelfish.cdr.models.payloads.banking.account.directdebit.BankingDirectDebitV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.scheduled.BankingScheduledPaymentV1;
import io.biza.deepthought.shared.component.service.GrantService;
import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.banking.DioCustomerScheduledPayment;
import io.biza.deepthought.shared.payloads.dio.common.DioCustomer;
import io.biza.deepthought.shared.payloads.dio.enumerations.DioCustomerType;
import io.biza.deepthought.shared.persistence.model.bank.payments.DirectDebitData;
import io.biza.deepthought.shared.persistence.model.bank.payments.ScheduledPaymentData;
import io.biza.deepthought.shared.persistence.model.customer.CustomerData;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;

public class ScheduledPaymentDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(ScheduledPaymentData.class, DioCustomerScheduledPayment.class)
    .fieldAToB("id", "id")
    .field("", "cdrBanking")
    .byDefault().register();
    
    orikaMapperFactory.classMap(ScheduledPaymentData.class, BankingScheduledPaymentV1.class)
        .fieldAToB("nickName", "nickname").fieldAToB("payerReference", "payerReference")
        .fieldAToB("payeeReference", "payeeReference").fieldAToB("status", "status")
        .fieldAToB("nextPaymentDate", "recurrence.nextPaymentDate")
        .fieldAToB("scheduleType", "recurrence.type")
        .fieldAToB("finalPaymentDate", "recurrence.intervalSchedule.finalPaymentDate")
        .fieldAToB("paymentsRemaining", "recurrence.intervalSchedule.paymentsRemaining")
        .fieldAToB("nonBusinessDayTreatment",
            "recurrence.intervalScheduled.nonBusinessDayTreatment")
        .fieldAToB("finalPaymentDate", "recurrence.lastWeekDay.finalPaymentDate")
        .fieldAToB("paymentsRemaining", "recurrence.lastWeekDay.paymentsRemaining")
        .fieldAToB("paymentFrequency", "recurrence.lastWeekDay.interval")
        .fieldAToB("dayOfWeek", "recurrence.lastWeekDay.dayOfWeek")
        .fieldAToB("nonBusinessDayTreatment", "recurrence.lastWeekDay.nonBusinessDayTreatment")
        .fieldAToB("scheduleDescription", "recurrence.eventBased.description").register();

    // TODO: from mapping
    // TODO: id mapping

  }
}
