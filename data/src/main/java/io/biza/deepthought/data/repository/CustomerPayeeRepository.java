package io.biza.deepthought.data.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.bank.payments.PayeeData;

@Repository
public interface CustomerPayeeRepository extends JpaRepository<PayeeData, UUID> {
  public List<PayeeData> findAllByCustomerIdAndCustomerBrandId(UUID customerId, UUID brandId);
  public Optional<PayeeData> findByIdAndCustomerIdAndCustomerBrandId(UUID id, UUID customerId, UUID brandId);
}
