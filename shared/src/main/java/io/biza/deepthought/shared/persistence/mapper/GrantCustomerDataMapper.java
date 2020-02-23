package io.biza.deepthought.shared.persistence.mapper;

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
import io.biza.deepthought.shared.payloads.dio.grant.DioGrantAccount;
import io.biza.deepthought.shared.payloads.dio.grant.DioGrantCustomer;
import io.biza.deepthought.shared.persistence.model.bank.account.BankAccountCreditCardData;
import io.biza.deepthought.shared.persistence.model.bank.account.BankAccountFeatureData;
import io.biza.deepthought.shared.persistence.model.bank.account.BankAccountLoanAccountData;
import io.biza.deepthought.shared.persistence.model.bank.account.BankAccountTermDepositData;
import io.biza.deepthought.shared.persistence.model.grant.GrantAccountData;
import io.biza.deepthought.shared.persistence.model.grant.GrantCustomerData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;

@Slf4j
public class GrantCustomerDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(GrantCustomerData.class, DioGrantCustomer.class)
      .field("account.id", "accountId").field("access", "access").register();
  }
}
