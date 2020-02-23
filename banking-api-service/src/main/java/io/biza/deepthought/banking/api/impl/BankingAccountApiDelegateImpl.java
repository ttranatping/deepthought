package io.biza.deepthought.banking.api.impl;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import io.biza.babelfish.cdr.models.payloads.banking.account.BankingAccountDetailV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.BankingAccountV1;
import io.biza.babelfish.cdr.models.responses.ResponseBankingAccountByIdV1;
import io.biza.babelfish.cdr.models.responses.ResponseBankingAccountListV1;
import io.biza.babelfish.cdr.models.responses.container.ResponseBankingAccountListDataV1;
import io.biza.deepthought.banking.api.delegate.BankingAccountApiDelegate;
import io.biza.deepthought.shared.component.mapper.DeepThoughtMapper;
import io.biza.deepthought.shared.component.service.GrantService;
import io.biza.deepthought.shared.exception.NotFoundException;
import io.biza.deepthought.shared.payloads.requests.RequestListAccounts;
import io.biza.deepthought.shared.persistence.model.grant.GrantAccountData;
import io.biza.deepthought.shared.util.CDRContainerAttributes;
import lombok.extern.slf4j.Slf4j;

@Validated
@Controller
@Slf4j
public class BankingAccountApiDelegateImpl implements BankingAccountApiDelegate {

  @Autowired
  GrantService bankingService;

  @Autowired
  private DeepThoughtMapper mapper;

  @Override
  public ResponseEntity<ResponseBankingAccountByIdV1> getAccountDetail(UUID accountId)
      throws NotFoundException {
    GrantAccountData accountResult = bankingService.getGrantAccount(accountId);
    ResponseBankingAccountByIdV1 accountResponse = new ResponseBankingAccountByIdV1();
    accountResponse.meta(CDRContainerAttributes.toMeta());
    accountResponse.links(CDRContainerAttributes.toLinks());
    accountResponse.data(mapper.map(accountResult, BankingAccountDetailV1.class));
    return ResponseEntity.ok(accountResponse);
  }

  @Override
  public ResponseEntity<ResponseBankingAccountListV1> listAccounts(
      RequestListAccounts requestList) {
    Page<GrantAccountData> accountList = bankingService.listGrantAccountsPaginated(requestList);

    /**
     * Build response components
     */
    ResponseBankingAccountListV1 listResponse = ResponseBankingAccountListV1.builder()
        .meta(CDRContainerAttributes.toMetaPaginated(accountList))
        .links(CDRContainerAttributes.toLinksPaginated(accountList))
        .data(ResponseBankingAccountListDataV1.builder()
            .accounts(mapper.mapAsList(accountList.getContent(), BankingAccountV1.class)).build())
        .build();
    LOG.debug("List response came back with: {}", listResponse);
    return ResponseEntity.ok(listResponse);
  }
}
