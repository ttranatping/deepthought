package io.biza.deepthought.data.specification;

import java.time.OffsetDateTime;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import io.biza.babelfish.cdr.enumerations.BankingProductCategory;
import io.biza.babelfish.cdr.enumerations.BankingProductEffectiveWithAll;
import io.biza.deepthought.data.persistence.model.BrandData;
import io.biza.deepthought.data.persistence.model.BrandData_;
import io.biza.deepthought.data.persistence.model.ProductData;
import io.biza.deepthought.data.persistence.model.ProductData_;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingData;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingData_;

public class ProductSpecifications {

  public static Specification<ProductData> updatedSince(OffsetDateTime updatedSince) {
    return (root, query, cb) -> {
      Join<ProductData, ProductCdrBankingData> bankingJoin = root.join(ProductData_.cdrBanking);
      return cb.greaterThan(bankingJoin.get(ProductCdrBankingData_.lastUpdated), updatedSince);
    };
  }
  
  public static Specification<ProductData> brand(String brandName) {
    return (root, query, cb) -> {
      Join<ProductData, BrandData> brandJoin = root.join(ProductData_.brand);
      return cb.equal(brandJoin.get(BrandData_.name), brandName);
    };
  }
  
  public static Specification<ProductData> productCategory(
      BankingProductCategory productCategory) {
    return (root, query, cb) -> {
      Join<ProductData, ProductCdrBankingData> bankingJoin = root.join(ProductData_.cdrBanking);
      return cb.equal(bankingJoin.get(ProductCdrBankingData_.productCategory), productCategory);
    };
  } 

  public static Specification<ProductData> effective(
      BankingProductEffectiveWithAll effective) {
    
    if (effective.equals(BankingProductEffectiveWithAll.CURRENT)) {
      return (root, query, cb) -> {
        Join<ProductData, ProductCdrBankingData> bankingJoin = root.join(ProductData_.cdrBanking);
        
        Predicate effectiveFromNow =
            cb.lessThanOrEqualTo(bankingJoin.get(ProductCdrBankingData_.effectiveFrom), OffsetDateTime.now());
        Predicate effectiveFromNull = cb.isNull(bankingJoin.get(ProductCdrBankingData_.effectiveFrom));
        Predicate effectiveToNow =
            cb.greaterThanOrEqualTo(bankingJoin.get(ProductCdrBankingData_.effectiveTo), OffsetDateTime.now());
        Predicate effectiveToNull = cb.isNull(bankingJoin.get(ProductCdrBankingData_.effectiveTo));
        return cb.and(cb.or(effectiveFromNow, effectiveFromNull),
            cb.or(effectiveToNow, effectiveToNull));
      };
    }

    if (effective.equals(BankingProductEffectiveWithAll.FUTURE)) {
      return (root, query, cb) -> {
        
        Join<ProductData, ProductCdrBankingData> bankingJoin = root.join(ProductData_.cdrBanking);

        Predicate effectiveFromNow =
            cb.greaterThanOrEqualTo(bankingJoin.get(ProductCdrBankingData_.effectiveFrom), OffsetDateTime.now());
        Predicate effectiveFromNull = cb.isNull(bankingJoin.get(ProductCdrBankingData_.effectiveFrom));
        Predicate effectiveToNow =
            cb.greaterThanOrEqualTo(bankingJoin.get(ProductCdrBankingData_.effectiveTo), OffsetDateTime.now());
        Predicate effectiveToNull = cb.isNull(bankingJoin.get(ProductCdrBankingData_.effectiveTo));
        return cb.and(cb.or(effectiveFromNow, effectiveFromNull),
            cb.or(effectiveToNow, effectiveToNull));
      };
    }

    return (root, query, cb) -> {
      /** Pointless match of anything */
      return null;
    };
  }

}
