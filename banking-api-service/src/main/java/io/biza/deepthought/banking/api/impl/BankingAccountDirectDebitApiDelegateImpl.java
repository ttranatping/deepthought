package io.biza.deepthought.banking.api.impl;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import io.biza.babelfish.cdr.models.payloads.banking.account.directdebit.BankingDirectDebitV1;
import io.biza.babelfish.cdr.models.responses.ResponseBankingDirectDebitAuthorisationListV1;
import io.biza.babelfish.cdr.models.responses.container.ResponseBankingDirectDebitAuthorisationListDataV1;
import io.biza.deepthought.banking.api.delegate.BankingAccountDirectDebitApiDelegate;
import io.biza.deepthought.banking.requests.RequestDirectDebitsByAccounts;
import io.biza.deepthought.banking.requests.RequestDirectDebitsByBulk;
import io.biza.deepthought.banking.service.DirectDebitService;
import io.biza.deepthought.shared.component.mapper.DeepThoughtMapper;
import io.biza.deepthought.shared.exception.NotFoundException;
import io.biza.deepthought.shared.persistence.model.bank.payments.DirectDebitData;
import io.biza.deepthought.shared.util.CDRContainerAttributes;
import lombok.extern.slf4j.Slf4j;

@Validated
@Controller
@Slf4j
public class BankingAccountDirectDebitApiDelegateImpl
    implements BankingAccountDirectDebitApiDelegate {

  @Autowired
  DirectDebitService directDebitService;

  @Autowired
  private DeepThoughtMapper mapper;


  @Override
  public ResponseEntity<ResponseBankingDirectDebitAuthorisationListV1> listByAccount(UUID accountId,
      RequestDirectDebitsByAccounts requestDirectDebits) throws NotFoundException {

    Page<DirectDebitData> directDebitsList =
        directDebitService.listDirectDebitsByAccount(accountId, requestDirectDebits);

    /**
     * Build response components
     */
    ResponseBankingDirectDebitAuthorisationListV1 listResponse =
        ResponseBankingDirectDebitAuthorisationListV1.builder()
            .meta(CDRContainerAttributes.toMetaPaginated(directDebitsList))
            .links(CDRContainerAttributes.toLinksPaginated(directDebitsList))
            .data(ResponseBankingDirectDebitAuthorisationListDataV1.builder()
                .directDebitAuthorisations(
                    mapper.mapAsList(directDebitsList.getContent(), BankingDirectDebitV1.class))
                .build())
            .build();
    LOG.debug("List response came back with: {}", listResponse);
    return ResponseEntity.ok(listResponse);
  }

  @Override
  public ResponseEntity<ResponseBankingDirectDebitAuthorisationListV1> listByAccountList(
      RequestDirectDebitsByAccounts requestDirectDebits) {
    Page<DirectDebitData> directDebitsList =
        directDebitService.listDirectDebitsByAccountList(requestDirectDebits);

    /**
     * Build response components
     */
    ResponseBankingDirectDebitAuthorisationListV1 listResponse =
        ResponseBankingDirectDebitAuthorisationListV1.builder()
            .meta(CDRContainerAttributes.toMetaPaginated(directDebitsList))
            .links(CDRContainerAttributes.toLinksPaginated(directDebitsList))
            .data(ResponseBankingDirectDebitAuthorisationListDataV1.builder()
                .directDebitAuthorisations(
                    mapper.mapAsList(directDebitsList.getContent(), BankingDirectDebitV1.class))
                .build())
            .build();
    LOG.debug("List response came back with: {}", listResponse);
    return ResponseEntity.ok(listResponse);
  }

  @Override
  public ResponseEntity<ResponseBankingDirectDebitAuthorisationListV1> listAll(
      RequestDirectDebitsByBulk requestDirectDebits) {
    Page<DirectDebitData> directDebitsList =
        directDebitService.listDirectDebitsWithFilter(requestDirectDebits);

    /**
     * Build response components
     */
    ResponseBankingDirectDebitAuthorisationListV1 listResponse =
        ResponseBankingDirectDebitAuthorisationListV1.builder()
            .meta(CDRContainerAttributes.toMetaPaginated(directDebitsList))
            .links(CDRContainerAttributes.toLinksPaginated(directDebitsList))
            .data(ResponseBankingDirectDebitAuthorisationListDataV1.builder()
                .directDebitAuthorisations(
                    mapper.mapAsList(directDebitsList.getContent(), BankingDirectDebitV1.class))
                .build())
            .build();
    LOG.debug("List response came back with: {}", listResponse);
    return ResponseEntity.ok(listResponse);
  }
}
