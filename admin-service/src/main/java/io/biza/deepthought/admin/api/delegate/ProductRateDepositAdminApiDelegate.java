package io.biza.deepthought.admin.api.delegate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import io.biza.deepthought.admin.exceptions.ValidationListException;
import io.biza.deepthought.data.payloads.dio.product.DioBankProductRateDeposit;

public interface ProductRateDepositAdminApiDelegate {
  default Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  default ResponseEntity<List<DioBankProductRateDeposit>> listProductRateDeposits(UUID brandId,
      UUID productId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioBankProductRateDeposit> getProductRateDeposit(UUID brandId, UUID productId,
      UUID rateId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioBankProductRateDeposit> createProductRateDeposit(UUID brandId, UUID productId,
      DioBankProductRateDeposit rate) throws ValidationListException {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<Void> deleteProductRateDeposit(UUID brandId, UUID productId, UUID rateId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioBankProductRateDeposit> updateProductRateDeposit(UUID brandId, UUID productId,
      UUID rateId, DioBankProductRateDeposit rate) throws ValidationListException {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

}
