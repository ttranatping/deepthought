package io.biza.deepthought.shared.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.shared.persistence.model.product.banking.BankProductFeeData;

@Repository
public interface ProductBankingFeeRepository
    extends JpaRepository<BankProductFeeData, UUID> {
  public List<BankProductFeeData> findAllByProduct_Product_Brand_IdAndProduct_Product_Id(
      UUID brandId, UUID productId);
  public Optional<BankProductFeeData> findByIdAndProduct_Product_Brand_IdAndProduct_Product_Id(
      UUID id, UUID brandId, UUID productId);
}
