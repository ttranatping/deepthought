package io.biza.deepthought.shared.persistence.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.shared.persistence.model.bank.payments.DirectDebitAuthorisedEntityData;

@Repository
public interface BankAuthorisedEntityRepository extends JpaRepository<DirectDebitAuthorisedEntityData, UUID> {
  
}
