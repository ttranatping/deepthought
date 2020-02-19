package io.biza.deepthought.banking.api.delegate;

import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import io.biza.babelfish.cdr.models.responses.ResponseBankingDirectDebitAuthorisationListV1;
import io.biza.deepthought.banking.requests.RequestDirectDebitsByAccounts;
import io.biza.deepthought.banking.requests.RequestDirectDebitsByBulk;

public interface BankingAccountDirectDebitApiDelegate {
  default Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  default ResponseEntity<ResponseBankingDirectDebitAuthorisationListV1> listByAccount(
      UUID accountId, RequestDirectDebitsByAccounts requestDirectDebits) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<ResponseBankingDirectDebitAuthorisationListV1> listByAccountList(
      RequestDirectDebitsByAccounts requestDirectDebits) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<ResponseBankingDirectDebitAuthorisationListV1> listAll(
      RequestDirectDebitsByBulk requestDirectDebits) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }



}
