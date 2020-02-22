package io.biza.deepthought.banking.api.delegate;

import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import io.biza.babelfish.cdr.models.responses.ResponseBankingScheduledPaymentsListV1;
import io.biza.deepthought.banking.requests.RequestScheduledPaymentsByAccounts;
import io.biza.deepthought.banking.requests.RequestScheduledPaymentsByBulk;
import io.biza.deepthought.shared.exception.NotFoundException;

public interface BankingAccountScheduledPaymentApiDelegate {
  default Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  default ResponseEntity<ResponseBankingScheduledPaymentsListV1> listByAccount(
      UUID accountId, RequestScheduledPaymentsByAccounts requestList) throws NotFoundException{
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<ResponseBankingScheduledPaymentsListV1> listAll(
      RequestScheduledPaymentsByBulk requestList){
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<ResponseBankingScheduledPaymentsListV1> listByAccountList(
      RequestScheduledPaymentsByAccounts requestList){
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }


}
