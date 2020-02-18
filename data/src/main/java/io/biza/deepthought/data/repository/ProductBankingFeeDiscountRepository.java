package io.biza.deepthought.data.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.bank.product.BankProductFeeDiscountData;

@Repository
public interface ProductBankingFeeDiscountRepository
    extends JpaRepository<BankProductFeeDiscountData, UUID> {
  public List<BankProductFeeDiscountData> findAllByFee_Product_Product_Brand_IdAndFee_Product_Product_IdAndFee_Id(
      UUID brandId, UUID productId, UUID feeId);
  public Optional<BankProductFeeDiscountData> findByIdAndFee_Product_Product_Brand_IdAndFee_Product_Product_IdAndFee_Id(
      UUID id, UUID brandId, UUID productId, UUID feeId);
}


