package io.biza.deepthought.shared.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.shared.persistence.model.bank.account.BankAccountData;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountData, UUID>, JpaSpecificationExecutor<BankAccountData> {
  public List<BankAccountData> findAllByBranchIdAndBranchBrandId(UUID branchId, UUID brandId);
  public Optional<BankAccountData> findByIdAndBranchIdAndBranchBrandId(UUID id, UUID branchId, UUID brandId);
  public Optional<BankAccountData> findByIdAndBranchBrandId(UUID id, UUID brandId);
  public Optional<BankAccountData> findByIdAndCustomerAccountsCustomerId(UUID id, UUID customerId);
}