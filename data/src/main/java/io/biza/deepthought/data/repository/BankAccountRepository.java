package io.biza.deepthought.data.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountData;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountData, UUID> {
  public List<BankAccountData> findAllByBranchIdAndBranchBrandId(UUID branchId, UUID brandId);
  public Optional<BankAccountData> findByIdAndBranchIdAndBranchBrandId(UUID id, UUID branchId, UUID brandId);
  public Optional<BankAccountData> findByIdAndBranchBrandId(UUID id, UUID brandId);
}
