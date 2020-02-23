package io.biza.deepthought.shared.persistence.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import io.biza.babelfish.cdr.enumerations.BankingTransactionStatus;
import io.biza.deepthought.shared.persistence.model.bank.transaction.BankAccountTransactionData;

@Repository
public interface BankAccountTransactionRepository extends JpaRepository<BankAccountTransactionData, UUID>, JpaSpecificationExecutor<BankAccountTransactionData> {
  public List<BankAccountTransactionData> findAllByAccountIdAndAccountBranchIdAndAccountBranchBrandId(UUID accountId, UUID branchId, UUID brandId);
  public Optional<BankAccountTransactionData> findByIdAndAccountIdAndAccountBranchIdAndAccountBranchBrandId(UUID id, UUID accountId, UUID branchId, UUID brandId);
  
  @Query("SELECT SUM(t.amount) FROM BankAccountTransactionData t WHERE t.account = :accountId AND t.status = :status AND t.amount > 0")
  BigDecimal creditsByAccountAndStatus(UUID accountId, BankingTransactionStatus status);
  
  @Query("SELECT SUM(t.amount) FROM BankAccountTransactionData t WHERE t.account = :accountId AND t.status = :status AND t.amount < 0")
  BigDecimal debitsByAccountAndStatus(UUID accountId, BankingTransactionStatus status);
  
}
