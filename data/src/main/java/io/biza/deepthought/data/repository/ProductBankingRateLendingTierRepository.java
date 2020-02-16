package io.biza.deepthought.data.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.bank.product.ProductBankingRateLendingTierData;

@Repository
public interface ProductBankingRateLendingTierRepository
    extends JpaRepository<ProductBankingRateLendingTierData, UUID> {
  public List<ProductBankingRateLendingTierData> findAllByLendingRate_Product_Product_Brand_IdAndLendingRate_Product_Product_IdAndLendingRate_Id(
      UUID brandId, UUID productId, UUID depositRateId);
  public Optional<ProductBankingRateLendingTierData> findByIdAndLendingRate_Product_Product_Brand_IdAndLendingRate_Product_Product_IdAndLendingRate_Id(
      UUID id, UUID brandId, UUID productId, UUID depositRateId);
}


