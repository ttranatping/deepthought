package io.biza.deepthought.shared.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.shared.persistence.model.product.banking.ProductBankRateDepositTierData;

@Repository
public interface ProductBankRateDepositTierRepository
    extends JpaRepository<ProductBankRateDepositTierData, UUID> {
  public List<ProductBankRateDepositTierData> findAllByDepositRate_Product_Product_Brand_IdAndDepositRate_Product_Product_IdAndDepositRate_Id(
      UUID brandId, UUID productId, UUID depositRateId);
  public Optional<ProductBankRateDepositTierData> findByIdAndDepositRate_Product_Product_Brand_IdAndDepositRate_Product_Product_IdAndDepositRate_Id(
      UUID id, UUID brandId, UUID productId, UUID depositRateId);
}


