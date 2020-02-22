package io.biza.deepthought.data.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.grant.GrantAccountData;

@Repository
public interface GrantAccountRepository extends JpaRepository<GrantAccountData, UUID>, JpaSpecificationExecutor<GrantAccountData> {
  
  
}
