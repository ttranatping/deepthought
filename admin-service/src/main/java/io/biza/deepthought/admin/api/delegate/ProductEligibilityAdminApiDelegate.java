package io.biza.deepthought.admin.api.delegate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import io.biza.deepthought.admin.exceptions.ValidationListException;
import io.biza.deepthought.data.payloads.dio.product.DioBankProductEligibility;

public interface ProductEligibilityAdminApiDelegate {
  default Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  default ResponseEntity<List<DioBankProductEligibility>> listProductEligibilitys(UUID brandId,
      UUID productId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioBankProductEligibility> getProductEligibility(UUID brandId, UUID productId,
      UUID eligibilityId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioBankProductEligibility> createProductEligibility(UUID brandId,
      UUID productId, DioBankProductEligibility eligibility) throws ValidationListException {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<Void> deleteProductEligibility(UUID brandId, UUID productId,
      UUID eligibilityId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioBankProductEligibility> updateProductEligibility(UUID brandId,
      UUID productId, UUID eligibilityId, DioBankProductEligibility eligibility) throws ValidationListException {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

}
