package io.biza.deepthought.shared.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.shared.persistence.model.customer.bank.CustomerAccountData;

@Repository
public interface CustomerAccountRepository extends JpaRepository<CustomerAccountData, UUID> {
  public List<CustomerAccountData> findAllByCustomerIdAndCustomerBrandId(UUID customerId, UUID brandId);
  public Optional<CustomerAccountData> findByIdAndCustomerIdAndCustomerBrandId(UUID id, UUID customerId, UUID brandId);
  public Optional<CustomerAccountData> findByBankAccountIdAndCustomerIdAndCustomerBrandId(UUID accountId, UUID customerId, UUID brandId);
  
}
