package io.biza.deepthought.banking.api.delegate;

import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import io.biza.babelfish.cdr.models.responses.ResponseBankingAccountsBalanceByIdV1;
import io.biza.babelfish.cdr.models.responses.ResponseBankingAccountsBalanceListV1;
import io.biza.deepthought.banking.requests.RequestBalancesByAccounts;
import io.biza.deepthought.banking.requests.RequestBalancesByBulk;

public interface BankingAccountBalanceApiDelegate {
  default Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  default ResponseEntity<ResponseBankingAccountsBalanceListV1> listBalancesBulk(RequestBalancesByBulk requestListBulkBalances) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }
  
  default ResponseEntity<ResponseBankingAccountsBalanceListV1> listAccountBalances(
      RequestBalancesByAccounts build) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<ResponseBankingAccountsBalanceByIdV1> getAccountBalance(UUID accountId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

}
