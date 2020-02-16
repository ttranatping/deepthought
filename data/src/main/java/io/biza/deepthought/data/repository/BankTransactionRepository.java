package io.biza.deepthought.data.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.bank.transaction.BankTransactionData;

@Repository
public interface BankTransactionRepository extends JpaRepository<BankTransactionData, UUID> {
  
}
