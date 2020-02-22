package io.biza.deepthought.banking.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import io.biza.deepthought.banking.requests.RequestScheduledPaymentsByAccounts;
import io.biza.deepthought.banking.requests.RequestScheduledPaymentsByBulk;
import io.biza.deepthought.banking.requests.RequestListAccounts;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountData;
import io.biza.deepthought.data.persistence.model.bank.payments.ScheduledPaymentData;
import io.biza.deepthought.data.persistence.model.grant.GrantAccountData;
import io.biza.deepthought.data.repository.CustomerBankScheduledPaymentRepository;
import io.biza.deepthought.data.specification.ScheduledPaymentSpecifications;
import io.biza.deepthought.shared.exception.NotFoundException;
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
