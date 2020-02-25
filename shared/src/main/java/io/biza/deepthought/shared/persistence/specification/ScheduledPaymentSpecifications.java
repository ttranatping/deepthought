package io.biza.deepthought.shared.persistence.specification;

import java.util.Arrays;
import java.util.UUID;
import javax.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import io.biza.deepthought.shared.persistence.model.bank.account.BankAccountData_;
import io.biza.deepthought.shared.persistence.model.bank.payments.ScheduledPaymentData_;
import io.biza.deepthought.shared.persistence.model.bank.account.BankAccountData;
import io.biza.deepthought.shared.persistence.model.bank.payments.ScheduledPaymentData;

public class ScheduledPaymentSpecifications {

  public static Specification<ScheduledPaymentData> accountId(UUID accountId) {
    return (root, query, cb) -> {
      Join<ScheduledPaymentData, BankAccountData> grantJoin = root.join(ScheduledPaymentData_.from);
      return cb.equal(grantJoin.get(BankAccountData_.id), accountId);
    };
  }
  
  public static Specification<ScheduledPaymentData> accountIds(UUID... accountIds) {
    return (root, query, cb) -> {
      Join<ScheduledPaymentData, BankAccountData> grantJoin = root.join(ScheduledPaymentData_.from);
      return grantJoin.get(BankAccountData_.id).in(Arrays.asList(accountIds));
    };
  }
  
}
