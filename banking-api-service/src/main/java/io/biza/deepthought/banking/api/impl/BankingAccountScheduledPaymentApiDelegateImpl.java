package io.biza.deepthought.banking.api.impl;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.scheduled.BankingScheduledPaymentV1;
import io.biza.babelfish.cdr.models.responses.ResponseBankingScheduledPaymentsListV1;
import io.biza.babelfish.cdr.models.responses.container.ResponseBankingScheduledPaymentsListDataV1;
import io.biza.deepthought.banking.api.delegate.BankingAccountScheduledPaymentApiDelegate;
import io.biza.deepthought.banking.requests.RequestScheduledPaymentsByAccounts;
import io.biza.deepthought.banking.requests.RequestScheduledPaymentsByBulk;
import io.biza.deepthought.banking.service.ScheduledPaymentsService;
import io.biza.deepthought.data.component.DeepThoughtMapper;
import io.biza.deepthought.data.persistence.model.bank.payments.ScheduledPaymentData;
import io.biza.deepthought.shared.exception.NotFoundException;
import io.biza.deepthought.shared.support.CDRContainerAttributes;
import lombok.extern.slf4j.Slf4j;

@Validated
@Controller
@Slf4j
public class BankingAccountScheduledPaymentApiDelegateImpl
    implements BankingAccountScheduledPaymentApiDelegate {

  @Autowired
  ScheduledPaymentsService directDebitService;

  @Autowired
  private DeepThoughtMapper mapper;


  @Override
  public ResponseEntity<ResponseBankingScheduledPaymentsListV1> listByAccount(UUID accountId,
      RequestScheduledPaymentsByAccounts requestScheduledPayments) throws NotFoundException {

    Page<ScheduledPaymentData> directDebitsList =
        directDebitService.listScheduledPaymentsByAccount(accountId, requestScheduledPayments);

    /**
     * Build response components
     */
    ResponseBankingScheduledPaymentsListV1 listResponse =
        ResponseBankingScheduledPaymentsListV1.builder()
            .meta(CDRContainerAttributes.toMetaPaginated(directDebitsList))
            .links(CDRContainerAttributes.toLinksPaginated(directDebitsList))
            .data(ResponseBankingScheduledPaymentsListDataV1.builder()
                .scheduledPayments(
                    mapper.mapAsList(directDebitsList.getContent(), BankingScheduledPaymentV1.class))
                .build())
            .build();
    LOG.debug("List response came back with: {}", listResponse);
    return ResponseEntity.ok(listResponse);
  }

  @Override
  public ResponseEntity<ResponseBankingScheduledPaymentsListV1> listByAccountList(
      RequestScheduledPaymentsByAccounts requestScheduledPayments) {
    Page<ScheduledPaymentData> directDebitsList =
        directDebitService.listScheduledPaymentsByAccountList(requestScheduledPayments);

    /**
     * Build response components
     */
    ResponseBankingScheduledPaymentsListV1 listResponse =
        ResponseBankingScheduledPaymentsListV1.builder()
            .meta(CDRContainerAttributes.toMetaPaginated(directDebitsList))
            .links(CDRContainerAttributes.toLinksPaginated(directDebitsList))
            .data(ResponseBankingScheduledPaymentsListDataV1.builder()
                .scheduledPayments(
                    mapper.mapAsList(directDebitsList.getContent(), BankingScheduledPaymentV1.class))
                .build())
            .build();
    LOG.debug("List response came back with: {}", listResponse);
    return ResponseEntity.ok(listResponse);
  }

  @Override
  public ResponseEntity<ResponseBankingScheduledPaymentsListV1> listAll(
      RequestScheduledPaymentsByBulk requestScheduledPayments) {
    Page<ScheduledPaymentData> directDebitsList =
        directDebitService.listScheduledPaymentsWithFilter(requestScheduledPayments);

    /**
     * Build response components
     */
    ResponseBankingScheduledPaymentsListV1 listResponse =
        ResponseBankingScheduledPaymentsListV1.builder()
            .meta(CDRContainerAttributes.toMetaPaginated(directDebitsList))
            .links(CDRContainerAttributes.toLinksPaginated(directDebitsList))
            .data(ResponseBankingScheduledPaymentsListDataV1.builder()
                .scheduledPayments(
                    mapper.mapAsList(directDebitsList.getContent(), BankingScheduledPaymentV1.class))
                .build())
            .build();
    LOG.debug("List response came back with: {}", listResponse);
    return ResponseEntity.ok(listResponse);
  }
}
