package io.biza.deepthought.data.specification;

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

public class GrantAccountSpecifications {

  public static Specification<GrantAccountData> subject(String subject) {
    return (root, query, cb) -> {
      Join<GrantAccountData, GrantData> grantJoin = root.join(GrantAccountData_.grant);
      return cb.equal(grantJoin.get(GrantData_.subject), subject);
    };
  }

  public static Specification<GrantAccountData> expiryBefore(OffsetDateTime expiry) {
    return (root, query, cb) -> {
      Join<GrantAccountData, GrantData> grantJoin = root.join(GrantAccountData_.grant);
      return cb.greaterThan(grantJoin.get(GrantData_.expiry), expiry);
    };
  }
  
  public static Specification<GrantAccountData> accountIds(UUID... accountIds) {
    return (root, query, cb) -> {
      Predicate[] accountFilter = new Predicate[accountIds.length];
      for(int i = 0; i < accountIds.length; i++) {
        accountFilter[i] = cb.equal(root.get(GrantAccountData_.id), accountIds[i]);
      }
      return cb.or(accountFilter);
    };
  }

  public static Specification<GrantAccountData> accountId(UUID accountId) {
    return (root, query, cb) -> {
      return cb.equal(root.get(GrantAccountData_.id), accountId);
    };
  }
  
  public static Specification<GrantAccountData> productCategory(BankingProductCategory productCategory) {
    return (root, query, cb) -> {
      Join<GrantAccountData, BankAccountData> accountJoin = root.join(GrantAccountData_.account);
      Join<BankAccountData, ProductData> rootProductJoin = accountJoin.join(BankAccountData_.product);
      Join<ProductData, BankProductData> productJoin = rootProductJoin.join(ProductData_.cdrBanking);
      return cb.equal(productJoin.get(BankProductData_.productCategory), productCategory);
    };
  }
  
  public static Specification<GrantAccountData> accountStatus(BankingAccountStatusWithAll accountStatus) {
    return (root, query, cb) -> {
      if(accountStatus == BankingAccountStatusWithAll.ALL) {
        return null;
      }
      
      Join<GrantAccountData, BankAccountData> accountJoin = root.join(GrantAccountData_.account);
      return cb.equal(accountJoin.get(BankAccountData_.status), DioAccountStatus.valueOf(accountStatus.toString()));
    };
  }
  
  public static Specification<GrantAccountData> ownerStatus(String subject, Boolean ownerStatus) {
    return (root, query, cb) -> {
      Join<GrantAccountData, GrantData> grantJoin = root.join(GrantAccountData_.grant);
      grantJoin = grantJoin.on(cb.equal(grantJoin.get(GrantData_.subject), subject));
      Join<GrantData, GrantCustomerData> grantCustomerJoin = grantJoin.join(GrantData_.customers);
      Join<GrantCustomerData, CustomerData> customerJoin = grantCustomerJoin.join(GrantCustomerData_.customer);
      Join<CustomerData, CustomerBankAccountData> customerBankAccountJoin = customerJoin.join(CustomerData_.accounts);
      return cb.equal(customerBankAccountJoin.get(CustomerBankAccountData_.owner), ownerStatus);
    };
  }


}
