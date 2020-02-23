package io.biza.deepthought.data.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.customer.CustomerData;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerData, UUID> {
  public List<CustomerData> findAllByBrandId(UUID brandId);
  public Optional<CustomerData> findByIdAndBrandId(UUID id, UUID brandId);
}
