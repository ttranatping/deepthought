package io.biza.deepthought.data.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.bank.payments.ScheduledPaymentData;

@Repository
public interface CustomerBankScheduledPaymentRepository extends JpaRepository<ScheduledPaymentData, UUID>, JpaSpecificationExecutor<ScheduledPaymentData> {

}
