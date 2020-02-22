package io.biza.deepthought.data.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountData;
import io.biza.deepthought.data.persistence.model.bank.payments.CustomerBankPayeeData;
import io.biza.deepthought.data.persistence.model.customer.CustomerData;
import io.biza.deepthought.data.persistence.model.grant.GrantAccountData;
import io.biza.deepthought.data.persistence.model.grant.GrantData;
import io.biza.deepthought.data.persistence.model.grant.GrantResourceData;
import io.biza.deepthought.data.persistence.model.product.ProductData;

@Repository
public interface GrantResourceRepository extends JpaRepository<GrantResourceData, UUID>, JpaSpecificationExecutor<GrantResourceData> {
  public Optional<GrantResourceData> findByIdAndGrantId(UUID id, UUID grantId);
  public Optional<GrantResourceData> findByGrantIdAndId(UUID grantId, UUID resourceId);
  public Optional<GrantResourceData> findByGrantIdAndObjectId(UUID grantId, UUID objectId);
  
}
