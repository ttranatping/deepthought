package io.biza.deepthought.data.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.account.AccountData;
import io.biza.deepthought.data.persistence.model.customer.CustomerData;

@Repository
public interface AccountRepository extends JpaRepository<AccountData, UUID> {
  
}
