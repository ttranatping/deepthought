package io.biza.deepthought.shared.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.shared.persistence.model.product.ProductBundleData;

@Repository
public interface ProductBankingBundleRepository extends JpaRepository<ProductBundleData, UUID> {
  public List<ProductBundleData> findAllByBrandId(UUID brandId);
  public Optional<ProductBundleData> findByIdAndBrandId(UUID id, UUID brandId);
}
