package io.biza.deepthought.shared.persistence.mapper.grant;

import java.util.ArrayList;
import java.util.List;
import io.biza.babelfish.cdr.enumerations.BankingLoanRepaymentType;
import io.biza.babelfish.cdr.enumerations.BankingTermDepositMaturityInstructions;
import io.biza.babelfish.cdr.enumerations.PayloadTypeBankingAccount;
import io.biza.babelfish.cdr.exceptions.LabelValueEnumValueNotSupportedException;
import io.biza.babelfish.cdr.models.payloads.banking.account.BankingAccountDetailV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.BankingAccountV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.BankingCreditCardAccountV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.BankingLoanAccountV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.BankingTermDepositAccountV1;
import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductFeatureWithActivatedV1;
import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.grant.DioGrant;
import io.biza.deepthought.shared.persistence.model.bank.account.BankAccountCreditCardData;
import io.biza.deepthought.shared.persistence.model.bank.account.BankAccountFeatureData;
import io.biza.deepthought.shared.persistence.model.bank.account.BankAccountLoanAccountData;
import io.biza.deepthought.shared.persistence.model.bank.account.BankAccountTermDepositData;
import io.biza.deepthought.shared.persistence.model.grant.GrantCustomerAccountData;
import io.biza.deepthought.shared.persistence.model.grant.GrantData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;

@Slf4j
public class GrantDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(GrantData.class, DioGrant.class).fieldAToB("created", "created")
        .byDefault().register();
  }
}
