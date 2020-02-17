package io.biza.deepthought.admin.api.delegate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import io.biza.deepthought.admin.exceptions.ValidationListException;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccountTransaction;

public interface BankAccountTransactionAdminApiDelegate {
  default Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  default ResponseEntity<List<DioBankAccountTransaction>> listTransactions(UUID brandId, UUID branchId, UUID accountId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioBankAccountTransaction> getTransaction(UUID brandId, UUID branchId, UUID accountId, UUID transactionId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioBankAccountTransaction> createTransaction(UUID brandId, UUID branchId, UUID accountId, DioBankAccountTransaction transactionData)
      throws ValidationListException {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<Void> deleteTransaction(UUID brandId, UUID branchId, UUID accountId, UUID transactionId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioBankAccountTransaction> updateTransaction(UUID brandId, UUID branchId, UUID accountId, UUID transactionId,
      DioBankAccountTransaction transactionData) throws ValidationListException {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

}
