package io.biza.deepthought.shared.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.shared.persistence.model.product.banking.ProductBankFeeDiscountData;

@Repository
public interface ProductBankFeeDiscountRepository
    extends JpaRepository<ProductBankFeeDiscountData, UUID> {
  public List<ProductBankFeeDiscountData> findAllByFee_Product_Product_Brand_IdAndFee_Product_Product_IdAndFee_Id(
      UUID brandId, UUID productId, UUID feeId);
  public Optional<ProductBankFeeDiscountData> findByIdAndFee_Product_Product_Brand_IdAndFee_Product_Product_IdAndFee_Id(
      UUID id, UUID brandId, UUID productId, UUID feeId);
}


