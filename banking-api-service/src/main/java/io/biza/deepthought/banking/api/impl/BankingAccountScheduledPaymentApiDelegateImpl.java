package io.biza.deepthought.banking.api.impl;

import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import io.biza.babelfish.cdr.enumerations.BankingAccountStatusWithAll;
import io.biza.babelfish.cdr.enumerations.BankingProductCategory;
import io.biza.babelfish.cdr.models.payloads.banking.account.BankingAccountDetailV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.BankingAccountV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.scheduled.BankingScheduledPaymentV1;
import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductDetailV2;
import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductV2;
import io.biza.babelfish.cdr.models.responses.ResponseBankingAccountByIdV1;
import io.biza.babelfish.cdr.models.responses.ResponseBankingAccountListV1;
import io.biza.babelfish.cdr.models.responses.ResponseBankingAccountsBalanceByIdV1;
import io.biza.babelfish.cdr.models.responses.ResponseBankingAccountsBalanceListV1;
import io.biza.babelfish.cdr.models.responses.ResponseBankingScheduledPaymentsListV1;
import io.biza.babelfish.cdr.models.responses.ResponseBankingProductByIdV2;
import io.biza.babelfish.cdr.models.responses.ResponseBankingProductListV2;
import io.biza.babelfish.cdr.models.responses.ResponseBankingScheduledPaymentsListV1;
import io.biza.babelfish.cdr.models.responses.container.ResponseBankingAccountListDataV1;
import io.biza.babelfish.cdr.models.responses.container.ResponseBankingScheduledPaymentsListDataV1;
import io.biza.babelfish.cdr.models.responses.container.ResponseBankingProductListDataV2;
import io.biza.deepthought.banking.api.delegate.BankingAccountApiDelegate;
import io.biza.deepthought.banking.api.delegate.BankingAccountBalanceApiDelegate;
import io.biza.deepthought.banking.api.delegate.BankingAccountScheduledPaymentApiDelegate;
import io.biza.deepthought.banking.api.delegate.BankingAccountScheduledPaymentApiDelegate;
import io.biza.deepthought.banking.requests.RequestBalancesByAccounts;
import io.biza.deepthought.banking.requests.RequestBalancesByCriteria;
import io.biza.deepthought.banking.requests.RequestScheduledPaymentsByAccounts;
import io.biza.deepthought.banking.requests.RequestScheduledPaymentsByBulk;
import io.biza.deepthought.banking.requests.RequestListAccounts;
import io.biza.deepthought.banking.requests.RequestScheduledPaymentsByAccounts;
import io.biza.deepthought.banking.requests.RequestScheduledPaymentsByBulk;
import io.biza.deepthought.banking.service.ScheduledPaymentsService;
import io.biza.deepthought.banking.service.GrantService;
import io.biza.deepthought.data.component.DeepThoughtMapper;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountData;
import io.biza.deepthought.data.persistence.model.bank.payments.CustomerBankScheduledPaymentData;
import io.biza.deepthought.data.persistence.model.grant.GrantAccountData;
import io.biza.deepthought.data.persistence.model.product.ProductData;
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

    Page<CustomerBankScheduledPaymentData> directDebitsList =
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
    Page<CustomerBankScheduledPaymentData> directDebitsList =
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
    Page<CustomerBankScheduledPaymentData> directDebitsList =
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
