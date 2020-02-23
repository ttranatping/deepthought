package io.biza.deepthought.banking.api.impl;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.BankingPayeeDetailV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.BankingPayeeV1;
import io.biza.babelfish.cdr.models.responses.ResponseBankingPayeeByIdV1;
import io.biza.babelfish.cdr.models.responses.ResponseBankingPayeeListV1;
import io.biza.babelfish.cdr.models.responses.container.ResponseBankingPayeeListDataV1;
import io.biza.deepthought.banking.api.delegate.BankingPayeeApiDelegate;
import io.biza.deepthought.banking.requests.RequestListPayees;
import io.biza.deepthought.banking.service.PayeeService;
import io.biza.deepthought.shared.component.mapper.DeepThoughtMapper;
import io.biza.deepthought.shared.exception.InvalidSubjectException;
import io.biza.deepthought.shared.exception.NotFoundException;
import io.biza.deepthought.shared.persistence.model.bank.payments.PayeeData;
import io.biza.deepthought.shared.util.CDRContainerAttributes;
import lombok.extern.slf4j.Slf4j;

@Validated
@Controller
@Slf4j
public class BankingPayeeApiDelegateImpl
    implements BankingPayeeApiDelegate {

  @Autowired
  PayeeService payeeService;

  @Autowired
  private DeepThoughtMapper mapper;


  @Override
  public ResponseEntity<ResponseBankingPayeeListV1> listPayees(RequestListPayees requestList) throws InvalidSubjectException {
    Page<PayeeData> payeeData = payeeService.listPayeesWithFilter(requestList);

    /**
     * Build response components
     */
    ResponseBankingPayeeListV1 listResponse = ResponseBankingPayeeListV1
        .builder().meta(CDRContainerAttributes.toMetaPaginated(payeeData))
        .links(CDRContainerAttributes.toLinksPaginated(payeeData))
        .data(ResponseBankingPayeeListDataV1.builder().payees(mapper.mapAsList(payeeData.getContent(), BankingPayeeV1.class)).build())
        .build();
    LOG.debug("List response came back with: {}", listResponse);
    return ResponseEntity.ok(listResponse);
  }

  @Override
  public ResponseEntity<ResponseBankingPayeeByIdV1> getPayeeDetail(UUID payeeId) throws NotFoundException, InvalidSubjectException {
    PayeeData payee = payeeService.getPayee(payeeId);
    ResponseBankingPayeeByIdV1 payeeResponse = new ResponseBankingPayeeByIdV1();
    payeeResponse.meta(CDRContainerAttributes.toMeta());
    payeeResponse.links(CDRContainerAttributes.toLinks());
    payeeResponse.data(mapper.map(payee, BankingPayeeDetailV1.class));
    return ResponseEntity.ok(payeeResponse);
  }
}
