package io.biza.deepthought.banking.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import io.biza.deepthought.banking.requests.RequestListTransactions;
import io.biza.deepthought.data.persistence.model.bank.transaction.BankAccountTransactionData;
import io.biza.deepthought.data.persistence.model.grant.GrantAccountData;
import io.biza.deepthought.data.repository.BankAccountTransactionRepository;
import io.biza.deepthought.data.specification.TransactionSpecifications;
import io.biza.deepthought.shared.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionService {

  @Autowired
  GrantService accountService;
  
  @Autowired
  BankAccountTransactionRepository transactionRepository;
  
  public BankAccountTransactionData getTransaction(UUID accountId, UUID resourceId) throws NotFoundException {
    UUID transactionId = accountService.getObjectIdByAccountIdAndResourceId(accountId, resourceId);
    Optional<BankAccountTransactionData> transactionData = transactionRepository.findById(transactionId);
    
    if(transactionData.isPresent()) {
      return transactionData.get();
    } else {
      throw new NotFoundException("Unable to convert mapping to transaction data");
    }
    
  }

  public Page<BankAccountTransactionData> listTransactions(UUID accountId, RequestListTransactions requestListTransactions) throws NotFoundException {
    
    GrantAccountData grantAccount = accountService.getGrantAccount(accountId);
    
    Specification<BankAccountTransactionData> filterSpecifications = Specification.where(TransactionSpecifications.accountId(grantAccount.id()));
    
    /**
     * Oldest/Newest Time
     */
    if(requestListTransactions.oldestDateTime() != null) {
      filterSpecifications = filterSpecifications.and(TransactionSpecifications.oldestTime(requestListTransactions.oldestDateTime()));
    }
    if(requestListTransactions.newestDateTime() != null) {
      filterSpecifications = filterSpecifications.and(TransactionSpecifications.newestTime(requestListTransactions.newestDateTime()));
    }

    
    /**
     * Min/Max Amounts
     */
    if(requestListTransactions.maxAmount() != null) {
      filterSpecifications = filterSpecifications.and(TransactionSpecifications.maxAmount(requestListTransactions.maxAmount()));
    }
    if(requestListTransactions.minAmount() != null) {
      filterSpecifications = filterSpecifications.and(TransactionSpecifications.minAmount(requestListTransactions.minAmount()));
    }
    
    /**
     * Text filter
     */
    if(requestListTransactions.stringFilter() != null && requestListTransactions.stringFilter() != "") {
      filterSpecifications = filterSpecifications.and(TransactionSpecifications.textFilter(requestListTransactions.stringFilter()));
    }

    return transactionRepository.findAll(filterSpecifications, PageRequest.of(requestListTransactions.page() - 1, requestListTransactions.pageSize()));
    
  }
}
