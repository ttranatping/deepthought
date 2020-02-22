package io.biza.deepthought.data.specification;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountData;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountData_;
import io.biza.deepthought.data.persistence.model.bank.transaction.BankAccountTransactionData;
import io.biza.deepthought.data.persistence.model.bank.transaction.BankAccountTransactionData_;

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
