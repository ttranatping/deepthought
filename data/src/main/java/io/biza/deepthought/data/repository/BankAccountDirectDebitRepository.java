package io.biza.deepthought.data.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.bank.payments.DirectDebitData;
import io.biza.deepthought.data.persistence.model.grant.GrantResourceData;

@Repository
public interface BankAccountDirectDebitRepository extends JpaRepository<DirectDebitData, UUID>, JpaSpecificationExecutor<DirectDebitData> {
  public List<DirectDebitData> findAllByAccountIdAndAccountBranchIdAndAccountBranchBrandId(UUID accountId, UUID branchId, UUID brandId);
  public Optional<DirectDebitData> findByIdAndAccountIdAndAccountBranchIdAndAccountBranchBrandId(UUID id, UUID accountId, UUID branchId, UUID brandId);

}
