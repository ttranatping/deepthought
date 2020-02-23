package io.biza.deepthought.shared.component.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import io.biza.babelfish.cdr.enumerations.BankingAccountStatusWithAll;
import io.biza.deepthought.shared.exception.InvalidSubjectException;
import io.biza.deepthought.shared.exception.NotFoundException;
import io.biza.deepthought.shared.payloads.requests.RequestListAccounts;
import io.biza.deepthought.shared.persistence.model.grant.GrantAccountData;
import io.biza.deepthought.shared.persistence.model.grant.GrantCustomerData;
import io.biza.deepthought.shared.persistence.model.grant.GrantResourceData;
import io.biza.deepthought.shared.persistence.repository.GrantAccountRepository;
import io.biza.deepthought.shared.persistence.repository.GrantCustomerRepository;
import io.biza.deepthought.shared.persistence.repository.GrantResourceRepository;
import io.biza.deepthought.shared.persistence.specification.GrantAccountSpecifications;
import io.biza.deepthought.shared.persistence.specification.GrantCustomerSpecifications;
import io.biza.deepthought.shared.util.UserPrincipalUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GrantService {

  @Autowired
  private GrantAccountRepository accountRepository;
  
  @Autowired
  private GrantCustomerRepository customerRepository;
  
  @Autowired
  private GrantResourceRepository resourceRepository;
  
  public UUID getObjectIdByResourceId(UUID id) throws NotFoundException, InvalidSubjectException {
    GrantCustomerData customer = getGrantCustomer();
    Optional<GrantResourceData> resourceData = resourceRepository.findByGrantIdAndId(customer.grant().id(), id);
    
    if(resourceData.isPresent()) {
      return resourceData.get().objectId();
    } else {
      throw new NotFoundException("Requested Grant Resource not found");
    }
  }
  
  public UUID getObjectIdByAccountIdAndResourceId(UUID accountId, UUID id) throws NotFoundException {
    GrantAccountData account = getGrantAccount(accountId);
    Optional<GrantResourceData> resourceData = resourceRepository.findByGrantIdAndId(account.grant().id(), id);
    
    if(resourceData.isPresent()) {
      return resourceData.get().objectId();
    } else {
      throw new NotFoundException("Requested Grant Resource not found");
    }
  }
  
  public UUID getOrCreateResourceIdByAccountIdAndObjectId(UUID accountId, UUID objectId) throws NotFoundException {
    GrantAccountData account = getGrantAccount(accountId);
    Optional<GrantResourceData> resourceData = resourceRepository.findByGrantIdAndObjectId(account.grant().id(), objectId);
    
    if(resourceData.isPresent()) {
      return resourceData.get().id();
    } else {
      GrantResourceData grantResource = resourceRepository.save(GrantResourceData.builder().objectId(objectId).grant(account.grant()).build());
      return grantResource.id();
    }
  }
  
  public List<GrantAccountData> listGrantAccountByIds(UUID...accountIds) {
    return accountRepository.findAll(GrantAccountSpecifications
        .accountIds(accountIds)
        .and(GrantAccountSpecifications.expiryBefore(OffsetDateTime.now()))
        .and(GrantAccountSpecifications.subject(UserPrincipalUtil.getSubject())));    
  }
  
  public Page<GrantAccountData> listPaginatedGrantAccountByIds(Integer page, Integer pageSize, UUID... accountIds) {
    return accountRepository.findAll(GrantAccountSpecifications
        .accountIds(accountIds)
        .and(GrantAccountSpecifications.expiryBefore(OffsetDateTime.now()))
        .and(GrantAccountSpecifications.subject(UserPrincipalUtil.getSubject())), PageRequest.of(page - 1, pageSize));
  }
  
  public GrantCustomerData getGrantCustomer() throws InvalidSubjectException {
    LOG.debug("Retrieving customer with subject identifier of {}", UserPrincipalUtil.getSubject());

    Specification<GrantCustomerData> filterSpecification =
        GrantCustomerSpecifications.expiryBefore(OffsetDateTime.now())
            .and(GrantCustomerSpecifications.subject(UserPrincipalUtil.getSubject()));

    List<GrantCustomerData> grantList = customerRepository.findAll(filterSpecification);

    // Despite being a list we currently limit one grant to one customer so we just pull the first
    // record of the first account returned (there should only be zero or one tuple)
    if (grantList != null && grantList.size() > 0) {
      return grantList.iterator().next();
    } else {
      throw new InvalidSubjectException("Customer for subject " + UserPrincipalUtil.getSubject() + " cannot be found");
    }
  }

  public GrantAccountData getGrantAccount(UUID accountId) throws NotFoundException {
    LOG.debug("Retrieving account with grant identifier of {}", accountId);

    Specification<GrantAccountData> filterSpecification =
        GrantAccountSpecifications.accountId(accountId)
            .and(GrantAccountSpecifications.expiryBefore(OffsetDateTime.now()))
            .and(GrantAccountSpecifications.subject(UserPrincipalUtil.getSubject()));

    List<GrantAccountData> grantList = accountRepository.findAll(filterSpecification);

    // Despite being a list the accountId filter should be exclusive so we just pull the first
    // record of the first account returned (there should only be zero or one tuple)
    if (grantList != null && grantList.size() > 0) {
      return grantList.iterator().next();
    } else {
      throw new NotFoundException("Account " + accountId + " cannot be found");
    }
  }
  
  private Specification<GrantAccountData> generateListAccountsSpecification(RequestListAccounts requestList) {
    Specification<GrantAccountData> filterSpecifications = Specification.where(null);
    
    /**
     * Subject and optionally ownership status
     */
    if(requestList.isOwned() != null) {
      filterSpecifications = filterSpecifications.and(GrantAccountSpecifications.ownerStatus(UserPrincipalUtil.getSubject(), requestList.isOwned()));
    } else {
      filterSpecifications = filterSpecifications.and(GrantAccountSpecifications.subject(UserPrincipalUtil.getSubject()));
    }
    
    /**
     * Account status
     */
    if(List.of(BankingAccountStatusWithAll.OPEN, BankingAccountStatusWithAll.CLOSED).contains(requestList.accountStatus())) {
      filterSpecifications = filterSpecifications.and(GrantAccountSpecifications.accountStatus(requestList.accountStatus()));
    }
    
    /**
     * Product Category
     */
    if(requestList.productCategory() != null) {
      filterSpecifications = filterSpecifications.and(GrantAccountSpecifications.productCategory(requestList.productCategory()));
    }
    
    return filterSpecifications;
  }

  public Page<GrantAccountData> listGrantAccountsPaginated(RequestListAccounts requestList) {
    LOG.debug("Retrieving a paginated list of accounts with input request of {}", requestList);

    return accountRepository.findAll(generateListAccountsSpecification(requestList),
        PageRequest.of(requestList.page() - 1, requestList.pageSize()));
  }
  
  public List<GrantAccountData> listGrantAccounts(RequestListAccounts requestList) {
    LOG.debug("Retrieving a list of accounts with input request of {}", requestList);
    return accountRepository.findAll(generateListAccountsSpecification(requestList));    
  }
}
