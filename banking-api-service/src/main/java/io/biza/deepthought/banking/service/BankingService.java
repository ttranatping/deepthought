package io.biza.deepthought.banking.service;

import java.time.OffsetDateTime;
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
import io.biza.deepthought.data.persistence.model.product.ProductData;
import io.biza.deepthought.data.persistence.model.product.ProductData_;
import io.biza.deepthought.data.repository.BankAccountRepository;
import io.biza.deepthought.data.repository.GrantAccountRepository;
import io.biza.deepthought.data.specification.BankAccountSpecifications;
import io.biza.deepthought.data.specification.ProductBankingSpecifications;
import io.biza.deepthought.shared.exception.NotFoundException;
import io.biza.deepthought.shared.security.UserPrincipalUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BankingService {

  @Autowired
  private GrantAccountRepository accountRepository;


  public BankAccountData getAccount(UUID accountId) throws NotFoundException {
    LOG.debug("Retrieving account with grant identifier of {}", accountId);
    Optional<BankAccountData> productResult =
        accountRepository.findByIdAndGrantSubjectAndGrantExpiryBefore(accountId,
            UserPrincipalUtil.getSubject(), OffsetDateTime.now());
    
    if(productResult.isPresent()) {
      return productResult.get();
    } else {
      throw NotFoundException.builder().build();
    }
  }

  public Page<BankAccountData> listAccounts(RequestListAccounts requestList) {
    LOG.debug("Retrieving a list of accounts with input request of {}", requestList);

    Specification<BankAccountData> filterSpecifications = Specification.where(null);

    if (requestList.productCategory() != null) {
      filterSpecifications = filterSpecifications
          .and(BankAccountSpecifications.productCategory(requestList.productCategory()));
    }

    if (requestList.accountStatus() != null
        && requestList.accountStatus() != BankingAccountStatusWithAll.ALL) {
      filterSpecifications = filterSpecifications.and(BankAccountSpecifications
          .accountStatus(DioAccountStatus.valueOf(requestList.accountStatus().toString())));
    }

    /**
     * Paginated Result
     */
    return accountRepository.findAll(filterSpecifications,
        PageRequest.of(requestList.page() - 1, requestList.pageSize()));
  }
}
