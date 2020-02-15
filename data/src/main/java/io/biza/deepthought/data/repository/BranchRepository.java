package io.biza.deepthought.data.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.bank.BranchData;
import io.biza.deepthought.data.persistence.model.bank.BrandData;
import io.biza.deepthought.data.persistence.model.product.ProductBundleData;

@Repository
public interface BranchRepository extends JpaRepository<BranchData, UUID> {
  public BranchData findByBsb(Integer bsb);
}
