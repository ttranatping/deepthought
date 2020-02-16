package io.biza.deepthought.data.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.bank.product.ProductBankingRateDepositData;

@Repository
public interface ProductBankingRateDepositRepository
    extends JpaRepository<ProductBankingRateDepositData, UUID> {
  public List<ProductBankingRateDepositData> findAllByProduct_Product_Brand_IdAndProduct_Product_Id(
      UUID brandId, UUID productId);
  public Optional<ProductBankingRateDepositData> findByIdAndProduct_Product_Brand_IdAndProduct_Product_Id(
      UUID id, UUID brandId, UUID productId);
}
