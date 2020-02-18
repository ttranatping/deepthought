package io.biza.deepthought.data.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.bank.product.BankProductRateLendingTierData;

@Repository
public interface ProductBankingRateLendingTierRepository
    extends JpaRepository<BankProductRateLendingTierData, UUID> {
  public List<BankProductRateLendingTierData> findAllByLendingRate_Product_Product_Brand_IdAndLendingRate_Product_Product_IdAndLendingRate_Id(
      UUID brandId, UUID productId, UUID depositRateId);
  public Optional<BankProductRateLendingTierData> findByIdAndLendingRate_Product_Product_Brand_IdAndLendingRate_Product_Product_IdAndLendingRate_Id(
      UUID id, UUID brandId, UUID productId, UUID depositRateId);
}


