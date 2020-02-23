package io.biza.deepthought.shared.persistence.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.shared.persistence.model.grant.GrantAccountData;

@Repository
public interface GrantAccountRepository extends JpaRepository<GrantAccountData, UUID>, JpaSpecificationExecutor<GrantAccountData> {
  
  
}
