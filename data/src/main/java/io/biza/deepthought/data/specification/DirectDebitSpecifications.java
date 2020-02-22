package io.biza.deepthought.data.specification;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
import io.biza.deepthought.data.persistence.model.bank.payments.DirectDebitData;
import io.biza.deepthought.data.persistence.model.bank.payments.BankAccountDirectDebitData_;
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
import io.biza.deepthought.data.persistence.model.product.banking.BankProductData;

public class DirectDebitSpecifications {

  public static Specification<DirectDebitData> accountId(UUID accountId) {
    return (root, query, cb) -> {
      Join<DirectDebitData, BankAccountData> grantJoin = root.join(BankAccountDirectDebitData_.account);
      return cb.equal(grantJoin.get(BankAccountData_.id), accountId);
    };
  }
  
  public static Specification<DirectDebitData> accountIds(UUID... accountIds) {
    return (root, query, cb) -> {
      Join<DirectDebitData, BankAccountData> grantJoin = root.join(BankAccountDirectDebitData_.account);
      return grantJoin.get(BankAccountData_.id).in(Arrays.asList(accountIds));
    };
  }
  
}
