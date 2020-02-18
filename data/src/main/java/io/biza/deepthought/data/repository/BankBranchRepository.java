package io.biza.deepthought.data.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.bank.BankBranchData;

@Repository
public interface BankBranchRepository extends JpaRepository<BankBranchData, UUID> {
  public Optional<BankBranchData> findByBsb(Integer bsb);
  public List<BankBranchData> findAllByBrandId(UUID brandId);
  public Boolean existsByIdAndBrandId(UUID id, UUID brandId);
  public Optional<BankBranchData> findByIdAndBrandId(UUID id, UUID brandId);
}
