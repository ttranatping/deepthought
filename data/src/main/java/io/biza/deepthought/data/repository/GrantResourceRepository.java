package io.biza.deepthought.data.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.grant.GrantResourceData;

@Repository
public interface GrantResourceRepository extends JpaRepository<GrantResourceData, UUID>, JpaSpecificationExecutor<GrantResourceData> {
  public Optional<GrantResourceData> findByIdAndGrantId(UUID id, UUID grantId);
  public Optional<GrantResourceData> findByGrantIdAndId(UUID grantId, UUID resourceId);
  public Optional<GrantResourceData> findByGrantIdAndObjectId(UUID grantId, UUID objectId);
  
}
