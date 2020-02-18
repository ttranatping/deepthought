package io.biza.deepthought.data.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.customer.bank.CustomerBankAccountData;

@Repository
public interface CustomerBankAccountRepository extends JpaRepository<CustomerBankAccountData, UUID> {
  public List<CustomerBankAccountData> findAllByCustomerIdAndCustomerBrandId(UUID customerId, UUID brandId);
  public Optional<CustomerBankAccountData> findByIdAndCustomerIdAndCustomerBrandId(UUID id, UUID customerId, UUID brandId);
  public Optional<CustomerBankAccountData> findByAccountIdAndCustomerIdAndCustomerBrandId(UUID accountId, UUID customerId, UUID brandId);
  
}
