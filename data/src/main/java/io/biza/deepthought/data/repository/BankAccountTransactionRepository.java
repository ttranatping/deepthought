package io.biza.deepthought.data.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import io.biza.babelfish.cdr.enumerations.BankingTransactionStatus;
import io.biza.babelfish.cdr.enumerations.BankingTransactionType;
import io.biza.deepthought.data.persistence.model.bank.transaction.BankAccountTransactionData;
import io.biza.deepthought.data.persistence.model.grant.GrantAccountData;

@Repository
public interface BankAccountTransactionRepository extends JpaRepository<BankAccountTransactionData, UUID>, JpaSpecificationExecutor<BankAccountTransactionData> {
  public List<BankAccountTransactionData> findAllByAccountIdAndAccountBranchIdAndAccountBranchBrandId(UUID accountId, UUID branchId, UUID brandId);
  public Optional<BankAccountTransactionData> findByIdAndAccountIdAndAccountBranchIdAndAccountBranchBrandId(UUID id, UUID accountId, UUID branchId, UUID brandId);
  
  @Query("SELECT SUM(t.amount) FROM BANK_TRANSACTION t WHERE t.ACCOUNT_ID = :accountId AND t.STATUS = :status AND t.amount > 0")
  BigDecimal creditsByAccountAndStatus(UUID accountId, BankingTransactionStatus status);
  
  @Query("SELECT SUM(t.amount) FROM BANK_TRANSACTION t WHERE t.ACCOUNT_ID = :accountId AND t.STATUS = :status AND t.amount < 0")
  BigDecimal debitsByAccountAndStatus(UUID accountId, BankingTransactionStatus status);
  
}
