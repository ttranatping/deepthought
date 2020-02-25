package io.biza.deepthought.shared.persistence.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.shared.persistence.model.grant.GrantCustomerAccountData;
import io.biza.deepthought.shared.persistence.model.grant.GrantData;

@Repository
public interface GrantCustomerAccountRepository extends JpaRepository<GrantCustomerAccountData, UUID>, JpaSpecificationExecutor<GrantCustomerAccountData> {
  
}
