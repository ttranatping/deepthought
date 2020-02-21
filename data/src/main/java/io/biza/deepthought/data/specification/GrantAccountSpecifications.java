package io.biza.deepthought.data.specification;

import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import io.biza.babelfish.cdr.enumerations.BankingAccountStatusWithAll;
import io.biza.babelfish.cdr.enumerations.BankingProductCategory;
import io.biza.babelfish.cdr.enumerations.BankingProductEffectiveWithAll;
import io.biza.deepthought.data.enumerations.DioAccountStatus;
import io.biza.deepthought.data.persistence.model.BrandData;
import io.biza.deepthought.data.persistence.model.BrandData_;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountData;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountData_;
import io.biza.deepthought.data.persistence.model.bank.product.BankProductData;
import io.biza.deepthought.data.persistence.model.bank.product.BankProductData_;
import io.biza.deepthought.data.persistence.model.grant.GrantAccountData;
import io.biza.deepthought.data.persistence.model.grant.GrantAccountData_;
import io.biza.deepthought.data.persistence.model.grant.GrantData;
import io.biza.deepthought.data.persistence.model.grant.GrantData_;
import io.biza.deepthought.data.persistence.model.product.ProductData;
import io.biza.deepthought.data.persistence.model.product.ProductData_;

public class GrantAccountSpecifications {

  public static Specification<GrantData> subject(String subject) {
    return (root, query, cb) -> {
      return cb.equal(root.get(GrantData_.subject), subject);
    };
  }

  public static Specification<GrantData> expiryBefore(OffsetDateTime expiry) {
    return (root, query, cb) -> {
      return cb.greaterThan(root.get(GrantData_.expiry), expiry);
    };
  }

  public static Specification<GrantData> accountId(UUID accountId) {
    return (root, query, cb) -> {
      Join<GrantData, GrantAccountData> grantJoin = root.join(GrantData_.accounts);
      return cb.equal(grantJoin.get(GrantAccountData_.id), accountId);
    };
  }
  
  public static Specification<BankAccountData> productCategory(
      BankingProductCategory productCategory) {
    return (root, query, cb) -> {
      Join<BankAccountData, ProductData> bankingJoin = root.join(BankAccountData_.product);
      Join<ProductData, BankProductData> productJoin = bankingJoin.join(ProductData_.cdrBanking);
      return cb.equal(productJoin.get(BankProductData_.productCategory), productCategory);
    };
  }
  
  public static Specification<GrantAccountData> accountStatus(
      DioAccountStatus dioAccountStatus) {
    return (root, query, cb) -> {
      Join<GrantData, GrantAccountData> grantJoin = root.join(GrantData_.accounts);
      Join<GrantAccountData, BankAccountData> bankingJoin = root.join(GrantAccountData_.account);
      return cb.equal(root.get(BankAccountData_.status), dioAccountStatus);
    };
  }
}
