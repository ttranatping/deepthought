package io.biza.deepthought.admin.api.delegate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import io.biza.deepthought.admin.exceptions.ValidationListException;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccountDirectDebit;

public interface BankAccountDirectDebitAdminApiDelegate {
  default Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  default ResponseEntity<List<DioBankAccountDirectDebit>> listDirectDebits(UUID brandId, UUID branchId, UUID accountId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioBankAccountDirectDebit> getDirectDebit(UUID brandId, UUID branchId, UUID accountId, UUID directDebitId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioBankAccountDirectDebit> createDirectDebit(UUID brandId, UUID branchId, UUID accountId, DioBankAccountDirectDebit directDebitData)
      throws ValidationListException {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<Void> deleteDirectDebit(UUID brandId, UUID branchId, UUID accountId, UUID directDebitId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioBankAccountDirectDebit> updateDirectDebit(UUID brandId, UUID branchId, UUID accountId, UUID directDebitId,
      DioBankAccountDirectDebit directDebitData) throws ValidationListException {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

}
