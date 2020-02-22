package io.biza.deepthought.banking.api.impl;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import io.biza.babelfish.cdr.models.payloads.banking.account.balance.BankingBalanceV1;
import io.biza.babelfish.cdr.models.responses.ResponseBankingAccountsBalanceByIdV1;
import io.biza.babelfish.cdr.models.responses.ResponseBankingAccountsBalanceListV1;
import io.biza.babelfish.cdr.models.responses.container.ResponseBankingAccountsBalanceListDataV1;
import io.biza.deepthought.banking.api.delegate.BankingAccountBalanceApiDelegate;
import io.biza.deepthought.banking.requests.RequestBalancesByAccounts;
import io.biza.deepthought.banking.requests.RequestBalancesByCriteria;
import io.biza.deepthought.banking.requests.RequestListAccounts;
import io.biza.deepthought.banking.service.AccountBalanceService;
import io.biza.deepthought.data.component.DeepThoughtMapper;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccountBalance;
import io.biza.deepthought.shared.exception.NotFoundException;
import io.biza.deepthought.shared.support.CDRContainerAttributes;
import lombok.extern.slf4j.Slf4j;

@Validated
@Controller
@Slf4j
public class BankingAccountBalanceApiDelegateImpl implements BankingAccountBalanceApiDelegate {

  @Autowired
  AccountBalanceService balanceService;

  @Autowired
  private DeepThoughtMapper mapper;


  @Override
  public ResponseEntity<ResponseBankingAccountsBalanceListV1> listBalancesByCriteria(
      RequestBalancesByCriteria requestByCriteria) {
    Page<DioBankAccountBalance> balanceList =
        balanceService.listBalancesByCriteria(RequestListAccounts.builder()
            .accountStatus(requestByCriteria.accountStatus()).isOwned(requestByCriteria.isOwned())
            .page(requestByCriteria.page()).pageSize(requestByCriteria.pageSize())
            .productCategory(requestByCriteria.productCategory()).build());

    /**
     * Build response components
     */
    ResponseBankingAccountsBalanceListV1 listResponse = ResponseBankingAccountsBalanceListV1
        .builder().meta(CDRContainerAttributes.toMetaPaginated(balanceList))
        .links(CDRContainerAttributes.toLinksPaginated(balanceList))
        .data(ResponseBankingAccountsBalanceListDataV1.builder()
            .balances(mapper.mapAsList(balanceList.getContent(), BankingBalanceV1.class)).build())
        .build();
    LOG.debug("List response came back with: {}", listResponse);
    return ResponseEntity.ok(listResponse);
  }

  @Override
  public ResponseEntity<ResponseBankingAccountsBalanceListV1> listBalancesByAccounts(
      RequestBalancesByAccounts requestByAccounts) {
    Page<DioBankAccountBalance> balanceList =
        balanceService.listBalancesByAccountList(requestByAccounts);

    /**
     * Build response components
     */
    ResponseBankingAccountsBalanceListV1 listResponse = ResponseBankingAccountsBalanceListV1
        .builder().meta(CDRContainerAttributes.toMetaPaginated(balanceList))
        .links(CDRContainerAttributes.toLinksPaginated(balanceList))
        .data(ResponseBankingAccountsBalanceListDataV1.builder()
            .balances(mapper.mapAsList(balanceList.getContent(), BankingBalanceV1.class)).build())
        .build();
    LOG.debug("List response came back with: {}", listResponse);
    return ResponseEntity.ok(listResponse);
  }

  @Override
  public ResponseEntity<ResponseBankingAccountsBalanceByIdV1> getAccountBalance(UUID accountId)
      throws NotFoundException {
    DioBankAccountBalance balance = balanceService.getBalance(accountId);

    /**
     * Build response components
     */
    ResponseBankingAccountsBalanceByIdV1 listResponse = ResponseBankingAccountsBalanceByIdV1
        .builder().meta(CDRContainerAttributes.toMeta()).links(CDRContainerAttributes.toLinks())
        .data(mapper.map(balance, BankingBalanceV1.class)).build();
    LOG.debug("List response came back with: {}", listResponse);
    return ResponseEntity.ok(listResponse);
  }
}
