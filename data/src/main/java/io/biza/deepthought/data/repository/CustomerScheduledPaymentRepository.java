package io.biza.deepthought.data.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.bank.payments.ScheduledPaymentData;

@Repository
public interface CustomerScheduledPaymentRepository extends JpaRepository<ScheduledPaymentData, UUID> {
  public List<ScheduledPaymentData> findAllByCustomerIdAndCustomerBrandId(UUID customerId, UUID brandId);
  public Optional<ScheduledPaymentData> findByIdAndCustomerIdAndCustomerBrandId(UUID id, UUID customerId, UUID brandId);
}
