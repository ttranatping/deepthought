package io.biza.deepthought.banking.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.criteria.Join;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import io.biza.babelfish.cdr.enumerations.BankingAccountStatusWithAll;
import io.biza.deepthought.banking.requests.RequestListAccounts;
import io.biza.deepthought.data.enumerations.DioAccountStatus;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountData;
import io.biza.deepthought.data.persistence.model.bank.product.BankProductData;
import io.biza.deepthought.data.persistence.model.bank.product.BankProductData_;
import io.biza.deepthought.data.persistence.model.grant.GrantAccountData;
import io.biza.deepthought.data.persistence.model.grant.GrantData;
import io.biza.deepthought.data.persistence.model.grant.GrantResourceData;
import io.biza.deepthought.data.persistence.model.product.ProductData;
import io.biza.deepthought.data.persistence.model.product.ProductData_;
import io.biza.deepthought.data.repository.BankAccountRepository;
import io.biza.deepthought.data.repository.GrantAccountRepository;
import io.biza.deepthought.data.repository.GrantRepository;
import io.biza.deepthought.data.repository.GrantResourceRepository;
import io.biza.deepthought.data.specification.BankAccountSpecifications;
import io.biza.deepthought.data.specification.GrantAccountSpecifications;
import io.biza.deepthought.data.specification.GrantSpecifications;
import io.biza.deepthought.data.specification.ProductBankingSpecifications;
import io.biza.deepthought.shared.exception.NotFoundException;
import io.biza.deepthought.shared.security.UserPrincipalUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GrantService {

  @Autowired
  private GrantAccountRepository accountRepository;
  
  @Autowired
  private GrantResourceRepository resourceRepository;
  
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
