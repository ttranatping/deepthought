package io.biza.deepthought.data.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.bank.payments.CustomerBankScheduledPaymentData;
import io.biza.deepthought.data.persistence.model.bank.payments.CustomerBankPayeeData;

@Repository
public interface CustomerScheduledPaymentRepository extends JpaRepository<CustomerBankScheduledPaymentData, UUID> {
  public List<CustomerBankScheduledPaymentData> findAllByCustomerIdAndCustomerBrandId(UUID customerId, UUID brandId);
  public Optional<CustomerBankScheduledPaymentData> findByIdAndCustomerIdAndCustomerBrandId(UUID id, UUID customerId, UUID brandId);
}
