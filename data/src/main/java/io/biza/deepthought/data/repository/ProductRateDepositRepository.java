package io.biza.deepthought.data.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingFeeData;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingRateDepositData;

@Repository
public interface ProductRateDepositRepository
    extends JpaRepository<ProductCdrBankingRateDepositData, UUID> {
  public List<ProductCdrBankingRateDepositData> findAllByProduct_Product_Brand_IdAndProduct_Product_Id(
      UUID brandId, UUID productId);
  public Optional<ProductCdrBankingRateDepositData> findByIdAndProduct_Product_Brand_IdAndProduct_Product_Id(
      UUID id, UUID brandId, UUID productId);
}
