package io.biza.deepthought.data.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingFeeDiscountData;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingRateLendingData;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingRateLendingTierData;

@Repository
public interface ProductRateLendingTierRepository
    extends JpaRepository<ProductCdrBankingRateLendingTierData, UUID> {
  public List<ProductCdrBankingRateLendingTierData> findAllByLendingRate_Product_Product_Brand_IdAndLendingRate_Product_Product_IdAndLendingRate_Id(
      UUID brandId, UUID productId, UUID depositRateId);
  public Optional<ProductCdrBankingRateLendingTierData> findByIdAndLendingRate_Product_Product_Brand_IdAndLendingRate_Product_Product_IdAndLendingRate_Id(
      UUID id, UUID brandId, UUID productId, UUID depositRateId);
}


