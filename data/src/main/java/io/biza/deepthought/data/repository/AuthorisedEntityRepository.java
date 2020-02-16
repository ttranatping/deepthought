package io.biza.deepthought.data.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.account.AccountCreditCardData;
import io.biza.deepthought.data.persistence.model.account.AccountData;
import io.biza.deepthought.data.persistence.model.customer.CustomerData;
import io.biza.deepthought.data.persistence.model.payments.AuthorisedEntityData;
import io.biza.deepthought.data.persistence.model.payments.DirectDebitData;

@Repository
public interface AuthorisedEntityRepository extends JpaRepository<AuthorisedEntityData, UUID> {
  
}
