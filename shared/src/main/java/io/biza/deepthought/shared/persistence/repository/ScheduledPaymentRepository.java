package io.biza.deepthought.shared.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.shared.persistence.model.bank.payments.ScheduledPaymentData;

@Repository
public interface ScheduledPaymentRepository extends JpaRepository<ScheduledPaymentData, UUID>, JpaSpecificationExecutor<ScheduledPaymentData> {
  public List<ScheduledPaymentData> findAllByCustomerIdAndCustomerBrandId(UUID customerId, UUID brandId);
  public Optional<ScheduledPaymentData> findByIdAndCustomerIdAndCustomerBrandId(UUID id, UUID customerId, UUID brandId);
}
