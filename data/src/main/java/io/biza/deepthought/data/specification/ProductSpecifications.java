package io.biza.deepthought.data.specification;

import java.time.OffsetDateTime;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import io.biza.babelfish.cdr.enumerations.BankingProductCategory;
import io.biza.babelfish.cdr.enumerations.BankingProductEffectiveWithAll;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingData;

public class ProductSpecifications {

  public static Specification<ProductCdrBankingData> updatedSince(OffsetDateTime updatedSince) {
    return (root, query, cb) -> {
      return cb.greaterThan(root.get("lastUpdated"), updatedSince);
    };
  }

  public static Specification<ProductCdrBankingData> productCategory(
      BankingProductCategory productCategory) {
    return (root, query, cb) -> {
      return cb.equal(root.get("productCategory"), productCategory);
    };
  } 

  public static Specification<ProductCdrBankingData> effective(
      BankingProductEffectiveWithAll effective) {
    if (effective.equals(BankingProductEffectiveWithAll.CURRENT)) {
      return (root, query, cb) -> {
        Predicate effectiveFromNow =
            cb.lessThanOrEqualTo(root.get("effectiveFrom"), OffsetDateTime.now());
        Predicate effectiveFromNull = cb.isNull(root.get("effectiveFrom"));
        Predicate effectiveToNow =
            cb.greaterThanOrEqualTo(root.get("effectiveTo"), OffsetDateTime.now());
        Predicate effectiveToNull = cb.isNull(root.get("effectiveTo"));
        return cb.and(cb.or(effectiveFromNow, effectiveFromNull),
            cb.or(effectiveToNow, effectiveToNull));
      };
    }

    if (effective.equals(BankingProductEffectiveWithAll.FUTURE)) {
      return (root, query, cb) -> {
        Predicate effectiveFromNow =
            cb.greaterThanOrEqualTo(root.get("effectiveFrom"), OffsetDateTime.now());
        Predicate effectiveFromNull = cb.isNull(root.get("effectiveFrom"));
        Predicate effectiveToNow =
            cb.greaterThanOrEqualTo(root.get("effectiveTo"), OffsetDateTime.now());
        Predicate effectiveToNull = cb.isNull(root.get("effectiveTo"));
        return cb.and(cb.or(effectiveFromNow, effectiveFromNull),
            cb.or(effectiveToNow, effectiveToNull));
      };
    }

    return (root, query, cb) -> {
      /** Pointless match of anything */
      Predicate effectiveFromNotNull = cb.isNotNull(root.get("effectiveFrom"));
      Predicate effectiveFromNull = cb.isNull(root.get("effectiveFrom"));
      return cb.or(effectiveFromNotNull, effectiveFromNull);
    };
  }

}
