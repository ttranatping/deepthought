package io.biza.deepthought.shared.persistence.specification;

import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import io.biza.deepthought.shared.persistence.model.grant.GrantAccountData_;
import io.biza.deepthought.shared.persistence.model.grant.GrantData_;
import io.biza.deepthought.shared.persistence.model.grant.GrantAccountData;
import io.biza.deepthought.shared.persistence.model.grant.GrantData;

public class GrantSpecifications {

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
}
