package io.biza.deepthought.data.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.bank.transaction.BankAccountTransactionData;

@Repository
public interface BankAccountTransactionRepository extends JpaRepository<BankAccountTransactionData, UUID> {
  public List<BankAccountTransactionData> findAllByAccountIdAndAccountBranchIdAndAccountBranchBrandId(UUID accountId, UUID branchId, UUID brandId);
  public Optional<BankAccountTransactionData> findByIdAndAccountIdAndAccountBranchIdAndAccountBranchBrandId(UUID id, UUID accountId, UUID branchId, UUID brandId);
  
}
