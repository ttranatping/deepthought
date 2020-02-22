package io.biza.deepthought.data.specification;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
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
import io.biza.deepthought.data.persistence.model.bank.transaction.BankAccountTransactionData;
import io.biza.deepthought.data.persistence.model.bank.transaction.BankAccountTransactionData_;
import io.biza.deepthought.data.persistence.model.customer.CustomerData;
import io.biza.deepthought.data.persistence.model.customer.CustomerData_;
import io.biza.deepthought.data.persistence.model.customer.bank.CustomerBankAccountData;
import io.biza.deepthought.data.persistence.model.customer.bank.CustomerBankAccountData_;
import io.biza.deepthought.data.persistence.model.grant.GrantAccountData;
import io.biza.deepthought.data.persistence.model.grant.GrantAccountData_;
import io.biza.deepthought.data.persistence.model.grant.GrantCustomerData;
import io.biza.deepthought.data.persistence.model.grant.GrantCustomerData_;
import io.biza.deepthought.data.persistence.model.grant.GrantData;
import io.biza.deepthought.data.persistence.model.grant.GrantData_;
import io.biza.deepthought.data.persistence.model.product.ProductData;
import io.biza.deepthought.data.persistence.model.product.ProductData_;

public class TransactionSpecifications {

  public static Specification<BankAccountTransactionData> accountId(UUID accountId) {
    return (root, query, cb) -> {
      Join<BankAccountTransactionData, BankAccountData> grantJoin = root.join(BankAccountTransactionData_.account);
      return cb.equal(grantJoin.get(BankAccountData_.id), accountId);
    };
  }
  
  public static Specification<BankAccountTransactionData> oldestTime(OffsetDateTime oldestTime) {
    return (root, query, cb) -> {
      return cb.greaterThan(root.get(BankAccountTransactionData_.posted), oldestTime);
    };
  }

  public static Specification<BankAccountTransactionData> newestTime(OffsetDateTime newestTime) {
    return (root, query, cb) -> {
      return cb.lessThan(root.get(BankAccountTransactionData_.posted), newestTime);
    };
  }
  
  public static Specification<BankAccountTransactionData> minAmount(BigDecimal minAmount) {
    return (root, query, cb) -> {
      return cb.greaterThan(root.get(BankAccountTransactionData_.amount), minAmount);
    };
  }

  public static Specification<BankAccountTransactionData> maxAmount(BigDecimal maxAmount) {
    return (root, query, cb) -> {
      return cb.lessThan(root.get(BankAccountTransactionData_.amount), maxAmount);
    };
  }
  
  public static Specification<BankAccountTransactionData> textFilter(String text) {
    return (root, query, cb) -> {
      return cb.like(root.get(BankAccountTransactionData_.reference), "%" + text + "%");
    };
  }

}
