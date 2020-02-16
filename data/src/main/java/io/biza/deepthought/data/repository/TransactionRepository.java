package io.biza.deepthought.data.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.account.AccountCreditCardData;
import io.biza.deepthought.data.persistence.model.account.AccountData;
import io.biza.deepthought.data.persistence.model.customer.CustomerData;
import io.biza.deepthought.data.persistence.model.payments.DirectDebitData;
import io.biza.deepthought.data.persistence.model.payments.PayeeData;
import io.biza.deepthought.data.persistence.model.payments.ScheduledPaymentData;
import io.biza.deepthought.data.persistence.model.transaction.TransactionData;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionData, UUID> {
  
}
