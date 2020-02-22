package io.biza.deepthought.banking.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.criteria.Join;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import io.biza.babelfish.cdr.enumerations.BankingAccountStatusWithAll;
import io.biza.babelfish.cdr.enumerations.BankingTransactionStatus;
import io.biza.deepthought.banking.requests.RequestBalancesByAccounts;
import io.biza.deepthought.banking.requests.RequestBalancesByCriteria;
import io.biza.deepthought.banking.requests.RequestListAccounts;
import io.biza.deepthought.data.enumerations.DioAccountStatus;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccountBalance;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountData;
import io.biza.deepthought.data.persistence.model.bank.product.BankProductData;
import io.biza.deepthought.data.persistence.model.bank.product.BankProductData_;
import io.biza.deepthought.data.persistence.model.grant.GrantAccountData;
import io.biza.deepthought.data.persistence.model.grant.GrantData;
import io.biza.deepthought.data.persistence.model.product.ProductData;
import io.biza.deepthought.data.persistence.model.product.ProductData_;
import io.biza.deepthought.data.repository.BankAccountRepository;
import io.biza.deepthought.data.repository.BankAccountTransactionRepository;
import io.biza.deepthought.data.repository.GrantAccountRepository;
import io.biza.deepthought.data.repository.GrantRepository;
import io.biza.deepthought.data.specification.BankAccountSpecifications;
import io.biza.deepthought.data.specification.GrantAccountSpecifications;
import io.biza.deepthought.data.specification.GrantSpecifications;
import io.biza.deepthought.data.specification.ProductBankingSpecifications;
import io.biza.deepthought.shared.exception.NotFoundException;
import io.biza.deepthought.shared.security.UserPrincipalUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountBalanceService {

  @Autowired
  private GrantService accountService;

  @Autowired
  private BankAccountTransactionRepository transactionRepository;

  public Page<DioBankAccountBalance> listBalancesByCriteria(
      RequestListAccounts requestListAccounts) {
    List<DioBankAccountBalance> accountBalances = new ArrayList<DioBankAccountBalance>();

    Page<GrantAccountData> accountsByList = accountService.listGrantAccountsPaginated(requestListAccounts);

    accountsByList.forEach(grantAccount -> {
      accountBalances.add(getBalanceFromData(grantAccount));
    });

    return new PageImpl<DioBankAccountBalance>(accountBalances,
        PageRequest.of(requestListAccounts.page(), requestListAccounts.pageSize()),
        accountsByList.getTotalElements());

  }

  public Page<DioBankAccountBalance> listBalancesByAccountList(
      RequestBalancesByAccounts requestList) {
    List<DioBankAccountBalance> accountBalances = new ArrayList<DioBankAccountBalance>();

    Page<GrantAccountData> accountsByList = accountService
        .listPaginatedGrantAccountByIds(requestList.page(), requestList.pageSize(), requestList
            .accountIds().data().accountIds().stream().map(UUID::fromString).toArray(UUID[]::new));

    accountsByList.forEach(grantAccount -> {
      accountBalances.add(getBalanceFromData(grantAccount));
    });

    return new PageImpl<DioBankAccountBalance>(accountBalances,
        PageRequest.of(requestList.page(), requestList.pageSize()),
        accountsByList.getTotalElements());

  }

  private DioBankAccountBalance getBalanceFromData(GrantAccountData grantAccount) {
    BigDecimal pendingCredits = transactionRepository
        .creditsByAccountAndStatus(grantAccount.account().id(), BankingTransactionStatus.PENDING);
    BigDecimal pendingDebits = transactionRepository
        .debitsByAccountAndStatus(grantAccount.account().id(), BankingTransactionStatus.PENDING);
    BigDecimal postedCredits = transactionRepository
        .creditsByAccountAndStatus(grantAccount.account().id(), BankingTransactionStatus.POSTED);
    BigDecimal postedDebits = transactionRepository
        .debitsByAccountAndStatus(grantAccount.account().id(), BankingTransactionStatus.POSTED);
    BigDecimal currentBalance =
        pendingCredits.add(postedCredits).subtract(pendingDebits).subtract(postedDebits);
    BigDecimal availableBalance = postedCredits.subtract(pendingDebits).subtract(postedDebits);

    return DioBankAccountBalance.builder().currentBalance(currentBalance)
        .availableBalance(availableBalance).build();
  }
  
  public DioBankAccountBalance getBalance(UUID accountId) throws NotFoundException {
    return getBalanceFromData(accountService.getGrantAccount(accountId));
  }

}
