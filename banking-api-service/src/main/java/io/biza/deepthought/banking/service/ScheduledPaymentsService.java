package io.biza.deepthought.banking.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
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
import io.biza.deepthought.banking.requests.RequestScheduledPaymentsByAccounts;
import io.biza.deepthought.banking.requests.RequestScheduledPaymentsByBulk;
import io.biza.deepthought.banking.requests.RequestListAccounts;
import io.biza.deepthought.data.enumerations.DioAccountStatus;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccountBalance;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountData;
import io.biza.deepthought.data.persistence.model.bank.payments.ScheduledPaymentData;
import io.biza.deepthought.data.persistence.model.bank.product.BankProductData_;
import io.biza.deepthought.data.persistence.model.bank.transaction.BankAccountTransactionData;
import io.biza.deepthought.data.persistence.model.grant.GrantAccountData;
import io.biza.deepthought.data.persistence.model.grant.GrantData;
import io.biza.deepthought.data.persistence.model.product.ProductData;
import io.biza.deepthought.data.persistence.model.product.ProductData_;
import io.biza.deepthought.data.persistence.model.product.banking.BankProductData;
import io.biza.deepthought.data.repository.BankAccountRepository;
import io.biza.deepthought.data.repository.BankAccountTransactionRepository;
import io.biza.deepthought.data.repository.CustomerBankScheduledPaymentRepository;
import io.biza.deepthought.data.repository.GrantAccountRepository;
import io.biza.deepthought.data.repository.GrantRepository;
import io.biza.deepthought.data.specification.BankAccountSpecifications;
import io.biza.deepthought.data.specification.ScheduledPaymentSpecifications;
import io.biza.deepthought.data.specification.GrantAccountSpecifications;
import io.biza.deepthought.data.specification.GrantSpecifications;
import io.biza.deepthought.data.specification.ProductBankingSpecifications;
import io.biza.deepthought.data.specification.TransactionSpecifications;
import io.biza.deepthought.shared.exception.NotFoundException;
import io.biza.deepthought.shared.security.UserPrincipalUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ScheduledPaymentsService {

  @Autowired
  private GrantService grantService;

  @Autowired
  private CustomerBankScheduledPaymentRepository scheduledPaymentRepository;


  public Page<ScheduledPaymentData> listScheduledPaymentsByAccount(UUID accountId,
      RequestScheduledPaymentsByAccounts requestScheduledPayments) throws NotFoundException {

    GrantAccountData grantAccount = grantService.getGrantAccount(accountId);
    Specification<ScheduledPaymentData> filterSpecifications =
        Specification.where(ScheduledPaymentSpecifications.accountId(grantAccount.account().id()));

    return scheduledPaymentRepository.findAll(filterSpecifications,
        PageRequest.of(requestScheduledPayments.page() - 1, requestScheduledPayments.pageSize()));

  }

  public Page<ScheduledPaymentData> listScheduledPaymentsByAccountList(
      RequestScheduledPaymentsByAccounts requestList) {
    LOG.debug("Retrieving a list of direct debits with input request of {}", requestList);

    List<GrantAccountData> accountsByList = grantService.listGrantAccountByIds(requestList
        .accountIds().data().accountIds().stream().map(UUID::fromString).toArray(UUID[]::new));

    return scheduledPaymentRepository.findAll(
        ScheduledPaymentSpecifications.accountIds(accountsByList.stream().map(GrantAccountData::account)
            .collect(Collectors.toList()).stream().map(BankAccountData::id)
            .collect(Collectors.toList()).toArray(UUID[]::new)),
        PageRequest.of(requestList.page() - 1, requestList.pageSize()));
  }

  public Page<ScheduledPaymentData> listScheduledPaymentsWithFilter(
      RequestScheduledPaymentsByBulk requestList) {
    LOG.debug("Retrieving a list of direct debits with input request of {}", requestList);

    List<GrantAccountData> accountsByList = grantService
        .listGrantAccounts(RequestListAccounts.builder().accountStatus(requestList.accountStatus())
            .isOwned(requestList.isOwned()).productCategory(requestList.productCategory()).build());

    return scheduledPaymentRepository.findAll(
        ScheduledPaymentSpecifications.accountIds(accountsByList.stream().map(GrantAccountData::account)
            .collect(Collectors.toList()).stream().map(BankAccountData::id)
            .collect(Collectors.toList()).toArray(UUID[]::new)),
        PageRequest.of(requestList.page() - 1, requestList.pageSize()));
  }

}
