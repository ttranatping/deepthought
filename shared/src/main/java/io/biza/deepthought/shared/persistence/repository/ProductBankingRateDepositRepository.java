package io.biza.deepthought.shared.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.shared.persistence.model.product.banking.BankProductRateDepositData;

@Repository
public interface ProductBankingRateDepositRepository
    extends JpaRepository<BankProductRateDepositData, UUID> {
  public List<BankProductRateDepositData> findAllByProduct_Product_Brand_IdAndProduct_Product_Id(
      UUID brandId, UUID productId);
  public Optional<BankProductRateDepositData> findByIdAndProduct_Product_Brand_IdAndProduct_Product_Id(
      UUID id, UUID brandId, UUID productId);
}
