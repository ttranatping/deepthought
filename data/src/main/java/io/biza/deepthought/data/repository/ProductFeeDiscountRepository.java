package io.biza.deepthought.data.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.product.ProductBankingFeeDiscountData;

@Repository
public interface ProductFeeDiscountRepository
    extends JpaRepository<ProductBankingFeeDiscountData, UUID> {
  public List<ProductBankingFeeDiscountData> findAllByFee_Product_Product_Brand_IdAndFee_Product_Product_IdAndFee_Id(
      UUID brandId, UUID productId, UUID feeId);
  public Optional<ProductBankingFeeDiscountData> findByIdAndFee_Product_Product_Brand_IdAndFee_Product_Product_IdAndFee_Id(
      UUID id, UUID brandId, UUID productId, UUID feeId);
}


