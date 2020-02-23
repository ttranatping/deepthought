package io.biza.deepthought.shared.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.shared.persistence.model.product.banking.ProductBankFeeDiscountEligibilityData;

@Repository
public interface ProductBankFeeDiscountEligibilityRepository
    extends JpaRepository<ProductBankFeeDiscountEligibilityData, UUID> {
  public List<ProductBankFeeDiscountEligibilityData> findAllByDiscount_Fee_Product_Product_Brand_IdAndDiscount_Fee_Product_Product_IdAndDiscount_Fee_IdAndDiscount_Id(
      UUID brandId, UUID productId, UUID feeId, UUID discountId);

  public Optional<ProductBankFeeDiscountEligibilityData> findByIdAndDiscount_Fee_Product_Product_Brand_IdAndDiscount_Fee_Product_Product_IdAndDiscount_Fee_IdAndDiscount_Id(
      UUID id, UUID brandId, UUID productId, UUID feeId, UUID discountId);
}


