package io.biza.deepthought.shared.persistence.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.shared.persistence.model.product.banking.ProductBankData;

@Repository
public interface ProductBankRepository extends JpaRepository<ProductBankData, UUID>, JpaSpecificationExecutor<ProductBankData> {

}
