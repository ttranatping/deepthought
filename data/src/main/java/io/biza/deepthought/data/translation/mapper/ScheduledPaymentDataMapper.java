package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.enumerations.AddressPurpose;
import io.biza.babelfish.cdr.enumerations.PayloadTypeBankingPayee;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.bpay.BankingBillerPayeeV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.domestic.BankingDomesticPayeeV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.international.BankingInternationalPayeeV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.scheduled.BankingScheduledPaymentFromV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.scheduled.BankingScheduledPaymentRecurrenceV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.scheduled.BankingScheduledPaymentV1;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.banking.DioBranch;
import io.biza.deepthought.data.payloads.dio.banking.DioPayee;
import io.biza.deepthought.data.payloads.dio.banking.DioScheduledPayment;
import io.biza.deepthought.data.payloads.dio.common.DioAddress;
import io.biza.deepthought.data.payloads.dio.common.DioEmail;
import io.biza.deepthought.data.payloads.dio.common.DioPerson;
import io.biza.deepthought.data.payloads.dio.common.DioPhoneNumber;
import io.biza.deepthought.data.persistence.model.bank.BranchData;
import io.biza.deepthought.data.persistence.model.payments.PayeeData;
import io.biza.deepthought.data.persistence.model.payments.ScheduledPaymentData;
import io.biza.deepthought.data.persistence.model.person.PersonAddressData;
import io.biza.deepthought.data.persistence.model.person.PersonData;
import io.biza.deepthought.data.persistence.model.person.PersonEmailData;
import io.biza.deepthought.data.persistence.model.person.PersonPhoneData;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;

public class ScheduledPaymentDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(ScheduledPaymentData.class, DioScheduledPayment.class)
        .fieldAToB("id", "id")
        .field("", "cdrBanking")
        .byDefault().register();
    
    orikaMapperFactory.classMap(ScheduledPaymentData.class, BankingScheduledPaymentV1.class)
        .fieldAToB("id", "scheduledPaymentId")
        .field("nickName", "nickname")
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
