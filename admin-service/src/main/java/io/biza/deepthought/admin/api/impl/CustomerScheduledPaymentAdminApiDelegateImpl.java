package io.biza.deepthought.admin.api.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import io.biza.deepthought.admin.Labels;
import io.biza.deepthought.admin.api.delegate.CustomerScheduledPaymentAdminApiDelegate;
import io.biza.deepthought.admin.exceptions.ValidationListException;
import io.biza.deepthought.admin.support.DeepThoughtValidator;
import io.biza.deepthought.data.component.DeepThoughtMapper;
import io.biza.deepthought.data.enumerations.DioExceptionType;
import io.biza.deepthought.data.payloads.dio.banking.DioCustomerScheduledPayment;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountData;
import io.biza.deepthought.data.persistence.model.bank.payments.CustomerBankScheduledPaymentData;
import io.biza.deepthought.data.persistence.model.customer.CustomerData;
import io.biza.deepthought.data.repository.CustomerScheduledPaymentRepository;
import io.biza.deepthought.data.repository.BankAccountRepository;
import io.biza.deepthought.data.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;

@Validated
@Controller
@Slf4j
public class CustomerScheduledPaymentAdminApiDelegateImpl implements CustomerScheduledPaymentAdminApiDelegate {

  @Autowired
  private DeepThoughtMapper mapper;

  @Autowired
  private CustomerScheduledPaymentRepository bankScheduledPaymentRepository;
  
  @Autowired
  private CustomerRepository customerRepository;
  
  @Autowired
  private Validator validator;

  @Override
  public ResponseEntity<List<DioCustomerScheduledPayment>> listScheduledPayments(UUID brandId, UUID customerId) {
    List<CustomerBankScheduledPaymentData> bankAccountData = bankScheduledPaymentRepository.findAllByCustomerIdAndCustomerBrandId(customerId, brandId);
    LOG.debug("Listing all bank scheduledPayments for brand id of {} customer id of {} and received {}", brandId, customerId, bankAccountData);
    return ResponseEntity.ok(mapper.mapAsList(bankAccountData, DioCustomerScheduledPayment.class));
  }

  @Override
  public ResponseEntity<DioCustomerScheduledPayment> getScheduledPayment(UUID brandId, UUID customerId, UUID scheduledPaymentId) {
    Optional<CustomerBankScheduledPaymentData> data = bankScheduledPaymentRepository.findByIdAndCustomerIdAndCustomerBrandId(scheduledPaymentId, customerId, brandId);

    if (data.isPresent()) {
      LOG.info("Retrieving a single bank scheduled payment with brand id {} and customer id of {} and id of {} and content of {}",
          brandId, customerId, scheduledPaymentId, data.get());
      return ResponseEntity.ok(mapper.map(data.get(), DioCustomerScheduledPayment.class));
    } else {
      LOG.warn(
          "Attempted to retrieve a single bank scheduled payment but could not find with brand id of {} and customer id of {} and id of {}",
          brandId, customerId, scheduledPaymentId);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<DioCustomerScheduledPayment> createScheduledPayment(UUID brandId, UUID customerId, DioCustomerScheduledPayment createRequest)
      throws ValidationListException {
    
    DeepThoughtValidator.validate(validator, createRequest);
    
    Optional<CustomerData> customer = customerRepository.findByIdAndBrandId(customerId, brandId);
    
    if (!customer.isPresent()) {
      LOG.warn("Attempted to create a scheduled payment for a customer which could not be identified with brand {} and customer id of {}", brandId, customerId);
      throw ValidationListException.builder().type(DioExceptionType.INVALID_CUSTOMER).explanation(Labels.ERROR_INVALID_CUSTOMER).build();
    }
    
    CustomerBankScheduledPaymentData requestScheduledPayment = mapper.map(createRequest, CustomerBankScheduledPaymentData.class);
    requestScheduledPayment.customer(customer.get());
    
    CustomerBankScheduledPaymentData scheduledPayment = bankScheduledPaymentRepository.save(requestScheduledPayment);
    
    LOG.debug("Creating a new bank scheduled payment for brand {} customer {} with content of {}", brandId, customerId, scheduledPayment);
    return getScheduledPayment(brandId, customerId, scheduledPayment.id());
  }

  @Override
  public ResponseEntity<Void> deleteScheduledPayment(UUID brandId, UUID customerId, UUID scheduledPaymentId) {
    Optional<CustomerBankScheduledPaymentData> optionalData = bankScheduledPaymentRepository.findByIdAndCustomerIdAndCustomerBrandId(scheduledPaymentId, customerId, brandId);

    if (optionalData.isPresent()) {
      LOG.info("Deleting scheduled payment with id of {} brand of {} customer of {}", scheduledPaymentId, brandId, customerId);
      bankScheduledPaymentRepository.delete(optionalData.get());
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      LOG.warn(
          "Attempted to delete scheduled payment but it couldn't be found with brand {} customer {} and id of {}",
          brandId, customerId, scheduledPaymentId);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<DioCustomerScheduledPayment> updateScheduledPayment(UUID brandId, UUID customerId, UUID scheduledPaymentId,
      DioCustomerScheduledPayment createRequest) throws ValidationListException {

    DeepThoughtValidator.validate(validator, createRequest);
    
    Optional<CustomerBankScheduledPaymentData> optionalData = bankScheduledPaymentRepository.findByIdAndCustomerIdAndCustomerBrandId(scheduledPaymentId, customerId, brandId);

    if (optionalData.isPresent()) {
      CustomerBankScheduledPaymentData data = optionalData.get();
      mapper.map(createRequest, CustomerBankScheduledPaymentData.class);
      bankScheduledPaymentRepository.save(data);
      
      LOG.debug("Updated scheduled payment with brand {} customer id {} and id {} containing content of {}", brandId,
          customerId, scheduledPaymentId, data);
      return getScheduledPayment(brandId, customerId, scheduledPaymentId);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
