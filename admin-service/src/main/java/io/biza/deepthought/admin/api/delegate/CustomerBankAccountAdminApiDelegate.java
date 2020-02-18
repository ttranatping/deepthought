package io.biza.deepthought.admin.api.delegate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import io.biza.deepthought.admin.exceptions.ValidationListException;
import io.biza.deepthought.data.payloads.dio.common.DioCustomerBankAccount;
import io.biza.deepthought.data.payloads.requests.RequestCustomerBankAccountConnection;

public interface CustomerBankAccountAdminApiDelegate {
  default Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  default ResponseEntity<List<DioCustomerBankAccount>> listAssociations(UUID brandId,
      UUID customerId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioCustomerBankAccount> associateAccount(UUID brandId, UUID customerId,
      RequestCustomerBankAccountConnection accountRequest) throws ValidationListException {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<Void> unassociateAccount(UUID brandId, UUID customerId, UUID accountId)
      throws ValidationListException {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }


  default ResponseEntity<DioCustomerBankAccount> getAssociation(UUID brandId, UUID customerId,
      UUID associationId) throws ValidationListException {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }
}
