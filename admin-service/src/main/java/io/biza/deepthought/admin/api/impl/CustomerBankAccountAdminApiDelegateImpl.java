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
import io.biza.deepthought.admin.api.delegate.CustomerBankAccountAdminApiDelegate;
import io.biza.deepthought.admin.exceptions.ValidationListException;
import io.biza.deepthought.data.component.DeepThoughtMapper;
import io.biza.deepthought.data.enumerations.DioExceptionType;
import io.biza.deepthought.data.payloads.dio.common.DioCustomerBankAccount;
import io.biza.deepthought.data.payloads.requests.RequestCustomerBankAccountConnection;
import io.biza.deepthought.data.persistence.model.BrandData;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountData;
import io.biza.deepthought.data.persistence.model.customer.CustomerData;
import io.biza.deepthought.data.persistence.model.customer.bank.CustomerBankAccountData;
import io.biza.deepthought.data.repository.BankAccountRepository;
import io.biza.deepthought.data.repository.BrandRepository;
import io.biza.deepthought.data.repository.CustomerBankAccountRepository;
import io.biza.deepthought.data.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;

@Validated
@Controller
@Slf4j
public class CustomerBankAccountAdminApiDelegateImpl
    implements CustomerBankAccountAdminApiDelegate {

  @Autowired
  private DeepThoughtMapper mapper;

  @Autowired
  private CustomerBankAccountRepository customerAccountRepository;

  @Autowired
  private BankAccountRepository accountRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private BrandRepository brandRepository;

  @Autowired
  private Validator validator;

  @Override
  public ResponseEntity<List<DioCustomerBankAccount>> listAssociations(UUID brandId,
      UUID customerId) {
    List<CustomerBankAccountData> customerData =
        customerAccountRepository.findAllByCustomerIdAndCustomerBrandId(customerId, brandId);
    LOG.debug(
        "Listing all customer bank account associations for brand {} and customer id {} and received {}",
        brandId, customerId, customerData);
    return ResponseEntity.ok(mapper.mapAsList(customerData, DioCustomerBankAccount.class));
  }

  @Override
  public ResponseEntity<DioCustomerBankAccount> getAssociation(UUID brandId, UUID customerId,
      UUID associationId) throws ValidationListException {

    Optional<CustomerBankAccountData> optionalCustomer = customerAccountRepository
        .findByIdAndCustomerIdAndCustomerBrandId(associationId, customerId, brandId);

    if (!optionalCustomer.isPresent()) {
      LOG.warn(
          "Attempted to retrieve an association for brand {} customer {} and association {} that doesn't exist",
          brandId, customerId, associationId);
      throw ValidationListException.builder().type(DioExceptionType.INVALID_ASSOCIATION)
          .explanation(Labels.ERROR_INVALID_ASSOCIATION).build();
    }

    return ResponseEntity.ok(mapper.map(optionalCustomer.get(), DioCustomerBankAccount.class));

  }

  @Override
  public ResponseEntity<DioCustomerBankAccount> associateAccount(UUID brandId, UUID customerId,
      RequestCustomerBankAccountConnection accountRequest) throws ValidationListException {

    validator.validate(accountRequest);

    Optional<BrandData> brand = brandRepository.findById(brandId);

    if (!brand.isPresent()) {
      LOG.warn("Attempted to create a customer with non existent brand of {}", brandId);
      throw ValidationListException.builder().type(DioExceptionType.INVALID_BRAND)
          .explanation(Labels.ERROR_INVALID_BRAND).build();
    }

    Optional<CustomerData> customer = customerRepository.findByIdAndBrandId(customerId, brandId);

    if (!customer.isPresent()) {
      LOG.warn("Requested customer {} for brand {} doesn't exist", customerId, brandId);
      throw ValidationListException.builder().type(DioExceptionType.INVALID_CUSTOMER)
          .explanation(Labels.ERROR_INVALID_CUSTOMER).build();
    }

    Optional<BankAccountData> account =
        accountRepository.findByIdAndBranchBrandId(accountRequest.bankAccountId(), brandId);

    if (!account.isPresent()) {
      LOG.warn("Requested customer {} for brand {} doesn't exist", customerId, brandId);
      throw ValidationListException.builder().type(DioExceptionType.INVALID_ACCOUNT)
          .explanation(Labels.ERROR_INVALID_BRAND).build();
    }

    Optional<CustomerBankAccountData> optionalAssociation = customerAccountRepository
        .findByAccountIdAndCustomerIdAndCustomerBrandId(accountRequest.bankAccountId(), customerId, brandId);

    if (!optionalAssociation.isPresent()) {
      LOG.info("Creating a Customer Association for brand {} customer {} and account {}", brandId,
          customerId, accountRequest.bankAccountId());

      CustomerBankAccountData data =
          customerAccountRepository.save(CustomerBankAccountData.builder().account(account.get())
              .customer(customer.get()).owner(accountRequest.makeOwner()).build());

      return ResponseEntity.ok(mapper.map(data, DioCustomerBankAccount.class));
    } else {
      return ResponseEntity.ok(mapper.map(optionalAssociation.get(), DioCustomerBankAccount.class));
    }
  }


  @Override
  public ResponseEntity<Void> unassociateAccount(UUID brandId, UUID customerId, UUID associationId)
      throws ValidationListException {
    Optional<CustomerBankAccountData> optionalCustomer = customerAccountRepository
        .findByIdAndCustomerIdAndCustomerBrandId(associationId, customerId, brandId);

    if (!optionalCustomer.isPresent()) {
      LOG.warn(
          "Deleting Customer Association for brand {} customer {} and association {} that doesn't exist",
          brandId, customerId, associationId);
      throw ValidationListException.builder().type(DioExceptionType.INVALID_ASSOCIATION)
          .explanation(Labels.ERROR_INVALID_ASSOCIATION).build();
    } else {
      LOG.debug("Deleting customer with brand of {} and customer id of {}", brandId, customerId);
      customerAccountRepository.delete(optionalCustomer.get());
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }
}
