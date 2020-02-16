package io.biza.deepthought.data.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.account.AccountData;
import io.biza.deepthought.data.persistence.model.account.AccountLoanAccountData;
import io.biza.deepthought.data.persistence.model.account.AccountTermDepositData;
import io.biza.deepthought.data.persistence.model.customer.CustomerAccountCardData;
import io.biza.deepthought.data.persistence.model.customer.CustomerData;

@Repository
public interface AccountLoanAccountRepository extends JpaRepository<AccountLoanAccountData, UUID> {
  
}
