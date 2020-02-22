package io.biza.deepthought.banking.api.impl;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import io.biza.babelfish.cdr.models.payloads.banking.account.transaction.BankingTransactionDetailV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.transaction.BankingTransactionV1;
import io.biza.babelfish.cdr.models.responses.ResponseBankingTransactionByIdV1;
import io.biza.babelfish.cdr.models.responses.ResponseBankingTransactionListV1;
import io.biza.babelfish.cdr.models.responses.container.ResponseBankingTransactionListDataV1;
import io.biza.deepthought.banking.api.delegate.BankingAccountTransactionApiDelegate;
import io.biza.deepthought.banking.requests.RequestListTransactions;
import io.biza.deepthought.banking.service.GrantService;
import io.biza.deepthought.banking.service.TransactionService;
import io.biza.deepthought.data.component.DeepThoughtMapper;
import io.biza.deepthought.data.persistence.model.bank.transaction.BankAccountTransactionData;
import io.biza.deepthought.shared.exception.NotFoundException;
import io.biza.deepthought.shared.support.CDRContainerAttributes;
import lombok.extern.slf4j.Slf4j;

@Validated
@Controller
@Slf4j
public class BankingAccountTransactionApiDelegateImpl
    implements BankingAccountTransactionApiDelegate {

  @Autowired
  TransactionService transactionService;
  
  @Autowired
  GrantService accountService;
  
  @Autowired
  private DeepThoughtMapper mapper;


  @Override
  public ResponseEntity<ResponseBankingTransactionListV1> getTransactions(
      UUID accountId, RequestListTransactions requestListTransactions) throws NotFoundException {
    
    Page<BankAccountTransactionData> transactionData = transactionService.listTransactions(accountId,  requestListTransactions);

    /**
     * Build response components
     */
    ResponseBankingTransactionListV1 listResponse = ResponseBankingTransactionListV1
        .builder().meta(CDRContainerAttributes.toMetaPaginated(transactionData))
        .links(CDRContainerAttributes.toLinksPaginated(transactionData))
        .data(ResponseBankingTransactionListDataV1.builder().transactions(mapper.mapAsList(transactionData.getContent(), BankingTransactionV1.class)).build())
        .build();
    LOG.debug("List response came back with: {}", listResponse);
    return ResponseEntity.ok(listResponse);
  }

  @Override
  public ResponseEntity<ResponseBankingTransactionByIdV1> getTransactionDetail(UUID accountId,
      UUID transactionId) throws NotFoundException{
    BankAccountTransactionData transaction = transactionService.getTransaction(accountId, transactionId);
    ResponseBankingTransactionByIdV1 transactionResponse = new ResponseBankingTransactionByIdV1();
    transactionResponse.meta(CDRContainerAttributes.toMeta());
    transactionResponse.links(CDRContainerAttributes.toLinks());
    transactionResponse.data(mapper.map(transaction, BankingTransactionDetailV1.class));
    return ResponseEntity.ok(transactionResponse);
  }
}
