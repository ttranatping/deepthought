package io.biza.deepthought.data.specification;

import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import io.biza.deepthought.data.persistence.model.grant.GrantCustomerData;
import io.biza.deepthought.data.persistence.model.grant.GrantCustomerData_;
import io.biza.deepthought.data.persistence.model.grant.GrantData;
import io.biza.deepthought.data.persistence.model.grant.GrantData_;

public class GrantCustomerSpecifications {

  public static Specification<GrantCustomerData> subject(String subject) {
    return (root, query, cb) -> {
      Join<GrantCustomerData, GrantData> grantJoin = root.join(GrantCustomerData_.grant);
      return cb.equal(grantJoin.get(GrantData_.subject), subject);
    };
  }

  public static Specification<GrantCustomerData> expiryBefore(OffsetDateTime expiry) {
    return (root, query, cb) -> {
      Join<GrantCustomerData, GrantData> grantJoin = root.join(GrantCustomerData_.grant);
      return cb.greaterThan(grantJoin.get(GrantData_.expiry), expiry);
    };
  }
  
  public static Specification<GrantCustomerData> customerIds(UUID... customerIds) {
    return (root, query, cb) -> {
      Predicate[] accountFilter = new Predicate[customerIds.length];
      for(int i = 0; i < customerIds.length; i++) {
        accountFilter[i] = cb.equal(root.get(GrantCustomerData_.id), customerIds[i]);
      }
      return cb.or(accountFilter);
    };
  }

  public static Specification<GrantCustomerData> customerId(UUID customerId) {
    return (root, query, cb) -> {
      return cb.equal(root.get(GrantCustomerData_.id), customerId);
    };
  }
}
