package io.biza.deepthought.shared.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.shared.persistence.model.bank.payments.PayeeData;

@Repository
public interface PayeeRepository extends JpaRepository<PayeeData, UUID>, JpaSpecificationExecutor<PayeeData> {
  public List<PayeeData> findAllByCustomerIdAndCustomerBrandId(UUID customerId, UUID brandId);
  public Optional<PayeeData> findByIdAndCustomerIdAndCustomerBrandId(UUID id, UUID customerId, UUID brandId);
}
