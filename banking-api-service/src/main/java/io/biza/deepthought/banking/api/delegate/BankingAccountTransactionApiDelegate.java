package io.biza.deepthought.banking.api.delegate;

import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import io.biza.babelfish.cdr.models.responses.ResponseBankingTransactionByIdV1;
import io.biza.babelfish.cdr.models.responses.ResponseBankingTransactionListV1;
import io.biza.deepthought.banking.requests.RequestListTransactions;
import io.biza.deepthought.shared.exception.NotFoundException;

public interface BankingAccountTransactionApiDelegate {
  default Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  default ResponseEntity<ResponseBankingTransactionListV1> getTransactions(
      @NotNull @Valid UUID accountId, RequestListTransactions build) throws NotFoundException {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<ResponseBankingTransactionByIdV1> getTransactionDetail(@NotNull @Valid UUID accountId,
      @NotNull @Valid UUID transactionId) throws NotFoundException{
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

}
