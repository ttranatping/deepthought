package io.biza.deepthought.shared.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.shared.persistence.model.product.banking.ProductBankRateLendingTierData;

@Repository
public interface ProductBankRateLendingTierRepository
    extends JpaRepository<ProductBankRateLendingTierData, UUID> {
  public List<ProductBankRateLendingTierData> findAllByLendingRate_Product_Product_Brand_IdAndLendingRate_Product_Product_IdAndLendingRate_Id(
      UUID brandId, UUID productId, UUID depositRateId);
  public Optional<ProductBankRateLendingTierData> findByIdAndLendingRate_Product_Product_Brand_IdAndLendingRate_Product_Product_IdAndLendingRate_Id(
      UUID id, UUID brandId, UUID productId, UUID depositRateId);
}


