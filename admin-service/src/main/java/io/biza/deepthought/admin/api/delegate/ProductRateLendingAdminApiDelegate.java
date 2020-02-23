package io.biza.deepthought.admin.api.delegate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import io.biza.deepthought.admin.exceptions.ValidationListException;
import io.biza.deepthought.shared.payloads.dio.product.DioProductRateLending;

public interface ProductRateLendingAdminApiDelegate {
  default Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  default ResponseEntity<List<DioProductRateLending>> listProductRateLendings(UUID brandId,
      UUID productId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioProductRateLending> getProductRateLending(UUID brandId, UUID productId,
      UUID rateId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioProductRateLending> createProductRateLending(UUID brandId, UUID productId,
      DioProductRateLending rate) throws ValidationListException {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<Void> deleteProductRateLending(UUID brandId, UUID productId, UUID rateId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioProductRateLending> updateProductRateLending(UUID brandId, UUID productId,
      UUID rateId, DioProductRateLending rate) throws ValidationListException {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

}
