package io.biza.deepthought.admin.api.delegate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import io.biza.deepthought.admin.exceptions.ValidationListException;
import io.biza.deepthought.shared.payloads.dio.banking.DioCustomerScheduledPayment;

public interface CustomerScheduledPaymentAdminApiDelegate {
  default Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  default ResponseEntity<List<DioCustomerScheduledPayment>> listScheduledPayments(UUID brandId, UUID customerId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioCustomerScheduledPayment> getScheduledPayment(UUID brandId, UUID customerId, UUID payeeId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioCustomerScheduledPayment> createScheduledPayment(UUID brandId, UUID customerId, DioCustomerScheduledPayment payee)
      throws ValidationListException {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<Void> deleteScheduledPayment(UUID brandId, UUID customerId, UUID payeeId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioCustomerScheduledPayment> updateScheduledPayment(UUID brandId, UUID customerId, UUID payeeId,
      DioCustomerScheduledPayment customer) throws ValidationListException {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }
}
