package io.biza.deepthought.data.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountData;
import io.biza.deepthought.data.persistence.model.bank.payments.CustomerBankPayeeData;

@Repository
public interface CustomerPayeeRepository extends JpaRepository<CustomerBankPayeeData, UUID> {
  public List<CustomerBankPayeeData> findAllByCustomerIdAndCustomerBrandId(UUID customerId, UUID brandId);
  public Optional<CustomerBankPayeeData> findByIdAndCustomerIdAndCustomerBrandId(UUID id, UUID customerId, UUID brandId);
}
